package com.rit.AndroidAnalysisEngine.engine.impl;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import android.app.Activity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by McAfee on 10/14/2014.
 */
public class ActivityFinderUnitTest {

    private ActivityFinder activityFinder;

    private final String jarPath = "src/test/resources/robodemo-sample-1.0.1_alreadyundexed.jar";

    @Before
    public void setUp(){
        activityFinder = new ActivityFinder();
    }

    @Test
    @Ignore("Requires files I didn't want to commit.")
    public void dynamicallyLoadClass() throws Exception{

        URL[] urlToHit = new URL[1];
        urlToHit[0] = new File(jarPath).toURI().toURL();
        URLClassLoader loader = URLClassLoader.newInstance(urlToHit);

        /*System.out.println("MY CLASSPATH:");

        for(URL url: loader.getURLs()){
            System.out.println(url.getFile());
        }*/

        Class<?> clazz = null;
        try {
            clazz = Class.forName("com.octo.android.robodemo.sample.MainActivity", true, loader);
        }catch(ClassNotFoundException ex){
            Assert.fail("Did not find main activity!");
        }
        Assert.assertNotNull(clazz);
        Assert.assertEquals(clazz.getName(), "com.octo.android.robodemo.sample.MainActivity");
        //therefore, we found the class!
    }

    @Test
    @Ignore("Requires files I didn't want to commit.")
    public void getActivityImplementingClasses() throws Exception{
        Set<Class<? extends Activity>> acts = new HashSet<Class<? extends Activity>>();
        Set<Class<? extends Activity>> resultSet = activityFinder.getClassInfo(new File(jarPath), acts);
        
        /*System.out.println("MY RESULTS:");
        Iterator<Class<? extends Activity>> rator= resultSet.iterator();
        while (rator.hasNext()){
        	System.out.println(rator.next().getName());
        }*/
        
        Assert.assertEquals(3, resultSet.size());
    }
}
