package com.rit.AndroidAnalysisEngine.engine;

import java.nio.file.Path;

public interface ApkToJarConverter {

	public String convertApkToJar(Path pathToApk, Path dirToCreateJar);
}
