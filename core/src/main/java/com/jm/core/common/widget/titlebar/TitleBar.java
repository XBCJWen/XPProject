package com.jm.core.common.widget.titlebar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jm.core.R;
import com.jm.core.common.tools.utils.ActivitiesManager;

/**
 * 标题
 *
 * @author jinXiong.Xie
 * @deprecated Use {@link MyTitleBar} instead.
 */
@Deprecated
public class TitleBar extends RelativeLayout {

    protected RelativeLayout leftLayout;
    protected ImageView leftImage;
    protected RelativeLayout rightLayout;
    protected ImageView rightImage;
    protected TextView titleView;
    private TextView rightTextView;
    private TextView leftTextView;
    protected RelativeLayout titleLayout;

    public TitleBar(Context context) {
        super(context);
        init(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RelativeLayout getTitleLayout() {
        return titleLayout;
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.widget_title_bar, this);
        leftLayout = (RelativeLayout) findViewById(R.id.left_layout);
        leftImage = (ImageView) findViewById(R.id.left_image);
        rightLayout = (RelativeLayout) findViewById(R.id.right_layout);
        rightImage = (ImageView) findViewById(R.id.right_image);
        titleView = (TextView) findViewById(R.id.title);
        rightTextView = (TextView) findViewById(R.id.right_text);
        leftTextView = (TextView) findViewById(R.id.left_text);
        titleLayout = (RelativeLayout) findViewById(R.id.root);

        parseStyle(context, attrs);
    }

    private void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PocketTitleBar);
            String title = ta.getString(R.styleable.PocketTitleBar_titleBarTitle);
            titleView.setText(title);

            String leftText = ta.getString(R.styleable.PocketTitleBar_titleBarLeftText);
            if (TextUtils.isEmpty(leftText)) {
                leftTextView.setVisibility(GONE);
            } else {
                leftTextView.setText(title);
            }

            Drawable leftDrawable = ta.getDrawable(R.styleable.PocketTitleBar_titleBarLeftImage);
            if (null != leftDrawable) {
                leftImage.setImageDrawable(leftDrawable);
            } else {
//                leftImage.setImageResource(R.drawable.icon_back);
            }
            leftLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getContext() instanceof Activity) {
                        ActivitiesManager.getInstance().popOneActivity(((Activity) getContext()));
                    }
                }
            });
            Drawable rightDrawable = ta.getDrawable(R.styleable.PocketTitleBar_titleBarRightImage);
            if (null != rightDrawable) {
                rightImage.setImageDrawable(rightDrawable);
            } else {
                rightImage.setVisibility(GONE);
            }

            String rightText = ta.getString(R.styleable.PocketTitleBar_titleBarRightText);
            if (TextUtils.isEmpty(rightText)) {
                rightTextView.setVisibility(GONE);
            } else {
                rightTextView.setText(title);
            }

            Drawable background = ta.getDrawable(R.styleable.PocketTitleBar_titleBarBackground);
            if (null != background) {
                titleLayout.setBackgroundDrawable(background);
            }

            ta.recycle();
        }
    }

    public void setLeftImageResource(int resId) {
        leftImage.setImageResource(resId);
    }

    public void setRightImageResource(int resId) {
        rightImage.setImageResource(resId);
        rightImage.setVisibility(VISIBLE);
    }

    public void setLeftLayoutClickListener(OnClickListener listener) {
        leftLayout.setOnClickListener(listener);
    }

    public void setRightLayoutClickListener(OnClickListener listener) {
        rightLayout.setOnClickListener(listener);
    }

    public void setLeftLayoutVisibility(int visibility) {
        leftLayout.setVisibility(visibility);
    }

    public void setBackVisibility(int visibility) {
        leftImage.setVisibility(visibility);
    }

    public void setRightLayoutVisibility(int visibility) {
        rightLayout.setVisibility(visibility);
    }

    public void setTitle(String title) {
        titleView.setText(title);
    }

    @Override
    public void setBackgroundColor(int color) {
        titleLayout.setBackgroundColor(color);
    }

    public void setRightText(String text) {
        rightTextView.setText(text);
        rightTextView.setVisibility(VISIBLE);
    }

    public void setLeftText(String text) {
        leftTextView.setText(text);
        leftTextView.setVisibility(VISIBLE);

        leftImage.setVisibility(GONE);
        leftLayout.setOnClickListener(null);
    }

    public RelativeLayout getLeftLayout() {
        return leftLayout;
    }

    public RelativeLayout getRightLayout() {
        return rightLayout;
    }

}

//<com.ui.widget.PocketTitleBar
//        android:id="@+id/title_bar"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        app:titleBarLeftImage="@drawable/pocket_title_back"
//        app:titleBarTitle="@string/title_about" />