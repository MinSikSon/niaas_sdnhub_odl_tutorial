package org.sdnhub.odl.tutorial.learningswitch.impl;

import java.util.Arrays;

public abstract class Niaas_Parser_MacAddr extends Niaas_Parser{
	private static final int MAC_ADDRESS_SIZE = 6;
	private static final int DST_MAC_START_POSITION = 0;
	private static final int DST_MAC_END_POSITION = 6;
	private static final int SRC_MAC_START_POSITION = 6;
	private static final int SRC_MAC_END_POSITION = 12;
	private static final int ETHER_TYPE_START_POSITION = 12;
	private static final int ETHER_TYPE_END_POSITION = 14;
	private Niaas_Parser_MacAddr(){
	}
	
	/**
     * @param stringMac
     * @return intArray[MAC_ADDRESS_SIZE]
     */
//	public static void display_etherType(String stringEtherTypeHex) {
//		int LOG_TEST = 1; // SMS
//		final Logger LOG = LoggerFactory.getLogger(SMS_Parser_MacAddr.class);
//		if(LOG_TEST == 1) LOG.debug("  etherType(hex): {}", stringEtherTypeHex);
//		if(stringEtherTypeHex.equals("0800")){ // IPv4
//			if(LOG_TEST == 1) LOG.debug("  etherType(txt): IPv4");
//		}else if(stringEtherTypeHex.equals("0806")){ // ARP
//			if(LOG_TEST == 1) LOG.debug("  etherType(txt): ARP");
//		}else if(stringEtherTypeHex.equals("88CC")){ // LLDP
//			if(LOG_TEST == 1) LOG.debug("  etherType(txt): LLDP");
//		}else if(stringEtherTypeHex.equals("86dd")){ // IPv6
//			if(LOG_TEST == 1) LOG.debug("  etherType(txt): IPv6");
//		}else if(stringEtherTypeHex.equals("8808")){ // Ethernet flow control
//			if(LOG_TEST == 1) LOG.debug("  etherType(txt): Ethernet flow control");
//		}else{
//			if(LOG_TEST == 1) LOG.debug("  etherType(txt): ???");
//		}
//	}	
	
	/**
     * @param stringMac
     * @return intArray[MAC_ADDRESS_SIZE]
     */
	public static int[] macAddr_stringMac_to_intArray(String stringMac){
		byte[] byteArray = new byte[MAC_ADDRESS_SIZE];
		int[] intArray = new int[MAC_ADDRESS_SIZE];
		String[] stringValue_Length = stringMac.split(":");
		String split_stringValue = stringMac.replaceAll(":","");
		// the number of "." is three.
		if(stringValue_Length.length != MAC_ADDRESS_SIZE){
			System.out.println("(Error) macAddr_byteArray_to_intArray");
			return null; // error
		}
		byteArray = stringHex_to_byteArray(split_stringValue);
		for(int i = 0 ; i < MAC_ADDRESS_SIZE ; i++){
			intArray[i] = byte_to_int(byteArray[i]);
		}
		return intArray;
	}
	
	/**
     * @param intArray[MAC_ADDRESS_SIZE]
     * @return stringMac
     */
	public static String macAddr_intArray_to_string(int[] intArray){
		String stringMac;
		String[] stringArray = new String[MAC_ADDRESS_SIZE];
		byte[] byteArray = new byte[MAC_ADDRESS_SIZE];
		if(intArray.length != MAC_ADDRESS_SIZE) {
			System.out.println("(Error) macAddr_intArray_to_byteArray");
			return null; // error
		}
		for(int i = 0 ; i< MAC_ADDRESS_SIZE ; i++){
			if(-128 > intArray[i] || intArray[i] > 255) {
				System.out.println("SMS_Parser_MacAddr------------------------------here 73");
				System.out.println("intArray["+i+"]: " + intArray[i]);
			}
		}
		for(int i = 0 ; i < MAC_ADDRESS_SIZE ; i++){
			byteArray[i] = int_to_byte(intArray[i]);
		}
		stringMac = byteArray_to_stringHex(byteArray);
		for(int i = 0 ; i < MAC_ADDRESS_SIZE ; i++){
			stringArray[i] = stringMac.substring(2*i, 2*(i+1));
		}
		stringMac = stringArray[0] + ":" + stringArray[1] + ":" + stringArray[2] + ":" + stringArray[3] + ":" + stringArray[4] + ":" + stringArray[5];
		
		return stringMac;
	}
	
	/**
     * @param intArray[MAC_ADDRESS_SIZE]
     * @return byteArray[MAC_ADDRESS_SIZE]
     */
	public static byte[] macAddr_intArray_to_byteArray(int[] intArray){
		byte[] byteArray = new byte[MAC_ADDRESS_SIZE];
		if(intArray.length != MAC_ADDRESS_SIZE) {
			System.out.println("(Error) macAddr_intArray_to_byteArray");
			return null; // error
		}
		for(int i = 0 ; i< MAC_ADDRESS_SIZE ; i++){
			if(-128 > intArray[i] || intArray[i] > 255) {
				System.out.println("SMS_Parser_MacAddr------------------------------here 59");
				System.out.println("intArray["+i+"]: " + intArray[i]);
			}
		}
		for(int i = 0 ; i < MAC_ADDRESS_SIZE ; i++){
			byteArray[i] = int_to_byte(intArray[i]);
		}
		return byteArray;
	}
	
	/**
     * @param byteArray[MAC_ADDRESS_SIZE]
     * @return intArray[MAC_ADDRESS_SIZE]
     */
	public static int[] macAddr_byteArray_to_intArray(byte[] byteArray){
		int[] intArray = new int[MAC_ADDRESS_SIZE];
		if(byteArray.length != MAC_ADDRESS_SIZE){
			System.out.println("(Error) macAddr_byteArray_to_intArray");
			return null; // error
		}
		for(int i = 0 ; i < MAC_ADDRESS_SIZE ; i++){
			intArray[i] = byte_to_int(byteArray[i]);
		}
		return intArray;
	}
	
	/**
     * @param payload, etherTypeRaw
     * @return etherType_hex
     */
	public static String get_stringEtherTypeHex(byte[] payload) {
		byte[] etherTypeRaw = Arrays.copyOfRange(payload, ETHER_TYPE_START_POSITION, ETHER_TYPE_END_POSITION);
		String stringEtherTypeHex = "0";
		if(byteArray_to_stringHex(etherTypeRaw).equals("0800")){ // IPv4
			stringEtherTypeHex = "0800";
			return stringEtherTypeHex;
		}else if(byteArray_to_stringHex(etherTypeRaw).equals("0806")){ // ARP
			stringEtherTypeHex = "0806";
			return stringEtherTypeHex;
		}else if(byteArray_to_stringHex(etherTypeRaw).equals("86dd")){ // IPv6
			stringEtherTypeHex = "86dd";
			return stringEtherTypeHex;
		}else{
			stringEtherTypeHex = "0";
			return stringEtherTypeHex;
		}
	}
	
}
