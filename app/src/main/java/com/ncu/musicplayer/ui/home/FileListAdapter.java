package com.ncu.musicplayer.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ncu.musicplayer.R;

import java.util.ArrayList;
import java.util.List;

public class FileListAdapter extends RecyclerView.Adapter<FileListAdapter.Holder> {
    List<String> paths = null;
    OnItemClickListener mItemClickListener = null;

    Context mContext;
    public FileListAdapter(Context context,List<String> paths) {
        this.mContext = context.getApplicationContext();
        this.paths = paths;
    }
    public FileListAdapter(Context context) {
        this.mContext = context.getApplicationContext();
        this.paths = new ArrayList<>();
    }
    public void setPaths(List<String> paths) {
        this.paths = paths;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_item, parent, false);
        final Holder holder = new Holder(view);
        if (mItemClickListener != null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onClick(holder);
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        String path = paths.get(position);
        holder.tvPath.setText(path);
        if (path.contains(".mp3")||path.contains(".MP3")){
            holder.imgIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.music));
        }else{
            holder.imgIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.file));
        }
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }

    public static class Holder extends RecyclerView.ViewHolder{
        public TextView tvPath;
        public ImageView imgIcon;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tvPath = itemView.findViewById(R.id.file_item_path);
            imgIcon = itemView.findViewById(R.id.file_item_icon);
        }
    }
    interface OnItemClickListener{
        void onClick(Holder holder);
    }
}
