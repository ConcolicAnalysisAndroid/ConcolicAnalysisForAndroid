#!/bin/bash

echo "Installing apktools";
TARGET_DIR=/home/vagrant/Workspace/AnalysisEngine/tools/apktools;
APKTOOLS_VERSION=2.0.0rc3;
APKTOOLS_FILENAME=apktools.jar

rm -rf $TARGET_DIR;
mkdir -p $TARGET_DIR;
cd $TARGET_DIR;
wget https://bitbucket.org/iBotPeaches/apktool/downloads/apktool_$APKTOOLS_VERSION.jar -O $APKTOOLS_FILENAME;