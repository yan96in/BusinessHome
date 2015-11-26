package com.businesshome.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.baidu.location.BDLocation;
import com.businesshome.R;
import com.businesshome.activity.Activity_SearchPolicy;
import com.businesshome.activity.CityList;
import com.businesshome.activity.RaceDetailActivity;
import com.businesshome.adapter.PolicyAdapter;
import com.businesshome.datas.GetPolicys;
import com.businesshome.model.Policy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class PolicyFragment extends Fragment{

	private LinearLayout line_search,qiehuan_city;
	private ListView raceListView;
	TextView showcity;
	List<Policy> datas = new ArrayList<Policy>();
	PolicyAdapter adapter = null;
	BDLocation bdl;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_policy, null);
		
		line_search = (LinearLayout) rootView.findViewById(R.id.line_search);
		raceListView = (ListView) rootView.findViewById(R.id.list_policy);
		qiehuan_city = (LinearLayout) rootView.findViewById(R.id.qiehuan_city);
		showcity = (TextView) rootView.findViewById(R.id.show_city);
		bdl = new BDLocation();
		String loc = bdl.getCity();
		if(loc == null){
			showcity.setText("定位中");
		}else {
			showcity.setText(loc);
		}
		
		line_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),Activity_SearchPolicy.class);
				//Bundle bundle = new Bundle();
				intent.putExtra("data", (Serializable)datas);
				startActivity(intent);
				//bundle.putCharSequenceArrayList("datas", (Serializable)datas);
				//bundle.putString("datas", datas);
			}
		});
		qiehuan_city.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),CityList.class);
				startActivityForResult(intent, 1000);
				//startActivity(new Intent(getActivity(), CityList.class));
			}
		});
		
		raceListView.setOnItemClickListener(new OnItemClickListener() {

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
				Intent intent = new Intent(getActivity(),RaceDetailActivity.class);
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
				//GetNews getNews = new GetNews();
				//datas = getNews.getNewsDatas();
				GetPolicys getPolicys = new GetPolicys();
				datas = getPolicys.getPolicyDatas();
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		switch(resultCode){
//		case RESULT_OK:
//			
//		}
		String city = data.getExtras().getString("city");
		showcity.setText(city);
		Iterator<Policy> iterator = datas.iterator();
		List<Policy> list = new ArrayList<Policy>();
		while(iterator.hasNext()){
			Policy policy = iterator.next();
			if(policy.getTitle().contains(city)){
				list.add(policy);
			}
		}
		if(list.size()>0){
			adapter.update(list);
		}else{
			
			Toast.makeText(getActivity(), "未查到结果", Toast.LENGTH_SHORT).show();
		}
	};
	Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			String msgobj = msg.obj.toString();
			System.out.println(msgobj.toString());
			for(int i=0; i< datas.size();i++){
				System.out.println(datas.get(i).getTitle());
			}
			adapter = new PolicyAdapter(getActivity(), datas);
			raceListView.setAdapter(adapter);
		};
	};
}
