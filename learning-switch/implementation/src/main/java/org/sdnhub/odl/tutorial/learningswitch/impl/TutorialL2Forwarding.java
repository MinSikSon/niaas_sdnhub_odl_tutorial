/*
 * Copyright (C) 2015 SDN Hub

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

package org.sdnhub.odl.tutorial.learningswitch.impl;


import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.OutputActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowCapableNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.Table;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.TableKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.InstructionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.ApplyActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeRef;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingListener;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketProcessingService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.PacketReceived;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.packet.service.rev130709.TransmitPacketInputBuilder;
import org.opendaylight.yangtools.concepts.Registration;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.sdnhub.odl.tutorial.utils.GenericTransactionUtils;
import org.sdnhub.odl.tutorial.utils.PacketParsingUtils;
import org.sdnhub.odl.tutorial.utils.inventory.InventoryUtils;
import org.sdnhub.odl.tutorial.utils.openflow13.MatchUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class TutorialL2Forwarding  implements AutoCloseable, PacketProcessingListener {
	private final Logger LOG = LoggerFactory.getLogger(this.getClass());
	private int LOG_TITLE = 1; // SMS
	private int LOG_TEST = 1; // SMS
	private int LOG_MULTI = 1; // SMS
	private final static long FLOOD_PORT_NUMBER = 0xfffffffbL;
	
	//Members specific to this class
	private Map<String, NodeConnectorId> macTable = new HashMap<String, NodeConnectorId>();
	
	//SMS: ip-mac matching table
	private Map<String, String> IpMacTable = new HashMap<String, String>();
	
//	private String function = "hub";
	private String function = "learningSwitch";
        
	//Members related to MD-SAL operations
	private List<Registration> registrations;
	private DataBroker dataBroker;
	private PacketProcessingService packetProcessingService;
	
	private static String srcMac;
	private static String dstMac;
	
	// Manual input, cloud&fog ip
	private static String cloudIp = "10.0.0.20";
	private static String fogIp = "10.0.0.10";
	
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
		if(LOG_TITLE == 1) LOG.debug("");
		if(LOG_TITLE == 1) LOG.debug("==============onPacketReceived();==============");
		if(LOG_TEST == 1) LOG.debug("[notification.getPayload()] {}", notification.getPayload());
		if(LOG_MULTI == 1) LOG.debug("[LOG_MULTI]ingressNodeConnectorRef {}", ingressNodeConnectorRef);
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
		if(LOG_TITLE == 1) LOG.debug("(0) Ignore LLDP packets, etherType({})",etherType);
		if (etherType == 0x88cc) {
			return;
		}
		
		// Hub implementation
		if(LOG_TITLE == 1) LOG.debug("If behaving as (1)\"hub\", perform a PACKET_OUT with FLOOD action");
		if(LOG_TITLE == 1) LOG.debug("Else if behaving as (2)\"learning switch\"");
		if (function.equals("hub")) {
        	
			//flood packet (1)
			if(LOG_TITLE == 1) LOG.debug("(1) flood packet");
			packetOut(ingressNodeRef, floodNodeConnectorRef, notification.getPayload());
		} else {
        	//TODO: Extract payload
			byte[] payload = notification.getPayload();
			
        	//TODO: Extract MAC address (2.1)
			if(LOG_TITLE == 1) LOG.debug("(2.1) Extract MAC addresses");
			byte[] dstMacRaw = PacketParsingUtils.extractDstMac(payload);
			byte[] srcMacRaw = PacketParsingUtils.extractSrcMac(payload);
			srcMac = PacketParsingUtils.rawMacToString(srcMacRaw);
			dstMac = PacketParsingUtils.rawMacToString(dstMacRaw);
// >>>>>>>>>>>>>>>>>>> change the location.
// LOCATION 1		
			
			//TODO: Learn source MAC address (2.2 - 1)
			if(LOG_TITLE == 1) LOG.debug("(2.2) Learn source MAC address");
			this.macTable.put(srcMac, ingressNodeConnectorId); // SMS : Map<String, NodeConnectorId> macTable
			if(LOG_TEST == 1) LOG.debug("macTable++++++++++++++++++++++++++++++++++++++++++++++++++++");
			//SMS: display macTable
			Set<Entry<String, NodeConnectorId>> set = macTable.entrySet();
			Iterator<Entry<String,NodeConnectorId>> it = set.iterator();
			while(it.hasNext()) {
				Map.Entry<String, NodeConnectorId> e = (Map.Entry<String, NodeConnectorId>)it.next();
				if(LOG_TEST == 1) LOG.debug("  | key: {} | value: {} |", e.getKey(), e.getValue());
			}
			if(LOG_TEST == 1) LOG.debug("macTable----------------------------------------------------");
// >>>>>>>>>>>>>>>>>>> change the location.
// LOCATION 2	
/**********************************************************************			
 * SMS: Modify dst information +++++++++++++++++++++++++++++++
 **********************************************************************/
			if(LOG_TITLE == 1) LOG.debug("+++++++++++++++++++++++++++++++* Modify dst information +++++++++++++++++++++++++++++++");
			byte[] tmpSrcIp = new byte[4];
			byte[] tmpDstIp = new byte[4];
			String etherType_hex = SMS_Parser_Test.get_etherType(payload, etherTypeRaw);
			SMS_Display.display_etherType(etherType_hex);
			// SMS: display Address
			if(LOG_TEST == 1) LOG.debug("[before] ...");
			if(LOG_TEST == 1) LOG.debug("  | srcMac: {}", srcMac);
			if(LOG_TEST == 1) LOG.debug("  | dstMac: {}", dstMac);
			// SMS: get IpAddr (byte[])
			tmpSrcIp = SMS_Display.display_srcIp(payload, etherType_hex); // & display Addr 
			tmpDstIp = SMS_Display.display_dstIp(payload, etherType_hex); // & display Addr
			// SMS: get IpAddr (string)			
			String tmpSrcIp_string = SMS_Parser_Test.ip_byteArray_to_decimalString(tmpSrcIp);
			String tmpDstIp_string = SMS_Parser_Test.ip_byteArray_to_decimalString(tmpDstIp);
			
			
