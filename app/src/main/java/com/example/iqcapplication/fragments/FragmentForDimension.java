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

import com.example.iqcapplication.Adapters.CustomAdapterDimension;
import com.example.iqcapplication.Adapters.CustomAdapterInspection;
import com.example.iqcapplication.Adapters.CustomAdapterLot;
import com.example.iqcapplication.DatabaseHelper;
import com.example.iqcapplication.R;
import com.example.iqcapplication.encapsulation.DimensionEncapsulation;
import com.example.iqcapplication.encapsulation.InspectionEncapsulation;
import com.example.iqcapplication.encapsulation.LotEncapsulation;

import java.util.ArrayList;

public class FragmentForDimension extends Fragment {

    DatabaseHelper myDB;
    CustomAdapterDimension customAdapterDimension;
    ArrayList<DimensionEncapsulation> dimesiondata = new ArrayList<>();
    RecyclerView recyclerView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_for_dimension, container, false);

        recyclerView = view.findViewById(R.id.dimensionrecyclerview);



        myDB = new DatabaseHelper(getContext());

        customAdapterDimension = new CustomAdapterDimension(getActivity(),getContext(), dimesiondata);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(customAdapterDimension);
        displayData();


        return view;
    }

    void displayData() {

        Cursor cursor = myDB.readdcAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(),"no data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {

                dimesiondata.add(new DimensionEncapsulation
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
                                cursor.getString(23)


                        ));

            }
            Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
        }
    }
}