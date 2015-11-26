package com.businesshome.adapter;

import java.util.List;
import java.util.Map;
import com.businesshome.R;
import com.businesshome.model.News;
import com.businesshome.model.Policy;
import com.businesshome.util.MyLoader;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PolicyAdapter extends BaseAdapter {

	private Context context;
	private List<Policy> datas;
	public PolicyAdapter(Context context,List<Policy> datas){
		this.context = context;
		this.datas = datas;
	}
	// 更新适配器
		public void update(List<Policy> list) {
			this.datas = list;
			notifyDataSetChanged();
		}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final HolderView holderView;
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.item_policy, null);
			holderView = new HolderView();
			holderView.imageView = (ImageView) convertView.findViewById(R.id.policy_img);
			holderView.titleTextView = (TextView) convertView.findViewById(R.id.policy_title);
			holderView.dianjiTextView = (TextView) convertView.findViewById(R.id.policy_zang);
			convertView.setTag(holderView);
		}else {
			holderView = (HolderView) convertView.getTag();
		}
		//String imgurl = datas.get(position);
		//holderView.imageView.setTag(imgurl);
		
		
		//if(!imgurl.equals(" ") && imgurl!=null){
			//if (imgurl.equals(holderView.imageView.getTag())) {
			//	MyLoader.loader.displayImage(imgurl, holderView.imageView);
			//	}else{
					holderView.imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.app_icon));
			//	}
		//}
		
		//holderView.imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.app_icon));
		holderView.titleTextView.setText(datas.get(position).getTitle().toString());
		holderView.dianjiTextView.setText(datas.get(position).getZang()+"");
		
		
holderView.dianjiTextView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int a = datas.get(position).getZang() + 1;
				datas.get(position).setZang(a);
				holderView.dianjiTextView.setText(datas.get(position).getZang()+"");
			}
		});
		return convertView;
	}
	class HolderView {
		TextView titleTextView,dianjiTextView;
		ImageView imageView;
	}

}
