package com.jm.core.common.widget.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jm.core.R;


/**
 * 可选择文本
 */
public class SimpleCheckView extends RelativeLayout {
    protected String mTitleText = "";
    protected boolean mChecked = false;
    protected TextView mTitleTextView;
    protected ImageView mCheckImageView;
    protected OnCheckedChangeListener mOnCheckedChangeListener;
    protected OnClickListener mOnClickListener;
    protected boolean mBroadcasting;

    public SimpleCheckView(Context context) {
        super(context);
        init(context, null);
    }

    public SimpleCheckView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);


    }

    /**
     * 设置文本标题颜色
     * @param colorId
     */
    public void setTextColor(int colorId){
        mTitleTextView.setTextColor(getResources().getColor(colorId));
    }

    protected void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.pocket_simple_check, this);
        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mCheckImageView = (ImageView) findViewById(R.id.check);
        parseStyle(context, attrs);
    }

    protected void parseStyle(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SimpleCheckView);
            mTitleText = ta.getString(R.styleable.SimpleCheckView_titleText);
            mChecked = ta.getBoolean(R.styleable.SimpleCheckView_checked, false);
            mTitleTextView.setText(mTitleText);
            if (mChecked) {
                mCheckImageView.setVisibility(VISIBLE);
            } else {
                mCheckImageView.setVisibility(GONE);
            }
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    setChecked(!mChecked);
                }
            });
            ta.recycle();
        }
    }


    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            if (mChecked) {
                mCheckImageView.setVisibility(VISIBLE);
            } else {
                mCheckImageView.setVisibility(GONE);
            }
            if (mBroadcasting) {
                return;
            }
            mBroadcasting = true;
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(this, mChecked);
            }
            mBroadcasting = false;
        }
    }

    public String getTitleText() {
        return mTitleText;
    }

    public void setTitleText(String titleText) {
        mTitleText = titleText;
        mTitleTextView.setText(titleText);
    }

    public interface OnCheckedChangeListener {
        void onCheckedChanged(SimpleCheckView v, boolean checked);
    }

    public OnCheckedChangeListener getOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

}

//<com.yilife.pocket.widget.SimpleCheckView
//        android:id="@+id/check6"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:background="@drawable/btn_list_item_bg"
//        android:clickable="true"
//        app:titleText="违法（暴力恐怖、违禁品）"/>
