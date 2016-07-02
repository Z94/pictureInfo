package com.pictureCompare;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {
	  //根据Key读取Value
		public static String GetValueByKey(String key) {
			InputStream ips = PropertiesUtil.class.getResourceAsStream("/app.properties");
			BufferedReader ipss = new BufferedReader(new InputStreamReader(ips));
//			String filePath = PropertiesUtil.class.getResource("/").toString();
			Properties pps = new Properties();
			try {
//				InputStream in = new BufferedInputStream(new FileInputStream(filePath));
				pps.load(ipss);
				String value = pps.getProperty(key);
//				System.out.println(key + " = " + value);
				return value;

			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
}
