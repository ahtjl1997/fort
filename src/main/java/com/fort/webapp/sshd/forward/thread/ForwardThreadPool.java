package com.fort.webapp.sshd.forward.thread;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.sshd.client.channel.ClientChannel;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class ForwardThreadPool extends ThreadGroup {

	private ClientChannel clientChannel;
	
	private InputStream in;
	
	private OutputStream out;
	
	private OutputStream err;
	
	public ForwardThreadPool(ClientChannel clientChannel,InputStream in,OutputStream out,OutputStream err) {
		super(String.valueOf(new Date().getTime()));
		this.in = in;
		this.out = out;
		this.err = err;
		this.clientChannel = clientChannel;
	}
	
	public void start() {
		new SendErrDataWorkThread().start();
		new SendDataWorkThread().start();
		new ReadDataWorkThread().start();
	}
	
	private class SendErrDataWorkThread extends Thread{
		
		@Override
		public void run() {
			byte[] buff = new byte[1024];
			int len = -1;
			ByteOutputStream bos = new ByteOutputStream();
			InputStream error = clientChannel.getInvertedErr();
			try {
				while((len = error.read(buff)) != -1) {
					bos.flush();
					bos.write(buff, 0, len);
					err.write(buff, 0, len);
					err.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(error != null)
						error.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class SendDataWorkThread extends Thread{
		
		@Override
		public void run() {
			byte[] buff = new byte[1024];
			int len = -1;
			ByteOutputStream bos = new ByteOutputStream();
			InputStream is = clientChannel.getInvertedOut();
			try {
				while((len = is.read(buff)) != -1) {
					bos.flush();
					bos.write(buff, 0, len);
					out.write(buff, 0, len);
					out.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(is != null)
						is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

    private class ReadDataWorkThread extends Thread{

		@Override
		public void run() {
			byte[] buff = new byte[1024];
			int len = -1;
			ByteOutputStream bos = new ByteOutputStream();
			OutputStream send = clientChannel.getInvertedIn();
			try {
				while((len = in.read(buff)) != -1) {
					bos.flush();
					bos.write(buff, 0, len);
					System.out.println(bos.toString());
					send.write(buff, 0, len);
					send.flush();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(send != null)
						send.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    	
    }
}
