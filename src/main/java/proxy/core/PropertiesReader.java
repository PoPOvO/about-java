package proxy.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertiesReader {
	private static Map<String, String> propertiesMap;
	
	static {
		propertiesMap = new HashMap<>();
		InputStream is = PropertiesReader.class.getResourceAsStream("/base_config.properties");
		try {
			Properties properties = new Properties();
			properties.load(is);
			
			@SuppressWarnings("unchecked")
			Enumeration<String> names = (Enumeration<String>) properties.propertyNames();
			while (names.hasMoreElements()) {
				String name = names.nextElement();
				String value = properties.getProperty(name);
				propertiesMap.put(name, value);
			}
		} catch (IOException e) {
		}
	}
	
	public PropertiesReader() {
	}
	
	public static String getProperty(String key) {
		return propertiesMap.get(key);
	}
}
