package org.sdnhub.odl.tutorial.learningswitch.impl;

import java.util.ArrayList;
import java.util.List;

import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Ipv4Prefix;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Uri;
import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.DropActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.FloodActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.OutputActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.SetDlDstActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.SetDlSrcActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.SetNwDstActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.SetNwSrcActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.drop.action._case.DropAction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.drop.action._case.DropActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.flood.action._case.FloodActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.dl.dst.action._case.SetDlDstActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.dl.src.action._case.SetDlSrcActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.nw.dst.action._case.SetNwDstActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.set.nw.src.action._case.SetNwSrcActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.address.address.Ipv4Builder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.InstructionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.ApplyActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionKey;

import com.google.common.collect.Lists;

public class SMS_FlowMod {
//	public static InstructionsBuilder create_DlDst_NwDst_Instructions(MacAddress macAddress, Ipv4Prefix prefixDst) {
	public static InstructionsBuilder create_DlDst_NwDst_outputPort_Instructions(MacAddress macAddress, Ipv4Prefix prefixDst, String outputPort) {
		// ActionList
		List<Action> actionList = Lists.newArrayList();
		// Action 1
		ActionBuilder ab = new ActionBuilder();
		SetDlDstActionBuilder dlDstActionBuilder = new SetDlDstActionBuilder();
		dlDstActionBuilder.setAddress(macAddress);
		ab.setAction(new SetDlDstActionCaseBuilder().setSetDlDstAction(dlDstActionBuilder.build()).build());
		ab.setOrder(0);
		ab.setKey(new ActionKey(0));

		// Action 2
		ActionBuilder ab2 = new ActionBuilder();
		SetNwDstActionBuilder setNwDstActionBuilder = new SetNwDstActionBuilder();
		Ipv4Builder ipDst = new Ipv4Builder();
		ipDst.setIpv4Address(prefixDst);
		setNwDstActionBuilder.setAddress(ipDst.build());
		ab2.setAction(new SetNwDstActionCaseBuilder().setSetNwDstAction(setNwDstActionBuilder.build()).build());
		ab2.setOrder(1);
		ab2.setKey(new ActionKey(1));

		// - have to make nodeconnectionid store table
		// Action 3
		ActionBuilder ab3 = new ActionBuilder();
		OutputActionBuilder outputActionBuilder = new OutputActionBuilder();
//		outputActionBuilder.setOutputNodeConnector(new Uri("output:1"));
		outputActionBuilder.setOutputNodeConnector(new Uri("output:"+outputPort));
		ab3.setAction(new OutputActionCaseBuilder().setOutputAction(outputActionBuilder.build()).build());
		ab3.setOrder(2);
		ab3.setKey(new ActionKey(2));		
		
		actionList.add(ab.build());
		actionList.add(ab2.build());
		actionList.add(ab3.build());
		// Action End

		// Create an Apply Action
		ApplyActionsBuilder aab = new ApplyActionsBuilder();
		aab.setAction(actionList);

		// Wrap our Apply Action in an Instruction
		InstructionBuilder ib = new InstructionBuilder();
		ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
		ib.setOrder(0);
		ib.setKey(new InstructionKey(0));

		// Put our Instruction in a list of Instructions
		InstructionsBuilder isb = new InstructionsBuilder();
		List<Instruction> instructions = new ArrayList<Instruction>();
		instructions.add(ib.build());
		
		isb.setInstruction(instructions);
		return isb;
	}
	// createDlSrcInstructions(InstructionBuilder ib, MacAddress macAddress) {
//	public static InstructionsBuilder create_DlSrc_NwSrc_Instructions(MacAddress macAddress, Ipv4Prefix prefixSrc) {
	public static InstructionsBuilder create_DlSrc_NwSrc_outputPort_Instructions(MacAddress macAddress, Ipv4Prefix prefixSrc, String outputPort) {
		// ActionList
		List<Action> actionList = Lists.newArrayList();
		// Action 1
		ActionBuilder ab = new ActionBuilder();
		SetDlSrcActionBuilder dlSrcActionBuilder = new SetDlSrcActionBuilder();
		dlSrcActionBuilder.setAddress(macAddress);
		ab.setAction(new SetDlSrcActionCaseBuilder().setSetDlSrcAction(dlSrcActionBuilder.build()).build());
		ab.setOrder(0);
		ab.setKey(new ActionKey(0));

		// Action 2
		ActionBuilder ab2 = new ActionBuilder();
		SetNwSrcActionBuilder setNwsrcActionBuilder = new SetNwSrcActionBuilder();
		Ipv4Builder ipsrc = new Ipv4Builder();
		ipsrc.setIpv4Address(prefixSrc);
		setNwsrcActionBuilder.setAddress(ipsrc.build());
		ab2.setAction(new SetNwSrcActionCaseBuilder().setSetNwSrcAction(setNwsrcActionBuilder.build()).build());
		ab2.setOrder(1);
		ab2.setKey(new ActionKey(1));
		
		// - have to make nodeconnectionid store table
		// Action 3
		ActionBuilder ab3 = new ActionBuilder();
		OutputActionBuilder outputActionBuilder = new OutputActionBuilder();
		if(outputPort.equals("4294967291")){
			outputActionBuilder.setOutputNodeConnector(new Uri("FLOOD"));
		}else{
			outputActionBuilder.setOutputNodeConnector(new Uri("output:"+outputPort));
		}
		ab3.setAction(new OutputActionCaseBuilder().setOutputAction(outputActionBuilder.build()).build());
		ab3.setOrder(2);
		ab3.setKey(new ActionKey(2));
		
		actionList.add(ab.build());
		actionList.add(ab2.build());
		actionList.add(ab3.build());
		// Action End

		// Create an Apply Action
		ApplyActionsBuilder aab = new ApplyActionsBuilder();
		aab.setAction(actionList);

		// Wrap our Apply Action in an Instruction
		InstructionBuilder ib = new InstructionBuilder();
		ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
		ib.setOrder(0);
		ib.setKey(new InstructionKey(0));

		// Put our Instruction in a list of Instructions
		InstructionsBuilder isb = new InstructionsBuilder();
		List<Instruction> instructions = new ArrayList<Instruction>();
		instructions.add(ib.build());
		isb.setInstruction(instructions);
		return isb;
	}
	
