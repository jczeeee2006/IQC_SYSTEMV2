package com.example.iqcapplication.Update;

import static com.example.iqcapplication.MainActivity.connectionClass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iqcapplication.ConnectionClass;
import com.example.iqcapplication.DatabaseHelper;
import com.example.iqcapplication.R;
import com.example.iqcapplication.SapmpleActivityinlot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LotFormActivity extends AppCompatActivity {
    EditText totalquantityup, quantityrecievedup, lotnoup, lotquantup, boxnumup, rejectup, sampsizeup, boxseqidup,remarksup,dateToday;
    AutoCompleteTextView lot_invoicenoup, tv_partnameup, goodscup, et_partnumup;
    String id, REMARKS,LOTQUANT,LOTNUM,SAMPLE,REJECT,TOTALQUANT,ACTUALQUANT,BOXSEQ,BOX_NUMBER,PART_NAME,
            GOODS_CODE,PART_NUMBER,INVOICE, DATETIME;
    public static String    boxseqholder,invoiceholder,partnumholder,goodscodeholder,partnameholder,totalquantholder,lotnumholder,actualquantityHolder ;
    Button buttonAdd, button,buttonUpload,backButton;
    public static  String dateHolder;
    ConnectionClass connectionClassss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lot_form);

        boxseqidup = findViewById(R.id.boxSequenceup);
        lot_invoicenoup = findViewById(R.id.invoiceNumup);
        totalquantityup = findViewById(R.id.totalQuan_txtup);
        et_partnumup = findViewById(R.id.partNup);
        goodscup = findViewById(R.id.goodsCodeup);
        tv_partnameup =findViewById(R.id.partNameup);
        quantityrecievedup = findViewById(R.id.atctualQuantup);
        lotnoup = findViewById(R.id.lotNumberup);
        lotquantup = findViewById(R.id.lotQuantup);
        boxnumup = findViewById(R.id.boxNumup);
        rejectup = findViewById(R.id.rejectup);
        sampsizeup = findViewById(R.id.sampleSizeup);
        remarksup   = findViewById(R.id.remarksup);
        buttonAdd = findViewById(R.id.add);
        button = findViewById(R.id.failed);
        buttonUpload = findViewById(R.id.uploadtosqlserver);

        dateToday = findViewById(R.id.dateToday);
        backButton  = findViewById(R.id.lotbackButton);

        boxseqidup.setEnabled(false);
        lot_invoicenoup.setEnabled(false);
        et_partnumup.setEnabled(false);
        tv_partnameup.setEnabled(false);
        goodscup.setEnabled(false);
        getIntentData();



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog1();
            }
        });



        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog3();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LotFormActivity.this, SapmpleActivityinlot.class);
               boxseqholder = boxseqidup.getText().toString();
               invoiceholder = lot_invoicenoup.getText().toString();
               partnameholder = tv_partnameup.getText().toString();
               partnumholder  = et_partnumup.getText().toString();
               goodscodeholder = goodscup.getText().toString();
               totalquantholder = totalquantityup .getText().toString();
               lotnumholder = lotquantup.getText().toString();
               dateHolder = dateToday.getText().toString();
               actualquantityHolder  = quantityrecievedup.getText().toString();


                       startActivity(intent);

            }
        });
    }

    void confirmDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("UPDATE " + " DATA " + "?");
        builder.setMessage("Are you sure you want to UPDATE to this Device?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                savetoSQLite();


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


    // TODO: 08/04/2022      ahhhh check yung date today kung nag iinsert ba sa database server
    void savetoSQLite(){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    DatabaseHelper myDB = new DatabaseHelper(LotFormActivity.this);
                    INVOICE = lot_invoicenoup.getText().toString().trim();
                    PART_NUMBER =  et_partnumup.getText().toString().trim();
                    GOODS_CODE =   goodscup.getText().toString().trim();
                    PART_NAME =    tv_partnameup.getText().toString().trim();
                    BOX_NUMBER = boxnumup.getText().toString().trim();
                    BOXSEQ =   boxseqidup.getText().toString().trim();
                    ACTUALQUANT =  quantityrecievedup.getText().toString().trim();
                    TOTALQUANT = totalquantityup.getText().toString().trim();
                    REJECT =    rejectup.getText().toString().trim();
                    SAMPLE =  sampsizeup.getText().toString().trim();
                    LOTNUM   =  lotnoup.getText().toString().trim();
                    LOTQUANT = lotquantup.getText().toString().trim();
                    REMARKS  = remarksup.getText().toString().trim();
                    DATETIME = dateToday.getText().toString().trim();
                    myDB.updateData(id,INVOICE,PART_NUMBER,GOODS_CODE,PART_NAME, BOX_NUMBER,BOXSEQ, ACTUALQUANT, TOTALQUANT, REJECT, SAMPLE, LOTNUM, LOTQUANT, REMARKS,DATETIME);


                }catch(Exception e ){
                    Toast.makeText(LotFormActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    void confirmDialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload data to  " + " SQL SERVER " + "?");
        builder.setMessage("Are you sure you want to Proceed? this can't be undone ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(sampsizeup.getText().toString().equals("")){
                    sampsizeup.setError("Enter Sample size");
                }else if(!sampsizeup.getText().toString().equals("")){
                    saveToSQLSERVER();
                }

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



                  try{
                      connectionClassss = new ConnectionClass();
                      Connection con = connectionClass.CONN();
                      String query = "SELECT * FROM LotNumber WHERE  Date =  '"+ dateToday.getText().toString()+"' ";
                      PreparedStatement stmtt = con.prepareStatement(query);
                      ResultSet rs = stmtt.executeQuery();
                      if(rs.next()){

                          //  String time = rs.getString("Time");
                          Toast.makeText(LotFormActivity.this, "Data already existing in SQL Database", Toast.LENGTH_SHORT).show();
                      }else{
                          connectionClassss = new ConnectionClass();
                          String query1 = "INSERT INTO LotNumber (invoice_no, part_no, part_name, total_quantity, quantity_recieved,lot_no, lot_quantity, box_number,reject,sample_size, goodsCode, MaterialCodeBoxSeqID ,remarks, Date) values ('" + INVOICE + "','" + PART_NUMBER + "','" + PART_NAME + "','" + TOTALQUANT + "','" + ACTUALQUANT + "','" + LOTNUM + "','" +LOTQUANT + "','" + BOX_NUMBER + "','" + REJECT + "','" + SAMPLE + "', '" + GOODS_CODE + "', '" + BOXSEQ + "',  '" + REMARKS + "' ,  '" + dateToday.getText().toString() + "')";
                          Statement stmt = con.createStatement();
                          stmt.execute(query1);
                          Toast.makeText(LotFormActivity.this, "success", Toast.LENGTH_SHORT).show();

                      }


                  }catch(Exception e ){
                      Toast.makeText(LotFormActivity.this,e.toString(), Toast.LENGTH_SHORT).show();
                  }

    }



    void getIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("lot_invoiceno") && getIntent().hasExtra("et_partnum") && getIntent().hasExtra("goodsc") && getIntent().hasExtra("tv_partname") && getIntent().hasExtra("boxnum") &&
                getIntent().hasExtra("boxseqid") && getIntent().hasExtra("quantityrecieved") && getIntent().hasExtra("totalquantity") && getIntent().hasExtra("reject") && getIntent().hasExtra("sampsize")
                && getIntent().hasExtra("lotno") && getIntent().hasExtra("lotquant")  && getIntent().hasExtra("remarks")){

            id = getIntent().getStringExtra("id");
            INVOICE = getIntent().getStringExtra("lot_invoiceno");
            PART_NUMBER = getIntent().getStringExtra("et_partnum");
            GOODS_CODE = getIntent().getStringExtra("goodsc");
            PART_NAME = getIntent().getStringExtra("tv_partname");
            BOX_NUMBER = getIntent().getStringExtra("boxnum");
            BOXSEQ = getIntent().getStringExtra("boxseqid");
            ACTUALQUANT = getIntent().getStringExtra("quantityrecieved");
            TOTALQUANT = getIntent().getStringExtra("totalquantity");
            REJECT = getIntent().getStringExtra("reject");
            SAMPLE = getIntent().getStringExtra("sampsize");
            LOTNUM = getIntent().getStringExtra("lotno");
            LOTQUANT = getIntent().getStringExtra("lotquant");
            REMARKS = getIntent().getStringExtra("remarks");
            DATETIME  = getIntent().getStringExtra("dateToday");

            boxseqidup.setText(BOXSEQ);
            lot_invoicenoup.setText(INVOICE);
            totalquantityup.setText(TOTALQUANT);
            et_partnumup.setText(PART_NUMBER);
            goodscup.setText(GOODS_CODE);
            tv_partnameup.setText(PART_NAME);
            quantityrecievedup.setText(ACTUALQUANT);
            lotnoup.setText(LOTNUM);
            lotquantup.setText(LOTQUANT);
            boxnumup.setText(BOX_NUMBER);
            rejectup.setText(REJECT);
            sampsizeup.setText(SAMPLE);
            remarksup.setText(REMARKS);
            dateToday.setText(DATETIME);

        }

    }


}