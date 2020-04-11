package com.jm.core.common.widget.adapter.viewholder;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 通用ViewHolder，提高ListView Item复用性
 *
 * @author xiejinxiong
 *
 */
public class MyViewHolder {

	/** 储存控件 */
	private SparseArray<View> sparseArray;
	/** 储存ViewHolder对象 */
	private View mConverView;

	/**
	 * 构造方法，初始化对象及页面，并储存当前ViewHolder
	 *
	 * @param context
	 * @param parent
	 */
	private MyViewHolder(Context context, ViewGroup parent, int layoutId) {

		sparseArray = new SparseArray<View>();

		mConverView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);

		mConverView.setTag(this);

	}

	/**
	 * 获取ViewHolder对象
	 *
	 * @param context
	 * @param converView
	 * @param parent
	 * @param layoutId
	 * @return
	 */
	public static MyViewHolder getViewHolder(Context context, View converView,
										   ViewGroup parent, int layoutId) {

		if (converView == null) {
			return new MyViewHolder(context, parent, layoutId);
		}
		return (MyViewHolder) converView.getTag();

	}

	/**
	 * 获取控件
	 *
	 * @param viewId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {

		View view = sparseArray.get(viewId);
		if (view == null) {
			view = mConverView.findViewById(viewId);
			sparseArray.put(viewId, view);
		}
		return (T) view;

	}

	/**
	 * 获取converView对象
	 *
	 * @return
	 */
	public View getConverView() {
		return mConverView;
	}

	/**
	 * 为TextView设置字符串
	 *
	 * @param viewId
	 * @param str
	 */
	public void setText(int viewId, String str) {

		TextView tv = getView(viewId);
		tv.setText(str);

	}

	/**
	 * 为ImageView设置图片
	 *
	 * @param viewId
	 * @param drawableId
	 */
	public void setImageResource(int viewId, int drawableId) {
		ImageView imageView = getView(viewId);
		imageView.setImageResource(drawableId);
	}

	/**
	 * 为ImageView设置图片
	 *
	 * @param viewId
	 * @param bitmap
	 */
	public void setImageBitmap(int viewId, Bitmap bitmap) {
		ImageView imageView = getView(viewId);
		imageView.setImageBitmap(bitmap);
	}
}
