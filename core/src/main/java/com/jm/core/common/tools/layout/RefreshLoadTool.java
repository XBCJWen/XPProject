package com.jm.core.common.tools.layout;

import android.content.Context;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * 刷新库的简易封装
 *
 * @author jinXiong.Xie
 */

public class RefreshLoadTool {

    private RefreshLoadTool() {
    }

    /**
     * 初始化
     *
     * @param bgColor
     * @param textColor
     */
    public static void init(final int bgColor, final int textColor) {
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(bgColor, textColor);
//                return new MaterialHeader(context);
                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale);
            }
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Scale);
            }
        });

    }

    /**
     * 添加刷新的监听
     *
     * @param refreshLayout
     * @param listener
     */
    public static void initRefreshLoad(RefreshLayout refreshLayout, final RefreshLoadListener listener) {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (listener != null) {
                    listener.onRefresh(refreshlayout);
                }
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (listener != null) {
                    listener.onLoadMore(refreshlayout);
                }
            }
        });
        refreshLayout.setEnableHeaderTranslationContent(true);
        refreshLayout.setEnableFooterTranslationContent(true);
    }

    /**
     * 停止刷新和加载
     *
     * @param refreshLayout
     */
    public static void finish(RefreshLayout refreshLayout) {
        if (refreshLayout != null) {
            if (refreshLayout.isRefreshing()){
                refreshLayout.finishRefresh();
            }
            if (refreshLayout.isLoading()){
                refreshLayout.finishLoadmore();
            }
        }
    }

    /**
     * 刷新布局的监听
     *
     * @author jinXiong.Xie
     */

    public interface RefreshLoadListener {
        /**
         * 下拉刷新
         *
         * @param refreshlayout
         */
        void onRefresh(RefreshLayout refreshlayout);

        /**
         * 上拉加载更多
         *
         * @param refreshlayout
         */
        void onLoadMore(RefreshLayout refreshlayout);
    }

}
