package com.businesshome.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.businesshome.R;
import com.businesshome.adapter.RaceAdapter;
import com.businesshome.adapter.ZixunAdapter;
import com.businesshome.model.News;
import com.businesshome.model.Race;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class Activity_SearchRace extends Activity {

	private LinearLayout line_search;
	private EditText edit_search;
	private List<Race> datas = null;
	private List<Race> result = new ArrayList<Race>();
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//get data from the last activity.
				Intent intent = getIntent();
				@SuppressWarnings("unchecked")
				List<Race> rcvdatas = (List<Race>) intent.getSerializableExtra("data");
				datas = rcvdatas;
				
		//componentd.
		setContentView(R.layout.activity_searchraceresult);
		line_search = (LinearLayout) this.findViewById(R.id.line_search2);
		edit_search = (EditText) this.findViewById(R.id.edit_search);
		listView = (ListView) this.findViewById(R.id.listview_search);
		
		line_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				result.clear();
				if(datas==null){
					Toast.makeText(Activity_SearchRace.this, "没有数据", Toast.LENGTH_SHORT).show();
					return;
				}
				String key = edit_search.getText().toString();
				Iterator<Race> iterator = datas.iterator();
				while (iterator.hasNext()) {
					Race race = (Race) iterator.next();
					if(race.getTitle().contains(key)){
						result.add(race);
					}
				}
				if(result.size()>0){
					RaceAdapter adapter = new RaceAdapter(Activity_SearchRace.this, result);
					listView.setAdapter(adapter);
					listView.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {
							// TODO Auto-generated method stub
							Intent intent = new Intent(Activity_SearchRace.this,NewsDetailActivity.class);
							//用Bundle携带数据
						    Bundle bundle=new Bundle();
						    //传递name参数为tinyphp
						    bundle.putString("url", result.get(position).getUrl());
						    intent.putExtras(bundle);
							startActivity(intent);
						}
					});
				}else{
					//datas.clear();
					result.clear();
					Toast.makeText(Activity_SearchRace.this, "未查到结果", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		//line_search = this.findViewById()
		
		
		
		
		
	}
	
}
