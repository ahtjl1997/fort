package com.fort.webapp.sshd.forward.io;

import java.io.IOException;
import java.io.OutputStream;

public class FortForwardOutputStream extends OutputStream {

	private OutputStream out;
	
	public FortForwardOutputStream(OutputStream out) {
		this.out = out;
	}
	
	@Override
	public void write(int b) throws IOException {
		out.write(b);
	}

	@Override
	public void write(byte[] buff, int off, int len) throws IOException {
		System.out.print(new String(buff, off, len));
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
