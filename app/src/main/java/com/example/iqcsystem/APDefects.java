package com.example.iqcsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

public class APDefects extends AppCompatActivity {

    Dialog Successdialog, Faileddialog;
    Button APDefectsAdd, APDone;
    EditText APQuantity, APEncountered;
    ConnectionClass connectionClass;
    TextView Apdefects_invoiceno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apdefects);

        connectionClass = new ConnectionClass();
        Apdefects_invoiceno = findViewById(R.id.textView_invoiceno);

        String APinvoice = getIntent().getStringExtra("keyinvoice_no.");    //GETTING THE VALUE OF INVOICE NUMBER THAT IS PASS BY THE PREVIOUS FORM
        Apdefects_invoiceno.setText(APinvoice);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);



        APQuantity = findViewById(R.id.apdefectsquant);
        APEncountered = findViewById(R.id.apdefectsenc);

        APDefectsAdd = findViewById(R.id.btn_apApdefectsadd);
        APDefectsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert_defect_AP();
            }
        });

        APDone = findViewById(R.id.btn_apDone);
        APDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APDefects.super.onBackPressed();
            }
        });


    }
    public void reset()
    {
        APQuantity.setText("");
        APEncountered.setText("");
    }

    //-------------INSERT QUERY TO SQL DATABASE--------------------
    public void insert_defect_AP() {
        String Quantity = APQuantity.getText().toString();
        String Encountered = APEncountered.getText().toString();

        if (Quantity.trim().equals("") || Encountered.trim().equals(""))
            Toast.makeText(getApplicationContext(),"Fillup required fields!", Toast.LENGTH_LONG).show();
        else {


            try {
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN();

                String query = "UPDATE Appearance_Inspection SET defectqty = '"+Quantity+"', defect_enc = '"+Encountered+"' WHERE invoice_no = '"+LotNumber.invoicenumholder+"' AND id = '"+AppearanceInspection.appinspectioncheck_id_hldr+"'";
                Statement stmt = con.createStatement();
                stmt.execute(query);

                Toast.makeText(getApplicationContext(),"Successfully added!", Toast.LENGTH_SHORT).show();
                AlertSuccess();
                reset();

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(),ex.toString(), Toast.LENGTH_LONG).show();
                AlertFailed();
            }
        }
    }


    //-------------POPUP SUCCESS ALERT FUNCTION--------------
    public void AlertSuccess(){
        Successdialog = new Dialog(APDefects.this);
        Successdialog.setContentView(R.layout.alert_succesdefects);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Successdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.alertsuccessbackground));
        }

        Successdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Successdialog.setCancelable(false);

        Button ok = Successdialog.findViewById(R.id.btn_confirmlogout);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Successdialog.dismiss();
            }
        });

        Successdialog.show();
    }



    //-------------POPUP FAILED ALERT FUNCTION--------------
    public void AlertFailed(){
        Faileddialog = new Dialog(APDefects.this);
        Faileddialog.setContentView(R.layout.alert_faileddefects);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Faileddialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.alertsuccessbackground));
        }

        Faileddialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Faileddialog.setCancelable(false);

        Button ok = Faileddialog.findViewById(R.id.btn_confirmlogout);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Faileddialog.dismiss();
            }
        });

        Faileddialog.show();
    }
}
