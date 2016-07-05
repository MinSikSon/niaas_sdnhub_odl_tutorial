package org.sdnhub.odl.tutorial.learningswitch.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.inet.types.rev100924.Uri;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.DropActionCaseBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.OutputActionCaseBuilder;
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

import com.google.common.collect.Lists;

public class Niaas_FlowRule {
	public static InstructionsBuilder createInsturctions_One(NodeConnectorId egressNodeConnectorId){
		// ActionList
		List<Action> actionList = Lists.newArrayList();
		// Action 1
		ActionBuilder ab = new ActionBuilder();
		OutputActionBuilder oab = new OutputActionBuilder();
//		oab.setMaxLength(65535); // CONTROLLER / Send full packet and No buffer
		oab.setOutputNodeConnector(egressNodeConnectorId);
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
	
	public static InstructionsBuilder createSentToControllerInsturctions(){
		// test multitude "Action"
		List<Action> actionList = new ArrayList<Action>();												// (1)
		ActionBuilder ab = new ActionBuilder();															// (2)
		
		OutputActionBuilder oab = new OutputActionBuilder();												// (3)
		oab.setMaxLength(65535);																				// (3) - 1
		
		Uri value = new Uri("CONTROLLER");																	// (4)
		oab.setOutputNodeConnector(value);																	// (3) - 2
		ab.setAction(new OutputActionCaseBuilder().setOutputAction(oab.build()).build());			// (2) - 1 && (3) - 2
		ab.setOrder(0);																						// (2) - 2
		ab.setKey(new ActionKey(0));																		// (2) - 3
		actionList.add(ab.build());																			// (1) - 1 && (2) - 4
		
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
	
	public static InstructionsBuilder createInsturctions_flood(NodeConnectorId ingressNodeConnectorId,  Map<String, NodeConnectorId> macTable){
		// ActionList
		List<Action> actionList = Lists.newArrayList();
		
		// Action 1
		ActionBuilder ab1 = new ActionBuilder();
		OutputActionBuilder oab1 = new OutputActionBuilder();
		oab1.setMaxLength(65535); // CONTROLLER / Send full packet and No buffer
		oab1.setOutputNodeConnector(new Uri("CONTROLLER"));
		ab1.setAction(new OutputActionCaseBuilder().setOutputAction(oab1.build()).build());
		ab1.setOrder(0);
		ab1.setKey(new ActionKey(0));
		
		actionList.add(ab1.build());
		
		// Action 2~
		int i = 0;
		int actionCount = 1;
		
		ActionBuilder ab;
		OutputActionBuilder oab;
		
		Set<Entry<String, NodeConnectorId>> set = macTable.entrySet();;
		Iterator<Entry<String,NodeConnectorId>> it = set.iterator();;
		Map.Entry<String, NodeConnectorId> e;
		NodeConnectorId nodeConnectorId;
		String outputPort;
		ArrayList<String> outputPort_List = new ArrayList<String>();
		for(i = 0 ; i < macTable.size() ; i++){
			e = (Map.Entry<String, NodeConnectorId>)it.next();
			nodeConnectorId = e.getValue();
			if(Niaas_InventoryUtils.getSwitchNodeId(ingressNodeConnectorId).equals(Niaas_InventoryUtils.getSwitchNodeId(nodeConnectorId))) // nodeId 확인
			{ 
				outputPort = Niaas_InventoryUtils.getOutputPort(nodeConnectorId);
				if(!Niaas_InventoryUtils.getOutputPort(ingressNodeConnectorId).equals(outputPort)) // outputPort 확인
				{ 
					if(!outputPort_List.contains(outputPort)) // outputPort 중복 확인
					{
						outputPort_List.add(outputPort);
						ab = new ActionBuilder();
						oab = new OutputActionBuilder();
//						oab.setOutputNodeConnector(NodeConnectorId);
						oab.setOutputNodeConnector(new Uri("output:"+outputPort));
						ab.setAction(new OutputActionCaseBuilder().setOutputAction(oab.build()).build());
						ab.setOrder(actionCount);
						ab.setKey(new ActionKey(actionCount));
						
						actionList.add(ab.build());
						actionCount++;
					}
				}
			}
		}
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
