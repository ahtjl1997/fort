package com.fort.webapp.sshd.monitor.auth;

import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.session.ServerSession;
import org.springframework.stereotype.Service;

@Service("sshMonitorPasswordAuthenticator")
public class SshMonitorPasswordAuthenticator implements PasswordAuthenticator {

	@Override
	public boolean authenticate(String username, String password, ServerSession session)
			throws PasswordChangeRequiredException {
		return true;
	}

}
