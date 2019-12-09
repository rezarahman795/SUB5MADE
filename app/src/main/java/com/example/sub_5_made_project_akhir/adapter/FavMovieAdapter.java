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
import com.example.sub_5_made_project_akhir.model.Movie;

import java.util.ArrayList;

public class FavMovieAdapter extends RecyclerView.Adapter<FavMovieAdapter.ViewHolderFavMovie> {
    private ArrayList<Movie> listMovieFav;
    private Context context;

    public FavMovieAdapter(Context context) {
        this.context = context;
        listMovieFav = new ArrayList<>();
    }

    public void setListMovieFav(ArrayList<Movie> listMovieFav) {
        this.listMovieFav.clear();
        this.listMovieFav.addAll(listMovieFav);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavMovieAdapter.ViewHolderFavMovie onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolderFavMovie(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavMovieAdapter.ViewHolderFavMovie holder, int position) {
        Movie myMovie = listMovieFav.get(position);

        holder.txtNameMovies.setText(myMovie.getDetailNameMovie());
        holder.txtTglMovies.setText(myMovie.getTglMovieDetail());
        holder.rateMovie.setRating((float) (myMovie.getStar() / 2));
        holder.txtDescMovie.setText(myMovie.getDescMovieDetail());

        Glide.with(holder.itemView.getContext())
                .load(BuildConfig.POSTER_URL + myMovie.getImageOrigin())
                .placeholder(R.mipmap.ic_launcher)
                .apply(new RequestOptions().override(350, 550))
                .into(holder.pictMovies);
    }

    @Override
    public int getItemCount() {
        return listMovieFav.size();
    }

    public class ViewHolderFavMovie extends RecyclerView.ViewHolder {
        TextView txtNameMovies, txtTglMovies, txtDescMovie;
        ImageView pictMovies;
        RatingBar rateMovie;

        public ViewHolderFavMovie(@NonNull View itemView) {
            super(itemView);

            pictMovies = itemView.findViewById(R.id.item_poster_movie);
            txtNameMovies = itemView.findViewById(R.id.item_txt_name_movie);
            txtTglMovies = itemView.findViewById(R.id.item_txt_tgl_movie);
            txtDescMovie = itemView.findViewById(R.id.item_txt_desc_movie);
            rateMovie = itemView.findViewById(R.id.movie_rate);
        }
    }
}
