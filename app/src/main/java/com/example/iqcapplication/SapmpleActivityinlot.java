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
import android.text.Editable;
import android.text.TextWatcher;
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

import maes.tech.intentanim.CustomIntent;

public class SapmpleActivityinlot extends AppCompatActivity  {
    Button firstFragButton;
    ConnectionClass connectionClass;
    Button buttonShow,nextForm,clearDatA, uploadSaServer,deleteRecords;
    public static EditText totalquantity, quantityrecieved, lotno, lotquant, boxnum, reject, sampsize, boxseqid,remarks,dateToday;
    public static AutoCompleteTextView lot_invoiceno, tv_partname, goodsc, et_partnum;

    ArrayList<LotEncapsulation> lotData = new ArrayList<>();
    AutoCompleteTextView poLotnumber;
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
        poLotnumber = findViewById(R.id.poLotNumber);
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
        lot_invoiceno.setText(LotFormActivity.invoiceholder);
        et_partnum.setText(LotFormActivity.partnumholder);
        tv_partname.setText(LotFormActivity.partnameholder);
        goodsc.setText(LotFormActivity.goodscodeholder);
        lotno.setText(LotFormActivity.lotnumholder);
        totalquantity.setText(LotFormActivity.totalquantholder);
        quantityrecieved.setText(LotFormActivity.actualquantityHolder);
        sampsize.setText(LotFormActivity.samplesizeHolder);
        lotquant.setText(LotFormActivity.lotQuantityholder);

        //desc of data in lotnumber
        latestID = String.valueOf((Latest_ID("LotNumber")+1));


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


        et_partnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    boxseqid.setText(et_partnum.getText().toString() + "-" + latestID);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

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




