Vagrant::Config.run do |config|
  # All Vagrant configuration is done here. The most common configuration
  # options are documented and commented below. For a complete reference,
  # please see the online documentation at vagrantup.com.


  config.vm.box = "puppetlabs/centos-6.5-32-puppet"

  Vagrant::Config.run do |config|
      config.vm.customize ["modifyvm", :id, "--memory", 2048]
  end

  # Forward a port from the guest to the host, which allows for outside
  # computers to access the VM, whereas host only networking does not.
  config.vm.forward_port 22, 2244

  # Boot with a GUI so you can see the screen. (Default is headless)
  # config.vm.boot_mode = :gui


  # Share an additional folder to the guest VM. The first argument is
  # an identifier, the second is the path on the guest to mount the
  # folder, and the third is the path on the host to the actual folder.
  #config.vm.share_folder "maven-repo", "/.m2", "~/.m2"

  #installs public puppet modules in the VM, as it's pain to do on OSX
  config.vm.provision "shell", path: "vagrantScripts/install_public_puppet_modules.sh"

  # Enable provisioning with Puppet stand alone.
  config.vm.provision "puppet" do |puppet|
    puppet.module_path = ["puppet/modules"]
    puppet.manifests_path = "puppet/manifests"
    puppet.manifest_file = "site.pp"
  end

  #other tools
  config.vm.provision "shell", path: "vagrantScripts/maven_setup.sh"
  config.vm.provision "shell", path: "vagrantScripts/dex2jar_setup.sh"
  config.vm.provision "shell", path: "vagrantScripts/apktools_setup.sh"
  config.vm.provision "shell", path: "vagrantScripts/jpf_setup.sh"

end

