package com.fort.webapp.sshd.monitor.factory;

import org.apache.sshd.common.Factory;
import org.apache.sshd.server.Command;
import org.springframework.stereotype.Service;

import com.fort.webapp.sshd.monitor.thread.SshMonitorCommand;

@Service("sshMonitorShellFactory")
public class SshMonitorShellFactory implements Factory<Command> {

	@Override
	public Command create() {
		return new SshMonitorCommand();
	}

}
