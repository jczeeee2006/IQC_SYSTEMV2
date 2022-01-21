package com.example.iqcsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;


public class    LotNumber extends AppCompatActivity implements View.OnClickListener {

    ConnectionClass connectionClass;

    Button  Finish,buttonAdd;
    LinearLayout layoutlist;
    NestedScrollView scrollView;

    EditText totalquantity, quantityrecieved, lotno, lotquant, boxnum, reject, sampsize,lotnumber1,lotquantity1;
    AutoCompleteTextView lot_invoiceno, tv_partname, goodsc, boxseqid, et_partnum;
    public static String invoicenumholder,materialholder, boxseqholder, partnameholder, partnumholder, latestID;

    public int ctr=0;
    Dialog Logoutdialog;
    private  ConnectionClass connectionClassss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lot_number);

        connectionClass = new ConnectionClass();
        boxseqid = findViewById(R.id.boxseq);
        lot_invoiceno = findViewById(R.id.et_invoiceno);
        totalquantity = findViewById(R.id.lot_totalquant);
        et_partnum =  findViewById(R.id.lot_partno);
        goodsc = findViewById(R.id.lot_goodc);
        tv_partname = findViewById(R.id.lot_partname);
        quantityrecieved = findViewById(R.id.lot_quant_recieved);
        lotno = findViewById(R.id.lot_number);
        lotquant = findViewById(R.id.lot_quantity);
        boxnum = findViewById(R.id.lot_boxnumber);
        reject = findViewById(R.id.lot_reject);
        sampsize = findViewById(R.id.lot_sampsize);
        lotnumber1 = findViewById(R.id.lot_Number);
        lotquantity1 = findViewById(R.id.lot_Quantity);
        layoutlist = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.button_add);
        scrollView = findViewById(R.id.scrollView);
        buttonAdd.setOnClickListener(this);
        quantityrecieved.setEnabled(false);


        PartNumber();
        latestID = String.valueOf((Latest_ID("LotNumber")+1));
        addView();

        //log out button
        ImageView logout = findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();

            }
        });


        et_partnum.addTextChangedListener(new TextWatcher()
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
                    boxseqid.setText(et_partnum.getText().toString() + "-" + latestID);
                }

                else
                {

                }
            }
        });


        //next buttton
        Finish = findViewById(R.id.btn_done);
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ERROR MESSAGE FOR PART NUM
                if(et_partnum.length()==0){
                    et_partnum.setError("Enter Part Number");
                }
                // ERROR MESSAGE FOR TOTAL QUUANTITY
                else if( totalquantity.length()==0){
                    totalquantity.setError("Enter Total Quantity");
                }
                else if(quantityrecieved.length()==0)
                {
                  Toast.makeText(getApplicationContext(), "please fill up the required field",Toast.LENGTH_LONG).show();
                }

                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(LotNumber.this);
                    builder.setMessage("Are you sure you want to proceed?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            partnumholder = et_partnum.getText().toString();
                            partnameholder = tv_partname.getText().toString();
                            materialholder = goodsc.getText().toString();
                            invoicenumholder = lot_invoiceno.getText().toString();
                            boxseqholder = boxseqid.getText().toString();

                            Intent intent = new Intent(LotNumber.this, InspectionDetails.class);
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




    public void PartNumber(){

        connectionClass = new ConnectionClass();
      try{

          Connection con2 = connectionClass.CONN2();
          String query = "select DISTINCT (Part_Number) from tblInventory_MaterialDetails WHERE (Part_Number IS NOT NULL)";
          PreparedStatement stmt = con2.prepareStatement(query);
          ResultSet rs = stmt.executeQuery();

          final ArrayList<String> partn = new ArrayList<String>();
          while (rs.next()){
              String invoicenumber = rs.getString("Part_Number");
              partn.add(invoicenumber);
          }
          final AutoCompleteTextView partNumber = findViewById(R.id.lot_partno);
          final ArrayAdapter<String> invoice_array = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, partn);
          partNumber.setThreshold(1);
          partNumber.setAdapter(invoice_array);

            partNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();

                    partNumber.setText(item);

                    partnumholder = partNumber.getText().toString();
                    tv_partname.setText(nameAndGoodscode(partNumber.getText().toString(),"Part_Name"));
                    partnameholder = tv_partname.getText().toString();
                    goodsc.setText(nameAndGoodscode(partNumber.getText().toString(), "Goods_Code"));
                    materialholder = goodsc.getText().toString();
                    invoicenumholder = lot_invoiceno.getText().toString();
                    boxseqid.setText(et_partnum.getText().toString() + "-"+latestID);
                    boxseqholder = boxseqid.getText().toString();
                }
            });

      }catch (Exception e){
          e.printStackTrace();
      }
    }


    //-------------------------FILL PARTNAME------------------------
    public String nameAndGoodscode(String partno, String selectedCol)
    {
        String output = "";
        connectionClass = new ConnectionClass();
        try {
            Connection con2 = connectionClass.CONN2();//open ng connection sa connection class
            String query = "SELECT "+selectedCol+" FROM tblInventory_MaterialDetails WHERE Part_Number = '"+partno+"'";
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String generatedItem = rs.getString(selectedCol);
                output = generatedItem;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return output;
    }

    //-----------------------------SPIN MATERIAL NAME---------------------
    public void spinmatname(){

        final AutoCompleteTextView epartname = findViewById(R.id.lot_partname);
        connectionClass = new ConnectionClass();

        try{
            Connection con2 = connectionClass.CONN2();//open ng connection sa connection class
            String query = "select DISTINCT (MaterialName) from tblInventory_WHReceived WHERE InvoiceNumber = '"+invoicenumholder+"' AND MaterialCode_TOSS = '"+materialholder+"'";
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> invoice = new ArrayList<String>();
            while (rs.next()){
                String invoicenumber = rs.getString("MaterialName");
                invoice.add(invoicenumber);
            }

            final ArrayAdapter<String> invoice_array = new ArrayAdapter(this, android.R.layout.simple_spinner_item, invoice);
            epartname.setAdapter(invoice_array);

        }catch (Exception ex){
            ex.printStackTrace();
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

    public void Logout(){
        Logoutdialog = new Dialog(LotNumber.this);
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
                Toast.makeText(LotNumber.this, "Successfully Logged out!", Toast.LENGTH_SHORT).show();
            }
        });


        Logoutdialog.show();
    }

    @Override
    public void onBackPressed() {
        return;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_add:
                if (ctr > -1)
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LotNumber.this);
                    builder.setMessage("Are you sure you want to proceed?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (getData() == true) {
                                addView();
                            }
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
                else
                {
                    Toast.makeText(this, "Input valid and complete data!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    private void addView(){


        final View lot_Add = getLayoutInflater().inflate(R.layout.row_add_lot,null,false);

        EditText editText = lot_Add.findViewById(R.id.lot_Number);
        EditText editText1 = lot_Add.findViewById(R.id.lot_Quantity);
        EditText editText2 = lot_Add.findViewById(R.id.lot_remarks);
        editText.setHint("Enter Lot Number ("+(ctr+1)+")");
        editText1.setHint("Enter Lot Qty ("+(ctr+1)+")");
        editText2.setHint("Enter Remarks ("+(ctr+1)+")");
        layoutlist.addView(lot_Add);


        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {

                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                editText.requestFocus();
            }
        },1000);


    }

    public boolean getData()
    {

        boolean output = false;

        String Invoice_no = lot_invoiceno.getText().toString();
        String BoxSeq = boxseqid.getText().toString();
        String Part_no = et_partnum.getText().toString();
        String Part_name = tv_partname.getText().toString();
        String GoodsCode = goodsc.getText().toString();
        String TotalQuant = totalquantity.getText().toString();
        String QuantRecieved = quantityrecieved.getText().toString();
        String BoxNum  = boxnum.getText().toString();
        String Reject = reject.getText().toString();
        String SampleSize = sampsize.getText().toString();

        if (BoxSeq.trim().equals("") ||Invoice_no.trim().equals("") || Part_no.trim().equals("")||Part_name.trim().equals("")||GoodsCode.trim().equals("")||BoxNum.trim().equals("")||TotalQuant.trim().equals("")||BoxNum.trim().equals("")||SampleSize.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Input all fields!", Toast.LENGTH_LONG).show();
            output = false;
        }
        else {
            View viewChild = layoutlist.getChildAt(ctr);
            EditText lotNo = viewChild.findViewById(R.id.lot_Number);
            EditText lotQty = viewChild.findViewById(R.id.lot_Quantity);
            EditText remarks = viewChild.findViewById(R.id.lot_remarks);
            if (!lotNo.getText().toString().equals("") && !lotQty.getText().toString().equals("")) {
                try {
                    if (quantityrecieved.getText().toString().equals("")) {
                        quantityrecieved.setText(String.valueOf(0 + Integer.parseInt(lotQty.getText().toString())));
                        QuantRecieved = quantityrecieved.getText().toString();
                    } else {
                        quantityrecieved.setText(String.valueOf(Integer.parseInt(QuantRecieved) + Integer.parseInt(lotQty.getText().toString())));
                        QuantRecieved = quantityrecieved.getText().toString();
                    }
                    connectionClassss = new ConnectionClass();
                    Connection con = connectionClass.CONN();
                    String query = "INSERT INTO LotNumber (invoice_no, part_no, part_name, total_quantity, quantity_recieved,lot_no, lot_quantity, box_number,reject,sample_size, goodsCode, MaterialCodeBoxSeqID, remarks) values ('" + Invoice_no + "','" + Part_no + "','" + Part_name + "','" + TotalQuant + "','" + QuantRecieved + "','" + lotNo.getText().toString() + "','" + lotQty.getText().toString() + "','" + BoxNum + "','" + Reject + "','" + SampleSize + "', '" + GoodsCode + "', '" + BoxSeq + "',  '" + remarks.getText().toString() + "')";
                    Statement stmt = con.createStatement();
                    stmt.execute(query);
                    ctr++;
                    output = true;
                } catch (Exception e) {
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    output = false;
                }
            }
            else
            {
                Toast.makeText(this, "Input Lot Number / Lot Qty", Toast.LENGTH_SHORT).show();
                output = false;
            }
        }
        return output;
    }
    public void removeView(View view) {
        layoutlist.removeView(view);
    }
}
