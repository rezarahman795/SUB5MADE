package com.example.sub_5_made_project_akhir.view.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.sub_5_made_project_akhir.R;
import com.example.sub_5_made_project_akhir.adapter.FavMovieAdapter;
import com.example.sub_5_made_project_akhir.model.Movie;
import com.example.sub_5_made_project_akhir.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavMovieFragment extends Fragment {
    private RecyclerView rv_Fav_movie;
    private FavMovieAdapter favMovieAdapter;
    private ProgressBar pb_fav_movie;
    private MovieViewModel movieViewModelFav;


    public FavMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_Fav_movie = view.findViewById(R.id.rv_fav_movie);
        rv_Fav_movie.setHasFixedSize(true);
        pb_fav_movie = view.findViewById(R.id.pb_fav_movie);
        setHasOptionsMenu(true);

        favMovieAdapter = new FavMovieAdapter(getContext());
        favMovieAdapter.notifyDataSetChanged();

        showRecyclerListFavMovie();
        showLoadingFragment(true);
    }

    private void showRecyclerListFavMovie() {

        movieViewModelFav = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MovieViewModel.class);
        movieViewModelFav.getMovieFavourite("entertainment").observe(this, getFavObserveMovie);
        movieViewModelFav.setDBFavMovies("entertainment");

        rv_Fav_movie.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_Fav_movie.setAdapter(favMovieAdapter);

    }

    private final Observer<ArrayList<Movie>> getFavObserveMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movie) {
            if (movie != null) {
                favMovieAdapter.setListMovieFav(movie);
                showRecyclerListFavMovie();
                showLoadingFragment(false);

            } else {
                showLoadingFragment(false);
            }
        }
    };

    private void showLoadingFragment(boolean state) {
        if (state) {
            pb_fav_movie.setVisibility(View.VISIBLE);
        } else {
            pb_fav_movie.setVisibility(View.GONE);
        }
    }

    public void onResume() {
        super.onResume();
        movieViewModelFav  = ViewModelProviders.of(this).get(MovieViewModel.class);
        movieViewModelFav.setDBFavMovies("entertainment");
    }
}
