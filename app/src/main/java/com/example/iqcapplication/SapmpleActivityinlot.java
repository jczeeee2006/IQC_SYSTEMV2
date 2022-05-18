package com.example.iqcapplication;

import static com.example.iqcapplication.MainActivity.connectionClass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.iqcapplication.Update.LotFormActivity;
import com.example.iqcapplication.add.InspectionDetailsActivity;
import com.example.iqcapplication.encapsulation.LotEncapsulation;
import com.example.iqcapplication.fragments.FragmentforLot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SapmpleActivityinlot extends AppCompatActivity  {
    Button firstFragButton;
    ConnectionClass connectionClass;
    Button button,buttonAdd,buttonShow,nextForm,clearDatA, uploadSaServer,deleteRecords;
    public static EditText totalquantity, quantityrecieved, lotno, lotquant, boxnum, reject, sampsize, boxseqid,remarks,dateToday;
    public static AutoCompleteTextView lot_invoiceno, tv_partname, goodsc, et_partnum;

    ArrayList<LotEncapsulation> lotData = new ArrayList<>();

    ImageButton helpButton;

    public static String goodscodeholder, invoicenumholder,materialholder, boxseqholder, partnameholder,  partnumholder, latestID,dateholder;
    private  ConnectionClass connectionClassss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        replaceFragment(new FragmentforLot());

        setContentView(R.layout.activity_sapmple_activityinlot);


        Fragment fragment= new FragmentforLot();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        firstFragButton = findViewById(R.id.showbutton);
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
        buttonShow = findViewById(R.id.showbutton);
        helpButton = findViewById(R.id.helpButton);
        nextForm = findViewById(R.id.nextForm);
        clearDatA = findViewById(R.id.clearData);
        dateToday  = findViewById(R.id.dateToday);
        uploadSaServer = findViewById(R.id.uploadsaServer);
        deleteRecords = findViewById(R.id.deleteAllRecords);
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String noww = df.format(new Date());
        dateToday.setText(noww);

        connectionClass = new ConnectionClass();
        quantityrecieved.setEnabled(false);
        boxseqid.setEnabled(false);

        latestID = String.valueOf((Latest_ID("LotNumber")+1));


       // Query();
        //GoodsCodelist()

        boxseqid.setText(LotFormActivity.boxseqholder);
        lot_invoiceno.setText(LotFormActivity.invoiceholder);
        totalquantity.setText(LotFormActivity.totalquantholder);
        tv_partname.setText(LotFormActivity.partnameholder);
        et_partnum.setText(LotFormActivity.partnumholder);
        goodsc.setText(LotFormActivity.goodscodeholder);
        lotquant.setText(LotFormActivity.lotQuantityholder);
        lotno.setText(LotFormActivity.lotnumholder);
        boxnum.setText(LotFormActivity.boxnumholder);
        sampsize.setText(LotFormActivity.samplesizeHolder);
        quantityrecieved.setText(LotFormActivity.actualquantityHolder);
        reject.setText(LotFormActivity.rejectHolderr);
        LotAsync lotAsync = new LotAsync();
        lotAsync.execute("");
        buttonList();



    }

    public void buttonList(){
        nextForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog2();
            }
        });

        clearDatA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog3();
            }
        });



        buttonShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentforLot());
            }
        });




        uploadSaServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog5();
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myCustomDalog();
            }
        });

        deleteRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog10();
            }
        });


    }

    public void myCustomDalog(){
        final Dialog MyDialog = new Dialog(SapmpleActivityinlot.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.customdialog);

        MyDialog.show();
    }


    public void onPause(){
        super.onPause();
        String toStore = et_partnum.getText().toString();
        getSharedPreferences("PREFERENCE",MODE_PRIVATE).edit().putString("KEY", toStore);
    }


    public void onResume(){
        super.onResume();
        String toStore = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("KEY", et_partnum.getText().toString());
        et_partnum.setText(toStore);
    }


    private void replaceFragment(FragmentforLot lotFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,lotFragment);
        fragmentTransaction.commit();

    }


    public  class LotAsync extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {

            Query();
            GoodsCodelist();
            partnumList();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }

        @Override
        protected String doInBackground(String... strings) {


            return null;
        }
    }


    //--------------GENERATING OF SPECIFIC FIELDS FROM PART NUMBER----------------//
    public String Query(){

        connectionClass = new ConnectionClass();
        String z = "";
        try{
            Connection conn2 = connectionClass.CONN4();
            String query = "SELECT DISTINCT (INVOICE) from Receive";
            PreparedStatement stmt = conn2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            final ArrayList<String> inv = new ArrayList<String>();
            while (rs.next()) {
                String invoice = rs.getString("INVOICE");
                inv.add(invoice);


            }
            final AutoCompleteTextView   lot_invoiceno = findViewById(R.id.invoiceNum);
            final ArrayAdapter<String> partn_array = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, inv);
            lot_invoiceno.setThreshold(1);
            lot_invoiceno.setAdapter(partn_array);

            lot_invoiceno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    //-------SET THE DATA FROM DB TO PARTNUMBER TEXTBOX
                    lot_invoiceno.setText(item);
                    lot_invoiceno.showDropDown();
                    tv_partname.setText(nameandGoodsCode(lot_invoiceno.getText().toString(),"PART_NAME"));
                    partnameholder = tv_partname.getText().toString();
                    goodsc.setText(nameandGoodsCode(lot_invoiceno.getText().toString(), "GOODS_CODE"));
                    materialholder = goodsc.getText().toString();
                    et_partnum.setText(nameandGoodsCode(lot_invoiceno.getText().toString(), "PART_NUMBER"));
                    partnumholder = et_partnum.getText().toString();
                    boxseqid.setText(et_partnum.getText().toString() + "-"+latestID);
                    boxseqholder = boxseqid.getText().toString();

                }
            });

            lot_invoiceno.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lot_invoiceno.showDropDown();
                }
            });


        }catch (Exception e){
            z = e.toString();

        }
        return z;
    }

    public String GoodsCodelist(){

        connectionClass = new ConnectionClass();
        String z = "";
        try{

            Connection conn2 = connectionClass.CONN4();
            String query = "SELECT DISTINCT (GOODS_CODE) from Receive ";
            PreparedStatement stmt = conn2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            final ArrayList<String> inv = new ArrayList<String>();
            while (rs.next()) {
                String invoice = rs.getString("GOODS_CODE");
                inv.add(invoice);
            }

            final AutoCompleteTextView   goodsc = findViewById(R.id.goodsCode);
            final ArrayAdapter<String> partn_array = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, inv);
            goodsc.setThreshold(1);
            goodsc.setAdapter(partn_array);

            goodsc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    //-------SET THE DATA FROM DB TO PARTNUMBER TEXTBOX
                    goodsc.setText(item);

                    et_partnum.setText(invandgoodscode(goodsc.getText().toString(), "PART_NUMBER"));
                    partnumholder = et_partnum.getText().toString();
                    lot_invoiceno.setText(invandgoodscode(goodsc.getText().toString(), "INVOICE"));
                    invoicenumholder = et_partnum.getText().toString();
                    tv_partname.setText(invandgoodscode(goodsc.getText().toString(),"PART_NAME"));
                    partnameholder = tv_partname.getText().toString();

                    boxseqid.setText(et_partnum.getText().toString() + "-"+latestID);
                    boxseqholder = boxseqid.getText().toString();
                }
            });

            goodsc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goodsc.showDropDown();
                }
            });
        }catch (Exception e){
                z = e.toString();
        }
        return z;
    }


    public String partnumList(){

        connectionClass = new ConnectionClass();
        String z = "";
        try{

            Connection conn2 = connectionClass.CONN4();
            String query = "SELECT DISTINCT (PART_NUMBER) from Receive ";
            PreparedStatement stmt = conn2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            final ArrayList<String> inv = new ArrayList<String>();
            while (rs.next()) {
                String invoice = rs.getString("PART_NUMBER");
                inv.add(invoice);
            }

            final AutoCompleteTextView   partN = findViewById(R.id.partN);
            final ArrayAdapter<String> partn_array = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, inv);
            partN.setThreshold(1);
            partN.setAdapter(partn_array);

            partN.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    //-------SET THE DATA FROM DB TO PARTNUMBER TEXTBOX
                    partN.setText(item);

                    goodsc.setText(partNum(partN.getText().toString(), "GOODS_CODE"));
                    partnumholder = et_partnum.getText().toString();
                    lot_invoiceno.setText(partNum(partN.getText().toString(), "INVOICE"));
                    invoicenumholder = et_partnum.getText().toString();
                    tv_partname.setText(partNum(partN.getText().toString(),"PART_NAME"));
                    partnameholder = tv_partname.getText().toString();

                    boxseqid.setText(et_partnum.getText().toString() + "-"+latestID);
                    boxseqholder = boxseqid.getText().toString();
                }
            });

            partN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    partN.showDropDown();
                }
            });
        }catch (Exception e){
            z = e.toString();
        }
        return z;
    }

    //-----------------------GENERATING OF PARTNAME AND GOODS CODE DEPENDING ON PARTNUMBER-------------------//
    public String nameandGoodsCode(String invoice, String selectedCol){

        String output = "";
        connectionClass = new ConnectionClass();
        try{
            Connection con2 = connectionClass.CONN4();
            String query = "SELECT "+selectedCol+" FROM Receive WHERE INVOICE = '"+invoice+"'";
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String generatedItem = rs.getString(selectedCol);

                output = generatedItem;
            }
        }catch(Exception e){
            Toast.makeText(this, "Wifi is off, please connect to load data. ", Toast.LENGTH_LONG).show();
        }
        return output;
    }

    public String invandgoodscode(String goodsc, String selectedCol){

        String output = "";
        connectionClass = new ConnectionClass();
        try{
            Connection con2 = connectionClass.CONN4();
            String query = "SELECT "+selectedCol+" FROM Receive WHERE GOODS_CODE = '"+goodsc+"'";
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String generatedItem = rs.getString(selectedCol);

                output = generatedItem;
            }
        }catch(Exception e){
            Toast.makeText(this, "Wifi is off, please connect to load data. ", Toast.LENGTH_LONG).show();
        }
        return output;
    }


    public String partNum(String partNum, String selectedCol){

        String output = "";
        connectionClass = new ConnectionClass();
        try{
            Connection con2 = connectionClass.CONN4();
            String query = "SELECT "+selectedCol+" FROM Receive WHERE PART_NUMBER = '"+partNum+"'";
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String generatedItem = rs.getString(selectedCol);

                output = generatedItem;
            }
        }catch(Exception e){
            Toast.makeText(this, "Wifi is off, please connect to load data. ", Toast.LENGTH_LONG).show();
        }
        return output;
    }

    //----GET THE LATEST ID TO THE TOP -------//
    public int Latest_ID(String tablename){
        int output = 0;
        connectionClass = new ConnectionClass();
        try{
            Connection con = connectionClass.CONN2();//open ng connection sa connection class
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
/*
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

        }else{

            if(!lotno.getText().toString().equals("") && !lotquant.getText().toString().equals("")){
                try{


                    connectionClassss = new ConnectionClass();
                    Connection con = connectionClass.CONN();
                    String query = "INSERT INTO LotNumber (invoice_no, part_no, part_name, total_quantity, quantity_recieved,lot_no, lot_quantity, box_number,reject,sample_size, goodsCode, MaterialCodeBoxSeqID, remarks) values ('" + Invoice_no + "','" + Part_no + "','" + Part_name + "','" + TotalQuant + "','" + QuantRecieved + "','" + lotno.getText().toString() + "','" + lotquant.getText().toString() + "','" + BoxNum + "','" + reject + "','" + SampleSize + "', '" + GoodsCode + "', '" + BoxSeq + "',  '" + remarks.getText().toString() + "')";
                    Statement stmt = con.createStatement();
                    output = true;
                    stmt.execute(query);
                }catch (Exception e){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                    output = false;
                }
            }
        }

        return output;
    }*/

    void confirmDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload " + " Data " + "?");
        builder.setMessage("Are you sure you want to Upload to this Device?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addDatatoSQLite();


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    //--------------PROCEED TO NEXT FORM-------------------//
    void confirmDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Proceed to next " + " Form " + "?");
        builder.setMessage("Are you sure you want to Proceed?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(SapmpleActivityinlot.this, InspectionDetailsActivity.class);
                invoicenumholder = lot_invoiceno.getText().toString();
                partnumholder = et_partnum.getText().toString();
                partnameholder = tv_partname.getText().toString();
                goodscodeholder = goodsc.getText().toString();
                boxseqholder = boxseqid.getText().toString();
                dateholder  = dateToday.getText().toString();
                startActivity(intent);


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


    void saveToSQLSERVER(){


        String names = "";
        try{
            connectionClassss = new ConnectionClass();
            Connection con = connectionClass.CONN2();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String noww = df.format(new Date());
            dateToday.setText(noww);
            if(lot_invoiceno.getText().toString().equals("") && tv_partname.getText().toString().equals("") && quantityrecieved.getText().toString().equals("")){
                Toast.makeText(this, "Fill up required fields.", Toast.LENGTH_SHORT).show();
            }else{
                String query = "SELECT * FROM LotNumber  WHERE  Date =  '"+ dateToday.getText().toString()+"'   ";
                PreparedStatement stmtt = con.prepareStatement(query);
                ResultSet rs = stmtt.executeQuery();
                if(rs.next()){

                    //  String time = rs.getString("Time");
                    Toast.makeText(SapmpleActivityinlot.this, "Data already existing in SQL Database", Toast.LENGTH_SHORT).show();
                }else{
                    for(LotEncapsulation lotDatas :lotData){
                        names += lotDatas;
                        connectionClassss = new ConnectionClass();
                        String query1 = "INSERT INTO LotNumber (invoice_no, part_no, part_name, total_quantity, quantity_recieved,lot_no, lot_quantity, box_number,reject,sample_size, goodsCode, MaterialCodeBoxSeqID ,remarks, Date) values ('" + lotDatas.getLot_invoiceno() + "','" + lotDatas.getEt_partnum() + "','" +lotDatas.getTv_partname() + "','" + lotDatas.getTotalquantity() + "','" + lotDatas.getQuantityrecieved() + "','" + lotDatas.getLotno() + "','" +lotDatas.getLotquant() + "','" + lotDatas.getBoxnum() + "','" +lotDatas.getReject() + "','" + lotDatas.getSampsize() + "', '" + lotDatas.getGoodsc() + "', '" + lotDatas.getBoxseqid() + "',  '" + lotDatas.getRemarks() + "' ,  '" + lotDatas.getDateToday() + "')";
                        Statement stmt = con.createStatement();
                        stmt.execute(query1);
                        Toast.makeText(SapmpleActivityinlot.this, "success", Toast.LENGTH_SHORT).show();
                    }


                }

            }


        }catch(Exception e ){
            Toast.makeText(SapmpleActivityinlot.this,e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    //--------------UPLOADING TO SQL SERVER-------------------//
    void confirmDialog5() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload  " + " Data to our Server" + "?");
        builder.setMessage("Are you sure you want to upload data? this cannot be undone.");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveToSQLSERVER();
                addDatatoSQLite();


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    //------------CLEARING OF DATA----------------------------//
    void confirmDialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Clear " + " Data " + "?");
        builder.setMessage("Are you sure you want to Clear data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                clearData();


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    public void clearData(){
        lot_invoiceno.setText("");
        boxseqid.setText("");
        et_partnum.setText("");
        tv_partname.setText("");
        goodsc.setText("");
        totalquantity.setText("");
        quantityrecieved.setText("");
        boxnum.setText("");
        sampsize.setText("");
        lotno.setText("");
        lotno.setText("");
        boxseqid.setText("");
    }


    void confirmDialog10() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("DELETE " + " ALL RECORDS " + "?");
        builder.setMessage("Are you sure you want to DELETE data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                 DatabaseHelper myDB = new DatabaseHelper(SapmpleActivityinlot.this);
                 myDB.deleteallRow();
                 Toast.makeText(SapmpleActivityinlot.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(SapmpleActivityinlot.this, SapmpleActivityinlot.class);
                    startActivity(intent);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    //------------UPLOAD TO SQLITE----------------------------//
    public void addDatatoSQLite(){

        try{
            String QuantRecieved = quantityrecieved.getText().toString();
            if(!lotquant.getText().toString().equals("")){

                if(quantityrecieved.getText().toString().equals("") ){
                    quantityrecieved.setText(String.valueOf(0 + Integer.parseInt(lotquant.getText().toString())));

                }else{
                    quantityrecieved.setText(String.valueOf(Integer.parseInt(QuantRecieved) + Integer.parseInt(lotquant.getText().toString())));
                    quantityrecieved.setTextColor(Color.parseColor("#23f011"));
                }

                DatabaseHelper myDB = new DatabaseHelper(SapmpleActivityinlot.this);
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
                        remarks.getText().toString().trim(),
                        dateToday.getText().toString().trim()
                       );


            }
            else {
                Toast.makeText(SapmpleActivityinlot.this, "please fill up data", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }





}