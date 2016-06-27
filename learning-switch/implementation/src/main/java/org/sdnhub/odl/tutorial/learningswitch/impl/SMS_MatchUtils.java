// NIaaS SMS
package org.sdnhub.odl.tutorial.learningswitch.impl;

import org.opendaylight.yang.gen.v1.urn.ietf.params.xml.ns.yang.ietf.yang.types.rev100924.MacAddress;
import org.opendaylight.yang.gen.v1.urn.opendaylight.flow.types.rev131026.flow.MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.inventory.rev130819.NodeConnectorId;
import org.opendaylight.yang.gen.v1.urn.opendaylight.l2.types.rev130827.EtherType;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetDestinationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetSourceBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.ethernet.match.fields.EthernetTypeBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.EthernetMatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.Icmpv4MatchBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.model.match.types.rev131026.match.IpMatchBuilder;

public class SMS_MatchUtils {
	public static final long IPV4_LONG = (long) 0x800;
	public static final long LLDP_LONG = (long) 0x88CC;
	
	/** 
	 * Create Ethernet Destination Match + Create Ethernet Source Match + inPort
	 *
	 * @param matchBuilder 	MatchBuilder Object without a match yet
	 * @param sMacAddr		String representing a source MAC
	 * @param ncId			IngressNodeConnectorId
	 * @return matchBuilder Map MatchBuilder Object with a match
	 */
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
	
	/**
     * Create Ethernet Destination Match + EtherType ipv4
     *
     * @param matchBuilder MatchBuilder Object without a match yet
     * @param dMacAddr     String representing a destination MAC
     * @return matchBuilder Map MatchBuilder Object with a match
     */
    public static MatchBuilder createEthDstEthSrcIpv4Match(MatchBuilder matchBuilder, MacAddress sMacAddr, MacAddress dMacAddr, MacAddress mask) {

		EthernetMatchBuilder ethernetMatch = new EthernetMatchBuilder();
		
		EthernetSourceBuilder ethSourceBuilder = new EthernetSourceBuilder();
		ethSourceBuilder.setAddress(new MacAddress(sMacAddr));
		ethernetMatch.setEthernetSource(ethSourceBuilder.build());

		EthernetDestinationBuilder ethDestinationBuilder = new EthernetDestinationBuilder();
		ethDestinationBuilder.setAddress(new MacAddress(dMacAddr));
		if (mask != null) {
			ethDestinationBuilder.setMask(mask);
		}
		ethernetMatch.setEthernetDestination(ethDestinationBuilder.build());
		matchBuilder.setEthernetMatch(ethernetMatch.build());

		EthernetTypeBuilder ethTypeBuilder = new EthernetTypeBuilder();
		ethTypeBuilder.setType(new EtherType(IPV4_LONG));
		ethernetMatch.setEthernetType(ethTypeBuilder.build());
		matchBuilder.setEthernetMatch(ethernetMatch.build());

		return matchBuilder;
    }
    
    public static MatchBuilder createEthDstEthSrcIpv4InportMatch(MatchBuilder matchBuilder, MacAddress sMacAddr, MacAddress dMacAddr, MacAddress mask, NodeConnectorId ncId) {

		EthernetMatchBuilder ethernetMatch = new EthernetMatchBuilder();
		
		EthernetSourceBuilder ethSourceBuilder = new EthernetSourceBuilder();
		ethSourceBuilder.setAddress(new MacAddress(sMacAddr));
		ethernetMatch.setEthernetSource(ethSourceBuilder.build());

		EthernetDestinationBuilder ethDestinationBuilder = new EthernetDestinationBuilder();
		ethDestinationBuilder.setAddress(new MacAddress(dMacAddr));
		if (mask != null) {
			ethDestinationBuilder.setMask(mask);
		}
		ethernetMatch.setEthernetDestination(ethDestinationBuilder.build());
		matchBuilder.setEthernetMatch(ethernetMatch.build());

		EthernetTypeBuilder ethTypeBuilder = new EthernetTypeBuilder();
		ethTypeBuilder.setType(new EtherType(IPV4_LONG));
		ethernetMatch.setEthernetType(ethTypeBuilder.build());
		matchBuilder.setEthernetMatch(ethernetMatch.build());

		matchBuilder.setInPort(ncId); // createInPortMatch
		return matchBuilder;
    }
	
