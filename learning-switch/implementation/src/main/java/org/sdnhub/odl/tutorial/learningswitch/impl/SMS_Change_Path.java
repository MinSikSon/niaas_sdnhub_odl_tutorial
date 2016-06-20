package org.sdnhub.odl.tutorial.learningswitch.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMS_Change_Path {
	private static final int IP_ADDRESS_SIZE = 4;
	private static final int MAC_ADDRESS_SIZE = 6;
	private static final int DST_MAC_START_POSITION = 0;
	private static final int DST_MAC_END_POSITION = 6;
	private static final int SRC_MAC_START_POSITION = 6;
	private static final int SRC_MAC_END_POSITION = 12;
	private SMS_Change_Path(){
	}
	
	/**
     * @param byte[] payload, String srcIp, String dstIp, String srcIp_cloud, String dstIp_fog, String srcMac_cloud, String dstMac_fog
     */
	public static void change_cloud_to_fog(byte[] payload, String srcIp_cloud, String dstIp_fog, String srcMac_cloud, String dstMac_fog){
		int LOG_TEST = 0; // SMS
		final Logger LOG = LoggerFactory.getLogger(SMS_Change_Path.class);
		if(LOG_TEST == 1) LOG.debug("+++++++++++++++++++++++++change_cloud_to_fog+++++++++++++++++++++++++");
		
		// ethertype
		String stringEtherTypeHex = SMS_Parser_MacAddr.get_stringEtherTypeHex(payload);
		if(LOG_TEST == 1) LOG.debug("etherType_hex: {}",stringEtherTypeHex);
		// ip
		byte[] byteArray_srcIp = SMS_Parser_IpAddr.get_byteArray_SrcIp(payload); // & display Addr 
		byte[] byteArray_dstIp = SMS_Parser_IpAddr.get_byteArray_DstIp(payload); // & display Addr
		String string_srcIp = SMS_Parser_IpAddr.ipAddr_byteArray_to_stringIp(byteArray_srcIp);
		String string_dstIp = SMS_Parser_IpAddr.ipAddr_byteArray_to_stringIp(byteArray_dstIp);
		if(LOG_TEST == 1) LOG.debug("srcIp:({}), dstIp:({})",string_srcIp, string_dstIp);
		
		// variable
		int SRC_IP_START_POSITION, SRC_IP_END_POSITION;
		int DST_IP_START_POSITION, DST_IP_END_POSITION;
		int changeIpchecksum = 0;
		int changeTcpchecksum = 0;
		// alreay known information
		// cloud-serverIP: 10.0.0.3
		// fog-serverIP: 10.0.0.10
		byte[] tmpSrcIP = new byte[4];
		byte[] tmpDstIP = new byte[4];
		byte[] tmpSrcMac = new byte[6];
		byte[] tmpDstMac = new byte[6];
		// ip,mac display tmp
		byte[] displayBeforeIP = new byte[4];
		byte[] displayAfterIP = new byte[4];
		byte[] displayBeforeMac = new byte[6];
		byte[] displayAfterMac = new byte[6];
		// checksum
		byte[] tmpIpChecksum = new byte[2];
		byte[] byteIpHeader = new byte[20];
		byte[] tmpTcpChecksum = new byte[2];
		
/***********************************************************************************************
 * This is not define yet.
 ***********************************************************************************************/
		if(dstIp_fog == null){
			return;
		}

/***********************************************************************************************
 * Main Code
 ***********************************************************************************************/
		// check: etherType
		if(stringEtherTypeHex.equals("0800")){ // IPv4
			SRC_IP_START_POSITION = 26;	
			SRC_IP_END_POSITION = 29;
			DST_IP_START_POSITION = 30;	
			DST_IP_END_POSITION = 33;
			changeIpchecksum = 1;
			changeTcpchecksum = 1;
//			return;
		}else if(stringEtherTypeHex.equals("0806")){ // ARP
			SRC_IP_START_POSITION = 28;	
			SRC_IP_END_POSITION = 31;
			DST_IP_START_POSITION = 38;	
			DST_IP_END_POSITION = 41;
			if(LOG_TEST == 1) LOG.debug("-------------------------change_cloud_to_fog-------------------------");
			return; ///////////////////////////////////////////////////////////////////////////////////////////// VVVVVVVVVVVVVVV
		}else{
			if(LOG_TEST == 1) LOG.debug("[Unkown_etherType_hex");
			if(LOG_TEST == 1) LOG.debug("-------------------------change_cloud_to_fog-------------------------");
			return;
		}
		
		// change
		if(string_dstIp.equals(srcIp_cloud)){ // 10.0.0.3
			if(LOG_TEST == 1) LOG.debug("[change_cloud_to_fog] ipDst.equals({})++++++++++++", srcIp_cloud);
			tmpDstIP = SMS_Parser_IpAddr.ipAddr_intArray_to_byteArray(SMS_Parser_IpAddr.ipAddr_stringIp_to_intArray(dstIp_fog));
			//string_to_byteArray;
			if(dstMac_fog == null) {
				if(LOG_TEST == 1) LOG.debug("-------------------------change_cloud_to_fog-------------------------");
				return;
			}
			if(LOG_TEST == 1) LOG.debug("[dstMac_cloud] {}",dstMac_fog);
			tmpDstMac = SMS_Parser_MacAddr.macAddr_intArray_to_byteArray(SMS_Parser_MacAddr.macAddr_stringMac_to_intArray(dstMac_fog)); // VV
			// change dstIp: 10.0.0.3 to 10.0.0.10
			for(int i = 0; i < IP_ADDRESS_SIZE ; i++){
				displayBeforeIP[i] = payload[DST_IP_START_POSITION + i];
//				LOG.debug("[before {}] {}", DST_IP_START_POSITION + i, payload[DST_IP_START_POSITION + i]);
				payload[DST_IP_START_POSITION + i] = tmpDstIP[i];
				displayAfterIP[i] = payload[DST_IP_START_POSITION + i];
//				LOG.debug("[after  {}] {}", DST_IP_START_POSITION + i, payload[DST_IP_START_POSITION + i]);
			}
			if(LOG_TEST == 1) LOG.debug("[beforeIP] {}.{}.{}.{} [afterIP ] --> {}.{}.{}.{}", 
					displayBeforeIP[0], displayBeforeIP[1], displayBeforeIP[2], displayBeforeIP[3], 
					displayAfterIP[0], displayAfterIP[1], displayAfterIP[2], displayAfterIP[3]);
			// change Ip Checksum
			if(LOG_TEST == 1) LOG.debug("ipv4_Calculate_Checksum++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			if(changeIpchecksum == 1){
				if(LOG_TEST == 1) LOG.debug("[before] IPchecksum: {} {}", payload[SRC_IP_START_POSITION-2], payload[SRC_IP_START_POSITION-1]);
				for(int i = 0 ; i < 20 ; i++){
					byteIpHeader[i] = payload[14 + i];
				}
//				tmpIpChecksum = SMS_Parser_IpAddr.ipv4_Calculate_Checksum(byteIpHeader);
				tmpIpChecksum = SMS_Checksum.ipv4_Calculate_Checksum(byteIpHeader);
				
				payload[SRC_IP_START_POSITION-2] = tmpIpChecksum[0];
				payload[SRC_IP_START_POSITION-1] = tmpIpChecksum[1];
				if(LOG_TEST == 1) LOG.debug("[after ] IPchecksum: {} {}", payload[SRC_IP_START_POSITION-2], payload[SRC_IP_START_POSITION-1]);
			}
			if(LOG_TEST == 1) LOG.debug("ipv4_Calculate_Checksum------------------------------------------------------------");
			
			
			// change Tcp Checksum
			if(LOG_TEST == 1) LOG.debug("tcp_Calculate_Checksum++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			if(changeTcpchecksum == 1 && (payload.length > 22) && (payload[DST_IP_END_POSITION+2] != 0)){
				if(LOG_TEST == 1) LOG.debug("[before] Tcpchecksum: {} {}", payload[DST_IP_END_POSITION+17], payload[DST_IP_END_POSITION+18]);
				if(LOG_TEST == 1) LOG.debug("payload[46]:  {}", payload[46]);
//				tmpTcpChecksum = SMS_Parser_IpAddr.tcp_Calculate_Checksum(payload, srcIp, dstIp_fog);
				tmpTcpChecksum = SMS_Checksum.tcp_Calculate_Checksum(payload, string_srcIp, dstIp_fog);
				payload[50] = tmpTcpChecksum[0];
				payload[51] = tmpTcpChecksum[1];
				if(LOG_TEST == 1) LOG.debug("[after ] Tcpchecksum: {} {}", payload[DST_IP_END_POSITION+17], payload[DST_IP_END_POSITION+18]);
			}
			if(LOG_TEST == 1) LOG.debug("tcp_Calculate_Checksum------------------------------------------------------------");
			
			if(LOG_TEST == 1) LOG.debug("------");
			// change dstMac: 10.0.0.3's Mac to 10.0.0.10's Mac
			for(int i = 0; i < MAC_ADDRESS_SIZE ; i++){
				displayBeforeMac[i] = payload[DST_MAC_START_POSITION + i];
				payload[DST_MAC_START_POSITION + i] = tmpDstMac[i];
				displayAfterMac[i] = payload[DST_MAC_START_POSITION + i];
			}
			if(LOG_TEST == 1) LOG.debug("[beforeMac] {}:{}:{}:{}:{}:{} --> [afterMac ] {}:{}:{}:{}:{}:{}",
					displayBeforeMac[0],displayBeforeMac[1],displayBeforeMac[2],
					displayBeforeMac[3],displayBeforeMac[4],displayBeforeMac[5],
					displayAfterMac[0],displayAfterMac[1],displayAfterMac[2],
					displayAfterMac[3],displayAfterMac[4],displayAfterMac[5]);
			if(LOG_TEST == 1) LOG.debug("[change_cloud_to_fog] ipDst.equals({})------------",srcIp_cloud);
			if(LOG_TEST == 1) LOG.debug("-------------------------change_cloud_to_fog-------------------------");
//			return;
		}
		
		if(string_srcIp.equals(dstIp_fog)){ // 10.0.0.10
			if(LOG_TEST == 1) LOG.debug("[change_cloud_to_fog] ipSrc.equals({})++++++++++++", dstIp_fog);
			tmpSrcIP = SMS_Parser_IpAddr.ipAddr_intArray_to_byteArray(SMS_Parser_IpAddr.ipAddr_stringIp_to_intArray(srcIp_cloud));
			//string_to_byteArray;
			if(srcMac_cloud == null) return;
			if(LOG_TEST == 1) LOG.debug("[srcMac_fog] {}",srcMac_cloud);
			tmpSrcMac = SMS_Parser_MacAddr.macAddr_intArray_to_byteArray(SMS_Parser_MacAddr.macAddr_stringMac_to_intArray(srcMac_cloud)); // VV
			// change srcIP: 10.0.0.10 to 10.0.0.3
			for(int i = 0; i < IP_ADDRESS_SIZE ; i++){
				displayBeforeIP[i] = payload[SRC_IP_START_POSITION + i];
//				LOG.debug("[before {}] {}", SRC_IP_START_POSITION + i, payload[SRC_IP_START_POSITION + i]);
				payload[SRC_IP_START_POSITION + i] = tmpSrcIP[i];
				displayAfterIP[i] = payload[SRC_IP_START_POSITION + i];
//				LOG.debug("[after  {}] {}", SRC_IP_START_POSITION + i, payload[SRC_IP_START_POSITION + i]);
			}
			if(LOG_TEST == 1) LOG.debug("[beforeIP] {}.{}.{}.{} [afterIP ] --> {}.{}.{}.{}", 
					displayBeforeIP[0], displayBeforeIP[1], displayBeforeIP[2], displayBeforeIP[3], 
					displayAfterIP[0], displayAfterIP[1], displayAfterIP[2], displayAfterIP[3]);
			// change Ip Checksum
			if(LOG_TEST == 1) LOG.debug("ipv4_Calculate_Checksum++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			if(changeIpchecksum == 1){
				if(LOG_TEST == 1) LOG.debug("[before] IPchecksum: {} {}", payload[SRC_IP_START_POSITION-2], payload[SRC_IP_START_POSITION-1]);
				for(int i = 0 ; i < 20 ; i++){
					byteIpHeader[i] = payload[14 + i];
				}
//				tmpIpChecksum = SMS_Parser_IpAddr.ipv4_Calculate_Checksum(byteIpHeader);
				tmpIpChecksum = SMS_Checksum.ipv4_Calculate_Checksum(byteIpHeader);
				payload[SRC_IP_START_POSITION-2] = tmpIpChecksum[0];
				payload[SRC_IP_START_POSITION-1] = tmpIpChecksum[1];
				if(LOG_TEST == 1) LOG.debug("[after ] IPchecksum: {} {}", payload[SRC_IP_START_POSITION-2], payload[SRC_IP_START_POSITION-1]);
			}
			if(LOG_TEST == 1) LOG.debug("ipv4_Calculate_Checksum------------------------------------------------------------");
			
			// change Tcp Checksum
			if(LOG_TEST == 1) LOG.debug("tcp_Calculate_Checksum++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			if(changeTcpchecksum == 1 && (payload.length > 22) && (payload[DST_IP_END_POSITION+2] != 0)){
				if(LOG_TEST == 1) LOG.debug("[before] Tcpchecksum: {} {}", payload[DST_IP_END_POSITION+17], payload[DST_IP_END_POSITION+18]);
				if(LOG_TEST == 1) LOG.debug("payload[46]:  {}", payload[46]);
//				tmpTcpChecksum = SMS_Parser_IpAddr.tcp_Calculate_Checksum(payload, srcIp_cloud, dstIp);
				tmpTcpChecksum = SMS_Checksum.tcp_Calculate_Checksum(payload, srcIp_cloud, string_dstIp);
				payload[50] = tmpTcpChecksum[0];
				payload[51] = tmpTcpChecksum[1];
				if(LOG_TEST == 1) LOG.debug("[after ] Tcpchecksum: {} {}", payload[DST_IP_END_POSITION+17], payload[DST_IP_END_POSITION+18]);
			}
			if(LOG_TEST == 1) LOG.debug("tcp_Calculate_Checksum------------------------------------------------------------");
			
			if(LOG_TEST == 1) LOG.debug("------");
			// change srcMac: 10.0.0.10'Mac to 10.0.0.3's Mac
			for(int i = 0; i < MAC_ADDRESS_SIZE ; i++){
				displayBeforeMac[i] = payload[SRC_MAC_START_POSITION + i];
				payload[SRC_MAC_START_POSITION + i] = tmpSrcMac[i];
				displayAfterMac[i] = payload[SRC_MAC_START_POSITION + i];
			}
			if(LOG_TEST == 1) LOG.debug("[beforeMac] {}:{}:{}:{}:{}:{} --> [afterMac ] {}:{}:{}:{}:{}:{}",
					displayBeforeMac[0],displayBeforeMac[1],displayBeforeMac[2],
					displayBeforeMac[3],displayBeforeMac[4],displayBeforeMac[5],
					displayAfterMac[0],displayAfterMac[1],displayAfterMac[2],
					displayAfterMac[3],displayAfterMac[4],displayAfterMac[5]);
			if(LOG_TEST == 1) LOG.debug("[change_cloud_to_fog] ipSrc.equals({})------------", dstIp_fog);
			if(LOG_TEST == 1) LOG.debug("-------------------------change_cloud_to_fog-------------------------");
			if(LOG_TEST == 1) LOG.debug("[beforeMac] {}:{}:{}:{}:{}:{} --> [afterMac ] {}:{}:{}:{}:{}:{}",
					displayBeforeMac[0],displayBeforeMac[1],displayBeforeMac[2],
					displayBeforeMac[3],displayBeforeMac[4],displayBeforeMac[5],
					displayAfterMac[0],displayAfterMac[1],displayAfterMac[2],
					displayAfterMac[3],displayAfterMac[4],displayAfterMac[5]);
			if(LOG_TEST == 1) LOG.debug("[change_cloud_to_fog] ipSrc.equals({})------------", dstIp_fog);
			if(LOG_TEST == 1) LOG.debug("-------------------------change_cloud_to_fog-------------------------");
//			return;
		}
	}
}
