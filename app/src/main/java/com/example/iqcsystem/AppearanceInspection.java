package com.example.iqcsystem;



import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;

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
import java.util.Date;
import java.util.List;


public class    AppearanceInspection extends AppCompatActivity {

    Dialog Successdialog, Faileddialog, AddnewDialog;

    Button APAdd, APAddDefects, APNext, APBack,ApOK,ApNg,okk, ngg;
    ConnectionClass connectionClass;

    EditText App_IGCheckpoints, App_Instrumentused, App_Remarks,apsamplesize;
    
    TextView App_Judgement;

    public ArrayAdapter apinstrument_adapter;
    Spinner ap_spinInstrument;

    public static int appinspectioncheck_id_hldr =0;
    public List<Integer> id_list = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appearance_inspection);


        ap_spinInstrument = findViewById(R.id.instrumentspin);

        connectionClass = new ConnectionClass();



        okk = findViewById(R.id.btn_ok);
        ngg = findViewById(R.id.btn_ng);
        apsamplesize = findViewById(R.id.ap_sampsize);
        App_IGCheckpoints = findViewById(R.id.ap_igcheckpoints);
        App_Instrumentused = findViewById(R.id.ap_instrument);
        App_Judgement = findViewById(R.id.ap_judgement);
        App_Remarks = findViewById(R.id.ap_result2);
        apinstrument_adapter = ArrayAdapter.createFromResource(this, R.array.instrument_array1, android.R.layout.simple_spinner_item);
        apinstrument_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            ap_spinInstrument.setAdapter(apinstrument_adapter);
        ap_spinInstrument.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#eeeeee"));
                ((TextView) parent.getChildAt(0)).setTextSize(5);
                ap_instrument_adding();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        APAddDefects = findViewById(R.id.btn_apadddefects);
        APAddDefects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              APAdddefects();

            }
        });

        APNext = findViewById(R.id.btn_done);
        APNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               Next();
            }
        });


        ApOK = findViewById(R.id.btn_ok);
        ApOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ApNg = findViewById(R.id.btn_ng);
        ApNg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        okk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert_appearanceinspection("OK");
                reset();
            }
        });

        ngg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert_appearanceinspection("NG");
                reset();
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if(App_Judgement.getText().toString().equals("PASSED")||App_Judgement.getText().toString().equals("passed")||App_Judgement.getText().toString().equals("Passed")){
                    App_Judgement.setTextColor(Color.parseColor("#58f40b"));
                }

                else  if(App_Judgement.getText().toString().equals("Failed")||App_Judgement.getText().toString().equals("FAILED")||App_Judgement.getText().toString().equals("failed")){
                    App_Judgement.setTextColor(Color.parseColor("#d7282c"));
                }

                else {
                    App_Judgement.setTextColor(Color.parseColor("#000000"));
                }
            }
        };

        App_Judgement.addTextChangedListener(textWatcher);



    }



    public void setDate (TextView view){

        Date today = Calendar.getInstance().getTime();//getting date
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");//formating according to my need
        String date = formatter.format(today);
        view.setText(date);
    }

    public void ap_instrument_adding()
    {
        String instru = ap_spinInstrument.getSelectedItem().toString();
       App_Instrumentused.setEnabled(false);
        if (instru.equals("")) {
            App_Instrumentused.setText("");
        }

        if (instru.equals("MC-Microscope")) {
            App_Instrumentused.setText("MC-Microscope");
        }
        if (instru.equals("NE-Naked Eye")) {
            App_Instrumentused.setText("NE-Naked Eye");
        }

        if (instru.equals("MR-Magnifier")) {
            App_Instrumentused.setText("MR-Magnifier");

        }

        if (instru.equals("GN-Go nogo Jig")) {
            App_Instrumentused.setText("GN-Go nogo Jig");

        }
    }

    public void reset()
    {
        //apsamplesize.setText("");
        App_IGCheckpoints.setText("");
        App_Judgement.setText("");
        App_Remarks.setText("");

    }

    public void insert_appearanceinspection(String Result)
    {
        String Sample_size = apsamplesize.getText().toString();
        String IGCheckpoints = App_IGCheckpoints.getText().toString();
        String Instrumentused = App_Instrumentused.getText().toString();
        //String Result = App_Result.getText().toString();
        //String Judgement = App_Judgement.getText().toString();
        String Remarkss = App_Remarks.getText().toString();

        if (IGCheckpoints.trim().equals("")||Instrumentused.trim().equals("")|| Sample_size.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Fill up all fields!", Toast.LENGTH_LONG).show();
        }
        else {
            try {
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN();

                String query = "INSERT INTO Appearance_Inspection ( invoice_no, ig_checkpoints, instrument_used, result, remarks, goodsCode,MaterialCodeBoxSeqID) values ('"+LotNumber.invoicenumholder+"','"+IGCheckpoints+"','"+Instrumentused+"','"+Result+"','"+Remarkss+"','"+LotNumber.materialholder+"','"+LotNumber.boxseqholder+"')";
                String query1 = "UPDATE SampleSize SET appearance_sample_size = '"+Sample_size+"' WHERE id = '"+DimensionalCheck.samplesize_id_hldr+"'";
                Statement stmt = con.createStatement();
                stmt.execute(query+query1);

                Toast.makeText(getApplicationContext(), "Successfully added!",Toast.LENGTH_LONG).show();
                appinspectioncheck_id_hldr = Latest_ID();
                id_list.add(Latest_ID());
                //AlertSuccess();
                }
            catch (Exception ex) {
                //AlertFailed();
                Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void update_judgement()
    {
        String Judgement = App_Judgement.getText().toString();

            try {
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN();

                for(int x = 0; x<id_list.size(); x++) {
                String query1 = "UPDATE Appearance_Inspection SET judgement = '" + Judgement + "' WHERE id = '" + id_list.get(x) + "'";
                Statement stmt = con.createStatement();
                stmt.execute(query1);
                }
                Toast.makeText(getApplicationContext(), "Successfully updated!", Toast.LENGTH_LONG).show();
                appinspectioncheck_id_hldr = Latest_ID();
                id_list.add(Latest_ID());
               // AlertSuccess();



            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                AlertFailed();
            }

    }

    public void AlertSuccess(){
        Successdialog = new Dialog(AppearanceInspection.this);
        Successdialog.setContentView(R.layout.alertsucces);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
        {
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
        Faileddialog = new Dialog(AppearanceInspection.this);
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
        AddnewDialog = new Dialog(AppearanceInspection.this);
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

    public void Next()
    {
            if(App_Judgement.getText().toString().equals("PASSED")  || App_Judgement.getText().toString().equals("FAILED"
            ))
            {

                //Toast.makeText(getApplicationContext(),"pumasok",Toast.LENGTH_LONG).show();
                update_judgement();
                AlertDialog.Builder builder = new AlertDialog.Builder(AppearanceInspection.this);
                builder.setMessage("Are you sure you want to proceed?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(AppearanceInspection.this, FunctionalCheck.class);
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
            else{
                //App_Judgement.setText("");
                App_Judgement.setError("Judgement must be PASSED or FAILED!");
            }


    }





    public void APAdddefects(){

        Intent intent = new Intent(AppearanceInspection.this, APDefects.class);
        startActivity(intent);

    }

    public int Latest_ID()
    {
        int output = 0;

        connectionClass = new ConnectionClass();

        try {
            Connection con = connectionClass.CONN();//open ng connection sa connection class
            String query = "SELECT TOP 1 id FROM Appearance_Inspection ORDER BY id DESC";
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
    @Override
    public void onBackPressed() {
        return;
    }
}
