package com.rit.AndroidAnalysisEngine.engine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import com.rit.AndroidAnalysisEngine.Config;

public class ApktoolsWrapper {

	private Config config;

	public ApktoolsWrapper() {
		super();
		config = Config.getConfig();
	}
	
	public void unpackApkResources(Path path) throws IOException{
		
		File possiblyExistantDirectory = new File(config.getSpawnFolder());
		if (possiblyExistantDirectory.exists()){
			possiblyExistantDirectory.delete();
		}
		
		String jarCommand = "java -jar "+Config.getConfig().getApktoolsPath()+"apktools.jar d -f "+path.toString()+" -o "+config.getSpawnFolder();
		Process jarProc =  Runtime.getRuntime().exec(jarCommand);
        
		try {
			jarProc.waitFor(); //what could go wrong? Oh, right, infinite hang . . .
		} catch (InterruptedException e) {
			//HERESY WILL BE SUPP - wait, didn't I already yell this?
			e.printStackTrace();
		} 
		
		
		
	}
	
	
	
}
