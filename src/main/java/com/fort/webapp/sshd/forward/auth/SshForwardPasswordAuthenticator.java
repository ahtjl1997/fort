package com.fort.webapp.sshd.forward.auth;

import org.apache.sshd.server.auth.password.PasswordAuthenticator;
import org.apache.sshd.server.auth.password.PasswordChangeRequiredException;
import org.apache.sshd.server.session.ServerSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fort.module.employee.Employee;
import com.fort.service.employee.EmployeeService;
import com.fort.service.employee.LoginService;

@Service("sshForwardPasswordAuthenticator")
public class SshForwardPasswordAuthenticator implements PasswordAuthenticator {

	@Autowired
	private LoginService loginService;
	
	@Override
	public boolean authenticate(String username, String password, ServerSession session)
			throws PasswordChangeRequiredException {
		int index = username.indexOf('@');
		String loginName = username;
		if(index > 0) {
			loginName = username.substring(0, index);
		}
		Employee emp = loginService.login(loginName, password);
		if(emp == null || emp.getStatus() == 0) {
			return false;
		}
		return true;
	}

}
