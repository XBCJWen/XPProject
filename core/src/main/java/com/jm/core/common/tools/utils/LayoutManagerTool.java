package com.jm.core.common.tools.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jm.core.common.tools.layout.DividerGridItemDecoration;
import com.jm.core.common.tools.layout.DividerLinearItemDecoration;
import com.jm.core.common.tools.layout.SpaceGridItemDecoration;
import com.jm.core.common.tools.layout.SpaceLinearItemDecoration;

/**
 * LayoutManager工具类
 *
 * @author jinXiong.Xie
 */

public class LayoutManagerTool {

//    private final Context context;
//    private final RecyclerView recyclerView;
//    /**
//     * 间隔
//     */
//    private int space;
//    /**
//     * 是否可滑动
//     */
//    private boolean canScroll;
//    /**
//     * 方向
//     */
//    private int orientation;
//    /**
//     * grid类型可用，列数
//     */
//    private int size;
//    private int paddingLeft;
//    private int paddingTop;
//    private int paddingRight;
//    private int paddingBottom;
//    private boolean boolDivider;

    private Builder builder;

    private LayoutManagerTool(Builder builder) {
        this.builder = builder;
//        context = builder.context;
//        recyclerView = builder.recyclerView;
//        space = builder.space;
//        canScroll = builder.canScroll;
//        orientation = builder.orientation;
//        size = builder.size;
//        paddingLeft = builder.paddingLeft;
//        paddingTop = builder.paddingTop;
//        paddingRight = builder.paddingRight;
//        paddingBottom = builder.paddingBottom;
//        boolDivider = builder.boolDivider;

    }

    public static class Builder {
        private final Context context;
        private final RecyclerView recyclerView;
        /**
         * item里面的padding值
         */
        private int space;
        /**
         * 是否可滑动
         */
        private boolean canScroll;
        /**
         * 滑动方向
         */
        private int orientation;
        /**
         * 有多少列
         */
        private int size;
        private int paddingLeft;
        private int paddingTop;
        private int paddingRight;
        private int paddingBottom;
        /**
         * 分割线
         */
        private Drawable divider;

        public Builder(Context context, RecyclerView recyclerView) {

            this.context = context;
            this.recyclerView = recyclerView;

            getDefault();

        }

        /**
         * 获取默认值
         */
        private void getDefault() {

            space = 0;
            canScroll = true;
            orientation = LinearLayoutManager.VERTICAL;
            size = 1;

            paddingLeft = recyclerView.getPaddingLeft();
            paddingTop = recyclerView.getPaddingTop();
            paddingRight = recyclerView.getPaddingRight();
            paddingBottom = recyclerView.getPaddingBottom();

            divider = null;
        }

        public Builder space(int space) {
            this.space = space;
            return this;
        }

        public Builder canScroll(boolean canScroll) {
            this.canScroll = canScroll;
            return this;
        }

        public Builder orientation(int orientation) {
            this.orientation = orientation;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder paddingLeft(int paddingLeft) {
            this.paddingLeft = paddingLeft;
            return this;
        }

        public Builder paddingTop(int paddingTop) {
            this.paddingTop = paddingTop;
            return this;
        }

        public Builder paddingRight(int paddingRight) {
            this.paddingRight = paddingRight;
            return this;
        }

        public Builder paddingBottom(int paddingBottom) {
            this.paddingBottom = paddingBottom;
            return this;
        }

        public Builder divider(Drawable divider) {
            this.divider = divider;
            return this;
        }

        public LayoutManagerTool build() {
            return new LayoutManagerTool(this);
        }
    }

