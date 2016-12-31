package com.example.bannershowing.widget.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by guide on 2016/12/30.
 */

public class BannerPagerAdapter extends PagerAdapter {

    private List<ImageView> mImages;

    private IOnImageClickListener mIOnImageClickListener;

    private IOnImageLongClickListener mIOnImageLongClickListener;

    public BannerPagerAdapter(List<ImageView> list){
        this.mImages = list;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ImageView imageView = mImages.get(position);

        ViewGroup parent = (ViewGroup) imageView.getParent();

        if (parent != null) {
            parent.removeView(imageView);
        }

        container.addView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置ViewPager条目的点击监听事件
                if (mIOnImageClickListener != null){
                    mIOnImageClickListener.onImageClick(position-1);
                }

            }
        });

        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (mIOnImageLongClickListener != null) {
                    mIOnImageLongClickListener.onImageLongClick(position-1);
                }
                //这里的返回值表示是否拦截(消费)本次点击事件 true 代表拦截onClick()不会执行
                //false 代表不拦截 onClick() 还会执行
                return true;
            }
        });

        return imageView;
    }

    /**
     * 这个方法必须要重写 但可以是空的实现
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

    public void addOnImageClickListener(IOnImageClickListener iOnImageClickListener){
        this.mIOnImageClickListener = iOnImageClickListener;
    }

    public void addOnImageLongClickListener(IOnImageLongClickListener onImageLongClickListener){
        this.mIOnImageLongClickListener = onImageLongClickListener;
    }

    /**
     * ImageView的点击事件的回调
     */
    public interface IOnImageClickListener{
        //position item在真实数据源中的位置 0-(mData.size()-1)
        void onImageClick(int position);
    }

    /**
     * ImageView的长按事件的回调
     */
    public interface IOnImageLongClickListener{
        void onImageLongClick(int position);
    }
}
