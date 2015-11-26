package com.businesshome.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.TextView;
/**
 * <pre>��ʾ���� </pre>
 * 
 */
public class CWAddressListView extends ListView implements OnScrollListener{
    //����״̬
    public static final int PINNED_HEADER_GONE = 0;
    //��ʾ״̬
    public static final int PINNED_HEADER_VISIBLE = 1;
   // ��ʾmHeaderView������״̬
    public static final int PINNED_HEADER_PUSHED_UP = 2;
    //��ʾmHeaderView������״̬
    public static final int PINNED_HEADER_PUSHED_DOWN = 3;
    
    private View mHeaderView;//���ϱߵ�ͷ��ǩ
    private int mHeaderViewId;//ͷ��ǩ��id -- R.id.XXXX
    private boolean mHeaderViewVisible = false;//�Ƿ���ʾͷ��ǩ
    private int mHeaderViewY = 0;//ͷ��ǩӦ����ʾ��Y������
    private int mHeaderViewAlpha = 255;//ͷ��ǩ��͸����
    private int lastBottom = 0;//����ͷ��ǩӦ����ʾ��Y������
    
    private String mHeaderViewText = "A";
    private int mHeaderViewstate = PINNED_HEADER_VISIBLE;
    
    private int mHeaderViewWidth;
    private int mHeaderViewHeight;
    
