package com.jm.xpproject.utils.xp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.jm.core.common.widget.adapter.viewholder.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import com.jm.core.common.tools.layout.RefreshLoadTool;
import com.jm.core.common.tools.tools.GsonUtil;
import com.jm.core.common.widget.adapter.listadapter.BaseRecyclerAdapter;

import org.json.JSONObject;

import java.util.List;

/**
 * 基于小跑刷新加载的工具类
 *
 * @author jinXiong.Xie
 */

public class XPRefreshLoadUtil<T> extends XPBaseUtil {

    private SmartRefreshLayout refreshLayout;

    public void setRefreshLayout(SmartRefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    private int totalPage = -1;
    private int currentPage = 1;
    private int pageSize = 10;

    private List<T> listObject;
    private RecyclerView.Adapter<ViewHolder> mAdapter;
    private RefreshLoadCallBack refreshLoadCallBack;

    public XPRefreshLoadUtil(Context context) {
        super(context);
    }

    public XPRefreshLoadUtil(Context context, SmartRefreshLayout refreshLayout) {
        super(context);
        this.refreshLayout = refreshLayout;


        initRefreshLayout();
    }

    /**
     * 设置刷新框架的适配器
     *
     * @param listObject
     * @param mAdapter
     * @param refreshLoadCallBack
     */
    public void startRefresh(List<T> listObject, RecyclerView.Adapter<ViewHolder> mAdapter, RefreshLoadCallBack refreshLoadCallBack) {
        this.listObject = listObject;
        this.mAdapter = mAdapter;
        this.refreshLoadCallBack = refreshLoadCallBack;

        if (refreshLoadCallBack != null) {
            refreshLoadCallBack.httpListRecord(currentPage, pageSize);
        }
    }


    private void initRefreshLayout() {
        RefreshLoadTool.initRefreshLoad(refreshLayout, new RefreshLoadTool.RefreshLoadListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                reloadListData();
            }

            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                loadMore();
            }
        });
    }

    /**
     * 加载更多
     */
    public void loadMore() {
        //上拉加载更多
        if (totalPage > currentPage || totalPage == -1) {
            if (refreshLoadCallBack != null) {
                refreshLoadCallBack.httpListRecord(++currentPage, pageSize);
            }
        } else {
            showToast("无更多数据");
            refreshLayout.finishLoadmore(1000);
        }
    }

    /**
     * 重新刷新数据
     */
    public void reloadListData() {
        totalPage = -1;
        currentPage = 1;
        listObject.clear();
        notifyDataChanged();
        if (refreshLoadCallBack != null) {
            refreshLoadCallBack.httpListRecord(currentPage, pageSize);
        }
    }

    /**
     * 刷新加载的回调
     */
    public interface RefreshLoadCallBack {

        /**
         * 网络访问
         *
         * @param currentPage
         * @param pageSize
         */
        void httpListRecord(int currentPage, int pageSize);
    }

    /**
     * 停止刷新和加载
     */
    public void stopRefreshLoad() {
        (getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (refreshLayout == null) {
                    return;
                }
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.finishRefresh();
                }
                if (refreshLayout.isLoading()) {
                    refreshLayout.finishLoadmore();
                }
            }
        });
    }

    /**
     * 刷新数据
     *
     * @param dataObj
     */
    public void refreshListData(JSONObject dataObj, Class<T> cls) {
        try {

            List<T> beans = GsonUtil.gsonToList(dataObj.optJSONArray("list").toString(), cls);

            listObject.addAll(beans);

            totalPage = dataObj.optInt("totalPage");


            notifyDataChanged();

        } catch (Exception e) {
            e.printStackTrace();

            listObject.clear();

            notifyDataChanged();

        }
    }


    private void notifyDataChanged() {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();

        }
    }

}