////////////////////////////// test ++
//			if(tmpDstIp_string.equals("224.0.0.251")){
//				
//			}
////////////////////////////// test --
			
			
			
			
			if(LOG_TEST == 1) LOG.debug("IpMacTable++++++++++++++++++++++++++++++++++++++");
			//SMS: Learn srcIp+srcMac address
			this.IpMacTable.put(tmpSrcIp_string, srcMac);
			//SMS: display IpMacTable
			Set<Entry<String, String>> set2 = IpMacTable.entrySet();
			Iterator<Entry<String,String>> it2 = set2.iterator();
			while(it2.hasNext()) {
				Map.Entry<String, String> e2 = (Map.Entry<String, String>)it2.next();
				if(LOG_TEST == 1) LOG.debug("  | key: {} | value: {}  |", e2.getKey(), e2.getValue());
			}
			if(LOG_TEST == 1) LOG.debug("IpMacTable--------------------------------------");
			
// VV set cloud ip & fog ip
			// SMS: change IpAddr, MacAddr in payload
			String tmpSrcMac_string_cloud = this.IpMacTable.get(cloudIp); // 10.0.0.3
			String tmpDstMac_string_fog = this.IpMacTable.get(fogIp); // 10.0.0.10
			if(LOG_TEST == 1) LOG.debug("[tmpSrcMac_string_cloud] {}", tmpSrcMac_string_cloud);
			if(LOG_TEST == 1) LOG.debug("[tmpDstMac_string_fog] {}", tmpDstMac_string_fog);
			SMS_Change_Path.change_cloud_to_fog(payload, 
					tmpSrcIp_string, tmpDstIp_string, cloudIp, fogIp,
					tmpSrcMac_string_cloud, tmpDstMac_string_fog, 
					etherType_hex);
			// SMS: change srcMac, dstMac;
			dstMacRaw = PacketParsingUtils.extractDstMac(payload);
			srcMacRaw = PacketParsingUtils.extractSrcMac(payload);
			srcMac = PacketParsingUtils.rawMacToString(srcMacRaw);
			dstMac = PacketParsingUtils.rawMacToString(dstMacRaw);
			// SMS: display changed Address
			if(LOG_TEST == 1) LOG.debug("[after ] ...");
			if(LOG_TEST == 1) LOG.debug("  | srcMac: {}", srcMac);
			if(LOG_TEST == 1) LOG.debug("  | dstMac: {}", dstMac);
			tmpSrcIp = SMS_Display.display_srcIp(payload, etherType_hex); // & display Addr
			tmpDstIp = SMS_Display.display_dstIp(payload, etherType_hex); // & display Addr
			if(LOG_TITLE == 1) LOG.debug("-------------------------------* Modify dst information -------------------------------");
