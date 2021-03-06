package com.zxdz.car.base.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.jakewharton.rxbinding.view.RxView;
import com.zxdz.car.R;


import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;


public class StateView extends BaseView {
    @BindView(R.id.iv_loading)
    ImageView mLoadingImageView;

    @BindView(R.id.tv_message)
    TextView mMessageTextView;

    @BindView(R.id.btn_refresh)
    Button mRefreshButton;

    private View mContextView;

    public StateView(Context context) {
        super(context);
    }

    public StateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.base_view_state;
    }

    public void hide() {
        setVisibility(View.GONE);
        if (mContextView != null) {
            mContextView.setVisibility(View.VISIBLE);
        }
    }

    public void showLoading(View contextView, int type) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLoadingImageView.getLayoutParams();
        layoutParams.width = SizeUtils.dp2px(1080 / 3);
        layoutParams.height = SizeUtils.dp2px(408 / 3);
        mLoadingImageView.setLayoutParams(layoutParams);
        mLoadingImageView.invalidate();
        mContextView = contextView;
        mMessageTextView.setText("");
        setVisibility(View.VISIBLE);
        mRefreshButton.setVisibility(View.GONE);
        mContextView.setVisibility(View.GONE);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(SizeUtils.dp2px(1080 / 3), SizeUtils.dp2px(408 / 3));
        if (type == 1) {
            Glide.with(this).load(R.mipmap.base_loading).apply(requestOptions).into(mLoadingImageView);
        } else {
            Glide.with(this).load(R.mipmap.base_loading).apply(requestOptions).into(mLoadingImageView);
        }
    }


    public void showLoading(View contextView) {
        showLoading(contextView, 1);
    }

    public void showNoData(View contextView, String message) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLoadingImageView.getLayoutParams();
        layoutParams.width = SizeUtils.dp2px(312 / 3);
        layoutParams.height = SizeUtils.dp2px(370 / 3);
        mLoadingImageView.setLayoutParams(layoutParams);
        mContextView = contextView;
        setVisibility(View.VISIBLE);
        mRefreshButton.setVisibility(View.GONE);
        mContextView.setVisibility(View.GONE);
        mMessageTextView.setText(message);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(SizeUtils.dp2px(312 / 3), SizeUtils.dp2px(370 / 3));
        Glide.with(this).load(R.mipmap.base_no_data).apply(requestOptions).into(mLoadingImageView);
    }

    public void showNoData(View contextView) {
        showNoData(contextView, "暂无数据");
    }

    public void showNoNet(View contextView, String message, final OnClickListener onClickListener) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mLoadingImageView.getLayoutParams();
        layoutParams.width = SizeUtils.dp2px(312 / 3);
        layoutParams.height = SizeUtils.dp2px(370 / 3);
        mLoadingImageView.setLayoutParams(layoutParams);
        mContextView = contextView;
        setVisibility(View.VISIBLE);
        mContextView.setVisibility(View.GONE);
        mRefreshButton.setVisibility(View.VISIBLE);
        mMessageTextView.setText(message);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.override(SizeUtils.dp2px(312 / 3), SizeUtils.dp2px(370 / 3));
        if (ActivityUtils.isValidContext(getContext())) {
            Glide.with(this).load(R.mipmap.base_no_wifi).apply(requestOptions).into(mLoadingImageView);
        }
        RxView.clicks(mRefreshButton).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                onClickListener.onClick(mRefreshButton);
            }
        });
    }
}