    /**
     * 格子式
     *
     * @return
     */
    public GridLayoutManager gridLayoutMgr() {
        builder.recyclerView.setHasFixedSize(true);
        if (builder.space == 0) {
            setPadding();
        } else {
            if (builder.orientation == LinearLayoutManager.VERTICAL) {
                builder.paddingLeft = builder.space;
                setPadding();
            } else {
                builder.paddingLeft = builder.space;
                builder.paddingTop = builder.space;
                setPadding();
            }
        }
        RecyclerView.ItemDecoration itemDecoration = builder.recyclerView.getItemDecorationAt(0);
        if (itemDecoration == null) {
            if (builder.divider != null) {
                builder.recyclerView.addItemDecoration(new DividerGridItemDecoration(builder.divider));
            } else {
                builder.recyclerView.addItemDecoration(new SpaceGridItemDecoration(builder.space, builder.size, builder.orientation));
            }
        }
        GridLayoutManager mgr = new GridLayoutManager(builder.context, builder.size) {
            @Override
            public boolean canScrollVertically() {
                return builder.orientation == LinearLayoutManager.VERTICAL ? builder.canScroll : super.canScrollVertically();
            }

            @Override
            public boolean canScrollHorizontally() {
                return builder.orientation == LinearLayoutManager.HORIZONTAL ? builder.canScroll : super.canScrollHorizontally();
            }
        };
        mgr.setOrientation(builder.orientation);
        builder.recyclerView.setLayoutManager(mgr);
        return mgr;
    }

    /**
     * 线性方向
     *
     * @return
     */
    public LinearLayoutManager linearLayoutMgr() {
        builder.recyclerView.setHasFixedSize(true);
        if (builder.space == 0) {
            setPadding();
        } else {
            if (builder.orientation == LinearLayoutManager.HORIZONTAL) {
                builder.paddingLeft = builder.space;
                setPadding();
            }
        }
        RecyclerView.ItemDecoration itemDecoration = builder.recyclerView.getItemDecorationAt(0);
        if (itemDecoration == null) {
            if (builder.divider != null) {
                builder.recyclerView.addItemDecoration(new DividerLinearItemDecoration(builder.orientation, builder.divider));
            } else {
                builder.recyclerView.addItemDecoration(new SpaceLinearItemDecoration(builder.space, builder.orientation));
            }
        }

        LinearLayoutManager mgr = new LinearLayoutManager(builder.context) {
            @Override
            public boolean canScrollVertically() {
                return builder.orientation == LinearLayoutManager.VERTICAL ? builder.canScroll : super.canScrollVertically();
            }

            @Override
            public boolean canScrollHorizontally() {
                return builder.orientation == LinearLayoutManager.HORIZONTAL ? builder.canScroll : super.canScrollHorizontally();
            }
        };

        mgr.setOrientation(builder.orientation);
        builder.recyclerView.setLayoutManager(mgr);
        return mgr;
    }

    /**
     * 设置RecyclerView最大允许显示的行数
     *
     * @param context
     * @param recyclerView
     * @param maxLine      最大允许显示的行数
     * @param totalLine    总行数
     * @return
     */
    public static LinearLayoutManager linearLayoutMgrMaxLine(Context context, RecyclerView recyclerView, final int maxLine,
                                                             final int totalLine) {
        final LinearLayoutManager mgr = new LinearLayoutManager(context) {
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                View view = recycler.getViewForPosition(0);
                measureChild(view, widthSpec, heightSpec);
                int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                int measuredHeight = view.getMeasuredHeight() * totalLine;
                if (totalLine > maxLine) {
                    measuredHeight = view.getMeasuredHeight() * maxLine;
                }
                setMeasuredDimension(measuredWidth, measuredHeight);
            }
        };
        mgr.setAutoMeasureEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(mgr);

        return mgr;
    }

    /**
     * 设置RecyclerView最大允许显示的高度
     *
     * @param context
     * @param recyclerView
     * @param maxHeight    最大允许显示的高度
     * @param totalLine    总行数
     * @return
     */
    public static LinearLayoutManager linearLayoutMgrMaxHeight(Context context, RecyclerView recyclerView, final int maxHeight,
                                                               final int totalLine) {
        final LinearLayoutManager mgr = new LinearLayoutManager(context) {
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
                View view = recycler.getViewForPosition(0);
                measureChild(view, widthSpec, heightSpec);
                int measuredWidth = View.MeasureSpec.getSize(widthSpec);
                int measuredHeight = view.getMeasuredHeight() * totalLine;
                if (measuredHeight > maxHeight) {
                    measuredHeight = maxHeight;
                }
                setMeasuredDimension(measuredWidth, measuredHeight);
            }
        };
        mgr.setAutoMeasureEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(mgr);

