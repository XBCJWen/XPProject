package com.jm.xpproject.ui.common.act;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jm.api.util.IntentUtil;
import com.jm.api.util.SharedAccount;
import com.jm.core.common.tools.base.SharedPreferencesTool;
import com.jm.core.common.tools.utils.LayoutManagerTool;
import com.jm.core.common.widget.adapter.listadapter.BaseRecyclerAdapter;
import com.jm.core.common.widget.adapter.viewholder.ViewHolder;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.bean.UserData;
import com.jm.xpproject.config.GlobalConstant;
import com.jm.xpproject.http.api.BaseCloudApi;
import com.jm.xpproject.ui.login.act.SplashAct;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择服务器
 *
 * @author bjsdm
 * @date 2019/3/20
 */
public class SelectServiceAct extends MyTitleBarActivity {

    @BindView(R.id.tv_service)
    TextView tvService;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public void initViewAndUtil() {

        setServiceContent();
        initRecyclerView();
    }

    /**
     * 设置服务器信息
     */
    private void setServiceContent() {
        for (int i = 0; i < BaseCloudApi.getIpArray().length; i++) {
            if (BaseCloudApi.getIP().equals(BaseCloudApi.getIpArray()[i])) {
                tvService.setText("当前服务器为：" + BaseCloudApi.getIpNameArray()[i] + "\nIP地址为：" + BaseCloudApi.getIP());
                break;
            }
        }
    }

    @Override
    public void initNetLink() {

    }

    @Override
    protected int layoutResID() {
        return R.layout.activity_select_service;
    }

    @Override
    protected void initTitle() {
        setTitle(true, "选择服务器");
    }


    private List<Object> arrayList = new ArrayList<>();
    private BaseRecyclerAdapter<Object> recyclerAdapter;

    private void initRecyclerView() {
        new LayoutManagerTool.Builder(getActivity(), recyclerView).build().linearLayoutMgr();

        recyclerAdapter = new BaseRecyclerAdapter<Object>(getActivity(), R.layout.item_select_service, arrayList) {
            @Override
            public void convert(ViewHolder holder, Object o, final int position) {
                TextView tvIP = holder.getView(R.id.tv_ip);
                tvIP.setText("服务器为：" + BaseCloudApi.getIpNameArray()[position] + "\nIP地址为：" + BaseCloudApi.getIpArray()[position]);

                holder.setOnRootClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferencesTool tool = new SharedPreferencesTool(getActivity(), GlobalConstant.SERVER_IP);
                        tool.setParam(GlobalConstant.SERVER_IP, BaseCloudApi.getIpArray()[position]);

                        showLoading();
                        myHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                hiddenLoading();
                                //清除信息
                                UserData.getInstance().clear();
                                SharedAccount.getInstance(getActivity()).delete();

                                restartApp();
                            }
                        }, 2000);


                    }
                });
            }
        };
        recyclerView.setAdapter(recyclerAdapter);

        for (String ipName : BaseCloudApi.getIpNameArray()) {
            arrayList.add(new Object());
        }
        recyclerAdapter.notifyDataSetChanged();
    }

    /**
     * 重新启动App -> 杀进程,会短暂黑屏,启动慢
     */
    public void restartApp() {
        //启动页
        Intent intent = new Intent(getActivity(), SplashAct.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 页面跳转
     *
     * @param context
     */
    public static void actionStart(Context context) {
        Bundle bundle = new Bundle();
        IntentUtil.intentToActivity(context, SelectServiceAct.class, bundle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
