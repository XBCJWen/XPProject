package com.jm.xpproject.ui.main.fgm;


import android.support.v4.app.Fragment;
import android.view.View;

import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarFragment;
import com.jm.xpproject.ui.setting.act.SettingAct;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class GuideFgm extends MyTitleBarFragment {

    @Override
    protected int layoutResID() {
        return R.layout.fragment_guide_fgm;
    }

    @Override
    protected void initTitle() {
        setTitle(true, "dasda");
    }

    @Override
    public void initViewAndUtil(View view) {

    }

    @Override
    public void initNetLink() {

    }


    @OnClick(R.id.tv_setting)
    public void onViewClicked() {
        SettingAct.actionStart(getActivity());
    }
}
