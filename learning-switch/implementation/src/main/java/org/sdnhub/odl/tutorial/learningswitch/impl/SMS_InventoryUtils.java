package org.sdnhub.odl.tutorial.learningswitch.impl;

import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;

public class SMS_InventoryUtils {
//	public static String getSwitchNodeId(NodeId ingressNodeId){
//		String switchNodeId;
//		
//		String[] values = ingressNodeId.getValue().toString().split(":");
//		switchNodeId = values[0] + ":" + values[1];
//		return switchNodeId;
//	}
	
	public static String getSwitchNodeId(NodeConnectorId ingressNodeConnectorId){
		String switchNodeId;
		
		String[] values = ingressNodeConnectorId.getValue().toString().split(":");
		switchNodeId = values[0] + ":" + values[1];
		return switchNodeId;
	}
	public static int getSwitchNodeId_number(NodeConnectorId ingressNodeConnectorId){
		int switchNodeId_number;
		
		String[] values = ingressNodeConnectorId.getValue().toString().split(":");
		switchNodeId_number = Integer.parseInt(values[1]);
		return switchNodeId_number;
	}
	public static String getOutputPort(NodeConnectorId ingressNodeConnectorId){
		String switchNodeId;
		
		String[] values = ingressNodeConnectorId.getValue().toString().split(":");
		switchNodeId = values[2];
		return switchNodeId;
	}
	
}
