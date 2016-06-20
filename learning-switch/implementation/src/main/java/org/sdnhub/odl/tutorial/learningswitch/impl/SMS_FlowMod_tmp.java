package org.sdnhub.odl.tutorial.learningswitch.impl;

import java.util.ArrayList;
import java.util.List;

import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Uri;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.DecNwTtlCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.DropActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.OutputActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.dec.nw.ttl._case.DecNwTtl;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.dec.nw.ttl._case.DecNwTtlBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.drop.action._case.DropActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.output.action._case.OutputActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.Action;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.InstructionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.ApplyActionsCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.instruction.apply.actions._case.ApplyActionsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.Instruction;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;


public abstract class SMS_FlowMod_tmp {
	/* ActionBuilder */
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
	private static ActionBuilder createOFAction_piolink(int actionKeyVal) {
		DecNwTtlBuilder ta = new DecNwTtlBuilder();
		DecNwTtl decNwTtl = ta.build();
		ActionBuilder ab = new ActionBuilder();

		ab.setAction(new DecNwTtlCaseBuilder().setDecNwTtl(decNwTtl).build());

		ab.setOrder(0); // SMS
		ab.setKey(new ActionKey(actionKeyVal));
		return ab;
	}
	

	
	/* InstructionsBuilder */
	public static InstructionsBuilder piolink_test(){
		final Logger LOG = LoggerFactory.getLogger(SMS_FlowMod_tmp.class);
		List<Action> actionList = new ArrayList<Action>();
		actionList.add(createOFAction_piolink(0).build());
		actionList.add(createOFAction_controller(1).build());
		
		LOG.debug("piolink_test()+++++++++++++++++++++++++");
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
		ib.setKey(new InstructionKey(0));
		isb.setInstruction(instructions);
		return isb;
	}
	
	public static InstructionsBuilder createSentToControllerInsturctions(NodeConnectorId egressNodeConnectorId){
		// test multitude "Action"
		List<Action> actionList = new ArrayList<Action>();												// (1)
		ActionBuilder ab = new ActionBuilder();															// (2)
		ActionBuilder ab2 = new ActionBuilder();
		
		OutputActionBuilder oab = new OutputActionBuilder();												// (3)
		OutputActionBuilder oab2 = new OutputActionBuilder();	
		oab.setMaxLength(65535);																				// (3) - 1
		oab2.setMaxLength(65535);
		
		Uri value = new Uri("CONTROLLER");																	// (4)
		oab.setOutputNodeConnector(value);																	// (3) - 2
		oab2.setOutputNodeConnector(egressNodeConnectorId);
		ab.setAction(new OutputActionCaseBuilder().setOutputAction(oab.build()).build());			// (2) - 1 && (3) - 2
		ab.setOrder(0);																						// (2) - 2
		ab.setKey(new ActionKey(0));																		// (2) - 3
		ab2.setAction(new OutputActionCaseBuilder().setOutputAction(oab2.build()).build());
		ab2.setOrder(1);
		ab2.setKey(new ActionKey(1));
		actionList.add(ab.build());																			// (1) - 1 && (2) - 4
//		actionList.add(ab2.build());
		
		// Create an Apply Action
		ApplyActionsBuilder aab = new ApplyActionsBuilder();												// (5)
		aab.setAction(actionList);																			// (5) - 1 && (1) - 2
		
		// Wrap our Apply Action in an Instruction
		InstructionBuilder ib = new InstructionBuilder();												// (6)
		ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());		// (6) - 1 && (5) - 2
		ib.setOrder(0);																						// (6) - 2
		ib.setKey(new InstructionKey(0));																	// (6) - 3
		
		// Put our Instruction in a list of Instructions
		InstructionsBuilder isb = new InstructionsBuilder();												// (7)
		List<Instruction> instructions = new ArrayList<Instruction>();									// (8)
		instructions.add(ib.build());																		// (8) - 1 && (6) - 4
		
		isb.setInstruction(instructions);																	// (7) - 1
		return isb;																							// (7) - 2
	}
	
	
	public static InstructionsBuilder createInsturctions_Drop(){
		// Instructions List Stores Individual Instructions
		ActionBuilder ab = new ActionBuilder();																// (5)
		List<Action> actionList = Lists.newArrayList();														// (6)
		
		DropActionBuilder dab = new DropActionBuilder();
		
		ab.setAction(new DropActionCaseBuilder().setDropAction(dab.build()).build());
		ab.setOrder(0);																							// (5) - 2
		ab.setKey(new ActionKey(0));																			// (5) - 3
		
		actionList.add(ab.build());																				// (6) - 1 && (5) - 4
		
		ApplyActionsBuilder aab = new ApplyActionsBuilder();													// (7)
		aab.setAction(actionList);																				// (7) - 1 && (6) - 2
				
		// Create Apply Actions Instruction
		InstructionBuilder ib = new InstructionBuilder();													// (2)
		ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());			// (2) - 1 && (7) - 2
		ib.setOrder(0);																							// (2) - 2
		ib.setKey(new InstructionKey(0));																		// (2) - 3
		
		// Put our Instruction in a list of Instructions
		InstructionsBuilder isb = new InstructionsBuilder();													// (4)
		List<Instruction> instructions = Lists.newArrayList();												// (3)
		instructions.add(ib.build());																			// (3) - 1 && (2) - 4																// (7) - 1

		isb.setInstruction(instructions);
		return isb;																							// (7) - 2
	}
}

