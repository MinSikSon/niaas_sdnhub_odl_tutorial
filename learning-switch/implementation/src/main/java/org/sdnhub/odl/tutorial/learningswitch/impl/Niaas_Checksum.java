package org.sdnhub.odl.tutorial.learningswitch.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Niaas_Checksum extends Niaas_Parser_IpAddr{
	private Niaas_Checksum(){
	}
	/**
     * @param byteIpHeader
     * @return byteIpChecksumValue
     */
	public static byte[] ipv4_Calculate_Checksum(byte[] byteIpHeader){
		final Logger LOG = LoggerFactory.getLogger(Niaas_Checksum.class);
		int SMS_LOG = 0;
		// variable
		int[] intIpHeader = new int[20];
		int upperMask = 0x0000ff00;
		int lowerMask = 0x000000ff;
		int complementMask = 0x000000ff;
		int[] sumIpHeader = new int[2];
		byte[] byteIpChecksumValue = new byte[2];
		int carry_0;
		int carry_1;
		// store input checksum value. (122, 69)
		if(SMS_LOG == 1) LOG.debug("[before][byteIpHeader]:  {} {}", byteIpHeader[10], byteIpHeader[11]);
		byteIpChecksumValue[0] = byteIpHeader[10];
		byteIpChecksumValue[1] = byteIpHeader[11];
		if(SMS_LOG == 1) System.out.println("ipHeader size: " + byteIpHeader.length);
		if(byteIpHeader.length != 20){
			if(SMS_LOG == 1) System.out.println("Error: input size error");
			LOG.debug("Error: input size error");
			return byteIpChecksumValue;
		}
		// change checksum value to ZERO
		byteIpHeader[10] = 0;
		byteIpHeader[11] = 0;
		// change byte to int Value
		for(int i = 0 ; i < 20 ; i++){
			intIpHeader[i] = byte_to_int(byteIpHeader[i]);
		}
		
		carry_0 = 0;
		carry_1 = 0;
		// sum int Values
		for(int i = 0 ; i < 20 ; i++){
			if(i % 2 == 0){
				sumIpHeader[0] = sumIpHeader[0] + intIpHeader[i];
				carry_0 = (sumIpHeader[0] & upperMask) >> 8;
				sumIpHeader[0] = sumIpHeader[0] & lowerMask;
				sumIpHeader[1] = sumIpHeader[1] + carry_0;
			}else if(i % 2 == 1){
				sumIpHeader[1] = sumIpHeader[1] + intIpHeader[i];
				carry_1 = (sumIpHeader[1] & upperMask) >> 8;
				sumIpHeader[1] = sumIpHeader[1] & lowerMask;
				sumIpHeader[0] = sumIpHeader[0] + carry_1;
				if(sumIpHeader[0] >= 256){
					carry_0 = (sumIpHeader[0] & upperMask) >> 8;
					sumIpHeader[0] = sumIpHeader[0] & lowerMask;
					sumIpHeader[1] = sumIpHeader[1] + carry_0;
				}
			}
			carry_0 = 0;
			carry_1 = 0;
		}
		if(SMS_LOG == 1) System.out.println("sumIpHeader[0]:  " + sumIpHeader[0]);
		if(SMS_LOG == 1) System.out.println("sumIpHeader[1]:  " + sumIpHeader[1]);
		
		// 1's complement
		if(SMS_LOG == 1) System.out.println("<1's complement>");
		sumIpHeader[0] = sumIpHeader[0]^complementMask;
		sumIpHeader[1] = sumIpHeader[1]^complementMask;
		if(SMS_LOG == 1) System.out.println(sumIpHeader[0]);
		if(SMS_LOG == 1) System.out.println(sumIpHeader[1]);
		// change int to byte
		if(-128 > sumIpHeader[0] || sumIpHeader[0] > 255) {
			System.out.println("SMS_Parser_IpAddr------------------------------here 128");
			System.out.println("sumIpHeader[0]: " + sumIpHeader[0]);
		}
		if(-128 > sumIpHeader[1] || sumIpHeader[1] > 255) {
			System.out.println("SMS_Parser_IpAddr------------------------------here 128");
			System.out.println("sumIpHeader[1]: " + sumIpHeader[1]);
		}
		byteIpChecksumValue[0] = int_to_byte(sumIpHeader[0]);
		byteIpChecksumValue[1] = int_to_byte(sumIpHeader[1]);
		if(SMS_LOG == 1) System.out.println("byteChecksumValue[0]:  " + byteIpChecksumValue[0]);
		if(SMS_LOG == 1) System.out.println("byteChecksumValue[1]:  " + byteIpChecksumValue[1]);
		return byteIpChecksumValue;
	}
	
	/**
     * @param payload, srcIp, dstIp
     * @return byteTcpChecksumValue
     */
	public static byte[] tcp_Calculate_Checksum(byte[] payload, String srcIp, String dstIp){
		int SMS_LOG = 0;
		// TCP Header: 40byte // 20byte + 20byte(optional)
		// checksum: -123, -5 (byteTcpHeader[16], byteTcpHeader[17])	
		// variable
		byte[] byteTcpHeader = new byte[payload.length];
		byte[] bytePseudoHeaderSrcIp = new byte[4];
		byte[] bytePseudoHeaderDstIp = new byte[4];
		byte bytePseudoHeaderReserved = 0;
		byte bytePseudoHeaderProtocol = 6; // tcp = 6
//		byte bytePseudoHeaderTcpLenth = int_to_byte(byteTcpHeader.length);
		byte[] bytePseudoHeaderTcpLenth = new byte[2];
		int intPseudoHeaderTcpLenth;
		int[] intPseudoHeader = new int[12];
		int[] intTcpHeader = new int[payload.length];
		int upperMask = 0x0000ff00;
		int lowerMask = 0x000000ff;
		int complementMask = 0x000000ff;
		int[] sumTcpHeader = new int[2];
		int[] sumPseudoHeader = new int[2];
		byte[] byteTcpChecksumValue = new byte[2];
		int carry_0;
		int carry_1;
		
		//+++++++++++++++++++++++++++++++++
		int intIpHeaderLength = (byte_to_int(payload[14]) & 0x0f) * 4; // 
		if(SMS_LOG == 1) System.out.println("intIpHeaderLength: " + intIpHeaderLength);
		byte[] byteIpTotalHeaderLength = new byte[2];
		int intIpTotalHeaderLength;
		int intTcpPacketSize;
		byteIpTotalHeaderLength[0] = payload[16];
		byteIpTotalHeaderLength[1] = payload[17];
		intIpTotalHeaderLength = byte_to_int(payload[16]) * 256 + byte_to_int(payload[17]);
		if(SMS_LOG == 1) System.out.println(intIpTotalHeaderLength);
		intTcpPacketSize = intIpTotalHeaderLength - intIpHeaderLength;
		if(SMS_LOG == 1) System.out.println(intTcpPacketSize);
		//---------------------------------
		
		// extract byteTcpHeader from payload
		for(int i = 0 ; i < intTcpPacketSize ; i++){
			byteTcpHeader[i] = payload[34 + i]; //
//			LOG.debug("[byteTcpHeader[i]]:  {}", byteTcpHeader[i]);
		}
		
		// store checksum data
		byteTcpChecksumValue[0] = byteTcpHeader[16];
		byteTcpChecksumValue[1] = byteTcpHeader[17];
//		if(SMS_LOG == 1) System.out.println("tcpHeader size: " + (byte_to_int(byteTcpHeader[12]) / 4));
		if(SMS_LOG == 1) if(SMS_LOG == 1) System.out.println("intTcpPacketSize: " + intTcpPacketSize);
		// input pseudo Header data
		bytePseudoHeaderSrcIp = ipAddr_intArray_to_byteArray(ipAddr_stringIp_to_intArray(srcIp)); // 
		bytePseudoHeaderDstIp = ipAddr_intArray_to_byteArray(ipAddr_stringIp_to_intArray(dstIp)); // 
//		intPseudoHeaderTcpLenth = (byte_to_int(byteTcpHeader[12]) / 4);
		intPseudoHeaderTcpLenth = intTcpPacketSize;
		if(-128 > ((intPseudoHeaderTcpLenth & upperMask) >> 8) || ((intPseudoHeaderTcpLenth & upperMask) >> 8) > 255) {
			System.out.println("SMS_Parser_IpAddr------------------------------here 204");
			System.out.println("(intPseudoHeaderTcpLenth & upperMask) >> 8: " + ((intPseudoHeaderTcpLenth & upperMask) >> 8));
		}
		if(-128 > (intPseudoHeaderTcpLenth & lowerMask) || (intPseudoHeaderTcpLenth & lowerMask) > 255) {
			System.out.println("SMS_Parser_IpAddr------------------------------here 208");
			System.out.println("(intPseudoHeaderTcpLenth & lowerMask): " + (intPseudoHeaderTcpLenth & lowerMask));
		}
		bytePseudoHeaderTcpLenth[0] = int_to_byte((intPseudoHeaderTcpLenth & upperMask) >> 8);
		bytePseudoHeaderTcpLenth[1] = int_to_byte(intPseudoHeaderTcpLenth & lowerMask);
		// PseudoHead change byte to int
		intPseudoHeader[0] = byte_to_int(bytePseudoHeaderSrcIp[0]);
		intPseudoHeader[1] = byte_to_int(bytePseudoHeaderSrcIp[1]);
		intPseudoHeader[2] = byte_to_int(bytePseudoHeaderSrcIp[2]);
		intPseudoHeader[3] = byte_to_int(bytePseudoHeaderSrcIp[3]);
		intPseudoHeader[4] = byte_to_int(bytePseudoHeaderDstIp[0]);
		intPseudoHeader[5] = byte_to_int(bytePseudoHeaderDstIp[1]);
		intPseudoHeader[6] = byte_to_int(bytePseudoHeaderDstIp[2]);
		intPseudoHeader[7] = byte_to_int(bytePseudoHeaderDstIp[3]);
		intPseudoHeader[8] = byte_to_int(bytePseudoHeaderReserved);
		intPseudoHeader[9] = byte_to_int(bytePseudoHeaderProtocol);
		intPseudoHeader[10] = byte_to_int(bytePseudoHeaderTcpLenth[0]);
		intPseudoHeader[11] = byte_to_int(bytePseudoHeaderTcpLenth[1]);
		// sum Pseudo Header int Values
		carry_0 = 0;
		carry_1 = 0;
		for(int i = 0 ; i < 12 ; i++){
			if(i % 2 == 0){
				sumPseudoHeader[0] = sumPseudoHeader[0] + intPseudoHeader[i];
				carry_0 = (sumPseudoHeader[0] & upperMask) >> 8;
				sumPseudoHeader[0] = sumPseudoHeader[0] & lowerMask;
				sumPseudoHeader[1] = sumPseudoHeader[1] + carry_0;
			}else if(i % 2 == 1){
				sumPseudoHeader[1] = sumPseudoHeader[1] + intPseudoHeader[i];
				carry_1 = (sumPseudoHeader[1] & upperMask) >> 8;
				sumPseudoHeader[1] = sumPseudoHeader[1] & lowerMask;
				sumPseudoHeader[0] = sumPseudoHeader[0] + carry_1;
			}
//			System.out.println("carry_0: " + carry_0 +", carry_1: "+ carry_1 + ", sumPseudoHeader[0]: " + sumPseudoHeader[0] + ", sumPseudoHeader[1]: " + sumPseudoHeader[1]+ ", intPseudoHeader["+i+"]:" + intPseudoHeader[i]);
			carry_0 = 0;
			carry_1 = 0;
		}
//		if(SMS_LOG == 1) System.out.println("sumPseudoHeader[0]:  " + sumPseudoHeader[0]);
//		if(SMS_LOG == 1) System.out.println("sumPseudoHeader[1]:  " + sumPseudoHeader[1]);
		// add sumPseudoHeader to sumTcpHeader
		sumTcpHeader[0] = sumPseudoHeader[0];
		sumTcpHeader[1] = sumPseudoHeader[1];
		// change checksum value to ZERO
		byteTcpHeader[16] = 0;
		byteTcpHeader[17] = 0;
		// change byte to int Value
//		for(int i = 0 ; i < (byte_to_int(byteTcpHeader[12]) / 4) ; i++){
		for(int i = 0 ; i < intTcpPacketSize ; i++){
			intTcpHeader[i] = byte_to_int(byteTcpHeader[i]);
//			System.out.println("intTcpHeader[" + i + "]: " + intTcpHeader[i]);
		}

//////////////////////////// edit++++++++++++++++++++++++++++++++++++++
		// sum int Values
		carry_0 = 0;
		carry_1 = 0;
		if(SMS_LOG == 1) System.out.println("carry_0: " + carry_0 +", carry_1: "+ carry_1 + ", sumTcpHeader[0]: " + sumTcpHeader[0]  + ", sumTcpHeader[1]: " + sumTcpHeader[1]);
//		for(int i = 0 ; i < (byte_to_int(byteTcpHeader[12]) / 4) ; i++){
		for(int i = 0 ; i < intTcpPacketSize ; i++){
			if(i % 2 == 0){
				sumTcpHeader[0] = sumTcpHeader[0] + intTcpHeader[i];
				carry_0 = (sumTcpHeader[0] & upperMask) >> 8;
				sumTcpHeader[0] = sumTcpHeader[0] & lowerMask;
				sumTcpHeader[1] = sumTcpHeader[1] + carry_0;
			}else if(i % 2 == 1){
				sumTcpHeader[1] = sumTcpHeader[1] + intTcpHeader[i];
				carry_1 = (sumTcpHeader[1] & upperMask) >> 8;
				sumTcpHeader[1] = sumTcpHeader[1] & lowerMask;
				sumTcpHeader[0] = sumTcpHeader[0] + carry_1;
				///// +++ edit
				if(sumTcpHeader[0] >= 256){
					carry_0 = (sumTcpHeader[0] & upperMask) >> 8;
					sumTcpHeader[0] = sumTcpHeader[0] & lowerMask;
					sumTcpHeader[1] = sumTcpHeader[1] + carry_0;
				}
				///// --- edit
			}
			if(SMS_LOG == 1) System.out.println(i+")("+i%2 + ") carry_0: " + carry_0 +", carry_1: "+ carry_1 + ", sumTcpHeader[0]: " + sumTcpHeader[0]  + ", sumTcpHeader[1]: " + sumTcpHeader[1]+ ", intTcpHeader["+i+"]:" + intTcpHeader[i]);
			carry_0 = 0;
			carry_1 = 0;
		}
		if(SMS_LOG == 1) System.out.println("sumTcpHeader[0]:  " + sumTcpHeader[0]);
		if(SMS_LOG == 1) System.out.println("sumTcpHeader[1]:  " + sumTcpHeader[1]);
		
////////////////////////////edit--------------------------------------
		// 1's complement
		if(SMS_LOG == 1) System.out.println("<1's complement>");
		sumTcpHeader[0] = sumTcpHeader[0]^complementMask;
		sumTcpHeader[1] = sumTcpHeader[1]^complementMask;
		if(SMS_LOG == 1) System.out.println(sumTcpHeader[0]);
		if(SMS_LOG == 1) System.out.println(sumTcpHeader[1]);
		// change int to byte
		if(-128 > sumTcpHeader[0] || sumTcpHeader[0] > 255) {
			System.out.println("SMS_Parser_IpAddr------------------------------here 279");
			System.out.println("sumTcpHeader[0]: " + sumTcpHeader[0]);
		}
		if(-128 > sumTcpHeader[1] || sumTcpHeader[1] > 255) {
			System.out.println("SMS_Parser_IpAddr------------------------------here 279");
			System.out.println("sumTcpHeader[1]: " + sumTcpHeader[1]);
		}
		byteTcpChecksumValue[0] = int_to_byte(sumTcpHeader[0]);
		byteTcpChecksumValue[1] = int_to_byte(sumTcpHeader[1]);
		
		if(SMS_LOG == 1) System.out.println("byteChecksumValue[0]:  " + byteTcpChecksumValue[0]);
		if(SMS_LOG == 1) System.out.println("byteChecksumValue[1]:  " + byteTcpChecksumValue[1]);
		return byteTcpChecksumValue;
	}
}
