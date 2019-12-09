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
import com.example.sub_5_made_project_akhir.model.Movie;

import java.util.ArrayList;

public class MovieHorizontalAdapter extends RecyclerView.Adapter<MovieHorizontalAdapter.ViewMovieHorizontalHolder> {

    private ArrayList<Movie> listMovieHorizontal;
    private Context context;

    public MovieHorizontalAdapter(Context context) {
        this.context = context;
        listMovieHorizontal = new ArrayList<>();
    }

    public void setListMovieHorizontal(ArrayList<Movie> listMovie) {

        this.listMovieHorizontal = listMovie;
    }

    @NonNull
    @Override
    public MovieHorizontalAdapter.ViewMovieHorizontalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_horizontal, parent, false);
        return new ViewMovieHorizontalHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHorizontalAdapter.ViewMovieHorizontalHolder holder, int position) {
        Movie myMovie = listMovieHorizontal.get(position);

        Glide.with(holder.itemView.getContext())
                .load(BuildConfig.POSTER_URL + myMovie.getImageOrigin())
                .placeholder(R.mipmap.ic_launcher)
                .apply(new RequestOptions().override(350, 550))
                .into(holder.pictMoviesHorizontal);
    }

    @Override
    public int getItemCount() {
        return listMovieHorizontal.size();
    }

    public class ViewMovieHorizontalHolder extends RecyclerView.ViewHolder {
        ImageView pictMoviesHorizontal;

        public ViewMovieHorizontalHolder(@NonNull View itemView) {
            super(itemView);

            pictMoviesHorizontal = itemView.findViewById(R.id.image_item_photo_movie);
        }
    }
}
