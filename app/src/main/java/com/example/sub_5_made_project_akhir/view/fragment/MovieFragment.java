package com.example.sub_5_made_project_akhir.view.fragment;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.sub_5_made_project_akhir.R;
import com.example.sub_5_made_project_akhir.adapter.MovieAdapter;
import com.example.sub_5_made_project_akhir.adapter.MovieHorizontalAdapter;
import com.example.sub_5_made_project_akhir.model.Movie;
import com.example.sub_5_made_project_akhir.view.activity.MovieDetailActivity;
import com.example.sub_5_made_project_akhir.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment {
    private RecyclerView rv_verticalMovie, rv_horizontalMovie;
    private MovieAdapter movieAdapter;
    private MovieViewModel movieViewModel;
    private ProgressBar pb_Movie;
    private MovieHorizontalAdapter movieHorizontalAdapter;


    public MovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_verticalMovie = view.findViewById(R.id.rv_movie);
        rv_verticalMovie.setHasFixedSize(true);
        rv_horizontalMovie = view.findViewById(R.id.rv_trailer_movie);
        rv_horizontalMovie.setHasFixedSize(true);
        setHasOptionsMenu(true);
        pb_Movie = view.findViewById(R.id.pb_movie);

        movieAdapter = new MovieAdapter(getContext());
        movieAdapter.notifyDataSetChanged();
        movieHorizontalAdapter = new MovieHorizontalAdapter(getContext());
        movieHorizontalAdapter.notifyDataSetChanged();

        showRecyclerList();
        showLoadingFragment(true);
    }


    private void showRecyclerList() {

        movieViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MovieViewModel.class);
        movieViewModel.getTrailerMovie().observe(this, getTrailerMovie);
        movieViewModel.setTrailersMovies();

        movieViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MovieViewModel.class);
        movieViewModel.getMovies().observe(this, getRequestMovie);
        movieViewModel.setRequestMovie();

        rv_verticalMovie.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_verticalMovie.setAdapter(movieAdapter);
        rv_horizontalMovie.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_horizontalMovie.setAdapter(movieHorizontalAdapter);

        movieAdapter.setOnItemClickCallback(new MovieAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(Movie dataMovie) {

                showSelectedMovie(dataMovie);

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.toolbar_menu, menu);

        SearchManager searchMovie = (SearchManager) Objects.requireNonNull(getActivity()).getSystemService(Context.SEARCH_SERVICE);

        if (searchMovie != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchMovie.getSearchableInfo(getActivity().getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_movie));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String keyword) {
                    movieViewModel.getSearchMovies().observe(Objects.requireNonNull(getActivity()), getSearchMovie);
                    movieViewModel.setSearchMovie(keyword);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }

        MenuItem menuItem = menu.findItem(R.id.search);
        menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                movieViewModel.setRequestMovie();
                return true;
            }
        });
    }


    private final Observer<ArrayList<Movie>> getTrailerMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> trailerMovie) {
            showLoadingFragment(true);
            if (trailerMovie != null) {
                movieHorizontalAdapter.setListMovieHorizontal(trailerMovie);
                showRecyclerList();
                showLoadingFragment(false);

            } else {
                showLoadingFragment(false);
            }
        }
    };

    private final Observer<ArrayList<Movie>> getRequestMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> requestMovie) {
            showLoadingFragment(true);
            if (requestMovie != null) {
                movieAdapter.setListMovie(requestMovie);
                showRecyclerList();
                showLoadingFragment(false);

            } else {
                showLoadingFragment(false);
            }
        }
    };

    private final Observer<ArrayList<Movie>> getSearchMovie = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movie) {
            showLoadingFragment(true);
            if (movie != null) {
                movieAdapter.setListMovie(movie);
                showRecyclerList();
                showLoadingFragment(false);
                if (movie.size() == 0)
                    Toast.makeText(getContext(), "Sorry! No Data Result", Toast.LENGTH_LONG).show();
            } else {
                showLoadingFragment(false);
            }
        }
    };

    private void showLoadingFragment(boolean state) {
        if (state) {
            pb_Movie.setVisibility(View.VISIBLE);
        } else {
            pb_Movie.setVisibility(View.GONE);
        }
    }
    private void showSelectedMovie(Movie detailDataMovie) {
        Intent movieDetail = new Intent(getContext(), MovieDetailActivity.class);
        movieDetail.putExtra("GET_DATA_MOVIE", detailDataMovie);
        startActivity(movieDetail);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


}
