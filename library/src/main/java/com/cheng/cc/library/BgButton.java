package com.cheng.cc.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;


/**
 * @author Created by cc on 17/6/17.
 * @fileName BgButton
 * @githublink https://github.com/cc0819
 * @csdnlink http://blog.csdn.net/qq_25404567
 */

public class BgButton extends TextView {

    private int bgColor;
    private int borderColor;
    private int borderWidth;
    private int textColor;

    private float radius;
    private float topLeftRadius;
    private float topRightRadius;
    private float bottomLeftRadius;
    private float bottomRightRadius;

    //点击状态
    private boolean isChick;
    private int clickedBgColor;
    private int clickedCornerRadiusColor;
    private int clickedTextColor;


    private int shapeType;

    private float borderDashLength;
    private float borderDashGapSmall;
    private float borderDashGap;

    private Paint paintBg;
    private Paint paintBorder;

    private Path mPath;
    private RectF mReactf = new RectF();
    private float[] radiusf;


    public BgButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BgButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BgButton);
        if (typedArray != null) {
            bgColor = typedArray.getColor(R.styleable.BgButton_bgColor, 0x00000000);
            borderColor = typedArray.getColor(R.styleable.BgButton_cornerRadiusColor, 0x00000000);
            borderWidth = typedArray.getDimensionPixelSize(R.styleable.BgButton_borderWidth, 1);

            radius = typedArray.getDimension(R.styleable.BgButton_cornerRadius, 0);
            topLeftRadius = typedArray.getDimension(R.styleable.BgButton_topLeftRadius, -1);
            topRightRadius = typedArray.getDimension(R.styleable.BgButton_topRightRadius, -1);
            bottomLeftRadius = typedArray.getDimension(R.styleable.BgButton_bottomLeftRadius, -1);
            bottomRightRadius = typedArray.getDimension(R.styleable.BgButton_bottomRightRadius, -1);
            shapeType = typedArray.getInt(R.styleable.BgButton_shapeType, GradientDrawable.RECTANGLE);

            borderDashLength = typedArray.getDimension(R.styleable.BgButton_borderDashLength, 5);
            borderDashGapSmall = typedArray.getDimension(R.styleable.BgButton_borderDashGapSmall, 0);
            borderDashGap = typedArray.getDimension(R.styleable.BgButton_borderDashGap, 0);

            isChick = typedArray.getBoolean(R.styleable.BgButton_isChick, false);

            clickedBgColor = typedArray.getColor(R.styleable.BgButton_clickedBgColor, -1);
            clickedCornerRadiusColor = typedArray.getColor(R.styleable.BgButton_clickedCornerRadiusColor, -1);
            textColor = getCurrentTextColor();
            clickedTextColor = typedArray.getColor(R.styleable.BgButton_clickedTextColor, -1);
            typedArray.recycle();

        }
        Log.e("info", "--------初始化状态---" + isChick + "---" + clickedBgColor);
        initDraw();

    }


    private void initDraw() {
        topLeftRadius = topLeftRadius == -1 ? radius : topLeftRadius;
        topRightRadius = topRightRadius == -1 ? radius : topRightRadius;
        bottomLeftRadius = bottomLeftRadius == -1 ? radius : bottomLeftRadius;
        bottomRightRadius = bottomRightRadius == -1 ? radius : bottomRightRadius;
        borderDashGapSmall = borderDashGapSmall == 0 ? borderDashLength : borderDashGapSmall;

        clickedBgColor = clickedBgColor == -1 ? bgColor : clickedBgColor;
        clickedCornerRadiusColor = clickedCornerRadiusColor == -1 ? borderColor : clickedCornerRadiusColor;
        clickedTextColor = clickedTextColor == -1 ? textColor : clickedTextColor;


        if (borderWidth > 0 && borderColor != 0) {
            paintBorder = new Paint();
            if (isChick) {
                paintBorder.setColor(clickedCornerRadiusColor);
            } else {
                paintBorder.setColor(borderColor);
            }
            paintBorder.setStyle(Paint.Style.STROKE);
            paintBorder.setStrokeWidth(borderWidth);
            paintBorder.setAntiAlias(true);
        }
        paintBg = new Paint();
        if (isChick) {
            paintBg.setColor(clickedBgColor);
            setTextColor(clickedTextColor);
        } else {
            paintBg.setColor(bgColor);
            setTextColor(textColor);
        }
        paintBg.setAntiAlias(true);
        paintBg.setStyle(Paint.Style.FILL);

        if (radius == 0 && shapeType == GradientDrawable.RECTANGLE) {
            mPath = new Path();
            radiusf = new float[]{topLeftRadius, topLeftRadius, topRightRadius, topRightRadius,
                    bottomRightRadius, bottomRightRadius, bottomLeftRadius, bottomLeftRadius};
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mReactf.set(borderWidth, borderWidth, getWidth() - borderWidth, getHeight() - borderWidth);
        if (mPath != null) {
            mPath.addRoundRect(mReactf, radiusf, Path.Direction.CW);
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (shapeType == GradientDrawable.RECTANGLE) {
            if (radius == 0) {
                canvas.drawPath(mPath, paintBg);
            } else {
                canvas.drawRoundRect(mReactf, radius, radius, paintBg);
                if (paintBorder != null) {
                    if (borderDashGap > 0) {
                        PathEffect effects = new DashPathEffect(new float[]{borderDashLength,
                                borderDashLength, borderDashGapSmall, borderDashLength}, borderDashGap);//设置虚线的间隔和点的长度
                        paintBorder.setPathEffect(effects);
                    }
                    canvas.drawRoundRect(mReactf, radius, radius, paintBorder);
                }
            }
        } else {
            canvas.drawOval(mReactf, paintBg);
            if (paintBorder != null) {
                if (borderDashGap > 0) {
                    PathEffect effects = new DashPathEffect(new float[]{borderDashLength,
                            borderDashLength, borderDashGapSmall, borderDashLength}, borderDashGap);
                    paintBorder.setPathEffect(effects);
                }
                canvas.drawOval(mReactf, paintBorder);
            }
        }
        super.onDraw(canvas);//需要在自己绘制边框后绘制，否则会被覆盖掉
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isChick = !isChick;
            drawBackgroud(isChick);
        }
        return super.onTouchEvent(event);
    }

    private void drawBackgroud(boolean b) {
//        Log.e("info", "--------点击状态---" + isChick);
        if (b) {
            paintBg.setColor(clickedBgColor);
            if (borderWidth > 0 && borderColor != 0) {
                paintBorder.setColor(clickedCornerRadiusColor);
            }
            setTextColor(clickedTextColor);
        } else {
            paintBg.setColor(bgColor);
            if (borderWidth > 0 && borderColor != 0) {
                paintBorder.setColor(borderColor);
            }
            setTextColor(textColor);
        }

        invalidate();
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getTopLeftRadius() {
        return topLeftRadius;
    }

    public void setTopLeftRadius(float topLeftRadius) {
        this.topLeftRadius = topLeftRadius;
    }

    public float getTopRightRadius() {
        return topRightRadius;
    }

    public void setTopRightRadius(float topRightRadius) {
        this.topRightRadius = topRightRadius;
    }

    public float getBottomLeftRadius() {
        return bottomLeftRadius;
    }

    public void setBottomLeftRadius(float bottomLeftRadius) {
        this.bottomLeftRadius = bottomLeftRadius;
    }

    public float getBottomRightRadius() {
        return bottomRightRadius;
    }

    public void setBottomRightRadius(float bottomRightRadius) {
        this.bottomRightRadius = bottomRightRadius;
    }

    public int getShapeType() {
        return shapeType;
    }

    public void setShapeType(int shapeType) {
        this.shapeType = shapeType;
    }

    public float getBorderDashLength() {
        return borderDashLength;
    }

    public void setBorderDashLength(float borderDashLength) {
        this.borderDashLength = borderDashLength;
    }

    public float getBorderDashGapSmall() {
        return borderDashGapSmall;
    }

    public void setBorderDashGapSmall(float borderDashGapSmall) {
        this.borderDashGapSmall = borderDashGapSmall;
    }

    public float getBorderDashGap() {
        return borderDashGap;
    }

    public void setBorderDashGap(float borderDashGap) {
        this.borderDashGap = borderDashGap;
    }

    public boolean isChick() {
        return isChick;
    }

    public void setChick(boolean chick) {
        isChick = chick;
    }

    public int getClickedBgColor() {
        return clickedBgColor;
    }

    public void setClickedBgColor(int clickedBgColor) {
        this.clickedBgColor = clickedBgColor;
    }

    public int getClickedCornerRadiusColor() {
        return clickedCornerRadiusColor;
    }

    public void setClickedCornerRadiusColor(int clickedCornerRadiusColor) {
        this.clickedCornerRadiusColor = clickedCornerRadiusColor;
    }

    public int getClickedTextColor() {
        return clickedTextColor;
    }

    public void setClickedTextColor(int clickedTextColor) {
        this.clickedTextColor = clickedTextColor;
    }
}
