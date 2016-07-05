package org.sdnhub.odl.tutorial.learningswitch.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Niaas_Parser {
	
	/**
     * @param intValue
     * @return byteValue
     */
	public static byte int_to_byte(int intValue){
		final Logger LOG = LoggerFactory.getLogger(Niaas_Parser.class);
		if(-128 <= intValue && intValue <= 255) {
			byte byteValue = (byte)intValue;
			return byteValue;
		}else {
			LOG.debug("(Error) int_to_byte  (intValue: {})", intValue);
			return 0; // error
		}
	}
	
	/**
     * @param byteValue
     * @return intValue
     */
	public static int byte_to_int(byte byteValue){
		int intValue = byteValue & 0xff;
		return intValue;
	}
	
	/**
     * @param stringHex
     * @return byteArray
     */
	public static byte[] stringHex_to_byteArray(String stringHex) {
		if (stringHex == null || stringHex.length() == 0){
			return null;
		}
		byte[] byteArray = new byte[stringHex.length() / 2];
		for (int i = 0 ; i < byteArray.length; i++) {
			byteArray[i] = (byte) Integer.parseInt(stringHex.substring(2*i, 2*i + 2), 16);
		}
		return byteArray;
	}
	
	/**
     * @param byteArray
     * @return stringHex
     */
	public static String byteArray_to_stringHex(byte[] ba) {
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
