package com.rit.AndroidAnalysisEngine.engine;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.SystemUtils;

import com.rit.AndroidAnalysisEngine.Config;
import com.rit.AndroidAnalysisEngine.engine.impl.ActivityFinder;

import android.app.Activity;

public class WrapperCompiler {
	
	private final String indexToken = "{{INDEX}}";
	private final String importsToken = "{{CLASS_IMPORTS}}";
	private final String functionsToken = "{{FUNCTION_CALLS}}";
	private final String classpathToken = "{{CLASSPATH}}";
	private final String functionsToTestToken = "{{FUNCTIONS_TO_TEST}}";
	private String classPathDelimiter;
	private Config config;
	
	public WrapperCompiler(){
		classPathDelimiter = SystemUtils.IS_OS_WINDOWS ? " ; " : " : ";
		config = Config.getConfig();
	}
	
	private String readFile(File src, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(src.getPath()));
			  return new String(encoded, encoding);
			}
	
	
	public void spawnFiles(int index, File targetJar) throws IOException{
		ActivityFinder activityFinder = new ActivityFinder();
		SourceWriter writer = new SourceWriter(activityFinder.getClassInfo(targetJar, new HashSet<Class<? extends Activity>>()));
		spawnWrapperFile(index, writer );
		spawnWrapperRunnerFile(index, writer );
		spawnManifestFile(index, targetJar);
		spawnJpfFile(index, targetJar, writer);
	}
	
	private String getClassPathStringMultiLine(Path targetJarPath) throws IOException{
		return getClassPathString(targetJarPath).replace(":", "\n  ");
	}
	
	private String getClassPathString(Path targetJarPath) throws IOException{
		String classpath = "";
		File dir = new File("./lib/");
        File [] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar");
            }
        });
        for (File file : files) {
        	classpath+= ":"+new File(config.getSpawnFolder()).toPath().relativize(file.toPath()).toString();
        }
        String relativePath = new File(".").toPath().relativize(targetJarPath).toString();
		return relativePath + classpath;
	}
	
	public void spawnManifestFile(int index, File targetJar) throws IOException{
		File sourceFile = new File(config.getSpawnSourceFolder()+"/manifest.mf.template");
		
		String rawSource = readFile(sourceFile, Charset.defaultCharset());
		String editedSource = rawSource.replace(indexToken, new Integer(index).toString());
		editedSource = editedSource.replace("{{CLASSPATH}}", getClassPathStringMultiLine(targetJar.toPath()));

		PrintWriter outToFile = new PrintWriter(config.getSpawnFolder()+"/manifest"+index+".mf");
		outToFile.println(editedSource);
		outToFile.close();
	}
	
	public void spawnWrapperFile(int index, SourceWriter manipulator) throws IOException{
		File sourceFile = new File(config.getSpawnSourceFolder()+"/Wrapper.template");
		
		String rawSource = readFile(sourceFile, Charset.defaultCharset());
		String editedSource = rawSource.replace(indexToken, new Integer(index).toString());
		editedSource = editedSource.replace(importsToken, manipulator.getImportStrings());
		editedSource = editedSource.replace(functionsToken, manipulator.getFunctionCallStrings());
		
		PrintWriter outToFile = new PrintWriter(config.getSpawnFolder()+"/Wrapper"+index+".java");
		outToFile.println(editedSource);
		outToFile.close();
		
	}
	
	public void spawnWrapperRunnerFile(int index, SourceWriter manipulator) throws IOException{
		File sourceFile = new File(config.getSpawnSourceFolder()+"/WrapperRunner.template");
		
		String rawSource = readFile(sourceFile, Charset.defaultCharset());
		String editedSource = rawSource.replace(indexToken, new Integer(index).toString());
		
		PrintWriter outToFile = new PrintWriter(config.getSpawnFolder()+"/WrapperRunner"+index+".java");
		outToFile.println(editedSource);
		outToFile.close();
		
	}
	
	public void spawnJpfFile(int index, File targetJar, SourceWriter manipulator) throws IOException{
		File sourceFile = new File(config.getSpawnSourceFolder()+"/Wrapper.jpf.template");
		
		String rawSource = readFile(sourceFile, Charset.defaultCharset());
		String editedSource = rawSource.replace(indexToken, new Integer(index).toString());
		editedSource = editedSource.replace(classpathToken, config.getSpawnFolder()+"/Wrapper"+index+".jar:"+getClassPathString(targetJar.toPath())).replace("../", "./");
		editedSource = editedSource.replace(functionsToTestToken, manipulator.getFunctionToTestStrings());
		
		PrintWriter outToFile = new PrintWriter(config.getSpawnFolder()+"/Wrapper"+index+".jpf");
		outToFile.println(editedSource);
		outToFile.close();
		
	}
	
	public File doCompile(int index, File targetJar) throws IOException{
		spawnFiles(index, targetJar);
			
		//compile
		String compileCommond = "javac -g -classpath ./lib/*:"+targetJar.toPath().toString()+" "+config.getSpawnFolder()+"/Wrapper"+index+".java "+config.getSpawnFolder()+"/WrapperRunner"+index+".java";
		Process compileProc = Runtime.getRuntime().exec(compileCommond);
		try {
			compileProc.waitFor(); //what could go wrong? Oh, right, infinite hang . . .
		} catch (InterruptedException e) {
			//HERESY WILL BE SUPPRESSED
			e.printStackTrace();
		} 
		
		File classFile = new File(config.getSpawnFolder()+"/Wrapper"+index+".class");
		if(!classFile.exists()){
			System.out.println("Wrapper compile failed");
			return null;
		}
		
		//make the damn jar
		String jarCommand = "jar cmf "+config.getSpawnFolder()+"/manifest"+index+".mf "+config.getSpawnFolder()+"/Wrapper"+index+".jar "+config.getSpawnFolder()+"/Wrapper"+index+".class "+config.getSpawnFolder()+"/WrapperRunner"+index+".class"; 
		Process jarProc =  Runtime.getRuntime().exec(jarCommand);
        
		try {
			jarProc.waitFor(); //what could go wrong? Oh, right, infinite hang . . .
		} catch (InterruptedException e) {
			//HERESY WILL BE SUPP - wait, didn't I already yell this?
			e.printStackTrace();
		} 
		//clean up
		//cleanOutJavaFiles();
        //cleanOutClassFiles();
        return new File("./spawn/Wrapper"+index+".jpf");
        /*WARNING: if you got major.minor versions, I feel bad for you son
        			I got 99 problems, but too many JDKs ain't one 
        Real Answer: get your default user JDK and JRE in sync.  No really. 
        	The inanity of how I'm doing this (who uses exec?) means I can't control 
        	which java is on your path.*/
	}
	
	public void cleanOutJarFiles() throws IOException{
		cleanOutFilesByPattern("*.jar");
	}
	
	private void cleanOutClassFiles() throws IOException{
		cleanOutFilesByPattern("*.class");
	}
	
	private void cleanOutJavaFiles() throws IOException{
		cleanOutFilesByPattern("*.java");
	}
	
	private void cleanOutFilesByPattern(String pattern) throws IOException{
		File dir = new File("./spawn");
        File [] files = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(".class");
            }
        });
        for (File file : files) {
            file.delete();
        }
	}
}
