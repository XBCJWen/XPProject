package com.jm.core.common.tools.utils;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jm.core.common.tools.base.PixelsTools;

import java.util.ArrayList;
import java.util.List;

/**
 * 导航下标的工具类
 *
 * @author jinXiong.Xie
 */

public class GuideIndexUtil {

    private Context context;
    /**
     * 导航栏的布局
     */
    private LinearLayout llGuide;
    /**
     * 选择的图片
     */
    private int selectDrawable;
    /**
     * 正常的图片
     */
    private int normalDrawable;
    /**
     * 下标数
     */
    private int num;
    /**
     * 储存下标
     */
    private List<ImageView> indexViews = new ArrayList<>();

    public GuideIndexUtil(Context context, LinearLayout llGuide, int selectDrawable, int normalDrawable, int num) {
        this.context = context;
        this.llGuide = llGuide;
        this.selectDrawable = selectDrawable;
        this.normalDrawable = normalDrawable;
        this.num = num;
    }

    /**
     * 下标的宽度
     */
    private int indexWidth = -1;
    /**
     * 下标的高度
     */
    private int indexHeight = -1;
    /**
     * 默认的下标宽度
     */
    private final int DEFAULT_INDEX_WIDTH = 8;

    /**
     * 下标的间距
     */
    private int indexMargin = 5;

    public void setIndexWidthHeight(int indexWidth, int indexHeight) {
        this.indexWidth = indexWidth;
        this.indexHeight = indexHeight;
    }

    public void setIndexWidth(int indexWidth) {
        this.indexWidth = indexWidth;
        this.indexHeight = indexWidth;
    }

    public void setIndexMargin(int indexMargin) {
        this.indexMargin = indexMargin;
    }

    /**
     * 清除下标宽度
     */
    public void clearIndexWidth() {
        indexWidth = -1;
    }

    /**
     * 初始化下标
     */
    public void initGuideIndex() {
        //移除原有的View
        if (llGuide != null) {
            llGuide.removeAllViews();
        }
        if (indexViews != null) {
            indexViews.clear();
        }

        int imageWidth;
        int imageHeight;
        if (indexWidth == -1) {
            imageWidth = DEFAULT_INDEX_WIDTH;
            imageHeight = DEFAULT_INDEX_WIDTH;
        } else {
            imageWidth = indexWidth;
            imageHeight = indexHeight;
        }

        int pixelsWidth = PixelsTools.dip2Px(context, imageWidth);
        int pixelsHeight = PixelsTools.dip2Px(context, imageHeight);

        for (int i = 0; i < num; i++) {
            ImageView imageView = new ImageView(context);
            LinearLayout.LayoutParams layoutParams = null;
            if (indexWidth == -1) {
                layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            } else {
                layoutParams = new LinearLayout.LayoutParams(pixelsWidth, pixelsHeight);
            }
            if (i != num - 1) {
                layoutParams.setMargins(0, 0, PixelsTools.dip2Px(context, indexMargin), 0);
            }
            imageView.setLayoutParams(layoutParams);

            indexViews.add(imageView);
            llGuide.addView(imageView);
        }

        selectIndex(0);
    }

    /**
     * 选择下标
     *
     * @param position
     */
    public void selectIndex(int position) {
        for (int i = 0; i < indexViews.size(); i++) {
            if (i == position) {
                indexViews.get(i).setImageResource(selectDrawable);
            } else {
                indexViews.get(i).setImageResource(normalDrawable);
            }
        }
    }
}
