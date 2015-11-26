package com.businesshome.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.businesshome.R;
import com.businesshome.adapter.CityHeadViewAdapter;
import com.businesshome.datas.DBManager;
import com.businesshome.model.CityModel;
import com.businesshome.ui.MyGridView;
import com.businesshome.ui.MyLetterListView;
import com.businesshome.ui.MyLetterListView.OnTouchingLetterChangedListener;

/**
 * 
 * @author scuch
 *
 */
public class CityList extends Activity
{
    private BaseAdapter adapter;

    private ListView mCityLit;

    private TextView overlay;

    private MyLetterListView letterListView;

    private HashMap<String, Integer> alphaIndexer;// 

    private String[] sections;// 

    private Handler handler;

    private OverlayThread overlayThread;

    private SQLiteDatabase database;

    private ArrayList<CityModel> mCityNames;
    
    private MyGridView mGridView;
    
    private Button cityButton = null;
	private String loc = null; // 
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_list);

        mCityLit = (ListView) findViewById(R.id.city_list);
        //mCityLit.setmHeaderViewVisible(true);
//        mCityLit.setPinnedHeaderView(this, R.layout.title,
//                R.id.contactitem_catalog);
        letterListView = (MyLetterListView) findViewById(R.id.cityLetterListView);
        DBManager dbManager = new DBManager(this);
        dbManager.openDateBase();
        dbManager.closeDatabase();
        database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/"
                + DBManager.DB_NAME, null);
        Log.e("x", "存储路径="+DBManager.DB_PATH + "/"
                + DBManager.DB_NAME);
        mCityNames = getCityNames();
        database.close();
        letterListView
                .setOnTouchingLetterChangedListener(new LetterListViewListener());
        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
        overlayThread = new OverlayThread();
        initOverlay();
        
        
        mCityLit.addHeaderView(getHeadView());
        
        
        setAdapter(mCityNames);
        mCityLit.setOnItemClickListener(new CityListOnItemClick());

    }

    private View getHeadView() {
		View view = LayoutInflater.from(this).inflate(R.layout.city_headview,
				null);
		mGridView = (MyGridView) view.findViewById(R.id.gv_headview_city);
		final String[] datas = this.getResources().getStringArray(R.array.hot_city);
		mGridView.setAdapter(new CityHeadViewAdapter(this));
		
		
		mLocationClient = new LocationClient(getApplicationContext()); //
		mLocationClient.registerLocationListener(myListener); // 
		cityButton = (Button) view.findViewById(R.id.cityButton);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开GPS
		option.setAddrType("all");// 
		option.setCoorType("bd09ll");// 
		option.setScanSpan(3000);// 
		option.disableCache(false);// 
		option.setPriority(LocationClientOption.NetWorkFirst);// 
		mLocationClient.setLocOption(option);// 
		mLocationClient.start();// 
		mLocationClient.requestLocation();// 
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
	            cityButton.setText(datas[position]);
	            stopListener();
	            Intent intent = new Intent();
	            String city = cityButton.getText().toString();
	            intent.putExtra("city", city);
	            CityList.this.setResult(RESULT_OK,intent);
	            CityList.this.finish();
				
			}
