package com.businesshome.datas;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.businesshome.json.JsonUtil;
import com.businesshome.model.News;
import com.businesshome.model.Race;
import com.businesshome.net.ConnNet;


public class GetRaces {

	public List<Race> getRaceDatas(){
		 List<Race> races = null;
		ConnNet connNet = new ConnNet();
		String url = "servlet/RaceServlet";
		//List<NameValuePair> params = new ArrayList<NameValuePair>();
		//params.add(new BasicNameValuePair("username", username));
		//params.add(new BasicNameValuePair("password", password));
		try {
			//HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
			HttpPost httpPost = connNet.gethttPost(url);
			System.out.println(httpPost.toString());
			//httpPost.setEntity(entity);
			HttpClient client = new DefaultHttpClient();
			HttpResponse httpResponse = client.execute(httpPost);
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils
						.toString(httpResponse.getEntity(), "utf-8");
				JsonUtil jsonutil = new JsonUtil();
				System.out.println(result);
				races = jsonutil.stringFromJsonToRaces(result);
			} else {
				races = null;
			}
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (ParseException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return races;
	}
//	public static void main(String[] args){
//		GetNews getNews = new GetNews();
//		List<News> news = getNews.getNewsDatas();
//		System.out.println(news.size());
//	}
}
