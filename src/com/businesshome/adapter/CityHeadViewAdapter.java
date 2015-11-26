package com.businesshome.adapter;

import com.businesshome.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CityHeadViewAdapter extends BaseAdapter{

	private Context context;
	private String[] datas;
	
	public CityHeadViewAdapter(Context context) {
		this.context = context;
		datas = context.getResources().getStringArray(R.array.hot_city);
	}
	
	
	public int getCount() {
		return datas.length;
	}

	public Object getItem(int position) {
		return datas[position];
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		HolderView holder = null;
		
		if(convertView == null){
			holder = new HolderView();
			convertView = LayoutInflater.from(context).inflate(R.layout.city_headview_item, null);
			holder.tv = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		}else{
			holder = (HolderView) convertView.getTag();
		}
		holder.tv.setText(datas[position]);
		
		return convertView;
	}
	
	
	

	class HolderView{
		TextView tv;
	}
}
