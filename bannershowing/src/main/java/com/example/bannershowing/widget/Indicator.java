package com.example.bannershowing.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.example.bannershowing.Banner;

/**
 * 自定义的Indicator
 */

public class Indicator extends View {

    private Paint mPaint;

    private int dotSize;

    private Banner mBanner;

    private int dotColor;

    public Indicator(Context context,Banner banner) {
        super(context);
        mBanner = banner;
        dotSize = banner.dotSize;
        dotColor = banner.defaultDotColor;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(dotSize,MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(dotSize,MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(dotColor);
        canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2,mPaint);
    }

    public void setDotColor(int color){
        dotColor = color;
        invalidate();
    }
}