    public static MatchBuilder createEthDstEthSrcMatch(MatchBuilder matchBuilder, MacAddress sMacAddr, MacAddress dMacAddr, MacAddress mask) {

		EthernetMatchBuilder ethernetMatch = new EthernetMatchBuilder();
		
		EthernetSourceBuilder ethSourceBuilder = new EthernetSourceBuilder();
		ethSourceBuilder.setAddress(new MacAddress(sMacAddr));
		ethernetMatch.setEthernetSource(ethSourceBuilder.build());

		EthernetDestinationBuilder ethDestinationBuilder = new EthernetDestinationBuilder();
		ethDestinationBuilder.setAddress(new MacAddress(dMacAddr));
		if (mask != null) {
			ethDestinationBuilder.setMask(mask);
		}
		ethernetMatch.setEthernetDestination(ethDestinationBuilder.build());
		matchBuilder.setEthernetMatch(ethernetMatch.build());

//		EthernetTypeBuilder ethTypeBuilder = new EthernetTypeBuilder();
//		ethTypeBuilder.setType(new EtherType(IPV4_LONG));
//		ethernetMatch.setEthernetType(ethTypeBuilder.build());
//		matchBuilder.setEthernetMatch(ethernetMatch.build());

		return matchBuilder;
    }
    
	/**
     * Create Ethernet Destination Match + EtherType ipv4
     *
     * @param matchBuilder MatchBuilder Object without a match yet
     * @param dMacAddr     String representing a destination MAC
     * @return matchBuilder Map MatchBuilder Object with a match
     */
    public static MatchBuilder createEthDstIpv4Match(MatchBuilder matchBuilder, MacAddress dMacAddr, MacAddress mask) {

        EthernetMatchBuilder ethernetMatch = new EthernetMatchBuilder();
        EthernetDestinationBuilder ethDestinationBuilder = new EthernetDestinationBuilder();
        ethDestinationBuilder.setAddress(new MacAddress(dMacAddr));
        if (mask != null) {
            ethDestinationBuilder.setMask(mask);
        }
        ethernetMatch.setEthernetDestination(ethDestinationBuilder.build());
        matchBuilder.setEthernetMatch(ethernetMatch.build());

        EthernetTypeBuilder ethTypeBuilder = new EthernetTypeBuilder();
        ethTypeBuilder.setType(new EtherType(IPV4_LONG));
        ethernetMatch.setEthernetType(ethTypeBuilder.build());
        matchBuilder.setEthernetMatch(ethernetMatch.build());
        
        return matchBuilder;
    }
    
    /**
     * Create Ethernet Source Match + EtherType ipv4
     *
     * @param matchBuilder MatchBuilder Object without a match yet
     * @param sMacAddr     String representing a source MAC
     * @return matchBuilder Map MatchBuilder Object with a match
     */
    public static MatchBuilder createEthSrcIpv4Match(MatchBuilder matchBuilder, MacAddress sMacAddr) {

        EthernetMatchBuilder ethernetMatch = new EthernetMatchBuilder();
        EthernetSourceBuilder ethSourceBuilder = new EthernetSourceBuilder();
        ethSourceBuilder.setAddress(new MacAddress(sMacAddr));
        ethernetMatch.setEthernetSource(ethSourceBuilder.build());
        matchBuilder.setEthernetMatch(ethernetMatch.build());

        EthernetTypeBuilder ethTypeBuilder = new EthernetTypeBuilder();
        ethTypeBuilder.setType(new EtherType(IPV4_LONG));
        ethernetMatch.setEthernetType(ethTypeBuilder.build());
        matchBuilder.setEthernetMatch(ethernetMatch.build());
        
        return matchBuilder;
    }
	
	/**
	 * Match LLDP code
	 *
	 * @param matchBuilder MatchBuilder Object without a match yet
	 * @return matchBuilder Map MatchBuilder Object with a match
	 */
	public static MatchBuilder createLLDPMatch(MatchBuilder matchBuilder) {

	    EthernetMatchBuilder eth = new EthernetMatchBuilder();
	    EthernetTypeBuilder ethTypeBuilder = new EthernetTypeBuilder();
	    ethTypeBuilder.setType(new EtherType(LLDP_LONG));
	    eth.setEthernetType(ethTypeBuilder.build());
	    matchBuilder.setEthernetMatch(eth.build());

	    return matchBuilder;
	}	
}
// NIaaS SMS END