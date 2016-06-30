/*
C * Copyright (C) 2015 SDN Hub

 Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.
 You may not use this file except in compliance with this License.
 You may obtain a copy of the License at

    http://www.gnu.org/licenses/gpl-3.0.txt

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 implied.

 *
 */

/* CLOUD_SERVER 와 FOG_SERVER 간의 경로 변경 과정 */
/*
 * [c.f.] macTable에는 srcMac과 ingressNodeConnectorId가 매핑 되어 있다.
 * [1] srcIp와 srcMac를 매핑한 후 ipMacTable에 저장.
 * [2] IF) PACKET 들어온 해당 ovs가 FOG_SERVER가 동작할 위치인지 확인. 즉, ovsId == runningFogOvsId 인지 확인.
 * [3]   IF) docker img가 FOG_SERVER 상에 배포되어 docker container가 동작 중인지 확인.
 * [4-1]   IF) PACKET's dstIp == CLOUD_SERVER's address [10.0.0.100] 인지 확인.
 * [5-1]     PACKET의 dstIp (CLOUD_SERVER [10.0.0.100]) 및 dstMac을 FOG_SERVER's address [10.0.0.10]으로 바꿔주는 
 *           FLOW_RULE을 해당 ovsId를 갖는 ovs에 내려준다. outputPort도 바꿔준다.
 *         FI)
 * [4-2]   IF) PACKET's srcIp == FOG_SERVER's address [10.0.0.10] 인지 확인.
 * [5-2]     PACKET의 srcIp (FOG_SERVER [10.0.0.10]) 및 srcMac을 CLOUD_SERVER's address [10.0.0.100]으로 바꿔주는
 *           FLOW_RULE을 해당 ovsId를 갖는 ovs에 내려준다. (outputPort는 바꾸지 않아도 될 것 같다.)
 *         FI)
 *       FI)
 *     FI)
 */

