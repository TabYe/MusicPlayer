package com.ncu.musicplayer.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ncu.musicplayer.R;
import com.ncu.musicplayer.ui.BaseFragment;

public class NotificationsFragment extends BaseFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_notifications;
    }
}