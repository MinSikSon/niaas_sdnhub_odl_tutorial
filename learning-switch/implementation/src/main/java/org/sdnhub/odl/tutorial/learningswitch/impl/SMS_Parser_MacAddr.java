package org.sdnhub.odl.tutorial.learningswitch.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMS_Parser_MacAddr extends SMS_Parser{
	private SMS_Parser_MacAddr(){
		
	}
	public static int[] macAddr_string_to_intArray(String stringValue){
		byte[] byteArray = new byte[6];
		int[] intArray = new int[6];
		String[] stringValue_Length = stringValue.split(":");
		String split_stringValue = stringValue.replaceAll(":","");
		// the number of "." is three.
		if(stringValue_Length.length != 6){
			System.out.println("(Error) macAddr_byteArray_to_intArray");
			return null; // error
		}
		byteArray = hex_to_ByteArray(split_stringValue);
		for(int i = 0 ; i < 6 ; i++){
			intArray[i] = byte_to_int(byteArray[i]);
		}
		return intArray;
	}
	public static String macAddr_intArray_to_string(int[] intArray){
		String stringValue;
		String[] stringArray = new String[6];
		byte[] byteArray = new byte[6];
		if(intArray.length != 6) {
			System.out.println("(Error) macAddr_intArray_to_byteArray");
			return null; // error
		}
		for(int i = 0 ; i< 6 ; i++){
			if(-128 > intArray[i] || intArray[i] > 255) {
				System.out.println("SMS_Parser_MacAddr------------------------------here 36");
				System.out.println("intArray["+i+"]: " + intArray[i]);
			}
		}
		for(int i = 0 ; i < 6 ; i++){
			byteArray[i] = int_to_byte(intArray[i]);
		}
		stringValue = byteArray_to_Hex(byteArray);
		for(int i = 0 ; i < 6 ; i++){
			stringArray[i] = stringValue.substring(2*i, 2*(i+1));
		}
		stringValue = stringArray[0] + ":" + stringArray[1] + ":" + stringArray[2] + ":" + stringArray[3] + ":" + stringArray[4] + ":" + stringArray[5];
		
		return stringValue;
	}
	public static byte[] macAddr_intArray_to_byteArray(int[] intArray){
		byte[] byteArray = new byte[6];
		if(intArray.length != 6) {
			System.out.println("(Error) macAddr_intArray_to_byteArray");
			return null; // error
		}
		for(int i = 0 ; i< 6 ; i++){
			if(-128 > intArray[i] || intArray[i] > 255) {
				System.out.println("SMS_Parser_MacAddr------------------------------here 59");
				System.out.println("intArray["+i+"]: " + intArray[i]);
			}
		}
		for(int i = 0 ; i < 6 ; i++){
			byteArray[i] = int_to_byte(intArray[i]);
		}
		return byteArray;
	}
	public static int[] macAddr_byteArray_to_intArray(byte[] byteArray){
		int[] intArray = new int[6];
		if(byteArray.length != 6){
			System.out.println("(Error) macAddr_byteArray_to_intArray");
			return null; // error
		}
		for(int i = 0 ; i < 6 ; i++){
			intArray[i] = byte_to_int(byteArray[i]);
		}
		return intArray;
	}
}
