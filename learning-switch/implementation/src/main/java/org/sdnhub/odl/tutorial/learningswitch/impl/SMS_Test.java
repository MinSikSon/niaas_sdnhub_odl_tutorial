package org.sdnhub.odl.tutorial.learningswitch.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Uri;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.DecNwTtlCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.OutputActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.dec.nw.ttl._case.DecNwTtl;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.dec.nw.ttl._case.DecNwTtlBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.inventory.rev130819.tables.table.FlowBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.FlowCookie;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.FlowModFlags;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.InstructionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.ApplyActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.Nodes;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.Node;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.nodes.NodeKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.EtherType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetTypeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.EthernetMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4Match;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.layer._3.match.Ipv4MatchBuilder;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sms.vendor.test.SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseBuilder;
import org.sms.vendor.test.SMS_DstBuilder;
import org.sms.vendor.test.SMS_Reg0;
import org.sms.vendor.test.SMS_RegCaseBuilder;
import org.sms.vendor.test.SMS_RegLoadBuilder;


public class SMS_Test {
	// match
	private static MatchBuilder createSmsMatchBld() {
		MatchBuilder match = new MatchBuilder();
		Ipv4MatchBuilder ipv4Match = new Ipv4MatchBuilder();
		Ipv4Prefix prefix = new Ipv4Prefix("10.0.0.1/24");
		ipv4Match.setIpv4Destination(prefix);
		Ipv4Match i4m = ipv4Match.build();
		match.setLayer3Match(i4m);

		EthernetMatchBuilder eth = new EthernetMatchBuilder();
		EthernetTypeBuilder ethTypeBuilder = new EthernetTypeBuilder();
		ethTypeBuilder.setType(new EtherType(0x0800L));
		eth.setEthernetType(ethTypeBuilder.build());
		match.setEthernetMatch(eth.build());

		// AugmentTuple<Match> extAugmentWrapper = createNxMatchAugment();
		// match.addAugmentation(extAugmentWrapper.getAugmentationClass(), extAugmentWrapper.getAugmentationObject());

		return match;
	}
	// flow
//	public static AddFlowInputBuilder createSmsFlowInputBuilder() {
	public static FlowBuilder createSmsFlowBuilder() {
//		AddFlowInputBuilder flow = new AddFlowInputBuilder();
		FlowBuilder flow = new FlowBuilder();
		flow.setPriority(2);
		flow.setMatch(createSmsMatchBld().build());
		flow.setInstructions(createDecNwTtlInstructionsBld().build());
		flow.setBarrier(Boolean.FALSE);
		BigInteger value = BigInteger.valueOf(10L);
		flow.setCookie(new FlowCookie(value));
		flow.setCookieMask(new FlowCookie(value));
		flow.setHardTimeout(0);
		flow.setIdleTimeout(0);
		flow.setInstallHw(false);
		flow.setStrict(false);
		flow.setContainerName(null);
		flow.setFlags(new FlowModFlags(false, false, false, false, true));
		flow.setTableId((short) 0);

		flow.setFlowName("SmsFLOW");
    
		// Construct the flow instance id
		final InstanceIdentifier<Node> flowInstanceId = InstanceIdentifier
            .builder(Nodes.class) // File under nodes
            .child(Node.class, new NodeKey(new NodeId("openflow:1"))).build(); // A particular node identified by nodeKey
//		flow.setNode(new NodeRef(flowInstanceId));
    
//    pushFlowViaRpc(flow.build());
//    
//    return Futures.immediateFuture(RpcResultBuilder.<Void>status(true).build());
    return flow;
}

/**
 * @param addFlowInput
 */
//private void pushFlowViaRpc(AddFlowInput addFlowInput) {
//    flowService.addFlow(addFlowInput);
//}
	
	// instruction
	public static InstructionsBuilder createDecNwTtlInstructionsBld() {
		final Logger LOG = LoggerFactory.getLogger(SMS_Test.class);
		LOG.debug("createDecNwTtlInstructionsBld()++++++++++++++++++++++++++++++++++++");
		// Add our drop action to a list
		List<Action> actionList = new ArrayList<Action>();
		actionList.add(createOFAction(0).build());
		actionList.add(createSmsActionBld(1).build());
//		actionList.add(createOFAction_controller(0).build()); // test

		// Create an Apply Action
		ApplyActionsBuilder aab = new ApplyActionsBuilder();
		aab.setAction(actionList);

		// Wrap our Apply Action in an Instruction
		InstructionBuilder ib = new InstructionBuilder();
		ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
		ib.setKey(new InstructionKey(0));
		ib.setOrder(0);

		// Put our Instruction in a list of Instructions
		InstructionsBuilder isb = new InstructionsBuilder();
		List<Instruction> instructions = new ArrayList<Instruction>();
		instructions.add(ib.build());
		ib.setKey(new InstructionKey(0));
		isb.setInstruction(instructions);
		LOG.debug("createDecNwTtlInstructionsBld()------------------------------------");
		return isb;
	}

	// action
	private static ActionBuilder createOFAction(int actionKeyVal) {
		DecNwTtlBuilder ta = new DecNwTtlBuilder();
		DecNwTtl decNwTtl = ta.build();
		ActionBuilder ab = new ActionBuilder();
		ab.setAction(new DecNwTtlCaseBuilder().setDecNwTtl(decNwTtl).build());
		ab.setKey(new ActionKey(actionKeyVal));
		return ab;
	}

	// action
	private static ActionBuilder createSmsActionBld(int actionKeyVal){
		// vendor part
		SMS_RegCaseBuilder smsRegCaseBld = new SMS_RegCaseBuilder().setSMS_Reg(SMS_Reg0.class);
		SMS_DstBuilder dstBld = new SMS_DstBuilder().setSMS_DstChoice(smsRegCaseBld.build()).setStart(0).setEnd(5);
		SMS_RegLoadBuilder smsRegLoadBuilder = new SMS_RegLoadBuilder();
		smsRegLoadBuilder.setSMS_Dst(dstBld.build());
		smsRegLoadBuilder.setValue(BigInteger.valueOf(55L));
		SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseBuilder topSmsActionCaseBld = new SMS_ActionRegLoadNodesNodeTableFlowApplyActionsCaseBuilder();
		topSmsActionCaseBld.setSMS_RegLoad(smsRegLoadBuilder.build());
//		// base part
		ActionBuilder abExt = new ActionBuilder();
		abExt.setKey(new ActionKey(actionKeyVal));
		abExt.setAction(topSmsActionCaseBld.build());
		return abExt;
	}
	
	// test action
	private static ActionBuilder createOFAction_controller(int actionKeyVal) {
		ActionBuilder ab = new ActionBuilder();
		OutputActionBuilder oab = new OutputActionBuilder(); // SMS
		oab.setMaxLength(65535); // SMS
		Uri value = new Uri("CONTROLLER");	// SMS
//		Uri value = new Uri("piolink(body=33554442) (body2=50331658)"); // SMS	
//		Uri value = new Uri("EXPERIMENTER"); // SMS
		oab.setOutputNodeConnector(value); // SMS

		ab.setAction(new OutputActionCaseBuilder().setOutputAction(oab.build()).build()); // SMS
		ab.setOrder(0); // SMS
		ab.setKey(new ActionKey(actionKeyVal));
		return ab;
	}
}