    private void replaceFragment(FragmentforLot lotFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,lotFragment);
        fragmentTransaction.commit();

    }


    public  class LotAsync extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            invoiceList();
            //Query();
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

    public String GoodsCodelist(){

        connectionClass = new ConnectionClass();
        String z = "";


        try{
            Connection conn2 = connectionClass.CONN4();
            String query = "SELECT GOODS_CODE from Receive ";
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

                    et_partnum.setText(invandgoodscode(goodsc.getText().toString(), "PART_NUMBER"));
                    partnumholder = et_partnum.getText().toString();
//                  lot_invoiceno.setText(invandgoodscode(goodsc.getText().toString(), "INVOICE"));
//                  invoicenumholder = et_partnum.getText().toString();
                    tv_partname.setText(invandgoodscode(goodsc.getText().toString(),"PART_NAME"));
                    partnameholder = tv_partname.getText().toString();
                    totalquantity.setText(invandgoodscode(goodsc.getText().toString(),"QTY"));
                    boxseqid.setText(et_partnum.getText().toString() + "-"+latestID);
                    boxseqholder = boxseqid.getText().toString();
                    poLotnumber.setText(nameandGoodsCode(goodsc.getText().toString(), "PO"));
                }
            });



        }catch (Exception e){
                z = e.toString();
        }
        return z;
    }

    public void invoiceList(){
        connectionClass = new ConnectionClass();

        try{

            Connection conn2 = connectionClass.CONN4();
            String query = "SELECT DISTINCT (INVOICE) from Receive  ";
            PreparedStatement stmt = conn2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            final ArrayList<String> inv = new ArrayList<String>();
            while (rs.next()) {
                String invoice = rs.getString("INVOICE");
                inv.add(invoice);

            }

            final AutoCompleteTextView   invoice = findViewById(R.id.invoiceNum);
            final ArrayAdapter<String> invoicenum = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, inv);
            invoice.setThreshold(1);
            invoice.setAdapter(invoicenum);

            invoice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    //-------SET THE DATA FROM DB TO PARTNUMBER TEXTBOX
                    invoice.setText(item);



                    tv_partname.setText(nameandGoodsCode(invoice.getText().toString(),"PART_NAME"));
                    partnameholder = tv_partname.getText().toString();
                    et_partnum.setText(nameandGoodsCode(invoice.getText().toString(), "PART_NUMBER"));
                    boxseqid.setText(et_partnum.getText().toString() + "-"+latestID);
                    boxseqholder = boxseqid.getText().toString();
                    totalquantity.setText(nameandGoodsCode(invoice.getText().toString(), "QTY"));

//                    goodsc.setText(nameandGoodsCode(invoice.getText().toString(), "GOODS_CODE"));
//                    goodscodeholder = et_partnum.getText().toString();

                    poLotnumber.setText(nameandGoodsCode(invoice.getText().toString(), "PO"));

                }
            });


        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }



    public void partnumList(){

        connectionClass = new ConnectionClass();

        try{

            Connection conn2 = connectionClass.CONN4();
            String query = "SELECT DISTINCT (PART_NUMBER) from Receive";
            PreparedStatement stmt = conn2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            final ArrayList<String> inv = new ArrayList<String>();
            while (rs.next()) {
                String invoice = rs.getString("PART_NUMBER");
                inv.add(invoice);
            }

            final AutoCompleteTextView   et_partnum = findViewById(R.id.partN);
            final ArrayAdapter<String> partn_array = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, inv);
            et_partnum.setThreshold(1);
            et_partnum.setAdapter(partn_array);

         et_partnum.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

             }
         });

        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }


    //-----------------------GENERATING OF PARTNAME AND GOODS CODE DEPENDING ON PARTNUMBER-------------------//
    public String nameandGoodsCode(String invoice, String selectedCol){

        String output = "";
        connectionClass = new ConnectionClass();
        try{
            Connection con2 = connectionClass.CONN4();
            String query = "SELECT  "+selectedCol+" FROM Receive WHERE INVOICE = '"+invoice+"'";
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
                CustomIntent.customType(SapmpleActivityinlot.this,"left-to-right");


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


        String QuantReceived = quantityrecieved.getText().toString();

        try{
            connectionClassss = new ConnectionClass();
            Connection con = connectionClass.CONN2();
            SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            String noww = df.format(new Date());
            dateToday.setText(noww);
            if(lot_invoiceno.getText().toString().equals("") && tv_partname.getText().toString().equals("") && quantityrecieved.getText().toString().equals("")){
                Toast.makeText(this, "Fill up required fields.", Toast.LENGTH_SHORT).show();
            }else{
                if (quantityrecieved.getText().toString().equals("")) {
                    quantityrecieved.setText(String.valueOf(0 + Integer.parseInt(lotquant.getText().toString())));
                    QuantReceived = quantityrecieved.getText().toString();
                }
                else {
                    quantityrecieved.setText(String.valueOf(Integer.parseInt(QuantReceived) + Integer.parseInt(lotquant.getText().toString())));
                    QuantReceived = quantityrecieved.getText().toString();
                }

                String query = "SELECT * FROM LotNumber  WHERE  Date =  '"+ dateToday.getText().toString()+"'   ";
                PreparedStatement stmtt = con.prepareStatement(query);
                ResultSet rs = stmtt.executeQuery();
                if(rs.next()){

                    String time = rs.getString("Time");
                    Toast.makeText(SapmpleActivityinlot.this, "Data already existing in SQL Database", Toast.LENGTH_SHORT).show();
                }else{

                    connectionClassss = new ConnectionClass();

                    String query1 = "INSERT INTO LotNumber (invoice_no, part_no, part_name, total_quantity, quantity_recieved,lot_no, lot_quantity, box_number,reject,sample_size, goodsCode, MaterialCodeBoxSeqID ,remarks, Date,PO_Number) values ('" + lot_invoiceno.getText().toString() + "','" + et_partnum.getText().toString() + "','" + tv_partname.getText().toString() + "','" + totalquantity.getText().toString() + "','" + quantityrecieved.getText().toString() + "','" + lotno.getText().toString() + "','" +lotquant.getText().toString() + "','" + boxnum.getText().toString() + "','" + reject.getText().toString() + "','" + sampsize.getText().toString() + "', '" + goodsc.getText().toString() + "', '" + boxseqid.getText().toString() + "',  '" + remarks.getText().toString() + "' ,  '" + dateToday.getText().toString() + "','"+poLotnumber.getText().toString()+"')";
                    Statement stmt = con.createStatement();
                    stmt.execute(query1);
                    Toast.makeText(SapmpleActivityinlot.this, "success", Toast.LENGTH_SHORT).show();
                    Connection connn = connectionClass.CONN4();
                    String query3 = "exec.dbo.SP_UpdateReject '" +Integer.parseInt(tv_partname.getText().toString())  +"','" +Integer.parseInt(et_partnum.getText().toString())+"','" +Integer.parseInt(lot_invoiceno.getText().toString()) +"','" +Integer.parseInt(reject.getText().toString()) +"'";
                    Statement stmt2 = connn.createStatement();
                    stmt2.execute(query3);


                    Toast.makeText(getApplicationContext(),"Successfully updated!", Toast.LENGTH_SHORT).show();
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


        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }






    
}