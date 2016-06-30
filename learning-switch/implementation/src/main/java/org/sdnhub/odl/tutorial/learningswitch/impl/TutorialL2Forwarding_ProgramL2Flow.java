package org.sdnhub.odl.tutorial.learningswitch.impl;

import java.math.BigInteger;
import java.util.Map;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowCapableNode;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.FlowId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.Table;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.TableKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.Flow;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.FlowCookie;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.InstructionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.sdnhub.odl.tutorial.utils.GenericTransactionUtils;
import org.sdnhub.odl.tutorial.utils.openflow13.MatchUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TutorialL2Forwarding_ProgramL2Flow {
	private static int cookie = 0;

	/**
	* [CLOUD_SERVER -> FOG_SERVER] 로의 경로 변경 flow_rule을 ovs에 내리기 위해 사용.
	* 
	* @parameter: NodeId nodeId
	* @parameter: DataBroker dataBroker
	* @parameter: String fogServerIp
	* @parameter: String fogServerMac
	* @parameter: String fogSwitchOutputPort
 	* @parameter: String cloudServerIp
 	* @parameter: String cloudServerMac
	* @return: void
	*/
	public static void programL2Flow_pathChange_CloudToFog(NodeId nodeId, DataBroker dataBroker, 
			String fogServerIp, String fogServerMac, String fogSwitchOutputPort,
			String cloudServerIp, String cloudServerMac,
			String srcMac)
	{
		final Logger LOG = LoggerFactory.getLogger(TutorialL2Forwarding_ProgramL2Flow.class);
		LOG.debug("programL2Flow_pathChange_CloudToFog() ++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		// Creating match object
		MatchBuilder matchBuilder = new MatchBuilder();		
		SMS_MatchUtils.createEthDstIpv4Match(matchBuilder, new MacAddress(cloudServerMac), null);
//		SMS_MatchUtils.createEthDstEthSrcIpv4Match(matchBuilder, new MacAddress(srcMac), new MacAddress(cloudServerMac), null);
		// Create Flow
		FlowBuilder flowBuilder = new FlowBuilder();
		flowBuilder.setMatch(matchBuilder.build());
		
		String flowId = "L2_Rule_CloudToFog";
		flowBuilder.setId(new FlowId(flowId));
		FlowKey key = new FlowKey(new FlowId(flowId));
		flowBuilder.setBarrier(true);
		flowBuilder.setTableId((short) 0);
		flowBuilder.setKey(key);
		flowBuilder.setPriority(30);
//		flowBuilder.setPriority(130);
		flowBuilder.setFlowName(flowId);
		flowBuilder.setIdleTimeout(1800);
		flowBuilder.setHardTimeout(3600);
		flowBuilder.setCookie(new FlowCookie(new BigInteger(Integer.toString(0x11111111))));
		MacAddress mac = new MacAddress(fogServerMac);
		Ipv4Prefix ip = new Ipv4Prefix(fogServerIp + "/0");
//		flowBuilder.setInstructions(SMS_FlowMod.create_DlDst_NwDst_outputPort_controller_Instructions(mac, ip, fogSwitchOutputPort).build());
		flowBuilder.setInstructions(SMS_FlowMod.create_DlDst_NwDst_outputPort_Instructions(mac, ip, fogSwitchOutputPort).build());
		
		InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
				.child(Node.class, new NodeKey(nodeId))
				.augmentation(FlowCapableNode.class)
				.child(Table.class, new TableKey(flowBuilder.getTableId()))
				.child(Flow.class, flowBuilder.getKey())
				.build();
		GenericTransactionUtils.writeData(dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
	}
	/**
	* [CLOUD_SERVER -> FOG_SERVER] 로의 경로 변경 flow_rule을 ovs에 내리기 위해 사용.
	* 
	* @parameter: NodeId nodeId
	* @parameter: DataBroker dataBroker
	* @parameter: String fogServerIp
	* @parameter: String fogServerMac
	* @parameter: String fogSwitchOutputPort
 	* @parameter: String cloudServerIp
 	* @parameter: String cloudServerMac
	* @return: void
	*/
	public static void programL2Flow_pathChange_FogToCloud(NodeId nodeId, DataBroker dataBroker, 
			String fogServerIp, String fogServerMac, String fogSwitchOutputPort,
			String cloudServerIp, String cloudServerMac, String modOutputPort,
			String srcMac)
	{
		final Logger LOG = LoggerFactory.getLogger(TutorialL2Forwarding_ProgramL2Flow.class);
		LOG.debug("programL2Flow_pathChange_FogToCloud() ++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		// Creating match object
		MatchBuilder matchBuilder = new MatchBuilder();
		SMS_MatchUtils.createEthSrcIpv4Match(matchBuilder, new MacAddress(fogServerMac));
//		SMS_MatchUtils.createEthDstEthSrcIpv4Match(matchBuilder, new MacAddress(fogServerMac), new MacAddress(srcMac), null);
		
		// Create Flow
		FlowBuilder flowBuilder = new FlowBuilder();
		flowBuilder.setMatch(matchBuilder.build());
		
		String flowId = "L2_Rule_FogToCloud";
		flowBuilder.setId(new FlowId(flowId));
		FlowKey key = new FlowKey(new FlowId(flowId));
		flowBuilder.setBarrier(true);
		flowBuilder.setTableId((short) 0);
		flowBuilder.setKey(key);
		flowBuilder.setPriority(30);
//		flowBuilder.setPriority(130);
		flowBuilder.setFlowName(flowId);
		flowBuilder.setIdleTimeout(1800);
		flowBuilder.setHardTimeout(3600);
		flowBuilder.setCookie(new FlowCookie(new BigInteger(Integer.toString(0x22222222))));
		MacAddress mac = new MacAddress(cloudServerMac);
		Ipv4Prefix ip = new Ipv4Prefix(cloudServerIp + "/0");
//		flowBuilder.setInstructions(SMS_FlowMod.create_DlSrc_NwSrc_Controller_Instructions(mac, ip).build());
//		flowBuilder.setInstructions(SMS_FlowMod.create_DlSrc_NwSrc_outputPort_Instructions(mac, ip, modOutputPort).build());
		flowBuilder.setInstructions(SMS_FlowMod.createControllerInstructions().build());
		InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
				.child(Node.class, new NodeKey(nodeId))
				.augmentation(FlowCapableNode.class)
				.child(Table.class, new TableKey(flowBuilder.getTableId()))
				.child(Flow.class, flowBuilder.getKey())
				.build();
		GenericTransactionUtils.writeData(dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
	}
	
	public static void programL2Flow_pathChange_FogToCloud_2(NodeId nodeId, DataBroker dataBroker, 
			String fogServerIp, String fogServerMac, String fogSwitchOutputPort, NodeConnectorId fogNodeConnectorId,
			String cloudServerIp, String cloudServerMac, String modOutputPort,
			String dstMac)
	{
		final Logger LOG = LoggerFactory.getLogger(TutorialL2Forwarding_ProgramL2Flow.class);
		LOG.debug("programL2Flow_pathChange_FogToCloud() ++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		// Creating match object
		MatchBuilder matchBuilder = new MatchBuilder();
//		SMS_MatchUtils.createEthSrcIpv4Match(matchBuilder, new MacAddress(fogServerMac));
//		SMS_MatchUtils.createEthDstEthSrcIpv4Match(matchBuilder, new MacAddress(fogServerMac), new MacAddress(dstMac), null);
		SMS_MatchUtils.createEthDstEthSrcIpv4InportMatch(matchBuilder, new MacAddress(fogServerMac), new MacAddress(dstMac), null, fogNodeConnectorId);
		
		// Create Flow
		FlowBuilder flowBuilder = new FlowBuilder();
		flowBuilder.setMatch(matchBuilder.build());
		
		String flowId = "L2_Rule_"+modOutputPort;
		flowBuilder.setId(new FlowId(flowId));
		FlowKey key = new FlowKey(new FlowId(flowId));
		flowBuilder.setBarrier(true);
		flowBuilder.setTableId((short) 0);
		flowBuilder.setKey(key);
		flowBuilder.setPriority(35);
//		flowBuilder.setPriority(130);
		flowBuilder.setFlowName(flowId);
		flowBuilder.setIdleTimeout(1800);
		flowBuilder.setHardTimeout(3600);
		cookie++;
		flowBuilder.setCookie(new FlowCookie(new BigInteger(Integer.toString(0x22222222+cookie))));
		MacAddress mac = new MacAddress(cloudServerMac);
		Ipv4Prefix ip = new Ipv4Prefix(cloudServerIp + "/0");
//		flowBuilder.setInstructions(SMS_FlowMod.create_DlSrc_NwSrc_Instructions(mac, ip).build());
		flowBuilder.setInstructions(SMS_FlowMod.create_DlSrc_NwSrc_outputPort_Instructions(mac, ip, modOutputPort).build());
		InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
				.child(Node.class, new NodeKey(nodeId))
				.augmentation(FlowCapableNode.class)
				.child(Table.class, new TableKey(flowBuilder.getTableId()))
				.child(Flow.class, flowBuilder.getKey())
				.build();
		GenericTransactionUtils.writeData(dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
	}
	
	public static void programL2Flow_initSwitch_1(byte[] payload, NodeId nodeId, DataBroker dataBroker){
		final Logger LOG = LoggerFactory.getLogger(TutorialL2Forwarding_ProgramL2Flow.class);
		int LOG_TITLE = 0;
		int LOG_programL2Flow = 0;
		
		LOG.debug("programL2Flow_initSwitch_1() ++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		// Creating match object
		MatchBuilder matchBuilder = new MatchBuilder();														// (1)
		
		// Create Flow
		FlowBuilder flowBuilder = new FlowBuilder();															// (9)
		flowBuilder.setMatch(matchBuilder.build());															// (9) - 1 && (1) - 3
		
		String flowId = "L2_Rule_Drop";																			// (10)
		flowBuilder.setId(new FlowId(flowId));																	// (9) - 2
		FlowKey key = new FlowKey(new FlowId(flowId));														// (11)
		flowBuilder.setBarrier(true);																			// (9) - 3
		flowBuilder.setTableId((short) 0);																		// (9) - 4
		flowBuilder.setKey(key);																					// (9) - 5
		flowBuilder.setPriority(0);																			// (9) - 6
		flowBuilder.setFlowName(flowId);																		// (9) - 7
		flowBuilder.setHardTimeout(0);																			// (9) - 8
		flowBuilder.setIdleTimeout(0);																			// (9) - 9
		flowBuilder.setCookie(new FlowCookie(new BigInteger(Integer.toString(0x33333331))));
		
		flowBuilder.setInstructions(SMS_FlowRule.createInsturctions_Drop().build());
		
		InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
				.child(Node.class, new NodeKey(nodeId))
				.augmentation(FlowCapableNode.class)
				.child(Table.class, new TableKey(flowBuilder.getTableId()))
				.child(Flow.class, flowBuilder.getKey())
				.build();
		GenericTransactionUtils.writeData(dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
	}
	public static void programL2Flow_initSwitch_2(byte[] payload, NodeId nodeId, DataBroker dataBroker){
		final Logger LOG = LoggerFactory.getLogger(TutorialL2Forwarding_ProgramL2Flow.class);
		int LOG_TITLE = 0;
		int LOG_programL2Flow = 0;
		
		LOG.debug("programL2Flow_initSwitch_2() ++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		// Creating match object
		MatchBuilder matchBuilder = new MatchBuilder();														// (1)
		SMS_MatchUtils.createLLDPMatch(matchBuilder);		
		
		// Create Flow
		FlowBuilder flowBuilder = new FlowBuilder();															// (9)
		flowBuilder.setMatch(matchBuilder.build());															// (9) - 1 && (1) - 3
		
		String flowId = "L2_Rule_0x88cc";																			// (10)
		flowBuilder.setId(new FlowId(flowId));																	// (9) - 2
		FlowKey key = new FlowKey(new FlowId(flowId));														// (11)
		flowBuilder.setBarrier(true);																			// (9) - 3
		flowBuilder.setTableId((short) 0);																		// (9) - 4
		flowBuilder.setKey(key);																					// (9) - 5
		flowBuilder.setPriority(100);																			// (9) - 6
		flowBuilder.setFlowName(flowId);																		// (9) - 7
		flowBuilder.setHardTimeout(0);																			// (9) - 8
		flowBuilder.setIdleTimeout(0);																			// (9) - 9
		flowBuilder.setCookie(new FlowCookie(new BigInteger(Integer.toString(0x33333332))));
		
		flowBuilder.setInstructions(SMS_FlowRule.createSentToControllerInsturctions().build());
		
		InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
				.child(Node.class, new NodeKey(nodeId))
				.augmentation(FlowCapableNode.class)
				.child(Table.class, new TableKey(flowBuilder.getTableId()))
				.child(Flow.class, flowBuilder.getKey())
				.build();
		GenericTransactionUtils.writeData(dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
	}
	
	public static void programL2Flow_pathChange_O(byte[] payload, NodeId nodeId, 
			String srcIp, String dstIp, String srcMac, String dstMac, 
			NodeConnectorId ingressNodeConnectorId, NodeConnectorId egressNodeConnectorId, 
			Map<String, String> ipMacTable, Map<String, NodeConnectorId> macTable,
			DataBroker dataBroker,
			String CLOUD_SERVER, String FOG_SERVER, String fogNodeId, String fogOutputPort) 
	{
		// SMS: debug log
		final Logger LOG = LoggerFactory.getLogger(TutorialL2Forwarding_ProgramL2Flow.class);
		
		String stringEtherTypeHex = SMS_Parser_MacAddr.get_stringEtherTypeHex(payload);
	
		LOG.debug("programL2Flow_pathChange_O() ++++++++++++++++++++++++++++++++++++++++++++++++++++++");
/* [2] IF) PACKET 들어온 해당 ovs가 FOG_SERVER가 동작할 위치인지 확인. 즉, ovsId == runningFogOvsId 인지 확인. */
		String switchNodeId = SMS_InventoryUtils.getSwitchNodeId(ingressNodeConnectorId);
		String switchOutputPort = SMS_InventoryUtils.getOutputPort(ingressNodeConnectorId);
		LOG.debug("switchNodeId = {}  |  switchOutputPort = {}", switchNodeId, switchOutputPort);

//    	/* Programming a flow involves:
//    	 * 1. Creating a Flow object that has a match and a list of instructions,
//    	 * 2. Adding Flow object as an augmentation to the Node object in the inventory. 
//    	 * 3. FlowProgrammer module of OpenFlowPlugin will pick up this data change and eventually program the switch.
//    	 */
//		
//		// Creating match object
//		MatchBuilder matchBuilder = new MatchBuilder();
//		SMS_MatchUtils.createEthDstEthSrcInPortMatch(matchBuilder, new MacAddress(srcMac), new MacAddress(dstMac), ingressNodeConnectorId);
//		
//		// Create Flow
//		FlowBuilder flowBuilder = new FlowBuilder();
//		flowBuilder.setMatch(matchBuilder.build());
//		
//		String flowId = "L2_Rule_" + dstMac + srcMac;
//		flowBuilder.setId(new FlowId(flowId));
//		FlowKey key = new FlowKey(new FlowId(flowId));
//		flowBuilder.setBarrier(true);
//		flowBuilder.setTableId((short) 0);
//		flowBuilder.setKey(key);
//		flowBuilder.setFlowName(flowId);
//		
///* [4-1]   IF) PACKET's dstIp == CLOUD_SERVER's address [10.0.0.100] 인지 확인. */
///* [5-1]     PACKET의 dstIp (CLOUD_SERVER [10.0.0.100]) 및 dstMac을 FOG_SERVER's address [10.0.0.10]으로 바꿔주는 */ 
///*           FLOW_RULE을 해당 ovsId를 갖는 ovs에 내려준다. outputPort도 바꿔준다.*/
///*         FI) */
///* [4-2]   IF) PACKET's srcIp == FOG_SERVER's address [10.0.0.10] 인지 확인. */
///* [5-2]     PACKET의 srcIp (FOG_SERVER [10.0.0.10]) 및 srcMac을 CLOUD_SERVER's address [10.0.0.100]으로 바꿔주는 */
///*           FLOW_RULE을 해당 ovsId를 갖는 ovs에 내려준다. (outputPort는 바꾸지 않아도 될 것 같다.) */
///*         FI) */
//// SMS NIaaS: Test
//		// variable
//		String stringDstMac, stringSrcMac;
//		MacAddress mac;
//		Ipv4Prefix ip;
//		NodeConnectorId nodeConnectorId;
//		NodeConnectorId nodeConnectorId2;
//		String modSwitchNodeId;
//		String modOutputPort;
////		if(switchNodeId.equals(fogNodeId)){
//			if(srcIp.equals(CLOUD_SERVER) && dstIp.equals(FOG_SERVER)){ // SMS: CLOUD_SERVER -> FOG_SERVER로 향하는 패킷 drop
//				LOG.debug("/***********");
//				LOG.debug("[CASE 3] CLOUD -> FOG 인 패킷 drop");
//				flowBuilder.setHardTimeout(0);
//				flowBuilder.setIdleTimeout(0);
//				flowBuilder.setPriority(500);
//				flowBuilder.setCookie(new FlowCookie(new BigInteger(Integer.toString(0x33333333))));
//				InstructionsBuilder isb = SMS_FlowMod.createDropInstructions();
//				flowBuilder.setInstructions(isb.build());
//				LOG.debug("***********/");
//			}else if(dstIp.equals(CLOUD_SERVER) && !(srcIp.equals(FOG_SERVER))){ // SMS: PACKET의 dstIp 확인
//				LOG.debug("/***********");
//				LOG.debug("[CASE 1] Client -> CLOUD 를 Client -> FOG 로");
//				flowBuilder.setHardTimeout(0);
//				flowBuilder.setIdleTimeout(0);
//				flowBuilder.setPriority(500);
//				cookie++;
//				flowBuilder.setCookie(new FlowCookie(new BigInteger(Integer.toString(cookie))));
//				
//				stringDstMac = ipMacTable.get(FOG_SERVER);
//				if(stringDstMac == null) return;
//				mac = new MacAddress(stringDstMac); // SMS: ok
//				ip = new Ipv4Prefix(FOG_SERVER + "/0");
//				// extract output port
//				nodeConnectorId = macTable.get(stringDstMac);
//				modSwitchNodeId = SMS_InventoryUtils.getSwitchNodeId(nodeConnectorId);
//				modOutputPort = SMS_InventoryUtils.getOutputPort(nodeConnectorId);
//				LOG.debug("[CASE 1 - status] {} | {} | {} | {} | {}", mac, ip, nodeConnectorId, modSwitchNodeId, modOutputPort);
//				InstructionsBuilder isb = SMS_FlowMod.create_DlDst_NwDst_outputPort_Instructions(mac, ip, modOutputPort);
//				flowBuilder.setInstructions(isb.build());
//	//					flowBuilder.setInstructions(SMS_FlowMod.create_DlDst_NwDst_Instructions(mac, ip).build());
//	//					if(LOG_programL2Flow == 1) LOG.debug("[SMS| TEST 1] sw {}, output {}", modSwitchNodeId, modOutputPort);
//				LOG.debug("***********/");
//			}else if(srcIp.equals(FOG_SERVER) && !(dstIp.equals(CLOUD_SERVER))){ // SMS: PACKET의 srcIp 확인
//				LOG.debug("/***********");
//				LOG.debug("[CASE 2] FOG -> Client 를 CLOUD -> Client 로");
//				flowBuilder.setHardTimeout(0);
//				flowBuilder.setIdleTimeout(0);
//				flowBuilder.setPriority(500);
//				cookie++;
//				flowBuilder.setCookie(new FlowCookie(new BigInteger(Integer.toString(cookie))));
//				stringSrcMac = ipMacTable.get(CLOUD_SERVER);
//				if(stringSrcMac == null) return;
//				mac = new MacAddress(stringSrcMac);
//				ip = new Ipv4Prefix(CLOUD_SERVER + "/0");
//				// extract output port
//				nodeConnectorId = macTable.get(stringSrcMac);
//				nodeConnectorId2 = macTable.get(dstMac);
//				modSwitchNodeId = SMS_InventoryUtils.getSwitchNodeId(nodeConnectorId);
//	//					modOutputPort = SMS_InventoryUtils.getOutputPort(nodeConnectorId);
//				modOutputPort = SMS_InventoryUtils.getOutputPort(nodeConnectorId2);
//				LOG.debug("[CASE 2 - status] {} | {} | {} | {} | {}", mac, ip, nodeConnectorId, modSwitchNodeId, modOutputPort);
//				InstructionsBuilder isb = SMS_FlowMod.create_DlSrc_NwSrc_Instructions(mac, ip, modOutputPort);
//				flowBuilder.setInstructions(isb.build());
//	//					flowBuilder.setInstructions(SMS_FlowMod.create_DlSrc_NwSrc_Instructions(mac, ip).build());
//	//					if(LOG_programL2Flow == 1) LOG.debug("[SMS| TEST 2] sw {}, output {}", modSwitchNodeId, modOutputPort);
//				LOG.debug("***********/");
//			}else{
//				LOG.debug("/***********");
//				LOG.debug("[CASE 4] this PACKET, FOG CLOUD neither");
//				flowBuilder.setHardTimeout(0);
//				flowBuilder.setIdleTimeout(0);
//				flowBuilder.setPriority(400);
//				flowBuilder.setCookie(new FlowCookie(new BigInteger(Integer.toString(0x44444444))));
//	//					if(LOG_programL2Flow == 1) LOG.debug("[SMS] TEST 3");
//	//					flowBuilder.setPriority(400);
//	//					flowBuilder.setInstructions(SMS_FlowMod_tmp.createSentToControllerInsturctions(egressNodeConnectorId).build());
//				// * 에러) 패킷의 목적지의 nodeId와 현재의 nodeId가 다를 경우, outputPort를 지정할 때 에러가 생긴다.
//				if(switchNodeId.equals(SMS_InventoryUtils.getSwitchNodeId(egressNodeConnectorId))){
//					LOG.debug("createInsturctions_One()");
//					flowBuilder.setInstructions(SMS_FlowRule.createInsturctions_One(egressNodeConnectorId).build());
//				}else{
//					// 이 부분에, 아래의 return;을 주석처리 하고, flood 동작을 추가해야 한다.
//					// 아래 쪽의 메소드도 여기와 마찬가지로 수정해야 한다.
////					return;
//					LOG.debug("createInsturctions_flood()");
//					flowBuilder.setInstructions(SMS_FlowRule.createInsturctions_flood(ingressNodeConnectorId, macTable).build());
//				}
//				LOG.debug("***********/");
//			}
////		}
//// SMS NIaaS END

//////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////
// SMS: test
		// (*) 임시: TEST 할당.
//		fogNodeId = "openflow:1";
		CLOUD_SERVER = "10.0.0.100";
//		FOG_SERVER = "10.0.0.10";
		
		// Creating match object
		MatchBuilder matchBuilder = new MatchBuilder();
		SMS_MatchUtils.createEthDstEthSrcInPortMatch(matchBuilder, new MacAddress(srcMac), new MacAddress(dstMac), ingressNodeConnectorId);
		
		// Create Flow
		FlowBuilder flowBuilder = new FlowBuilder();
		flowBuilder.setMatch(matchBuilder.build());
		
		String flowId = "L2_Rule_" + dstMac +"_"+ srcMac;
		flowBuilder.setId(new FlowId(flowId));
		FlowKey key = new FlowKey(new FlowId(flowId));
		flowBuilder.setBarrier(true);
		flowBuilder.setTableId((short) 0);
		flowBuilder.setKey(key);
		flowBuilder.setFlowName(flowId);
		
		String stringDstMac, stringSrcMac;
		String strCloudMacAddr, strFogMacAddr;
		MacAddress mac;
		Ipv4Prefix ip;
		NodeConnectorId fogNodeConnectorId;
//		NodeConnectorId cloudNodeConnectorId;
		
		String MOD_OutputPort;

		if(switchNodeId.equals(fogNodeId)){ // openflow:x
			if (dstIp.equals(CLOUD_SERVER) && !srcIp.equals(FOG_SERVER)) { // dstIp, 10.0.0.100
				LOG.debug("-------------------------------------------------------------------------------------");
				// option
				flowBuilder.setHardTimeout(0);
				flowBuilder.setIdleTimeout(0);
				flowBuilder.setPriority(500);
				flowBuilder.setCookie(new FlowCookie(new BigInteger(Integer.toString(cookie))));
				
				LOG.debug("[CASE 1] dstIp: CLOUD_SERVER -> FOG_SERVER");				
				strFogMacAddr = ipMacTable.get(FOG_SERVER); // extract FOG_SERVER's macAddr
				if(strFogMacAddr == null) return;
				
				mac = new MacAddress(strFogMacAddr); // MacAddress mac;
				ip = new Ipv4Prefix(FOG_SERVER + "/24"); // Ipv4Prefix ip;

				fogNodeConnectorId = macTable.get(strFogMacAddr); // openflow:x:x
				
				MOD_OutputPort = SMS_InventoryUtils.getOutputPort(fogNodeConnectorId); // extract MOD_outputPort
				LOG.debug("[CASE 1 - status] {} | {} | {} | {}", mac, ip, switchNodeId, MOD_OutputPort);
//				flowBuilder.setInstructions(SMS_FlowMod.create_DlDst_NwDst_outputPort_controller_Instructions(mac, ip, MOD_OutputPort).build());
				flowBuilder.setInstructions(SMS_FlowMod.create_DlDst_NwDst_outputPort_Instructions(mac, ip, MOD_OutputPort).build());
				
				InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
						.child(Node.class, new NodeKey(nodeId))
						.augmentation(FlowCapableNode.class)
						.child(Table.class, new TableKey(flowBuilder.getTableId()))
						.child(Flow.class, flowBuilder.getKey())
						.build();
				
				GenericTransactionUtils.writeData(dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
			}
			else if (srcIp.equals(FOG_SERVER) && !dstIp.equals(CLOUD_SERVER)) { // srcIp, 10.0.0.10
				LOG.debug("-------------------------------------------------------------------------------------");
				// option
				flowBuilder.setHardTimeout(0);
				flowBuilder.setIdleTimeout(0);
				flowBuilder.setPriority(500);
				flowBuilder.setCookie(new FlowCookie(new BigInteger(Integer.toString(cookie))));
				
				LOG.debug("[CASE 2] srcIp: FOG_SERVER -> CLOUD_SERVER");
				strCloudMacAddr = ipMacTable.get(CLOUD_SERVER);
				if(strCloudMacAddr == null) return;
				
				mac = new MacAddress(strCloudMacAddr); // MacAddress mac;
				ip = new Ipv4Prefix(CLOUD_SERVER + "/24"); // Ipv4Prefix ip;
				
//				cloudNodeConnectorId = macTable.get(strCloudMacAddr); // openflow:x:x
				
				MOD_OutputPort = SMS_InventoryUtils.getOutputPort(macTable.get(dstMac)); // 원래 outputPort 그대로 사용
				LOG.debug("[CASE 2 - status] {} | {} | {} | {}", mac, ip, switchNodeId, MOD_OutputPort);
				flowBuilder.setInstructions(SMS_FlowMod.create_DlSrc_NwSrc_outputPort_Instructions(mac, ip, MOD_OutputPort).build());
				
				InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
						.child(Node.class, new NodeKey(nodeId))
						.augmentation(FlowCapableNode.class)
						.child(Table.class, new TableKey(flowBuilder.getTableId()))
						.child(Flow.class, flowBuilder.getKey())
						.build();
				
				GenericTransactionUtils.writeData(dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
			}else{
				LOG.debug("-------------------------------------------------------------------------------------");
				// option
				flowBuilder.setHardTimeout(0);
				flowBuilder.setIdleTimeout(0);
				flowBuilder.setPriority(400);
				flowBuilder.setCookie(new FlowCookie(new BigInteger(Integer.toString(0x44444444))));
				
				LOG.debug("[CASE 3] 일반 동작 (priority = 400 | cookie = 0x44444444)");
				LOG.debug("[CASE 3 - status] {} | {}", switchNodeId, switchOutputPort);
				flowBuilder.setInstructions(SMS_FlowRule.createInsturctions_One(egressNodeConnectorId).build());
				
				InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
						.child(Node.class, new NodeKey(nodeId))
						.augmentation(FlowCapableNode.class)
						.child(Table.class, new TableKey(flowBuilder.getTableId()))
						.child(Flow.class, flowBuilder.getKey())
						.build();
				
				GenericTransactionUtils.writeData(dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
			}
		}
		cookie++;
// SMS END
//////////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		LOG.debug("programL2Flow_pathChange_O() ------------------------------------------------------");
    }
	public static void programL2Flow_pathChange_X(byte[] payload, NodeId nodeId, 
			String srcIp, String dstIp,	String srcMac, String dstMac, 
			NodeConnectorId ingressNodeConnectorId, NodeConnectorId egressNodeConnectorId, 
			Map<String, String> ipMacTable, Map<String, NodeConnectorId> macTable,
			DataBroker dataBroker) 
	{
		final Logger LOG = LoggerFactory.getLogger(TutorialL2Forwarding_ProgramL2Flow.class);
		int LOG_TITLE = 0;
		int LOG_programL2Flow = 0;
		
		LOG.debug("programL2Flow_pathChange_X() ++++++++++++++++++++++++++++++++++++++++++++++++++++++");
/* [2] IF) PACKET 들어온 해당 ovs가 FOG_SERVER가 동작할 위치인지 확인. 즉, ovsId == runningFogOvsId 인지 확인. */
		String switchNodeId = SMS_InventoryUtils.getSwitchNodeId(ingressNodeConnectorId);
		String switchInputPort = SMS_InventoryUtils.getOutputPort(ingressNodeConnectorId);
//		LOG.debug("switchNodeId = {} | switchOutputPort = {}", switchNodeId, switchOutputPort);
		
		
    	/* Programming a flow involves:
    	 * 1. Creating a Flow object that has a match and a list of instructions,
    	 * 2. Adding Flow object as an augmentation to the Node object in the inventory. 
    	 * 3. FlowProgrammer module of OpenFlowPlugin will pick up this data change and eventually program the switch.
    	 */
		
		// Creating match object
		MatchBuilder matchBuilder = new MatchBuilder();														// (1)
		MatchUtils.createEthDstMatch(matchBuilder, new MacAddress(dstMac), null);							// && (1) - 1
		MatchUtils.createInPortMatch(matchBuilder, ingressNodeConnectorId);								// && (1) - 2		
		
		// Create Flow
		FlowBuilder flowBuilder = new FlowBuilder();															// (9)
		flowBuilder.setMatch(matchBuilder.build());															// (9) - 1 && (1) - 3
		
		String flowId = "L2_Rule_" + dstMac;																	// (10)
		flowBuilder.setId(new FlowId(flowId));																	// (9) - 2
		FlowKey key = new FlowKey(new FlowId(flowId));														// (11)
		flowBuilder.setBarrier(true);																			// (9) - 3
		flowBuilder.setTableId((short) 0);																		// (9) - 4
		flowBuilder.setKey(key);																					// (9) - 5
		flowBuilder.setPriority(300);																			// (9) - 6
		flowBuilder.setFlowName(flowId);																		// (9) - 7
		flowBuilder.setHardTimeout(0);																			// (9) - 8
		flowBuilder.setIdleTimeout(0);																			// (9) - 9
		flowBuilder.setCookie(new FlowCookie(new BigInteger(Integer.toString(0x33333333))));
		
		// * 에러) 패킷의 목적지의 nodeId와 현재의 nodeId가 다를 경우, outputPort를 지정할 때 에러가 생긴다.
//		if(switchNodeId.equals(SMS_InventoryUtils.getSwitchNodeId(egressNodeConnectorId))){
			flowBuilder.setInstructions(SMS_FlowRule.createInsturctions_One(egressNodeConnectorId).build());
			
			InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
					.child(Node.class, new NodeKey(nodeId))
					.augmentation(FlowCapableNode.class)
					.child(Table.class, new TableKey(flowBuilder.getTableId()))
					.child(Flow.class, flowBuilder.getKey())
					.build();
			GenericTransactionUtils.writeData(dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
//		}
//		else{
//			// 이 부분에, 아래의 return;을 주석처리 하고, flood 동작을 추가해야 한다.
////			return;
//			LOG.debug("createInsturctions_flood()");
//			flowBuilder.setInstructions(SMS_FlowRule.createInsturctions_flood(ingressNodeConnectorId, macTable).build());
			
//			InstanceIdentifier<Flow> flowIID = InstanceIdentifier.builder(Nodes.class)
//					.child(Node.class, new NodeKey(nodeId))
//					.augmentation(FlowCapableNode.class)
//					.child(Table.class, new TableKey(flowBuilder.getTableId()))
//					.child(Flow.class, flowBuilder.getKey())
//					.build();
//			GenericTransactionUtils.writeData(dataBroker, LogicalDatastoreType.CONFIGURATION, flowIID, flowBuilder.build(), true);
//		}
		
        
		

//		LOG.debug("programL2Flow_pathChange_X() ------------------------------------------------------");
    }
}
