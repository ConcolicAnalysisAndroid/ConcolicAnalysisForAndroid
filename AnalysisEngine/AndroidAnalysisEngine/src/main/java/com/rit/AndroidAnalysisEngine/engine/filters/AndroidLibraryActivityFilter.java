package com.rit.AndroidAnalysisEngine.engine.filters;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.io.Files;

import android.app.Activity;

public class AndroidLibraryActivityFilter {
	
	public Set<Class<? extends Activity>> filterOutClassesFromAndroidLibraries(Set<Class<? extends Activity>> unfilteredList){
		Set<Class<? extends Activity>> filteredList = new HashSet<Class<? extends Activity>>();
		
		File blocklistFile = new File("configs/AndroidActivityClassesBlocklist.txt");
		List<String> blocklist = new ArrayList<String>();
		try{
			if(!blocklistFile.exists()){
				throw new Exception("Blocklist?  What Blocklist?");
			}
			blocklist = Files.readLines(blocklistFile, Charset.defaultCharset());
		}catch(Exception ex){
			System.out.println("help, help, I'm being suppressed!");
		}
		
		for(Class<? extends Activity> clazz : unfilteredList){
			if(blocklist.contains(clazz.getName())){
				continue;
			}else{
				filteredList.add(clazz);
			}
		}
		
		return filteredList;
	}
}
