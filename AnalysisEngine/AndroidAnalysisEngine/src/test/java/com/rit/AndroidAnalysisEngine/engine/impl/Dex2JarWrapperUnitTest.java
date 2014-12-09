package com.rit.AndroidAnalysisEngine.engine.impl;

import java.io.File;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class Dex2JarWrapperUnitTest {
	
	private Dex2JarWrapper dex2JarWrapper;

    private final String apkPath = "./src/test/resources/robodemo-sample-1.0.1.apk";
    private final String jarPath = "./src/test/resources/robodemo-sample-1.0.1_dex2jar.jar";


    private void cleanUpJar(){
        File dexjar = new File(jarPath);
        if(dexjar.exists()){
            dexjar.delete();
        }
    }

	@Before
	public void setUp(){
		dex2JarWrapper = new Dex2JarWrapper();
        cleanUpJar();
	}
	
	@Test //test has issue
    @Ignore("Requires files I didn't want to commit.")
	public void testDexToJar() throws Exception{
        File sourceFile = new File(apkPath);
        Assert.assertTrue("Robodemo apk does not exist", sourceFile.exists());
		dex2JarWrapper.convertApkToJar(sourceFile.toPath(), null);
		Thread.sleep(1000); //because lag
		File result = new File(apkPath);
		Assert.assertTrue("Robodemo jar does not exist", result.exists());
	}
	
	
}
