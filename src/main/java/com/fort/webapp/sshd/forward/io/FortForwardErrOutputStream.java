package com.fort.webapp.sshd.forward.io;

import java.io.IOException;
import java.io.OutputStream;

public class FortForwardErrOutputStream extends OutputStream {

	private OutputStream err;
	
	public FortForwardErrOutputStream(OutputStream err) {
		this.err = err;
	}
	
	@Override
	public void write(int b) throws IOException {
		err.write(b);
	}

	@Override
	public void write(byte[] buff, int off, int len) throws IOException {
		//System.out.println(new String(buff, off, len));
		err.write(buff, off, len);
	}

	@Override
	public void write(byte[] b) throws IOException {
		err.write(b);
	}

	@Override
	public void flush() throws IOException {
		err.flush();
	}

	@Override
	public void close() throws IOException {
		err.close();
	}
	
}
