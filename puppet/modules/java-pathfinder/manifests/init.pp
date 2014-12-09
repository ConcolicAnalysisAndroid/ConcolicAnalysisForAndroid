class java-pathfinder{ 
	require workspace
	require java
	require ant

	file {'/home/vagrant/.jpf':
      ensure  => directory,
    }
	
	file {'site.properties':
      path    => '/home/vagrant/.jpf/site.properties',
      ensure  => present,
      source => '/vagrant/puppet/modules/java-pathfinder/sources/site.properties',
    }
	
	file {'/home/vagrant/Workspace/AnalysisEngine/jpf':
      ensure  => directory,
    }
	
}