/**********************************************************************			
 * SMS: Modify dst information -------------------------------
 **********************************************************************/

//			//SMS: Learn source MAC address (2.2 - 2)
//			if(LOG_TITLE == 1) LOG.debug("[SMS+TITLE] (2.2) Learn source MAC address");
//			this.macTable.put(srcMac, ingressNodeConnectorId); // SMS : Map<String, NodeConnectorId> macTable
			
			//TODO: Lookup destination MAC address in table (2.3)
			if(LOG_TITLE == 1) LOG.debug("(2.3) Lookup in MAC table for the target node connector of dst_mac");
//			NodeConnectorId egressNodeConnectorId = null;
			NodeConnectorId egressNodeConnectorId = macTable.get(dstMac); // SMS
			
			//TODO: If found (2.3.1)
			if(LOG_TITLE == 1) LOG.debug("(2.3.1) If found");
			if(egressNodeConnectorId != null){
				//TODO: 2.3.1.1 perform FLOW_MOD for that dst_mac through the target node connector
				if(LOG_TITLE == 1) LOG.debug("(2.3.1.1) perform FLOW_MOD for that dst_mac through the target node connector");
//				if(LOG_ON == 1) LOG.debug("[SMS] ingressNodeConnectorRef {}",ingressNodeConnectorRef);
//				if(LOG_ON == 1) LOG.debug("[SMS] egressNodeConnectorRef {}",egressNodeConnectorRef);
				
//				programL2Flow(ingressNodeId, dstMac, ingressNodeConnectorId, egressNodeConnectorId);
				
				NodeConnectorRef egressNodeConnectorRef = InventoryUtils.getNodeConnectorRef(egressNodeConnectorId);
//				NodeId egressNodeId = InventoryUtils.getNodeId(egressNodeConnectorRef);
				NodeRef egressNodeRef = InventoryUtils.getNodeRef(egressNodeConnectorRef);
				
				//TODO: 2.3.1.2 perform PACKET_OUT of this packet to target node connector
				if(LOG_TITLE == 1) LOG.debug("(2.3.1.2) perform PACKET_OUT of this packet to target node connector");
				packetOut(egressNodeRef, egressNodeConnectorRef, payload);
			}else{
            	//2.3.2 Flood packet
				if(LOG_TITLE == 1) LOG.debug("(2.3.2) Flood packet");
//				packetOut(ingressNodeRef, floodNodeConnectorRef, payload);
				packetOut(ingressNodeRef, floodNodeConnectorRef, payload);
			}
		}
	}

	private void packetOut(NodeRef egressNodeRef, NodeConnectorRef egressNodeConnectorRef, byte[] payload) {
		Preconditions.checkNotNull(packetProcessingService);
//		LOG.debug("Flooding packet of size {} out of port {}", payload.length, egressNodeConnectorRef);
		if(LOG_MULTI == 1) LOG.debug("[LOG_MULTI]egressNodeRef {}", egressNodeRef);
		//Construct input for RPC call to packet processing service
		TransmitPacketInput input = new TransmitPacketInputBuilder()
				.setPayload(payload)
				.setNode(egressNodeRef)
				.setEgress(egressNodeConnectorRef)
				.build();
//		LOG.debug("[input  ] {}", input);
		packetProcessingService.transmitPacket(input);
//		LOG.debug("[packetProcessingService] {}",packetProcessingService);
	}    
	
	
	
	private void programL2Flow(NodeId nodeId, String dstMac, NodeConnectorId ingressNodeConnectorId, NodeConnectorId egressNodeConnectorId) {

    	/* Programming a flow involves:
    	 * 1. Creating a Flow object that has a match and a list of instructions,
    	 * 2. Adding Flow object as an augmentation to the Node object in the inventory. 
    	 * 3. FlowProgrammer module of OpenFlowPlugin will pick up this data change and eventually program the switch.
    	 */
		if(LOG_TITLE == 1) LOG.debug("==============programL2Flow();==============");
		// Creating match object
		MatchBuilder matchBuilder = new MatchBuilder();
		MatchUtils.createEthDstMatch(matchBuilder, new MacAddress(dstMac), null);
		MatchUtils.createInPortMatch(matchBuilder, ingressNodeConnectorId);
		
		// Instructions List Stores Individual Instructions
		InstructionBuilder ib = new InstructionBuilder();
		List<Instruction> instructions = Lists.newArrayList();
		InstructionsBuilder isb = new InstructionsBuilder();
		
		ActionBuilder ab = new ActionBuilder();
		List<Action> actionList = Lists.newArrayList();
		ApplyActionsBuilder aab = new ApplyActionsBuilder();

		if(LOG_TITLE == 1) LOG.debug("Set output action++++++++++++++++++++++++++++++++++++++++++");
		// Set output action
		OutputActionBuilder output = new OutputActionBuilder();
		output.setOutputNodeConnector(egressNodeConnectorId);
		output.setMaxLength(65535); // Send full packet and No buffer
//		DropActionCase dropAction = new DropActionCaseBuilder().build();
		ab.setAction(new OutputActionCaseBuilder().setOutputAction(output.build()).build());
//		ab.setAction(dropAction);
		ab.setOrder(0);
		ab.setKey(new ActionKey(0));
		actionList.add(ab.build());
		
		// SMS: test+++++++++++++++++++++++++++++++++++++++++++
		ActionBuilder ab2 = new ActionBuilder();
		OutputActionBuilder output2 = new OutputActionBuilder();
		output2.setOutputNodeConnector(ingressNodeConnectorId);
		output2.setMaxLength(65535); // Send full packet and No buffer
//		DropActionCase dropAction = new DropActionCaseBuilder().build();
		ab2.setAction(new OutputActionCaseBuilder().setOutputAction(output2.build()).build());
//		ab2.setAction(dropAction);
		ab2.setOrder(1);
		ab2.setKey(new ActionKey(1));
		actionList.add(ab2.build());
		// SMS: test-------------------------------------------
		
		if(LOG_TITLE == 1) LOG.debug("actionList+++++++++++++++++++++++++++++++++++++++++++++++++++++");
		for(int i = 0 ; i < actionList.size() ; i++){
			if(LOG_TEST == 1) LOG.debug("  | index: {} | value: {} |", i, actionList.get(i));
		}
		if(LOG_TITLE == 1) LOG.debug("actionList-----------------------------------------------------");
		if(LOG_TITLE == 1) LOG.debug("Set output action------------------------------------------");
		// Create Apply Actions Instruction
		aab.setAction(actionList);
		ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
		ib.setOrder(0);
		ib.setKey(new InstructionKey(0));
		instructions.add(ib.build());

		if(LOG_TITLE == 1) LOG.debug("++++++++++Create Flow++++++++++");
		// Create Flow
		FlowBuilder flowBuilder = new FlowBuilder();
		flowBuilder.setMatch(matchBuilder.build());

		String flowId = "L2_Rule_" + dstMac;
		flowBuilder.setId(new FlowId(flowId));
		FlowKey key = new FlowKey(new FlowId(flowId));
		flowBuilder.setBarrier(true);
		flowBuilder.setTableId((short) 0);
		flowBuilder.setKey(key);
//		flowBuilder.setPriority(32768);
		flowBuilder.setPriority(500);
		flowBuilder.setFlowName(flowId);
		flowBuilder.setHardTimeout(0);
		flowBuilder.setIdleTimeout(0);
		flowBuilder.setInstructions(isb.setInstruction(instructions).build());
        
		LOG.debug("flowBuilder++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		LOG.debug("flowBuilder.getInstructions(): {}", flowBuilder.getInstructions());
		LOG.debug("flowBuilder.getInstructions().getInstruction(): {}", flowBuilder.getInstructions());
		LOG.debug("flowBuilder------------------------------------------------------");
        
		InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
				.child(Node.class, new NodeKey(nodeId))
				.augmentation(FlowCapableNode.class)
				.child(Table.class, new TableKey(flowBuilder.getTableId()))
				.child(Flow.class, flowBuilder.getKey())
				.build();
		GenericTransactionUtils.writeData(dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
		if(LOG_TITLE == 1) LOG.debug("----------Create Flow----------");
    }
}


