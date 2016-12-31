package com.example.bannershowing;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.bannershowing.widget.Indicator;
import com.example.bannershowing.widget.adapters.BannerPagerAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的轮播控件 包含ViewPager和LinearLayout
 *  设置图片资源的代码要在其他设置的后面调用才能正常工作
 */

public class Banner extends FrameLayout implements Handler.Callback,
        ViewPager.OnPageChangeListener, View.OnClickListener {
    //自动播放的标志
    public static final int AUTO_PLAY = 5<<5;
    //Indicator默认的未选中的颜色
    public int defaultDotColor = Color.GRAY;
    //Indicator和ViewPager联动时的颜色
    public int selectedDotColor = Color.WHITE;
    //Indicator的大小
    public int dotSize = 15;
    //发送消息的Handler
    private Handler mHandler;
    //默认的轮播间隔
    private int interval = 5000;
    //ViewPager
    private ViewPager mViewPager;
    //ViewPager的样式 默认CENTER_CROP
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.CENTER_CROP;
    //存放Indicator的容器
    private LinearLayout dotContainer;
    //网络请求中的占位图片
    private Drawable holderDrawable;
    //网络请求错误显示的图片
    private Drawable errorDrawable;
    //适配器
    private BannerPagerAdapter mAdapter;
    //数据源
    private List<ImageView> mData;
    //存放Indicator的集合
    private List<Indicator> mDots;
    //当前ViewPager的位置
    private int currentPagerPsn;
    //当前Indicator的位置
    private int currentDotPsn;

    public Banner(Context context) {
        this(context,null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mHandler = new Handler(this);
        mData = new ArrayList<>();
        mDots = new ArrayList<>();
        mAdapter = new BannerPagerAdapter(mData);

        initContent(context);
    }

    /**
     * //设置ViewPager的点击事件
     */
    public void addOnViewPagerItemClickListener(BannerPagerAdapter.IOnImageClickListener listener) {
        mAdapter.addOnImageClickListener(listener);
    }

    /**
     * ViewPager 的长按事件
     * @param listener
     */
    public void addOnViewPagerItemLongClickListener(BannerPagerAdapter.IOnImageLongClickListener listener){
        mAdapter.addOnImageLongClickListener(listener);
    }

    /**
     * 初始化ViewPager和LinearLayout
     * @param context
     */
    private void initContent(Context context) {

        FrameLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        //初始化ViewPager
        mViewPager = new ViewPager(context);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        addView(mViewPager,params);

        //初始化盛放Indicator的容器
        dotContainer = new LinearLayout(context);
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
        params.bottomMargin = 10;
        addView(dotContainer,params);
    }

    /**
     * 设置ViewPager的图片资源
     * 该方法要在其他方法的后面调用
     * @param params 图片的网址
     */
    public void setImages(String...params){
        //初始化Picasso
        Picasso picasso = Picasso.with(getContext());
        //存放从网络获取的图片
        List<ImageView> list = new ArrayList<>();
        //开始从网络加载图片
        for (int i = 0; i < params.length; i++) {

            ImageView imageView = new ImageView(getContext());

            imageView.setScaleType(mScaleType);

            picasso.load(params[i]).placeholder(holderDrawable==null?new ColorDrawable():holderDrawable).
                    error(errorDrawable==null?new ColorDrawable():errorDrawable).into(imageView);

            list.add(imageView);
        }
        //设置从网络获取的图片
        setImages(list);
    }

    /**
     * 设置Indicator选中时的颜色
     * @param color
     */
    public void setSelectedIndicatorColor(int color){
        selectedDotColor = color;
    }

    /**
     * 设置Indicator的默认的颜色
     * @param color
     */
    public void setDefaultIndicatorColor(int color){
        defaultDotColor = color;
    }

    /**
     * 设置Indicator的直径
     * 如果小于15,则使用默认值
     * @param size 直径
     */
    public void setIndicatorSize(int size){
        if (size<=15){
            return;
        }
        dotSize = size;
    }

    /**
     * 设置ViewPager的轮播间隔时间
     * @param interval 间隔时间(毫秒)
     */
    public void setPlayInterval(int interval){
        this.interval = interval;
    }

    /**
     * 设置要显示的图片的ScaleType
     * @param scaleType
     */
    public void setScaleType(ImageView.ScaleType scaleType){
        this.mScaleType = scaleType;
    }

    /**
     * 设置加载图片过程的占位图片
     * @param holderDrawable 占位图片
     */
    public void setHolderDrawable(Drawable holderDrawable){
        this.holderDrawable = holderDrawable;
    }

    /**
     * 设置图片加载失败时显示的图片
     * @param errorDrawable 加载失败时显示的图片
     */
    public void setErrorDrawable(Drawable errorDrawable){
        this.errorDrawable = errorDrawable;
    }

    /**
     * 设置ViewPager的图片资源并且初始化Indicator
     * 该方法要在其他设置方法的后面调用
     * @param images 图片资源
     */
    public void setImages(List<ImageView> images){
        //初始化数据源 实现无限轮播，需要比正常数据源多出两个
        mData.add(images.get(images.size()-1));
        mData.addAll(images);
        mData.add(images.get(0));

        if (images.size()>1){//当图片多于一张时，显示Indicator
            showIndicator(images.size());
        }

        mAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(1,false);
        currentPagerPsn = 1;
        currentDotPsn = 0;
        refreshDotColor(currentDotPsn);
    }

    /**
     * 同步Indicator的颜色
     * @param position  当前选中的Indicator的位置
     */
    private void refreshDotColor(int position) {
        for (Indicator indicator:mDots){
            indicator.setDotColor(defaultDotColor);
        }
        mDots.get(position).setDotColor(selectedDotColor);
    }

    /**
     * 向容器中添加小圆点
     * @param number  要添加的数量
     */
    private void showIndicator(int number) {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(5,0,5,0);

        for (int i = 0; i < number; i++) {
            Indicator indicator = new Indicator(getContext(),this);
            indicator.setTag(i);
            indicator.setOnClickListener(this);
            mDots.add(indicator);
            dotContainer.addView(indicator,params);
        }
    }

    @Override
    public boolean handleMessage(Message message) {

        switch (message.what)
        {
            case AUTO_PLAY:
                currentPagerPsn++;
                if (currentPagerPsn >= mData.size()-1){
                    currentPagerPsn = 1;
                }
                mViewPager.setCurrentItem(currentPagerPsn,false);
                currentDotPsn = currentPagerPsn-1;
                refreshDotColor(currentDotPsn);
                break;
        }

        return false;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //关键代码 实现无限轮播
        if (position == mData.size()-1){
            position = 1;
        }else if (position == 0){
            position = mData.size()-2;
        }
        mViewPager.setCurrentItem(position,false);

        currentPagerPsn = position;
        currentDotPsn = position-1;
        refreshDotColor(currentDotPsn);
        //开启自动轮播
        mHandler.sendEmptyMessageDelayed(AUTO_PLAY,interval);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        switch (state)
        {
            case ViewPager.SCROLL_STATE_DRAGGING://当手指滑动时,移除消息
                if (mHandler.hasMessages(AUTO_PLAY)){
                    mHandler.removeMessages(AUTO_PLAY);
                }
                break;
        }

    }

    @Override
    public void onClick(View view) {

        int id = (int) view.getTag();

        if (id == currentDotPsn){
            return;
        }
        //点击Indicator时移除消息
        if (mHandler.hasMessages(AUTO_PLAY)){
            mHandler.removeMessages(AUTO_PLAY);
        }
        //ViewPager联动显示
        mViewPager.setCurrentItem(id+1,false);
        currentDotPsn = id;
        //刷新Indicator的颜色
        refreshDotColor(currentDotPsn);
    }
}
