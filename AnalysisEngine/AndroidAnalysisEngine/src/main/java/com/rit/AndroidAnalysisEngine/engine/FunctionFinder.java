package com.rit.AndroidAnalysisEngine.engine;

import android.app.Activity;



import java.io.File;
import java.net.MalformedURLException;
import java.util.Set;

/**
 * Created by McAfee on 10/14/2014.
 */
public interface FunctionFinder {

	public Set<Class<? extends Activity>> getClassInfo(File jarToSearch, Set<Class<? extends Activity>> currentInfos) throws MalformedURLException;
}
