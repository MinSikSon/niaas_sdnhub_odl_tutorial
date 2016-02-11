package org.sdnhub.odl.tutorial.learningswitch.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SMS_Parser {
	
	public static byte int_to_byte(int intValue){
		final Logger LOG = LoggerFactory.getLogger(SMS_Parser.class);
		if(-128 <= intValue && intValue <= 255) {
			return (byte)intValue;
		}else {
			LOG.debug("(Error) int_to_byte  (intValue: {})", intValue);
			return 0; // error
		}
	}
	public static int byte_to_int(byte byteValue){
		int intValue = byteValue & 0xff;
		return intValue;
	}
	public static byte[] hex_to_ByteArray(String hex) {
		if (hex == null || hex.length() == 0){
			return null;
		}
		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0 ; i < ba.length; i++) {
			ba[i] = (byte) Integer.parseInt(hex.substring(2*i, 2*i + 2), 16);
		}
		return ba;
	}
	public static String byteArray_to_Hex(byte[] ba) {
		if (ba == null || ba.length == 0){
			return null;
		}
		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;
		for (int x = 0 ; x < ba.length ; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
			sb.append(hexNumber.substring(hexNumber.length() - 2 ));
		}
		return sb.toString();
	}
	
	
}
