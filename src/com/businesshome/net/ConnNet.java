package com.businesshome.net;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

import android.widget.Toast;

public class ConnNet {

	// �����IP��̬���䣬ÿ������ʱ��Ҫ���·��ʵ�URL
	private static final String URLVAR = "http://121.48.195.253:8080/BH/";

	/**
	 * 
	 * @param urlpath
	 * @return
	 */
	public HttpURLConnection getConn(String urlpath) {
		String finalurl = URLVAR + urlpath;
		HttpURLConnection connection = null;
		try {
			URL url = new URL(finalurl);
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true); // ����������
			connection.setDoOutput(true); // ���������
			connection.setUseCaches(false); // ������ʹ�û���
			connection.setRequestMethod("POST"); // ����ʽ
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Toast.makeText( null, "网络连接故障", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return connection;

	}

	public HttpPost gethttPost(String uripath) {
		HttpPost httpPost = new HttpPost(URLVAR + uripath);

		System.out.println(URLVAR + uripath);
		return httpPost;
	}

	public HttpGet gethttGet(String uripath) {
		HttpGet httpGet = new HttpGet(URLVAR + uripath);

		System.out.println(URLVAR + uripath);
		return httpGet;
	}

}

