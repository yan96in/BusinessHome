package com.businesshome.adapter;

import java.util.List;
import java.util.Map;
import com.businesshome.R;
import com.businesshome.model.News;
import com.businesshome.util.MyLoader;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ZixunAdapter extends BaseAdapter {

	private Context context;
	private List<News> datas;
	public ZixunAdapter(Context context,List<News> datas){
		this.context = context;
		this.datas = datas;
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
			convertView = LayoutInflater.from(context).inflate(R.layout.item_zixun, null);
			holderView = new HolderView();
			holderView.imageView = (ImageView) convertView.findViewById(R.id.zixun_img);
			holderView.titleTextView = (TextView) convertView.findViewById(R.id.zixun_title);
			holderView.dianjiTextView = (TextView) convertView.findViewById(R.id.zixun_zang);
			convertView.setTag(holderView);
		}else {
			holderView = (HolderView) convertView.getTag();
		}
		String imgUrl = "http://" + datas.get(position).getImgAddr();
		holderView.imageView.setTag(imgUrl);

		if (imgUrl.equals(holderView.imageView.getTag())) {
			// ImageLoader.ImageListener imageListener =
			// MyImageLoader.imageLoader
			// .getImageListener(holder.ivHead, R.drawable.defaul_head,
			// R.drawable.defaul_head);
			// MyImageLoader.imageLoader.get(imageUrl, imageListener);

			MyLoader.loader.displayImage(imgUrl, holderView.imageView);
		}
		//holderView.imageView.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher));
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
