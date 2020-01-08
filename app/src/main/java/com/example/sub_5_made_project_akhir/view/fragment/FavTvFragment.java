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
import com.example.sub_5_made_project_akhir.adapter.FavTVAdapter;
import com.example.sub_5_made_project_akhir.model.TV;
import com.example.sub_5_made_project_akhir.viewmodel.TvViewModel;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavTvFragment extends Fragment {
    private RecyclerView rv_Fav_Tv;
    private FavTVAdapter favTVAdapter;
    private ProgressBar pb_fav_tv;
    private TvViewModel tvViewModel;


    public FavTvFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_tv, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv_Fav_Tv = view.findViewById(R.id.rv_fav_tv);
        rv_Fav_Tv.setHasFixedSize(true);
        pb_fav_tv = view.findViewById(R.id.pb_fav_tv);
        setHasOptionsMenu(true);

        favTVAdapter = new FavTVAdapter(getContext());
        favTVAdapter.notifyDataSetChanged();

        showRecyclerListFavTV();
        showLoadingFragment(true);
    }

    private void showRecyclerListFavTV() {

        tvViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(TvViewModel.class);
        tvViewModel.getTvFavourite("entertainment").observe(this, getFavObserveTV);
        tvViewModel.setDBFavTv("entertainment");

        rv_Fav_Tv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_Fav_Tv.setAdapter(favTVAdapter);

    }

    private final Observer<ArrayList<TV>> getFavObserveTV = new Observer<ArrayList<TV>>() {
        @Override
        public void onChanged(ArrayList<TV> tv) {
            if (tv != null) {
                favTVAdapter.setListFavTV(tv);
                showRecyclerListFavTV();
                showLoadingFragment(false);

            } else {
                showLoadingFragment(false);
            }
        }
    };

    private void showLoadingFragment(boolean state) {
        if (state) {
            pb_fav_tv.setVisibility(View.VISIBLE);
        } else {
            pb_fav_tv.setVisibility(View.GONE);
        }
    }

    public void onResume() {
        super.onResume();
        tvViewModel  = ViewModelProviders.of(this).get(TvViewModel.class);
        tvViewModel.setDBFavTv("entertainment");
    }
}