//
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				if (isnext == 7) {
//					String city = cities.get(arg2 - 1).getName();
//					AppContext appContext = (AppContext) getApplicationContext();
//					appContext.setCity(city);
//					startActivity(new Intent(SelectCityActivity.this,
//							MainActivity.class));
//					finish();
//				} else {
//					String city = cities.get(arg2 - 1).getName();
//					Intent intent = new Intent(SelectCityActivity.this,
//							MainActivity.class);
//					intent.putExtra("city", city);
//					setResult(1, intent);
//					finish();
//				}
//			}
		});
		return view;
	}

	
    private ArrayList<CityModel> getCityNames()
    {
        ArrayList<CityModel> names = new ArrayList<CityModel>();
        Cursor cursor = database.rawQuery(
                "SELECT * FROM T_City ORDER BY NameSort", null);
        for (int i = 0; i < cursor.getCount(); i++)
        {
            cursor.moveToPosition(i);
            CityModel cityModel = new CityModel();
            cityModel.setCityName(cursor.getString(cursor
                    .getColumnIndex("CityName")));
            cityModel.setNameSort(cursor.getString(cursor
                    .getColumnIndex("NameSort")));
            names.add(cityModel);
        }
        return names;
    }

 
    class CityListOnItemClick implements OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
                long arg3)
        {
            CityModel cityModel = (CityModel) mCityLit.getAdapter()
                    .getItem(pos);
            
            cityButton.setText(cityModel.getCityName());
            stopListener();
            Toast.makeText(CityList.this, cityModel.getCityName(),
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            String city = cityButton.getText().toString();
            intent.putExtra("city", city);
            CityList.this.setResult(RESULT_OK,intent);
            CityList.this.finish();
        }
    }

    /**
     * ΪListView
     * 
     * @param list
     */
    private void setAdapter(List<CityModel> list)
    {
        if (list != null)
        {
            adapter = new ListAdapter(this, list);
            mCityLit.setAdapter(adapter);
        }

    }

    /**
     * ListViewAdapter
     * 
     */
    private class ListAdapter extends BaseAdapter
    {
        private LayoutInflater inflater;

        private List<CityModel> list;

        public ListAdapter(Context context, List<CityModel> list)
        {
            this.inflater = LayoutInflater.from(context);
            this.list = list;
            CityModel cityModel = new CityModel();
            cityModel.setCityName("成都");
            cityModel.setNameSort("#");
            list.add(0, cityModel);
            CityModel cityMode2 = new CityModel();
            cityMode2.setCityName("北京");
            cityMode2.setNameSort("#");
            list.add(1, cityMode2);
            CityModel cityMode3 = new CityModel();
            cityMode3.setCityName("合肥");
            cityMode3.setNameSort("#");
            list.add(2, cityMode3);
            alphaIndexer = new HashMap<String, Integer>();
            sections = new String[list.size()];
            for (int i = 0; i < list.size(); i++)
            {
                // getAlpha(list.get(i));
                String currentStr = list.get(i).getNameSort();
                String previewStr = (i - 1) >= 0 ? list.get(i - 1)
                        .getNameSort() : " ";
                if (!previewStr.equals(currentStr))
                {
                    String name = list.get(i).getNameSort();
                    alphaIndexer.put(name, i);
                    sections[i] = name;
                }
            }
            System.out.println("alphaIndexer  =" + alphaIndexer.toString());
        }

        @Override
        public int getCount()
        {
            return list.size();
        }

        @Override
        public Object getItem(int position)
        {
            return list.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder holder;
            if (convertView == null)
            {
                convertView = inflater.inflate(R.layout.list_item, null);
                holder = new ViewHolder();
                holder.alpha = (TextView) convertView
                        .findViewById(R.id.contactitem_catalog);
                holder.name = (TextView) convertView
                        .findViewById(R.id.contactitem_nick);
                convertView.setTag(holder);
            }
            else
            {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.name.setText(list.get(position).getCityName());
            String currentStr = list.get(position).getNameSort();
            String previewStr = (position - 1) >= 0 ? list.get(position - 1)
                    .getNameSort() : " ";
            if (!previewStr.equals(currentStr))
            {
                holder.alpha.setVisibility(View.VISIBLE);
                holder.alpha.setText(currentStr);
            }
            else
            {
                holder.alpha.setVisibility(View.GONE);
            }
            return convertView;
        }

        private class ViewHolder
        {
            TextView alpha;

            TextView name;
        }

    }

    private void initOverlay()
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        WindowManager windowManager = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    private class LetterListViewListener implements
            OnTouchingLetterChangedListener
    {

        @Override
        public void onTouchingLetterChanged(final String s)
        {
            if (alphaIndexer.get(s) != null)
            {
                int position = alphaIndexer.get(s);
                // mCityLit.setSelection(position);
                // overlay.setText(sections[position]);
                // overlay.setVisibility(View.VISIBLE);
                // handler.removeCallbacks(overlayThread);
                // handler.postDelayed(overlayThread, 1500);
                if (android.os.Build.VERSION.SDK_INT >= 11)
                {
                    mCityLit.smoothScrollToPositionFromTop(position, 0, 300);
                }
                else if (android.os.Build.VERSION.SDK_INT >= 8)
                {
                    int firstVisible = mCityLit.getFirstVisiblePosition();
                    int lastVisible = mCityLit.getLastVisiblePosition();
                    if (position < firstVisible)
                        mCityLit.smoothScrollToPosition(position);
                    else
                        mCityLit.smoothScrollToPosition(position + lastVisible
                                - firstVisible - 2);
                }
                else
                {
                    mCityLit.setSelectionFromTop(position, 0);
                }

            }
            
//            Intent intent = new Intent();
//            String city = cityButton.getText().toString();
//            intent.putExtra("city", city);
//            CityList.this.setResult(RESULT_OK,intent);
//            CityList.this.finish();
        }

    }

    private class OverlayThread implements Runnable
    {

        @Override
        public void run()
        {
            overlay.setVisibility(View.GONE);
        }

    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		stopListener();
		super.onDestroy();
	}

	private void stopListener() {
		// TODO Auto-generated method stub
		if (mLocationClient != null && mLocationClient.isStarted())
		{
			mLocationClient.stop();// �رն�λSDK
			mLocationClient = null;
		}
	}
	
	
	public class MyLocationListener implements BDLocationListener
	{

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location != null)
			{
				StringBuffer sb = new StringBuffer(128);// ���ܷ��񷵻صĻ�����
				sb.append(location.getCity());// ��ó���
				loc = sb.toString().trim();
				Log.e("xtn", "��λ�ĳ���="+loc.toString());
				cityButton.setText(loc);
			} else
			{
				cityButton.setText("�޷���λ");
				return;
			}
		}

		@Override
		public void onReceivePoi(BDLocation arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
			Intent intent = new Intent();
            String city = cityButton.getText().toString();
            intent.putExtra("city", city);
            CityList.this.setResult(RESULT_OK,intent);
            CityList.this.finish();
			return false; 
			}
		return super.onKeyDown(keyCode, event);
	}

}