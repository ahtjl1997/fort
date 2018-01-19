package com.fort.webapp.sshd.forward.factory;

import org.apache.sshd.common.Factory;
import org.apache.sshd.server.Command;
import org.springframework.stereotype.Service;

import com.fort.webapp.sshd.forward.thread.SshForwardCommand;

@Service("sshForwardShellFactory")
public class SshForwardShellFactory implements Factory<Command> {
	
	@Override
	public Command create() {
		return new SshForwardCommand();
	}

}
