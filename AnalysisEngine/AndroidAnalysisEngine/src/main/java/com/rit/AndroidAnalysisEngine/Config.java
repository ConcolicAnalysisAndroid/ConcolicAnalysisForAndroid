package com.rit.AndroidAnalysisEngine;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	private Properties props;
	
	private final String spawnSourceFolder = "./spawnsource";
	private final String spawnFolder = "./spawn";
	
	
	private Config(){
		props = new Properties();
		InputStream input = null;
	
		try {
			 
			input = new FileInputStream("./configs/config.properties");
	 
			// load a properties file
			props.load(input);
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public static Config getConfig(){
		return new Config();
	}
	
	public String getJpfPath(){
		return props.getProperty("jpf_path");
	}
	
	public String getDex2JarPath(){
		return props.getProperty("dex2jar_path");
	}
	
	public String getApktoolsPath(){
		return props.getProperty("apktools_path");
	}

	public String getSpawnSourceFolder() {
		return spawnSourceFolder;
	}

	public String getSpawnFolder() {
		return spawnFolder;
	}
	
	
	
}
