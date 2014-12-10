package com.rit.AndroidAnalysisEngine.engine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.*;

import com.rit.AndroidAnalysisEngine.Config;

public class SpawnBuilder {

	private Config config = null;
	
	
	
	
	public SpawnBuilder() {
		super();
		config = Config.getConfig();
	}

	/**
	 * Returns path to apk within spawn
	 * @param path
	 * @return
	 */
	public Path buildSpawn(Path sourcePath){
		
		String spawnDirString = config.getSpawnFolder();
		File spawnDir  = new File(spawnDirString);
		if (!spawnDir.exists()){
			try{
				spawnDir.mkdir();
		     } catch(SecurityException se){
		        //handle it
		    	 se.printStackTrace();
		     }   
		}
		
		Path newApkPath = new File(spawnDirString+"/"+sourcePath.getFileName()).toPath();
		
		try {
			Files.copy(sourcePath, newApkPath, REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newApkPath;
	}
	
	public void cleanSpawn(){
		
	}
	
}
