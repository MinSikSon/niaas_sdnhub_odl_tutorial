package org.sdnhub.odl.tutorial.learningswitch.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SMS_Parser_IpAddr extends SMS_Parser{
	private static final int IP_ADDRESS_SIZE = 4;
//	private static int SRC_IP_START_POSITION;
//	private static int SRC_IP_END_POSITION;
//	private static int DST_IP_START_POSITION;
//	private static int DST_IP_END_POSITION;
//	private SMS_Parser_IpAddr(){
//	}
	
	/**
     * @param payload, stringEtherTypeHex
     * @return byteArray_SrcIp[IP_ADDRESS_SIZE]
     */
	public static byte[] get_byteArray_SrcIp(byte[] payload) {
		String stringEtherTypeHex = SMS_Parser_MacAddr.get_stringEtherTypeHex(payload);
		int LOG_TEST = 0; // SMS
		final Logger LOG = LoggerFactory.getLogger(SMS_Parser_IpAddr.class);
		byte[] byteArray_SrcIp = new byte[IP_ADDRESS_SIZE];
		if(stringEtherTypeHex.equals("0800")){ // IPv4
			if(LOG_TEST == 1) LOG.debug("  | srcIp: {}.{}.{}.{}", payload[26],payload[27],payload[28],payload[29]);
			for(int i = 0 ; i < IP_ADDRESS_SIZE ; i++){
				byteArray_SrcIp[i] = payload[26+i];
			}
		}else if(stringEtherTypeHex.equals("0806")){ // ARP
			if(LOG_TEST == 1) LOG.debug("  | srcIp: {}.{}.{}.{}", payload[28],payload[29],payload[30],payload[31]);
			for(int i = 0 ; i < IP_ADDRESS_SIZE ; i++){
				byteArray_SrcIp[i] = payload[28+i];
			}
		}else if(stringEtherTypeHex.equals("86dd")){ // IPv6
			if(LOG_TEST == 1) LOG.debug("  | srcIp: ?.?.?.?.?.?");
		}else if(stringEtherTypeHex.equals("88CC")){ // LLDP
			if(LOG_TEST == 1) LOG.debug("  | srcIp: ?.?.?.?");
		}else if(stringEtherTypeHex.equals("8808")){ // Ethernet flow control
			if(LOG_TEST == 1) LOG.debug("  | srcIp: ?.?.?.?");
		}else{
			if(LOG_TEST == 1) LOG.debug("  | srcIp: ?.?.?.?");
		}
		return byteArray_SrcIp;
	}
	
	/**
     * @param payload, stringEtherTypeHex
     * @return byteArray_DstIp[IP_ADDRESS_SIZE]
     */
	public static byte[] get_byteArray_DstIp(byte[] payload) {
		String stringEtherTypeHex = SMS_Parser_MacAddr.get_stringEtherTypeHex(payload);
		int LOG_TEST = 0; // SMS
		final Logger LOG = LoggerFactory.getLogger(SMS_Parser_IpAddr.class);
		byte[] byteArray_DstIp = new byte[IP_ADDRESS_SIZE];
		if(stringEtherTypeHex.equals("0800")){ // IPv4
			if(LOG_TEST == 1) LOG.debug("  | dstIp: {}.{}.{}.{}", payload[30],payload[31],payload[32],payload[33]);
			for(int i = 0 ; i < IP_ADDRESS_SIZE ; i++){
				byteArray_DstIp[i] = payload[30+i];
			}
		}else if(stringEtherTypeHex.equals("0806")){ // ARP
			if(LOG_TEST == 1) LOG.debug("  | dstIp: {}.{}.{}.{}", payload[38],payload[39],payload[40],payload[41]);
			for(int i = 0 ; i < IP_ADDRESS_SIZE ; i++){
				byteArray_DstIp[i] = payload[38+i];
			}
		}else if(stringEtherTypeHex.equals("86dd")){ // IPv6
			if(LOG_TEST == 1) LOG.debug("  | dstIp: ?.?.?.?.?.?");
		}else if(stringEtherTypeHex.equals("88CC")){ // LLDP
			if(LOG_TEST == 1) LOG.debug("  | dstIp: ?.?.?.?");
		}else if(stringEtherTypeHex.equals("8808")){ // Ethernet flow control
			if(LOG_TEST == 1) LOG.debug("  | dstIp: ?.?.?.?");
		}else{
			if(LOG_TEST == 1) LOG.debug("  | dstIp: ?.?.?.?");
		}
		return byteArray_DstIp;
	}
	
	/**
     * @param byteArray[IP_ADDRESS_SIZE]
     * @return intArray[IP_ADDRESS_SIZE]
     */
	public static int[] ipAddr_byteArray_to_intArray(byte[] byteArray){
		final Logger LOG = LoggerFactory.getLogger(SMS_Parser_IpAddr.class);
		int[] intArray = new int[IP_ADDRESS_SIZE];
		if(byteArray.length != IP_ADDRESS_SIZE){
			LOG.debug("(Error) ipAddr_byteArray_to_intArray");
			return null; // error
		}
		for(int i = 0 ; i < IP_ADDRESS_SIZE ; i++){
			intArray[i] = byte_to_int(byteArray[i]);
		}
		return intArray;
	}
	
	/**
     * @param intArray[IP_ADDRESS_SIZE]
     * @return byteArray[IP_ADDRESS_SIZE]
     */
	public static byte[] ipAddr_intArray_to_byteArray(int[] intArray){
		final Logger LOG = LoggerFactory.getLogger(SMS_Parser_IpAddr.class);
		byte[] byteArray = new byte[IP_ADDRESS_SIZE];
		if(intArray.length != IP_ADDRESS_SIZE) {
			LOG.debug("(Error) ipAddr_intArray_to_byteArray");
			return null; // error
		}
		for(int i = 0 ; i< IP_ADDRESS_SIZE ; i++){
			if(-128 > intArray[i] || intArray[i] > 255) {
				System.out.println("SMS_Parser_IpAddr------------------------------here 31");
				System.out.println("intArray["+i+"]: " + intArray[i]);
			}
		}
		for(int i = 0 ; i < IP_ADDRESS_SIZE ; i++){
			byteArray[i] = int_to_byte(intArray[i]);
		}
		return byteArray;
	}
	
	/**
     * @param stringIp
     * @return intArray[IP_ADDRESS_SIZE]
     */
	public static int[] ipAddr_stringIp_to_intArray(String stringIp){
		final Logger LOG = LoggerFactory.getLogger(SMS_Parser_IpAddr.class);
		int[] intArray = new int[IP_ADDRESS_SIZE];
		String[] split_stringValue = stringIp.split("\\.");
		// the number of "." is three.
		if(split_stringValue.length != IP_ADDRESS_SIZE){
			LOG.debug("(Error) ipAddr_byteArray_to_intArray");
			return null; // error
		}
		for(int i = 0 ; i < IP_ADDRESS_SIZE ; i ++){
			intArray[i] = Integer.parseInt(split_stringValue[i]);
		}
		return intArray;
	}
	
	/**
     * @param intArray[IP_ADDRESS_SIZE]
     * @return stringIp
     */
	public static String ipAddr_intArray_to_stringIp(int[] intArray){
		final Logger LOG = LoggerFactory.getLogger(SMS_Parser_IpAddr.class);
		String stringIp;
		if(intArray.length != IP_ADDRESS_SIZE) {
			LOG.debug("(Error) ipAddr_intArray_to_byteArray");
			return null; // error
		}
		stringIp = intArray[0] + "." + intArray[1] + "." + intArray[2] + "." + intArray[3];
		
		return stringIp;
	}
	
	/**
     * @param byteArray[IP_ADDRESS_SIZE]
     * @return stringIp
     */
	public static String ipAddr_byteArray_to_stringIp(byte[] byteArray) {
		String stringIp = "";
		for(int i = 0 ; i < byteArray.length ; i ++){
			stringIp = stringIp.concat(Integer.toString(0xff & byteArray[i]));
			if(i != (byteArray.length - 1)){
				stringIp = stringIp.concat(".");
			}
		}
		return stringIp;
	}
	
	/**
     * @param stringIp
     * @return byteArray[IP_ADDRESS_SIZE]
     */
	public static byte[] ipAddr_stringIp_to_byteArray(String stringIp) {
		final Logger LOG = LoggerFactory.getLogger(SMS_Parser_IpAddr.class);
		byte[] byteArray = new byte[6];
		String[] split_stringValue = stringIp.split("\\.");
		if(split_stringValue.length != 6){
			LOG.debug("(Error) ipAddr_byteArray_to_intArray");
			return null; // error
		}
		for(int i = 0 ; i < 6 ; i ++){
			byteArray[i] = int_to_byte(Integer.parseInt(split_stringValue[i]));
		}
		return byteArray;
	}
}
