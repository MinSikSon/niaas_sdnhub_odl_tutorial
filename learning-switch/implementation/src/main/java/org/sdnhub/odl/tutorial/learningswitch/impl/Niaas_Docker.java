/*
 * Name: SMS_Docker
 * 
 * Ver: 1.0
 *  
 * Data: 2016-0602
 */

package org.sdnhub.odl.tutorial.learningswitch.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Niaas_Docker {
	/**
	 * FOG_SERVER에서 docker container가 동작중인지 확인하고, 해당 ip를 ArrayList.
	 * 
	 * @parameter: ArrayList<String> fogServerDockerList
	 * @return: void
	 */
//	private static ArrayList<String> fogServerDockerList = new ArrayList<String>();
	public static void addFogServerIpToList(ArrayList<String> fogServer_Ip_List){
//		final Logger LOG = LoggerFactory.getLogger(SMS_Docker.class);
//		LOG.debug("addFogServerIpToList()++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//		int exist = 0;
		try {
			Process process = Runtime.getRuntime().exec("/home/sms/workspace/SDNHub_Opendaylight_Tutorial/admin/GET_DOCKER_STATUS.sh "
					+ "/home/sms/workspace/SDNHub_Opendaylight_Tutorial/admin/DOCKER_SWITCH_IP.txt");
			
			process.getErrorStream().close();
			process.getInputStream().close();
			process.getOutputStream().close();
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("/home/sms/workspace/SDNHub_Opendaylight_Tutorial/admin/DOCKER_SWITCH_IP.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // no information
		String value1 = "";
		String value2 = "";
		String value3 = "";
		String ip = "";
		int[] ipAddr = new int[4];
		
		/*-
		 * $ docker -ps --format "table {{.Name}}@{{.Ports}}"
		 * 위의 docker 명령어를 사용하였을 때, 출력되는 데이터 중에 필요한 정보만 자르고 저장하는 과정.
		 */
		while (true) {
			String line = null;
			try {
				line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (line == null)
				break;
			StringTokenizer tokens1 = new StringTokenizer(line, "@");
//			System.out.println("[token1 count] " + tokens1.countTokens());
			while(true){
				value1 = tokens1.nextToken();
//				System.out.println("[tokens1 value] " + value1);
				
				StringTokenizer tokens2 = new StringTokenizer(value1, ", ");
//				if(tokens2.countTokens() > 2){
//					System.out.println("<");
//					System.out.println("[token2 count] " + tokens2.countTokens());
					while(true){
						value2 = tokens2.nextToken();
//						System.out.println("[tokens2 value] " + value2);
						
						StringTokenizer tokens3 = new StringTokenizer(value2, ".");
						if(tokens3.countTokens() >= 4){
//							System.out.println("<<");
//							System.out.println("[token3 count] " + tokens3.countTokens());
							int i = 0;
							while(true){
								value3 = tokens3.nextToken();
//								System.out.println("[tokens3 value] " + value3);
								if(i != 3){
									ipAddr[i] = Integer.parseInt(value3);
								}else if(i == 3){
									StringTokenizer tokens4 = new StringTokenizer(value3, ":");
									ipAddr[i] = Integer.parseInt(tokens4.nextToken());
								}
								i++;
								if(tokens3.countTokens() == 0) break;
							}
							/*-
							 *  ip주소가 FOG_SERVER ip list와 겹치는지 검사 후, 겹치지 않으면 삽입.
							 */
							ip = ipAddr[0] + "." + ipAddr[1] + "." + ipAddr[2] + "." + ipAddr[3];
							boolean check = checkFogServerIpExistInArray(fogServer_Ip_List, ip);
							if(check == false) fogServer_Ip_List.add(ip);
//							System.out.println(ip);
//							System.out.println(">>");
						}
						if(tokens2.countTokens() == 0) break;
					}
//					System.out.println(">");
//				}
				if(tokens1.countTokens() == 0) break;
			}
//			System.out.println("--------------------------");
		}
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		return exist;
//		LOG.debug("addFogServerIpToList()------------------------------------------------------------");
	}
	
	/**
	 * addFogServerIpToList에서 사용. ArrayList에 fogServerIp에 해당하는 값이 존재하는지 확인.
	 * 
	 * @parameter: ArrayList<String> fogServerDockerList,
	 * @parameter: String fogServerIp,
	 * @return: boolean
	 */
	public static boolean checkFogServerIpExistInArray(ArrayList<String> fogServer_Ip_List, String fogServerIp){
		boolean check = fogServer_Ip_List.contains(fogServerIp);
		return check;
	}
	
	
	/**
	* CLOUD_SERVER ip를 확인해고, 해당 ip를 ArrayList에 저장.
	* 
	* @parameter: ArrayList<String> cloudServer_Ip_List
	* @return: void
	*/
	public static void addCloudServerIpToList(ArrayList<String> cloudServer_Ip_List){
//		final Logger LOG = LoggerFactory.getLogger(SMS_Docker.class);
//		LOG.debug("addFogServerOvsIdToList()++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("/home/sms/workspace/SDNHub_Opendaylight_Tutorial/admin/CLOUD_SERVER_IP.txt"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(true){
			String line = null;
			try {
				line = br.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (line == null)
				break;
			boolean check = checkCloudServerIpExistInArray(cloudServer_Ip_List, line);
			if(check == false) cloudServer_Ip_List.add(line);
		}
//		for(int i = 0 ; i < fogServerOvsIdList.size() ; i++){
//			System.out.println(fogServerOvsIdList.get(i));
//		}
//		LOG.debug("addFogServerOvsIdToList()------------------------------------------------------------");
	}
	
	/**
	* addCloudServerIpToList()에서 사용. ArrayList에 cloudServerIp에 해당하는 값이 존재하는지 확인.
	* 
	* @parameter: ArrayList<String> cloudServer_Ip_List, 
	* @parameter: String cloudServerIp, 
	* @return: boolean
	*/
	public static boolean checkCloudServerIpExistInArray(ArrayList<String> cloudServer_Ip_List, String cloudServerIp) {
		boolean check = cloudServer_Ip_List.contains(cloudServerIp);
		return check;
	}
	
	
	///**
	//* FOG_SERVER가 동작할 ovs의 id를 확인해고, 해당 id를 ArrayList에 저장.
	//* 
	//* @parameter: ArrayList<String> fogServerOvsIdList
	//* @return: void
	//*/
	//public static void addFogServerOvsIdToList(ArrayList<String> fogServerOvsIdList){
////		final Logger LOG = LoggerFactory.getLogger(SMS_Docker.class);
////		LOG.debug("addFogServerOvsIdToList()++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//		BufferedReader br = null;
//		try {
//			br = new BufferedReader(new FileReader("/home/sms/workspace/SDNHub_Opendaylight_Tutorial/admin/FOG_SERVER_OVS_ID.txt"));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		while(true){
//			String line = null;
//			try {
//				line = br.readLine();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			if (line == null)
//				break;
//			boolean check = checkFogServerIpExistInArray(fogServerOvsIdList, line);
//			
//			if(check == false) fogServerOvsIdList.add(line);
//		}
////		for(int i = 0 ; i < fogServerOvsIdList.size() ; i++){
////			System.out.println(fogServerOvsIdList.get(i));
////		}
////		LOG.debug("addFogServerOvsIdToList()------------------------------------------------------------");
	//}
	//
	///**
	//* addFogServerOvsIdToList()에서 사용. ArrayList에 fogServerOvsId에 해당하는 값이 존재하는지 확인.
	//* 
	//* @parameter: ArrayList<String> fogServerOvsIdList, 
	//* @parameter: String fogServerOvsId, 
	//* @return: boolean
	//*/
	//public static boolean checkFogServerOvsIdExistInArray(ArrayList<String> fogServerOvsIdList, String fogServerOvsId) {
//		boolean check = fogServerOvsIdList.contains(fogServerOvsId);
//		return check;
	//}
}