    public CWAddressListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setOnScrollListener(this);//��listview����OnScrollListener
    }

    public CWAddressListView(Context context) {
        super(context);
        this.setOnScrollListener(this);//��listview����OnScrollListener
    }

    public CWAddressListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setOnScrollListener(this);//��listview����OnScrollListener
    }
    
    /**
     * ����ͷ��ǩ�ؼ�
     * @param context �����Ķ���
     * @param layout ͷ��ǩ��layout
     * @param viewIdͷ��ǩ��ID
     */
    public void setPinnedHeaderView(Context context, int layout, int textViewId) {
        //���ݿؼ�layout�������Ķ�������ͷ��ǩVIew
        mHeaderView = LayoutInflater.from(context).inflate(layout, this, false);
        mHeaderViewId = textViewId;//�����ı��ؼ���id
        // Disable vertical fading when the pinned header is present
        // TODO change ListView to allow separate measures for top and bottom fading edge;
        // in this particular case we would like to disable the top, but not the bottom edge.
        if (mHeaderView != null) {
            setFadingEdgeLength(0);//���ñ�Ե��ɫ��û�����õ��϶�listview���±�Ե�������ɫ
        }
        requestLayout();//��������
     }

     @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mHeaderView != null) {
            measureChild(mHeaderView, widthMeasureSpec, heightMeasureSpec);
            mHeaderViewWidth = mHeaderView.getMeasuredWidth();//���ͷ��ǩ�Ŀ��
            mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        }
    }
     
     @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mHeaderView != null) {
            mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);//������ͷ��ǩ����ʾλ��
            configureHeaderView();//������ͷ��ǩ����ʾλ�ã���ɫ��͸��������ȣ�
        }
    }
    
     @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHeaderViewVisible && mHeaderView != null) {
            drawChild(canvas, mHeaderView, getDrawingTime());//����ͷ��ǩ��listview��
        }
    }
     
     /**
      * ����ͷ��ǩ����ʾλ�ã���ɫ��͸��������ȣ�
      * @param position
      */
     public void configureHeaderView() {
            if (mHeaderView == null) {
                return;
            }
            switch (mHeaderViewstate) {
                case PINNED_HEADER_GONE: {//ͷ��ǩ����ʾ
                    mHeaderViewVisible = false;
                    break;
                }
//                case PINNED_HEADER_VISIBLE: {//ͷ��ǩ��ʾ��Ĭ��״̬��
//                    configurePinnedHeader(mHeaderView, 255);//����ͷ��ǩ�����ֺ�͸���ȵ�
//                    if (mHeaderView.getTop() != 0) {
//                        mHeaderView.layout(0, 0, mHeaderViewWidth, mHeaderViewHeight);//����ͷ��ǩ����ʾλ�ã������Ĭ��ԭʼ״̬��
//                    }
//                    mHeaderViewVisible = true;
//                    break;
//                }
//                case PINNED_HEADER_PUSHED_UP: {//ͷ��ǩ����״̬
//                     mHeaderView.layout(0, mHeaderViewY, mHeaderViewWidth, mHeaderViewHeight + mHeaderViewY);
//                     mHeaderViewVisible = true;
//                     break;
//                }
//                case PINNED_HEADER_PUSHED_DOWN://ͷ��ǩ������״̬
//                     mHeaderView.layout(0, mHeaderViewY, mHeaderViewWidth, mHeaderViewHeight + mHeaderViewY);
//                     mHeaderViewVisible = true;
//                     break;
                default:
                	break;
            }
        }
     /**
      * ����ͷ��ǩ����ʾ�ı���״̬
      * @param mHeaderViewText �ı�����
      * @param mHeaderViewstate ��ǩ״̬
      */
     public void setHeaderViewTextAndState(String mHeaderViewText, int mHeaderViewstate)
     {
         this.mHeaderViewText = mHeaderViewText;
         this.mHeaderViewstate = mHeaderViewstate;
         refreshView(getChildCount());//ˢ��ͷ��ǩ
     }
     
    /**
     * �����Ƿ���ʾͷ��ǩ�Ƿ���ʾ
     * @param mHeaderViewVisible
     */
    public void setmHeaderViewVisible(boolean mHeaderViewVisible) {
        this.mHeaderViewVisible = mHeaderViewVisible;
    }
     /**
      * ����ͷ��ǩ
      * @param header ͷ��ǩ
      * @param alpha ͸����
      */
    public void configurePinnedHeader(View header, int alpha) {
        TextView lSectionHeader = (TextView)header;
        lSectionHeader.setText(mHeaderViewText);
        lSectionHeader.setBackgroundColor(Color.rgb(154, 171, 194));
        lSectionHeader.setTextColor(Color.rgb(255, 255, 255));
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
            int visibleItemCount, int totalItemCount) {
        refreshView(visibleItemCount);//ˢ��ͷ��ǩ
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub
//      System.out.println("scrollState :" + scrollState);
    }
    /**
     * ˢ��ͷ��ǩ
     * @param visibleItemCount listview��ʾ������
     */
    private void refreshView(int visibleItemCount)
    {
        
        View firstView = getChildAt(0);//�����ʾ�ĵ�һ���ؼ�
        View secondView = null;
        if(visibleItemCount > 2)
        {
            secondView = getChildAt(1);//�����ʾ�ĵڶ����ؼ�
        }
        if(secondView == null)//�޵ڶ��Ҳ��û���϶��ı�Ҫ�����ý��ж�ͷ��ǩ�Ļ��Ʊ仯
        {
            configureHeaderView();//ˢ��ͷ��ǩ��Ȼ��return
            return;
        }
        //��õ�һ�͵ڶ�����ı�ǩ
        View firstTvCatalog = firstView.findViewById(mHeaderViewId);
        View secondTvCatalog = secondView.findViewById(mHeaderViewId);
        if(firstTvCatalog == null || secondTvCatalog == null)//��
        {
            configureHeaderView();//ˢ��ͷ��ǩ��Ȼ��return
            return;
        }
        
        if(secondTvCatalog.getVisibility() == View.GONE && firstTvCatalog.getVisibility() == View.GONE)
        {
            mHeaderViewstate = PINNED_HEADER_VISIBLE;//�ָ�Ĭ�ϵ�״̬
        }
        
        if (firstView != null) {//��һ�����
            mHeaderViewText = (String) firstTvCatalog.getTag();//��õ�һ��ı�ǩ������ı�
            int firstBottom = firstView.getBottom();//��õ�һ��ĵײ����붥���ĸ߶�
            int headerHeight = mHeaderView.getHeight();//���ͷ��ǩ�ĸ߶�
          //����һ��ĵײ����붥���ĸ߶ȱ� ͷ��ǩ�ĸ߶�Сʱ��˵��ͷ��ǩ��λ����Ҫ����ʹ��һ������ǩ������ͷ��ǩճ��һ��
            if (firstBottom < headerHeight) {
                mHeaderViewY = (firstBottom - headerHeight);//ͷ��ǩ��ʾ��Y��λ��
                //͸���ȸ���ͷ��ǩ���ĸ߶�����
                mHeaderViewAlpha = 255 * (headerHeight + mHeaderViewY) / headerHeight;
            } else {
                mHeaderViewY = 0;
                mHeaderViewAlpha = 255;
            }
           
            if(secondTvCatalog.getVisibility() == View.VISIBLE)//�ڶ���ı�ǩ����
            {
                ////ͷ��ǩ��top��С��mHeaderViewY���ȡ�˵����Ҫ�ƶ�ͷ��ǩ
                if (mHeaderView.getTop() != mHeaderViewY) {
                    if(lastBottom > mHeaderViewY)//˵����ͷ��ǩ����
                    {
                        mHeaderViewstate = PINNED_HEADER_PUSHED_UP;
                    }else if(lastBottom < mHeaderViewY)//ͷ��ǩ����
                    {
                        mHeaderViewstate = PINNED_HEADER_PUSHED_DOWN;
                    }else//û�� ά��ԭ״̬
                    {
                        
                    }
                    
                    lastBottom = mHeaderViewY;
                }else if(headerHeight == firstBottom)//mHeaderView�ײ�����һ����ǩ�����غ�
                {
                    mHeaderViewstate = PINNED_HEADER_VISIBLE;
                }
            } 
            
            if(firstTvCatalog.getVisibility() == View.VISIBLE)
            {
                if(firstView.getTop() == 0)//mHeaderView��listview�ı�ǩ�غ�
                {
                    mHeaderViewstate = PINNED_HEADER_VISIBLE;
                }
                if(secondTvCatalog.getVisibility() == View.GONE)//firstTvCatalog��ʾ��secondTvCatalog����ʾ��˵������ά��ͷ��ǩ�ı仯������Ĭ��״̬
                {
                    mHeaderViewstate = PINNED_HEADER_VISIBLE;
                }
            }
            configurePinnedHeader(mHeaderView, mHeaderViewAlpha);
            configureHeaderView();
        }
    }
}
