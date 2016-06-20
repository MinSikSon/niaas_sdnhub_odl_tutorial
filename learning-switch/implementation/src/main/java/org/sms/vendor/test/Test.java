package org.sms.vendor.test;

//import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.DecNwTtlCaseBuilder;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.dec.nw.ttl._case.DecNwTtl;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.action.dec.nw.ttl._case.DecNwTtlBuilder;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.action.types.rev131112.action.list.ActionKey;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.instruction.list.InstructionKey;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.actions.grouping.Action;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.action.rev150203.actions.grouping.ActionBuilder;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.instruction.rev130731.instruction.grouping.instruction.choice.ApplyActionsCaseBuilder;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.instruction.rev130731.instruction.grouping.instruction.choice.apply.actions._case.ApplyActionsBuilder;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.instruction.rev130731.instructions.grouping.Instruction;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.openflow.common.instruction.rev130731.instructions.grouping.InstructionBuilder;
//import org.opendaylight.yang.gen.v1.urn.opendaylight.table.types.rev131026.table.feature.prop.type.table.feature.prop.type.InstructionsBuilder;

/**
 * @author msunal
 *
 */
public class Test  {
	
	
//	public static InstructionsBuilder createDecNwTtlInstructionsBld() {
//        // Add our drop action to a list
//        List<Action> actionList = new ArrayList<Action>();
//        actionList.add(createOFAction(0).build());
//        actionList.add(createNxActionBld(1).build());
//        
//        // Create an Apply Action
//        ApplyActionsBuilder aab = new ApplyActionsBuilder();
//        aab.setAction(actionList);
//       
//        // Wrap our Apply Action in an Instruction
//        InstructionBuilder ib = new InstructionBuilder();
//        ib.setInstruction(new ApplyActionsCaseBuilder().setApplyActions(aab.build()).build());
//        ib.setOrder(0);
//        ib.setKey(new InstructionKey(0));
//
//        // Put our Instruction in a list of Instructions
//        InstructionsBuilder isb = new InstructionsBuilder();
//        List<Instruction> instructions = new ArrayList<Instruction>();
//        instructions.add(ib.build());
//        ib.setKey(new InstructionKey(0));
//        isb.setInstruction(instructions);
//        return isb;
//    }
//
//    /**
//     * @param actionKeyVal 
//     * @return
//     */
//    private static ActionBuilder createOFAction(int actionKeyVal) {
//        DecNwTtlBuilder ta = new DecNwTtlBuilder();
//        DecNwTtl decNwTtl = ta.build();
//        ActionBuilder ab = new ActionBuilder();
//        ab.setAction(new DecNwTtlCaseBuilder().setDecNwTtl(decNwTtl).build());
//        ab.setKey(new ActionKey(actionKeyVal));
//        return ab;
//    }
//
//    /**
//     * @param actionKeyVal TODO
//     * @return
//     */
//    private static ActionBuilder createNxActionBld(int actionKeyVal) {
//        // vendor part
////    	 DstNxRegCaseBuilder nxRegCaseBld = new DstNxRegCaseBuilder().setNxReg(NxmNxReg0.class);
//        DstNxRegCaseBuilder nxRegCaseBld = new DstNxRegCaseBuilder().setNxReg(NxmNxReg.class);
//        DstBuilder dstBld = new DstBuilder()
//            .setDstChoice(nxRegCaseBld.build())
//            .setStart(0)
//            .setEnd(5);
//        NxRegLoadBuilder nxRegLoadBuilder = new NxRegLoadBuilder();
//        nxRegLoadBuilder.setDst(dstBld.build());
//        nxRegLoadBuilder.setValue(BigInteger.valueOf(55L));
//        NxActionRegLoadNodesNodeTableFlowApplyActionsCaseBuilder topNxActionCaseBld = new NxActionRegLoadNodesNodeTableFlowApplyActionsCaseBuilder();
//        topNxActionCaseBld.setNxRegLoad(nxRegLoadBuilder.build());
//        
//        // base part
//        ActionBuilder abExt = new ActionBuilder();
//        abExt.setKey(new ActionKey(actionKeyVal));
//        abExt.setAction(topNxActionCaseBld.build());
//        return abExt;
//    }
    

}
