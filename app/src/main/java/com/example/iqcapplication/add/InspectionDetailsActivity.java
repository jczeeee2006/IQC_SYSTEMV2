package com.example.iqcapplication.add;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.iqcapplication.ConnectionClass;
import com.example.iqcapplication.DatabaseHelper;
import com.example.iqcapplication.DimensionalActivity;
import com.example.iqcapplication.MainActivity;
import com.example.iqcapplication.R;
import com.example.iqcapplication.SapmpleActivityinlot;
import com.example.iqcapplication.VisualInspection;
import com.example.iqcapplication.fragments.FragmentForInspection;
import com.example.iqcapplication.fragments.FragmentforLot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InspectionDetailsActivity extends AppCompatActivity {
    EditText invoicenum, preparedby, temp, assemblyline, partnum,partName, humidity, supplier,  goodc, dateeee, inspector, dateinspected,samplesize,
            datereceived,invoicequant,dateTodayins,boxsequenceins;
    AutoCompleteTextView oir,inspecttype,testreport,mattype,inscoc,rohscomp,prodtype,ulmarking,maker;
    Button nextForm,addData,showButton;
    ConnectionClass connectionClass;
    private int mYear,mMonth,mDay;

    public static String goodscodeholder,invoicenumholderr;
    final Calendar myCalendar= Calendar.getInstance();

    public ArrayAdapter inspecttypee,OIRR,testreportt,mattypee,cocc,rohscompp,prodadapter,uladpter,makerADapter ;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);
        Fragment fragment= new FragmentForInspection();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayoutins, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        connectionClass = new ConnectionClass();


        preparedby = findViewById(R.id.prepared);
        dateeee = findViewById(R.id.dateinspectup);
        temp = findViewById(R.id.temperature);
        assemblyline = findViewById(R.id.assyLineup);

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
        addData = findViewById(R.id.button2);
        showButton = findViewById(R.id.button3);
        maker = findViewById(R.id.makerup);
        nextForm = findViewById(R.id.inspectnextbutton);
        boxsequenceins = findViewById(R.id.boxseqinspection);
        dateTodayins = findViewById(R.id.dateToday1);



         //----------------------HUMIDITY-------------------------///

        humidity.setEnabled(false);

        temp.setEnabled(false);


        invoicenum.setText(SapmpleActivityinlot.invoicenumholder);
        goodc.setText(SapmpleActivityinlot.goodscodeholder);
        partName.setText(SapmpleActivityinlot.partnameholder);
        partnum.setText(SapmpleActivityinlot.partnumholder);
        boxsequenceins.setText(SapmpleActivityinlot.boxseqholder);
        goodscodeholder = goodc.getText().toString();
        invoicenumholderr  = invoicenum.getText().toString();
        temp.setText(temp_hum("Temperature"));
        humidity.setText(temp_hum("Humidity"));
        preparedby.setText(MainActivity.lastNameholder);
        inspector.setText(MainActivity.lastNameholder);
        inspector.setEnabled(false);
        preparedby.setEnabled(false);
        invoicenum.setEnabled(false);
        dateeee.setEnabled(false);
        goodc.setEnabled(false);
        partnum.setEnabled(false);
        partName.setEnabled(false);
        InspectionAsync inspectionAsync = new InspectionAsync();
        inspectionAsync.execute("");

    }

    public void buttons(){

        nextForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog2();
            }
        });
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog1();


            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new FragmentForInspection());
            }
        });

    }

    void confirmDialog1() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Upload " + " Data " + "?");
        builder.setMessage("Are you sure you want to Upload to this Device?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                  insertIntoSQLlite();


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


    void confirmDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Proceed to" + "next form" + "?");
        builder.setMessage("Are you sure you want to proceed to next form?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(InspectionDetailsActivity.this, DimensionalActivity.class);

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

    public void dateFormats(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",Locale.ENGLISH);
        String now = df.format(new Date());
        dateTodayins.setText(now);


        SimpleDateFormat dff = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
        String noww = dff.format(new Date());
        datereceived.setText(noww);

        SimpleDateFormat dfff = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
        String nowwww = dfff.format(new Date());
        dateinspected.setText(nowwww);

        SimpleDateFormat dffff = new SimpleDateFormat("yyyy/MM/dd",Locale.ENGLISH);
        String nowwwww = dffff.format(new Date());
        dateeee.setText(nowwwww);
    }


    public void invoiceQuantity(){

    }

    public void Maker() {

        makerADapter = ArrayAdapter.createFromResource(this, R.array.Makers, android.R.layout.simple_dropdown_item_1line);
        makerADapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        maker.setAdapter(makerADapter);

        maker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                maker.showDropDown();
            }
        });

    }

    public void suupplier() {
        final AutoCompleteTextView SUPPLIER = findViewById(R.id.supplierup);
        connectionClass = new ConnectionClass();

        try {
            Connection con = connectionClass.CONN4();//open ng connection sa connection class
            String query = "select *  from Receive  WHERE INVOICE = '"+ invoicenum.getText().toString() +"'  AND GOODS_CODE = '"+ goodc.getText().toString() +"'  AND PART_NAME = '"+ partName.getText().toString() +"' AND PART_NUMBER = '"+ partnum.getText().toString() +"'  ";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String supplier = rs.getString("SUPPLIER");
                String  invoicequantity = rs.getString("QTY");
                String  assylinee = rs.getString("ASY_LINE");
                SUPPLIER.setText(supplier);
                invoicequant.setText(invoicequantity);
                assemblyline.setText(assylinee);

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    public void myCustomDalog(){
        final Dialog MyDialog = new Dialog(InspectionDetailsActivity.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.customdialog);

        MyDialog.show();
    }



    void date(){
        dateeee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == dateeee){
                    final Calendar calendar = Calendar.getInstance();
                    mYear = calendar.get(Calendar.YEAR);
                    mMonth = calendar.get ( Calendar.MONTH );
                    mDay = calendar.get ( Calendar.DAY_OF_MONTH );

                    DatePickerDialog datePickerDialog = new DatePickerDialog ( InspectionDetailsActivity.this, new DatePickerDialog.OnDateSetListener () {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            dateeee.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                        }
                    }, mYear, mMonth, mDay );
                    datePickerDialog.show ();
                }

            }
        });

    }

  void date1(){
        dateinspected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == dateinspected){
                    final Calendar calendar = Calendar.getInstance();
                    mYear = calendar.get(Calendar.YEAR);
                    mMonth = calendar.get ( Calendar.MONTH );
                    mDay = calendar.get ( Calendar.DAY_OF_MONTH );

                    DatePickerDialog datePickerDialog = new DatePickerDialog ( InspectionDetailsActivity.this, new DatePickerDialog.OnDateSetListener () {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            dateinspected.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                        }
                    }, mYear, mMonth, mDay );
                    datePickerDialog.show ();
                }

            }
        });

    }

    void date2(){
        datereceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == datereceived){
                    final Calendar calendar = Calendar.getInstance();
                    mYear = calendar.get(Calendar.YEAR);
                    mMonth = calendar.get ( Calendar.MONTH );
                    mDay = calendar.get ( Calendar.DAY_OF_MONTH );

                    DatePickerDialog datePickerDialog = new DatePickerDialog ( InspectionDetailsActivity.this, new DatePickerDialog.OnDateSetListener () {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            datereceived.setText ( dayOfMonth + "/" + (month + 1) + "/" + year );
                        }
                    }, mYear, mMonth, mDay );
                    datePickerDialog.show ();
                }

            }
        });

    }




    private void replaceFragment(FragmentForInspection inspectFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayoutins,inspectFragment);
        fragmentTransaction.commit();

    }

    void OOIR(){
        AutoCompleteTextView oir = findViewById(R.id.oirup);


        OIRR = ArrayAdapter.createFromResource(this, R.array.OIR, android.R.layout.simple_dropdown_item_1line);
        OIRR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        oir.setAdapter(OIRR);

        oir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oir.showDropDown();
            }
        });
    }

    void rohs(){
        AutoCompleteTextView rohs = findViewById(R.id.rohsup);


        rohscompp = ArrayAdapter.createFromResource(this, R.array.RoHS_compliance, android.R.layout.simple_dropdown_item_1line);
        rohscompp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rohs.setAdapter(rohscompp);

        rohs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rohs.showDropDown();
            }
        });
    }

    void prodtype(){
        AutoCompleteTextView prodtype = findViewById(R.id.prodtypeup);


        prodadapter = ArrayAdapter.createFromResource(this, R.array.Production_type, android.R.layout.simple_dropdown_item_1line);
        prodadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prodtype.setAdapter(prodadapter);

        prodtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prodtype.showDropDown();
            }
        });
    }

    void mattype(){
        AutoCompleteTextView matype = findViewById(R.id.mattypeup);


        mattypee = ArrayAdapter.createFromResource(this, R.array.material, android.R.layout.simple_dropdown_item_1line);
        mattypee.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        matype.setAdapter(mattypee);

        matype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matype.showDropDown();
            }
        });
    }

    void ulMarking(){

        AutoCompleteTextView ulmarking = findViewById(R.id.ulMarkingup);
        uladpter = ArrayAdapter.createFromResource(this, R.array.UL_marking, android.R.layout.simple_dropdown_item_1line);
        uladpter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ulmarking.setAdapter(uladpter);

        ulmarking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ulmarking.showDropDown();
            }
        });
    }

    void coc(){

        AutoCompleteTextView cocauto = findViewById(R.id.cocup);
        cocc = ArrayAdapter.createFromResource(this, R.array.CoC, android.R.layout.simple_dropdown_item_1line);
        cocc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cocauto.setAdapter(uladpter);

        cocauto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cocauto.showDropDown();
            }
        });
    }


    void testReport(){

        AutoCompleteTextView testReport = findViewById(R.id.testreportup);
        testreportt = ArrayAdapter.createFromResource(this, R.array.Test_report, android.R.layout.simple_dropdown_item_1line);
        testreportt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        testReport.setAdapter(testreportt);
        testReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testReport.showDropDown();
            }
        });
    }

    void inspecttype(){

        AutoCompleteTextView inspectype = findViewById(R.id.inspecttypeup);
        inspecttypee = ArrayAdapter.createFromResource(this, R.array.Inspection_type, android.R.layout.simple_dropdown_item_1line);
        inspecttypee.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inspectype.setAdapter(inspecttypee);

        inspectype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inspectype.showDropDown();
            }
        });
    }
    public String temp_hum(String colname)
    {
        String output = "";
        connectionClass = new ConnectionClass();
        try {
            Connection con2 = connectionClass.CONN3();//open ng connection sa connection class
            String query = "SELECT TOP 1 "+colname+"  FROM TempHumid_Log ORDER BY ID DESC";
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String tem_hum = rs.getString(colname);

                output = tem_hum;

            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return output;
    }

    public void insertIntoSQLlite(){
        DatabaseHelper myDB = new DatabaseHelper(InspectionDetailsActivity.this);
    /* TODO eto yung issue sa hindi tama ang data na lumalabas */
            myDB.addinspection(
                    preparedby.getText().toString().trim(),
                    dateeee.getText().toString().trim(),
                    invoicenum.getText().toString().trim(),
                    goodc.getText().toString().trim(),
                    partName.getText().toString().trim(),

                    invoicequant.getText().toString().trim(),
                    assemblyline.getText().toString().trim(),
                    partnum.getText().toString().trim(),
                    temp.getText().toString().trim(),
                    rohscomp.getText().toString().trim(),

                    dateinspected.getText().toString().trim(),
                    humidity.getText().toString().trim(),
                    supplier.getText().toString().trim(),
                    inspector.getText().toString().trim(),
                    datereceived.getText().toString().trim(),

                    maker.getText().toString().trim(),
                    samplesize.getText().toString().trim(),
                    mattype.getText().toString().trim(),
                    inspecttype.getText().toString().trim(),
                    ulmarking.getText().toString().trim(),

                    oir.getText().toString().trim(),
                    inscoc.getText().toString().trim(),
                    prodtype.getText().toString().trim(),
                    testreport.getText().toString().trim(),
                    dateTodayins.getText().toString().trim(),
                    boxsequenceins.getText().toString().trim()
            );




    }


    public class InspectionAsync extends AsyncTask<String,String, String>{
        @Override
        protected void onPreExecute() {
            inspecttype();
            OOIR();
            rohs();
            prodtype();
            Maker();
            mattype();
            ulMarking();
            coc();
            testReport();
            date();
            date1();
            date2();
            buttons();
            dateFormats();
            suupplier();
            OOIR();
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


    @Override
    public void onBackPressed() {
        return;
    }


}