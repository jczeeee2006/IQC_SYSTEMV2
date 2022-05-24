package com.example.iqcapplication.Update;

import static com.example.iqcapplication.FunctionalActivity2.isNumeric;
import static com.example.iqcapplication.MainActivity.connectionClass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iqcapplication.ConnectionClass;
import com.example.iqcapplication.DatabaseHelper;
import com.example.iqcapplication.DimensionalActivity;
import com.example.iqcapplication.FunctionalActivity2;
import com.example.iqcapplication.R;
import com.example.iqcapplication.SapmpleActivityinlot;
import com.example.iqcapplication.add.InspectionDetailsActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionalActivity extends AppCompatActivity {

    EditText fc_checkPoints,upperSpec,lowerSpec,sammpleUnit,ffcsampleSize,fcquantdefect,fcupencountered,fcremarks;


    ConnectionClass connectionClass = new ConnectionClass();
    EditText Fc_1, Fc_2, Fc_3, Fc_4, Fc_5, Fc_6, Fc_7, Fc_8, Fc_9, Fc_10;
    AutoCompleteTextView instrumentUsed;
    TextView fc_Minimum,fc_Maximum,fc_Average,FC_Judgement,dateTodayfc;

    String id,instrumentUsedstring,  samplenum,  checkpoint,  samplUnit,  sample1,  sample2,  sample3,  sample4 ,  sample5,  sample6,
            sample7,  sample8,  sample9, sample10, lower,  upper,  max,  min,  average,  judgement,gdatedim;

    public static int  funcheck_id_hldr = 0, samplesizefc_id_hldr=0;

    public static int ctr = 1, samplesize_id_hldr=0, dimcheck_id_hldr = 0, sampleSizeDC = 0;
    public  static String judgeHolder = "PASSED", colorHolder = "#58f40b";
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

    Button addData,uploadtosqlsserver,backbutton,addDefectdc,updateTosqlite ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functional2);

        sammpleUnit = findViewById(R.id.sampleUnitupfc);
        addData = findViewById(R.id.viewdadtfun);

        //-----SAMPLE VALUES---
        Fc_1  = findViewById(R.id.fc1up);
        Fc_2  = findViewById(R.id.fc2up);
        Fc_3  = findViewById(R.id.fc3up);
        Fc_4  = findViewById(R.id.fc4up);
        Fc_5  = findViewById(R.id.fc5up);
        Fc_6  = findViewById(R.id.fc6up);
        Fc_7  = findViewById(R.id.fc7up);
        Fc_8  = findViewById(R.id.fc8up);
        Fc_9  = findViewById(R.id.fc9up);
        Fc_10  = findViewById(R.id.fc10up);

        instrumentUsed = findViewById(R.id.instrumentUsedupfc);
        fcquantdefect = findViewById(R.id.fcquantityDefect);
        fcupencountered  = findViewById(R.id.fcupencountered);
        fcremarks = findViewById(R.id.FunctRemarksdefect);

        fc_Minimum  = findViewById(R.id.minimumupfc);
        fc_Maximum  = findViewById(R.id.maximumupfc);
        fc_Average  = findViewById(R.id.average_txtupfc);

        FC_Judgement  = findViewById(R.id.fcjudgeMentup);
        dateTodayfc  = findViewById(R.id.datefunup);
        backbutton = findViewById(R.id.BACKbUTTONFC);
        fc_checkPoints  = findViewById(R.id.checkPointup);
        upperSpec  = findViewById(R.id.upperspecsupfc);
        lowerSpec  = findViewById(R.id.lowerspecupfc);
        ffcsampleSize = findViewById(R.id.sampleSizefc_up);
        uploadtosqlsserver = findViewById(R.id.updateTosqlite);

        getIntentData();

          sampleComputation();


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FunctionalActivity.this, FunctionalActivity2.class);
                startActivity(intent);
            }
        });
        uploadtosqlsserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();
            }
        });

        judgeHolder = "PASSED";
        colorHolder = "#58f40b";
        if(upperSpec.getText().toString().equals(""))
        {
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
            float Lspec = 0;
            float Uspec = 0;

            try {
                Lspec = Float.parseFloat(lowerSpec.getText().toString());
                Uspec = Float.parseFloat(upperSpec.getText().toString());
            } catch (Exception ex) {
                Toast.makeText(FunctionalActivity.this, "Input Valid Value", Toast.LENGTH_SHORT).show();
            }

            if (!Fc_1.getText().toString().equals("")) {
                try {
                    num1 = Float.parseFloat(Fc_1.getText().toString());
                } catch (Exception ex) {

                    Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();

                }


                if (num1 < Lspec || num1 > Uspec) {

                    judgeHolder = "FAILED";
                    colorHolder = "#FF0000";
                    FC_Judgement.setText(judgeHolder);
                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                    Fc_1.setTextColor(Color.parseColor("#FF0000"));

                } else {
                    FC_Judgement.setText(judgeHolder);

                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                    Fc_1.setTextColor(Color.parseColor("#58f40b"));
                }
            }

            if (!Fc_2.getText().toString().equals("")) {

                try {
                    num2 = Float.parseFloat(Fc_2.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

                if (num2 < Lspec || num2 > Uspec) {
                    judgeHolder = "FAILED";

                    colorHolder = "#FF0000";
                    FC_Judgement.setText(judgeHolder);

                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                    Fc_2.setTextColor(Color.parseColor("#FF0000"));
                } else {
                    FC_Judgement.setText(judgeHolder);

                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                    Fc_2.setTextColor(Color.parseColor("#58f40b"));
                }

            }

            if (!Fc_3.getText().toString().equals("")) {
                try {
                    num3 = Float.parseFloat(Fc_3.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }


                if (num3 < Lspec || num3 > Uspec) {
                    judgeHolder = "FAILED";

                    colorHolder = "#FF0000";
                    FC_Judgement.setText(judgeHolder);

                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                    Fc_3.setTextColor(Color.parseColor("#FF0000"));
                } else {
                    FC_Judgement.setText(judgeHolder);
                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                    Fc_3.setTextColor(Color.parseColor("#58f40b"));
                }

            }

            if (!Fc_4.getText().toString().equals("")) {
                try {
                    num4 = Float.parseFloat(Fc_4.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }


                if (num4 < Lspec || num4 > Uspec) {
                    judgeHolder = "FAILED";

                    colorHolder = "#FF0000";
                    FC_Judgement.setText(judgeHolder);

                    Fc_4.setTextColor(Color.parseColor("#FF0000"));
                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                } else {
                    FC_Judgement.setText(judgeHolder);
                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                    Fc_4.setTextColor(Color.parseColor("#58f40b"));
                }
            }

            if (!Fc_5.getText().toString().equals("")) {
                try {
                    num5 = Float.parseFloat(Fc_5.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }


                if (num5 < Lspec || num5 > Uspec) {
                    judgeHolder = "FAILED";

                    colorHolder = "#FF0000";
                    FC_Judgement.setText(judgeHolder);

                    Fc_5.setTextColor(Color.parseColor(colorHolder));
                    FC_Judgement.setTextColor(Color.parseColor("#FF0000"));
                } else {
                    FC_Judgement.setText(judgeHolder);
                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                    Fc_5.setTextColor(Color.parseColor("#58f40b"));

                }

            }

            if (!Fc_6.getText().toString().equals("")) {

                try {
                    num6 = Float.parseFloat(Fc_6.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }


                if (num6 < Lspec || num6 > Uspec) {
                    judgeHolder = "FAILED";

                    colorHolder = "#FF0000";
                    FC_Judgement.setText(judgeHolder);
                    Fc_6.setTextColor(Color.parseColor(colorHolder));
                    FC_Judgement.setTextColor(Color.parseColor("#FF0000"));
                } else {
                    FC_Judgement.setText(judgeHolder);
                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                    Fc_6.setTextColor(Color.parseColor("#58f40b"));
                }
            }

            if (!Fc_7.getText().toString().equals("")) {
                try {
                    num7 = Float.parseFloat(Fc_7.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }


                if (num7 < Lspec || num7 > Uspec) {
                    judgeHolder = "FAILED";

                    colorHolder = "#FF0000";
                    FC_Judgement.setText(judgeHolder);
                    Fc_7.setTextColor(Color.parseColor("#FF0000"));
                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                } else {
                    FC_Judgement.setText(judgeHolder);
                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                    Fc_7.setTextColor(Color.parseColor("#58f40b"));
                }
            }

            if (!Fc_8.getText().toString().equals("")) {

                try {
                    num8 = Float.parseFloat(Fc_8.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }


                if (num8 < Lspec || num8 > Uspec) {
                    judgeHolder = "FAILED";

                    colorHolder = "#FF0000";
                    FC_Judgement.setText(judgeHolder);
                    Fc_8.setTextColor(Color.parseColor("#FF0000"));
                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                } else {
                    FC_Judgement.setText(judgeHolder);
                    Fc_8.setTextColor(Color.parseColor("#58f40b"));
                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                }
            }

            if (!Fc_9.getText().toString().equals("")) {

                try {
                    num9 = Float.parseFloat(Fc_9.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }


                if (num9 < Lspec || num9 > Uspec) {
                    judgeHolder = "FAILED";

                    colorHolder = "#FF0000";
                    FC_Judgement.setText(judgeHolder);
                    Fc_9.setTextColor(Color.parseColor("#FF0000"));
                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                } else {
                    FC_Judgement.setText(judgeHolder);
                    Fc_9.setTextColor(Color.parseColor("#58f40b"));
                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                }
            }

            if (!Fc_10.getText().toString().equals("")) {

                try {
                    num10 = Float.parseFloat(Fc_10.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }


                if (num10 < Lspec || num10 > Uspec) {
                    judgeHolder = "FAILED";

                    colorHolder = "#FF0000";
                    FC_Judgement.setText(judgeHolder);
                    Fc_10.setTextColor(Color.parseColor("#FF0000"));
                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                } else {
                    FC_Judgement.setText(judgeHolder);
                    Fc_10.setTextColor(Color.parseColor("#58f40b"));
                    FC_Judgement.setTextColor(Color.parseColor(colorHolder));
                }
            }
        }
    }


    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("UPDATE" + "DATA" + "?");
        builder.setMessage("Are you sure you want to UPDATE?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               // updateDatainSQlite();
                updateData();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


    void confirmDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("UPDATE" + "DATA" + "?");
        builder.setMessage("Are you sure you want to UPLOAD? this process can't be undone");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            //   insert_funcheck();
              //  insert_sampleSize();


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }





    public void updateDatainSQlite(){
        try{
            DatabaseHelper myDB = new DatabaseHelper(FunctionalActivity.this);

            instrumentUsedstring =    instrumentUsed.getText().toString().trim();
            samplenum  = ffcsampleSize.getText().toString().trim();
            checkpoint = fc_checkPoints.getText().toString().trim();
            samplUnit  = sammpleUnit.getText().toString().trim();

            sample1 = Fc_1.getText().toString().trim();
            sample2 = Fc_2.getText().toString().trim();
            sample3 = Fc_3.getText().toString().trim();
            sample4 = Fc_4.getText().toString().trim();
            sample5 = Fc_5.getText().toString().trim();
            sample6 = Fc_6.getText().toString().trim();
            sample7 = Fc_7.getText().toString().trim();
            sample8 = Fc_8.getText().toString().trim();
            sample9 = Fc_9.getText().toString().trim();
            sample10 = Fc_10.getText().toString().trim();

            lower = lowerSpec.getText().toString().trim();
            upper = upperSpec.getText().toString().trim();

            min = fc_Minimum.getText().toString().trim();
            average = fc_Average.getText().toString().trim();
            max = fc_Maximum.getText().toString().trim();

            judgement = FC_Judgement.getText().toString().trim();
            gdatedim = dateTodayfc.getText().toString().trim();
            myDB.updateDc( id, instrumentUsedstring, samplenum,  checkpoint,  samplUnit,  sample1,  sample2,  sample3,  sample4 ,  sample5,  sample6,
                    sample7,  sample8,  sample9, sample10, lower,  upper,  max,  min,  average,  judgement,gdatedim);

            Toast.makeText(FunctionalActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();

        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    void updateData(){
        try{
            connectionClass = new ConnectionClass();
            Connection con = connectionClass.CONN2();

            String query = " UPDATE FunctionalCheck SET invoice_no = '" + DimensionalActivity.invoicedcholder + "', checkpoints = '"+fc_checkPoints.getText().toString()+ "'," +
                    " instrument_used = '"+instrumentUsed.getText().toString()+ "', sample_unit = '"+sammpleUnit.getText().toString()+ "'," +
                    " sample1 = '"+Fc_1.getText().toString()+ "', sample2 = '"+Fc_2.getText().toString()+ "', sample3 = '"+Fc_3.getText().toString()+ "'," +
                    " sample4 = '"+Fc_4.getText().toString()+ "', sample5 = '"+Fc_5.getText().toString()+ "', sample6 = '"+Fc_6.getText().toString()+ "', sample7 = '"+Fc_7.getText().toString()+ "', sample8 = '"+Fc_8.getText().toString()+ "', sample9 = '"+Fc_9.getText().toString()+ "'," +
                    "sample10 = '"+Fc_10.getText().toString()+ "',  minimum = '"+fc_Minimum.getText().toString()+ "',  average = '"+fc_Average.getText().toString()+ "',  " +
                    "maximum = '"+fc_Maximum.getText().toString()+" ',  lower_spec_limit = '"+lowerSpec.getText().toString()+" ',  upper_spec_limit = '"+upperSpec.getText().toString()+" ', " +
                    "judgement = '"+FC_Judgement.getText().toString()+" ' WHERE MaterialCodeBoxSeqID = '" + SapmpleActivityinlot.boxseqholder+ "'";
            Statement stmt =  con.createStatement();
            stmt.execute(query);

            Toast.makeText(getApplicationContext(),"Successfully updated!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }
        finally {

        }
    }

    void sampleComputation() {

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (isNumeric(Fc_1.getText().toString()) == true) {

                    lowerSpec.setEnabled(true);
                    upperSpec.setEnabled(true);

                    List<Float> list = new ArrayList<Float>();
                    float sum = 0;
                    list.add(num1);
                    Collections.sort(list);

                    float average = (num1) / list.size();

                    final float min = list.get(0);
                    float max = list.get(list.size() - 1);


                    fc_Minimum.setText(String.valueOf(min));
                    fc_Maximum.setText(String.valueOf(max));
                    fc_Average.setText(String.valueOf(average));

                } else {
                    fc_Minimum.setText("");
                    fc_Average.setText("");
                    fc_Maximum.setText("");
                    lowerSpec.setEnabled(false);
                    upperSpec.setEnabled(false);

                    if (isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true) {
                        float num1 = Float.parseFloat(Fc_1.getText().toString());
                        float num2 = Float.parseFloat(Fc_2.getText().toString());
                        List<Float> list = new ArrayList<Float>();
                        float sum = 0;
                        list.add(num1);
                        list.add(num2);
                        Collections.sort(list);


                        float average = (num1 + num2) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        fc_Minimum.setText(String.valueOf(min));
                        fc_Maximum.setText(String.valueOf(max));
                        fc_Average.setText(String.valueOf(average));

                    } else {

                    }

                    if (isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true && isNumeric(Fc_3.getText().toString()) == true) {

                        float num1 = Float.parseFloat(Fc_1.getText().toString());
                        float num2 = Float.parseFloat(Fc_2.getText().toString());
                        float num3 = Float.parseFloat(Fc_3.getText().toString());
                        List<Float> list = new ArrayList<Float>();

                        list.add(num1);
                        list.add(num2);
                        list.add(num3);
                        Collections.sort(list);

                        float average = (num1 + num2 + num3) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        fc_Minimum.setText(String.valueOf(min));
                        fc_Maximum.setText(String.valueOf(max));
                        fc_Average.setText(String.valueOf(average));

                    } else {

                    }

                    if (isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true && isNumeric(Fc_3.getText().toString()) == true && isNumeric(Fc_4.getText().toString()) == true) {

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

                        float average = (num1 + num2 + num3 + num4) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        fc_Minimum.setText(String.valueOf(min));
                        fc_Maximum.setText(String.valueOf(max));
                        fc_Average.setText(String.valueOf(average));

                    } else {

                    }

                    if (isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true && isNumeric(Fc_3.getText().toString()) == true && isNumeric(Fc_4.getText().toString()) == true && isNumeric(Fc_5.getText().toString()) == true) {

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

                        float average = (num1 + num2 + num3 + num4 + num5) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        fc_Minimum.setText(String.valueOf(min));
                        fc_Maximum.setText(String.valueOf(max));
                        fc_Average.setText(String.valueOf(average));

                    } else {

                    }

                    if (isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true && isNumeric(Fc_3.getText().toString()) == true && isNumeric(Fc_4.getText().toString()) == true && isNumeric(Fc_5.getText().toString()) == true && isNumeric(Fc_6.getText().toString()) == true) {

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

                        float average = (num1 + num2 + num3 + num4 + num5 + num6) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);

                        fc_Minimum.setText(String.valueOf(min));
                        fc_Maximum.setText(String.valueOf(max));
                        fc_Average.setText(String.valueOf(average));

                    } else {

                    }

                    if (isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true && isNumeric(Fc_3.getText().toString()) == true && isNumeric(Fc_4.getText().toString()) == true && isNumeric(Fc_5.getText().toString()) == true && isNumeric(Fc_6.getText().toString()) == true && isNumeric(Fc_1.getText().toString()) == true) {

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

                        float average = (num1 + num2 + num3 + num4 + num5 + num6 + num7) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);

                        fc_Minimum.setText(String.valueOf(min));
                        fc_Maximum.setText(String.valueOf(max));
                        fc_Average.setText(String.valueOf(average));

                    } else {

                    }


                    if (isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true && isNumeric(Fc_3.getText().toString()) == true && isNumeric(Fc_4.getText().toString()) == true && isNumeric(Fc_5.getText().toString()) == true && isNumeric(Fc_6.getText().toString()) == true && isNumeric(Fc_7.getText().toString()) == true && isNumeric(Fc_8.getText().toString()) == true) {

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

                        float average = (num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        fc_Minimum.setText(String.valueOf(min));
                        fc_Maximum.setText(String.valueOf(max));
                        fc_Average.setText(String.valueOf(average));

                    } else {

                    }

                    if (isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true && isNumeric(Fc_3.getText().toString()) == true && isNumeric(Fc_4.getText().toString()) == true && isNumeric(Fc_5.getText().toString()) == true && isNumeric(Fc_6.getText().toString()) == true && isNumeric(Fc_7.getText().toString()) == true && isNumeric(Fc_8.getText().toString()) == true && isNumeric(Fc_9.getText().toString()) == true) {

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

                        float average = (num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        fc_Minimum.setText(String.valueOf(min));
                        fc_Maximum.setText(String.valueOf(max));
                        fc_Average.setText(String.valueOf(average));
                    } else {

                    }

                    if (isNumeric(Fc_1.getText().toString()) == true && isNumeric(Fc_2.getText().toString()) == true && isNumeric(Fc_3.getText().toString()) == true && isNumeric(Fc_4.getText().toString()) == true && isNumeric(Fc_5.getText().toString()) == true && isNumeric(Fc_6.getText().toString()) == true && isNumeric(Fc_7.getText().toString()) == true && isNumeric(Fc_8.getText().toString()) == true && isNumeric(Fc_9.getText().toString()) == true && isNumeric(Fc_10.getText().toString()) == true) {

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

                        float average = (num1 + num2 + num3 + num4 + num5 + num6 + num7 + num8 + num9 + num10) / list.size();

                        final float min = list.get(0);
                        float max = list.get(list.size() - 1);


                        fc_Minimum.setText(String.valueOf(min));
                        fc_Maximum.setText(String.valueOf(max));
                        fc_Average.setText(String.valueOf(average));
                    } else {

                    }
                }
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


   /* public void insert_funcheck()
    {

        String Checkpoints = fc_checkPoints.getText().toString();
        String Instrumentused = instrumentUsed.getText().toString();
        String Sampleunit = sammpleUnit.getText().toString();
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
        String Min = fc_Minimum.getText().toString();
        String Ave = fc_Average.getText().toString();
        String Max = fc_Maximum.getText().toString();
        String LowerSpec = lowerSpec.getText().toString();
        String UppperSpec = upperSpec.getText().toString();
        String Judgmnt = FC_Judgement.getText().toString();
        String Samplesize = ffcsampleSize.getText().toString();
        String Remm = fcremarks.getText().toString();


        if (Checkpoints.trim().equals(""))//dagdagan mo
        {
            Toast.makeText(getApplicationContext(), "Must input atleast 1 checkpoint!", Toast.LENGTH_LONG).show();
        }
        else {
            try {
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN2();

                String query = "INSERT INTO FunctionalCheck (invoice_no, goodsCode, checkpoints, instrument_used, sample_unit, sample1, sample2, sample3, sample4, sample5, sample6, sample7, sample8, sample9, sample10, minimum, average, maximum, lower_spec_limit, upper_spec_limit, judgement, remarks, MaterialCodeBoxSeqID) values ('"+ SapmpleActivityinlot.invoicenumholder+"', '"+SapmpleActivityinlot.materialholder+"', '"+Checkpoints+"','"+Instrumentused+"','"+Sampleunit+"','"+DC1+"','"+DC2+"','"+DC3+"','"+DC4+"','"+DC5+"','"+DC6+"','"+DC7+"','"+DC8+"','"+DC9+"','"+DC10+"','"+Min+"','"+Ave+"','"+Max+"','"+LowerSpec+"','"+UppperSpec+"','"+Judgmnt+"','"+Remm+"','"+SapmpleActivityinlot.boxseqholder+"')";
                //Toast.makeText(this, String.valueOf(sampleSizeFC), Toast.LENGTH_LONG).show();
                String query1 = "UPDATE SampleSize SET function_sample_size = '"+ffcsampleSize.getText().toString()+"' WHERE id = '"+DimensionActivity.samplesize_id_hldr+"'";
                Statement stmt = con.createStatement();
                stmt.execute(query+query1);
                Toast.makeText(getApplicationContext(), "Successfully added!", Toast.LENGTH_SHORT).show();
                samplesizefc_id_hldr = Latest_ID("SampleSize");
                funcheck_id_hldr = Latest_ID("FunctionalCheck");





            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }*/

    public int Latest_ID(String tablename){
        int output = 0;



        try {
            connectionClass = new ConnectionClass();
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

    public void insert_sampleSize(){

        try{
            connectionClass = new ConnectionClass();
            Connection con = connectionClass.CONN2();
            String query1 = "INSERT INTO SampleSize (function_sample_size, goodsCode,invoice_number,MaterialCodeBoxSeqID) values ('"+ffcsampleSize.getText().toString()+"', '"+SapmpleActivityinlot.materialholder+"', '"+SapmpleActivityinlot.invoicenumholder+"', '"+SapmpleActivityinlot.boxseqholder+"')";
            Statement stmt = con.createStatement();
            stmt.execute(query1);
            samplesizefc_id_hldr = Latest_ID("SampleSize");
            funcheck_id_hldr = Latest_ID("FunctionalCheck");
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }


    public void getIntentData(){
        if(getIntent().hasExtra("id") &&  getIntent().hasExtra("getInstrumentUsed") && getIntent().hasExtra("getCheckpointdc") &&  getIntent().hasExtra("getSampleUnit") &&
                getIntent().hasExtra("getSampleSize") &&  getIntent().hasExtra("getSample1dc") &&
                getIntent().hasExtra("getSample2dc") &&  getIntent().hasExtra("getSample3dc") &&  getIntent().hasExtra("getSample4dc") &&   getIntent().hasExtra("getSample5dc") &&
                getIntent().hasExtra("getSample6dc") &&  getIntent().hasExtra("getSample7dc") &&  getIntent().hasExtra("getSample8dc") &&  getIntent().hasExtra("getSample9dc") &&
                getIntent().hasExtra("getSample10dc") &&   getIntent().hasExtra("getUpperSpecs") && getIntent().hasExtra("getLoweSpecs") &&
                getIntent().hasExtra("getMinimum") &&   getIntent().hasExtra("getAverage") &&  getIntent().hasExtra("getMaximum") &&  getIntent().hasExtra("getJudgement") &&  getIntent().hasExtra("getdatedim")
        ){


            id   = getIntent().getStringExtra("id");
            instrumentUsedstring = getIntent().getStringExtra("getInstrumentUsed");
            checkpoint   = getIntent().getStringExtra("getCheckpointdc");
            samplUnit   = getIntent().getStringExtra("getSampleUnit");
            samplenum   = getIntent().getStringExtra("getSampleSize");

            sample1   = getIntent().getStringExtra("getSample1dc");
            sample2   = getIntent().getStringExtra("getSample2dc");
            sample3   = getIntent().getStringExtra("getSample3dc");
            sample4   = getIntent().getStringExtra("getSample4dc");
            sample5   = getIntent().getStringExtra("getSample5dc");

            sample6   = getIntent().getStringExtra("getSample6dc");
            sample7   = getIntent().getStringExtra("getSample7dc");
            sample8   = getIntent().getStringExtra("getSample8dc");
            sample9   = getIntent().getStringExtra("getSample9dc");
            sample10   = getIntent().getStringExtra("getSample10dc");

            upper   = getIntent().getStringExtra("getUpperSpecs");
            lower   = getIntent().getStringExtra("getLoweSpecs");
            min   = getIntent().getStringExtra("getMinimum");
            average   = getIntent().getStringExtra("getAverage");
            max   = getIntent().getStringExtra("getMaximum");
            judgement   = getIntent().getStringExtra("getJudgement");
            gdatedim = getIntent().getStringExtra("getdatedim");


            sammpleUnit.setText(samplUnit);
            ffcsampleSize.setText(samplenum);


            instrumentUsed.setText(instrumentUsedstring);
            fc_checkPoints.setText(checkpoint);

            Fc_1.setText(sample1);
            Fc_2.setText(sample2);
            Fc_3.setText(sample3);
            Fc_4.setText(sample4);
            Fc_5.setText(sample5);
            Fc_6.setText(sample6);
            Fc_7.setText(sample7);
            Fc_8.setText(sample8);
            Fc_9.setText(sample9);
            Fc_10.setText(sample10);


            lowerSpec.setText(lower);
            upperSpec.setText(upper);

            fc_Minimum.setText(min);
            fc_Maximum.setText(max);
            fc_Average.setText(average);

            FC_Judgement.setText(judgement);

            dateTodayfc.setText(gdatedim);
        }
    }




}