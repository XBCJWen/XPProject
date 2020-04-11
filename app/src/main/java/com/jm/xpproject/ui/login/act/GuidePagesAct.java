package com.jm.xpproject.ui.login.act;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jm.api.util.IntentUtil;
import com.jm.api.util.SharedAccount;
import com.jm.core.common.tools.utils.GuideIndexUtil;
import com.jm.core.common.tools.viewpager.ViewPagerViewUtil;
import com.jm.xpproject.R;
import com.jm.xpproject.base.MyTitleBarActivity;
import com.jm.xpproject.config.change.DataConfig;
import com.jm.xpproject.config.change.UIConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jm.xpproject.config.change.UIConfig.GUIDE_IMAGE;

/**
 * 引导页
 *
 * @author jinXiong.Xie
 */
public class GuidePagesAct extends MyTitleBarActivity {

    @BindView(R.id.viewpager)
    ViewPager adViewPager;
    @BindView(R.id.tv_jump_over)
    TextView tvJumpOver;
    @BindView(R.id.tv_experience)
    TextView tvExperience;
    @BindView(R.id.ll_guide_index)
    LinearLayout llGuideIndex;
    private List<View> views = new ArrayList<>();

    private GuideIndexUtil guideIndexUtil;
    private ViewPagerViewUtil viewPagerViewUtil;

    /**
     * 下标的宽度
     */
    private final int INDEX_WIDTH = 8;

    /**
     * 下标的间距
     */
    private final int INDEX_MARGIN = 5;

    @Override
    protected int layoutResID() {
        return R.layout.activity_guide_pages;
    }

    @Override
    protected void initTitle() {
        hideTitleBar();
    }

    @Override
    public void initViewAndUtil() {
        initGuideIndex(GUIDE_IMAGE.length);

        initAdViewPager();

        //默认显示第一个
        if (GUIDE_IMAGE.length > 0) {
            changeIndexStyle(0);
        }
    }

    @Override
    public void initNetLink() {

    }

    private void initGuideIndex(int num) {

        guideIndexUtil = new GuideIndexUtil(this, llGuideIndex, UIConfig.GUIDE_INDEX_SELECT_BG, UIConfig.GUIDE_INDEX_NORMAL_BG, num);

        guideIndexUtil.setIndexWidth(INDEX_WIDTH);
        guideIndexUtil.setIndexMargin(INDEX_MARGIN);
        guideIndexUtil.initGuideIndex();
    }

    public void initAdViewPager() {

        views = new ArrayList<>();

        for (int i = 0; i < GUIDE_IMAGE.length; i++) {
            //设置引导页中的背景图片
            ImageView imgBg = new ImageView(this);
            imgBg.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imgBg.setImageResource(GUIDE_IMAGE[i]);

            views.add(imgBg);
        }


        viewPagerViewUtil = new ViewPagerViewUtil(adViewPager, views);
        viewPagerViewUtil.setPageSelectedListener(new ViewPagerViewUtil.PageSelectedListener() {
            @Override
            public void onPageSelected(int position) {
                changeIndexStyle(position);
            }
        });
        viewPagerViewUtil.initViewPager();

    }


    /**
     * 更改指示标样式
     *
     * @param position
     */
    private void changeIndexStyle(int position) {
        guideIndexUtil.selectIndex(position);

        if (position == 0) {
            tvJumpOver.setVisibility(View.VISIBLE);
        } else {
            tvJumpOver.setVisibility(View.GONE);
        }

        if (position == GUIDE_IMAGE.length - 1) {
            tvExperience.setVisibility(View.VISIBLE);
        } else {
            tvExperience.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.tv_jump_over, R.id.tv_experience})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_jump_over:
            case R.id.tv_experience:
                goToLogin();
                break;
            default:
                break;
        }
    }

    /**
     * 进入登录页
     */
    private void goToLogin() {
        SharedAccount.getInstance(this).save(false);

        if (DataConfig.NOT_JUMP_OVER_LOGIN){
            DataConfig.turnToLogin(getActivity());
        }else {
            DataConfig.turnToMain(getActivity());
        }
        finish();
    }

    public static void actionStart(Context context) {
        IntentUtil.intentToActivity(context, GuidePagesAct.class);
    }

}
