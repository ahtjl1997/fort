package com.fort.webapp.sshd.forward.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ClientChannel;
import org.apache.sshd.client.future.ConnectFuture;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.channel.Channel;
import org.apache.sshd.common.channel.ChannelListener;
import org.apache.sshd.common.io.IoInputStream;
import org.apache.sshd.common.util.buffer.Buffer;
import org.apache.sshd.common.util.buffer.ByteArrayBuffer;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.SessionAware;
import org.apache.sshd.server.session.ServerSession;
import org.springframework.stereotype.Service;

import com.fort.webapp.sshd.forward.io.FortForwardErrOutputStream;
import com.fort.webapp.sshd.forward.io.FortForwardInputStream;
import com.fort.webapp.sshd.forward.io.FortForwardOutputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class SshForwardCommand implements Command, SessionAware,Runnable {

	private ServerSession session;
	
	private InputStream in;
	
	private OutputStream out;
	
	private OutputStream err;
	
	private ExitCallback callback;
	
	private Thread task = null;
	
	@Override
	public void run() {
		String username = session.getUsername();
		System.out.println("username:" + username);
		String[] userArr = username.split("@");
		String user = userArr[1];
		String host = userArr[2];
		String port = userArr[3];
		SshClient client = SshClient.setUpDefaultClient();
		try {
			client.start();
			ConnectFuture cf = client.connect(user, host, Integer.valueOf(port));
			if(cf != null) {
				cf.verify(7, TimeUnit.SECONDS);
				ClientSession cs = cf.getSession();
				cs.addPasswordIdentity("root");
				cs.auth().verify(5L, TimeUnit.SECONDS);
				ClientChannel cc = cs.createShellChannel();
				cc.setIn(new FortForwardInputStream(in));
				cc.setErr(new FortForwardErrOutputStream(err));
				cc.setOut(new FortForwardOutputStream(out));
				cc.open().await();
				cc.addChannelListener(new ChannelListener() {
					@Override
					public void channelClosed(Channel channel, Throwable reason) {
						callback.onExit(0);
					}
				});
			}
		} catch (Exception e) {
			callback.onExit(0);
			e.printStackTrace();
		}
	}

	@Override
	public void start(Environment env) throws IOException {
		task = new Thread(this, "fort-sshd");
		task.start();
	}

	@Override
	public void destroy() throws Exception {
		try {
			if(in != null) {
				in.close();
			}
			if(out != null) {
				out.close();
			}
			if(err != null) {
				err.close();
			}
		}catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(task != null) {
				task.interrupt();
			}
		}
	}

	@Override
	public void setSession(ServerSession session) {
		this.session = session;
	}

	@Override
	public void setInputStream(InputStream in) {
		this.in = in;
	}

	@Override
	public void setOutputStream(OutputStream out) {
		this.out = out;
	}

	@Override
	public void setErrorStream(OutputStream err) {
		this.err = err;
	}

	@Override
	public void setExitCallback(ExitCallback callback) {
		this.callback = callback;
	}

}
