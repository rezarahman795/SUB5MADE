package com.example.sub_5_made_project_akhir.view.fragment;


import android.app.SearchManager;
import android.content.Context;
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
import com.example.sub_5_made_project_akhir.adapter.TVAdapter;
import com.example.sub_5_made_project_akhir.adapter.TVHorizontalAdapter;
import com.example.sub_5_made_project_akhir.model.Movie;
import com.example.sub_5_made_project_akhir.model.TV;
import com.example.sub_5_made_project_akhir.viewmodel.MovieViewModel;
import com.example.sub_5_made_project_akhir.viewmodel.TvViewModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class TVfragment extends Fragment {
    private RecyclerView rv_verticalTV, rv_horizontalTV;
    private ProgressBar pb_TV;
    private TvViewModel tvViewModel;
    private TVAdapter tvAdapter;
    private TVHorizontalAdapter tvHorizontalAdapter;


    public TVfragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvfragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rv_verticalTV = view.findViewById(R.id.rv_tv);
        rv_verticalTV.setHasFixedSize(true);
        rv_horizontalTV = view.findViewById(R.id.rv_trailer_tv);
        rv_horizontalTV.setHasFixedSize(true);
        setHasOptionsMenu(true);
        pb_TV = view.findViewById(R.id.pb_tv);

        tvAdapter = new TVAdapter(getContext());
        tvAdapter.notifyDataSetChanged();
        tvHorizontalAdapter = new TVHorizontalAdapter(getContext());
        tvHorizontalAdapter.notifyDataSetChanged();

        showRecyclerList();
        showLoadingFragment(true);
    }

    private void showRecyclerList() {

        tvViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TvViewModel.class);
        tvViewModel.getTrailerTV().observe(this, getTrailerTV);
        tvViewModel.setTrailerTV();

        tvViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TvViewModel.class);
        tvViewModel.getRequestTV().observe(this, getRequestTV);
        tvViewModel.setRequestTV();

        rv_verticalTV.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_verticalTV.setAdapter(tvAdapter);
        rv_horizontalTV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_horizontalTV.setAdapter(tvHorizontalAdapter);
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
                    tvViewModel.getSearchTV().observe(Objects.requireNonNull(getActivity()), getSearchTV);
                    tvViewModel.setSearchTV(keyword);
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
                tvViewModel.setRequestTV();
                return true;
            }
        });
    }


    private final Observer<ArrayList<TV>> getTrailerTV = new Observer<ArrayList<TV>>() {
        @Override
        public void onChanged(ArrayList<TV> trailerTV) {
            showLoadingFragment(true);
            if (trailerTV != null) {
                tvHorizontalAdapter.setListTVHorizontal(trailerTV);
                showRecyclerList();
                showLoadingFragment(false);

            } else {
                showLoadingFragment(false);
            }
        }
    };

    private final Observer<ArrayList<TV>> getRequestTV = new Observer<ArrayList<TV>>() {
        @Override
        public void onChanged(ArrayList<TV> requestTV) {
            showLoadingFragment(true);
            if (requestTV != null) {
                tvAdapter.setListSerialTV(requestTV);
                showRecyclerList();
                showLoadingFragment(false);

            } else {
                showLoadingFragment(false);
            }
        }
    };

    private final Observer<ArrayList<TV>> getSearchTV = new Observer<ArrayList<TV>>() {
        @Override
        public void onChanged(ArrayList<TV> searchTV) {
            showLoadingFragment(true);
            if (searchTV != null) {
                tvAdapter.setListSerialTV(searchTV);
                showRecyclerList();
                showLoadingFragment(false);
                if (searchTV.size() == 0)
                    Toast.makeText(getContext(), "Sorry! No Data Result", Toast.LENGTH_LONG).show();
            } else {
                showLoadingFragment(false);
            }
        }
    };

    private void showLoadingFragment(boolean state) {
        if (state) {
            pb_TV.setVisibility(View.VISIBLE);
        } else {
            pb_TV.setVisibility(View.GONE);
        }
    }
}
