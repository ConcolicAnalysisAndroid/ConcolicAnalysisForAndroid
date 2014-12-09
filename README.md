#Concolic Analysis System Proving Android Readiness (CASPAR)

## General setup:

1. Setup the VM
  1. Install Vagrant
  2. Go to the root directoroty of the repo (where this file is)
  3. Run "vagrant up" and wait for the machine to come online
  4. Run "vagrant ssh" to enter the virtual machine
2. All included projects will be in "~/Workspace/" in the VM

## Requirements

1. Linux or possibly OSX only.  Windows is not supported at this time.  Hence, the management has provided a lovely vagrant VM

## Analysis Engine Project Setup:

1. MAKE SURE YOU HAVE A JDK INSTALLED AND ON YOUR PATH
  1. Requires at least 1.7
  2. Please make sure your default JRE and default JRE are the same major version
2. In the VM, navigate to "~/Workspace/AnalysisEngine"
  1. Set up JPF
    1. run jpf_clone.sh - this pulls down the repositories
	2. run jpf_build.sh - this builds the required jars
  2. In the host OS, import "AnalysisEngine/AndroidAnalysisEngine/" as a maven project in the IDE of your choice

