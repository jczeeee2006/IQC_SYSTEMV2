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

import com.example.iqcapplication.Adapters.CustomAdapterInspection;

import com.example.iqcapplication.DatabaseHelper;
import com.example.iqcapplication.R;
import com.example.iqcapplication.encapsulation.InspectionEncapsulation;

import java.util.ArrayList;

public class FragmentForInspection extends Fragment {
    DatabaseHelper myDB;
    CustomAdapterInspection customAdapterIns;
    ArrayList<InspectionEncapsulation> InspectEncap = new ArrayList<>();
    RecyclerView recyclerView;
    Toolbar tOolbar;
    SearchView searchView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_for_inspection, container, false);
        recyclerView = view.findViewById(R.id.recyclerviewins);
        tOolbar = view.findViewById(R.id.toolbarIns);
        recyclerView.setHasFixedSize(true);

        myDB = new DatabaseHelper(getContext());
        displayData();


        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(tOolbar);
        activity.getSupportActionBar().setTitle("SEARCH RECORDS");
        customAdapterIns = new CustomAdapterInspection(getActivity(),getContext(), InspectEncap);
        recyclerView.setAdapter(customAdapterIns);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));



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
                customAdapterIns.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    void displayData() {
        Cursor cursor = myDB.readinspectData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                InspectEncap.add(new InspectionEncapsulation
                        (cursor.getInt(0),
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
                                cursor.getString(15),
                                cursor.getString(16),
                                cursor.getString(17),
                                cursor.getString(18),
                                cursor.getString(19),
                                cursor.getString(20),
                                cursor.getString(21),
                                cursor.getString(22),
                                cursor.getString(23),
                                cursor.getString(24),
                                cursor.getString(25),
                                cursor.getString(26),
                                cursor.getString(27)
                        ));

            }
            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
        }


    }
}