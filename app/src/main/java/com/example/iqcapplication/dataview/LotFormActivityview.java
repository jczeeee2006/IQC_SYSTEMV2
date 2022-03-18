package com.example.iqcapplication.dataview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.example.iqcapplication.Adapters.CustomAdapterLot;
import com.example.iqcapplication.DatabaseHelper;
import com.example.iqcapplication.R;
import com.example.iqcapplication.encapsulation.LotEncapsulation;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class LotFormActivityview extends AppCompatActivity {
    DatabaseHelper myDB;
    CustomAdapterLot customAdapterLot;
    ArrayList<LotEncapsulation> lotData = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lot_form_activityview);

        // Inflate the layout for this fragment
        recyclerView = findViewById(R.id.recyclerview);

        myDB = new DatabaseHelper(LotFormActivityview.this);

        displayData();
        customAdapterLot = new CustomAdapterLot(LotFormActivityview.this, lotData);
        recyclerView.setAdapter(customAdapterLot);
        recyclerView.setLayoutManager(new LinearLayoutManager(LotFormActivityview.this));
    }

    void displayData() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
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
                                cursor.getString(13)

                        ));

            }
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        }
    }
}