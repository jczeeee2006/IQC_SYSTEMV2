package com.example.iqcapplication.dataview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.iqcapplication.Adapters.CustomAdapterInspection;
import com.example.iqcapplication.Adapters.CustomAdapterLot;
import com.example.iqcapplication.DatabaseHelper;
import com.example.iqcapplication.R;
import com.example.iqcapplication.encapsulation.InspectionEncapsulation;
import com.example.iqcapplication.encapsulation.LotEncapsulation;

import java.util.ArrayList;

public class InspectionDetailsDataView extends AppCompatActivity {
    DatabaseHelper myDB;
    CustomAdapterInspection customAdapterIns;
    ArrayList<InspectionEncapsulation> InspectEncap = new ArrayList<>();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details_data_view);

        recyclerView = findViewById(R.id.recyclerviewinspection);

        myDB = new DatabaseHelper(InspectionDetailsDataView.this);

        displayData();
        customAdapterIns = new CustomAdapterInspection(InspectionDetailsDataView.this, InspectEncap);
        recyclerView.setAdapter(customAdapterIns);
        recyclerView.setLayoutManager(new LinearLayoutManager(InspectionDetailsDataView.this));
    }

    void displayData() {
        Cursor cursor = myDB.readinspectData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
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
                                cursor.getString(25)

                        ));

            }
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
    }
}