        return mgr;
    }

    /**
     * 设置RecyclerView最大允许显示的宽度
     *
     * @param context
     * @param recyclerView
     * @param maxWidth     最大允许显示的宽度
     * @return
     */
    public static LinearLayoutManager linearLayoutMgrMaxWidth(Context context, RecyclerView recyclerView, final int maxWidth) {
        final LinearLayoutManager mgr = new LinearLayoutManager(context) {
            @Override
            public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
//                int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
//                int widthSize = View.MeasureSpec.getSize(widthMeasureSpec);
////                //获取屏幕的最大宽度
////                DisplayMetrics metric = new DisplayMetrics();
////                ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metric);
////                int width = metric.widthPixels;
////                maxWidth = 4*width/5;
//                if (widthMode == View.MeasureSpec.AT_MOST && widthSize > maxWidth) {
//                    widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(maxWidth, widthMode);
//                }
//                super.onMeasure(recycler, state, widthMeasureSpec, heightMeasureSpec);
                View view = recycler.getViewForPosition(0);
                measureChild(view, widthSpec, heightSpec);
                int measuredWidth = View.MeasureSpec.getSize(widthSpec);
//                int measuredHeight = view.getMeasuredHeight() * totalLine;
                if (measuredWidth > maxWidth) {
                    measuredWidth = maxWidth;
                }
                setMeasuredDimension(measuredWidth, heightSpec);
            }
        };
        mgr.setAutoMeasureEnabled(false);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(mgr);

        return mgr;
    }

    /**
     * 设置调用smoothScrollToPosition时，将其移动到中间位置
     *
     * @return
     */
    public LinearLayoutManager centerLayoutManager() {
        builder.recyclerView.setHasFixedSize(true);
        if (builder.orientation == LinearLayoutManager.HORIZONTAL) {
            builder.paddingLeft = builder.space;
            setPadding();
        }
        RecyclerView.ItemDecoration itemDecoration = builder.recyclerView.getItemDecorationAt(0);
        if (itemDecoration == null) {
            builder.recyclerView.addItemDecoration(new SpaceLinearItemDecoration(builder.space, builder.orientation));
        }
        final LinearLayoutManager mgr = new LinearLayoutManager(builder.context) {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(recyclerView.getContext());
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }

            @Override
            public boolean canScrollVertically() {
                return builder.canScroll;
            }
        };
        mgr.setOrientation(builder.orientation);
        builder.recyclerView.setLayoutManager(mgr);
        return mgr;
    }

    /**
     * 设置recyclerView的Padding距离
     */
    public void setPadding() {

        builder.recyclerView.setPadding(builder.paddingLeft, builder.paddingTop, builder.paddingRight, builder.paddingBottom);
        builder.recyclerView.setClipToPadding(false);
    }

    private static class CenterSmoothScroller extends LinearSmoothScroller {

        CenterSmoothScroller(Context context) {
            super(context);
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            return (boxStart + (boxEnd - boxStart) / 2) - (viewStart + (viewEnd - viewStart) / 2);
        }
    }


    /**
     * 获取可见的item的标识
     *
     * @param recyclerView
     * @return 第一个为可见的上标，第二个为可见的下标
     */
    public static int[] requestVisibleItemPosition(RecyclerView recyclerView) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        //判断是当前layoutManager是否为LinearLayoutManager
        // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            //获取第一个可见view的位置
            int firstItemPosition = linearManager.findFirstVisibleItemPosition();
            //获取最后一个可见view的位置
            int lastItemPosition = linearManager.findLastVisibleItemPosition();
            return new int[]{firstItemPosition, lastItemPosition};
        }

        return new int[]{0, 0};
    }

    /**
     * 获取可见的上标
     *
     * @param recyclerView
     * @return
     */
    public static int requestFirstVisibleItemPosition(RecyclerView recyclerView) {
        return requestVisibleItemPosition(recyclerView)[0];
    }

    /**
     * 获取可见的下标
     *
     * @param recyclerView
     * @return
     */
    public static int requestLastVisibleItemPosition(RecyclerView recyclerView) {
        return requestVisibleItemPosition(recyclerView)[1];
    }
}
