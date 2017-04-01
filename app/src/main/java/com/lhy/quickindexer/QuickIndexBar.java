package com.lhy.quickindexer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * 创 建 人: 路好营
 * 创建日期: 2017/3/29 11:51
 * 添加备注: 快速索引栏
 */

public class QuickIndexBar extends View {
    private static final String[] LETTERS = new String[]{
            "A", "B", "C", "D", "E", "F",
            "G", "H", "I", "J", "K", "L",
            "M", "N", "O", "P", "Q", "R",
            "S", "T", "U", "V", "W", "X",
            "Y", "Z"
    };
    private Paint paint;//画笔
    private float mHeight;//每个字母所占用空间的高度(比字母本身高度大)
    private float mWidth;//每个字母所占用空间的宽度(比字母本身宽度大)

    private int lastIndex = -1;//记录上次触摸的索引,用来判断索引发生了变化才更新界面

    public QuickIndexBar(Context context) {
        this(context, null);
    }

    public QuickIndexBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickIndexBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);//消除锯齿
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.DEFAULT_BOLD);//字体加粗
        paint.setTextSize(40);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < LETTERS.length; i++) {
            String letter = LETTERS[i];
            //绘制字母,根坐标是左下角
            float x = mWidth * 0.5f - paint.measureText(letter) * 0.5f;//左下角的x坐标
            Rect bounds = new Rect();
            paint.getTextBounds(letter, 0, letter.length(), bounds);//把字母本身的范围传到矩形bounds里面
            float y = mHeight * 0.5f + bounds.height() * 0.5f + i * mHeight;//左下角的y坐标
            //invalidate()执行后,会调用此方法重新绘制一次,改变字体颜色
            paint.setColor(i == lastIndex ? Color.GRAY : Color.WHITE);//如果当前手指处于该字母上,则改变字母的颜色
            canvas.drawText(LETTERS[i], x, y, paint);//绘制字体
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredHeight = getMeasuredHeight();//索引条总长度
        mHeight = measuredHeight * 1.0f / LETTERS.length;//每个字母所占用空间的高度(比字母本身高度大)
        mWidth = getMeasuredWidth() * 1.0f;//每个字母所占用空间的宽度(比字母本身宽度大)
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y;
        int currentIndex;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                y = event.getY();//获取手指触摸的当前在索引条上的屏幕高度像素值
                currentIndex = (int) (y / mHeight);//得到当前的索引值
                if (currentIndex != lastIndex) {//当索引值发生了变化才更新
                    if (currentIndex >= 0 && currentIndex < LETTERS.length) {//控制索引值的范围
                        lastIndex = currentIndex;//记录当前的索引值
                        if (onLetterUpdateListener != null) {
                            onLetterUpdateListener.onLetterUpdate(LETTERS[currentIndex]);//更新ListView查找到的item的位置
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                lastIndex = -1;//抬手还原索引值
                onLetterUpdateListener.onHiddenLetter();//隐藏屏幕中间的提示字母
                break;
            default:
                break;
        }
        invalidate();//给lastIndex赋值后,再次绘制更新界面,把改变字体的颜色显示出来
        return true;
    }

    private OnLetterUpdateListener onLetterUpdateListener;

    public OnLetterUpdateListener getOnLetterUpdateListener() {
        return onLetterUpdateListener;
    }

    public void setOnLetterUpdateListener(OnLetterUpdateListener onLetterUpdateListener) {
        this.onLetterUpdateListener = onLetterUpdateListener;
    }

    public interface OnLetterUpdateListener {
        void onLetterUpdate(String letter);

        void onHiddenLetter();//隐藏
    }
}
