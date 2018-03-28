package com.vincent.custom.custom_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.vincent.custom.util.DpUtil;

/**
 * @author Administrator QQ:1032006226
 * @version v1.0
 * @name CustomView
 * @page com.vincent.custom.custom_view
 * @class describe 每月电量统计
 * @date 2018/3/28 23:43
 */
public class ElectricStatisticsMonthView extends View {
    private Context mContext;
    //当前月的天数 28 29 30 31
    private int dayNum;
    //控件的宽度
    private float mViewWidth;
    //控件的高度
    private float mViewHeight;
    //今日电量
    private float mTodayElectricQuantity;
    //画布背景颜色
    private int mBackgroundColor = Color.parseColor("#454a42");
    //横向的线条
    private Paint mLineCrosswise;
    //横向的线条颜色
    private int mLineCrosswiseColor = Color.parseColor("#4a5349");
    //横向线条的宽度
    private float mLineCrosswiseWidth = 4f;
    //左边距
    private float marginLeft;
    //左下角的今天的日期文字
    private Paint mTodayDatePaint;
    //文字颜色
    private int mTodayDateColor = Color.parseColor("#f0f1f0");
    //文字大小
    private int mTodayDateTextSize ;
    //设置今天是本月的第多少天
    private int todayNum;
    //获取文本的高度
    private int todayDateHeight;
    //获取今日日期文本的宽度
    private int todayDateWidth;
    //设置今日日期文本距离每月天数的间距
    private int todayTopMargin = 5;
    //默认每月最大值数量
    private int maxValue = 100;
    //设置两条线之间的数据间隔值
    private int intervalValue = 10;
    //距离顶部的边距
    private int marginTop = 15 * 4;
    //共有多少条线
    private float maxLineNum = (maxValue*1.0f)/(intervalValue*1.0f);
    //当前天数 如果当前日期小于10号 则绘制10天
    private int currentDay;
    //左边的数据 0 10 20 30 。。。
    private Paint mLeftTag;
    //左边的数值的颜色
    private int mLeftTagColor = Color.parseColor("#f0f0f0");
    //左边的数值的字体大小
    private int mLeftTagTextSize = 24 * 4;
    //绘制天
    private Paint mDatePaint;
    //天颜色
    private int mDateTextColor = Color.parseColor("#a8a7a7");
    //天文字大小
    private int mDateTextSize = 24 * 4;
    //纵向的线
    private Paint mLengthwaysLinePaint;
    //纵向的线颜色值
    private int mLengthwaysLineColor = Color.parseColor("#939492");
    //纵向的线宽度
    private float mLengthwaysLineWidth = 8f;
    //横向的线
    private Paint mCrosswiseLinePaint;
    //横线的宽度
    private float mCrosswiseLineWidth = 8f;
    //横线的颜色
    private int mCrosswiseLineColor = Color.parseColor("#686868");

    /**
     * 设置今日是本月的第多少天
     * @param todayNum
     */
    public void setTodayNum(int todayNum) {
        this.todayNum = todayNum;
    }

