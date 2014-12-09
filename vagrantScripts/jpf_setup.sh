#!/bin/bash

echo "Installing Java Pathfinder";
TARGET_DIR=/home/vagrant/Workspace/AnalysisEngine/tools/jpf;
CORE_DIR=$TARGET_DIR/jpf-core;
SYMBC_DIR=$TARGET_DIR/jpf-symbc;

rm -rf $TARGET_DIR
mkdir -p $CORE_DIR;
mkdir -p $SYMBC_DIR;
hg clone --verbose http://babelfish.arc.nasa.gov/hg/jpf/jpf-core $CORE_DIR;
hg clone --verbose http://babelfish.arc.nasa.gov/hg/jpf/jpf-symbc $SYMBC_DIR;

cd $CORE_DIR;
ant;
cd $SYMBC_DIR;
ant;