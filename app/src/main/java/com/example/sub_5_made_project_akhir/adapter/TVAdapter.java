package com.example.sub_5_made_project_akhir.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sub_5_made_project_akhir.BuildConfig;
import com.example.sub_5_made_project_akhir.R;
import com.example.sub_5_made_project_akhir.model.TV;

import java.util.ArrayList;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.ViewHolderTV> {
    private ArrayList<TV> listSerialTV;
    private Context context;

    public TVAdapter(Context context) {
        this.context = context;
        listSerialTV= new ArrayList<>();
    }

    public void setListSerialTV(ArrayList<TV> listTV) {

        this.listSerialTV = listTV;
    }


    @NonNull
    @Override
    public TVAdapter.ViewHolderTV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_serial_tv, parent, false);
        return new ViewHolderTV(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVAdapter.ViewHolderTV holder, int position) {
        TV mySerialTV = listSerialTV.get(position);

        holder.txtNameSerial.setText(mySerialTV.getSerialNameTV());
        holder.txtTglSerial.setText(mySerialTV.getTglSerial());
        holder.txtDescSerial.setText(mySerialTV.getDescSerial());
        holder.rateTV.setRating((float) (mySerialTV.getRateTV()));

        Glide.with(holder.itemView.getContext())
                .load(BuildConfig.POSTER_URL + mySerialTV.getPictureTV())
                .placeholder((R.mipmap.ic_launcher))
                .apply(new RequestOptions().override(350, 550))
                .into(holder.pictSerial);
    }

    @Override
    public int getItemCount() {
        return listSerialTV.size();
    }

    public class ViewHolderTV extends RecyclerView.ViewHolder {
        TextView txtNameSerial, txtTglSerial, txtDescSerial;
        RatingBar rateTV;
        ImageView pictSerial;

        public ViewHolderTV(@NonNull View itemView) {
            super(itemView);

            pictSerial = itemView.findViewById(R.id.item_poster_serial);
            txtNameSerial = itemView.findViewById(R.id.item_txt_name_serial);
            txtTglSerial = itemView.findViewById(R.id.item_txt_tgl_serial);
            txtDescSerial = itemView.findViewById(R.id.txt_desc_serial);
            rateTV = itemView.findViewById(R.id.TV_RATE);
        }
    }
}
