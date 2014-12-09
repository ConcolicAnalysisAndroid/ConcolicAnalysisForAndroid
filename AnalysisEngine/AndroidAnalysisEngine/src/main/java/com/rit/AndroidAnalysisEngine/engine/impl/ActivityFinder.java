package com.rit.AndroidAnalysisEngine.engine.impl;

import android.app.Activity;

import com.rit.AndroidAnalysisEngine.engine.FunctionFinder;
import com.rit.AndroidAnalysisEngine.engine.filters.AndroidLibraryActivityFilter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

import org.apache.commons.lang.ArrayUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;




//used because maven is dumb with subdependencies sometimes
import com.google.common.base.Predicate;
import javassist.bytecode.ClassFile;

/**
 * Created by McAfee on 10/14/2014.
 */
public class ActivityFinder implements FunctionFinder {

	
    public Set<Class<? extends Activity>> getClassInfo(File jarToSearch, Set<Class<? extends Activity>> currentInfos) throws MalformedURLException{

    	final AndroidLibraryActivityFilter filter = new AndroidLibraryActivityFilter();
    	
        if (null==currentInfos){
            currentInfos=new TreeSet<Class<? extends Activity>>();
        }
        URL[] urlToHit = new URL[1];
        urlToHit[0] = jarToSearch.toURI().toURL();

        ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] allUrls = (URL[]) ArrayUtils.addAll(urlToHit, ((URLClassLoader)cl).getURLs());
        URLClassLoader allLoader = URLClassLoader.newInstance(allUrls);
        
        Reflections reflections = new Reflections(

            new ConfigurationBuilder()
            	//.filterInputsBy(new FilterBuilder().include("com.octo.*"))
            	.setUrls(ClasspathHelper.forClassLoader(allLoader)).addClassLoader(allLoader)
                .setScanners(new SubTypesScanner())
        );

        return filter.filterOutClassesFromAndroidLibraries(reflections.getSubTypesOf(Activity.class));
    }

}
