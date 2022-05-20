package com.example.iqcapplication.Update;

import static com.example.iqcapplication.MainActivity.connectionClass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iqcapplication.ConnectionClass;
import com.example.iqcapplication.DatabaseHelper;
import com.example.iqcapplication.DimensionalActivity;
import com.example.iqcapplication.R;
import com.example.iqcapplication.SapmpleActivityinlot;
import com.example.iqcapplication.add.InspectionDetailsActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LotFormActivity extends AppCompatActivity {
    EditText totalquantityup, quantityrecievedup, lotnoup, lotquantup, boxnumup, rejectUp, sampsizeup, boxseqidup,remarksup,dateToday;
    AutoCompleteTextView lot_invoicenoup, tv_partnameup, goodscup, et_partnumup;
    String id, REMARKS,LOTQUANT,LOTNUM,SAMPLE,REJECT,TOTALQUANT,ACTUALQUANT,BOXSEQ,BOX_NUMBER,PART_NAME,
            GOODS_CODE,PART_NUMBER,INVOICE, DATETIME, rejectHolder;
    public static String    boxseqholder,invoiceholder,partnumholder,goodscodeholder,partnameholder,totalquantholder,lotnumholder,actualquantityHolder, lotQuantityholder,
            boxnumholder,samplesizeHolder, rejectHolderr;

    TextView difFerence,totallGood;

    Button buttonAdd, button,buttonUpload,backButton, updateRejectup;
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
        rejectUp = findViewById(R.id.rejectup);
        sampsizeup = findViewById(R.id.sampleSizeup);
        remarksup   = findViewById(R.id.remarksup);
        buttonAdd = findViewById(R.id.add);
        button = findViewById(R.id.failed);
        difFerence = findViewById(R.id.difference);
        totallGood = findViewById(R.id.totalGood);
        buttonUpload = findViewById(R.id.uploadtosqlserver);

        dateToday = findViewById(R.id.dateToday);
        backButton  = findViewById(R.id.lotbackButton);
        updateRejectup = findViewById(R.id.updateReject);
        boxseqidup.setEnabled(false);
        lot_invoicenoup.setEnabled(false);
        et_partnumup.setEnabled(false);
        tv_partnameup.setEnabled(false);
        goodscup.setEnabled(false);
        getIntentData();

        samplenumberenabled();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog1();
            }


        });


        updateRejectup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int firstvalue= Integer.parseInt((totalquantityup.getText().toString()));
                int secondvalue=Integer.parseInt((rejectUp.getText().toString()));
                int diff = 0;
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN2();
                try {

                    if(rejectUp.getText().toString().equals("0")  ){
                        difFerence.setText(String.valueOf(0 - Integer.parseInt(rejectUp.getText().toString())));
                        String query7 = " UPDATE LotNumber SET DIFF = '" + Integer.parseInt(difFerence.getText().toString())+ "' WHERE invoice_no = '" + lot_invoicenoup.getText().toString() + "' AND MaterialCodeBoxSeqID = '" + boxseqidup.getText().toString() + "' AND Date = '" + dateToday.getText().toString() + "' ";
                        Statement stmt =  con.createStatement();
                        stmt.execute(query7);



                    }else{
                        difFerence.setText(String.valueOf(firstvalue - secondvalue));
                        difFerence.setText(String.valueOf(diff));
                        totalquantityup.setTextColor(Color.parseColor("#23f011"));


                        String query5 = "  SELECT SUM(reject)  as sumreject  FROM LotNumber  WHERE invoice_no= '"+lot_invoicenoup.getText().toString()+"' AND MaterialCodeBoxSeqID  = '"+boxseqidup.getText().toString()+"'";
                        PreparedStatement stmtt = con.prepareStatement(query5);
                        ResultSet rs = stmtt.executeQuery();
                        while(rs.next()){
                            String totalSum = rs.getString("sumreject");
                            rejectHolder = totalSum;
                        }
                        totallGood.setText(rejectHolder);
                        int thirdVal=Integer.parseInt((totallGood.getText().toString()));
                        totallGood.setText(String.valueOf(firstvalue - thirdVal));




                        String query6 = " UPDATE LotNumber SET Total_good = '" + totallGood.getText().toString()+ "' WHERE invoice_no = '" + lot_invoicenoup.getText().toString() + "' AND MaterialCodeBoxSeqID = '" + boxseqidup.getText().toString() + "' ";
                        Statement stmt1 =  con.createStatement();
                        stmt1.execute(query6);


                        Toast.makeText(LotFormActivity.this, "success", Toast.LENGTH_SHORT).show();


                        String query = " UPDATE LotNumber SET reject = '" + Integer.parseInt(rejectUp.getText().toString() )+ "' WHERE invoice_no = '" + lot_invoicenoup.getText().toString() + "' AND MaterialCodeBoxSeqID = '" + boxseqidup.getText().toString() + "' AND Date = '" + dateToday.getText().toString() + "'";
                        difFerence.setText(String.valueOf(firstvalue - secondvalue));
                        String query2 = " UPDATE LotNumber SET DIFF = '" + Integer.parseInt(difFerence.getText().toString())+ "' WHERE invoice_no = '" + lot_invoicenoup.getText().toString() + "' AND MaterialCodeBoxSeqID = '" + boxseqidup.getText().toString() + "' AND Date = '" + dateToday.getText().toString() + "' ";
                        Statement stmt =  con.createStatement();
                        stmt.execute(query+query2);

                        Toast.makeText(getApplicationContext(),"Successfully updated!", Toast.LENGTH_SHORT).show();

                    }




                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(),ex.toString(), Toast.LENGTH_LONG).show();

                }


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
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pendingIntent =
                        PendingIntent.getActivity(LotFormActivity.this, 0, intent, 0);
                boxseqholder = boxseqidup.getText().toString();
                invoiceholder = lot_invoicenoup.getText().toString();
                partnameholder = tv_partnameup.getText().toString();
                partnumholder  = et_partnumup.getText().toString();
                goodscodeholder = goodscup.getText().toString();
                totalquantholder = totalquantityup .getText().toString();
                lotnumholder = lotnoup.getText().toString();
                dateHolder = dateToday.getText().toString();
                actualquantityHolder  = quantityrecievedup.getText().toString();
                lotQuantityholder = lotquantup.getText().toString();
                boxnumholder = boxnumup.getText().toString();
                samplesizeHolder   = sampsizeup.getText().toString();
                rejectHolderr = rejectUp.getText().toString();

                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }



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
                updateSQLData();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


    public void samplenumberenabled(){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                     //  quantityrecievedup.setText(lotquantup.getText() + "");
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        };


            lotquantup.addTextChangedListener(textWatcher);

    }

    // TODO: 08/04/2022      ahhhh check yung date today kung nag iinsert ba sa database server
    void savetoSQLite(){


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
                    REJECT =    rejectUp.getText().toString().trim();
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


    void updateSQLData(){
        try{
            connectionClass = new ConnectionClass();
            Connection con = connectionClass.CONN2();

            String query = " UPDATE LotNumber SET invoice_no = '" + lot_invoicenoup.getText().toString() + "', part_no = '"+et_partnumup.getText().toString()+ "', part_name = '"+tv_partnameup.getText().toString()+ "', total_quantity = '"+totalquantityup.getText().toString()+ "', quantity_recieved = '"+quantityrecievedup.getText().toString()+ "', lot_no = '"+lotnoup.getText().toString()+ "', lot_quantity = '"+lotquantup.getText().toString()+ "', box_number = '"+boxnumup.getText().toString()+ "', reject = '"+rejectUp.getText().toString()+ "', sample_size = '"+sampsizeup.getText().toString()+ "' WHERE Date = '" + dateToday.getText().toString() + "' AND MaterialCodeBoxSeqID = '" + boxseqidup.getText().toString() + "'";
            Statement stmt =  con.createStatement();
            stmt.execute(query);

            Toast.makeText(getApplicationContext(),"Successfully updated!", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }


    void confirmDialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload data to  " + " SQL SERVER " + "?");
        builder.setMessage("Are you sure you want to Proceed? this can't be undone ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                saveToSQLSERVER();
                totalQuantofReject();
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
                      Connection con = connectionClass.CONN2();
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

    void totalQuantofReject(){



        try{
            connectionClassss = new ConnectionClass();
            Connection con = connectionClass.CONN2();
            String query = "  SELECT SUM(reject)  as sumreject  FROM LotNumber  WHERE invoice_no= '"+lot_invoicenoup.getText().toString()+"' AND MaterialCodeBoxSeqID  = '"+boxseqidup.getText().toString()+"'";
            PreparedStatement stmtt = con.prepareStatement(query);
            ResultSet rs = stmtt.executeQuery();
            while(rs.next()){
                String totalSum = rs.getString("sumreject");
                rejectHolder = totalSum;
            }

            String query2 = " UPDATE LotNumber SET DIFF = '" + rejectHolder+ "' WHERE invoice_no = '" + lot_invoicenoup.getText().toString() + "' AND MaterialCodeBoxSeqID = '" + boxseqidup.getText().toString() + "' AND Date = '" + dateToday.getText().toString() + "' ";
            Statement stmt =  con.createStatement();
            stmt.execute(query2);

            rejectUp.setText(rejectHolder);
                Toast.makeText(LotFormActivity.this, "success", Toast.LENGTH_SHORT).show();




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
            rejectUp.setText(REJECT);
            sampsizeup.setText(SAMPLE);
            remarksup.setText(REMARKS);
            dateToday.setText(DATETIME);

        }

    }


}