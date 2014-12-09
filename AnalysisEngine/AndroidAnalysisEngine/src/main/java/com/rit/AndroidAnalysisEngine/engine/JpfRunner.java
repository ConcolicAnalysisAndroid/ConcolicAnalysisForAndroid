package com.rit.AndroidAnalysisEngine.engine;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.google.common.io.CharStreams;
import com.rit.AndroidAnalysisEngine.Config;

public class JpfRunner {

	public File runAgainstFile(String originalFileName, File file) throws IOException{
		
		String jpfDir = Config.getConfig().getJpfPath();
		String command =  "-log";// -log ./spawn/"+file.getName();
		String[] CMD_ARRAY = {"/bin/sh", jpfDir+"bin/jpf", "-log", "./spawn/"+file.getName()};
		(new File("./results/")).mkdirs();
		String resultsFile = "./results/"+originalFileName+".jpfout.txt";
		final ProcessBuilder builder = new ProcessBuilder(CMD_ARRAY).redirectErrorStream(true).redirectOutput(new File(resultsFile)).directory(new File(".").getAbsoluteFile());
		Process runProc = builder.start();

		try {
			runProc.waitFor();	//what could go wrong? Oh, right, infinite hang . . .
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		return new File(resultsFile); 
	}
	
	
	
	
}
