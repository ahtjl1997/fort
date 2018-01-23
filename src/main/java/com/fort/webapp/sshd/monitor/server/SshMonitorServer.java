package com.fort.webapp.sshd.monitor.server;

import java.io.File;

import org.apache.sshd.common.PropertyResolverUtils;
import org.apache.sshd.server.ServerFactoryManager;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fort.webapp.sshd.monitor.auth.SshMonitorPasswordAuthenticator;
import com.fort.webapp.sshd.monitor.factory.SshMonitorShellFactory;

@Component
public class SshMonitorServer implements ApplicationRunner {

	@Autowired
	private SshMonitorPasswordAuthenticator sshMonitorPasswordAuthenticator;
	
	@Autowired
	private SshMonitorShellFactory sshMonitorShellFactory;
	
	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		SshServer sshd = SshServer.setUpDefaultServer();
		PropertyResolverUtils.updateProperty(sshd, ServerFactoryManager.WELCOME_BANNER, "欢迎使用运维审计系统\n\n");
		sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(new File("hostkey.ser")));
		sshd.setPort(9999);
		sshd.setPasswordAuthenticator(sshMonitorPasswordAuthenticator);
		sshd.setShellFactory(sshMonitorShellFactory);
		sshd.start();
	}

}
