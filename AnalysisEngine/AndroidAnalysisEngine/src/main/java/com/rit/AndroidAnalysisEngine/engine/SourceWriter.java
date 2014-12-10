package com.rit.AndroidAnalysisEngine.engine;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Set;

import android.app.Activity;

public class SourceWriter {
	
	private Set<Class<? extends Activity>> activityClasses;
	
	private String importLines = "";
	private String functionLines = "";
	private String functionToTestLines = "";
	
	private final String onCreateIdentifier = "onCreate(android.os.Bundle)";
	
	public SourceWriter( Set<Class<? extends Activity>> activityClasses ){
		this.activityClasses = activityClasses;
		
		Iterator<Class<? extends Activity>> rator= activityClasses.iterator();
		Class<? extends Activity> curAct;
        while (rator.hasNext()){
        	curAct = rator.next();
        	processActivity(curAct);
        }
	}
	
	private void processActivity(Class<? extends Activity> clazz){
		
		if(Modifier.isAbstract(clazz.getModifiers())){
			return;
		}
		
		generateImportStrings(clazz);
		generateFunctionCalls(clazz);
		generateFuntionToTestLines(clazz);
	}
	
	
	private void generateImportStrings(Class<? extends Activity> clazz){
		importLines +="import "+clazz.getName()+";\n";
	}
	
	private void addOnCreateFunctionCalls(Class<? extends Activity> clazz){
		/*functionLines += "\t\t\t" + clazz.getSimpleName() + " "+clazz.getSimpleName()+"Var = "+
        			"new "+clazz.getSimpleName()+"();\n";
        functionLines += "\t\t\t" + clazz.getSimpleName()+"Var"+".onCreate(null);\n";*/
	}
	
	private void generateFunctionCalls(Class<? extends Activity> clazz){
		
		//System.out.println("Checking methods of: "+ clazz.getSimpleName());
		
		Method[] methods = clazz.getDeclaredMethods();
		
		functionLines += "\t\ttry{\n"; 
		functionLines += "\t\t\t"+clazz.getSimpleName()+" "+clazz.getSimpleName().toLowerCase()+" = Robolectric.buildActivity("+clazz.getSimpleName()+".class).create().get();\n";
		
		for (Method method : methods){
			
			if(!Modifier.isPublic(method.getModifiers())){
				continue;
			}
			//onCreate
			if(method.toGenericString().contains(onCreateIdentifier)){
				addOnCreateFunctionCalls(clazz);
			}
		}
		
		//for things beyond the capabilities of generic mocks
		functionLines += "\t\t}catch(RuntimeException ex){\n\t\t\tSystem.out.println(\"Mockable Terminus!\");\n\t\t}\n"; 
		 
	}
	
	private void generateFuntionToTestLines(Class<? extends Activity> clazz){
		
		functionToTestLines+=clazz.getName()+".onCreate(sym),";
		
		Method[] methods = clazz.getDeclaredMethods();
		
		for (Method method : methods){
			
			if(!Modifier.isPublic(method.getModifiers())){
				continue;
			}
			//onCreate
			if(method.toGenericString().contains(onCreateIdentifier)){
			}
			
		}
	}

	public Set<Class<? extends Activity>> getActivityClasses() {
		return activityClasses;
	}

	public String getImportStrings() {
		return importLines;
	}

	public String getFunctionCallStrings() {
		return functionLines;
	}

	public String getFunctionToTestStrings() {
		return functionToTestLines.substring(0, functionToTestLines.length()-1);
	}
	
	
	
}
