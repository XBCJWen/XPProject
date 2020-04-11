package com.jm.core.common.widget.imageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.jm.core.R;

/**
 * 自定义开关按钮
 * Created by Jinxiong.Xie on 2016/10/19.
 */
public class SwitchButton extends ImageView implements View.OnClickListener {

    private int closeImage;//关闭状态下显示的图片
    private int openImage;//开启状态下显示的图片
    private OnSwitchButtonClick onSwitchButtonClick;

    public SwitchButton(Context context) {
        super(context);
        init();
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.SwitchButton, defStyleAttr, 0);

        closeImage = a.getResourceId(R.styleable.SwitchButton_closeImage, 0);
        openImage = a.getResourceId(R.styleable.SwitchButton_openImage, 0);

        a.recycle();//完成资源回收

        init();
    }

    /**
     * 初始化
     */
    private void init(){
        changeImageShow(isSelected());

        setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        setSelected(!isSelected());
        changeImageShow(isSelected());

        if (onSwitchButtonClick != null) {
            onSwitchButtonClick.onClick(isSelected());
        }
    }

    /**
     * 更改图片的显示
     * @param isSelected
     */
    public void changeImageShow(boolean isSelected) {

        if (closeImage == 0 || openImage == 0) {
            return;
        }

        if (isSelected) {
            setImageResource(openImage);
        } else {
            setImageResource(closeImage);
        }
    }

    public interface OnSwitchButtonClick {

        void onClick(boolean selected);
    }

    public void setOnSwitchButtonClick(OnSwitchButtonClick onSwitchButtonClick) {
        this.onSwitchButtonClick = onSwitchButtonClick;
    }

    public void setCloseImage(int closeImage) {
        this.closeImage = closeImage;
    }

    public void setOpenImage(int openImage) {
        this.openImage = openImage;
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        changeImageShow(selected);
    }
}

//<com.xiaoyan.mylibrary.SwitchButton
//        android:id="@+id/switchbtn"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        app:closeImage="@drawable/warn_close_btn"
//        app:openImage="@drawable/warn_open_btn" />

//switchButton = (SwitchButton) findViewById(R.id.switchbtn);
//        switchButton.setSelected(true);
//        switchButton.setOnSwitchButtonClick(new SwitchButton.OnSwitchButtonClick() {
//@Override
//public void onClick(boolean selected) {
//        Toast.makeText(MainActivity.this, "selected:" + selected, Toast.LENGTH_SHORT).show();
//        }
//        });
