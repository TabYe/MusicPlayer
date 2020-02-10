package com.ncu.musicplayer.ui;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public abstract class BaseFragment extends Fragment {
    private ViewDataBinding mDataBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return mDataBinding.getRoot();
    }
    @LayoutRes
    abstract protected int getLayoutId();

    protected <T extends ViewDataBinding> T getBinding(){
        return (T)mDataBinding;
    }
    @Override
    public void onDestroyView() {
        if (mDataBinding != null){
            mDataBinding.unbind();
        }
        super.onDestroyView();
    }
}
