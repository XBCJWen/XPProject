package com.jm.core.common.widget.adapter.listadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jm.core.common.widget.adapter.viewholder.MyViewHolder;

import java.util.List;


/**
 * 对于BaseAdapter进行进一步的封装
 *
 * @author xiejinxiong
 * @param <T>
 *
 */
public abstract class MyBaseAdapter<T> extends BaseAdapter {

	protected Context context;
	protected List<T> list;
	protected int layoutId;

	public MyBaseAdapter(Context context, List<T> list, int layoutId) {
		this.context = context;
		this.list = list;
		this.layoutId = layoutId;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		MyViewHolder viewHolder = MyViewHolder.getViewHolder(context, convertView,
				parent, layoutId);

		conver(viewHolder, getItem(position), position);

		return viewHolder.getConverView();
	}

	public abstract void conver(MyViewHolder viewHolder, Object object,
								int position);

}
