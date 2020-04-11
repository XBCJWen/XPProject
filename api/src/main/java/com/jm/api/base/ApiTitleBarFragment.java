package com.jm.api.base;

import com.jm.core.common.tools.base.PixelsTools;
import com.jm.core.framework.BaseTitleBarFragment;


/**
 * 基于MyTitleBarFragment的修改
 *
 * @author jinXiong.Xie
 */

public abstract class ApiTitleBarFragment extends BaseTitleBarFragment {



    public int dip2Px(int dpValue) {
        return PixelsTools.dip2Px(getActivity(), dpValue);
    }

    public void showDataErrorToast() {
        showToast("数据异常");
    }
}
