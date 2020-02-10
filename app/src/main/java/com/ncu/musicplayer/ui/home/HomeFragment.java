package com.ncu.musicplayer.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ncu.musicplayer.R;
import com.ncu.musicplayer.databinding.FragmentHomeBinding;
import com.ncu.musicplayer.ui.BaseFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import io.reactivex.functions.Consumer;

public class HomeFragment extends BaseFragment {

    FragmentHomeBinding mBinding;

    FileListAdapter mAdapter;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding = getBinding();

        new RxPermissions(this).request(Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean){
                            init(savedInstanceState);
                        }
                    }
                });
    }
    void init(Bundle savedInstanceState){
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    list.sort(new Comparator<String>() {
                        @Override
                        public int compare(String o1, String o2) {
                            return o1.compareTo(o2);
                        }
                    });
                }
                mAdapter.setPaths(list);
                mBinding.setPath(path);
            }
            else if (file.isFile()){
                if (file.getAbsolutePath().contains(".mp3")||file.getAbsolutePath().contains(".MP3")){
                    add(file);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    void add(File file){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uriForFile = FileProvider.getUriForFile(getContext(), "com.ncu.musicplayer.fileprovider", file);
        Log.e("Uri",uriForFile + "");
        intent.setDataAndType(uriForFile, "audio/MP3");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }
}