package com.example.iqcapplication.Update;

import static com.example.iqcapplication.MainActivity.connectionClass;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iqcapplication.ConnectionClass;
import com.example.iqcapplication.DatabaseHelper;
import com.example.iqcapplication.DimensionalActivity;
import com.example.iqcapplication.MainActivity;
import com.example.iqcapplication.R;
import com.example.iqcapplication.SapmpleActivityinlot;
import com.example.iqcapplication.VisualInspection;
import com.example.iqcapplication.add.InspectionDetailsActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InspectionActivity extends AppCompatActivity {
    Button updatedata,uploadData,backform;

    EditText invoicenum, preparedby, temp, assemblyline, partnum,partName, humidity, supplier,maker,  goodc;
    EditText dateeee, inspector, dateinspected,samplesize,datereceived,invoicequant, oir,inspecttype,testreport,mattype,inscoc,rohscomp,prodtype,ulmarking,dateToday,boxseqins;

    ConnectionClass connectionClass;

     String insid,  prepared,  prepareddate, insinvoicenum,  insgoods, inspartname,  insinvoicequant,  insassyline,
     inspatnum, instemp, insrohs,  insdateinspected,  inshumid,  insupp, inspectorrs,  insdatereceived,
     insmaker, inssample,  insmaterial,  insinspecttype,  insulmarking,  insoir,  inscocs,  instestreport,prodttpes,dateTodayString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);

        //----call this functions--------//
        valueNames();
        getIntentData();

        //-----BUTTON CLICKS-----------//
        updatedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog2();
            }
        });

        uploadData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog3();
            }
        });
        backform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InspectionActivity.this, InspectionDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pendingIntent =
                        PendingIntent.getActivity(InspectionActivity.this, 0, intent, 0);
                try {
                    pendingIntent.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void valueNames(){
        updatedata = findViewById(R.id.updatedata2);
        connectionClass = new ConnectionClass();

        preparedby = findViewById(R.id.prepared);
        dateeee = findViewById(R.id.dateinspectup);
        temp = findViewById(R.id.temperature);
        assemblyline = findViewById(R.id.assyLineup);
        maker = findViewById(R.id.makerup);

        invoicenum = findViewById(R.id.invoiceNumberinsup);
        goodc = findViewById(R.id.goodsCodeinspup);
        partnum = findViewById(R.id.partNuminspecup);
        partName = findViewById(R.id.partName);
        humidity = findViewById(R.id.humidity);

        supplier = findViewById(R.id.supplierup);
        oir = findViewById(R.id.oirup);
        testreport = findViewById(R.id.testreportup);
        mattype = findViewById(R.id.mattypeup);
        inscoc = findViewById(R.id.cocup);

        supplier = findViewById(R.id.supplierup);
        inspecttype   = findViewById(R.id.inspecttypeup);
        rohscomp = findViewById(R.id.rohsup);
        prodtype = findViewById(R.id.prodtypeup);
        ulmarking = findViewById(R.id.ulMarkingup);

        inspector = findViewById(R.id.inspectorup);
        samplesize = findViewById(R.id.sampleSizeinspectup);
        dateinspected = findViewById(R.id.dateinsp);
        datereceived = findViewById(R.id.dateReceivedup);
        invoicequant = findViewById(R.id.invoiceQuantityup);
        boxseqins = findViewById(R.id.boxseqinspection);
        updatedata = findViewById(R.id.updatedata2);
        uploadData = findViewById(R.id.button3);
        backform = findViewById(R.id. inspectnextbutton);
        dateToday = findViewById(R.id.dateToday1);
        boxseqins.setText(SapmpleActivityinlot.boxseqholder);
    }

    void confirmDialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload data to  " + " SQL SERVER " + "?");
        builder.setMessage("Are you sure you want to Proceed? this can't be undone ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                saveToSQLSERVER();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }


   void sqlLiteDatabase(){


                try{
                    DatabaseHelper myDB = new DatabaseHelper(InspectionActivity.this);

                    prepared = preparedby.getText().toString().trim();
                    prepareddate = dateeee.getText().toString().trim();
                    insinvoicenum  =     invoicenum.getText().toString().trim();
                    insgoods  = goodc.getText().toString().trim();
                    inspartname = partName.getText().toString().trim();
                    insinvoicequant  = invoicequant.getText().toString().trim();
                    insassyline  = assemblyline.getText().toString().trim();
                    inspatnum = partnum.getText().toString().trim();
                    instemp   = temp.getText().toString().trim();
                    insrohs   = rohscomp.getText().toString().trim();
                    insdateinspected  = dateinspected.getText().toString().trim();
                    inshumid  =  humidity.getText().toString().trim();
                    insupp    = supplier.getText().toString().trim();
                    inspectorrs  =  inspector.getText().toString().trim();
                    insdatereceived  =  datereceived.getText().toString().trim();
                    insmaker =  maker.getText().toString().trim();
                    inssample  =  samplesize.getText().toString().trim();
                    insmaterial  =  mattype.getText().toString().trim();
                    insinspecttype  = inspecttype.getText().toString().trim();
                    insulmarking  = ulmarking.getText().toString().trim();
                    insoir  =  oir.getText().toString().trim();
                    inscocs    =  inscoc.getText().toString().trim();
                    prodttpes  = prodtype.getText().toString().trim();
                    instestreport =  testreport.getText().toString().trim();
                    dateTodayString  =  dateToday.getText().toString().trim();

                    myDB.updateinspectdata(insid,  prepared,  prepareddate, insinvoicenum,  insgoods, inspartname,  insinvoicequant,  insassyline,
                            inspatnum, instemp, insrohs,  insdateinspected,  inshumid,  insupp, inspectorrs,  insdatereceived,
                            insmaker, inssample,  insmaterial,  insinspecttype,  insulmarking,  insoir,  inscocs,prodttpes,  instestreport, dateTodayString);
                    Toast.makeText(InspectionActivity.this, "Successfully inserted", Toast.LENGTH_SHORT).show();


                }catch (Exception e){
                    Toast.makeText(InspectionActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

    }


    void saveToSQLSERVER() {

                String prepared = preparedby.getText().toString();
                try{
                    connectionClass = new ConnectionClass();
                    Connection con = connectionClass.CONN2();
                    String query = "SELECT * FROM inspectiondata WHERE  Date =  '"+ dateToday.getText().toString() +"' ";
                    PreparedStatement stmtt = con.prepareStatement(query);
                    ResultSet rs = stmtt.executeQuery();
                    if(rs.next()){

                        //  String time = rs.getString("Time");
                        Toast.makeText(InspectionActivity.this, "Data already existing in SQL Database", Toast.LENGTH_SHORT).show();
                    }
                   else{
                       connectionClass = new ConnectionClass();
                        Connection conn = connectionClass.CONN2();
                        String query2 = "INSERT INTO inspectiondata (prepared,  prepared_date, temperature, assembly_line, invoice_no, part_number, rohs_compliance, humidity, inspected_date, recieved_date, supplier, maker, inspector, material_type, production_type, inspection_type, oir, test_report, sample_size, ul_marking, coc, partname, invoicequant, goodsCode, MaterialCodeBoxSeqID, Date) values ('" + prepared + "','" + dateeee.getText().toString() + "','" + instemp + "','" + insassyline + "','" + invoicenum.getText().toString() + "','" + inspatnum + "','" + insrohs + "','" + inshumid + "','" + dateinspected.getText().toString() + "','" + datereceived.getText().toString() + "','" + insupp + "','" + insmaker + "','" + inspectorrs + "','" + insmaterial + "','" + prodttpes + "','" + insinspecttype + "','" + insoir + "','" + instestreport + "','" + inssample + "','" + insulmarking + "','" + inscocs + "','" + inspartname + "','" + insinvoicequant + "','" + insgoods + "','" + SapmpleActivityinlot.boxseqholder + "', '" + dateToday.getText().toString()+ "')";
                        Statement stmt = conn.createStatement();
                        stmt.execute(query2);

                    }


                }catch(Exception e ){
                    Toast.makeText(InspectionActivity.this,e.toString(), Toast.LENGTH_SHORT).show();
                }

    }

    void confirmDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("UPDATE " + " DATA " + "?");
        builder.setMessage("Are you sure you want to UPDATE DATA?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                sqlLiteDatabase();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }





    void getIntentData(){
        if(getIntent().hasExtra("idins_txt") && getIntent().hasExtra("prepared_txt") && getIntent().hasExtra("prepareddate_txt") && getIntent().hasExtra("invoiceIns_txt") && getIntent().hasExtra("goodsCodeIns_txt")
                && getIntent().hasExtra("partNameIns_txt") && getIntent().hasExtra("invoiceQuantins_txt") && getIntent().hasExtra("assylineins_txt") && getIntent().hasExtra("partNumIns_txt") && getIntent().hasExtra("tempIns_txt")
                && getIntent().hasExtra("rohsins_txt") && getIntent().hasExtra("dateinspect_txt") && getIntent().hasExtra("humidIns_txt")  && getIntent().hasExtra("supplierins_txt")  && getIntent().hasExtra("inspectorins_txt")
                && getIntent().hasExtra("date_received_txt")  && getIntent().hasExtra("makerins_txt")  && getIntent().hasExtra("sampleSizeIns_txt")  && getIntent().hasExtra("materialin_txtcocins_txt")  && getIntent().hasExtra("inspectypeins_txt")
                && getIntent().hasExtra("ul_Marking_txt")  && getIntent().hasExtra("oirIns_txt")  && getIntent().hasExtra("productionins_txt")  && getIntent().hasExtra("itestReportIns_txtd")  && getIntent().hasExtra("cocins_txt")  && getIntent().hasExtra("dateToday_txt")){

            insid  = getIntent().getStringExtra("idins_txt");
            prepared  = getIntent().getStringExtra("prepared_txt");
            prepareddate  = getIntent().getStringExtra("prepareddate_txt");
            insinvoicenum  = getIntent().getStringExtra("invoiceIns_txt");
            insgoods  = getIntent().getStringExtra("goodsCodeIns_txt");

            inspartname  = getIntent().getStringExtra("partNameIns_txt");
            insinvoicequant  = getIntent().getStringExtra("invoiceQuantins_txt");
            insassyline  = getIntent().getStringExtra("assylineins_txt");
            inspatnum  = getIntent().getStringExtra("partNumIns_txt");
            instemp  = getIntent().getStringExtra("tempIns_txt");

            insrohs  = getIntent().getStringExtra("rohsins_txt");
            insdateinspected  = getIntent().getStringExtra("dateinspect_txt");
            inshumid  = getIntent().getStringExtra("humidIns_txt");
            insupp  = getIntent().getStringExtra("supplierins_txt");
            inspectorrs  = getIntent().getStringExtra("inspectorins_txt");

            insdatereceived  = getIntent().getStringExtra("date_received_txt");
            insmaker  = getIntent().getStringExtra("makerins_txt");
            inssample  = getIntent().getStringExtra("sampleSizeIns_txt");
            insmaterial  = getIntent().getStringExtra("materialin_txtcocins_txt");
            insinspecttype  = getIntent().getStringExtra("inspectypeins_txt");

            insulmarking  = getIntent().getStringExtra("ul_Marking_txt");
            insoir  = getIntent().getStringExtra("oirIns_txt");
            inscocs  = getIntent().getStringExtra("cocins_txt");
            prodttpes  = getIntent().getStringExtra("productionins_txt");
            instestreport  = getIntent().getStringExtra("itestReportIns_txtd");
            dateTodayString   = getIntent().getStringExtra("dateToday_txt");




            preparedby.setText(prepared);
            dateeee.setText(prepareddate);
            temp.setText(instemp);
            assemblyline.setText(insassyline);
            maker.setText(insmaker);

            invoicenum.setText(insinvoicenum);
            goodc.setText(insgoods);
            partnum.setText(inspatnum);
            partName.setText(inspartname);
            humidity.setText(inshumid);

            supplier.setText(insupp);
            oir.setText(insoir);
            testreport.setText(instestreport);
            mattype.setText(insmaterial);
            inscoc.setText(inscocs);


            ulmarking.setText(insulmarking);
            prodtype.setText(prodttpes);
            rohscomp.setText(insrohs);
            inspecttype.setText(insinspecttype);

            invoicequant.setText(insinvoicequant);
            datereceived.setText(insdatereceived);
            dateinspected.setText(insdateinspected);
            dateToday.setText(dateTodayString);
            samplesize.setText(inssample);
            inspector.setText(inspectorrs);

        }
    }
}