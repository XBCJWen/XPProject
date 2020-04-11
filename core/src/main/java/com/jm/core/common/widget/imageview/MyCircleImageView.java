package com.jm.core.common.widget.imageview;

import com.jm.core.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆形图片（可带边框）
 *
 * @author xiejinxiong 若想使用边框属性，记得在xml中添加
 *         xmlns:app="http://schemas.android.com/apk/res-auto"(自定义属性)
 */
public class MyCircleImageView extends ImageView {

	/** 图片画笔 */
	private Paint mdrawPaint;
	/** 边框的画笔 */
	private Paint mBorderPaint;
	/** 圆图片的半径 */
	private int mRadius;
	/** ImageView最短边长 */
	private int imgMinLength;

	/** 设置默认边框宽度为0 */
	private static final int DEFAULT_BORDER_WIDTH = 0;
	/** 设置默认边框颜色为黑色 */
	private static final int DEFAULT_BORDER_COLOR = Color.BLACK;
	/** 边框颜色 */
	private int mBorderColor = DEFAULT_BORDER_COLOR;
	/** 边框宽度 */
	private int mBorderWidth = DEFAULT_BORDER_WIDTH;

	public MyCircleImageView(Context context) {
		super(context);
	}

	public MyCircleImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyCircleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initBorderValue(context, attrs, defStyle);
	}

	/**
	 * 初始化边框值
	 */
	private void initBorderValue(Context context, AttributeSet attrs,
								 int defStyle) {
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.MyCircleImageView, defStyle, 0);

		// 获取在xml里面设置控件的属性值
		mBorderWidth = a.getDimensionPixelSize(
				R.styleable.MyCircleImageView_border_width,
				DEFAULT_BORDER_WIDTH);
		mBorderColor = a.getColor(R.styleable.MyCircleImageView_border_color,
				DEFAULT_BORDER_COLOR);

		a.recycle();// 返回一个TypedArray，预防后面使用
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		// 如果类型是圆形，则强制改变view的宽高一致，以小值为准
		imgMinLength = Math.min(getMeasuredWidth(), getMeasuredHeight());
		setMeasuredDimension(imgMinLength, imgMinLength);
		mRadius = imgMinLength / 2;

		initDraw();
		initBorder();

		// 重绘
		invalidate();

	}

	/**
	 * 初始化图片描绘
	 */
	private void initDraw() {

		// 实例画笔（抗锯齿|抗抖动）
		mdrawPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);

		// 获取位图
		Drawable draw = getDrawable();
		if (draw == null) {
			return;
		}
		BitmapDrawable bd = (BitmapDrawable) draw;
		Bitmap mBitmap = bd.getBitmap();

		// 用于记录图片缩放移动等改动
		Matrix matrix = new Matrix();
		// 根据图片的宽高对图片进行缩放，与centerCrop相似，同时，对缩放后的图片进行居中显示
		if (mBitmap.getWidth() > mBitmap.getHeight()) {
			float mDrawScale = imgMinLength * 1.0f / mBitmap.getHeight();
			matrix.setScale(mDrawScale, mDrawScale);
			matrix.postTranslate(
					-(mBitmap.getWidth() * mDrawScale / 2 - mRadius), 0);
		} else {
			float mDrawScale = imgMinLength * 1.0f / mBitmap.getWidth();
			matrix.setScale(mDrawScale, mDrawScale);
			matrix.postTranslate(0,
					-(mBitmap.getHeight() * mDrawScale / 2 - mRadius));
		}

		// 设置着色器
		BitmapShader bs = new BitmapShader(mBitmap, Shader.TileMode.CLAMP,
				Shader.TileMode.CLAMP);
		bs.setLocalMatrix(matrix);
		mdrawPaint.setShader(bs);

	}

	/**
	 * 初始化边框描绘
	 */
	private void initBorder() {

		// 实例画笔（抗锯齿|抗抖动）
		mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
		// 设置画笔属性
		mBorderPaint.setStyle(Paint.Style.STROKE);// 设置只描边
		mBorderPaint.setColor(mBorderColor);
		mBorderPaint.setStrokeWidth(mBorderWidth);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		// 绘制圆角图片
		canvas.drawCircle(mRadius, mRadius, mRadius - mBorderWidth, mdrawPaint);
		if (mBorderWidth != 0) {
			// 绘画圆形边框
			canvas.drawCircle(mRadius, mRadius, mRadius - mBorderWidth,
					mBorderPaint);
		}

	}
}
