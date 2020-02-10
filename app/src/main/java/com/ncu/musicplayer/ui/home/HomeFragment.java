package com.ncu.musicplayer.ui.home;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ncu.musicplayer.R;
import com.ncu.musicplayer.databinding.FragmentHomeBinding;
import com.ncu.musicplayer.ui.BaseFragment;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends BaseFragment {

    FragmentHomeBinding mBinding;

    FileListAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding = getBinding();

        String rootPath = "/storage/emulated/0";
        mBinding.setPath(rootPath);
        mAdapter = new FileListAdapter(getContext());
        updateList(rootPath);
        mBinding.fileList.setAdapter(mAdapter);
        mBinding.fileList.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter.setOnItemClickListener(new FileListAdapter.OnItemClickListener() {
            @Override
            public void onClick(FileListAdapter.Holder holder) {
                String path = holder.tvPath.getText().toString();
                path = mBinding.getPath() + File.separator + path;
                updateList(path);
            }
        });

        mBinding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(mBinding.getPath());
                String path = file.getParent();
                updateList(path);
            }
        });
    }
    void updateList(String path){
        try {
            File file = new File(path);
            if (file.isDirectory()){
                List<String> list = Arrays.asList(file.list());
                mAdapter.setPaths(list);
                mBinding.setPath(path);
            }
            else if (file.isFile()){
                if (file.getAbsolutePath().contains(".mp3")||file.getAbsolutePath().contains(".MP3")){
                    add();
                }
            }
        }catch(Exception e){

        }
    }
    void add(){}
}