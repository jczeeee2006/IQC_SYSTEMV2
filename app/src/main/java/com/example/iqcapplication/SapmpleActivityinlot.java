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
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Calendar;
import java.util.Date;

import maes.tech.intentanim.CustomIntent;

public class SapmpleActivityinlot extends AppCompatActivity  {
    Button firstFragButton;
    ConnectionClass connectionClass;
    Button buttonShow,nextForm,clearDatA, uploadSaServer,deleteRecords;
    public static EditText  quantityrecieved, lotno, lotquant, boxnum, reject, sampsize, boxseqid,remarks,dateToday;
    public static AutoCompleteTextView lot_invoiceno, tv_partname, goodsc, et_partnum,autoPO, totalquantity;

    ArrayList<LotEncapsulation> lotData = new ArrayList<>();
    AutoCompleteTextView poLotnumber;
    ImageButton helpButton;

    public static String goodscodeholder, invoicenumholder,materialholder, boxseqholder, partnameholder,  partnumholder, latestID,dateholder,poholder,totalquanttholder;
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
        autoPO = findViewById(R.id.poLotNumber);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String noww = df.format(new Date());
        dateToday.setText(noww);

        Date currentTime = Calendar.getInstance().getTime();
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


            fillinvoice();
            partNumber();
            spinpartNamee();
          //  purchaseOrder();
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
                totalquanttholder = totalquantity.getText().toString();
                poholder = poLotnumber.getText().toString();
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
                    Toast.makeText(SapmpleActivityinlot.this, "Data already existing in SQL Datab ase", Toast.LENGTH_SHORT).show();
                }else{

                    connectionClassss = new ConnectionClass();
                     con = connectionClass.CONN2();
                    String query1 = "INSERT INTO LotNumber (invoice_no, part_no, part_name, total_quantity, quantity_recieved,lot_no, lot_quantity, box_number,reject,sample_size, goodsCode, MaterialCodeBoxSeqID ,remarks, Date,PO_Number) values ('" + lot_invoiceno.getText().toString() + "','" + et_partnum.getText().toString() + "','" + tv_partname.getText().toString() + "','" + totalquantity.getText().toString() + "','" + quantityrecieved.getText().toString() + "','" + lotno.getText().toString() + "','" +lotquant.getText().toString() + "','" + boxnum.getText().toString() + "','" + reject.getText().toString() + "','" + sampsize.getText().toString() + "', '" + goodsc.getText().toString() + "', '" + boxseqid.getText().toString() + "',  '" + remarks.getText().toString() + "' ,  '" + dateToday.getText().toString() + "','"+poLotnumber.getText().toString()+"')";
                    Statement stmt = con.createStatement();
                    stmt.execute(query1);
                    Toast.makeText(SapmpleActivityinlot.this, "success", Toast.LENGTH_SHORT).show();
                    Connection connn = connectionClass.CONN4();
                    String query3 = "UPDATE Receive set NO_GOOD = '"+reject.getText().toString()+"', STATUS = 'IQC_END',  IQC_END_DT = '"+dateToday.getText().toString()+"' WHERE  INVOICE =  '" +lot_invoiceno.getText().toString() +"' AND PART_NAME = '" +tv_partname.getText().toString()  +"' AND PART_NUMBER = '" +et_partnum.getText().toString()+"' AND INVOICE = '" +lot_invoiceno.getText().toString() +"' AND PO = '"+poLotnumber.getText().toString()+"'";
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
    //TODO YUNG QUERY KULANG PARA MAAYOS, NEED MAWALA NUNG QTY PAG NAG INSERT NA
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
        poLotnumber.setText("");
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


    // INVOICE NUMBER GENERATING
    public void fillinvoice(){
        final AutoCompleteTextView INVOICE = (AutoCompleteTextView) findViewById(R.id.invoiceNum);
     //   final Spinner SINVOOICE = findViewById(R.id.invSpin);
        connectionClass = new ConnectionClass();

        try{
            Connection con2 = connectionClass.CONN4();//open ng connection sa connection class
            String query = "select DISTINCT (INVOICE) from Receive where ARCHIVE = 0";
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            final ArrayList<String> invoice = new ArrayList<String>();

            while (rs.next()){
                String invoicenumber = rs.getString("INVOICE");
                invoice.add(invoicenumber);
            }


            final ArrayAdapter<String> invoice_array = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, invoice);
            INVOICE.setThreshold(1);
            INVOICE.setAdapter(invoice_array);



            partNumber();


            //-------AUTO COMPLETE SAUGGESTED ITEM-----
            INVOICE.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(getApplicationContext(), "Selected Item is: \t" + item, Toast.LENGTH_LONG).show();
                    INVOICE.setText(item);
                    invoicenumholder = item;

                    spinpartNum();
                }
            });


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    //--------------------------------GOODS CODE--------------------------
    public void partNumber(){

        final AutoCompleteTextView autopartNum = (AutoCompleteTextView) findViewById(R.id.partN);
        // final Spinner SgoodC = (Spinner) findViewById(R.id.goodsSpin);
        //final AutoCompleteTextView mname = (AutoCompleteTextView) findViewById(R.id.et_partname);
        connectionClass = new ConnectionClass();

        try{

            Connection con2 = connectionClass.CONN4();//open ng connection sa connection class
            String query = "SELECT  DISTINCT (PART_NUMBER) from Receive where PART_NUMBER IS NOT NULL AND INVOICE = '"+invoicenumholder+"'";
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> PARTn = new ArrayList<String>();
            while (rs.next()){
                String autopartNum1  = rs.getString("PART_NUMBER");
            
            }

            ArrayAdapter<String> goodc_array = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, PARTn);
            autopartNum.setThreshold(1);
            autopartNum.setAdapter(goodc_array);


            partNamee();

            autopartNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    autopartNum.showDropDown();
                    autopartNum.setCursorVisible(true);
                    spinpartNamee();
                }
            });


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void spinpartNum(){

        final AutoCompleteTextView partNum = (AutoCompleteTextView) findViewById(R.id.partN);

        connectionClass = new ConnectionClass();

        try{

            Connection con2 = connectionClass.CONN4();//open ng connection sa connection class
            String query = "select DISTINCT (PART_NUMBER) from Receive WHERE PART_NUMBER IS NOT NULL AND  INVOICE = '"+invoicenumholder+"'  ";// WHERE InvoiceNumber = '"+invoicenum+"'
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> matn = new ArrayList<String>();
            while (rs.next()){
                String materialcode = rs.getString("PART_NUMBER");
                matn.add(materialcode);
            }

            ArrayAdapter<String> goodc_array = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, matn);
            partNum.setAdapter(goodc_array);
            partNum.setText("");
            //MaterialCode();

            partNum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String item = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(getApplicationContext(), "Selected Item is: \t" + item, Toast.LENGTH_LONG).show();
                    partNum.setText(item);
                    partnumholder = item;
                    spinpartNamee();

                }
            });


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    //--------------------------------GOODS CODE--------------------------
    public void partNamee(){

        final AutoCompleteTextView partNamee = (AutoCompleteTextView) findViewById(R.id.partName);
        // final Spinner SgoodC = (Spinner) findViewById(R.id.goodsSpin);
        //final AutoCompleteTextView mname = (AutoCompleteTextView) findViewById(R.id.et_partname);
        connectionClass = new ConnectionClass();

        try{

            Connection con2 = connectionClass.CONN4();//open ng connection sa connection class
            String query = "select DISTINCT (PART_NAME) from Receive WHERE PART_NAME IS NOT NULL AND  PART_NUMBER = '"+partnumholder+"'  ";// WHERE InvoiceNumber = '"+invoicenum+"'
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> matn = new ArrayList<String>();
            while (rs.next()){
                String materialcode = rs.getString("PART_NAME");
                matn.add(materialcode);
            }

            ArrayAdapter<String> goodc_array = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, matn);
            partNamee.setAdapter(goodc_array);
            MaterialCode();

            partNamee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View arg0) {
                    partNamee.showDropDown();
                    partNamee.setCursorVisible(true);
                    //spingoods();

                }
            });


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    public void spinpartNamee(){

        final AutoCompleteTextView partNamee = (AutoCompleteTextView) findViewById(R.id.partName);

        connectionClass = new ConnectionClass();

        try{

            Connection con2 = connectionClass.CONN4();//open ng connection sa connection class
            String query = "select DISTINCT (PART_NAME) from Receive WHERE PART_NAME IS NOT NULL AND  PART_NUMBER = '"+partnumholder+"'  ";// WHERE InvoiceNumber = '"+invoicenum+"'
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> matn = new ArrayList<String>();
            while (rs.next()){
                String materialcode = rs.getString("PART_NAME");
                matn.add(materialcode);
            }

            ArrayAdapter<String> goodc_array = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, matn);
            partNamee.setAdapter(goodc_array);
            partNamee.setText("");
            MaterialCode();

            partNamee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String item = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(getApplicationContext(), "Selected Item is: \t" + item, Toast.LENGTH_LONG).show();
                    partNamee.setText(item);
                    partnameholder = item;



                    spingoods();

                }
            });


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }




    //--------------------------------GOODS CODE--------------------------
    public void MaterialCode(){

        final AutoCompleteTextView autoGoods = (AutoCompleteTextView) findViewById(R.id.goodsCode);
       // final Spinner SgoodC = (Spinner) findViewById(R.id.goodsSpin);
        //final AutoCompleteTextView mname = (AutoCompleteTextView) findViewById(R.id.et_partname);
        connectionClass = new ConnectionClass();

        try{

            Connection con2 = connectionClass.CONN4();//open ng connection sa connection class
            String query = "SELECT DISTINCT (GOODS_CODE) FROM Receive WHERE PART_NAME= '"+partnameholder+"' AND INVOICE = '"+invoicenumholder+"' AND PART_NUMBER = '"+partnumholder+"'";// WHERE InvoiceNumber = '"+invoicenum+"'
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> matn = new ArrayList<String>();
            while (rs.next()){
                String materialcode = rs.getString("GOODS_CODE");
                matn.add(materialcode);
            }

            ArrayAdapter<String> goodc_array = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, matn);
            autoGoods.setAdapter(goodc_array);
            //TotalQuantity();
            PONumber();
            autoGoods.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View arg0) {

                    autoGoods.showDropDown();
                    autoGoods.setCursorVisible(true);

                }
            });


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

  //  --------------------------------spinner goods code------------------
    public void spingoods(){

        connectionClass = new ConnectionClass();

        try{
            Connection con2 = connectionClass.CONN4();//open ng connection sa connection class
            String query = "SELECT DISTINCT (GOODS_CODE) FROM Receive WHERE PART_NAME= '"+partnameholder+"' AND INVOICE = '"+invoicenumholder+"' AND PART_NUMBER = '"+partnumholder+"'";
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> invoice = new ArrayList<String>();

            while (rs.next()){
                String invoicenumber = rs.getString("GOODS_CODE");
                invoice.add(invoicenumber);
            }


          //  final Spinner goodsSpinn = findViewById(R.id.goodsSpin);
            AutoCompleteTextView invAuto = findViewById(R.id.goodsCode);
            ArrayAdapter<String> invoice_array = new ArrayAdapter(this, android.R.layout.simple_spinner_item, invoice);
            invAuto.setAdapter(invoice_array);
            //TotalQuantity();
            PONumber();
            invAuto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String item = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(getApplicationContext(), "Selected Item is: \t" + item, Toast.LENGTH_LONG).show();
                    invAuto.setText(item);
                    goodscodeholder = item;
                   // spinTotalquant();
                   // poLotnumber.setText(POandGoodsCode(goodsc.getText().toString(),et_partnum.getText().toString(),lot_invoiceno.getText().toString() ,"PO"));
                   // totalquantity.setText(totalandGoodsCode(goodsc.getText().toString(),poLotnumber.getText().toString(),tv_partname.getText().toString(), "QTY"));

                    spinPO();

                }
            });




        }catch (Exception ex){
            ex.printStackTrace();
        }

    }




    public String POandGoodsCode(String inv, String qty,String go,String partName,String partNumber, String selectedCol){

        String output = "";
        connectionClass = new ConnectionClass();
        try{
            Connection con2 = connectionClass.CONN4();
            String query = " SELECT  "+selectedCol+" FROM Receive WHERE  INVOICE = '"+inv+"' AND QTY = '"+qty+"' AND GOODS_CODE  = '"+go+"' AND PART_NAME = '"+partName+"' AND PART_NUMBER = '"+partNumber+"' ";
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                String generatedItem = rs.getString(selectedCol);
                output = generatedItem;
            }
        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        return output;
    }


    public String totalandGoodsCode(String go,String pname, String selectedCol){

        String output = "";
        connectionClass = new ConnectionClass();
        try{
            Connection con2 = connectionClass.CONN4();
            String query = "SELECT  "+selectedCol+" FROM Receive WHERE PO is not null and PART_NAME = '"+pname+"' and GOODS_CODE = '"+go+"' ";
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


    public void TotalQuantity(){

        final AutoCompleteTextView autoTotal = (AutoCompleteTextView) findViewById(R.id.totalQuan_txt);
        connectionClass = new ConnectionClass();

        try{

            Connection con2 = connectionClass.CONN4();//open ng connection sa connection class
            String query = "SELECT  (QTY) FROM Receive WHERE  GOODS_CODE = '"+goodscodeholder+"' AND INVOICE = '"+invoicenumholder  +"' AND PART_NAME = '"+partnameholder+"' AND PART_NUMBER = '"+et_partnum.getText().toString()+"'  ";// WHERE InvoiceNumber = '"+invoicenum+"'
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> matn = new ArrayList<String>();
            while (rs.next()){
                String materialcode = rs.getString("QTY");
                matn.add(materialcode);
            }

            ArrayAdapter<String> goodc_array = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, matn);
            autoTotal.setAdapter(goodc_array);


            autoTotal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View arg0) {

                    autoTotal.showDropDown();
                    autoTotal.setCursorVisible(true);

                }
            });


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
    public void spinTotalquant(){

        connectionClass = new ConnectionClass();

        try{
            Connection con2 = connectionClass.CONN4();//open ng connection sa connection class
            String query = "SELECT  (QTY) FROM Receive WHERE  GOODS_CODE = '"+goodscodeholder+"' AND INVOICE = '"+invoicenumholder  +"' AND PART_NAME = '"+partnameholder+"' AND PART_NUMBER = '"+et_partnum.getText().toString()+"' ";
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> invoice = new ArrayList<String>();

            while (rs.next()){
                String invoicenumber = rs.getString("QTY");
                invoice.add(invoicenumber);
            }


            //  final Spinner goodsSpinn = findViewById(R.id.goodsSpin);
            final AutoCompleteTextView autoTotal = (AutoCompleteTextView) findViewById(R.id.totalQuan_txt);
            ArrayAdapter<String> invoice_array = new ArrayAdapter(this, android.R.layout.simple_spinner_item, invoice);
            autoTotal.setAdapter(invoice_array);

            autoTotal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String item = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(getApplicationContext(), "Selected Item is: \t" + item, Toast.LENGTH_LONG).show();
                    autoTotal.setText(item);
                    totalquanttholder = item;


                    poLotnumber.setText(POandGoodsCode(lot_invoiceno.getText().toString(),   totalquantity.getText().toString(), goodsc.getText().toString() ,tv_partname.getText().toString(),et_partnum.getText().toString(),"PO"));



                }
            });




        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void PONumber(){

        final AutoCompleteTextView autoTotal = (AutoCompleteTextView) findViewById(R.id.poLotNumber);
        connectionClass = new ConnectionClass();

        try{

            Connection con2 = connectionClass.CONN4();//open ng connection sa connection class
            String query = "SELECT  (PO) FROM Receive WHERE GOODS_CODE = '"+goodscodeholder+"' AND INVOICE = '"+invoicenumholder  +"' AND PART_NAME = '"+partnameholder+"' AND PART_NUMBER = '"+et_partnum.getText().toString()+"'  ";// WHERE InvoiceNumber = '"+invoicenum+"'
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> matn = new ArrayList<String>();
            while (rs.next()){
                String materialcode = rs.getString("PO");
                matn.add(materialcode);
            }

            ArrayAdapter<String> goodc_array = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, matn);
            autoTotal.setAdapter(goodc_array);


            autoTotal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View arg0) {

                    autoTotal.showDropDown();
                    autoTotal.setCursorVisible(true);

                }
            });


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void spinPO(){

        connectionClass = new ConnectionClass();

        try{
            Connection con2 = connectionClass.CONN4();//open ng connection sa connection class
            String query = "SELECT  (PO) FROM Receive WHERE   GOODS_CODE = '"+goodscodeholder+"' AND INVOICE = '"+invoicenumholder  +"' AND PART_NAME = '"+partnameholder+"' AND PART_NUMBER = '"+et_partnum.getText().toString()+"' ";
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> invoice = new ArrayList<String>();

            while (rs.next()){
                String invoicenumber = rs.getString("PO");
                invoice.add(invoicenumber);
            }


            //  final Spinner goodsSpinn = findViewById(R.id.goodsSpin);
            final AutoCompleteTextView autoTotal = (AutoCompleteTextView) findViewById(R.id.poLotNumber);
            ArrayAdapter<String> invoice_array = new ArrayAdapter(this, android.R.layout.simple_spinner_item, invoice);
            autoTotal.setAdapter(invoice_array);

            autoTotal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    String item = parent.getItemAtPosition(position).toString();
                    //Toast.makeText(getApplicationContext(), "Selected Item is: \t" + item, Toast.LENGTH_LONG).show();
                    autoTotal.setText(item);
                    poholder = item;





                }
            });




        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}