package org.sdnhub.odl.tutorial.learningswitch.impl;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketReceived;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInputBuilder;
import org.opendaylight.yangtools.concepts.Registration;
import org.sdnhub.odl.tutorial.utils.PacketParsingUtils;
import org.sdnhub.odl.tutorial.utils.inventory.InventoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class TutorialL2Forwarding  implements AutoCloseable, PacketProcessingListener {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
// SMS NIaaS: [*] variables
	// SMS: ip-mac matching table
	private Map<String, String> ipMacTable = new HashMap<String, String>();
	
	// Commented by SMS: private String function = "hub";
	private String function = "learningSwitch";
	
	private static String srcIp;
	private static String dstIp;
//	private static String CLOUD_SERVER = "10.0.0.100";
//	private static String FOG_SERVER;
	
	private static int SMS_COUNT_COUNT = 1;
	private Integer[] macTableInit = new Integer[20];
	
//	private BufferedWriter ipMacTableOut = null;
//	private BufferedWriter macTableOut = null;
	
	private Map<String, NodeConnectorId>[] macTableDst = new HashMap[20];
	private Map<NodeConnectorId, NodeConnectorId> linkTable = new HashMap<NodeConnectorId, NodeConnectorId>();
// SMS NIaaS END	
	
	private final static long FLOOD_PORT_NUMBER = 0xfffffffbL;
	
	//Members specific to this class
	// Commented by SMS:	private Map<String, NodeConnectorId> macTable = new HashMap<String, NodeConnectorId>();
	private Map<String, NodeConnectorId>[] macTable = new HashMap[20];	
	
	//Members related to MD-SAL operations
	private List<Registration> registrations;
	private DataBroker dataBroker;
	private PacketProcessingService packetProcessingService;
	
	private static String srcMac;
	private static String dstMac;
	
    public TutorialL2Forwarding(DataBroker dataBroker, NotificationProviderService notificationService, RpcProviderRegistry rpcProviderRegistry) {
    	//Store the data broker for reading/writing from inventory store
		this.dataBroker = dataBroker;

		//Get access to the packet processing service for making RPC calls later
		this.packetProcessingService = rpcProviderRegistry.getRpcService(PacketProcessingService.class);    

    	//List used to track notification (both data change and YANG-defined) listener registrations
		this.registrations = Lists.newArrayList();

		//Register this object for receiving notifications when there are PACKET_INs
		registrations.add(notificationService.registerNotificationListener(this));
  	}

	@Override
	public void close() throws Exception {
		for (Registration registration : registrations) {
			registration.close();
		}
		registrations.clear();
	}
    
	@Override
	public void onPacketReceived(PacketReceived notification) {		
			
    	LOG.trace("Received packet notification {}", notification.getMatch());
    	
		NodeConnectorRef ingressNodeConnectorRef = notification.getIngress();
		NodeRef ingressNodeRef = InventoryUtils.getNodeRef(ingressNodeConnectorRef);
		NodeConnectorId ingressNodeConnectorId = InventoryUtils.getNodeConnectorId(ingressNodeConnectorRef);
		NodeId ingressNodeId = InventoryUtils.getNodeId(ingressNodeConnectorRef);
		// Useful to create it beforehand
		NodeConnectorId floodNodeConnectorId = InventoryUtils.getNodeConnectorId(ingressNodeId, FLOOD_PORT_NUMBER);
		NodeConnectorRef floodNodeConnectorRef = InventoryUtils.getNodeConnectorRef(floodNodeConnectorId);

// SMS NIaaS: Variables
		// macTableTotal
		Set<Entry<String, NodeConnectorId>> setTotal;
		Iterator<Entry<String,NodeConnectorId>> itTotal;
		// macTable
		Set<Entry<String, NodeConnectorId>> set;
		Iterator<Entry<String,NodeConnectorId>> it;
		Map.Entry<String, NodeConnectorId> e;
		Set<Entry<String, NodeConnectorId>> set1;
		Iterator<Entry<String,NodeConnectorId>> it1;
		Map.Entry<String, NodeConnectorId> e1;
		// ipMac
		Set<Entry<String, String>> set2;
		Iterator<Entry<String,String>> it2;
		Map.Entry<String, String> e2;
		// [2]
		String switchNodeId = SMS_InventoryUtils.getSwitchNodeId(ingressNodeConnectorId);
		int switchNodeId_number = SMS_InventoryUtils.getSwitchNodeId_number(ingressNodeConnectorId);
		String switchInputPort = SMS_InventoryUtils.getOutputPort(ingressNodeConnectorId);
		
		// [2], [3]
		int FOG_SERVER_IP_ON = 0;
		// [3]
		NodeConnectorId fogNodeConnectorId = null;
		String fogSwitchNodeId = "";
		String fogSwitchOutputPort = "";
		ArrayList<String> fogServer_Ip_List = new ArrayList<String>();
		SMS_Docker.addFogServerIpToList(fogServer_Ip_List);
		int fogServerDockerList_count = 0;
		String fogServerIp = "";
		String fogServerMac = "";
		String stringEtherTypeHex = "";
		// [3]+
		int CLOUD_SERVER_IP_ON = 0;
		NodeConnectorId cloudNodeConnectorId = null;
		String cloudSwitchNodeId = "";
		String cloudSwitchOutputPort = "";
		ArrayList<String> cloudServer_Ip_List = new ArrayList<String>();
		SMS_Docker.addCloudServerIpToList(cloudServer_Ip_List); // 일단 CLOUD_SERVER는 한곳이다.
		String cloudServerIp = "";
		String cloudServerMac = "";
// SMS NIaaS END
		
// SMS NIaaS: INIT
		// macTable init
		if(macTable[0] == null) macTable[0] = new HashMap<String, NodeConnectorId>();
			
		if(macTable[switchNodeId_number] == null){
			macTable[switchNodeId_number] = new HashMap<String, NodeConnectorId>();
			LOG.debug("[macTable init] switchNodeId_number: {}", switchNodeId_number);
			macTableDst[switchNodeId_number] = new HashMap<String, NodeConnectorId>();
		}
// SMS END
		
        /*
         * Logic:
         * 0. Ignore LLDP packets
         * 1. If behaving as "hub", perform a PACKET_OUT with FLOOD action
         * 2. Else if behaving as "learning switch",
         *    2.1. Extract MAC addresses
         *    2.2. Update MAC table with source MAC address
         *    2.3. Lookup in MAC table for the target node connector of dst_mac
         *         2.3.1 If found, 
         *               2.3.1.1 perform FLOW_MOD for that dst_mac through the target node connector
         *               2.3.1.2 perform PACKET_OUT of this packet to target node connector
         *         2.3.2 If not found, perform a PACKET_OUT with FLOOD action
         */
		
    	//Ignore LLDP packets, or you will be in big trouble
		byte[] etherTypeRaw = PacketParsingUtils.extractEtherType(notification.getPayload());
		int etherType = (0x0000ffff & ByteBuffer.wrap(etherTypeRaw).getShort());
		if (etherType == 0x88cc) {
			return;
		}
		
		// Hub implementation
		if (function.equals("hub")) {
			//flood packet (1)
			packetOut(ingressNodeRef, floodNodeConnectorRef, notification.getPayload());
		} else {
        	// TODO: Extract payload
			byte[] payload = notification.getPayload();
			
        	// TODO: Extract MAC address (2.1)
			byte[] dstMacRaw = PacketParsingUtils.extractDstMac(payload);
			byte[] srcMacRaw = PacketParsingUtils.extractSrcMac(payload);
			
			srcMac = PacketParsingUtils.rawMacToString(srcMacRaw);
			dstMac = PacketParsingUtils.rawMacToString(dstMacRaw);
			NodeConnectorId egressNodeConnectorId = macTable[switchNodeId_number].get(dstMac);
			
// SMS NIaaS: init
			//SMS: Extract IP address
			srcIp = SMS_Parser_IpAddr.ipAddr_byteArray_to_stringIp(SMS_Parser_IpAddr.get_byteArray_SrcIp(payload));
			dstIp = SMS_Parser_IpAddr.ipAddr_byteArray_to_stringIp(SMS_Parser_IpAddr.get_byteArray_DstIp(payload));
			stringEtherTypeHex = SMS_Parser_MacAddr.get_stringEtherTypeHex(payload);
// SMS NIaaS END
			
// SMS NIaaS: init flow |  priority=0 actions=drop  ||  priority=100 dl_type=0x88cc actions=CONTROLLER:65535  |
//			if (macTableInit[switchNodeId_number] == null) {
//				macTableInit[switchNodeId_number] = 1;
//				TutorialL2Forwarding_ProgramL2Flow.programL2Flow_initSwitch_1(payload, ingressNodeId, dataBroker);
//				TutorialL2Forwarding_ProgramL2Flow.programL2Flow_initSwitch_2(payload, ingressNodeId, dataBroker);
//			}
// SMS NIaaS END
			
// SMS NIaaS: linkTable
			if(this.macTable[0].get(srcMac) != null){
				
			}
// SMS NIaaS END
			
			

	// SMS NIaaS: macTable[0] 에는 모든 ovs의 MAC-ingressNodeConnectorId 정보를 저장
				this.macTable[0].put(srcMac, ingressNodeConnectorId);
				setTotal = macTable[0].entrySet();
				itTotal = setTotal.iterator();
				Map.Entry<String, NodeConnectorId> eTotal;
				BufferedWriter macTableOutTotal = null;
				String macTableNameTotal = "mactable_0.log";
				try {
					macTableOutTotal = new BufferedWriter(new FileWriter("/home/sms/workspace/SDNHub_Opendaylight_Tutorial/admin/log/"+macTableNameTotal));
					while(itTotal.hasNext()) {							
						eTotal = (Map.Entry<String, NodeConnectorId>)itTotal.next();
						String txt = "|key: " + eTotal.getKey() + "\t|value: " + eTotal.getValue();
						macTableOutTotal.write(txt);
						macTableOutTotal.newLine();
					}
					macTableOutTotal.close();
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
	// SMS NIaaS END
				
	// SMS NIaaS: macTable
				/* [c.f.] macTable에는 srcMac과 ingressNodeConnectorId가 매핑 되어 있다. */
				// TODO: Learn source MAC address (2.2 - 1)
				byte[] ttlRaw = PacketParsingUtils.extractIpHeaderTTL(payload);
				String ttl = PacketParsingUtils.rawttlToString(ttlRaw);
				LOG.debug("=====================================================================================");
				LOG.debug("1>  srcMac: {}  | dstMac: {}  | ethType: {}  | ttl: {}  | SMS_COUNT_COUNT: {}", srcMac, dstMac, stringEtherTypeHex, ttl, SMS_COUNT_COUNT);	
				SMS_COUNT_COUNT++;
				this.macTable[switchNodeId_number].put(srcMac, ingressNodeConnectorId); // SMS : Map<String, NodeConnectorId> macTable
	//			this.macTableDst[switchNodeId_number].put(dstMac, egressNodeConnectorId); // V
	//			this.macTableDst[switchNodeId_number].put(srcMac, floodNodeConnectorId); // V
	//			LOG.debug("2>  ingressNodeConnectorId: {}",ingressNodeConnectorId);
	//				LOG.debug("(switchNodeId: {} | switchInputPort: {})", switchNodeId, switchInputPort);
				set = macTable[switchNodeId_number].entrySet();
	//			set1 = macTableDst[switchNodeId_number].entrySet(); // V
				it = set.iterator();
	//			it1 = set1.iterator();
	//			Map.Entry<String, NodeConnectorId> e;
				BufferedWriter macTableOut = null;
				String macTableName = "mactable_"+switchNodeId_number+".log";
				try {
					macTableOut = new BufferedWriter(new FileWriter("/home/sms/workspace/SDNHub_Opendaylight_Tutorial/admin/log/"+macTableName));
					while(it.hasNext()) {							
						e = (Map.Entry<String, NodeConnectorId>)it.next();
						String txt = "|key: " + e.getKey() + "\t|value: " + e.getValue();
						macTableOut.write(txt);
						macTableOut.newLine();
					}
	//				macTableOut.write("////////////////////////////////////////////////");
	//				macTableOut.newLine();
	//				while(it1.hasNext()) {							
	//					e1 = (Map.Entry<String, NodeConnectorId>)it1.next();
	//					String txt = "|key: " + e1.getKey() + "\t|value: " + e1.getValue();
	//					macTableOut.write(txt);
	//					macTableOut.newLine();
	//				}
					macTableOut.close();
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
	// SMS NIaaS END
			
			if (etherType == 0x800) {
	/* [1] srcIp와 srcMac를 매핑한 후 ipMacTable에 저장. */
	// SMS NIaaS: ipMacTable
				this.ipMacTable.put(srcIp, srcMac);
				set2 = ipMacTable.entrySet();
				it2 = set2.iterator();
	//			Map.Entry<String, String> e2;
				BufferedWriter ipMacTableOut = null;
				try {
					ipMacTableOut = new BufferedWriter(new FileWriter("/home/sms/workspace/SDNHub_Opendaylight_Tutorial/admin/log/ipmactable.log"));
					while(it2.hasNext()) {
						e2 = (Map.Entry<String, String>)it2.next();
						String txt = "|key: " + e2.getKey() + "    \t|value: " + e2.getValue();
						ipMacTableOut.write(txt);
						ipMacTableOut.newLine();
					}
					ipMacTableOut.close();
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
			}
// SMS NIaaS END
			
// SMS: Learn source MAC address (2.2 - 2)
			// TODO: Lookup destination MAC address in table (2.3)
//			NodeConnectorId egressNodeConnectorId = macTable[switchNodeId_number].get(dstMac);
			LOG.debug("2>  ingressNodeConnectorId: {}",ingressNodeConnectorId);
			LOG.debug("3>  egressNodeConnectorId: {}", egressNodeConnectorId);
			NodeConnectorId egressNodeConnectorId_macTable0 = macTable[0].get(dstMac);
			LOG.debug(" >  egressNodeConnectorId(macTable[0]): {}", egressNodeConnectorId_macTable0);
			
			
			
/* [3]   IF) docker img가 FOG_SERVER 상에 배포되어 docker container가 동작 중인지 확인. (e.g. 10.0.0.10) */
// SMS NIaaS
			
//			String switchNodeId = null;
//			if(egressNodeConnectorId != null){
//				switchNodeId = SMS_InventoryUtils.getSwitchNodeId(egressNodeConnectorId);
//			}
			FOG_SERVER_IP_ON = 0;
			if(fogServer_Ip_List.isEmpty()){
				FOG_SERVER_IP_ON = 0;
			}else{
				for(String list: fogServer_Ip_List){
					fogServerIp = list; 
					fogServerMac = ipMacTable.get(fogServerIp); 
					LOG.debug("*> fogServerIp: {} | fogServerMac: {}",fogServerIp,fogServerMac);
					if(fogServerMac != null){ // fogServerIp
						fogNodeConnectorId = macTable[switchNodeId_number].get(fogServerMac);
						LOG.debug("**> fogNodeConnectorId: {}",fogNodeConnectorId);
						if(fogNodeConnectorId != null){ // fogServerMac
							fogSwitchNodeId = SMS_InventoryUtils.getSwitchNodeId(fogNodeConnectorId); // openflow:x
							fogSwitchOutputPort = SMS_InventoryUtils.getOutputPort(fogNodeConnectorId); // fogSwitchOutputPort
							LOG.debug("***> switchNodeId: {} | fogSwitchNodeId: {}",switchNodeId,fogSwitchNodeId);
							if(switchNodeId.equals(fogSwitchNodeId)) {
								LOG.debug("****> fogServerMac: {} | fogNodeConnectorId: {} | switchNodeId: {} | fogSwitchNodeId: {}",
										fogServerMac, fogNodeConnectorId, switchNodeId, fogSwitchNodeId);
								FOG_SERVER_IP_ON = 1;
								break;
							}
						}
					}
				}
			}
			CLOUD_SERVER_IP_ON = 0;
			if(cloudServer_Ip_List.isEmpty()){
				CLOUD_SERVER_IP_ON = 0;
			}else{
				cloudServerIp = cloudServer_Ip_List.get(0); // cloudServerIp
				cloudServerMac = ipMacTable.get(cloudServerIp); // cloudServerMac
				if(cloudServerMac != null){
//									cloudNodeConnectorId = macTable[0].get(cloudServerMac); 
//									if(fogNodeConnectorId != null){ // cloudServerMac
						CLOUD_SERVER_IP_ON = 1;
//									}
				}
			}
// SMS NIaaS END	

// FOG_SERVER_OVS_ID_CHECK과 DOCKER_SWITCH_CHECK 둘다 1일 경우, 임시로 수동으로 openflow:1과 10.0.0.10을 매핑.
// 나중에는 자동으로 인지하고 매핑하도록 해야함.
// SMS NiaaS: set up "FLOW_RULE" to ovs
			LOG.debug("4> fogServerIp: {} | fogServerMac: {} | fogSwitchOutputPort: {} | cloudServerIp: {} | cloudServerMac: {}",
					fogServerIp, fogServerMac, fogSwitchOutputPort, cloudServerIp, cloudServerMac);
			LOG.debug(" > FOG_SERVER_IP_ON: {} | CLOUD_SERVER_IP_ON: {}", FOG_SERVER_IP_ON, CLOUD_SERVER_IP_ON);
			LOG.debug("5> switchNodeId {} | fogSwitchNodeId {}", switchNodeId, fogSwitchNodeId);
			// TODO: If found (2.3.1)
			if(egressNodeConnectorId != null){
				// TODO: 2.3.1.1 perform FLOW_MOD for that dst_mac through the target node connector
				LOG.debug("6-1>  ");
				if(stringEtherTypeHex.equals("0800")){
					if(FOG_SERVER_IP_ON == 1 && CLOUD_SERVER_IP_ON == 1){
						if(srcMac.equals(cloudServerMac)){
							;
						}else if(srcMac.equals(fogServerMac)){
							TutorialL2Forwarding_ProgramL2Flow.programL2Flow_pathChange_FogToCloud_2(ingressNodeId, dataBroker,
									fogServerIp, fogServerMac, fogSwitchOutputPort, fogNodeConnectorId,
									cloudServerIp, cloudServerMac, SMS_InventoryUtils.getOutputPort(egressNodeConnectorId),
									dstMac);
						}else {
							TutorialL2Forwarding_ProgramL2Flow.programL2Flow_pathChange_CloudToFog(ingressNodeId, dataBroker,
									fogServerIp, fogServerMac, fogSwitchOutputPort,
									cloudServerIp, cloudServerMac,
									srcMac);
							TutorialL2Forwarding_ProgramL2Flow.programL2Flow_pathChange_FogToCloud(ingressNodeId, dataBroker,
									fogServerIp, fogServerMac, fogSwitchOutputPort,
									cloudServerIp, cloudServerMac, switchInputPort,
									srcMac);
						}
					}
				}
				
// SMS NiaaS End
		
				NodeConnectorRef egressNodeConnectorRef = InventoryUtils.getNodeConnectorRef(egressNodeConnectorId);
				NodeRef egressNodeRef = InventoryUtils.getNodeRef(egressNodeConnectorRef);
				
				// TODO: 2.3.1.2 perform PACKET_OUT of this packet to target node connector
				packetOut(egressNodeRef, egressNodeConnectorRef, payload);
			}
			else if(egressNodeConnectorId_macTable0 != null){
				LOG.debug("6-2>  ");
				if(stringEtherTypeHex.equals("0800")){
					if(FOG_SERVER_IP_ON == 1 && CLOUD_SERVER_IP_ON == 1){
						if(srcMac.equals(cloudServerMac)){
							;
						}else if(srcMac.equals(fogServerMac)){
							TutorialL2Forwarding_ProgramL2Flow.programL2Flow_pathChange_FogToCloud_2(ingressNodeId, dataBroker,
									fogServerIp, fogServerMac, fogSwitchOutputPort, fogNodeConnectorId,
									cloudServerIp, cloudServerMac, "4294967291", // flood
									dstMac);
						}else {
							TutorialL2Forwarding_ProgramL2Flow.programL2Flow_pathChange_CloudToFog(ingressNodeId, dataBroker,
									fogServerIp, fogServerMac, fogSwitchOutputPort,
									cloudServerIp, cloudServerMac,
									srcMac);
							TutorialL2Forwarding_ProgramL2Flow.programL2Flow_pathChange_FogToCloud(ingressNodeId, dataBroker,
									fogServerIp, fogServerMac, fogSwitchOutputPort,
									cloudServerIp, cloudServerMac, switchInputPort,
									srcMac);
						}
					}
				}
				
// SMS NIaaS End
		
				// TODO: 2.3.1.2 perform PACKET_OUT of this packet to target node connector
				packetOut(ingressNodeRef, floodNodeConnectorRef, payload);
			}
			else{
            	// 2.3.2 Flood packet
				LOG.debug("6-3>  floodNodeConnectorId: {}", floodNodeConnectorId); // SMS
				packetOut(ingressNodeRef, floodNodeConnectorRef, payload);
			}
		}
	}

	private void packetOut(NodeRef egressNodeRef, NodeConnectorRef egressNodeConnectorRef, byte[] payload) {
		Preconditions.checkNotNull(packetProcessingService);
		//Construct input for RPC call to packet processing service
		TransmitPacketInput input = new TransmitPacketInputBuilder()
				.setPayload(payload)
				.setNode(egressNodeRef)
				.setEgress(egressNodeConnectorRef)
				.build();
		packetProcessingService.transmitPacket(input);
	}    
}



