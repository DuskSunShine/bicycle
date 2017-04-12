package com.bravesoft.bravegogo.utils;

import android.os.Handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *这个网络工具类，调用的时候，不用考虑线程问题
 */
public class HandlerNetUtil {
	private static Handler  handler = new Handler();
		
	public static void doNetwork(final String path,final DataCallback callback){
		new Thread(
				new Runnable() {
					@Override
					public void run() {
						try {
							URL url = new URL(path);
							HttpURLConnection connection = (HttpURLConnection) url.openConnection();
							connection.setRequestMethod("GET");
							connection.setConnectTimeout(5000);
							if (200 == connection.getResponseCode()) {
								InputStream is = connection.getInputStream();
								ByteArrayOutputStream baos = new ByteArrayOutputStream();
								int len = 0;
								byte[] buffer = new byte[1024];
								while((len = is.read(buffer)) != -1){
									baos.write(buffer, 0, len);
								}
								
								final byte[] bytes = baos.toByteArray();
								
								handler.post(new Runnable() {
									@Override
									public void run() {//主线程中
											callback.doData(bytes);
									}
								});
								
							}
							
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}
		).start();
	}


	public interface DataCallback{
		public void doData(byte[] bytes);
	}
}
