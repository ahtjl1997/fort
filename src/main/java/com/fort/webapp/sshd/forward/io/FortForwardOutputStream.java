package com.fort.webapp.sshd.forward.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.fort.webapp.sshd.monitor.pool.MonitorIo;
import com.fort.webapp.sshd.monitor.pool.MonitorPool;

public class FortForwardOutputStream extends OutputStream {

	private OutputStream out;
	
	private String sessionId;
	
	public FortForwardOutputStream(OutputStream out,String sessionId) {
		this.out = out;
		this.sessionId = sessionId;
	}
	
	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}

	@Override
	public void write(byte[] buff, int off, int len) throws IOException {
		//System.out.print(new String(buff, off, len));
		Map<String,MonitorIo> monitorMap = MonitorPool.pool.get(sessionId);
		synchronized (monitorMap) {
			Set<String> offLine = new HashSet<String>();
			for(Entry<String,MonitorIo> entry : monitorMap.entrySet()) {
				MonitorIo monitor = entry.getValue();
				OutputStream mout = monitor.getOut();
				String monitorId = entry.getKey();
				try {
					mout.write(buff, off, len);
					mout.flush();
				}catch (Exception e) {
					e.printStackTrace();
					offLine.add(monitorId);
				}
			}
			
			for(String id : offLine) {
				monitorMap.remove(id);
			}
		}
		
		out.write(buff, off, len);
	}

	@Override
	public void write(byte[] b) throws IOException {
		out.write(b);
	}

	@Override
	public void flush() throws IOException {
		out.flush();
	}

	@Override
	public void close() throws IOException {
		out.close();
	}
	
}