    /**
     * 初始化
     * @param context
     */
    private void init(Context context) {
        this.mContext = context;
        marginLeft = DpUtil.dip2px(mContext,15);
        mTodayDateTextSize = DpUtil.px2sp(mContext,30*8);
        //表格横线画笔
        mLineCrosswise = new Paint();
        mLineCrosswise.setStrokeWidth(mLineCrosswiseWidth);
        mLineCrosswise.setColor(mLineCrosswiseColor);
        //抗锯齿
        mLineCrosswise.setAntiAlias(true);
        //今日日期画笔
        mTodayDatePaint = new Paint();
        mTodayDatePaint.setTextSize(mTodayDateTextSize);
        mTodayDatePaint.setColor(mTodayDateColor);
        mTodayDatePaint.setAntiAlias(true);
        //左边的数值画笔
        mLeftTag = new Paint();
        mLeftTag.setTextSize(DpUtil.px2sp(mContext,mLeftTagTextSize));
        mLeftTag.setColor(mLeftTagColor);
        mLeftTag.setAntiAlias(true);
        //初始化 天 画笔
        mDatePaint = new Paint();
        mDatePaint.setTextSize(DpUtil.px2sp(mContext,mDateTextSize));
        mDatePaint.setColor(mDateTextColor);
        mDatePaint.setAntiAlias(true);

        mLengthwaysLinePaint = new Paint();
        mLengthwaysLinePaint.setAntiAlias(true);
        mLengthwaysLinePaint.setColor(mLengthwaysLineColor);
        mLengthwaysLinePaint.setStrokeWidth(mLengthwaysLineWidth);

        mCrosswiseLinePaint = new Paint();
        mCrosswiseLinePaint.setAntiAlias(true);
        mCrosswiseLinePaint.setColor(mCrosswiseLineColor);
        mCrosswiseLinePaint.setStrokeWidth(mCrosswiseLineWidth);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mBackgroundColor);
        drawBackground(canvas);
    }

    /**
     * 绘制横线的线条
     * @param canvas
     */
    private void drawBackground(Canvas canvas) {
        //先绘制左下角的文字 今天的日期
        if(todayNum == 0){
            throw new RuntimeException("please setting todayNum.");
        }
        String today = String.valueOf(todayNum)+"日";
        canvas.drawText(today,marginLeft,mViewHeight-marginLeft,mTodayDatePaint);
        Rect rect = new Rect();
        mTodayDatePaint.getTextBounds(today, 0, today.length(), rect);
        todayDateWidth = rect.width();//文本的宽度
        todayDateHeight = rect.height();//文本的高度

        /*float lineMargin = mViewHeight-
        //绘制左边的数值 从0开始
        for (int i = 0;i<)*/

        //绘制 天
        currentDay = todayNum;
        if(currentDay < 10){
            currentDay = 10;
        }
        //天与天之间的间隔距离，根据VIew的宽度/需要绘制的天数来得到
        float dayInterval = (mViewWidth -marginLeft * 4)*(1.0f)/currentDay;
        //起点X坐标
        float startX = marginLeft * (2.1f);
        //起点Y坐标
        float startY = mViewHeight - todayDateHeight*2.5f - 2 * DpUtil.dip2px(mContext,todayTopMargin);
        for (int i = 0;i<currentDay;i++){
            canvas.drawText(String.valueOf(i+1),startX + dayInterval * i,startY,mDatePaint);
            //TODO 绘制日期的时候最好顺便绘制数据
        }
        // 数值的第一个点的x坐标
        float mFirValueX = marginLeft * (1.6f);
        //数值的第一个点的y坐标
        float mFirValueY = startY - marginLeft * 2;
        float lineMargin = (mFirValueY-marginTop)/maxLineNum;
        //mLeftTag 文字的宽度
        float mLeftTagTextWidth = 0f;
        //绘制左边的文字
        for (int i = 0;i<maxLineNum;i++){
            String text = String.valueOf(i * intervalValue);
            canvas.drawText(text,mFirValueX,mFirValueY + lineMargin * i * (-1f),mLeftTag);
            if(i == maxLineNum -1){
                Rect rectText = new Rect();
                mTodayDatePaint.getTextBounds(text, 0, text.length(), rectText);
                mLeftTagTextWidth = rectText.width();//文本的宽度
            }
        }
        //纵向的竖线 x坐标
        float mLengthwaysLineX = marginLeft + mLeftTagTextWidth + 5 * 4;
        //绘制纵向的线
        canvas.drawLine(mLengthwaysLineX,mFirValueY,mLengthwaysLineX,marginTop,mLengthwaysLinePaint);
        //画横线
        for (int i = 0;i < maxLineNum;i++){
            canvas.drawLine(mLengthwaysLineX,mFirValueY - lineMargin * i,(mViewWidth - marginLeft * 2)*(1.0f),mFirValueY - lineMargin * i,mCrosswiseLinePaint);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //获取高度
        mViewHeight = h;
        //获取宽度
        mViewWidth = w;
    }


    public ElectricStatisticsMonthView(Context context) {
        super(context);
        init(context);
    }

    public ElectricStatisticsMonthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ElectricStatisticsMonthView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


}
