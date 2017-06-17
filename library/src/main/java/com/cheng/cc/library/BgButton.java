package com.cheng.cc.library;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;


/**
 * @author Created by cc on 17/6/17.
 * @fileName BgButton
 * @githublink https://github.com/cc0819
 * @csdnlink http://blog.csdn.net/qq_25404567
 */

public class BgButton extends TextView {

    private ColorStateList BgColor;
    private int cornerRadius;
    private int cornerRadiusColor;
    private int strokeWidth;


    public BgButton(Context context) {
//        super(context);
        this(context,null);
    }

    public BgButton(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context,attrs,0);
    }

    public BgButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.BgButton);

         BgColor = typedArray.getColorStateList(R.styleable.BgButton_BgColor);
        cornerRadius = typedArray.getDimensionPixelSize(R.styleable.BgButton_CornerRadius, 0);
        cornerRadiusColor = typedArray.getColor(R.styleable.BgButton_CornerRadiusColor,getCurrentTextColor());
        strokeWidth = typedArray.getDimensionPixelSize(R.styleable.BgButton_StrokeWidth,0);


//        float pressedRatio = typedArray.getFloat(R.styleable.BgButton_btnPressedRatio, 0.80f);
//        ColorStateList solidColor = typedArray.getColorStateList(R.styleable.BgButton_btnSolidColor);
//        int strokeDashWidth = typedArray.getDimensionPixelSize(R.styleable.BgButton_btnStrokeDashWidth, 0);
//        int strokeDashGap = typedArray.getDimensionPixelSize(R.styleable.BgButton_btnStrokeDashGap, 0);
        typedArray.recycle();
        drawBackground();

    }

    private void drawBackground() {
        setSingleLine(true);
        setGravity(Gravity.CENTER);
        BgDrawable drawable = new BgDrawable(false);
        drawable.setCornerRadius(cornerRadius);
        drawable.setStroke(strokeWidth, cornerRadiusColor, 0, 0);

        drawable.setSolidColors(BgColor);
        setBackground(drawable);

    }

    private static class BgDrawable extends GradientDrawable {
        private boolean mIsStadium = false;

        private ColorStateList mSolidColors;
        private int mFillColor;

        public BgDrawable(boolean isStadium) {
            mIsStadium = isStadium;
        }

        public void setSolidColors(ColorStateList colors) {
            mSolidColors = colors;
            setColor(colors.getDefaultColor());
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            if (mIsStadium) {
                RectF rect = new RectF(getBounds());
                setCornerRadius((rect.height() > rect.width() ? rect.width() : rect.height()) / 2);
            }
        }

        @Override
        public void setColor(int argb) {
            mFillColor = argb;
            super.setColor(argb);
        }

        @Override
        protected boolean onStateChange(int[] stateSet) {
            if (mSolidColors != null) {
                final int newColor = mSolidColors.getColorForState(stateSet, 0);
                if (mFillColor != newColor) {
                    setColor(newColor);
                    return true;
                }
            }
            return false;
        }

        @Override
        public boolean isStateful() {
            return super.isStateful() || (mSolidColors != null && mSolidColors.isStateful());
        }
    }


}
