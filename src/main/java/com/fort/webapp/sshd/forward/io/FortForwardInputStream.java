package com.fort.webapp.sshd.forward.io;

import java.io.IOException;
import java.io.InputStream;

public class FortForwardInputStream extends InputStream {

	private InputStream in;
	
	public FortForwardInputStream(InputStream in) {
		this.in = in;
	}
	
	@Override
	public int read() throws IOException {
		return in.read();
	}

	@Override
	public int read(byte[] buff, int off, int len) throws IOException {
		int length = in.read(buff, off, len);
		//System.out.print(new String(buff, off, length));
		return length;
	}

	@Override
	public int read(byte[] b) throws IOException {
		return in.read(b);
	}

	@Override
	public long skip(long n) throws IOException {
		return in.skip(n);
	}

	@Override
	public int available() throws IOException {
		return in.available();
	}

	@Override
	public void close() throws IOException {
		in.close();
	}

	@Override
	public synchronized void mark(int readlimit) {
		in.mark(readlimit);
	}

	@Override
	public synchronized void reset() throws IOException {
		in.reset();
	}

	@Override
	public boolean markSupported() {
		return in.markSupported();
	}

}
