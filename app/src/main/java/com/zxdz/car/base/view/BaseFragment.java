package com.zxdz.car.base.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.EmptyUtils;
import com.blankj.utilcode.util.LogUtils;
import com.hwangjr.rxbus.RxBus;
import com.zxdz.car.base.presenter.BasePresenter;
import butterknife.ButterKnife;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IView {
    protected View mRootView;
    protected P mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RxBus.get().register(this);
        if (mRootView == null) {
            mRootView = View.inflate(getActivity(), getLayoutId(), null);
            try {
                ButterKnife.bind(this, mRootView);
            } catch (Exception e) {
                e.printStackTrace();
                LogUtils.i(this.getClass().getSimpleName() + " init->初始化失败 原因:" + e);
            }
            init();
        }
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (EmptyUtils.isNotEmpty(mPresenter))
            mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EmptyUtils.isNotEmpty(mPresenter))
            mPresenter.unsubscribe();
        RxBus.get().unregister(this);
    }

}
