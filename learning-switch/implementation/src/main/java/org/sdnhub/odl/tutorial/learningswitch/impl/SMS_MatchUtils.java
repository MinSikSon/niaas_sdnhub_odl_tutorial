package org.sdnhub.odl.tutorial.learningswitch.impl;

import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetDestinationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetSourceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.EthernetMatchBuilder;

public class SMS_MatchUtils {
	public static MatchBuilder createEthDstEthSrcInPortMatch(MatchBuilder matchBuilder, MacAddress sMacAddr, MacAddress dMacAddr, NodeConnectorId ncId){
		EthernetMatchBuilder ethernetMatch = new EthernetMatchBuilder();
		EthernetSourceBuilder ethSourceBuilder = new EthernetSourceBuilder(); // createEthSrcMatch
		ethSourceBuilder.setAddress(new MacAddress(sMacAddr));
		ethernetMatch.setEthernetSource(ethSourceBuilder.build());
		
		EthernetDestinationBuilder ethDestinationBuilder = new EthernetDestinationBuilder(); // createEthDstMatch
		ethDestinationBuilder.setAddress(new MacAddress(dMacAddr));
		ethDestinationBuilder.setMask(null);
		ethernetMatch.setEthernetDestination(ethDestinationBuilder.build());
		matchBuilder.setEthernetMatch(ethernetMatch.build());
		
		matchBuilder.setInPort(ncId); // createInPortMatch
		return matchBuilder;
	}
}
