package org.sdnhub.odl.tutorial.learningswitch.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMS_Display {
	private SMS_Display(){
		
	}
//SMS: display_srcIp()	
	public static byte[] display_srcIp(byte[] payload, String etherType_hex) {
		int LOG_TEST = 0; // SMS
		final Logger LOG = LoggerFactory.getLogger(SMS_Display.class);
		byte[] tmpIP = new byte[4];
		if(etherType_hex.equals("0800")){ // IPv4
			if(LOG_TEST == 1) LOG.debug("  | srcIp: {}.{}.{}.{}", payload[26],payload[27],payload[28],payload[29]);
			for(int i = 0 ; i < 4 ; i++){
				tmpIP[i] = payload[26+i];
			}
		}else if(etherType_hex.equals("0806")){ // ARP
			if(LOG_TEST == 1) LOG.debug("  | srcIp: {}.{}.{}.{}", payload[28],payload[29],payload[30],payload[31]);
			for(int i = 0 ; i < 4 ; i++){
				tmpIP[i] = payload[28+i];
			}
		}else if(etherType_hex.equals("86dd")){ // IPv6
			if(LOG_TEST == 1) LOG.debug("  | srcIp: ?.?.?.?.?.?");
		}else if(etherType_hex.equals("88CC")){ // LLDP
			if(LOG_TEST == 1) LOG.debug("  | srcIp: ?.?.?.?");
		}else if(etherType_hex.equals("8808")){ // Ethernet flow control
			if(LOG_TEST == 1) LOG.debug("  | srcIp: ?.?.?.?");
		}else{
			if(LOG_TEST == 1) LOG.debug("  | srcIp: ?.?.?.?");
		}
		return tmpIP;
	}
	
	//SMS: display_dstIp()
	public static byte[] display_dstIp(byte[] payload, String etherType_hex) {
		int LOG_TEST = 0; // SMS
		final Logger LOG = LoggerFactory.getLogger(SMS_Display.class);
		byte[] tmpIP = new byte[4];
		if(etherType_hex.equals("0800")){ // IPv4
			if(LOG_TEST == 1) LOG.debug("  | dstIp: {}.{}.{}.{}", payload[30],payload[31],payload[32],payload[33]);
			for(int i = 0 ; i < 4 ; i++){
				tmpIP[i] = payload[30+i];
			}
		}else if(etherType_hex.equals("0806")){ // ARP
			if(LOG_TEST == 1) LOG.debug("  | dstIp: {}.{}.{}.{}", payload[38],payload[39],payload[40],payload[41]);
			for(int i = 0 ; i < 4 ; i++){
				tmpIP[i] = payload[38+i];
			}
		}else if(etherType_hex.equals("86dd")){ // IPv6
			if(LOG_TEST == 1) LOG.debug("  | dstIp: ?.?.?.?.?.?");
		}else if(etherType_hex.equals("88CC")){ // LLDP
			if(LOG_TEST == 1) LOG.debug("  | dstIp: ?.?.?.?");
		}else if(etherType_hex.equals("8808")){ // Ethernet flow control
			if(LOG_TEST == 1) LOG.debug("  | dstIp: ?.?.?.?");
		}else{
			if(LOG_TEST == 1) LOG.debug("  | dstIp: ?.?.?.?");
		}
		return tmpIP;
	}

	//SMS: display_etherType()
	public static void display_etherType(String etherType_hex) {
		int LOG_TEST = 0; // SMS
		final Logger LOG = LoggerFactory.getLogger(SMS_Display.class);
		if(LOG_TEST == 1) LOG.debug("  etherType(hex): {}", etherType_hex);
		if(etherType_hex.equals("0800")){ // IPv4
			if(LOG_TEST == 1) LOG.debug("  etherType(txt): IPv4");
		}else if(etherType_hex.equals("0806")){ // ARP
			if(LOG_TEST == 1) LOG.debug("  etherType(txt): ARP");
		}else if(etherType_hex.equals("88CC")){ // LLDP
			if(LOG_TEST == 1) LOG.debug("  etherType(txt): LLDP");
		}else if(etherType_hex.equals("86dd")){ // IPv6
			if(LOG_TEST == 1) LOG.debug("  etherType(txt): IPv6");
		}else if(etherType_hex.equals("8808")){ // Ethernet flow control
			if(LOG_TEST == 1) LOG.debug("  etherType(txt): Ethernet flow control");
		}else{
			if(LOG_TEST == 1) LOG.debug("  etherType(txt): ???");
		}
	}	
}
