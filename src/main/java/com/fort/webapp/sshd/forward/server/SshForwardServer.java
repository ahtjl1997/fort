package com.fort.webapp.sshd.forward.server;

import java.io.File;

import org.apache.sshd.common.Factory;
import org.apache.sshd.common.PropertyResolverUtils;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.ServerFactoryManager;
import org.apache.sshd.server.SshServer;
import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class SshForwardServer implements ApplicationRunner{

	@Autowired
	private PasswordAuthenticator sshForwardPasswordAuthenticator;
	
	@Autowired
	private Factory<Command> sshForwardShellFactory;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		SshServer sshd = SshServer.setUpDefaultServer();
		PropertyResolverUtils.updateProperty(sshd, ServerFactoryManager.WELCOME_BANNER, "欢迎使用运维审计系统\n\n");
		sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider(new File("hostkey.ser")));
		sshd.setPort(8888);
		sshd.setPasswordAuthenticator(sshForwardPasswordAuthenticator);
		sshd.setShellFactory(sshForwardShellFactory);
		sshd.start();
	}
	
}
