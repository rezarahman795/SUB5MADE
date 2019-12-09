package com.example.sub_5_made_project_akhir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sub_5_made_project_akhir.BuildConfig;
import com.example.sub_5_made_project_akhir.R;
import com.example.sub_5_made_project_akhir.model.TV;

import java.util.ArrayList;

public class TVHorizontalAdapter extends RecyclerView.Adapter<TVHorizontalAdapter.ViewHolderTvHorizontal> {
    private ArrayList<TV> listTVHorizontal;
    private Context context;

    public TVHorizontalAdapter(Context context) {
        this.context = context;
        listTVHorizontal = new ArrayList<>();
    }

    public void setListTVHorizontal(ArrayList<TV> listTV) {

        this.listTVHorizontal = listTV;
    }

    @NonNull
    @Override
    public TVHorizontalAdapter.ViewHolderTvHorizontal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_serial_tv_horizontal, parent, false);
        return new ViewHolderTvHorizontal(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVHorizontalAdapter.ViewHolderTvHorizontal holder, int position) {
        TV myTv = listTVHorizontal.get(position);

        Glide.with(holder.itemView.getContext())
                .load(BuildConfig.POSTER_URL + myTv.getPictureTV())
                .placeholder(R.mipmap.ic_launcher)
                .apply(new RequestOptions().override(350, 550))
                .into(holder.pictTvHorizontal);
    }

    @Override
    public int getItemCount() {
        return listTVHorizontal.size();
    }

    public class ViewHolderTvHorizontal extends RecyclerView.ViewHolder {
        ImageView pictTvHorizontal;

        public ViewHolderTvHorizontal(@NonNull View itemView) {
            super(itemView);
            pictTvHorizontal = itemView.findViewById(R.id.image_item_photo_tv);
        }
    }
}
