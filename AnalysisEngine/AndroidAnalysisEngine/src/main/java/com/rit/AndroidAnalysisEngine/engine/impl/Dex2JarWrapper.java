package com.rit.AndroidAnalysisEngine.engine.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import com.rit.AndroidAnalysisEngine.Config;
import com.rit.AndroidAnalysisEngine.engine.ApkToJarConverter;

public class Dex2JarWrapper implements ApkToJarConverter{
	
	public String convertApkToJar(Path pathToApk, Path dirToCreateJar) {
		// TODO Auto-generated method stub
		Runtime rt = Runtime.getRuntime();
	    try {
	    	String dexer = "sh "+Config.getConfig().getDex2JarPath()+"dex2jar.sh ";
	    	String dexerCommand = dexer + pathToApk.toString();
			Process pr = rt.exec(dexerCommand);
			pr.waitFor();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
	    String newjar = pathToApk.toString().replace(".apk", "_dex2jar.jar");
	    
	    if(!(new File(newjar).exists())){
	    	System.out.println("no undexed file created!");
	    }
	    	
		return newjar;
	}
	
}
