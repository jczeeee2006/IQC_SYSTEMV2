package com.example.iqcapplication.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.iqcapplication.Adapters.CustomAdapterLot;
import com.example.iqcapplication.DatabaseHelper;
import com.example.iqcapplication.R;
import com.example.iqcapplication.fragments.encapsulation.LotEncapsulation;

import java.util.ArrayList;


public class FragmentforLot extends Fragment  {
    DatabaseHelper myDB;
    CustomAdapterLot customAdapterLot;
    ArrayList<LotEncapsulation> lotData = new ArrayList<>();
    RecyclerView recyclerView;
    Toolbar tOolbar;
    MenuItem menuItem;
    SearchView searchView;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_forlot, container, false);



        recyclerView = view.findViewById(R.id.recyclerview);
        tOolbar = view.findViewById(R.id.toolbar);
        myDB = new DatabaseHelper(getContext());


        //-------------SEARCH VIEW TOOLBAR------------//
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(tOolbar);
        activity.getSupportActionBar().setTitle("SEARCH RECORDS");
        customAdapterLot = new CustomAdapterLot(getActivity(),getContext(), lotData);

        recyclerView.setAdapter(customAdapterLot);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        displayData();

        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        //-------------SEARCH VIEW DECLARE------------//
        inflater.inflate(R.menu.search_v, menu);
        MenuItem item = menu.findItem(R.id.searchairlist);
            searchView = (SearchView)item.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customAdapterLot.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }


    void displayData() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {

                lotData.add(new LotEncapsulation
                        (cursor.getString(0),
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6),
                                cursor.getString(7),
                                cursor.getString(8),
                                cursor.getString(9),
                                cursor.getString(10),
                                cursor.getString(11),
                                cursor.getString(12),
                                cursor.getString(13),
                                cursor.getString(14),
                                cursor.getString(15)
                        ));

            }
            Toast.makeText(getContext(), "View Successfully Loaded", Toast.LENGTH_SHORT).show();
        }
    }
}