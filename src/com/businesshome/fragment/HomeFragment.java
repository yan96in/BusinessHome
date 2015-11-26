package com.businesshome.fragment;

import java.io.Serializable;
import java.net.URI;
import java.util.List;
import com.businesshome.R;
import com.businesshome.activity.Activity_SearchNews;
import com.businesshome.activity.MainActivity;
import com.businesshome.activity.NewsDetailActivity;
import com.businesshome.activity.SendRequirement;
import com.businesshome.adapter.ZixunAdapter;
import com.businesshome.datas.GetNews;
import com.businesshome.json.JsonUtil;
import com.businesshome.model.News;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

public class HomeFragment extends Fragment {

	private LinearLayout line_addReq,line_searchNews;
	private ListView newsListView;
	List<News> datas = null;
	ZixunAdapter adapter = null;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_home, null);
		line_addReq = (LinearLayout) rootView.findViewById(R.id.line_sendreq);
		line_searchNews = (LinearLayout) rootView.findViewById(R.id.line_search);
		newsListView = (ListView) rootView.findViewById(R.id.news_listview);
		
		line_searchNews.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),Activity_SearchNews.class);
				//Bundle bundle = new Bundle();
				intent.putExtra("data", (Serializable)datas);
				startActivity(intent);
				//bundle.putCharSequenceArrayList("datas", (Serializable)datas);
				//bundle.putString("datas", datas);
			}
		});
		line_addReq.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getActivity(),SendRequirement.class));
				getActivity().overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
			}
		});
		newsListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
//				WebView webView = new WebView(getActivity());
//				WebSettings webSettings = webView.getSettings();
//				webSettings.setJavaScriptEnabled(true);
//				webView.loadUrl("http://www.cy211.cn/2015/10/312317.html");
//				webView.setWebViewClient(new WebViewClient(){
//			           @Override
//			        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//			            // TODO Auto-generated method stub
//			               //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
//			             view.loadUrl(url);
//			            return true;
//			        }
//			       });
				Intent intent = new Intent(getActivity(),NewsDetailActivity.class);
				//用Bundle携带数据
			    Bundle bundle=new Bundle();
			    //传递name参数为tinyphp
			    bundle.putString("url", datas.get(position).getUrl());
			    intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				GetNews getNews = new GetNews();
				datas = getNews.getNewsDatas();
				if(datas==null){
					System.out.println("未接收到数据！");
				}else {
					System.out.println(datas.size());
					//adapter = new ZixunAdapter(HomeFragment.this.getActivity(),datas);
					//newsListView.setAdapter(adapter);
					Message msg = new Message();
					msg.obj = datas;
					
					handler.sendMessage(msg);
				}
				
				
			}
		}).start();
		
		
		
		return rootView;
	}
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			String msgobj = msg.obj.toString();
			System.out.println(msgobj.toString());
			//datas = new JsonUtil().stringFromJsonToNews(msgobj);
			
			for(int i=0; i< datas.size();i++){
				System.out.println(datas.get(i).getTitle());
			}
			adapter = new ZixunAdapter(getActivity(), datas);
			newsListView.setAdapter(adapter);
		};
	};
}
