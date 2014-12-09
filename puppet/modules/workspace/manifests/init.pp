class workspace{

	file { '/home/vagrant/Workspace/':
	   ensure => 'link',
	   target => '/vagrant',
	}

	file { "/home/vagrant/Workspace/AnalysisEngine/tools":
		ensure => "directory",
	}
}