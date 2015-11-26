package com.businesshome.ui;

import com.businesshome.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;


public class ChangeColorIconWithTextView extends View {

	/**
	 *
	 * 内存中绘图属性
	 */
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Paint mPaint;
	
	//透明度 0.0-1.0
	private float mAlpha = 0f;
	//限制绘制icon的范围
	private Rect mIconRect;
	private Paint mTextPaint;
	private Rect mTextBound = new Rect();
	
	/**
	 * 下面四个是自定属性的
	 */
	//颜色 先设置默认的
    private int mColor = 0xFF45C01A;
	//图标
	private Bitmap mIconBitmap;
	//icon底部文本
	private String mText = "微信";
	//字体大小
	private int mTextSize = (int) TypedValue.applyDimension(
			TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics());
	
	
	/**
	 * 这个构造方法当在MainActivity代码中创建对象时会被调用 
	 * @param context
	 */
	public ChangeColorIconWithTextView(Context context) {
		super(context);
	}

	/**
	 * 这个构造方法是在什么情况被调用？答：通过xml文件来创建一个view对象的时候调用。很显然xml文件一般是布局文件，就是现实控件的时候调用
	 * 初始化自定义属性值
	 * 
	 * @param context
	 * @param attrs
	 */
	public ChangeColorIconWithTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		// 获取attr我们自定义的那个名字为ChangeColorIconView的那个
		////TypedArray是一个用来存放由context.obtainStyledAttributes获得的属性的数组  在使用完成后，一定要调用recycle方法   
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.ChangeColorIconView);

		int n = a.getIndexCount();//获取个数
		//遍历 给自定义属性设置默认值
		for (int i = 0; i < n; i++) {

			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.ChangeColorIconView_icon:
				BitmapDrawable drawable = (BitmapDrawable) a.getDrawable(attr);
				mIconBitmap = drawable.getBitmap();
				break;
			case R.styleable.ChangeColorIconView_color:
				mColor = a.getColor(attr, 0x45C01A);
				break;
			case R.styleable.ChangeColorIconView_text:
				mText = a.getString(attr);
				break;
			case R.styleable.ChangeColorIconView_text_size:
				mTextSize = (int) a.getDimension(attr, TypedValue
						.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10,
								getResources().getDisplayMetrics()));
				break;

			}
		}

		a.recycle();//回收掉

		mTextPaint = new Paint();
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(0xff555555);
		// 得到text绘制范围
		mTextPaint.getTextBounds(mText, 0, mText.length(), mTextBound);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// 得到绘制icon的宽
		//getMeasuredWidth就是底部每个view的宽度
		//getMeasuredWidth() - getPaddingLeft()- getPaddingRight()=icon图标的宽度
		//getMeasuredHeight() - getPaddingTop()- getPaddingBottom() - mTextBound.height())=view的高度-上下 -字的宽度=icon的高度
		int iconpWidth = Math.min(getMeasuredWidth() - getPaddingLeft()
				- getPaddingRight(), getMeasuredHeight() - getPaddingTop()
				- getPaddingBottom() - mTextBound.height());

		int left = getMeasuredWidth() / 2 - iconpWidth / 2;//计算出来就是除了icon左边或右边距离
		int top = (getMeasuredHeight() - mTextBound.height()) / 2 - iconpWidth
				/ 2;//计算出来就是除了icon上面的距离  因为我们的icon+文本是在中间的
		// 设置icon的绘制范围
		mIconRect = new Rect(left, top, left + iconpWidth, top + iconpWidth);

	}
	/**
	 * 
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		//画笔的alpha是0-255 是整数 所以转为int 
		int alpha = (int) Math.ceil((255 * mAlpha));//Math.ceil向上取整计算
		canvas.drawBitmap(mIconBitmap, null, mIconRect, null);//绘制原图
		setupTargetBitmap(alpha);//制了一个纯绿色的图标
		//绘制源文本 当前view上
		drawSourceText(canvas, alpha);
		//绘制变色的文本
		drawTargetText(canvas, alpha);
		//上面两个方法里面的 alpha  一个mTextPaint.setAlpha(255 - alpha); 一个 mTextPaint.setAlpha(alpha);
		//一个越来越透明 那么另外一个就越来越不透明
		canvas.drawBitmap(mBitmap, 0, 0, null);//最后把内存中两者的交集绘制出啦  覆盖掉上面绘制的原图

	}
	/**
	 * 在内存中绘制可变的icon
	 * 这里绘制了一个纯绿色的图标
	 * @param alpha
	 */
	private void setupTargetBitmap(int alpha) {
		mBitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(),
				Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		mPaint = new Paint();
		mPaint.setColor(mColor);
		mPaint.setAntiAlias(true);//去锯齿
		mPaint.setDither(true);
		mPaint.setAlpha(alpha);
		mCanvas.drawRect(mIconRect, mPaint);
		//canvas原有的图片 可以理解为背景 就是dst 新画上去的图片 可以理解为前景 就是src 
		//我们知道 在正常的情况下，在已有的图像上绘图将会在其上面添加一层新的形状。 如果新的Paint是完全不透明的，那么它将完全遮挡住下面的Paint； 
		//而setXfermode就可以来解决这个问题 
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));//绘制纯色 绘制背景
		mPaint.setAlpha(255);//设回来 设成不透明 也就是画笔是绿色的
		mCanvas.drawBitmap(mIconBitmap, null, mIconRect, mPaint);//把icon绘制上去
	}
	
	/**
	 * 绘制原文本
	 * @param canvas
	 * @param alpha
	 */
	private void drawSourceText(Canvas canvas, int alpha) {
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(0xff333333);
		mTextPaint.setAlpha(255 - alpha);
		canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2
				- mTextBound.width() / 2,
				mIconRect.bottom + mTextBound.height(), mTextPaint);//drawText(String text, float x, float y, Paint paint) //画个图就可以轻松理解


	}
	/**
	 * 绘制变色的
	 * @param canvas
	 * @param alpha
	 */
	private void drawTargetText(Canvas canvas, int alpha) {
		mTextPaint.setColor(mColor);
		mTextPaint.setAlpha(alpha);
		canvas.drawText(mText, mIconRect.left + mIconRect.width() / 2
				- mTextBound.width() / 2,
				mIconRect.bottom + mTextBound.height(), mTextPaint);

	}
	/**
	 * 设置颜色值
	 */
	public void setIconAlpha(float alpha) {
		this.mAlpha = alpha;
		invalidateView();
	}
	
	/**
	 * 重绘
	 */
	private void invalidateView() {
		if (Looper.myLooper() == Looper.getMainLooper() ) {//判断当前线程是否是UI线程(主线程)
			invalidate();// invalidate()方法  说明：invalidate()是用来刷新View的，必须是在UI线程中进行工作   。请求重绘View树，并且只绘制那些“需要重绘的”视图，即谁(是View的话，只绘制该View ；是ViewGroup，则绘制整个 ViewGroup)请求invalidate()方法，就绘制该视图

		} else {//当前是子线程的时候
			postInvalidate();//使用postInvalidate()刷新界面   使用postInvalidate则比较简单，不需要handler，直接在线程中调用postInvalidate即可。
		}
	}

//	public void setIconColor(int color) {
//		mColor = color;
//	}
//
//	public void setIcon(int resId) {
//		this.mIconBitmap = BitmapFactory.decodeResource(getResources(), resId);
//		if (mIconRect != null)
//			invalidateView();
//	}

//	public void setIcon(Bitmap iconBitmap) {
//		this.mIconBitmap = iconBitmap;
//		if (mIconRect != null)
//			invalidateView();
//	}

	private static final String INSTANCE_STATE = "instance_state";
	private static final String STATE_ALPHA = "state_alpha";
	
	//当长期后台运行再打开的时候 或者旋转屏幕的时候 都会重建Activity 而fragment还是之前那个 就会导致 底部view和界面不匹配  所以要使用下面的方法
	
	/**
	 * 
	 */
	@Override
	protected Parcelable onSaveInstanceState() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
		bundle.putFloat(STATE_ALPHA, mAlpha);
		return bundle;
	}

	/**
	 * 
	 */
	@Override
	protected void onRestoreInstanceState(Parcelable state) {
		if (state instanceof Bundle) {
			Bundle bundle = (Bundle) state;
			mAlpha = bundle.getFloat(STATE_ALPHA);
			super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
		} else {
			super.onRestoreInstanceState(state);
		}

	}

}
