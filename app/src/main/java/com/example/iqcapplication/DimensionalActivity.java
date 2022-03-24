package com.example.iqcapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class DimensionalActivity extends AppCompatActivity {
    EditText  dc_checkPoints,upperSpec,lowerSpec,sammpleUnit;
    TextView dc_Minimum,dc_Maximum,dc_Average,dc_Judgemen;
    EditText dc1,dc2,dc3,dc4,dc5,dc6,dc7,dc8,dc9,dc10;

    public ArrayAdapter  dcinstrument_adapter;
    Button addData,uploadtosqlite;

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
    public  static  String checkp_hldr = "", judgeHolder = "PASSED", colorHolder = "#58f40b";
    public static boolean fourinstrument = false, overallJudgement = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimensional);



        sammpleUnit = findViewById(R.id.sampleUnit);
        addData = findViewById(R.id.addData);

        //-----SAMPLE VALUES---
        dc1 = findViewById(R.id.dc1);
        dc2 = findViewById(R.id.dc2);
        dc3 = findViewById(R.id.dc3);
        dc4 = findViewById(R.id.dc4);
        dc5 = findViewById(R.id.dc5);
        dc6 = findViewById(R.id.dc6);
        dc7 = findViewById(R.id.dc7);
        dc8 = findViewById(R.id.dc8);
        dc9 = findViewById(R.id.dc9);
        dc10 = findViewById(R.id.dc10);

        //-----MIN MAX AVERAGE
        dc_Minimum = findViewById(R.id.minimum);
        dc_Maximum = findViewById(R.id.maximum);
        dc_Average = findViewById(R.id.average);
        dc_Judgemen = findViewById(R.id.judgement);
        //--- upper and lower specs
        upperSpec = findViewById(R.id.upperspecs);
        lowerSpec = findViewById(R.id.lowerspec);



        sammpleUnit.setText("Mm");
        sammpleUnit.setEnabled(false);

        dcInstrument();




    }


    public void dcInstrument(){
        AutoCompleteTextView instrumentUsed =  findViewById(R.id.instrumentUsed);


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

                    if (dc1.getVisibility() == View.VISIBLE)
                    {
                        try {
                            num1 = Float.parseFloat(dc1.getText().toString());
                        }
                        catch (Exception ex)
                        {

                            Toast.makeText(getApplicationContext(), "Insert value to sample 1!", Toast.LENGTH_LONG).show();

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

                    if (dc2.getVisibility() == View.VISIBLE) {

                        try {
                            num2 = Float.parseFloat(dc2.getText().toString());
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Insert value to sample 2!", Toast.LENGTH_LONG).show();
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

                    if (dc3.getVisibility() == View.VISIBLE) {
                        try {
                            num3 = Float.parseFloat(dc3.getText().toString());
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Insert value to sample 3!", Toast.LENGTH_LONG).show();
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

                    if (dc4.getVisibility() == View.VISIBLE) {
                        try{
                            num4 = Float.parseFloat(dc4.getText().toString());
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Insert value to sample 4!", Toast.LENGTH_LONG).show();
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

                    if (dc5.getVisibility() == View.VISIBLE) {
                        try{
                            num5 = Float.parseFloat(dc5.getText().toString());
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Insert value to sample 5!", Toast.LENGTH_LONG).show();
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

                    if (dc6.getVisibility() == View.VISIBLE) {

                        try{
                            num6 = Float.parseFloat(dc6.getText().toString());
                        }catch (Exception e ){
                            Toast.makeText(getApplicationContext(), "Insert value to sample 6!", Toast.LENGTH_LONG).show();
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

                    if (dc7.getVisibility() == View.VISIBLE)
                    {
                        try{
                            num7 = Float.parseFloat(dc7.getText().toString());
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Insert value to sample 7!", Toast.LENGTH_LONG).show();
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

                    if (dc8.getVisibility() == View.VISIBLE) {

                        try{
                            num8 = Float.parseFloat(dc8.getText().toString());
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Insert value to sample 8!", Toast.LENGTH_LONG).show();
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

                    if (dc9.getVisibility() == View.VISIBLE) {

                        try{
                            num9 = Float.parseFloat(dc9.getText().toString());
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Insert value to sample 8!", Toast.LENGTH_LONG).show();
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

                    if (dc10.getVisibility() == View.VISIBLE) {

                        try{
                            num10 = Float.parseFloat(dc10.getText().toString());
                        }catch(Exception e){
                            Toast.makeText(getApplicationContext(), "Insert value to sample 8!", Toast.LENGTH_LONG).show();
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









}