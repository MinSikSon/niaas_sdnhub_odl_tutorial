package org.sdnhub.odl.tutorial.learningswitch.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SMS_Parser_Test extends SMS_Parser{
//	private static final int MAC_ADDRESS_SIZE = 6;
//	private static final int DST_MAC_START_POSITION = 0;
//	private static final int DST_MAC_END_POSITION = 6;
//	private static final int SRC_MAC_START_POSITION = 6;
//	private static final int SRC_MAC_END_POSITION = 12;
//    
//	private static final int IP_ADDRESS_SIZE = 4;
//	private static int SRC_IP_START_POSITION;
//	private static int SRC_IP_END_POSITION;
//	private static int DST_IP_START_POSITION;
//	private static int DST_IP_END_POSITION;
	
//	private SMS_Parser(){
//		// prohibite to instantiate this class
//	}
	
	//SMS: get_etherType()
	public static String get_etherType(byte[] payload, byte[] etherTypeRaw) {
		String etherType_hex = "0";
		if(SMS_Parser_Test.byteArray_to_Hex(etherTypeRaw).equals("0800")){ // IPv4
			etherType_hex = "0800";
			return etherType_hex;
		}else if(SMS_Parser_Test.byteArray_to_Hex(etherTypeRaw).equals("0806")){ // ARP
			etherType_hex = "0806";
			return etherType_hex;
		}else if(SMS_Parser_Test.byteArray_to_Hex(etherTypeRaw).equals("86dd")){ // IPv6
			etherType_hex = "86dd";
			return etherType_hex;
		}else{
			etherType_hex = "0";
			return etherType_hex;
		}
	}
	
	
	public static byte[] decimal_to_byteArray(String decimal) {
		String hex = Integer.toHexString(Integer.parseInt(decimal));
		System.out.print(hex);
		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0 ; i < ba.length; i++) {
			ba[i] = (byte) Integer.parseInt(hex.substring(2*i, 2*i + 2), 16);
		}
		return ba;
	}
	
	public static byte decimal_to_byte(String decimal) {
		String hex = Integer.toHexString(Integer.parseInt(decimal));
		System.out.print(hex);
		byte ba = (byte) Integer.parseInt(hex, 16);
		return ba;
	}
	
	public static String byte_to_decimal(byte bb) {
		String decimalNumber;
		decimalNumber = Integer.toString(0xff & bb);
		return decimalNumber;
	}
	
	public static String ip_byteArray_to_decimalString(byte[] bb) {
		String decimalNumber = "";
		for(int i = 0 ; i < bb.length ; i ++){
			decimalNumber = decimalNumber.concat(Integer.toString(0xff & bb[i]));
			if(i != (bb.length - 1)){
				decimalNumber = decimalNumber.concat(".");
			}
		}
		return decimalNumber;
	}
	
	
	
	public static byte[] mod_checksum_minus(byte[] checksum){
		int[] checksum_to_int = new int[2];
//		checksum[0] = 127;
//		checksum[1] = -1;
		checksum_to_int[0] = checksum[0] & 0xff;
		checksum_to_int[1] = checksum[1] & 0xff;
			
		if(checksum_to_int[1] < 7){
			checksum_to_int[0] -= 1;
			checksum_to_int[1] -= 7;
			checksum_to_int[1] += 256;
		}else{
			checksum_to_int[1] -= 7;
		}
//		System.out.println(checksum_to_int[0]);
//		System.out.println(checksum_to_int[1]);
		if(checksum_to_int[0] > 127){
			checksum_to_int[0] -= 256;
		}
		if(checksum_to_int[1] > 127){
			checksum_to_int[1] -= 256;
		}
		// set
		checksum[0] = (byte) checksum_to_int[0];
		checksum[1] = (byte) checksum_to_int[1];
		return checksum;
	}
	
	public static byte[] mod_checksum_plus(byte[] checksum){
		int[] checksum_to_int = new int[2];
//		checksum[0] = 127;
//		checksum[1] = -1;
		checksum_to_int[0] = checksum[0] & 0xff;
		checksum_to_int[1] = checksum[1] & 0xff;
			
		if(checksum_to_int[1] < 249){
			checksum_to_int[1] += 7;
		}else{
			checksum_to_int[0] += 1;
			checksum_to_int[1] += 7;
			checksum_to_int[1] -= 256;
		}
		
//		System.out.println(checksum_to_int[0]);
//		System.out.println(checksum_to_int[1]);
		if(checksum_to_int[0] > 127){
			checksum_to_int[0] -= 256;
		}
		if(checksum_to_int[1] > 127){
			checksum_to_int[1] -= 256;
		}
		// set
		checksum[0] = (byte) checksum_to_int[0];
		checksum[1] = (byte) checksum_to_int[1];
		return checksum;
	}
}