	public static InstructionsBuilder create_DlSrc_NwSrc_Controller_Instructions(MacAddress macAddress, Ipv4Prefix prefixSrc) {
		// ActionList
		List<Action> actionList = Lists.newArrayList();
		// Action 1
		ActionBuilder ab = new ActionBuilder();
		SetDlSrcActionBuilder dlSrcActionBuilder = new SetDlSrcActionBuilder();
		dlSrcActionBuilder.setAddress(macAddress);
		ab.setAction(new SetDlSrcActionCaseBuilder().setSetDlSrcAction(dlSrcActionBuilder.build()).build());
		ab.setOrder(0);
		ab.setKey(new ActionKey(0));

		// Action 2
		ActionBuilder ab2 = new ActionBuilder();
		SetNwSrcActionBuilder setNwsrcActionBuilder = new SetNwSrcActionBuilder();
		Ipv4Builder ipsrc = new Ipv4Builder();
		ipsrc.setIpv4Address(prefixSrc);
		setNwsrcActionBuilder.setAddress(ipsrc.build());
		ab2.setAction(new SetNwSrcActionCaseBuilder().setSetNwSrcAction(setNwsrcActionBuilder.build()).build());
		ab2.setOrder(1);
		ab2.setKey(new ActionKey(1));

		// Action 3
		ActionBuilder ab3 = new ActionBuilder();
		OutputActionBuilder oab = new OutputActionBuilder();
		oab.setMaxLength(65535);
		Uri value = new Uri("CONTROLLER");
		oab.setOutputNodeConnector(value);
		ab3.setAction(new OutputActionCaseBuilder().setOutputAction(oab.build()).build());
		ab3.setOrder(2);
		ab3.setKey(new ActionKey(2));
		
		actionList.add(ab.build());
		actionList.add(ab2.build());
		actionList.add(ab3.build());
		// Action End

		// Create an Apply Action
		ApplyActionsBuilder aab = new ApplyActionsBuilder();
		aab.setAction(actionList);

		// Wrap our Apply Action in an Instruction
		InstructionBuilder ib = new InstructionBuilder();
		ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
		ib.setOrder(0);
		ib.setKey(new InstructionKey(0));

		// Put our Instruction in a list of Instructions
		InstructionsBuilder isb = new InstructionsBuilder();
		List<Instruction> instructions = new ArrayList<Instruction>();
		instructions.add(ib.build());
		isb.setInstruction(instructions);
		return isb;
	}
	
	public static InstructionsBuilder createDropInstructions() {
		// Add our drop action to a list
		List<Action> actionList = Lists.newArrayList();
		
		// Drop Action
		DropActionBuilder dab = new DropActionBuilder();
		DropAction dropAction = dab.build();
		ActionBuilder ab = new ActionBuilder();
		ab.setAction(new DropActionCaseBuilder().setDropAction(dropAction).build());
		ab.setOrder(0);
		ab.setKey(new ActionKey(0));
		
		actionList.add(ab.build());

		// Create an Apply Action
		ApplyActionsBuilder aab = new ApplyActionsBuilder();
		aab.setAction(actionList);

		// Wrap our Apply Action in an Instruction
		InstructionBuilder ib = new InstructionBuilder();
		ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
		ib.setOrder(0);
		ib.setKey(new InstructionKey(0));

		// Put our Instruction in a list of Instructions
		InstructionsBuilder isb = new InstructionsBuilder();
		List<Instruction> instructions = new ArrayList<Instruction>();
		instructions.add(ib.build());
		isb.setInstruction(instructions);
		return isb;
	}
	
	public static InstructionsBuilder createControllerInstructions() {
		// ActionList
		List<Action> actionList = Lists.newArrayList();
		// Action 1
		ActionBuilder ab = new ActionBuilder();
		OutputActionBuilder oab = new OutputActionBuilder();
		oab.setMaxLength(65535);
		Uri value = new Uri("CONTROLLER");
		oab.setOutputNodeConnector(value);
		ab.setAction(new OutputActionCaseBuilder().setOutputAction(oab.build()).build());
		ab.setOrder(0);
		ab.setKey(new ActionKey(0));
		
		actionList.add(ab.build());
		// Action End

		// Create an Apply Action
		ApplyActionsBuilder aab = new ApplyActionsBuilder();
		aab.setAction(actionList);

		// Wrap our Apply Action in an Instruction
		InstructionBuilder ib = new InstructionBuilder();
		ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
		ib.setOrder(0);
		ib.setKey(new InstructionKey(0));

		// Put our Instruction in a list of Instructions
		InstructionsBuilder isb = new InstructionsBuilder();
		List<Instruction> instructions = new ArrayList<Instruction>();
		instructions.add(ib.build());
		isb.setInstruction(instructions);
		return isb;
	}
}

