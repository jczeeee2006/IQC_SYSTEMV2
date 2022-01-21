package com.example.iqcsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.AsyncTask;
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
import java.sql.ResultSet;
import java.sql.Statement;

public class FCDefects extends AppCompatActivity {

    Dialog Successdialog, Faileddialog;
    Button FCDefectsAdd, FCDone;
    EditText FCQuantity, FCEncountered;
    ConnectionClass connectionClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcdefects);

        connectionClass = new ConnectionClass();
        //----------------------------POPUP WINDOWS------------------------------------

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

        FCQuantity = findViewById(R.id.fcdefectsquant);
        FCEncountered = findViewById(R.id.fcdefectsenc);

        FCDefectsAdd = findViewById(R.id.btn_fcFcdefectsadd);
        FCDefectsAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            insert_defect_FC();
            }
        });

        FCDone = findViewById(R.id.btn_fcDone);
        FCDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FCDefects.super.onBackPressed();
            }
        });



    }


    public void insert_defect_FC() {
        String Quantity = FCQuantity.getText().toString();
        String Encountered = FCEncountered.getText().toString();

        if (Quantity.trim().equals("") || Encountered.trim().equals(""))
            Toast.makeText(getApplicationContext(),"Fillup required fields!", Toast.LENGTH_LONG).show();
        else {

            try {
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN();

                String query = "UPDATE FunctionalCheck SET defectqty = '" + Quantity + "', defect_enc = '" + Encountered + "' WHERE invoice_no = '" + LotNumber.invoicenumholder + "' AND id = '" + FunctionalCheck.fccheck_id_hldr + "'";
                Statement stmt = con.createStatement();
                stmt.execute(query);

                Toast.makeText(getApplicationContext(),"Successfully added!", Toast.LENGTH_SHORT).show();
                AlertSuccess();

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(),ex.toString(), Toast.LENGTH_LONG).show();
                AlertFailed();
            }
        }
    }


    public void AlertSuccess(){
        Successdialog = new Dialog(FCDefects.this);
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


    public void AlertFailed(){
        Faileddialog = new Dialog(FCDefects.this);
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
