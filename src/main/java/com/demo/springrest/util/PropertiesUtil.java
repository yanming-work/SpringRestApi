package com.demo.springrest.util;

import java.io.InputStream;
import java.util.Properties;  
 
 
/** 
*  
* PropertiesUtil.java 
* 
* @desc properties 资源文件解析工具 
*/  
public class PropertiesUtil {    
	private static Properties prop = null;

	/**
	 * 初始化Properties实例
	 * @param propertyName
	 * @throws Exception
	 */
	public synchronized static void initProperty(String propertyName) throws Exception {
		if (prop == null) {
			prop = new Properties();
			InputStream inputstream = null;
			ClassLoader cl = PropertiesUtil.class.getClassLoader(); 
			//System.out.println(cl);
			if  (cl !=  null ) {        
				inputstream = cl.getResourceAsStream( propertyName );        
			}  else {        
				inputstream = ClassLoader.getSystemResourceAsStream(propertyName );        
			}   

			if (inputstream == null) {
				throw new Exception("inputstream " + propertyName+ " open null");
			}
			prop.load(inputstream);
			inputstream.close();
			inputstream = null;
		}
	}
    /**
     * 读取数据
     * @param propertyName
     * @param key
     * @return
     */
	public static String getValueByKey(String propertyName, String key) {
		String result = "";
		try {
			initProperty(propertyName);
			result = prop.getProperty(key);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static void main(String[] s) {
		
		try {
			
			System.out.println(PropertiesUtil.getValueByKey("ErrorCode.properties","10001"));
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
	}
}