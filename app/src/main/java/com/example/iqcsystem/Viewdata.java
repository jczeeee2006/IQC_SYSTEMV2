package com.example.iqcsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Viewdata extends AppCompatActivity{

    ConnectionClass connectionClass;

    Button Finish,buttonAdd;


    EditText reject, lotnumber1,lotquantity1;
    AutoCompleteTextView lot_invoiceno, tv_partname, goodsc, boxseqid, et_partnum;
    public static String invoicenumholder,materialholder, boxseqholder, partnameholder, partnumholder, latestID, lotnumholder;

    public int ctr=0;
    Dialog Logoutdialog;
    private  ConnectionClass connectionClassss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewdata);

        connectionClass = new ConnectionClass();

        reject = findViewById(R.id.lot_Reject);

        lotnumber1 = findViewById(R.id.lot_Number);
        lotquantity1 = findViewById(R.id.lot_Quantity);

        buttonAdd = findViewById(R.id.button_add);


        LotNumber();



        //log out button






        Finish = findViewById(R.id.btn_done);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ERROR MESSAGE FOR PART NUM
                if(lotnumber1.length()==0){
                    lotnumber1.setError("Enter Part Number");
                }
                // ERROR MESSAGE FOR TOTAL QUUANTITY
                else if(reject.length()==0)
                {
                    reject.setError("Enter Reject");
                    Toast.makeText(getApplicationContext(), "please fill up the required field",Toast.LENGTH_LONG).show();
                }

                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(Viewdata.this);
                    builder.setMessage("Are you sure you want to proceed?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            partnumholder = et_partnum.getText().toString();
                            partnameholder = tv_partname.getText().toString();
                            materialholder = goodsc.getText().toString();
                            invoicenumholder = lot_invoiceno.getText().toString();
                            boxseqholder = boxseqid.getText().toString();

                            Intent intent = new Intent(Viewdata.this, InspectionDetails.class);
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

            }
        });

    }




    public void LotNumber(){

        connectionClass = new ConnectionClass();
        try{

            Connection con = connectionClass.CONN();
            String query = "select lot_no from LotNumber ";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            final ArrayList<String> lotn = new ArrayList<String>();
            while (rs.next()){
                String lotnumber= rs.getString("lot_no");
                lotn.add(lotnumber);
            }
            final AutoCompleteTextView lotNumber = findViewById(R.id.lot_Number);
            final ArrayAdapter<String> invoice_array = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, lotn);
            lotNumber.setThreshold(1);
            lotNumber.setAdapter(invoice_array);

            lotNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();

                    lotNumber.setText(item);
                    lotnumholder = lotNumber.getText().toString();


                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }







    public void Logout(){
        Logoutdialog = new Dialog(Viewdata.this);
        Logoutdialog.setContentView(R.layout.alertlogout);

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Logoutdialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.logoutbackground));
        }

        Logoutdialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Logoutdialog.setCancelable(false);

        Button cancel = Logoutdialog.findViewById(R.id.btn_cancellogout);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logoutdialog.dismiss();
            }
        });

        Button confirm = Logoutdialog.findViewById(R.id.btn_confirmlogout);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class );
                startActivity(intent);
                finish();
                Toast.makeText(Viewdata.this, "Successfully Logged out!", Toast.LENGTH_SHORT).show();
            }
        });


        Logoutdialog.show();
    }

    @Override
    public void onBackPressed() {
        return;
    }





    public void getData()
    {
        boolean output = false;


        String Reject = reject.getText().toString();
        String lotNumber = lotnumber1.getText().toString();


        if (Reject.trim().equals("") || lotNumber.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Input  fields!", Toast.LENGTH_LONG).show();
            output = false;
        }
        else {

                try {

                    connectionClassss = new ConnectionClass();
                    Connection con = connectionClass.CONN();
                    String query = "UPDATE LotNumber  SET reject = ('" + lotnumber1.getText().toString() +"','" + Reject + "')";
                    Statement stmt = con.createStatement();
                    stmt.execute(query);
                    ctr++;
                    output = true;
                } catch (Exception e) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    output = false;
                }
            }
    }


}