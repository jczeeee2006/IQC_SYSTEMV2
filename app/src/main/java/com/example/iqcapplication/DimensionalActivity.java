package com.example.iqcapplication;


import static com.example.iqcapplication.MainActivity.connectionClass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iqcapplication.Update.DimensionActivity;
import com.example.iqcapplication.add.InspectionDetailsActivity;
import com.example.iqcapplication.fragments.FragmentForDimension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DimensionalActivity extends AppCompatActivity {
    EditText  dc_checkPoints,upperSpec,lowerSpec,sammpleUnit,dcsampleSize;
    TextView dc_Minimum,dc_Maximum,dc_Average,dc_Judgemen,dateToday, goodscodedim,invoicedimm;
    EditText dc1,dc2,dc3,dc4,dc5,dc6,dc7,dc8,dc9,dc10;

    public static String dcinstrumentholder, dccheckholder,dcupperspecholder,dclowerspecholder,dcsampleunitholder,dcsamplesizeholder,dcminimumholder,dcmaximumholder,dcaverageholder,dcjudgementholder,
            dc1holder,dc2holder,dc3holder,dc4holder,dc5holder,dc6holder,dc7holder,dc8holder,dc9holder,dc10holder;
    AutoCompleteTextView instrumentUsed;
    public ArrayAdapter  dcinstrument_adapter;
    Button addData,uploadtosqlite,nextFormdim,deleteDimension ;
    ImageButton helpbuttton;
    float num1 = 0;
    float num2 = 0;
    float num3 = 0;
    float num4 = 0;
    float num5 = 0;
    float num6 = 0;
    float num7 = 0;
    float num8 = 0;
    float num9 = 0;
    float num10 = 0;



    public static int ctr = 1, samplesize_id_hldr=0, dimcheck_id_hldr = 0, sampleSizeDC = 0;
    public  static String judgeHolder = "PASSED", colorHolder = "#58f40b", goodsdcholder,invoicedcholder;
    public static boolean fourinstrument = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimensional);



        sammpleUnit = findViewById(R.id.sampleUnit);
        addData = findViewById(R.id.viewdadtfun);

        //-----SAMPLE VALUES---
        dc1 = findViewById(R.id.fc1);
        dc2 = findViewById(R.id.fc2);
        dc3 = findViewById(R.id.fc3);
        dc4 = findViewById(R.id.fc4);
        dc5 = findViewById(R.id.fc5);
        dc6 = findViewById(R.id.fc6);
        dc7 = findViewById(R.id.fc7);
        dc8 = findViewById(R.id.fc8);
        dc9 = findViewById(R.id.fc9);
        dc10 = findViewById(R.id.fc10);
        instrumentUsed = findViewById(R.id.instrumentUsedfc);

        //-----MIN MAX AVERAGE
        dc_Minimum = findViewById(R.id.minimumfc);
        dc_Maximum = findViewById(R.id.maximumfc);
        dc_Average = findViewById(R.id.averagefc);
        dc_Judgemen = findViewById(R.id.fcjudgeMent);
        //--- upper and lower specs
        upperSpec = findViewById(R.id.upperspecsfc);
        lowerSpec = findViewById(R.id.lowerspecfc);
        dc_checkPoints = findViewById(R.id.checkPointfc);
        dcsampleSize = findViewById(R.id.sampleSizefc_);

        addData = findViewById(R.id.viewdadtfun);
        uploadtosqlite = findViewById(R.id.updateTosqlite);
        nextFormdim = findViewById(R.id.nextFormfc);

        sammpleUnit.setText("Mm");

        sammpleUnit.setEnabled(false);

        goodscodedim = findViewById(R.id.goodsdch);
        invoicedimm = findViewById(R.id.invoicedimm);
        deleteDimension = findViewById(R.id.deleteAllRecordsdim);

        invoicedcholder = invoicedimm.getText().toString();
        goodsdcholder = goodscodedim.getText().toString();


        dcInstrument();
        sampleComputation();
        upperSpec();
        disableTexts();
        samplenumberenabled();
        buttonss();
        dccheckholder = dc_checkPoints.getText().toString();
        dcupperspecholder = upperSpec.getText().toString();
        dclowerspecholder = lowerSpec.getText().toString();
        dcsampleunitholder =   sammpleUnit.getText().toString();
        dcsamplesizeholder =   dcsampleSize.getText().toString();
        dcminimumholder  = dc_Minimum.getText().toString();
        dcmaximumholder  = dc_Maximum.getText().toString();
        dcaverageholder  = dc_Average.getText().toString();
        dcjudgementholder = dc_Judgemen.getText().toString();
        helpbuttton = findViewById(R.id.helpButton1);
        dc1holder = dc1.getText().toString();
        dc2holder = dc2.getText().toString();
        dc3holder = dc3.getText().toString();
        dc4holder = dc4.getText().toString();
        dc5holder = dc5.getText().toString();
        dc6holder = dc6.getText().toString();
        dc7holder = dc7.getText().toString();
        dc8holder = dc8.getText().toString();
        dc9holder = dc9.getText().toString();
        dc10holder = dc10.getText().toString();
        dateToday = findViewById(R.id.dateTodaydim);
        dcinstrumentholder  = instrumentUsed.getText().toString();
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String noww = df.format(new Date());
        dateToday.setText(noww);
        instrumentUsed.setText(DimensionActivity.instrumenholder);
        dc_checkPoints.setText(DimensionActivity.dccheckpointsholder);
        dcsampleSize.setText(DimensionActivity.dcsamplesizeHolder);
        dc_Judgemen.setEnabled(false);


        helpbuttton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCustomDalog();
            }
        });


        nextFormdim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog3();
            }
        });

        deleteDimension.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog10();
            }
        });

    }


    public void myCustomDalog(){
        final Dialog MyDialog = new Dialog(DimensionalActivity.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.customdimdialog);

        MyDialog.show();
    }
    public void buttonss(){
        uploadtosqlite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentForDimension());
            }
        });

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog4();
            }
        });
    }


    private void replaceFragment(FragmentForDimension dcfragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.dcframelayout,dcfragment);
        fragmentTransaction.commit();

    }

    void confirmDialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Proceed to" + " Next Form " + "?");
        builder.setMessage("Are you sure you want to proceed?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(DimensionalActivity.this, VisualInspection.class);
                insert_sampleSize();
                startActivity(intent);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


    void confirmDialog4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload " + "Data " + "?");
        builder.setMessage("Are you sure you want to Upload?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(dcsampleSize.getText().toString().equals("") || lowerSpec.getText().toString().equals("") || upperSpec.getText().toString().equals("") ){
                    dcsampleSize.setError("Enter Sample size, this occurs due to tablet loosing to its keyboard");
                    lowerSpec.setError("Enter lower spec, the field is blank");
                }else if(!dcsampleSize.getText().toString().equals("")){
                    addDatatoSQLite();
                   // insert_dimcheck();
                }

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public void addDatatoSQLite(){
        try{
            DatabaseHelper myDB = new DatabaseHelper(DimensionalActivity.this);
            myDB.addDC(

                    instrumentUsed.getText().toString().trim(),
                    dcsampleSize.getText().toString().trim(),
                    dc_checkPoints.getText().toString().trim(),
                    sammpleUnit.getText().toString().trim(),
                    dc1.getText().toString().trim(),
                    dc2.getText().toString().trim(),
                    dc3.getText().toString().trim(),
                    dc4.getText().toString().trim(),
                    dc5.getText().toString().trim(),
                    dc6.getText().toString().trim(),
                    dc7.getText().toString().trim(),
                    dc8.getText().toString().trim(),
                    dc9.getText().toString().trim(),
                    dc10.getText().toString().trim(),
                    lowerSpec.getText().toString().trim(),
                    upperSpec.getText().toString().trim(),
                    dc_Minimum.getText().toString().trim(),
                    dc_Average.getText().toString().trim(),
                    dc_Maximum.getText().toString().trim(),
                    dc_Judgemen.getText().toString().trim(),
                    dateToday.getText().toString().trim()
            );
         //   insert_dimcheck();


        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
    public void disableTexts() {
        dc2.setEnabled(false);
        dc3.setEnabled(false);
        dc4.setEnabled(false);
        dc5.setEnabled(false);
        dc6.setEnabled(false);
        dc7.setEnabled(false);
        dc8.setEnabled(false);
        dc9.setEnabled(false);
        dc10.setEnabled(false);

    }

    public void samplenumberenabled(){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(dcsampleSize.getText().toString().equals("2")){
                    dc2.setEnabled(true);
                }else if(dcsampleSize.getText().toString().equals("")){
                    dc2.setEnabled(false);
                    dc3.setEnabled(false);
                    dc4.setEnabled(false);
                    dc5.setEnabled(false);
                    dc6.setEnabled(false);
                    dc7.setEnabled(false);
                    dc8.setEnabled(false);
                    dc9.setEnabled(false);
                    dc10.setEnabled(false);
                }

                if(dcsampleSize.getText().toString().equals("3")){
                    dc2.setEnabled(true);
                    dc3.setEnabled(true);
                }else if(dcsampleSize.getText().toString().equals("")){
                    dc2.setEnabled(false);
                    dc3.setEnabled(false);
                    dc4.setEnabled(false);
                    dc5.setEnabled(false);
                    dc6.setEnabled(false);
                    dc7.setEnabled(false);
                    dc8.setEnabled(false);
                    dc9.setEnabled(false);
                    dc10.setEnabled(false);
                }
                if(dcsampleSize.getText().toString().equals("4")){
                    dc2.setEnabled(true);
                    dc3.setEnabled(true);
                    dc4.setEnabled(true);
                }else if(dcsampleSize.getText().toString().equals("")){
                    dc2.setEnabled(false);
                    dc3.setEnabled(false);
                    dc4.setEnabled(false);
                    dc5.setEnabled(false);
                    dc6.setEnabled(false);
                    dc7.setEnabled(false);
                    dc8.setEnabled(false);
                    dc9.setEnabled(false);
                    dc10.setEnabled(false);
                }

                if(dcsampleSize.getText().toString().equals("5")){
                    dc2.setEnabled(true);
                    dc3.setEnabled(true);
                    dc4.setEnabled(true);
                    dc5.setEnabled(true);
                }else if(dcsampleSize.getText().toString().equals("")){
                    dc2.setEnabled(false);
                    dc3.setEnabled(false);
                    dc4.setEnabled(false);
                    dc5.setEnabled(false);
                    dc6.setEnabled(false);
                    dc7.setEnabled(false);
                    dc8.setEnabled(false);
                    dc9.setEnabled(false);
                    dc10.setEnabled(false);
                }


                if(dcsampleSize.getText().toString().equals("6")){
                    dc2.setEnabled(true);
                    dc3.setEnabled(true);
                    dc4.setEnabled(true);
                    dc5.setEnabled(true);
                    dc6.setEnabled(true);
                }else if(dcsampleSize.getText().toString().equals("")){
                    dc2.setEnabled(false);
                    dc3.setEnabled(false);
                    dc4.setEnabled(false);
                    dc5.setEnabled(false);
                    dc6.setEnabled(false);
                    dc7.setEnabled(false);
                    dc8.setEnabled(false);
                    dc9.setEnabled(false);
                    dc10.setEnabled(false);
                }
                if(dcsampleSize.getText().toString().equals("7")) {
                    dc2.setEnabled(true);
                    dc3.setEnabled(true);
                    dc4.setEnabled(true);
                    dc5.setEnabled(true);
                    dc6.setEnabled(true);
                    dc7.setEnabled(true);

                }else if(dcsampleSize.getText().toString().equals("")){
                    dc2.setEnabled(false);
                    dc3.setEnabled(false);
                    dc4.setEnabled(false);
                    dc5.setEnabled(false);
                    dc6.setEnabled(false);
                    dc7.setEnabled(false);
                    dc8.setEnabled(false);
                    dc9.setEnabled(false);
                    dc10.setEnabled(false);
                }

                if(dcsampleSize.getText().toString().equals("8")) {
                    dc2.setEnabled(true);
                    dc3.setEnabled(true);
                    dc4.setEnabled(true);
                    dc5.setEnabled(true);
                    dc6.setEnabled(true);
                    dc7.setEnabled(true);
                    dc8.setEnabled(true);
                }else if(dcsampleSize.getText().toString().equals("")){
                    dc2.setEnabled(false);
                    dc3.setEnabled(false);
                    dc4.setEnabled(false);
                    dc5.setEnabled(false);
                    dc6.setEnabled(false);
                    dc7.setEnabled(false);
                    dc8.setEnabled(false);
                    dc9.setEnabled(false);
                    dc10.setEnabled(false);
                }
                if(dcsampleSize.getText().toString().equals("9")) {
                    dc2.setEnabled(true);
                    dc3.setEnabled(true);
                    dc4.setEnabled(true);
                    dc5.setEnabled(true);
                    dc6.setEnabled(true);
                    dc7.setEnabled(true);
                    dc8.setEnabled(true);
                    dc9.setEnabled(true);
                }else if(dcsampleSize.getText().toString().equals("")){
                    dc2.setEnabled(false);
                    dc3.setEnabled(false);
                    dc4.setEnabled(false);
                    dc5.setEnabled(false);
                    dc6.setEnabled(false);
                    dc7.setEnabled(false);
                    dc8.setEnabled(false);
                    dc9.setEnabled(false);
                    dc10.setEnabled(false);
                }
                if(dcsampleSize.getText().toString().equals("10")) {
                    dc2.setEnabled(true);
                    dc3.setEnabled(true);
                    dc4.setEnabled(true);
                    dc5.setEnabled(true);
                    dc6.setEnabled(true);
                    dc7.setEnabled(true);
                    dc8.setEnabled(true);
                    dc9.setEnabled(true);
                    dc10.setEnabled(true);
                }else if(dcsampleSize.getText().toString().equals("")){
                    dc2.setEnabled(false);
                    dc3.setEnabled(false);
                    dc4.setEnabled(false);
                    dc5.setEnabled(false);
                    dc6.setEnabled(false);
                    dc7.setEnabled(false);
                    dc8.setEnabled(false);
                    dc9.setEnabled(false);
                    dc10.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        dcsampleSize.addTextChangedListener(textWatcher);
        dc1.addTextChangedListener(textWatcher);
        dc2 .addTextChangedListener(textWatcher);
        dc3.addTextChangedListener(textWatcher);
        dc4.addTextChangedListener(textWatcher);
        dc5.addTextChangedListener(textWatcher);
        dc6.addTextChangedListener(textWatcher);
        dc7.addTextChangedListener(textWatcher);
        dc8.addTextChangedListener(textWatcher);
        dc9.addTextChangedListener(textWatcher);
        dc10.addTextChangedListener(textWatcher);
    }



    public void dcInstrument(){
        AutoCompleteTextView instrumentUsed =  findViewById(R.id.instrumentUsedfc);


        dcinstrument_adapter = ArrayAdapter.createFromResource(this, R.array.instrument_array, android.R.layout.simple_dropdown_item_1line);
        dcinstrument_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        instrumentUsed.setAdapter(dcinstrument_adapter);

        instrumentUsed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                instrumentUsed.showDropDown();

            }
        });

    }

    public void upperSpec(){
        upperSpec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                judgeHolder = "PASSED";
                colorHolder = "#58f40b";

                if(upperSpec.getText().toString().equals("")){
                    judgeHolder = "PASSED";
                    colorHolder = "#58f40b";

                    dc_Judgemen.setText("");
                    dc1.setTextColor(Color.parseColor("#000000"));
                    dc2.setTextColor(Color.parseColor("#000000"));
                    dc3.setTextColor(Color.parseColor("#000000"));
                    dc4.setTextColor(Color.parseColor("#000000"));
                    dc5.setTextColor(Color.parseColor("#000000"));
                    dc6.setTextColor(Color.parseColor("#000000"));
                    dc7.setTextColor(Color.parseColor("#000000"));
                    dc8.setTextColor(Color.parseColor("#000000"));
                    dc9.setTextColor(Color.parseColor("#000000"));
                    dc10.setTextColor(Color.parseColor("#000000"));
                }
                else{

                    float Lspec = 0;
                    float Uspec = 0;

                    try{
                        Lspec = Float.parseFloat(lowerSpec.getText().toString());
                        Uspec = Float.parseFloat(upperSpec.getText().toString());
                    }
                    catch (Exception ex){
                        Toast.makeText(DimensionalActivity.this, "Input Valid Value", Toast.LENGTH_SHORT).show();
                    }

                    if (!dc1.getText().toString().equals(""))
                    {
                        try {
                            num1 = Float.parseFloat(dc1.getText().toString());
                        }
                        catch (Exception ex)
                        {

                            Toast.makeText(getApplicationContext(), ex.toString()  , Toast.LENGTH_LONG).show();

                        }


                        if (num1 < Lspec || num1 > Uspec)
                        {

                            judgeHolder = "FAILED";
                            colorHolder = "#FF0000";
                            dc_Judgemen.setText(judgeHolder);
                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                            dc1.setTextColor(Color.parseColor("#FF0000"));

                        }else{
                            dc_Judgemen.setText(judgeHolder);

                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                            dc1.setTextColor(Color.parseColor("#58f40b"));
                        }
                    }

                    if (!dc2.getText().toString().equals("")) {

                        try {
                            num2 = Float.parseFloat(dc2.getText().toString());
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }

                        if (num2 < Lspec || num2 > Uspec) {
                            judgeHolder = "FAILED";

                            colorHolder = "#FF0000";
                            dc_Judgemen.setText(judgeHolder);

                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                            dc2.setTextColor(Color.parseColor("#FF0000"));
                        } else {
                            dc_Judgemen.setText(judgeHolder);

                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                            dc2.setTextColor(Color.parseColor("#58f40b"));
                        }

                    }

                    if (!dc3.getText().toString().equals("")) {
                        try {
                            num3 = Float.parseFloat(dc3.getText().toString());
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }



                        if (num3 < Lspec || num3 > Uspec)
                        {
                            judgeHolder = "FAILED";

                            colorHolder = "#FF0000";
                            dc_Judgemen.setText(judgeHolder);

                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                            dc3.setTextColor(Color.parseColor("#FF0000"));
                        }else{
                            dc_Judgemen.setText(judgeHolder);
                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                            dc3.setTextColor(Color.parseColor("#58f40b"));
                        }

                    }

                    if (!dc4.getText().toString().equals("")) {
                        try{
                            num4 = Float.parseFloat(dc4.getText().toString());
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }


                        if (num4 < Lspec || num4 > Uspec)
                        {
                            judgeHolder = "FAILED";

                            colorHolder = "#FF0000";
                            dc_Judgemen.setText(judgeHolder);

                            dc4.setTextColor(Color.parseColor("#FF0000"));
                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                        }else{
                            dc_Judgemen.setText(judgeHolder);
                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                            dc4.setTextColor(Color.parseColor("#58f40b"));
                        }
                    }

                    if (!dc5.getText().toString().equals("")) {
                        try{
                            num5 = Float.parseFloat(dc5.getText().toString());
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }


                        if (num5 < Lspec || num5 > Uspec) {
                            judgeHolder = "FAILED";

                            colorHolder = "#FF0000";
                            dc_Judgemen.setText(judgeHolder);

                            dc5.setTextColor(Color.parseColor(colorHolder));
                            dc_Judgemen.setTextColor(Color.parseColor("#FF0000"));
                        }else{
                            dc_Judgemen.setText(judgeHolder);
                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                            dc5.setTextColor(Color.parseColor("#58f40b"));

                        }

                    }

                    if (!dc6.getText().toString().equals("")) {

                        try{
                            num6 = Float.parseFloat(dc6.getText().toString());
                        }catch (Exception e ){
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }


                        if (num6 < Lspec || num6 > Uspec) {
                            judgeHolder = "FAILED";

                            colorHolder = "#FF0000";
                            dc_Judgemen.setText(judgeHolder);
                            dc6.setTextColor(Color.parseColor(colorHolder));
                            dc_Judgemen.setTextColor(Color.parseColor("#FF0000"));
                        }else{
                            dc_Judgemen.setText(judgeHolder);
                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                            dc6.setTextColor(Color.parseColor("#58f40b"));
                        }
                    }

                    if (!dc7.getText().toString().equals(""))
                    {
                        try{
                            num7 = Float.parseFloat(dc7.getText().toString());
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }


                        if (num7 < Lspec || num7 > Uspec) {
                            judgeHolder = "FAILED";

                            colorHolder = "#FF0000";
                            dc_Judgemen.setText(judgeHolder);
                            dc7.setTextColor(Color.parseColor("#FF0000"));
                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                        }else{
                            dc_Judgemen.setText(judgeHolder);
                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                            dc7.setTextColor(Color.parseColor("#58f40b"));
                        }
                    }

                    if (!dc8.getText().toString().equals("")) {

                        try{
                            num8 = Float.parseFloat(dc8.getText().toString());
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }


                        if (num8 < Lspec || num8 > Uspec) {
                            judgeHolder = "FAILED";

                            colorHolder = "#FF0000";
                            dc_Judgemen.setText(judgeHolder);
                            dc8.setTextColor(Color.parseColor("#FF0000"));
                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                        }else{
                            dc_Judgemen.setText(judgeHolder);
                            dc8.setTextColor(Color.parseColor("#58f40b"));
                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                        }
                    }

                    if (!dc9.getText().toString().equals("")) {

                        try{
                            num9 = Float.parseFloat(dc9.getText().toString());
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }


                        if (num9 < Lspec || num9 > Uspec) {
                            judgeHolder = "FAILED";

                            colorHolder = "#FF0000";
                            dc_Judgemen.setText(judgeHolder);
                            dc9.setTextColor(Color.parseColor("#FF0000"));
                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                        }else{
                            dc_Judgemen.setText(judgeHolder);
                            dc9.setTextColor(Color.parseColor("#58f40b"));
                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                        }
                    }

                    if (!dc10.getText().toString().equals("")) {

                        try{
                            num10 = Float.parseFloat(dc10.getText().toString());
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        }


                        if (num10 < Lspec || num10 > Uspec) {
                            judgeHolder = "FAILED";

                            colorHolder = "#FF0000";
                            dc_Judgemen.setText(judgeHolder);
                            dc10.setTextColor(Color.parseColor("#FF0000"));
                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                        }else{
                            dc_Judgemen.setText(judgeHolder);
                            dc10.setTextColor(Color.parseColor("#58f40b"));
                            dc_Judgemen.setTextColor(Color.parseColor(colorHolder));
                        }
                    }


                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }



    void sampleComputation(){

        TextWatcher textWatcher = new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("WrongConstant")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (fourinstrument == false) {


                    if (!dc1.getText().toString().equals("")) {
                        if(dc1.getText().toString().charAt(0) != '.'){
                            num1 = Float.parseFloat(dc1.getText().toString());
                        }
                        else{
                            dc1.setText("");
                            Toast.makeText(getApplicationContext(), " Please input Digit First",Toast.LENGTH_LONG).show();
                        }

                        List<Float> list = new ArrayList<Float>();

                        list.add(num1);
                        Collections.sort(list);


                        float average = (num1) / list.size();
                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);

                        dc_Minimum.setText(String.valueOf(min));
                        dc_Maximum.setText(String.valueOf(max));
                        dc_Average.setText(String.valueOf(average));


                    } else {
                        dc_Minimum.setText("");
                        dc_Average.setText("");
                        dc_Maximum.setText("");
                        dcsampleSize.setText("");

                    }

                    if (!dc1.getText().toString().equals("") && !dc2.getText().toString().equals("")) {

                        if(dc1.getText().toString().charAt(0) != '.' && dc2.getText().toString().charAt(0) != '.')
                        {
                            num1 = Float.parseFloat(dc1.getText().toString());
                            num2  = Float.parseFloat(dc2.getText().toString());
                        }
                        else{
                            dc2.setText("");
                            Toast.makeText(getApplicationContext(), "Please input Digit First", Toast.LENGTH_LONG).show();
                        }

                        List<Float> list = new ArrayList<Float>();

                        list.add(num1);
                        list.add(num2);
                        Collections.sort(list);


                        float average = (num1 + num2) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        dc_Minimum.setText(String.valueOf(min));
                        dc_Maximum.setText(String.valueOf(max));
                        dc_Average.setText(String.valueOf(average));

                    } else {

                    }

                    if (!dc1.getText().toString().equals("") && !dc2.getText().toString().equals("") && !dc3.getText().toString().equals("")) {
                        if(dc1.getText().toString().charAt(0) != '.' && dc2.getText().toString().charAt(0) != '.' && dc3.getText().toString().charAt(0) != '.')
                        {
                            num1 = Float.parseFloat(dc1.getText().toString());
                            num2 = Float.parseFloat(dc2.getText().toString());
                            num3 = Float.parseFloat(dc3.getText().toString());

                        }
                        else{
                            dc3.setText("");
                            Toast.makeText(getApplicationContext(), "Please input Digit First", Toast.LENGTH_LONG).show();
                        }

                        List<Float> list = new ArrayList<Float>();

                        list.add(num1);
                        list.add(num2);
                        list.add(num3);
                        Collections.sort(list);

                        float average = (num1 + num2 + num3) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        dc_Minimum.setText(String.valueOf(min));
                        dc_Maximum.setText(String.valueOf(max));
                        dc_Average.setText(String.valueOf(average));


                    } else {

                    }

                    if (!dc1.getText().toString().equals("") && !dc2.getText().toString().equals("") && !dc3.getText().toString().equals("") && !dc4.getText().toString().equals("")) {
                        if(dc1.getText().toString().charAt(0) != '.' && dc2.getText().toString().charAt(0) != '.' && dc3.getText().toString().charAt(0) != '.' && dc4.getText().toString().charAt(0) != '.')
                        {
                            num1 = Float.parseFloat(dc1.getText().toString());
                            num2 = Float.parseFloat(dc2.getText().toString());
                            num3 = Float.parseFloat(dc3.getText().toString());
                            num4 = Float.parseFloat(dc4.getText().toString());

                        }
                        else{
                            dc4.setText("");
                            Toast.makeText(getApplicationContext(), "Please input Digit First", Toast.LENGTH_LONG).show();
                        }

                        List<Float> list = new ArrayList<Float>();

                        list.add(num1);
                        list.add(num2);
                        list.add(num3);
                        list.add(num4);
                        Collections.sort(list);

                        float average = (num1 + num2 + num3 + num4) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        dc_Minimum.setText(String.valueOf(min));
                        dc_Maximum.setText(String.valueOf(max));
                        dc_Average.setText(String.valueOf(average));

                    } else {

                    }

                    if (!dc1.getText().toString().equals("") && !dc2.getText().toString().equals("") && !dc3.getText().toString().equals("") && !dc4.getText().toString().equals("") && !dc5.getText().toString().equals("")) {

                        if(dc1.getText().toString().charAt(0) != '.' && dc2.getText().toString().charAt(0) != '.' && dc3.getText().toString().charAt(0) != '.' && dc4.getText().toString().charAt(0) != '.'&& dc5.getText().toString().charAt(0) != '.')
                        {
                            num1 = Float.parseFloat(dc1.getText().toString());
                            num2 = Float.parseFloat(dc2.getText().toString());
                            num3 = Float.parseFloat(dc3.getText().toString());
                            num4 = Float.parseFloat(dc4.getText().toString());
                            num5 = Float.parseFloat(dc5.getText().toString());
                        }
                        else{
                            dc5.setText("");
                            Toast.makeText(getApplicationContext(), "Please input Digit First", Toast.LENGTH_LONG).show();
                        }

                        List<Float> list = new ArrayList<Float>();

                        list.add(num1);
                        list.add(num2);
                        list.add(num3);
                        list.add(num4);
                        list.add(num5);
                        Collections.sort(list);

                        float average = (num1 + num2 + num3 + num4 + num5) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        dc_Minimum.setText(String.valueOf(min));
                        dc_Maximum.setText(String.valueOf(max));
                        dc_Average.setText(String.valueOf(average));

                    } else {

                    }

                    if (!dc1.getText().toString().equals("") && !dc2.getText().toString().equals("") && !dc3.getText().toString().equals("") && !dc4.getText().toString().equals("") && !dc5.getText().toString().equals("") && !dc6.getText().toString().equals("")) {
                        if(dc1.getText().toString().charAt(0) != '.' && dc2.getText().toString().charAt(0) != '.' && dc3.getText().toString().charAt(0) != '.' && dc4.getText().toString().charAt(0) != '.'&& dc5.getText().toString().charAt(0) != '.'&& dc6.getText().toString().charAt(0) != '.')
                        {
                            num1 = Float.parseFloat(dc1.getText().toString());
                            num2 = Float.parseFloat(dc2.getText().toString());
                            num3 = Float.parseFloat(dc3.getText().toString());
                            num4 = Float.parseFloat(dc4.getText().toString());
                            num5 = Float.parseFloat(dc5.getText().toString());
                            num6 = Float.parseFloat(dc6.getText().toString());
                        }
                        else{
                            dc6.setText("");
                            Toast.makeText(getApplicationContext(), "Please input Digit First", Toast.LENGTH_LONG).show();
                        }

                        List<Float> list = new ArrayList<Float>();

                        list.add(num1);
                        list.add(num2);
                        list.add(num3);
                        list.add(num4);
                        list.add(num5);
                        list.add(num6);
                        Collections.sort(list);

                        float average = (num1 + num2 + num3 + num4 + num5 + num6) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        dc_Minimum.setText(String.valueOf(min));
                        dc_Maximum.setText(String.valueOf(max));
                        dc_Average.setText(String.valueOf(average));

                    } else {

                    }

                    if (!dc1.getText().toString().equals("") && !dc2.getText().toString().equals("") && !dc3.getText().toString().equals("") && !dc4.getText().toString().equals("") && !dc5.getText().toString().equals("") && !dc6.getText().toString().equals("") && !dc7.getText().toString().equals("")) {
                        if(dc1.getText().toString().charAt(0) != '.' && dc2.getText().toString().charAt(0) != '.' && dc3.getText().toString().charAt(0) != '.' && dc4.getText().toString().charAt(0) != '.'&& dc5.getText().toString().charAt(0) != '.'&& dc6.getText().toString().charAt(0) != '.'&& dc7.getText().toString().charAt(0) != '.' )
                        {
                            num1 = Float.parseFloat(dc1.getText().toString());
                            num2 = Float.parseFloat(dc2.getText().toString());
                            num3 = Float.parseFloat(dc3.getText().toString());
                            num4 = Float.parseFloat(dc4.getText().toString());
                            num5 = Float.parseFloat(dc5.getText().toString());
                            num6 = Float.parseFloat(dc6.getText().toString());
                            num7 = Float.parseFloat(dc7.getText().toString());
                        }
                        else{
                            dc7.setText("");
                            Toast.makeText(getApplicationContext(), "Please input Digit First", Toast.LENGTH_LONG).show();
                        }


                        List<Float> list = new ArrayList<Float>();

                        list.add(num1);
                        list.add(num2);
                        list.add(num3);
                        list.add(num4);
                        list.add(num5);
                        list.add(num6);
                        list.add(num7);
                        Collections.sort(list);

                        float average = (num1 + num2 + num3 + num4 + num5 + num6 + num7) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        dc_Minimum.setText(String.valueOf(min));
                        dc_Maximum.setText(String.valueOf(max));
                        dc_Average.setText(String.valueOf(average));

                    } else {

                    }


                    if (!dc1.getText().toString().equals("") && !dc2.getText().toString().equals("") && !dc3.getText().toString().equals("") && !dc4.getText().toString().equals("") && !dc5.getText().toString().equals("") && !dc6.getText().toString().equals("") && !dc7.getText().toString().equals("") && !dc8.getText().toString().equals("")) {

                        if(dc1.getText().toString().charAt(0) != '.' && dc2.getText().toString().charAt(0) != '.' && dc3.getText().toString().charAt(0) != '.' && dc4.getText().toString().charAt(0) != '.'&& dc5.getText().toString().charAt(0) != '.'&& dc6.getText().toString().charAt(0) != '.' && dc7.getText().toString().charAt(0) != '.' && dc8.getText().toString().charAt(0) != '.' )
                        {
                            num1 = Float.parseFloat(dc1.getText().toString());
                            num2 = Float.parseFloat(dc2.getText().toString());
                            num3 = Float.parseFloat(dc3.getText().toString());
                            num4 = Float.parseFloat(dc4.getText().toString());
                            num5 = Float.parseFloat(dc5.getText().toString());
                            num6 = Float.parseFloat(dc6.getText().toString());
                            num7 = Float.parseFloat(dc7.getText().toString());
                            num8 = Float.parseFloat(dc8.getText().toString());
                        }
                        else{
                            dc8.setText("");
                            Toast.makeText(getApplicationContext(), "Please input Digit First", Toast.LENGTH_LONG).show();
                        }



                        List<Float> list = new ArrayList<Float>();

                        list.add(num1);
                        list.add(num2);
                        list.add(num3);
                        list.add(num4);
                        list.add(num5);
                        list.add(num6);
                        list.add(num7);
                        list.add(num8);
                        Collections.sort(list);

                        float average = (num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        dc_Minimum.setText(String.valueOf(min));
                        dc_Maximum.setText(String.valueOf(max));
                        dc_Average.setText(String.valueOf(average));

                    } else {

                    }

                    if (!dc1.getText().toString().equals("") && !dc2.getText().toString().equals("") && !dc3.getText().toString().equals("") && !dc4.getText().toString().equals("") && !dc5.getText().toString().equals("") && !dc6.getText().toString().equals("") && !dc7.getText().toString().equals("") && !dc8.getText().toString().equals("") && !dc9.getText().toString().equals("")) {

                        if(dc1.getText().toString().charAt(0) != '.' && dc2.getText().toString().charAt(0) != '.' && dc3.getText().toString().charAt(0) != '.' && dc4.getText().toString().charAt(0) != '.'&& dc5.getText().toString().charAt(0) != '.'&& dc6.getText().toString().charAt(0) != '.' && dc7.getText().toString().charAt(0) != '.' && dc8.getText().toString().charAt(0) != '.' && dc9.getText().toString().charAt(0) != '.' )
                        {
                            num1 = Float.parseFloat(dc1.getText().toString());
                            num2 = Float.parseFloat(dc2.getText().toString());
                            num3 = Float.parseFloat(dc3.getText().toString());
                            num4 = Float.parseFloat(dc4.getText().toString());
                            num5 = Float.parseFloat(dc5.getText().toString());
                            num6 = Float.parseFloat(dc6.getText().toString());
                            num7 = Float.parseFloat(dc7.getText().toString());
                            num8 = Float.parseFloat(dc8.getText().toString());
                            num9 = Float.parseFloat(dc9.getText().toString());
                        }
                        else{
                            dc9.setText("");
                            Toast.makeText(getApplicationContext(), "Please input Digit First", Toast.LENGTH_LONG).show();
                        }

                        List<Float> list = new ArrayList<Float>();

                        list.add(num1);
                        list.add(num2);
                        list.add(num3);
                        list.add(num4);
                        list.add(num5);
                        list.add(num6);
                        list.add(num7);
                        list.add(num8);
                        list.add(num9);
                        Collections.sort(list);

                        float average = (num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        dc_Minimum.setText(String.valueOf(min));
                        dc_Maximum.setText(String.valueOf(max));
                        dc_Average.setText(String.valueOf(average));

                    } else {

                    }

                    if (!dc1.getText().toString().equals("") && !dc2.getText().toString().equals("") && !dc3.getText().toString().equals("") && !dc4.getText().toString().equals("") && !dc5.getText().toString().equals("") && !dc6.getText().toString().equals("") && !dc7.getText().toString().equals("") && !dc8.getText().toString().equals("") && !dc9.getText().toString().equals("") && !dc10.getText().toString().equals("")) {


                        if(dc1.getText().toString().charAt(0) != '.' && dc2.getText().toString().charAt(0) != '.' && dc3.getText().toString().charAt(0) != '.' && dc4.getText().toString().charAt(0) != '.'&& dc5.getText().toString().charAt(0) != '.'&& dc6.getText().toString().charAt(0) != '.' && dc7.getText().toString().charAt(0) != '.' && dc8.getText().toString().charAt(0) != '.' && dc9.getText().toString().charAt(0) != '.'  && dc10.getText().toString().charAt(0) != '.'  )
                        {
                            num1 = Float.parseFloat(dc1.getText().toString());
                            num2 = Float.parseFloat(dc2.getText().toString());
                            num3 = Float.parseFloat(dc3.getText().toString());
                            num4 = Float.parseFloat(dc4.getText().toString());
                            num5 = Float.parseFloat(dc5.getText().toString());
                            num6 = Float.parseFloat(dc6.getText().toString());
                            num7 = Float.parseFloat(dc7.getText().toString());
                            num8 = Float.parseFloat(dc8.getText().toString());
                            num9 = Float.parseFloat(dc9.getText().toString());
                            num10 =Float.parseFloat(dc10.getText().toString());
                        }
                        else{
                            dc10.setText("");
                            Toast.makeText(getApplicationContext(), "Please input Digit First", Toast.LENGTH_LONG).show();
                        }


                        List<Float> list = new ArrayList<Float>();

                        list.add(num1);
                        list.add(num2);
                        list.add(num3);
                        list.add(num4);
                        list.add(num5);
                        list.add(num6);
                        list.add(num7);
                        list.add(num8);
                        list.add(num9);
                        list.add(num10);
                        Collections.sort(list);

                        float average = (num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        dc_Minimum.setText(String.valueOf(min));
                        dcsampleSize.setText((String.valueOf(list.size())));
                    } else {

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        dc1.addTextChangedListener(textWatcher);
        dc2.addTextChangedListener(textWatcher);
        dc3.addTextChangedListener(textWatcher);
        dc4.addTextChangedListener(textWatcher);
        dc5.addTextChangedListener(textWatcher);
        dc6.addTextChangedListener(textWatcher);
        dc7.addTextChangedListener(textWatcher);
        dc8.addTextChangedListener(textWatcher);
        dc9.addTextChangedListener(textWatcher);
        dc10.addTextChangedListener(textWatcher);

    }


    public void insert_dimcheck()
    {
        String Checkpoints = dc_checkPoints.getText().toString();
        String Instrumentused = instrumentUsed.getText().toString();
        String Sampleunit = sammpleUnit.getText().toString();
        String DC1 = dc1.getText().toString();
        String DC2 = dc2.getText().toString();
        String DC3 = dc3.getText().toString();
        String DC4 = dc4.getText().toString();
        String DC5 = dc5.getText().toString();
        String DC6 = dc6.getText().toString();
        String DC7 = dc7.getText().toString();
        String DC8 = dc8.getText().toString();
        String DC9 = dc9.getText().toString();
        String DC10 = dc10.getText().toString();

        String Min = dc_Minimum.getText().toString();
        String Ave = dc_Average.getText().toString();
        String Max = dc_Maximum.getText().toString();

        String LowerSpec = lowerSpec.getText().toString();
        String UppperSpec = upperSpec.getText().toString();
        String Judgmnt = dc_Judgemen.getText().toString();




        if (Checkpoints.trim().equals("")||Instrumentused.trim().equals(""))//dagdagan mo
        {
            Toast.makeText(getApplicationContext(), "Must input atleast 1 checkpoint!", Toast.LENGTH_LONG).show();
        }

        else {
            try {
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN2();
                String query = "SELECT * FROM DimensionalCheck WHERE  Date =  '"+ dateToday.getText().toString() +"' ";
                PreparedStatement stmtt = con.prepareStatement(query);
                ResultSet rs = stmtt.executeQuery();
                if(rs.next()){

                    //  String time = rs.getString("Time");
                    Toast.makeText(DimensionalActivity.this, "Data already existing in SQL Database", Toast.LENGTH_SHORT).show();
                }
                String query2 = "INSERT INTO DimensionalCheck (invoice_no, goodsCode, checkpoints, instrument_used, sample_unit, sample1, sample2, sample3, sample4, sample5, sample6, sample7, sample8, sample9, sample10, minimum, average, maximum, lower_spec_limit, upper_spec_limit, judgement,MaterialCodeBoxSeqID, Date) values ('"+SapmpleActivityinlot.invoicenumholder+"', '"+ SapmpleActivityinlot.goodscodeholder+"', '"+Checkpoints+"','"+Instrumentused+"','"+Sampleunit+"','"+DC1+"','"+DC2+"','"+DC3+"','"+DC4+"','"+DC5+"','"+DC6+"','"+DC7+"','"+DC8+"','"+DC9+"','"+DC10+"','"+Min+"','"+Ave+"','"+Max+"','"+LowerSpec+"','"+UppperSpec+"','"+Judgmnt+"' ,'"+SapmpleActivityinlot.boxseqholder+"','"+dateToday.getText().toString()+"')"   ;

                Statement stmt = con.createStatement();
                stmt.execute(query2);
                dimcheck_id_hldr = Latest_ID("DimensionalCheck");



                reset_sample();
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    public void insert_sampleSize(){

        try{
            connectionClass = new ConnectionClass();
            Connection con = connectionClass.CONN2();
            String query = "SELECT * FROM SampleSize WHERE  MaterialCodeBoxSeqID =  '"+ InspectionDetailsActivity.boxseqholder +"' and  dimension_sample_size  = '"+dcsampleSize.getText().toString()+"'  ";
            PreparedStatement stmtt = con.prepareStatement(query);
            ResultSet rs = stmtt.executeQuery();
            if(rs.next()){

                //  String time = rs.getString("Time");

            }

            String query1 = "INSERT INTO SampleSize (dimension_sample_size, goodsCode,invoice_number,MaterialCodeBoxSeqID) values ('"+dcsampleSize.getText().toString()+"', '"+InspectionDetailsActivity.goodscodeholder+"', '"+InspectionDetailsActivity.invoicenumholderr+"', '"+SapmpleActivityinlot.boxseqholder+"')";
            Statement stmt = con.createStatement();
            stmt.execute(query1);
            samplesize_id_hldr = Latest_ID("SampleSize");
            dimcheck_id_hldr = Latest_ID("DimensionalCheck");
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public int Latest_ID(String tablename){
        int output = 0;

        connectionClass = new ConnectionClass();

        try {
            Connection con = connectionClass.CONN2();//open ng connection sa connection class
            String query = "SELECT TOP 1 id FROM "+tablename+" ORDER BY id DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                output = id;
            }
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_SHORT);
        }

        return output;
    }


    public void reset_sample() {
        sampleSizeDC = ctr;
        ctr = 1;
        dc1.setText("");
        dc2.setText("");
        dc3.setText("");
        dc4.setText("");
        dc5.setText("");
        dc6.setText("");
        dc7.setText("");
        dc8.setText("");
        dc9.setText("");
        dc10.setText("");


        dc_checkPoints.setText("");
        lowerSpec.setText("");
        upperSpec.setText("");
        dc_Judgemen.setText("");

    }


    void confirmDialog10() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("DELETE " + " " +
                "PREVIOUS RECORDS " + "?");
        builder.setMessage("Are you sure you want to DELETE data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper myDB = new DatabaseHelper(DimensionalActivity.this);
                myDB.deleteallRow1();
                Toast.makeText(DimensionalActivity.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DimensionalActivity.this, DimensionalActivity.class);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }



}