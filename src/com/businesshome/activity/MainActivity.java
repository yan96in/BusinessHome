package com.businesshome.activity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.businesshome.R;
import com.businesshome.fragment.HomeFragment;
import com.businesshome.fragment.PolicyFragment;
import com.businesshome.fragment.RaceFragment;
import com.businesshome.fragment.TalkBarFragment;
import com.businesshome.ui.ChangeColorIconWithTextView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommConfig;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.beans.Topic;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.listeners.Listeners.FetchListener;
import com.umeng.comm.core.login.LoginListener;
import com.umeng.comm.core.login.Loginable;
import com.umeng.comm.core.nets.responses.AlbumResponse;
import com.umeng.comm.core.nets.responses.FeedsResponse;
import com.umeng.comm.core.nets.responses.TopicResponse;
import com.umeng.comm.core.nets.responses.UsersResponse;
import com.umeng.comm.core.sdkmanager.LocationSDKManager;
import com.umeng.comm.core.sdkmanager.LoginSDKManager;
import com.umeng.comm.ui.fragments.CommunityMainFragment;
import com.umeng.comm.ui.location.DefaultLocationImpl;

import android.annotation.SuppressLint;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;


public class MainActivity extends FragmentActivity implements
		OnPageChangeListener, OnClickListener {
	
	private CommunitySDK communitySDK = null;
	String topicId = "";
	
	private ViewPager mViewPager;
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;

	private String[] mTitles = new String[] { "First Fragment!",
			"Second Fragment!", "Third Fragment!" };

	private List<ChangeColorIconWithTextView> mTabIndicator = new ArrayList<ChangeColorIconWithTextView>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		//Umeng社區的初始化
		communitySDK = CommunityFactory.getCommSDK(this);
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		
		communitySDK.initSDK(this);
		 // =================== 自定义设置部分 =================
        // 在初始化CommunitySDK之前配置推送和登录等组件
        //useSocialLogin();

        // 使用自定义的ImageLoader
        // useMyImageLoader();

        // 使用自定义的登录系统
        // useCustomLogin();

        //initPlatforms(this);
        // 设置地理位置SDK
        LocationSDKManager.getInstance().addAndUse(new DefaultLocationImpl());
       // PushSDKManage.
		// setOverflowShowingAlways();//进行调用设置actionbar的显示图标
		// getActionBar().setDisplayShowHomeEnabled(false);//把actionbar上面的默认显示的菜单图标隐藏掉
		//

		initView();

		initDatas();

		PushManager.startWork(getApplicationContext(),PushConstants.LOGIN_TYPE_API_KEY,"TzYnNMwPP3pNVdrs5VxXpXOw");
		PushManager.enableLbs(getApplicationContext());
		mViewPager.setAdapter(mAdapter);
		mViewPager.setOnPageChangeListener(this);
		
		
	}

	private void initDatas() {

		HomeFragment homeFragment = new HomeFragment();
		PolicyFragment policyFragment = new PolicyFragment();
		RaceFragment raceFragment = new RaceFragment();
		//TalkBarFragment talkBarFragment = new TalkBarFragment();
		
		CommunityMainFragment talkBarFragment = new CommunityMainFragment();
		talkBarFragment.setBackButtonVisibility(View.INVISIBLE);
		
		mTabs.add(homeFragment);
		mTabs.add(talkBarFragment);
		mTabs.add(raceFragment);
		mTabs.add(policyFragment);

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int position) {
				return mTabs.get(position);
			}
		};

	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);

		ChangeColorIconWithTextView one = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_one);
		ChangeColorIconWithTextView two = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_two);
		ChangeColorIconWithTextView three = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_three);
		ChangeColorIconWithTextView four = (ChangeColorIconWithTextView) findViewById(R.id.id_indicator_four);

		mTabIndicator.add(one);
		mTabIndicator.add(two);
		mTabIndicator.add(three);
		mTabIndicator.add(four);

		
		
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);

		one.setIconAlpha(1.0f);// 设置了默认的第一个颜色的颜色
	}

	/**
     * 一些常用的接口以及获取推荐的数据接口
     */
    void someMethodsDemo() {
        // 主动登录
    	communitySDK.login(getApplicationContext(), new LoginListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(int stCode, CommUser userInfo) {

            }
        });

        // 获取登录SDK Manager
        LoginSDKManager manager = LoginSDKManager.getInstance();
        Loginable currentLoginable = manager.getCurrentSDK();
        // 是否登录
        currentLoginable.isLogined(getApplicationContext());

        // 未登录下获取话题
        communitySDK.fetchTopics(new FetchListener<TopicResponse>() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(TopicResponse response) {
                for (Topic item : response.result) {
                    Log.e("", "### topic id : " + item.id + ", name = " + item.name);
                    topicId = item.id;
                }

            }
        });

        // 未登录情况下获取某个话题下的feed
        communitySDK.fetchTopicFeed(topicId, new
                FetchListener<FeedsResponse>() {

                    @Override
                    public void onComplete(FeedsResponse response) {
                        Log.e("", "### 未登录下获取到某个topic下的feed : " + response.result.size());
                        for (FeedItem item : response.result) {
                            Log.e("", "### topic feed id : " + item.id + ", name = " +
                                    item.text);
                        }

                    }

                    @Override
                    public void onStart() {
                    }
                });

        // 推荐的feed
        communitySDK.fetchRecommendedFeeds(new FetchListener<FeedsResponse>() {

            @Override
            public void onComplete(FeedsResponse response) {
                Log.e("", "### 推荐feed  code : " + response.errCode + ", msg = " + response.errMsg);
                for (FeedItem item : response.result) {
                    Log.e("", "### 推荐feed id : " + item.id + ", name = " + item.text);
                }
            }

            @Override
            public void onStart() {

            }
        });

        // 获取推荐的话题
        communitySDK.fetchRecommendedTopics(new FetchListener<TopicResponse>() {

            @Override
            public void onComplete(TopicResponse response) {
                Log.e("", "### 推荐的话题 : ");
                for (Topic item : response.result) {
                    Log.e("", "### 话题 : " + item.name);
                }
            }

            @Override
            public void onStart() {

            }
        });

        // 获取某个话题活跃的用户
        communitySDK.fetchActiveUsers("541fe6f40bbbaf4f41f7aa3f", new FetchListener<UsersResponse>() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(UsersResponse response) {
                Log.e("", "### 某个话题的活跃用户 : ");
                for (CommUser user : response.result) {
                    Log.e("", "### 活跃用户 : " + user.name);
                }
            }
        });

        // 获取某用户的相册,也就是发布feed上传的所有图片
        communitySDK.fetchAlbums(CommConfig.getConfig().loginedUser.id,
                new FetchListener<AlbumResponse>() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onComplete(AlbumResponse response) {
                        Log.e("", "### response size : " + response.result.size());
                    }
                });

        // 搜索周边的feed
        communitySDK.searchFeedNearby(116.3758540000f, 39.9856970000f,
                new FetchListener<FeedsResponse>() {

                    @Override
                    public void onComplete(FeedsResponse response) {
                        Log.e("", "### 周边的feed : " + response.result.size());
                    }

                    @Override
                    public void onStart() {

                    }

                });
    }
	@Override
	public void onPageSelected(int arg0) {
	}

	/**
	 * positionOffset是设置颜色的
	 */
	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {// 从第1页到2页的时候 position 始终为0
										// positionOffset从0-1 从第2页到1页的时候
										// position 始终为0 positionOffset从1-0
		// Log.e("TAG", "position = " + position + " , positionOffset = "
		// + positionOffset);

		if (positionOffset > 0) {
			ChangeColorIconWithTextView left = mTabIndicator.get(position);
			ChangeColorIconWithTextView right = mTabIndicator.get(position + 1);

			left.setIconAlpha(1 - positionOffset);// 变小
			right.setIconAlpha(positionOffset);
		}

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}

	@Override
	public void onClick(View v) {

		resetOtherTabs();//

		switch (v.getId()) {
		case R.id.id_indicator_one:
			mTabIndicator.get(0).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(0, false);
			break;
		case R.id.id_indicator_two:
			mTabIndicator.get(1).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(1, false);
			break;
		case R.id.id_indicator_three:
			mTabIndicator.get(2).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(2, false);
			break;
		case R.id.id_indicator_four:
			mTabIndicator.get(3).setIconAlpha(1.0f);
			mViewPager.setCurrentItem(3, false);
			break;
		}

	}

	/**
	 * 
	 * 
	 * 这个方法是设置每个底部view的颜色
	 */
	private void resetOtherTabs() {
		for (int i = 0; i < mTabIndicator.size(); i++) {
			mTabIndicator.get(i).setIconAlpha(0);// 不是绿色 之前的颜色咯
		}
	}

	/**
	 * 打开菜单的时候 设置icon 使用反射
	 */
	// @Override
	// public boolean onMenuOpened(int featureId, Menu menu) {
	// if (featureId == Window.FEATURE_ACTION_BAR && menu != null)
	// {//这里也就是在actionbar打开菜单的时候
	// if (menu.getClass().getSimpleName().equals("MenuBuilder"))
	// {//MenuBuilder是menu的实现类
	// try {
	// Method m =
	// menu.getClass().getDeclaredMethod(//获取其中的一个方法setOptionalIconsVisible
	// "setOptionalIconsVisible", Boolean.TYPE);
	// m.setAccessible(true);
	// m.invoke(menu, true);
	// } catch (Exception e) {
	// }
	// }
	// }
	// return super.onMenuOpened(featureId, menu);
	// }

	/**
	 * 
	 * 把actionBar上默认的那个菜单图标改成我们自己的图标“+”号的图标 在style里面我们先定义了显示的样式 这里再写个方法 才可以改变图标
	 * 
	 * 不写样式只写这里 或者 只写样式 不写这里 都是不能改变图标 使用反射
	 */

	// private void setOverflowShowingAlways() {
	// try {
	// // true if a permanent menu key is present, false otherwise.
	// ViewConfiguration config = ViewConfiguration.get(this);
	// Field menuKeyField = ViewConfiguration.class
	// .getDeclaredField("sHasPermanentMenuKey");//找到menukey
	// menuKeyField.setAccessible(true);
	// menuKeyField.setBoolean(config, false);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

}
