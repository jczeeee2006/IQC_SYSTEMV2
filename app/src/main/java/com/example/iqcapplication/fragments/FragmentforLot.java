package com.example.iqcapplication.fragments;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.iqcapplication.Adapters.CustomAdapterLot;
import com.example.iqcapplication.DatabaseHelper;
import com.example.iqcapplication.R;
import com.example.iqcapplication.encapsulation.LotEncapsulation;

import java.util.ArrayList;


public class FragmentforLot extends Fragment {
    DatabaseHelper myDB;
    CustomAdapterLot customAdapterLot;
    ArrayList<LotEncapsulation> lotData = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_forlot, container, false);

        recyclerView = view.findViewById(R.id.recyclerview);

        recyclerView.setHasFixedSize(true);

        myDB = new DatabaseHelper(getContext());

        customAdapterLot = new CustomAdapterLot(getActivity(),getContext(), lotData);
        recyclerView.setAdapter(customAdapterLot);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        displayData();

        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        return view;
    }

    void displayData() {
        int ctr= 0;
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "no data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {

                lotData.add(new LotEncapsulation
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
                                cursor.getString(14)
                        ));

            }
            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
        }
    }
}