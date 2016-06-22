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
	private static String CLOUD_SERVER = "10.0.0.100";
	private static String FOG_SERVER;
	
	private static int SMS_COUNT_COUNT = 1;
	
//	private BufferedWriter ipMacTableOut = null;
//	private BufferedWriter macTableOut = null;
// SMS NIaaS END	
	
	private final static long FLOOD_PORT_NUMBER = 0xfffffffbL;
	
	//Members specific to this class
//	private Map<String, NodeConnectorId> macTable = new HashMap<String, NodeConnectorId>();
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
		// macTable
		Set<Entry<String, NodeConnectorId>> set;
		Iterator<Entry<String,NodeConnectorId>> it;
		// ipMac
		Set<Entry<String, String>> set2;
		Iterator<Entry<String,String>> it2;
		// [2]
		String switchNodeId = SMS_InventoryUtils.getSwitchNodeId(ingressNodeConnectorId);
		int switchNodeId_number = SMS_InventoryUtils.getSwitchNodeId_number(ingressNodeConnectorId);
		String switchInputPort = SMS_InventoryUtils.getOutputPort(ingressNodeConnectorId);
		String ovsId = "";
		ArrayList<String> fogServer_OvsId_List = new ArrayList<String>(); // FOG_SERVER_OVS_ID.txt 값들 저장할 List
		SMS_Docker.addFogServerOvsIdToList(fogServer_OvsId_List);
		int fogServerOvsIdList_count = 0;
		// [2], [3]
		int FOG_SERVER_OVS_ID_ON = 0;
		int DOCKER_SWITCH_IP_ON = 0;
		// [3]
		NodeConnectorId fogNodeConnectorId = null;
		String fogNodeId = "";
		String fogOutputPort = "";
		ArrayList<String> fogServer_Ip_List = new ArrayList<String>();
		SMS_Docker.addFogServerIpToList(fogServer_Ip_List);
		int fogServerDockerList_count = 0;
		String fogServerIp = "";
		String fogServerMac = "";
		String stringEtherTypeHex = "";
// SMS NIaaS END
		
