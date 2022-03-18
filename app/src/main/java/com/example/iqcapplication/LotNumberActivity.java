package com.example.iqcapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.iqcapplication.add.InspectionDetailsActivity;
import com.example.iqcapplication.dataview.LotFormActivityview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class LotNumberActivity extends AppCompatActivity {

    ConnectionClass connectionClass;
    Button button,buttonAdd,buttonShow,nextForm;

    EditText totalquantity, quantityrecieved, lotno, lotquant, boxnum, reject, sampsize, boxseqid,remarks;
    AutoCompleteTextView lot_invoiceno, tv_partname, goodsc, et_partnum;
    int ctr= 0;
    public static String invoicenumholder,materialholder, boxseqholder, partnameholder,  partnumholder, latestID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lot_number);

        boxseqid = findViewById(R.id.boxSequence);
        lot_invoiceno = findViewById(R.id.invoiceNum);
        totalquantity = findViewById(R.id.totalQuan_txt);
        et_partnum = findViewById(R.id.partN);
        goodsc = findViewById(R.id.goodsCode);
        tv_partname =findViewById(R.id.partName);
        quantityrecieved = findViewById(R.id.atctualQuant);
        lotno = findViewById(R.id.lotNumber);
        lotquant = findViewById(R.id.lotQuant);
        boxnum = findViewById(R.id.boxNum);
        reject = findViewById(R.id.reject);
        sampsize = findViewById(R.id.sampleSize);
        remarks   = findViewById(R.id.remarks);
        buttonAdd = findViewById(R.id.addlotbutton);
        button = findViewById(R.id.button);
        buttonShow = findViewById(R.id.showbutton);
        nextForm = findViewById(R.id.nextForm);
        connectionClass = new ConnectionClass();
        quantityrecieved.setEnabled(false);
        boxseqid.setEnabled(false);
        latestID = String.valueOf((Latest_ID("LotNumber")+1));
        Query();
        addData();

        nextForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LotNumberActivity.this, InspectionDetailsActivity.class);
                startActivity(intent);
            }
        });

        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LotNumberActivity.this, LotFormActivityview.class);
                startActivity(intent);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDatatoSQLite();
            }
        });

    }


    public void addData(){
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.addlotbutton:
                            if (getData() == true) {

                            }
                    }
            }
        });
    }


    //--------------GENERATING OF SPECIFIC FIELDS FROM PART NUMBER----------------//
    public  void Query(){
        connectionClass = new ConnectionClass();
        try{
            Connection conn2 = connectionClass.CONN();
            String query = "SELECT DISTINCT (Part_Number) from tblInventory_MaterialDetails ";
            PreparedStatement stmt = conn2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            final ArrayList<String> partn = new ArrayList<String>();
            while (rs.next()) {
                String partNum = rs.getString("Part_Number");
                partn.add(partNum);
            }
            final AutoCompleteTextView partNumber = findViewById(R.id.partN);
            final ArrayAdapter<String> partn_array = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, partn);
            partNumber.setThreshold(1);
            partNumber.setAdapter(partn_array);

            partNumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    //-------SET THE DATA FROM DB TO PARTNUMBER TEXTBOX
                    partNumber.setText(item);

                    tv_partname.setText(nameandGoodsCode(partNumber.getText().toString(),"Part_Name"));
                    partnameholder = tv_partname.getText().toString();
                    goodsc.setText(nameandGoodsCode(partNumber.getText().toString(), "Goods_Code"));
                    materialholder = goodsc.getText().toString();
                    boxseqid.setText(et_partnum.getText().toString() + "-"+latestID);
                    boxseqholder = boxseqid.getText().toString();

                }
            });


        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //-----------------------GENERATING OF PARTNAME AND GOODS CODE DEPENDING ON PARTNUMBER-------------------//
    public String nameandGoodsCode(String partno, String selectedCol){

        String output = "";
        connectionClass = new ConnectionClass();
        try{
            Connection con2 = connectionClass.CONN2();
            String query = "SELECT "+selectedCol+" FROM tblInventory_MaterialDetails WHERE Part_Number = '"+partno+"'";
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String generatedItem = rs.getString(selectedCol);

                output = generatedItem;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return output;
    }

        //----GET THE LATEST ID TO THE TOP -------//
    public int Latest_ID(String tablename){
        int output = 0;
        connectionClass = new ConnectionClass();
        try{
            Connection con = connectionClass.CONN();//open ng connection sa connection class
            String query = "SELECT TOP 1 id FROM "+tablename+" ORDER BY id DESC";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                output = id;
            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT);
        }
        return output;
    }

    public boolean getData(){
        boolean output = false;
        String Invoice_no = lot_invoiceno.getText().toString();
        String BoxSeq = boxseqid.getText().toString();
        String Part_no = et_partnum.getText().toString();
        String Part_name = tv_partname.getText().toString();
        String GoodsCode = goodsc.getText().toString();
        String TotalQuant = totalquantity.getText().toString();
        String QuantRecieved = quantityrecieved.getText().toString();
        String BoxNum  = boxnum.getText().toString();
        String SampleSize = sampsize.getText().toString();


        if (BoxSeq.trim().equals("") ||Invoice_no.trim().equals("") || Part_no.trim().equals("")||Part_name.trim().equals("")||GoodsCode.trim().equals("")||BoxNum.trim().equals("")||TotalQuant.trim().equals("")||BoxNum.trim().equals("")||SampleSize.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Input all fields!", Toast.LENGTH_LONG).show();
            output = false;
        }else{

            if(!lotno.getText().toString().equals("") && !lotquant.getText().toString().equals("")){
                try{
                    if(quantityrecieved.getText().toString().equals("")){
                        quantityrecieved.setText(String.valueOf(0 + Integer.parseInt(lotquant.getText().toString())));
                        QuantRecieved = quantityrecieved.getText().toString();
                    }else{
                        quantityrecieved.setText(String.valueOf(Integer.parseInt(QuantRecieved) + Integer.parseInt(lotquant.getText().toString())));
                        QuantRecieved = quantityrecieved.getText().toString();
                    }
                }catch (Exception e){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    output = false;
                }
            }
        }

        return output;
    }


    public void addDatatoSQLite(){
        try{
            if(!lotquant.getText().toString().equals("")){

                DatabaseHelper myDB = new DatabaseHelper(LotNumberActivity.this);
                myDB.addData(
                        lot_invoiceno.getText().toString().trim(),
                        et_partnum.getText().toString().trim(),
                        goodsc.getText().toString().trim(),
                        tv_partname.getText().toString().trim(),
                        boxnum.getText().toString().trim(),
                        boxseqid.getText().toString().trim(),
                        quantityrecieved.getText().toString().trim(),
                        totalquantity.getText().toString().trim(),
                        reject.getText().toString().trim(),
                        sampsize.getText().toString().trim(),
                        lotno.getText().toString().trim(),
                        lotquant.getText().toString().trim(),
                        remarks.getText().toString().trim());


                LayoutInflater mylayoutInflator = getLayoutInflater();
                View view1 = mylayoutInflator.inflate(R. layout.showpopup,null);
                Toast toast=new Toast (getApplicationContext ());
                toast.setView(view1);
                toast.setDuration (Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
                toast.show();
                }
            else {
                Toast.makeText(LotNumberActivity.this, "please fill up data", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

}