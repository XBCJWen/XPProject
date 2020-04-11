package com.jm.core.common.widget.adapter.listadapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.view.ViewGroup;

import com.jm.core.common.tools.base.PixelsTools;
import com.jm.core.common.widget.adapter.viewholder.ViewHolder;

import java.util.List;


/**
 * 九宫格布局（item里面的图片宽高设置为match_parent）
 *
 * @author jinXiong.Xie
 */

public abstract class AbstractNineGirdLayoutAdapter<T> extends BaseRecyclerAdapter<T> {

    private static final int NICE = 9;

    private static final int EIGHT = 8;

    private static final int SEVEN = 7;

    private static final int SIX = 6;

    private static final int FIVE = 5;

    private static final int FOUR = 4;

    private static final int TWO = 2;

    private static final int ONE = 1;

    private static final int ZERO = 0;

    /**
     * RecyclerView的最大高度
     */
    private int maxHeightValue;
    /**
     * 列数
     */
    private int spanCountValue = SIX;

    public AbstractNineGirdLayoutAdapter(Context context, int layoutId, List<T> mData, int maxHeightValueDp, GridLayoutManager gridLayoutManager) {
        super(context, layoutId, mData);

        maxHeightValue = PixelsTools.dip2Px(context, maxHeightValueDp);
        initGridLayoutManager(gridLayoutManager);
    }

    /**
     * 先把GridLayout分为6列，然后设置每一行显示的item占的列数，从而达到九宫格的效果
     *
     * @param gridLayoutManager
     */
    private void initGridLayoutManager(GridLayoutManager gridLayoutManager) {
        gridLayoutManager.setSpanCount(spanCountValue);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int itemCount = getItemCount();
                //复数
                if (itemCount % TWO == 0) {
                    if (itemCount == EIGHT) {
                        switch (position) {
                            case ZERO:
                                return spanCountValue / 2;
                            case ONE:
                                return spanCountValue / 2;
                            default:
                                return spanCountValue / 3;
                        }
                    }
                    return spanCountValue / 2;
                } else {
                    //单数
                    if (itemCount == NICE) {
                        return spanCountValue / 3;
                    }

                    if (position == ZERO) {
                        return spanCountValue;
                    } else {
                        if (itemCount == SEVEN) {
                            return spanCountValue / 3;
                        }
                    }
                    return spanCountValue / 2;
                }

            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();

        //动态更改Item高度
        int size = getItemCount();

        if (size <= ZERO) {
            return;
        }
        if (size <= TWO) {
            layoutParams.height = maxHeightValue;
        } else if (size <= FOUR) {
            layoutParams.height = maxHeightValue / 2;
        } else if (size >= FIVE) {
            layoutParams.height = maxHeightValue / 3;
        }
        holder.itemView.setLayoutParams(layoutParams);
    }


}