// SMS NIaaS: INIT
		// macTable init
		if(macTable[switchNodeId_number] == null){
			macTable[switchNodeId_number] = new HashMap<String, NodeConnectorId>();
			LOG.debug("[macTable init] switchNodeId_number: {}", switchNodeId_number);
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
//		if (etherType == 0x86dd) { // ipv6
//			return;
//		}
		
//// SMS NIaaS
//		BufferedReader reader = null;
//		String strReader = "";
//		try {
//			FileReader fr = new FileReader("/home/sms/workspace/SDNHub_Opendaylight_Tutorial/admin/table_clear.txt");
//			if (fr != null) {
//				reader = new BufferedReader(fr);
//				strReader = reader.readLine();
//				if (strReader.equals("clear")) {
//					macTable.clear();
//					ipMacTable.clear();
//					reader.close();
//					return;
//				}
//				reader.close();
//			}
//		} catch (FileNotFoundException e4) {
//			// TODO Auto-generated catch block
//			e4.printStackTrace();
//		} catch (IOException e4) {
//			// TODO Auto-generated catch block
//			e4.printStackTrace();
//		}
//// SMS NIaaS END
		
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

// SMS NIaaS: init
			//SMS: Extract IP address
			srcIp = SMS_Parser_IpAddr.ipAddr_byteArray_to_stringIp(SMS_Parser_IpAddr.get_byteArray_SrcIp(payload));
			dstIp = SMS_Parser_IpAddr.ipAddr_byteArray_to_stringIp(SMS_Parser_IpAddr.get_byteArray_DstIp(payload));
			stringEtherTypeHex = SMS_Parser_MacAddr.get_stringEtherTypeHex(payload);
// SMS NIaaS END
			
			
			
			
// SMS NIaaS: macTable
			/* [c.f.] macTable에는 srcMac과 ingressNodeConnectorId가 매핑 되어 있다. */
			// TODO: Learn source MAC address (2.2 - 1)
//			this.macTable.put(srcMac, ingressNodeConnectorId); // SMS : Map<String, NodeConnectorId> macTable
			byte[] ttlRaw = PacketParsingUtils.extractIpHeaderTTL(payload);
			String ttl = PacketParsingUtils.rawttlToString(ttlRaw);
			
//			if(stringEtherTypeHex.equals("0800") && ttl.equals("40")){
//			if(ttl.equals("40")){
			if(stringEtherTypeHex.equals("0800") || stringEtherTypeHex.equals("0806")){ // ipv4와 ARP
				LOG.debug("=====================================================================================");
				LOG.debug("1>  srcMac: {}  | dstMac: {}  | ethType: {}  | ttl: {}  | SMS_COUNT_COUNT: {}", srcMac, dstMac, stringEtherTypeHex, ttl, SMS_COUNT_COUNT);	
				SMS_COUNT_COUNT++;
				this.macTable[switchNodeId_number].put(srcMac, ingressNodeConnectorId); // SMS : Map<String, NodeConnectorId> macTable
				LOG.debug("2>  ingressNodeConnectorId: {}",ingressNodeConnectorId);
//				LOG.debug("(switchNodeId: {} | switchInputPort: {})", switchNodeId, switchInputPort);
				
	//			LOG.debug("macTable++++++++++++++++++++++++++++++++++++++++++++++++++++");
	//			set = macTable.entrySet();
				set = macTable[switchNodeId_number].entrySet();
				it = set.iterator();
				Map.Entry<String, NodeConnectorId> e;
				BufferedWriter macTableOut = null;
				String macTableName = "mactable_"+switchNodeId_number+".log";
				try {
					macTableOut = new BufferedWriter(new FileWriter("/home/sms/workspace/SDNHub_Opendaylight_Tutorial/admin/log/"+macTableName));
					while(it.hasNext()) {							
						e = (Map.Entry<String, NodeConnectorId>)it.next();
						String txt = "|key: " + e.getKey() + "\t|value: " + e.getValue();
						macTableOut.write(txt);
						macTableOut.newLine();
//						LOG.debug("  | key: {} | value: {} |", e.getKey(), e.getValue());
					}
					macTableOut.close();
				} catch (IOException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
	//			LOG.debug("macTable----------------------------------------------------");
			}				
// SMS NIaaS END

			
			
/* [1] srcIp와 srcMac를 매핑한 후 ipMacTable에 저장. */
// SMS NIaaS: ipMacTable
//			if(!srcIp.equals("0.0.0.0")) this.ipMacTable.put(srcIp, srcMac);
			this.ipMacTable.put(srcIp, srcMac);
//			LOG.debug("IpMacTable++++++++++++++++++++++++++++++++++++++++++++++++++");
			set2 = ipMacTable.entrySet();
			it2 = set2.iterator();
			Map.Entry<String, String> e2;
			BufferedWriter ipMacTableOut = null;
			try {
				ipMacTableOut = new BufferedWriter(new FileWriter("/home/sms/workspace/SDNHub_Opendaylight_Tutorial/admin/log/ipmactable.log"));
				while(it2.hasNext()) {
					e2 = (Map.Entry<String, String>)it2.next();
					String txt = "|key: " + e2.getKey() + "    \t|value: " + e2.getValue();
					ipMacTableOut.write(txt);
					ipMacTableOut.newLine();
//					LOG.debug("  | key: {} | value: {}  |", e2.getKey(), e2.getValue());
				}
				ipMacTableOut.close();
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
//			LOG.debug("IpMacTable--------------------------------------------------");
// SMS NIaaS END
			
			// SMS: Learn source MAC address (2.2 - 2)
			// TODO: Lookup destination MAC address in table (2.3)
//			NodeConnectorId egressNodeConnectorId = macTable.get(dstMac);
			NodeConnectorId egressNodeConnectorId = macTable[switchNodeId_number].get(dstMac);
			
			// TODO: If found (2.3.1)
			if(egressNodeConnectorId != null){
				LOG.debug("3>  egressNodeConnectorId: {}", egressNodeConnectorId);
				// TODO: 2.3.1.1 perform FLOW_MOD for that dst_mac through the target node connector				
/* [2] IF) PACKET 들어온 해당 ovs가 FOG_SERVER가 동작할 위치인지 확인. 즉, ovsId == runningFogOvsId 인지 확인. (e.g. openflow:1:5) */
				if(fogServer_OvsId_List.isEmpty()){
					FOG_SERVER_OVS_ID_ON = 0;
				}else{
//					LOG.debug("<1111111111  fogServerOvsIdList");
//					for(fogServerOvsIdList_count = 0 ; fogServerOvsIdList_count < fogServer_OvsId_List.size() ; fogServerOvsIdList_count++){
					for(String tmp: fogServer_OvsId_List){
//						ovsId = fogServer_OvsId_List.get(fogServerOvsIdList_count);
						ovsId = tmp;
						if(!(ovsId == null)){
							if(switchNodeId.equals(ovsId)){ // openflow:?
								FOG_SERVER_OVS_ID_ON = 1;
								break;
							}
						}
//						LOG.debug("fogServerOvsIdList({}) {}", fogServerOvsIdList_count, fogServerOvsIdList.get(fogServerOvsIdList_count));
					}
//					LOG.debug("1111111111>");
				}
				
/* [3]   IF) docker img가 FOG_SERVER 상에 배포되어 docker container가 동작 중인지 확인. (e.g. 10.0.0.10) */
				if(fogServer_Ip_List.isEmpty()){
					DOCKER_SWITCH_IP_ON = 0;
				}else{
//					LOG.debug("<2222222222  fogServerDockerList");
//					for(fogServerDockerList_count = 0 ; fogServerDockerList_count < fogServer_Ip_List.size() ; fogServerDockerList_count++){
					for(String tmp: fogServer_Ip_List){
//						fogServerIp = fogServer_Ip_List.get(fogServerDockerList_count);
						fogServerIp = tmp;
						fogServerMac = ipMacTable.get(fogServerIp);
//						LOG.debug("fogServerDockerListValue {} | ipMacTableValue {}", fogServerDockerListValue, ipMacTableValue);
						if(!(fogServerMac == null)){
							// extract NodeId, OutputPort
//							fogNodeConnectorId = macTable.get(fogServerMac);
							fogNodeConnectorId = macTable[switchNodeId_number].get(fogServerMac);
							if(!(fogNodeConnectorId == null)){
								fogNodeId = SMS_InventoryUtils.getSwitchNodeId(fogNodeConnectorId);
								fogOutputPort = SMS_InventoryUtils.getOutputPort(fogNodeConnectorId);
								if(switchNodeId.equals(fogNodeId)) {
	//								LOG.debug("fogNodeId {} | fogOutputPort {}", fogNodeId, fogOutputPort);
									DOCKER_SWITCH_IP_ON = 1;
									break;
								}
							}
						}
//						LOG.debug("fogServerDockerList({}) {}", fogServerDockerList_count, fogServerDockerList.get(fogServerDockerList_count));
					}
//					LOG.debug("2222222222>");
				}

// FOG_SERVER_OVS_ID_CHECK과 DOCKER_SWITCH_CHECK 둘다 1일 경우, 임시로 수동으로 openflow:1과 10.0.0.10을 매핑.
// 나중에는 자동으로 인지하고 매핑하도록 해야함.
// SMS NiaaS: set up "FLOW_RULE" to ovs
				
				FOG_SERVER = "";
//				if(stringEtherTypeHex.equals("0800") && ttl.equals("40")){
				if(stringEtherTypeHex.equals("0800") || stringEtherTypeHex.equals("0806")){ // ipv4와 ARP
//					LOG.debug("=====================================================================================");
//					LOG.debug("FOG_SERVER_OVS_ID_CHECK( {})  |  DOCKER_SWITCH_IP_ON( {})", FOG_SERVER_OVS_ID_ON, DOCKER_SWITCH_IP_ON);
//					LOG.debug("switchNodeId( {})  |  switchInputPort( {})  |  ovsId( {})  |  fogServerIp( {})", switchNodeId, switchInputPort, ovsId, fogServerIp);
//					LOG.debug("-------------------------------------------------------------------------------------");
//					if(FOG_SERVER_OVS_ID_ON == 1 && DOCKER_SWITCH_IP_ON == 1){
//						FOG_SERVER = fogServerIp; // V
//						TutorialL2Forwarding_ProgramL2Flow.programL2Flow_pathChange_O(payload, ingressNodeId, 
//								srcIp, dstIp, srcMac, dstMac, 
//								ingressNodeConnectorId, egressNodeConnectorId,
////								ipMacTable, macTable,
//								ipMacTable, macTable[switchNodeId_number],
//								dataBroker,
//								CLOUD_SERVER, FOG_SERVER, fogNodeId, fogOutputPort);
//					}
//					// 테스트 위해 주석처리
//					else {
						TutorialL2Forwarding_ProgramL2Flow.programL2Flow_pathChange_X(payload, ingressNodeId, 
								srcIp, dstIp, srcMac, dstMac, 
								ingressNodeConnectorId, egressNodeConnectorId,
								ipMacTable, macTable[switchNodeId_number],
								dataBroker);
//					}
				}
// SMS NiaaS End
		
				NodeConnectorRef egressNodeConnectorRef = InventoryUtils.getNodeConnectorRef(egressNodeConnectorId);
				NodeRef egressNodeRef = InventoryUtils.getNodeRef(egressNodeConnectorRef);
				
				// TODO: 2.3.1.2 perform PACKET_OUT of this packet to target node connector
				packetOut(egressNodeRef, egressNodeConnectorRef, payload);
			}else{
            	// 2.3.2 Flood packet
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



