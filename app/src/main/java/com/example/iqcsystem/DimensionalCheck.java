package com.example.iqcsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DimensionalCheck extends AppCompatActivity {

    Dialog Successdialog, Faileddialog, AddnewDialog;

    Button DCAdd,DCAddDefects,DCNext,DCBack,ADDSample, REMSample, confirm,buttonview;

    ImageView logout;


    ConnectionClass connectionClass;

    // -----------------------VARIABLES AND HOLDERSS-------------------
    EditText  Dc_Checkpoints, Dc_Instrumentused, Dc_Sampleunit, Dc_1, Dc_2, Dc_3, Dc_4, Dc_5, Dc_6, Dc_7, Dc_8, Dc_9, Dc_10;
    EditText Lowerspec, Upperspec,Dc_Samplesize, Defectqt, DCremarks;
    TextView Minimum, Maximum, Average, Judgement, Dc_label,Dc_Judgement;
    TextView Dc_invoiceno, tv_partnum, tv_partname;
    Spinner Dc_spinInstrument;
    public ArrayAdapter dcinstrument_adapter;

    public static int ctr = 1, samplesize_id_hldr=0, dimcheck_id_hldr = 0, sampleSizeDC = 0;
    public  static  String checkp_hldr = "", judgeHolder = "PASSED", colorHolder = "#58f40b";
    public static boolean fourinstrument = false, overallJudgement = true;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimensional_check);

        confirm = findViewById(R.id.btn_OK);
        Dc_label = findViewById(R.id.dim_number);
        Dc_Samplesize = findViewById(R.id.DCSamplesize);
        connectionClass = new ConnectionClass();


        Dc_Checkpoints = findViewById(R.id.DCCheckpoints);
        Dc_Instrumentused =findViewById(R.id.DCInstrument);
        Dc_spinInstrument = findViewById(R.id.instrumentspin);
        DCremarks = findViewById(R.id.dcremarks);
        Dc_Sampleunit = findViewById(R.id.DCSample);
        Dc_Sampleunit.setEnabled(false);
        ADDSample = findViewById(R.id.btn_addsample);
        REMSample= findViewById(R.id.btn_remsample);
        Dc_1 =findViewById(R.id.DC1);
        Dc_2 =findViewById(R.id.DC2);
        Dc_3 =findViewById(R.id.DC3);
        Dc_4 =findViewById(R.id.DC4);
        Dc_5 =findViewById(R.id.DC5);
        Dc_6 =findViewById(R.id.DC6);
        Dc_7 =findViewById(R.id.DC7);
        Dc_8 =findViewById(R.id.DC8);
        Dc_9 =findViewById(R.id.DC9);
        Dc_10 =findViewById(R.id.DC10);
        Minimum = findViewById(R.id.DCMin);
        Average = findViewById(R.id.DCAve);
        Maximum = findViewById(R.id.DCMax);
        Lowerspec = findViewById(R.id.DCLowerspec);
        Upperspec = findViewById(R.id.DCUpperspec);
        Judgement = findViewById(R.id.DCJudgement);
        DCremarks = findViewById(R.id.dcremarks);
        Minimum.setBackgroundColor(Color.parseColor("#d2d9d9"));
        Average.setBackgroundColor(Color.parseColor("#d2d9d9"));
        Maximum.setBackgroundColor(Color.parseColor("#d2d9d9"));


        Defectqt = findViewById(R.id.dcdefectsenc);

        Dc_invoiceno = findViewById(R.id.textView_invoiceno);
        String APinvoice = getIntent().getStringExtra("keyinvoice_no.");
        Dc_invoiceno.setText(APinvoice);
        Dc_Sampleunit.setText("mm");


        Dc_Judgement = findViewById(R.id.DCJudgement);

        dcinstrument_adapter = ArrayAdapter.createFromResource(this, R.array.instrument_array, android.R.layout.simple_spinner_item);
        dcinstrument_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Dc_spinInstrument.setAdapter(dcinstrument_adapter);
        Dc_spinInstrument.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dc_instrument_adding();
               /* if( Dc_spinInstrument.getSelectedItem().equals("PR-Feeler Gauge")){
                    fourinstrument = true;
                    Toast.makeText(getApplicationContext(), Dc_spinInstrument.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    Dc_1.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_1.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_1.setMaxLines(1);

                    Dc_2.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_2.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_2.setMaxLines(1);

                    Dc_3.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_3.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_3.setMaxLines(1);

                    Dc_4.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_4.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_4.setMaxLines(1);

                    Dc_5.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_5.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_5.setMaxLines(1);

                    Dc_6.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_6.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_6.setMaxLines(1);

                    Dc_7.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_7.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_7.setMaxLines(1);

                    Dc_8.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_8.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_8.setMaxLines(1);

                    Dc_9.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_9.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_9.setMaxLines(1);

                    Dc_10.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_10.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_10.setMaxLines(1);
                }

                else if( Dc_spinInstrument.getSelectedItem().equals("GN-GoNo/Go Jig")){
                    fourinstrument = true;
                    Toast.makeText(getApplicationContext(), Dc_spinInstrument.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    Dc_1.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_1.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    //Dc_1.setMaxLines(1);

                    Dc_2.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_2.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_2.setMaxLines(1);

                    Dc_3.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_3.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_3.setMaxLines(1);

                    Dc_4.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_4.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_4.setMaxLines(1);

                    Dc_5.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_5.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_5.setMaxLines(1);

                    Dc_6.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_6.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_6.setMaxLines(1);

                    Dc_7.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_7.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_7.setMaxLines(1);

                    Dc_8.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_8.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_8.setMaxLines(1);

                    Dc_9.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_9.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_9.setMaxLines(1);

                    Dc_10.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_10.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_10.setMaxLines(1);
                }

                else if( Dc_spinInstrument.getSelectedItem().equals("TG-Thread Gauge")){
                    fourinstrument = true;
                    Toast.makeText(getApplicationContext(), Dc_spinInstrument.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    Dc_1.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_1.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_1.setMaxLines(1);

                    Dc_2.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_2.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_2.setMaxLines(1);

                    Dc_3.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_3.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_3.setMaxLines(1);

                    Dc_4.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_4.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_4.setMaxLines(1);

                    Dc_5.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_5.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_5.setMaxLines(1);

                    Dc_6.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_6.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_6.setMaxLines(1);

                    Dc_7.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_7.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_7.setMaxLines(1);

                    Dc_8.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_8.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_8.setMaxLines(1);

                    Dc_9.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_9.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_9.setMaxLines(1);

                    Dc_10.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_10.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_10.setMaxLines(1);

                } else if( Dc_spinInstrument.getSelectedItem().equals("PG-Pin Gauge")){
                    fourinstrument = true;
                    Toast.makeText(getApplicationContext(), Dc_spinInstrument.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                    Dc_1.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_1.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_1.setMaxLines(1);

                    Dc_2.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_2.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_2.setMaxLines(1);

                    Dc_3.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_3.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_3.setMaxLines(1);

                    Dc_4.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_4.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_4.setMaxLines(1);

                    Dc_5.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_5.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_5.setMaxLines(1);

                    Dc_6.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_6.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_6.setMaxLines(1);

                    Dc_7.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_7.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_7.setMaxLines(1);

                    Dc_8.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_8.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_8.setMaxLines(1);

                    Dc_9.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_9.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_9.setMaxLines(1);

                    Dc_10.setInputType(InputType.TYPE_CLASS_TEXT);
                    Dc_10.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1) });
                    Dc_10.setMaxLines(1);

                }else{
                    fourinstrument = false;
                    Dc_1.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL );
                    Dc_1.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
                    Dc_1.setText("");
                    Dc_2.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    Dc_3.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    Dc_4.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    Dc_5.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    Dc_6.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    Dc_7.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    Dc_8.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    Dc_9.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    Dc_10.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        DCAddDefects = findViewById(R.id.btn_DCAdddefects);
        DCAddDefects.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                DcAdddefects();
            }
        });

        DCNext = findViewById(R.id.btn_dcnext);
        DCNext.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DimensionalCheck.this);
                builder.setMessage("Are you sure you want to proceed to the next form?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Next();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                //creating alert dialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        DCAdd = findViewById(R.id.btn_dcadd);
        DCAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(Dc_Checkpoints.length()==0) {
                    Dc_Checkpoints.setError("Input Checkpoint");
                }
                else if(Lowerspec.length()==0) {
                    Lowerspec.setError("Input Lower Spec");
                }
                else if(Upperspec.length()==0) {
                    Upperspec.setError("Input upper Spec");
                }

                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(DimensionalCheck.this);
                    builder.setMessage("Are you sure you want to proceed?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            insert_dimcheck();

                        }
                    });

                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    //creating alert dialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                }
            }

        });

        //For Removing Samples
        REMSample.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(ctr > 1 ) {
                    ctr--;

                }
                Hidesample();

            }
        });


        //for generating samples
        ADDSample.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                if(ctr < 10) {
                    ctr++;
                }
                showsample();

            }
        });
        logout = findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DimensionalCheck.this);
                builder.setMessage("Are you sure you want go to back?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(DimensionalCheck.this, LotNumber.class);
                        startActivity(intent);

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                //creating alert dialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        tv_partnum = findViewById(R.id.textView_partno);
        String partnum = getIntent().getStringExtra("keypartno");
        tv_partnum.setText(partnum);

        tv_partname = findViewById(R.id.textView_partname);
        String partname = getIntent().getStringExtra("keypartname");
        tv_partname.setText(partname);

        //---------- CHECKPOINT ALPHANUMERIC GENERATING-----------------------
        Dc_Checkpoints.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0) {
                    Dc_label.setText("Checkpoint:");
                    checkp_hldr = Dc_Checkpoints.getText().toString();
                    Dc_label.setText(Dc_label.getText().toString() + " " + checkp_hldr);


                }

                else
                {
                    Dc_label.setText("Checkpoint:");
                }
            }
        });


        Upperspec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                judgeHolder = "PASSED";
                colorHolder = "#58f40b";

                if (Upperspec.getText().toString().equals(""))
                {
                   // Toast.makeText(getApplicationContext(), "Input lower/upper spec value to proceed!", Toast.LENGTH_LONG).show();
                    judgeHolder = "PASSED";

                    colorHolder = "#58f40b";

                    Dc_Judgement.setText("");
                    Dc_1.setTextColor(Color.parseColor("#000000"));
                    Dc_2.setTextColor(Color.parseColor("#000000"));
                    Dc_3.setTextColor(Color.parseColor("#000000"));
                    Dc_4.setTextColor(Color.parseColor("#000000"));
                    Dc_5.setTextColor(Color.parseColor("#000000"));
                    Dc_6.setTextColor(Color.parseColor("#000000"));
                    Dc_7.setTextColor(Color.parseColor("#000000"));
                    Dc_8.setTextColor(Color.parseColor("#000000"));
                    Dc_9.setTextColor(Color.parseColor("#000000"));
                    Dc_10.setTextColor(Color.parseColor("#000000"));


                }
                else
                {
                    float Lspec = 0;
                    float Uspec = 0;
                    try{
                     Lspec = Float.parseFloat(Lowerspec.getText().toString());
                     Uspec = Float.parseFloat(Upperspec.getText().toString());
                    }
                    catch (Exception ex){
                    Toast.makeText(DimensionalCheck.this, "Input Valid Value", Toast.LENGTH_SHORT).show();
                    }




                    //Toast.makeText(getApplicationContext(), String.valueOf(Uspec), Toast.LENGTH_LONG).show();


                    if (Dc_1.getVisibility() == View.VISIBLE)
                    {
                        try {
                            num1 = Float.parseFloat(Dc_1.getText().toString());
                        }
                        catch (Exception ex)
                        {

                            Toast.makeText(getApplicationContext(), "Insert value to sample 1!", Toast.LENGTH_LONG).show();

                        }


                        if (num1 < Lspec || num1 > Uspec)
                        {

                            judgeHolder = "FAILED";
                            colorHolder = "#FF0000";
                            Dc_Judgement.setText(judgeHolder);
                            Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                            Dc_1.setTextColor(Color.parseColor("#FF0000"));

                        }else{
                            Dc_Judgement.setText(judgeHolder);

                            Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                            Dc_1.setTextColor(Color.parseColor("#58f40b"));
                        }
                    }
                    if (Dc_2.getVisibility() == View.VISIBLE)
                    {

                    try{
                        num2 = Float.parseFloat(Dc_2.getText().toString());
                    }catch (Exception e ){
                        Toast.makeText(getApplicationContext(), "Insert value to sample 2!", Toast.LENGTH_LONG).show();
                    }

                    if (num2 < Lspec || num2 > Uspec)
                    {
                        judgeHolder = "FAILED";

                        colorHolder = "#FF0000";
                        Dc_Judgement.setText(judgeHolder);

                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                        Dc_2.setTextColor(Color.parseColor("#FF0000"));
                    }else{
                        Dc_Judgement.setText(judgeHolder);

                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                        Dc_2.setTextColor(Color.parseColor("#58f40b"));
                }
                }if (Dc_3.getVisibility() == View.VISIBLE)
                {
                    try {
                        num3 = Float.parseFloat(Dc_3.getText().toString());
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(), "Insert value to sample 3!", Toast.LENGTH_LONG).show();
                    }



                    if (num3 < Lspec || num3 > Uspec)
                    {
                        judgeHolder = "FAILED";

                        colorHolder = "#FF0000";
                        Dc_Judgement.setText(judgeHolder);

                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                        Dc_3.setTextColor(Color.parseColor("#FF0000"));
                    }else{
                        Dc_Judgement.setText(judgeHolder);
                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                        Dc_3.setTextColor(Color.parseColor("#58f40b"));
                    }

                }if (Dc_4.getVisibility() == View.VISIBLE)
                {
                    try{
                        num4 = Float.parseFloat(Dc_4.getText().toString());
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Insert value to sample 4!", Toast.LENGTH_LONG).show();
                    }


                    if (num4 < Lspec || num4 > Uspec)
                    {
                        judgeHolder = "FAILED";

                        colorHolder = "#FF0000";
                        Dc_Judgement.setText(judgeHolder);

                        Dc_4.setTextColor(Color.parseColor("#FF0000"));
                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                    }else{
                        Dc_Judgement.setText(judgeHolder);
                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                        Dc_4.setTextColor(Color.parseColor("#58f40b"));
                    }
                }if (Dc_5.getVisibility() == View.VISIBLE)
                {
                    try{
                        num5 = Float.parseFloat(Dc_5.getText().toString());
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "Insert value to sample 5!", Toast.LENGTH_LONG).show();
                    }


                    if (num5 < Lspec || num5 > Uspec) {
                        judgeHolder = "FAILED";

                        colorHolder = "#FF0000";
                        Dc_Judgement.setText(judgeHolder);

                        Dc_5.setTextColor(Color.parseColor(colorHolder));
                        Dc_Judgement.setTextColor(Color.parseColor("#FF0000"));
                    }else{
                        Dc_Judgement.setText(judgeHolder);
                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                        Dc_5.setTextColor(Color.parseColor("#58f40b"));

                    }

                }if (Dc_6.getVisibility() == View.VISIBLE)
                {

                    try{
                        num6 = Float.parseFloat(Dc_6.getText().toString());
                    }catch (Exception e ){
                        Toast.makeText(getApplicationContext(), "Insert value to sample 6!", Toast.LENGTH_LONG).show();
                    }


                    if (num6 < Lspec || num6 > Uspec) {
                        judgeHolder = "FAILED";

                        colorHolder = "#FF0000";
                        Dc_Judgement.setText(judgeHolder);
                        Dc_6.setTextColor(Color.parseColor(colorHolder));
                        Dc_Judgement.setTextColor(Color.parseColor("#FF0000"));
                    }else{
                        Dc_Judgement.setText(judgeHolder);
                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                        Dc_6.setTextColor(Color.parseColor("#58f40b"));
                    }
                }if (Dc_7.getVisibility() == View.VISIBLE)
                {
                    try{
                        num7 = Float.parseFloat(Dc_7.getText().toString());
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(), "Insert value to sample 7!", Toast.LENGTH_LONG).show();
                    }


                    if (num7 < Lspec || num7 > Uspec) {
                        judgeHolder = "FAILED";

                        colorHolder = "#FF0000";
                        Dc_Judgement.setText(judgeHolder);
                        Dc_7.setTextColor(Color.parseColor("#FF0000"));
                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                    }else{
                        Dc_Judgement.setText(judgeHolder);
                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                        Dc_7.setTextColor(Color.parseColor("#58f40b"));
                    }
                }if (Dc_8.getVisibility() == View.VISIBLE)
                {
                    try{
                        num8 = Float.parseFloat(Dc_8.getText().toString());
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(), "Insert value to sample 8!", Toast.LENGTH_LONG).show();
                    }


                    if (num8 < Lspec || num8 > Uspec) {
                        judgeHolder = "FAILED";

                        colorHolder = "#FF0000";
                        Dc_Judgement.setText(judgeHolder);
                        Dc_8.setTextColor(Color.parseColor("#FF0000"));
                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                    }else{
                        Dc_Judgement.setText(judgeHolder);
                        Dc_8.setTextColor(Color.parseColor("#58f40b"));
                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                    }
                }if (Dc_9.getVisibility() == View.VISIBLE)
                {

                    try{
                        num9 = Float.parseFloat(Dc_9.getText().toString());
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(), "Insert value to sample 9!", Toast.LENGTH_LONG).show();
                    }

                    if (num9 < Lspec || num9 > Uspec) {
                        judgeHolder = "FAILED";

                        colorHolder = "#FF0000";
                        Dc_Judgement.setText(judgeHolder);
                        Dc_9.setTextColor(Color.parseColor("#FF0000"));
                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                    }else{
                        Dc_Judgement.setText(judgeHolder);
                        Dc_9.setTextColor(Color.parseColor("#58f40b"));
                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                    }
                }if (Dc_10.getVisibility() == View.VISIBLE)
                {

                    try{
                        num10 = Float.parseFloat(Dc_10.getText().toString());
                    }catch(Exception e){
                        Toast.makeText(getApplicationContext(), "Insert value to sample 10!", Toast.LENGTH_LONG).show();
                    }


                    if (num10 < Lspec || num10 > Uspec) {

                        judgeHolder = "FAILED";
                        colorHolder = "#FF0000";
                        Dc_Judgement.setText(judgeHolder);
                        Dc_10.setTextColor(Color.parseColor("#FF0000"));
                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));

                    } else {
                        Dc_Judgement.setText(judgeHolder);
                        Dc_10.setTextColor(Color.parseColor("#58f40b"));
                        Dc_Judgement.setTextColor(Color.parseColor(colorHolder));
                    }
                }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

                }
        });

        //Computation of every sample
        TextWatcher textWatcher = new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("WrongConstant")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (fourinstrument == false) {


                    if (!Dc_1.getText().toString().equals("")) {
                        if(Dc_1.getText().toString().charAt(0) != '.'){
                             num1 = Float.parseFloat(Dc_1.getText().toString());
                        }
                        else{
                            Dc_1.setText("");
                           Toast.makeText(getApplicationContext(), " Please input Digit First",Toast.LENGTH_LONG).show();
                        }

                        List<Float> list = new ArrayList<Float>();

                        list.add(num1);
                        Collections.sort(list);


                        float average = (num1) / list.size();
                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);

                        Minimum.setText(String.valueOf(min));
                        Maximum.setText(String.valueOf(max));
                        Average.setText(String.valueOf(average));
                        Dc_Samplesize.setText((String.valueOf(list.size())));
                        Dc_Samplesize.setEnabled(false);

                    } else {
                        Minimum.setText("");
                        Average.setText("");
                        Maximum.setText("");
                        Dc_Samplesize.setText("");

                    }

                    if (!Dc_1.getText().toString().equals("") && !Dc_2.getText().toString().equals("")) {

                        if(Dc_1.getText().toString().charAt(0) != '.' && Dc_2.getText().toString().charAt(0) != '.')
                        {
                            num1 = Float.parseFloat(Dc_1.getText().toString());
                            num2  = Float.parseFloat(Dc_2.getText().toString());
                        }
                        else{
                            Dc_2.setText("");
                            Toast.makeText(getApplicationContext(), "Please input Digit First", Toast.LENGTH_LONG).show();
                        }

                        List<Float> list = new ArrayList<Float>();

                        list.add(num1);
                        list.add(num2);
                        Collections.sort(list);


                        float average = (num1 + num2) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        Minimum.setText(String.valueOf(min));
                        Maximum.setText(String.valueOf(max));
                        Average.setText(String.valueOf(average));
                        Dc_Samplesize.setText((String.valueOf(list.size())));
                    } else {

                    }

                    if (!Dc_1.getText().toString().equals("") && !Dc_2.getText().toString().equals("") && !Dc_3.getText().toString().equals("")) {
                        if(Dc_1.getText().toString().charAt(0) != '.' && Dc_2.getText().toString().charAt(0) != '.' && Dc_3.getText().toString().charAt(0) != '.')
                        {
                            num1 = Float.parseFloat(Dc_1.getText().toString());
                            num2 = Float.parseFloat(Dc_2.getText().toString());
                            num3 = Float.parseFloat(Dc_3.getText().toString());

                        }
                        else{
                            Dc_3.setText("");
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


                        Minimum.setText(String.valueOf(min));
                        Maximum.setText(String.valueOf(max));
                        Average.setText(String.valueOf(average));
                        Dc_Samplesize.setText((String.valueOf(list.size())));

                    } else {

                    }

                    if (!Dc_1.getText().toString().equals("") && !Dc_2.getText().toString().equals("") && !Dc_3.getText().toString().equals("") && !Dc_4.getText().toString().equals("")) {
                        if(Dc_1.getText().toString().charAt(0) != '.' && Dc_2.getText().toString().charAt(0) != '.' && Dc_3.getText().toString().charAt(0) != '.' && Dc_4.getText().toString().charAt(0) != '.')
                        {
                            num1 = Float.parseFloat(Dc_1.getText().toString());
                            num2 = Float.parseFloat(Dc_2.getText().toString());
                            num3 = Float.parseFloat(Dc_3.getText().toString());
                            num4 = Float.parseFloat(Dc_4.getText().toString());

                        }
                        else{
                            Dc_4.setText("");
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


                        Minimum.setText(String.valueOf(min));
                        Maximum.setText(String.valueOf(max));
                        Average.setText(String.valueOf(average));
                        Dc_Samplesize.setText((String.valueOf(list.size())));
                    } else {

                    }

                    if (!Dc_1.getText().toString().equals("") && !Dc_2.getText().toString().equals("") && !Dc_3.getText().toString().equals("") && !Dc_4.getText().toString().equals("") && !Dc_5.getText().toString().equals("")) {

                        if(Dc_1.getText().toString().charAt(0) != '.' && Dc_2.getText().toString().charAt(0) != '.' && Dc_3.getText().toString().charAt(0) != '.' && Dc_4.getText().toString().charAt(0) != '.'&& Dc_5.getText().toString().charAt(0) != '.')
                        {
                            num1 = Float.parseFloat(Dc_1.getText().toString());
                            num2 = Float.parseFloat(Dc_2.getText().toString());
                            num3 = Float.parseFloat(Dc_3.getText().toString());
                            num4 = Float.parseFloat(Dc_4.getText().toString());
                            num5 = Float.parseFloat(Dc_5.getText().toString());
                        }
                        else{
                            Dc_5.setText("");
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


                        Minimum.setText(String.valueOf(min));
                        Maximum.setText(String.valueOf(max));
                        Average.setText(String.valueOf(average));
                        Dc_Samplesize.setText((String.valueOf(list.size())));
                    } else {

                    }

                    if (!Dc_1.getText().toString().equals("") && !Dc_2.getText().toString().equals("") && !Dc_3.getText().toString().equals("") && !Dc_4.getText().toString().equals("") && !Dc_5.getText().toString().equals("") && !Dc_6.getText().toString().equals("")) {
                        if(Dc_1.getText().toString().charAt(0) != '.' && Dc_2.getText().toString().charAt(0) != '.' && Dc_3.getText().toString().charAt(0) != '.' && Dc_4.getText().toString().charAt(0) != '.'&& Dc_5.getText().toString().charAt(0) != '.'&& Dc_6.getText().toString().charAt(0) != '.')
                        {
                            num1 = Float.parseFloat(Dc_1.getText().toString());
                            num2 = Float.parseFloat(Dc_2.getText().toString());
                            num3 = Float.parseFloat(Dc_3.getText().toString());
                            num4 = Float.parseFloat(Dc_4.getText().toString());
                            num5 = Float.parseFloat(Dc_5.getText().toString());
                            num6 = Float.parseFloat(Dc_6.getText().toString());
                        }
                        else{
                            Dc_6.setText("");
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


                        Minimum.setText(String.valueOf(min));
                        Maximum.setText(String.valueOf(max));
                        Average.setText(String.valueOf(average));
                        Dc_Samplesize.setText((String.valueOf(list.size())));
                    } else {

                    }

                    if (!Dc_1.getText().toString().equals("") && !Dc_2.getText().toString().equals("") && !Dc_3.getText().toString().equals("") && !Dc_4.getText().toString().equals("") && !Dc_5.getText().toString().equals("") && !Dc_6.getText().toString().equals("") && !Dc_7.getText().toString().equals("")) {
                        if(Dc_1.getText().toString().charAt(0) != '.' && Dc_2.getText().toString().charAt(0) != '.' && Dc_3.getText().toString().charAt(0) != '.' && Dc_4.getText().toString().charAt(0) != '.'&& Dc_5.getText().toString().charAt(0) != '.'&& Dc_6.getText().toString().charAt(0) != '.'&& Dc_7.getText().toString().charAt(0) != '.' )
                        {
                            num1 = Float.parseFloat(Dc_1.getText().toString());
                            num2 = Float.parseFloat(Dc_2.getText().toString());
                            num3 = Float.parseFloat(Dc_3.getText().toString());
                            num4 = Float.parseFloat(Dc_4.getText().toString());
                            num5 = Float.parseFloat(Dc_5.getText().toString());
                            num6 = Float.parseFloat(Dc_6.getText().toString());
                            num7 = Float.parseFloat(Dc_7.getText().toString());
                        }
                        else{
                            Dc_7.setText("");
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


                        Minimum.setText(String.valueOf(min));
                        Maximum.setText(String.valueOf(max));
                        Average.setText(String.valueOf(average));
                        Dc_Samplesize.setText((String.valueOf(list.size())));
                    } else {

                    }


                    if (!Dc_1.getText().toString().equals("") && !Dc_2.getText().toString().equals("") && !Dc_3.getText().toString().equals("") && !Dc_4.getText().toString().equals("") && !Dc_5.getText().toString().equals("") && !Dc_6.getText().toString().equals("") && !Dc_7.getText().toString().equals("") && !Dc_8.getText().toString().equals("")) {

                        if(Dc_1.getText().toString().charAt(0) != '.' && Dc_2.getText().toString().charAt(0) != '.' && Dc_3.getText().toString().charAt(0) != '.' && Dc_4.getText().toString().charAt(0) != '.'&& Dc_5.getText().toString().charAt(0) != '.'&& Dc_6.getText().toString().charAt(0) != '.' && Dc_7.getText().toString().charAt(0) != '.' && Dc_8.getText().toString().charAt(0) != '.' )
                        {
                            num1 = Float.parseFloat(Dc_1.getText().toString());
                            num2 = Float.parseFloat(Dc_2.getText().toString());
                            num3 = Float.parseFloat(Dc_3.getText().toString());
                            num4 = Float.parseFloat(Dc_4.getText().toString());
                            num5 = Float.parseFloat(Dc_5.getText().toString());
                            num6 = Float.parseFloat(Dc_6.getText().toString());
                            num7 = Float.parseFloat(Dc_7.getText().toString());
                            num8 = Float.parseFloat(Dc_8.getText().toString());
                        }
                        else{
                            Dc_8.setText("");
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


                        Minimum.setText(String.valueOf(min));
                        Maximum.setText(String.valueOf(max));
                        Average.setText(String.valueOf(average));
                        Dc_Samplesize.setText((String.valueOf(list.size())));
                    } else {

                    }

                    if (!Dc_1.getText().toString().equals("") && !Dc_2.getText().toString().equals("") && !Dc_3.getText().toString().equals("") && !Dc_4.getText().toString().equals("") && !Dc_5.getText().toString().equals("") && !Dc_6.getText().toString().equals("") && !Dc_7.getText().toString().equals("") && !Dc_8.getText().toString().equals("") && !Dc_9.getText().toString().equals("")) {

                        if(Dc_1.getText().toString().charAt(0) != '.' && Dc_2.getText().toString().charAt(0) != '.' && Dc_3.getText().toString().charAt(0) != '.' && Dc_4.getText().toString().charAt(0) != '.'&& Dc_5.getText().toString().charAt(0) != '.'&& Dc_6.getText().toString().charAt(0) != '.' && Dc_7.getText().toString().charAt(0) != '.' && Dc_8.getText().toString().charAt(0) != '.' && Dc_9.getText().toString().charAt(0) != '.' )
                        {
                            num1 = Float.parseFloat(Dc_1.getText().toString());
                            num2 = Float.parseFloat(Dc_2.getText().toString());
                            num3 = Float.parseFloat(Dc_3.getText().toString());
                            num4 = Float.parseFloat(Dc_4.getText().toString());
                            num5 = Float.parseFloat(Dc_5.getText().toString());
                            num6 = Float.parseFloat(Dc_6.getText().toString());
                            num7 = Float.parseFloat(Dc_7.getText().toString());
                            num8 = Float.parseFloat(Dc_8.getText().toString());
                            num9 = Float.parseFloat(Dc_9.getText().toString());
                        }
                        else{
                            Dc_9.setText("");
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


                        Minimum.setText(String.valueOf(min));
                        Maximum.setText(String.valueOf(max));
                        Average.setText(String.valueOf(average));
                        Dc_Samplesize.setText((String.valueOf(list.size())));
                    } else {

                    }

                    if (!Dc_1.getText().toString().equals("") && !Dc_2.getText().toString().equals("") && !Dc_3.getText().toString().equals("") && !Dc_4.getText().toString().equals("") && !Dc_5.getText().toString().equals("") && !Dc_6.getText().toString().equals("") && !Dc_7.getText().toString().equals("") && !Dc_8.getText().toString().equals("") && !Dc_9.getText().toString().equals("") && !Dc_10.getText().toString().equals("")) {


                        if(Dc_1.getText().toString().charAt(0) != '.' && Dc_2.getText().toString().charAt(0) != '.' && Dc_3.getText().toString().charAt(0) != '.' && Dc_4.getText().toString().charAt(0) != '.'&& Dc_5.getText().toString().charAt(0) != '.'&& Dc_6.getText().toString().charAt(0) != '.' && Dc_7.getText().toString().charAt(0) != '.' && Dc_8.getText().toString().charAt(0) != '.' && Dc_9.getText().toString().charAt(0) != '.'  && Dc_10.getText().toString().charAt(0) != '.'  )
                        {
                            num1 = Float.parseFloat(Dc_1.getText().toString());
                            num2 = Float.parseFloat(Dc_2.getText().toString());
                            num3 = Float.parseFloat(Dc_3.getText().toString());
                            num4 = Float.parseFloat(Dc_4.getText().toString());
                            num5 = Float.parseFloat(Dc_5.getText().toString());
                            num6 = Float.parseFloat(Dc_6.getText().toString());
                            num7 = Float.parseFloat(Dc_7.getText().toString());
                            num8 = Float.parseFloat(Dc_8.getText().toString());
                            num9 = Float.parseFloat(Dc_9.getText().toString());
                            num10 =Float.parseFloat(Dc_10.getText().toString());
                        }
                        else{
                            Dc_10.setText("");
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


                        Minimum.setText(String.valueOf(min));
                        Maximum.setText(String.valueOf(max));
                        Average.setText(String.valueOf(average));
                        Dc_Samplesize.setText((String.valueOf(list.size())));
                    } else {

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        Dc_1.addTextChangedListener(textWatcher);
        Dc_2.addTextChangedListener(textWatcher);
        Dc_3.addTextChangedListener(textWatcher);
        Dc_3.addTextChangedListener(textWatcher);
        Dc_4.addTextChangedListener(textWatcher);
        Dc_5.addTextChangedListener(textWatcher);
        Dc_6.addTextChangedListener(textWatcher);
        Dc_7.addTextChangedListener(textWatcher);
        Dc_8.addTextChangedListener(textWatcher);
        Dc_9.addTextChangedListener(textWatcher);
        Dc_10.addTextChangedListener(textWatcher);
    }

    public void reset_sample()
    {
        sampleSizeDC = ctr;
        ctr = 1;
        Dc_1.setText("");
        Dc_2.setText("");
        Dc_3.setText("");
        Dc_4.setText("");
        Dc_5.setText("");
        Dc_6.setText("");
        Dc_7.setText("");
        Dc_8.setText("");
        Dc_9.setText("");
        Dc_10.setText("");
        Dc_2.setVisibility(View.INVISIBLE);
        Dc_3.setVisibility(View.INVISIBLE);
        Dc_4.setVisibility(View.INVISIBLE);
        Dc_5.setVisibility(View.INVISIBLE);
        Dc_6.setVisibility(View.INVISIBLE);
        Dc_7.setVisibility(View.INVISIBLE);
        Dc_8.setVisibility(View.INVISIBLE);
        Dc_9.setVisibility(View.INVISIBLE);
        Dc_10.setVisibility(View.INVISIBLE);
        Dc_Checkpoints.setText("");
        Lowerspec.setText("");
        Upperspec.setText("");
        Judgement.setText("");
        Dc_Judgement.setText("");
    }

    public void Hidesample() {
        if(ctr == 1){
            Dc_2.setVisibility(View.INVISIBLE);
            Dc_2.setText("");
        }
        else if( ctr == 2){
            Dc_3.setVisibility(View.INVISIBLE);
            Dc_3.setText("");
        }

        else if(ctr == 3){
            Dc_4.setVisibility(View.INVISIBLE);
            Dc_4.setText("");
        }

        else if(ctr == 4){
            Dc_5.setVisibility(View.INVISIBLE);
            Dc_5.setText("");
        }

        else if(ctr == 5){
            Dc_6.setVisibility(View.INVISIBLE);
            Dc_6.setText("");
        }

        else if(ctr == 6){
            Dc_7.setVisibility(View.INVISIBLE);
            Dc_7.setText("");
        }

        else if(ctr == 7){
            Dc_8.setVisibility(View.INVISIBLE);
            Dc_8.setText("");
        }

        else if(ctr == 8){
            Dc_9.setVisibility(View.INVISIBLE);
            Dc_9.setText("");
        }
        else if(ctr == 9){
            Dc_10.setVisibility(View.INVISIBLE);
            Dc_10.setText("");
        }



    }

    public void showsample() {
        if( ctr == 2)
        {
            Dc_2.setVisibility(View.VISIBLE);
        }

        else if(ctr == 3)
        {
            Dc_3.setVisibility(View.VISIBLE);
        }

        else if(ctr == 4)
        {
            Dc_4.setVisibility(View.VISIBLE);
        }

        else if(ctr == 5)
        {
            Dc_5.setVisibility(View.VISIBLE);
        }

        else if(ctr == 6)
        {
            Dc_6.setVisibility(View.VISIBLE);
        }

        else if(ctr == 7)
        {
            Dc_7.setVisibility(View.VISIBLE);
        }

        else if(ctr == 8)
        {
            Dc_8.setVisibility(View.VISIBLE);
        }
        else if(ctr == 9)
        {
            Dc_9.setVisibility(View.VISIBLE);
        }

        else if(ctr == 10)
        {
            Dc_10.setVisibility(View.VISIBLE);
        }

    }

    public int Latest_ID(String tablename){
        int output = 0;

        connectionClass = new ConnectionClass();

        try {
            Connection con = connectionClass.CONN();//open ng connection sa connection class
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


        public void insert_sampleSize(){
            String Samplesize = Dc_Samplesize.getText().toString();
            try{
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN();
                String query1 = "INSERT INTO SampleSize (dimension_sample_size, goodsCode,invoice_number,MaterialCodeBoxSeqID) values ('"+sampleSizeDC+"', '"+LotNumber.materialholder+"', '"+LotNumber.invoicenumholder+"', '"+LotNumber.boxseqholder+"')";
                Statement stmt = con.createStatement();
                stmt.execute(query1);
                samplesize_id_hldr = Latest_ID("SampleSize");
                dimcheck_id_hldr = Latest_ID("DimensionalCheck");
            }catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }

        }

        public void insert_dimcheck()
        {
            String Checkpoints = Dc_Checkpoints.getText().toString();
            String Instrumentused = Dc_Instrumentused.getText().toString();
            String Sampleunit = Dc_Sampleunit.getText().toString();
            String DC1 = Dc_1.getText().toString();
            String DC2 = Dc_2.getText().toString();
            String DC3 = Dc_3.getText().toString();
            String DC4 = Dc_4.getText().toString();
            String DC5 = Dc_5.getText().toString();
            String DC6 = Dc_6.getText().toString();
            String DC7 = Dc_7.getText().toString();
            String DC8 = Dc_8.getText().toString();
            String DC9 = Dc_9.getText().toString();
            String DC10 = Dc_10.getText().toString();
            String Min = Minimum.getText().toString();
            String Ave = Average.getText().toString();
            String Max = Maximum.getText().toString();
            String LowerSpec = Lowerspec.getText().toString();
            String UppperSpec = Upperspec.getText().toString();
            String Judgmnt = Dc_Judgement.getText().toString();

            String DCRemarks = DCremarks.getText().toString();


            if (Checkpoints.trim().equals("")||Instrumentused.trim().equals(""))//dagdagan mo
            {
                Toast.makeText(getApplicationContext(), "Must input atleast 1 checkpoint!", Toast.LENGTH_LONG).show();
            }

            else {
            try {
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN();

                String query = "INSERT INTO DimensionalCheck (invoice_no, goodsCode, checkpoints, instrument_used, sample_unit, sample1, sample2, sample3, sample4, sample5, sample6, sample7, sample8, sample9, sample10, minimum, average, maximum, lower_spec_limit, upper_spec_limit, judgement, remarks,MaterialCodeBoxSeqID) values ('"+LotNumber.invoicenumholder+"', '"+LotNumber.materialholder+"', '"+Checkpoints+"','"+Instrumentused+"','"+Sampleunit+"','"+DC1+"','"+DC2+"','"+DC3+"','"+DC4+"','"+DC5+"','"+DC6+"','"+DC7+"','"+DC8+"','"+DC9+"','"+DC10+"','"+Min+"','"+Ave+"','"+Max+"','"+LowerSpec+"','"+UppperSpec+"','"+Judgmnt+"','"+DCRemarks+"','"+LotNumber.boxseqholder+"')"   ;

                Statement stmt = con.createStatement();
                stmt.execute(query);
                dimcheck_id_hldr = Latest_ID("DimensionalCheck");
                AlertSuccess();


                reset_sample();
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
            }
        }
        }


    public void AlertSuccess(){
        Successdialog = new Dialog(DimensionalCheck.this);
        Successdialog.setContentView(R.layout.alertsucces);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Successdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.alertsuccessbackground));
        }

        Successdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Successdialog.setCancelable(false);

        Button ok = Successdialog.findViewById(R.id.btn_confirmlogout);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertAddnew();
                Successdialog.dismiss();
            }
        });

        Successdialog.show();
    }


    public void AlertFailed(){
        Faileddialog = new Dialog(DimensionalCheck.this);
        Faileddialog.setContentView(R.layout.alertfailed);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Faileddialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.alertsuccessbackground));
        }

        Faileddialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Faileddialog.setCancelable(false);

        Button ok = Faileddialog.findViewById(R.id.btn_confirmlogout1);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Faileddialog.dismiss();
            }
        });

        Faileddialog.show();
    }

    public void AlertAddnew(){
        AddnewDialog = new Dialog(DimensionalCheck.this);
        AddnewDialog.setContentView(R.layout.activity_alert_addnew);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
            AddnewDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.alertsuccessbackground));
        }

        AddnewDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        AddnewDialog.setCancelable(false);

        Button addnew = AddnewDialog.findViewById(R.id.btn_alertaddnew);
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dc_1.setTextColor(Color.parseColor("#000000"));
                Dc_2.setTextColor(Color.parseColor("#000000"));
                Dc_3.setTextColor(Color.parseColor("#000000"));
                Dc_4.setTextColor(Color.parseColor("#000000"));
                Dc_5.setTextColor(Color.parseColor("#000000"));
                Dc_6.setTextColor(Color.parseColor("#000000"));
                Dc_7.setTextColor(Color.parseColor("#000000"));
                Dc_8.setTextColor(Color.parseColor("#000000"));
                Dc_9.setTextColor(Color.parseColor("#000000"));
                Dc_10.setTextColor(Color.parseColor("#000000"));

                AddnewDialog.dismiss();
            }
        });



        Button proceednext = AddnewDialog.findViewById(R.id.btn_alertproceed);
        proceednext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Next();
            }
        });

        AddnewDialog.show();
    }






    public void Next(){
        insert_sampleSize();
        Intent intent = new Intent(DimensionalCheck.this, AppearanceInspection.class);
        startActivity(intent);
        reset_sample();
    }

    public void DcAdddefects(){
        String invoice = Dc_invoiceno.getText().toString();
        Intent intent = new Intent(DimensionalCheck.this, DMDefects.class);
        intent.putExtra("keyinvoice_no.", invoice);
        startActivity(intent);
    }

    public void dc_instrument_adding()
    {
        String instru = Dc_spinInstrument.getSelectedItem().toString();
        Dc_Instrumentused.setEnabled(false);
        if (instru.equals("")) {
            Dc_Instrumentused.setText("");
        }

        if (instru.equals("SG-Steel Gauge")) {
            Dc_Instrumentused.setText("SG-Steel Gauge");

        }
        if (instru.equals("LCR-LCR Meter")) {
            Dc_Instrumentused.setText("LCR-LCR Meter");
        }
        if (instru.equals("DM-Digital Multimeter")) {
            Dc_Instrumentused.setText("DM-Digital Multimeter");
        }

        if (instru.equals("PR-Feeler Gauge")) {
            Dc_Instrumentused.setText("PR-Feeler Gauge");

        }
        if (instru.equals("DG-Depth Gauge")) {
            Dc_Instrumentused.setText("DG-Depth Gauge");
        }
        if (instru.equals("HG-Dial Height Gauge")) {
            Dc_Instrumentused.setText("HG-Dial Height Gauge");
        }

        if (instru.equals("TG-Thread Gauge")) {
            Dc_Instrumentused.setText("TG-Thread Gauge");

        }
        if (instru.equals("PG-Pin Gauge")) {
            Dc_Instrumentused.setText("PG-Pin Gauge");
        }
        if (instru.equals("SR-Steel Ruler")) {
            Dc_Instrumentused.setText("SR-Steel Ruler");
        }

        if (instru.equals("2D-Keyence")) {
            Dc_Instrumentused.setText("2D-Keyence");

        }
        if (instru.equals("MM-Micrometer")) {
            Dc_Instrumentused.setText("MM-Micrometer");
        }
        if (instru.equals("DC-Digital Caliper")) {
            Dc_Instrumentused.setText("DC-Digital Caliper");
        }

    }

    @Override
    public void onBackPressed() {
        return;
    }
}
