package com.jm.core.common.tools.layout;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * item与List之间的边距（线性方向）
 *
 * @author jinXiong.Xie
 */

public class SpaceLinearItemDecoration extends RecyclerView.ItemDecoration {

    /**
     * 距离
     */
    private int space;
    /**
     * 方向
     */
    private int direction;
//    /**
//     * 横向
//     */
//    public static final int HORIZONTAL = 0;
//    /**
//     * 竖向
//     */
//    public static final int VERTICAL = 1;

    public SpaceLinearItemDecoration(int space, int direction) {
        this.space = space;
        this.direction = direction;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if (space == 0) {
            return;
        }
        switch (direction) {
            case LinearLayoutManager.HORIZONTAL:
                outRect.top = space;
//                if (parent.getChildLayoutPosition(view) == 0) {
//                    outRect.left = space;
//                } else {
                outRect.left = 0;
//                }
                break;
            case LinearLayoutManager.VERTICAL:
                outRect.left = space;
                if (parent.getChildLayoutPosition(view) == 0) {
                    outRect.top = space;
                } else {
                    outRect.top = 0;
                }
                break;
            default:
        }
        outRect.right = space;
        outRect.bottom = space;
    }
}
