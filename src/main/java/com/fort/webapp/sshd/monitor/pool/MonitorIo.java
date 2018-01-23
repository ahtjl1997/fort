package com.fort.webapp.sshd.monitor.pool;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.sshd.server.ExitCallback;

public class MonitorIo {
	
	private InputStream in;
	
	private OutputStream out;
	
	private OutputStream err;
	
	private ExitCallback callback;

	public MonitorIo(InputStream in, OutputStream out, OutputStream err, ExitCallback callback) {
		this.in = in;
		this.out = out;
		this.err = err;
		this.callback = callback;
	}

	public InputStream getIn() {
		return in;
	}

	public void setIn(InputStream in) {
		this.in = in;
	}

	public OutputStream getOut() {
		return out;
	}

	public void setOut(OutputStream out) {
		this.out = out;
	}

	public OutputStream getErr() {
		return err;
	}

	public void setErr(OutputStream err) {
		this.err = err;
	}

	public ExitCallback getCallback() {
		return callback;
	}

	public void setCallback(ExitCallback callback) {
		this.callback = callback;
	}
}
