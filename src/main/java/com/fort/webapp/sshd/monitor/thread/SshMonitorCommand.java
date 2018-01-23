package com.fort.webapp.sshd.monitor.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.sshd.common.channel.Channel;
import org.apache.sshd.common.channel.ChannelListener;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.Environment;
import org.apache.sshd.server.ExitCallback;
import org.apache.sshd.server.SessionAware;
import org.apache.sshd.server.session.ServerSession;

import com.fort.webapp.sshd.monitor.pool.MonitorIo;
import com.fort.webapp.sshd.monitor.pool.MonitorPool;
import com.util.RandomUtil;
import com.util.StringUtil;

public class SshMonitorCommand implements Command, SessionAware,Runnable {

	private ServerSession session;
	
	private InputStream in;
	
	private OutputStream out;
	
	private OutputStream err;
	
	private ExitCallback callback;
	
	private Thread task = null;

	@Override
	public void run() {
		try {
			final String monitorId = session.getUsername(); 
			final String sessionId = RandomUtil.GetGuid32();
			MonitorIo mi = new MonitorIo(in, out, err, callback);
			Map<String,MonitorIo> map = MonitorPool.pool.get(monitorId);
			if(map == null) {
				callback.onExit(0);
			}else {
				synchronized (map) {
					map.put(sessionId, mi);
				}
			}
			session.addChannelListener(new ChannelListener() {
				@Override
				public void channelClosed(Channel channel, Throwable reason) {
					callback.onExit(0);
					Map<String,MonitorIo> monitorMap = MonitorPool.pool.get(monitorId);
					if(monitorMap != null) {
						synchronized (monitorMap) {
							monitorMap.remove(sessionId);
						}
					}
				}
			});
		}catch (Exception e) {
			e.printStackTrace();
			callback.onExit(0);
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
