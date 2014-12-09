#!/bin/bash

echo "Installing dex2jar";
TARGET_DIR=/home/vagrant/Workspace/tools/;
D2J_VERSION=0.0.9.15;
D2J_FILENAME=dex2jar.zip

cd $TARGET_DIR;
wget https://dex2jar.googlecode.com/files/dex2jar-$D2J_VERSION.zip -O $D2J_FILENAME;
unzip $D2J_FILENAME;
rm $D2J_FILENAME;