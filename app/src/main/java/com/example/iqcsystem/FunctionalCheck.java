package com.example.iqcsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.text.InputType.*;

public class FunctionalCheck extends AppCompatActivity {

    Dialog Successdialog, Faileddialog, AddnewDialog;

    Button FCAdd, DCAddDefects, FCNext, ADDSample, REMSample,confirm, lotDetails;
    ConnectionClass connectionClass;
    Spinner Dc_spinInstrument;
    ImageView cal2,calapproved;
    EditText FcSampleu,Fc_Checkpoints, Fc_Samplesize, Fc_Sampleunit, Fc_1, Fc_2, Fc_3, Fc_4, Fc_5, Fc_6, Fc_7, Fc_8, Fc_9, Fc_10;
    EditText Lowerspec, Upperspec, Remarkss, checkedDate, Approve, approveBy, checkedBy,FC_Judgement;
    TextView Judgement,  Minimum, Average, Maximum,Fc_label;
    AutoCompleteTextView Fc_Instrumentused;
    public ArrayAdapter fcinstrument_adapter;

    private static final String[] Instrument = new String[]{
            "GN-GoNo/Go Jig","PR-Feeler Gauge","TG-Thread Gauge","PG-Pin Gauge"
    };


    public static int ctr = 1, samplesize_id_hldr=0, fccheck_id_hldr = 0, sampleSizeFC = 0;
    public  static  String checkp_hldr = "", judgeHolder = "PASSED", xoHolder = "O", colorHolder = "#58f40b";
    public static boolean fourinstrument = false;
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

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functional_check);

        connectionClass = new ConnectionClass();

        Remarkss = findViewById(R.id.fcremarks);
        confirm = findViewById(R.id.btn_OK);
        Fc_Samplesize = findViewById(R.id.FCSamplesize);
        Fc_label = findViewById(R.id.dim_number);
        ADDSample = findViewById(R.id.btn_addsample);
        REMSample= findViewById(R.id.btn_remsample);
        Fc_Samplesize = findViewById(R.id.FCSamplesize);
        Fc_Checkpoints = findViewById(R.id.FCCheckpoints);
        Fc_Instrumentused =findViewById(R.id.FCInstrument);
        Fc_Sampleunit = findViewById(R.id.FCSample);
        Fc_1 = findViewById(R.id.FC1);
        Fc_2 = findViewById(R.id.FC2);
        Fc_3 = findViewById(R.id.FC3);
        Fc_4 = findViewById(R.id.FC4);
        Fc_5 = findViewById(R.id.FC5);
        Fc_6 = findViewById(R.id.FC6);
        Fc_7 = findViewById(R.id.FC7);
        Fc_8 = findViewById(R.id.FC8);
        Fc_9 = findViewById(R.id.FC9);
        Fc_10 = findViewById(R.id.FC10);
        Minimum = findViewById(R.id.FCMin);
        Average = findViewById(R.id.FCAve);
        Maximum = findViewById(R.id.FCMax);
        Lowerspec = findViewById(R.id.FCLowerspec);
        Upperspec = findViewById(R.id.FCUpperspec);
        Judgement = findViewById(R.id.FCJudgement);
        Dc_spinInstrument = findViewById(R.id.instrumentspin);
        FcSampleu = findViewById(R.id.FCSample);
        Fc_Checkpoints = findViewById(R.id.FCCheckpoints);
        checkedBy = findViewById(R.id.et_checkedby);
        approveBy = findViewById(R.id.et_approvedby);


        Minimum.setBackgroundColor(Color.parseColor("#d2d9d9"));
        Average.setBackgroundColor(Color.parseColor("#d2d9d9"));
        Maximum.setBackgroundColor(Color.parseColor("#d2d9d9"));
        FcSampleu.setText("mm");

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        EditText date  = findViewById(R.id.et_checkeddate);
        date.setText(date_n);

        String date_now2 = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        EditText dateee1 = findViewById(R.id.et_approveddate);
        dateee1.setText(date_now2);

        checkedDate = findViewById(R.id.et_checkeddate);
        cal2 = findViewById(R.id.checked_datepicker);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Instrument);
        Fc_Instrumentused.setAdapter(adapter);
        // textView is the TextView view that should display it

        cal2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar Cal = Calendar.getInstance();
                final int year = Cal.get(Calendar.YEAR);
                final int month = Cal.get(Calendar.MONTH);
                final int day = Cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(FunctionalCheck.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                        checkedDate.setText((month + 1) + " /" + year + "/ " + day);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });



        Approve = findViewById(R.id.et_approveddate);
        calapproved = findViewById(R.id.approved_datepicker);

        calapproved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar Cal = Calendar.getInstance();
                final int year = Cal.get(Calendar.YEAR);
                final int month = Cal.get(Calendar.MONTH);
                final int day = Cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(FunctionalCheck.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                        Approve.setText((month + 1) + " /" + year + "/ " + day);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });



        Judgement.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 if(Judgement.getText().toString().equals("PASSED") || Judgement.getText().toString().equals("Passed")||Judgement.getText().toString().equals("passed"))
                 {
                        Judgement.setTextColor(Color.parseColor("#58f40b"));
                 }
                 else if(Judgement.getText().toString().equals("FAILED") || Judgement.getText().toString().equals("Failed")||Judgement.getText().toString().equals("failed")){
                     Judgement.setTextColor(Color.parseColor("#FF0000"));
                 }
                 else{
                     Judgement.setTextColor(Color.parseColor("#000000"));
                 }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        fcinstrument_adapter = ArrayAdapter.createFromResource(this, R.array.instrument_array2, android.R.layout.simple_spinner_item);
        fcinstrument_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        FCNext = findViewById(R.id.btn_fcnext);
        FCNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkApprovedby_update();
              Next();
            }
        });



        FCAdd = findViewById(R.id.btn_fcadd);
        FCAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Fc_Checkpoints.length()==0){
                    Fc_Checkpoints.setError("Input Checkpoint");
                }
                if(Fc_Samplesize.length()==0){
                    Fc_Samplesize.setError("Input Sample Size");
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(FunctionalCheck.this);
                builder.setMessage("Are you sure you want to proceed?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        insert_funcheck();

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

        lotDetails = findViewById(R.id.btn_lotDetails);
        {

        }

        Upperspec.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                judgeHolder = "PASSED";
                colorHolder = "#58f40b";

                if(Upperspec.getText().toString().equals(""))
                {

                    judgeHolder = "PASSED";

                    colorHolder = "#58f40b";

                    FC_Judgement.setText("");
                    Fc_1.setTextColor(Color.parseColor("#000000"));
                    Fc_2.setTextColor(Color.parseColor("#000000"));
                    Fc_3.setTextColor(Color.parseColor("#000000"));
                    Fc_4.setTextColor(Color.parseColor("#000000"));
                    Fc_5.setTextColor(Color.parseColor("#000000"));
                    Fc_6.setTextColor(Color.parseColor("#000000"));
                    Fc_7.setTextColor(Color.parseColor("#000000"));
                    Fc_8.setTextColor(Color.parseColor("#000000"));
                    Fc_9.setTextColor(Color.parseColor("#000000"));
                    Fc_10.setTextColor(Color.parseColor("#000000"));


                }

                    else {
                        float Lspec = Float.parseFloat(Lowerspec.getText().toString());
                        float Uspec = Float.parseFloat(Upperspec.getText().toString());

                        if (Fc_1.getVisibility() == View.VISIBLE) {

                            num1 = Float.parseFloat(Fc_1.getText().toString());
                            if (num1 < Lspec || num1 > Uspec) {

                                judgeHolder = "FAILED";
                                colorHolder = "#FF0000";
                                FC_Judgement.setText(judgeHolder);

                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                                Fc_1.setTextColor(Color.parseColor("#FF0000"));

                            } else {
                                FC_Judgement.setText(judgeHolder);
                                Fc_1.setTextColor(Color.parseColor("#58f40b"));
                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                            }
                        }

                        if (Fc_2.getVisibility() == View.VISIBLE) {
                            num2 = Float.parseFloat(Fc_2.getText().toString());
                            if (num2 < Lspec || num2 > Uspec) {
                                judgeHolder = "FAILED";

                                colorHolder = "#FF0000";
                                FC_Judgement.setText(judgeHolder);

                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                                Fc_2.setTextColor(Color.parseColor("#FF0000"));

                            } else {
                                FC_Judgement.setText(judgeHolder);
                                Fc_2.setTextColor(Color.parseColor("#58f40b"));
                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                            }
                        }

                        if (Fc_3.getVisibility() == View.VISIBLE) {
                            num3 = Float.parseFloat(Fc_3.getText().toString());
                            if (num3 < Lspec || num3 > Uspec) {
                                judgeHolder = "FAILED";

                                colorHolder = "#FF0000";
                                FC_Judgement.setText(judgeHolder);

                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                                Fc_3.setTextColor(Color.parseColor("#FF0000"));

                            } else {
                                FC_Judgement.setText(judgeHolder);
                                Fc_3.setTextColor(Color.parseColor("#58f40b"));
                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                            }
                        }

                        if (Fc_4.getVisibility() == View.VISIBLE) {
                            num4 = Float.parseFloat(Fc_4.getText().toString());
                            if (num4 < Lspec || num4 > Uspec) {
                                judgeHolder = "FAILED";

                                colorHolder = "#FF0000";
                                FC_Judgement.setText(judgeHolder);

                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                                Fc_4.setTextColor(Color.parseColor("#FF0000"));

                            } else {
                                FC_Judgement.setText(judgeHolder);
                                Fc_4.setTextColor(Color.parseColor("#58f40b"));
                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                            }
                        }

                        if (Fc_5.getVisibility() == View.VISIBLE) {
                            num5 = Float.parseFloat(Fc_5.getText().toString());
                            if (num5 < Lspec || num5 > Uspec) {
                                judgeHolder = "FAILED";

                                colorHolder = "#FF0000";
                                FC_Judgement.setText(judgeHolder);

                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                                Fc_5.setTextColor(Color.parseColor("#FF0000"));

                            } else {
                                FC_Judgement.setText(judgeHolder);
                                Fc_5.setTextColor(Color.parseColor("#58f40b"));
                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                            }
                        }

                        if (Fc_6.getVisibility() == View.VISIBLE) {
                            num6 = Float.parseFloat(Fc_6.getText().toString());
                            if (num6 < Lspec || num6 > Uspec) {
                                judgeHolder = "FAILED";

                                colorHolder = "#FF0000";
                                FC_Judgement.setText(judgeHolder);

                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                                Fc_6.setTextColor(Color.parseColor("#FF0000"));

                            } else {
                                FC_Judgement.setText(judgeHolder);
                                Fc_6.setTextColor(Color.parseColor("#58f40b"));
                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                            }
                        }

                        if (Fc_7.getVisibility() == View.VISIBLE) {
                            num7 = Float.parseFloat(Fc_7.getText().toString());
                            if (num7 < Lspec || num7 > Uspec) {
                                judgeHolder = "FAILED";

                                colorHolder = "#FF0000";
                                FC_Judgement.setText(judgeHolder);

                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                                Fc_7.setTextColor(Color.parseColor("#FF0000"));

                            } else {
                                FC_Judgement.setText(judgeHolder);
                                Fc_7.setTextColor(Color.parseColor("#58f40b"));
                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                            }
                        }

                        if (Fc_8.getVisibility() == View.VISIBLE) {
                            num8 = Float.parseFloat(Fc_8.getText().toString());
                            if (num8 < Lspec || num8 > Uspec) {
                                judgeHolder = "FAILED";

                                colorHolder = "#FF0000";
                                FC_Judgement.setText(judgeHolder);

                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                                Fc_8.setTextColor(Color.parseColor("#FF0000"));

                            } else {
                                FC_Judgement.setText(judgeHolder);
                                Fc_8.setTextColor(Color.parseColor("#58f40b"));
                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                            }
                        }

                        if (Fc_9.getVisibility() == View.VISIBLE) {
                            num9 = Float.parseFloat(Fc_9.getText().toString());
                            if (num9 < Lspec || num9 > Uspec) {
                                judgeHolder = "FAILED";

                                colorHolder = "#FF0000";
                                FC_Judgement.setText(judgeHolder);

                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                                Fc_9.setTextColor(Color.parseColor("#FF0000"));
                            } else {
                                FC_Judgement.setText(judgeHolder);
                                Fc_9.setTextColor(Color.parseColor("#58f40b"));
                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                            }
                        }

                        if (Fc_10.getVisibility() == View.VISIBLE) {
                            num10 = Float.parseFloat(Fc_10.getText().toString());
                            if (num10 < Lspec || num10 > Uspec) {
                                judgeHolder = "FAILED";

                                colorHolder = "#FF0000";
                                FC_Judgement.setText(judgeHolder);

                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                                Fc_10.setTextColor(Color.parseColor("#FF0000"));
                            } else {
                                FC_Judgement.setText(judgeHolder);
                                Fc_10.setTextColor(Color.parseColor("#58f40b"));
                                FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                            }
                        }
                    }
            }




            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        DCAddDefects = findViewById(R.id.btn_FCAdddefects);
        DCAddDefects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              FcAdddefects();

            }
        });



        FC_Judgement = findViewById(R.id.FCJudgement);


        //For Removing Samples
        REMSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ctr > 1 ) {
                    ctr--;


                }
                Hidesample();

            }
        });

        //for generating samples
        ADDSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ctr < 10) {
                    ctr++;
                }
                showsample();


            }
        });

        // CHECKPOINT ALPHANUMERIC GENERATING
        Fc_Checkpoints.addTextChangedListener(new TextWatcher() {

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
                    Fc_label.setText("Checkpoint:");
                    checkp_hldr = Fc_Checkpoints.getText().toString();
                    Fc_label.setText(Fc_label.getText().toString() + " " + checkp_hldr);
                }

                else
                {
                    Fc_label.setText("Checkpoint:");
                }
            }
        });



        // FOR INPUTTING NUMERIC OR NOT

        TextWatcher textWatcher = new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(isNumeric(Fc_1.getText().toString()) == true) {

                        Lowerspec.setEnabled(true);
                        Upperspec.setEnabled(true);

                        List<Float> list = new ArrayList<Float>();
                        float sum = 0;
                        list.add(num1);
                        Collections.sort(list);

                        float average = (num1) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);



                        Minimum.setText(String.valueOf(min));
                        Maximum.setText(String.valueOf(max));
                        Average.setText(String.valueOf(average));
                        Fc_Samplesize.setText((String.valueOf(list.size())));
                    }
                    else{
                    Minimum.setText("");
                    Average.setText("");
                    Maximum.setText("");
                    Lowerspec.setEnabled(false);
                    Upperspec.setEnabled(false);
                }
                if(isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true) {
                    float num1 = Float.parseFloat(Fc_1.getText().toString());
                    float num2 = Float.parseFloat(Fc_2.getText().toString());
                    List<Float> list = new ArrayList<Float>();
                    float sum = 0;
                    list.add(num1);
                    list.add(num2);
                    Collections.sort(list);



                    float average = (num1+num2)/list.size();

                    final float min = list.get(0);
                    float max = list.get(list.size() - 1);


                    Minimum.setText(String.valueOf(min));
                    Maximum.setText(String.valueOf(max));
                    Average.setText(String.valueOf(average));
                    Fc_Samplesize.setText((String.valueOf(list.size())));
                }else{

                }

                if(isNumeric(Fc_1.getText().toString())== true && isNumeric(Fc_2.getText().toString()) == true &&isNumeric(Fc_3.getText().toString()) == true) {

                    float num1 = Float.parseFloat(Fc_1.getText().toString());
                    float num2 = Float.parseFloat(Fc_2.getText().toString());
                    float num3 = Float.parseFloat(Fc_3.getText().toString());
                    List<Float> list = new ArrayList<Float>();

                    list.add(num1);
                    list.add(num2);
                    list.add(num3);
                    Collections.sort(list);

                    float average = (num1+num2+num3)/list.size();

                    final float min = list.get(0);
                    float max = list.get(list.size() - 1);


                    Minimum.setText(String.valueOf(min));
                    Maximum.setText(String.valueOf(max));
                    Average.setText(String.valueOf(average));
                    Fc_Samplesize.setText((String.valueOf(list.size())));
                }else{

                }

                if(isNumeric(Fc_1.getText().toString())== true && isNumeric(Fc_2.getText().toString()) == true &&isNumeric(Fc_3.getText().toString()) == true && isNumeric(Fc_4.getText().toString()) == true) {

                    float num1 = Float.parseFloat(Fc_1.getText().toString());
                    float num2 = Float.parseFloat(Fc_2.getText().toString());
                    float num3 = Float.parseFloat(Fc_3.getText().toString());
                    float num4 = Float.parseFloat(Fc_4.getText().toString());
                    List<Float> list = new ArrayList<Float>();

                    list.add(num1);
                    list.add(num2);
                    list.add(num3);
                    list.add(num4);
                    Collections.sort(list);

                    float average = (num1+num2+num3+num4)/list.size();

                    final float min = list.get(0);
                    float max = list.get(list.size() - 1);


                    Minimum.setText(String.valueOf(min));
                    Maximum.setText(String.valueOf(max));
                    Average.setText(String.valueOf(average));
                    Fc_Samplesize.setText((String.valueOf(list.size())));
                }else{

                }

                if(isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true && isNumeric(Fc_3.getText().toString()) == true && isNumeric(Fc_4.getText().toString()) == true && isNumeric(Fc_5.getText().toString()) == true) {

                    float num1 = Float.parseFloat(Fc_1.getText().toString());
                    float num2 = Float.parseFloat(Fc_2.getText().toString());
                    float num3 = Float.parseFloat(Fc_3.getText().toString());
                    float num4 = Float.parseFloat(Fc_4.getText().toString());
                    float num5 = Float.parseFloat(Fc_5.getText().toString());
                    List<Float> list = new ArrayList<Float>();

                    list.add(num1);
                    list.add(num2);
                    list.add(num3);
                    list.add(num4);
                    list.add(num5);
                    Collections.sort(list);

                    float average = (num1+num2+num3+num4+num5)/list.size();

                    final float min = list.get(0);
                    float max = list.get(list.size() - 1);


                    Minimum.setText(String.valueOf(min));
                    Maximum.setText(String.valueOf(max));
                    Average.setText(String.valueOf(average));
                    Fc_Samplesize.setText((String.valueOf(list.size())));
                }else{

                }

                if(isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true && isNumeric(Fc_3.getText().toString()) == true && isNumeric(Fc_4.getText().toString()) == true && isNumeric(Fc_5.getText().toString()) == true && isNumeric(Fc_6.getText().toString()) == true) {

                    float num1 = Float.parseFloat(Fc_1.getText().toString());
                    float num2 = Float.parseFloat(Fc_2.getText().toString());
                    float num3 = Float.parseFloat(Fc_3.getText().toString());
                    float num4 = Float.parseFloat(Fc_4.getText().toString());
                    float num5 = Float.parseFloat(Fc_5.getText().toString());
                    float num6 = Float.parseFloat(Fc_6.getText().toString());
                    List<Float> list = new ArrayList<Float>();

                    list.add(num1);
                    list.add(num2);
                    list.add(num3);
                    list.add(num4);
                    list.add(num5);
                    list.add(num6);
                    Collections.sort(list);

                    float average = (num1+num2+num3+num4+num5+num6)/list.size();

                    final float min = list.get(0);
                    float max = list.get(list.size() - 1);

                    Minimum.setText(String.valueOf(min));
                    Maximum.setText(String.valueOf(max));
                    Average.setText(String.valueOf(average));
                    Fc_Samplesize.setText((String.valueOf(list.size())));
                }else{

                }

                if(isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true && isNumeric(Fc_3.getText().toString()) == true && isNumeric(Fc_4.getText().toString()) == true && isNumeric(Fc_5.getText().toString()) == true && isNumeric(Fc_6.getText().toString()) == true && isNumeric(Fc_1.getText().toString()) == true) {

                    float num1 = Float.parseFloat(Fc_1.getText().toString());
                    float num2 = Float.parseFloat(Fc_2.getText().toString());
                    float num3 = Float.parseFloat(Fc_3.getText().toString());
                    float num4 = Float.parseFloat(Fc_4.getText().toString());
                    float num5 = Float.parseFloat(Fc_5.getText().toString());
                    float num6 = Float.parseFloat(Fc_6.getText().toString());
                    float num7 = Float.parseFloat(Fc_7.getText().toString());
                    List<Float> list = new ArrayList<Float>();

                    list.add(num1);
                    list.add(num2);
                    list.add(num3);
                    list.add(num4);
                    list.add(num5);
                    list.add(num6);
                    list.add(num7);
                    Collections.sort(list);

                    float average = (num1+num2+num3+num4+num5+num6+num7)/list.size();

                    final float min = list.get(0);
                    float max = list.get(list.size() - 1);

                    Minimum.setText(String.valueOf(min));
                    Maximum.setText(String.valueOf(max));
                    Average.setText(String.valueOf(average));
                    Fc_Samplesize.setText((String.valueOf(list.size())));
                }else{

                }


                if(isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true && isNumeric(Fc_3.getText().toString()) == true && isNumeric(Fc_4.getText().toString()) == true && isNumeric(Fc_5.getText().toString()) == true && isNumeric(Fc_6.getText().toString()) == true && isNumeric(Fc_7.getText().toString()) == true && isNumeric(Fc_8.getText().toString()) == true) {

                    float num1 = Float.parseFloat(Fc_1.getText().toString());
                    float num2 = Float.parseFloat(Fc_2.getText().toString());
                    float num3 = Float.parseFloat(Fc_3.getText().toString());
                    float num4 = Float.parseFloat(Fc_4.getText().toString());
                    float num5 = Float.parseFloat(Fc_5.getText().toString());
                    float num6 = Float.parseFloat(Fc_6.getText().toString());
                    float num7 = Float.parseFloat(Fc_7.getText().toString());
                    float num8 = Float.parseFloat(Fc_8.getText().toString());
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

                    float average = (num1+num2+num3+num4+num5+num6+num7+num8)/list.size();

                    final float min = list.get(0);
                    float max = list.get(list.size() - 1);


                    Minimum.setText(String.valueOf(min));
                    Maximum.setText(String.valueOf(max));
                    Average.setText(String.valueOf(average));
                    Fc_Samplesize.setText((String.valueOf(list.size())));
                }else{

                }

                if(isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true && isNumeric(Fc_3.getText().toString()) == true && isNumeric(Fc_4.getText().toString()) == true && isNumeric(Fc_5.getText().toString()) == true && isNumeric(Fc_6.getText().toString()) == true && isNumeric(Fc_7.getText().toString()) == true && isNumeric(Fc_8.getText().toString()) == true && isNumeric(Fc_9.getText().toString()) == true) {

                    float num1 = Float.parseFloat(Fc_1.getText().toString());
                    float num2 = Float.parseFloat(Fc_2.getText().toString());
                    float num3 = Float.parseFloat(Fc_3.getText().toString());
                    float num4 = Float.parseFloat(Fc_4.getText().toString());
                    float num5 = Float.parseFloat(Fc_5.getText().toString());
                    float num6 = Float.parseFloat(Fc_6.getText().toString());
                    float num7 = Float.parseFloat(Fc_7.getText().toString());
                    float num8 = Float.parseFloat(Fc_8.getText().toString());
                    float num9 = Float.parseFloat(Fc_9.getText().toString());
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

                    float average = (num1+num2+num3+num4+num5+num6+num7+num8+num9)/list.size();

                    final float min = list.get(0);
                    float max = list.get(list.size() - 1);


                    Minimum.setText(String.valueOf(min));
                    Maximum.setText(String.valueOf(max));
                    Average.setText(String.valueOf(average));
                    Fc_Samplesize.setText((String.valueOf(list.size())));
                }else{

                }

                if(isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true && isNumeric(Fc_3.getText().toString()) == true && isNumeric(Fc_4.getText().toString()) == true && isNumeric(Fc_5.getText().toString()) == true && isNumeric(Fc_6.getText().toString()) == true && isNumeric(Fc_7.getText().toString()) == true && isNumeric(Fc_8.getText().toString()) == true && isNumeric(Fc_9.getText().toString()) == true && isNumeric(Fc_10.getText().toString()) == true  ) {

                    float num1 = Float.parseFloat(Fc_1.getText().toString());
                    float num2 = Float.parseFloat(Fc_2.getText().toString());
                    float num3 = Float.parseFloat(Fc_3.getText().toString());
                    float num4 = Float.parseFloat(Fc_4.getText().toString());
                    float num5 = Float.parseFloat(Fc_5.getText().toString());
                    float num6 = Float.parseFloat(Fc_6.getText().toString());
                    float num7 = Float.parseFloat(Fc_7.getText().toString());
                    float num8 = Float.parseFloat(Fc_8.getText().toString());
                    float num9 = Float.parseFloat(Fc_9.getText().toString());
                    float num10 = Float.parseFloat(Fc_10.getText().toString());
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

                    float average = (num1+num2+num3+num4+num5+num6+num7+num8+num9+num10)/list.size();

                    final float min = list.get(0);
                    float max = list.get(list.size() - 1);


                    Minimum.setText(String.valueOf(min));
                    Maximum.setText(String.valueOf(max));
                    Average.setText(String.valueOf(average));
                    Fc_Samplesize.setText((String.valueOf(list.size())));
                }
                else{

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };

        Fc_1.addTextChangedListener(textWatcher);
        Fc_2.addTextChangedListener(textWatcher);
        Fc_3.addTextChangedListener(textWatcher);
        Fc_3.addTextChangedListener(textWatcher);
        Fc_4.addTextChangedListener(textWatcher);
        Fc_5.addTextChangedListener(textWatcher);
        Fc_6.addTextChangedListener(textWatcher);
        Fc_7.addTextChangedListener(textWatcher);
        Fc_8.addTextChangedListener(textWatcher);
        Fc_9.addTextChangedListener(textWatcher);
        Fc_10.addTextChangedListener(textWatcher);

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

    public void insert_funcheck()
    {
        sampleSizeFC = ctr;
        String Checkpoints = Fc_Checkpoints.getText().toString();
        String Instrumentused = Fc_Instrumentused.getText().toString();
        String Sampleunit = Fc_Sampleunit.getText().toString();
        String DC1 = Fc_1.getText().toString();
        String DC2 = Fc_2.getText().toString();
        String DC3 = Fc_3.getText().toString();
        String DC4 = Fc_4.getText().toString();
        String DC5 = Fc_5.getText().toString();
        String DC6 = Fc_6.getText().toString();
        String DC7 = Fc_7.getText().toString();
        String DC8 = Fc_8.getText().toString();
        String DC9 = Fc_9.getText().toString();
        String DC10 = Fc_10.getText().toString();
        String Min = Minimum.getText().toString();
        String Ave = Average.getText().toString();
        String Max = Maximum.getText().toString();
        String LowerSpec = Lowerspec.getText().toString();
        String UppperSpec = Upperspec.getText().toString();
        String Judgmnt = FC_Judgement.getText().toString();
        String Samplesize = Fc_Samplesize.getText().toString();
        String Remm = Remarkss.getText().toString();


        if (Checkpoints.trim().equals(""))//dagdagan mo
        {
            Toast.makeText(getApplicationContext(), "Must input atleast 1 checkpoint!", Toast.LENGTH_LONG).show();
        }
        else {
            try {
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN();

                String query = "INSERT INTO FunctionalCheck (invoice_no, goodsCode, checkpoints, instrument_used, sample_unit, sample1, sample2, sample3, sample4, sample5, sample6, sample7, sample8, sample9, sample10, minimum, average, maximum, lower_spec_limit, upper_spec_limit, judgement, remarks, MaterialCodeBoxSeqID) values ('"+LotNumber.invoicenumholder+"', '"+LotNumber.materialholder+"', '"+Checkpoints+"','"+Instrumentused+"','"+Sampleunit+"','"+DC1+"','"+DC2+"','"+DC3+"','"+DC4+"','"+DC5+"','"+DC6+"','"+DC7+"','"+DC8+"','"+DC9+"','"+DC10+"','"+Min+"','"+Ave+"','"+Max+"','"+LowerSpec+"','"+UppperSpec+"','"+Judgmnt+"','"+Remm+"','"+LotNumber.boxseqholder+"')";
                //Toast.makeText(this, String.valueOf(sampleSizeFC), Toast.LENGTH_LONG).show();
                String query1 = "UPDATE SampleSize SET function_sample_size = '"+sampleSizeFC+"' WHERE id = '"+DimensionalCheck.samplesize_id_hldr+"'";
                Statement stmt = con.createStatement();
                stmt.execute(query+query1);
                Toast.makeText(getApplicationContext(), "Successfully added!", Toast.LENGTH_SHORT).show();
                samplesize_id_hldr = Latest_ID("SampleSize");
                fccheck_id_hldr = Latest_ID("FunctionalCheck");
                AlertSuccess();

              /*  if (FC_Judgement.getText().toString() == "FAILED" || Fc_Checkpoints.getText().toString() == "X")
                {
                    DimensionalCheck.overallJudgement = false;
                }
                if (DimensionalCheck.overallJudgement == false)
                {
                    overallJudgement("FAILED");
                }
                else
                {
                    overallJudgement("PASSED");
                }*/

                reset_sample();
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

   /* public void overallJudgement(String overallJudge)
    {
        try {
            connectionClass = new ConnectionClass();
            Connection con = connectionClass.CONN();
            String query = "INSERT INTO tblOverall_Judgement (invoice_no, goodsCode, overall_judgement, MaterialCodeBoxSeqID) values ('" + LotNumber.invoicenumholder + "', '" + LotNumber.materialholder + "', '" + overallJudge + "','" + LotNumber.boxseqholder + "')";
            Statement stmt = con.createStatement();
            stmt.execute(query);
        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }*/
    public void checkApprovedby_update()
    {
        String checkBy = checkedBy.getText().toString();
        String checkDate = checkedDate.getText().toString();
        String approvedBy = approveBy.getText().toString();
        String approveDate = Approve.getText().toString();
        try {
            connectionClass = new ConnectionClass();
            Connection con = connectionClass.CONN();
            String query = "UPDATE inspectiondata SET checked = '"+checkBy+"', checked_date = '"+checkDate+"', approved = '"+approvedBy+"', approved_date = '"+approveDate+"' WHERE MaterialCodeBoxSeqID = '"+LotNumber.boxseqholder+"'";
            Statement stmt = con.createStatement();
            stmt.execute(query);

        }
        catch (Exception ex)
        {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Hidesample() {
        if(ctr == 1){
            Fc_2.setVisibility(View.INVISIBLE);
            Fc_2.setText("");
        }
        else if( ctr == 2){
            Fc_3.setVisibility(View.INVISIBLE);
            Fc_3.setText("");
        }
        else if(ctr == 3){
            Fc_4.setVisibility(View.INVISIBLE);
            Fc_4.setText("");
        }

        else if(ctr == 4){
            Fc_5.setVisibility(View.INVISIBLE);
            Fc_5.setText("");
        }

        else if(ctr == 5){
            Fc_6.setVisibility(View.INVISIBLE);
            Fc_6.setText("");
        }

        else if(ctr == 6){
            Fc_7.setVisibility(View.INVISIBLE);
            Fc_7.setText("");
        }

        else if(ctr == 7){
            Fc_8.setVisibility(View.INVISIBLE);
            Fc_8.setText("");
        }

        else if(ctr == 8){
            Fc_9.setVisibility(View.INVISIBLE);
            Fc_9.setText("");
        }
        else if(ctr == 9){
            Fc_10.setVisibility(View.INVISIBLE);
            Fc_10.setText("");
        }



    }

    public void reset_sample()
    {
        sampleSizeFC = 0;
        ctr = 1;
        Fc_1.setText("");
        Fc_2.setText("");
        Fc_3.setText("");
        Fc_4.setText("");
        Fc_5.setText("");
        Fc_6.setText("");
        Fc_7.setText("");
        Fc_8.setText("");
        Fc_9.setText("");
        Fc_10.setText("");
        Fc_2.setVisibility(View.INVISIBLE);
        Fc_3.setVisibility(View.INVISIBLE);
        Fc_4.setVisibility(View.INVISIBLE);
        Fc_5.setVisibility(View.INVISIBLE);
        Fc_6.setVisibility(View.INVISIBLE);
        Fc_7.setVisibility(View.INVISIBLE);
        Fc_8.setVisibility(View.INVISIBLE);
        Fc_9.setVisibility(View.INVISIBLE);
        Fc_10.setVisibility(View.INVISIBLE);
        Fc_Checkpoints.setText("");
        Lowerspec.setText("");
        Upperspec.setText("");

        Judgement.setText("");
        FC_Judgement.setText("");
    }


    public void showsample() {
        if( ctr == 2){
            Fc_2.setVisibility(View.VISIBLE);
        }

        else if(ctr == 3){
            Fc_3.setVisibility(View.VISIBLE);
        }

        else if(ctr == 4){
            Fc_4.setVisibility(View.VISIBLE);
        }

        else if(ctr == 5){
            Fc_5.setVisibility(View.VISIBLE);
        }

        else if(ctr == 6){
            Fc_6.setVisibility(View.VISIBLE);
        }

        else if(ctr == 7){
            Fc_7.setVisibility(View.VISIBLE);
        }

        else if(ctr == 8){
            Fc_8.setVisibility(View.VISIBLE);
        }
        else if(ctr == 9){
            Fc_9.setVisibility(View.VISIBLE);
        }

        else if(ctr == 10){
            Fc_10.setVisibility(View.VISIBLE);
        }

    }
    public void instrumentUsed()
    {
        final Spinner InstruSpin = findViewById(R.id.instrumentspin);
        final AutoCompleteTextView fcInstrument = findViewById(R.id.FCInstrument);
        connectionClass = new ConnectionClass();
        try{
            Connection con = connectionClass.CONN();
            String query = "SELECT * from InstrumentUsed";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            final ArrayList<String> instru = new ArrayList<String>();
            while (rs.next()){
                String invoicenumber = rs.getString("Instrument_Used");
                instru.add(invoicenumber);
            }

            final ArrayAdapter<String> invoice_array = new ArrayAdapter(this, android.R.layout.simple_list_item_1, instru);
            fcInstrument.setThreshold(1);
            fcInstrument.setAdapter(invoice_array);

            fcInstrument.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String str = InstruSpin.getSelectedItem().toString();
                    fcInstrument.setText(str);



                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });




        }catch (Exception e){

        }
    }


    public void AlertSuccess(){
        Successdialog = new Dialog(FunctionalCheck.this);
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
        Faileddialog = new Dialog(FunctionalCheck.this);
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
        AddnewDialog = new Dialog(FunctionalCheck.this);
        AddnewDialog.setContentView(R.layout.activity_alert_addnew);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            AddnewDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.alertsuccessbackground));
        }

        AddnewDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        AddnewDialog.setCancelable(false);

        Button addnew = AddnewDialog.findViewById(R.id.btn_alertaddnew);
        addnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fc_1.setTextColor(Color.parseColor("#000000"));
                Fc_1.setTextColor(Color.parseColor("#000000"));
                Fc_1.setTextColor(Color.parseColor("#000000"));
                Fc_1.setTextColor(Color.parseColor("#000000"));
                Fc_1.setTextColor(Color.parseColor("#000000"));
                Fc_1.setTextColor(Color.parseColor("#000000"));
                Fc_1.setTextColor(Color.parseColor("#000000"));
                Fc_1.setTextColor(Color.parseColor("#000000"));
                Fc_1.setTextColor(Color.parseColor("#000000"));
                Fc_1.setTextColor(Color.parseColor("#000000"));
                AddnewDialog.dismiss();


            }
        });

        Button proceednext = AddnewDialog.findViewById(R.id.btn_alertproceed);
        proceednext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkApprovedby_update();
                Toast.makeText(getApplicationContext(), "Thank you!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FunctionalCheck.this, LotNumber.class);
                startActivity(intent);
            }
        });

        AddnewDialog.show();
    }

    public void FcAdddefects(){
        //String invoice = Fc_invoiceno.getText().toString();
        Intent intent = new Intent(FunctionalCheck.this, FCDefects.class);
        //intent.putExtra("keyinvoice_no.", invoice);
        startActivity(intent);
    }


    public void Next()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(FunctionalCheck.this);
        builder.setMessage("Are you sure you want to proceed?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getApplicationContext(), "Successfully inserted! Thank you!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FunctionalCheck.this, LotNumber.class);
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
    @Override
    public void onBackPressed() {
        return;
    }
}
