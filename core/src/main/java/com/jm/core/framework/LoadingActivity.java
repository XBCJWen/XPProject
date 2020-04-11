package com.jm.core.framework;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jm.core.R;
import com.jm.core.common.widget.layout.LoadingLayout;

/**
 * 默认具有加载状态布局的Activity
 */
public abstract class LoadingActivity extends BaseActivity {

  protected LoadingLayout vLoading;

  @Override
  protected View layoutView() {

    ViewGroup viewGroup = (ViewGroup) inflateLayout(R.layout.activity_loading);
    vLoading = (LoadingLayout) viewGroup.findViewById(R.id.loading);
    View root = LayoutInflater.from(this).inflate(layoutResID(), viewGroup, false);
    vLoading.addView(root);
    vLoading = LoadingLayout.wrap(root);

    return viewGroup;
  }

  protected abstract int layoutResID();

//  @Override
//  protected void InitView() {
//
//    init();
////    vLoading.setRetryListener(new View.OnClickListener() {
////      @Override
////      public void onClick(View v) {
////        Toast.makeText(v.getContext(), "retry" , Toast.LENGTH_LONG).show();
////      }
////    });
//////        vLoading.showEmpty();
////    vLoading.showEmpty();
//  }
//
//  protected abstract void init();
}
