/**
 * 
 */
package com.scr.util;

import java.util.Map;

import org.springframework.web.client.RestTemplate;

/**
 * @author vt1056
 *
 */
public class MobileNumberConfiguration {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MobileNumberConfiguration m = new MobileNumberConfiguration();
		System.out.println(m.getEmployees1());;

	}
	//@RequestMapping(value="/url",method=RequestMethod.GET)
	public String getEmployees1()
	{
	String phone="9398561285";
	String content="hi ramakrishna";

	final String uri = "http://smslogin.mobi/spanelv2/api.php?username=apsrtc&password=apsrtc@321&to="+phone+"&from=SRIJAY&message="+content+"";
	RestTemplate restTemplate = new RestTemplate();
	//restTemplate.get
	String result = restTemplate.getForObject(uri, String.class, Map.class);

	System.out.println(result);
	return "";
	}

}
