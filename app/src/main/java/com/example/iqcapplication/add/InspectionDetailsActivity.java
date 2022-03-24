package com.example.iqcapplication.add;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iqcapplication.ConnectionClass;
import com.example.iqcapplication.DatabaseHelper;
import com.example.iqcapplication.DimensionalActivity;
import com.example.iqcapplication.R;
import com.example.iqcapplication.SapmpleActivityinlot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InspectionDetailsActivity extends AppCompatActivity {
    EditText invoicenum, preparedby, temp, assemblyline, partnum,partName, humidity, supplier,maker,  goodc;
    EditText dateeee, inspector, dateinspected,samplesize,datereceived,invoicequant;
    AutoCompleteTextView oir,inspecttype,testreport,mattype,inscoc,rohscomp,prodtype,ulmarking;
    Button nextForm,addData,uploadData;
    ConnectionClass connectionClass;

    final Calendar myCalendar= Calendar.getInstance();


    public ArrayAdapter inspecttypee,OIRR,testreportt,mattypee,cocc,rohscompp,prodadapter,uladpter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);


        connectionClass = new ConnectionClass();
        OOIR();

        preparedby = findViewById(R.id.prepared);
        dateeee = findViewById(R.id.dateinspect);
        temp = findViewById(R.id.temperature);
        temp.setEnabled(false);
        temp.setBackgroundColor(Color.parseColor("#d2d9d9"));
        assemblyline = findViewById(R.id.assyLine);
        maker = findViewById(R.id.maker);
        invoicenum = findViewById(R.id.invoiceNumber);
        goodc = findViewById(R.id.goodsCodeinsp);
        partnum = findViewById(R.id.partNuminspec);
        partName = findViewById(R.id.partName);
        humidity = findViewById(R.id.humidity);
        humidity.setEnabled(false);
        humidity.setBackgroundColor(Color.parseColor("#d2d9d9"));
        supplier = findViewById(R.id.supplier);


        oir = findViewById(R.id.oir);
        testreport = findViewById(R.id.testreport);
        mattype = findViewById(R.id.mattype);
        inscoc = findViewById(R.id.coc);
        supplier = findViewById(R.id.supplier);
        inspecttype   = findViewById(R.id.inspecttype);
        rohscomp = findViewById(R.id.rohs);
        prodtype = findViewById(R.id.prodtype);
        ulmarking = findViewById(R.id.ulMarking);

        inspector = findViewById(R.id.inspector);
        samplesize = findViewById(R.id.sampleSizeinspect);
        dateinspected = findViewById(R.id.dateinsp);
        datereceived = findViewById(R.id.dateReceived);
        invoicequant = findViewById(R.id.invoiceQuantity);
        addData = findViewById(R.id.button2);
        nextForm = findViewById(R.id.inspectnextbutton);

        rohs();
        prodtype();
        mattype();
        ulMarking();
        coc();
        testReport();
        inspecttype();

        temp.setText(temp_hum("Temperature"));
        humidity.setText(temp_hum("Humidity"));
        nextForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InspectionDetailsActivity.this, SapmpleActivityinlot.class);
                startActivity(intent);
            }
        });


        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertIntoSQLlite();
            }
        });

        //------showing calendar in date inspected--------
        DatePickerDialog.OnDateSetListener date =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };
        //------showing calendar in date today--------
        DatePickerDialog.OnDateSetListener dateee =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel1();
            }
        };

        DatePickerDialog.OnDateSetListener datereceive =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel2();
            }
        };


        dateeee.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                new DatePickerDialog(InspectionDetailsActivity.this,dateee,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        dateinspected.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                new DatePickerDialog(InspectionDetailsActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        datereceived.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(InspectionDetailsActivity.this,datereceive,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        nextForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(preparedby.length()==0)
                {
                    preparedby.setError("Enter Prepared By");
                }

                if(partnum.length()==0)
                {
                    partnum.setError("Enter Partnumber");
                }

                if(invoicequant.length()==0)
                {
                    invoicequant.setError("Input Invoice Quantity");
                }

                if(invoicequant.length()==0)
                {
                    invoicequant.setError("Input Invoice Quantity");
                }

                if(assemblyline.length()==0)
                {
                    assemblyline.setError("Input Assembly Line");
                }
                else{
                    Intent intent = new Intent(InspectionDetailsActivity.this, DimensionalActivity.class);
                    startActivity(intent);

                }
            }
        });


    }

    private void updateLabel(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        dateinspected.setText(dateFormat.format(myCalendar.getTime()));


    }
    private void updateLabel1(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        dateeee.setText(dateFormat.format(myCalendar.getTime()));
    }


    private void updateLabel2(){
        String myFormat="MM/dd/yy";
        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
        datereceived.setText(dateFormat.format(myCalendar.getTime()));
    }


    void OOIR(){
        AutoCompleteTextView oir = findViewById(R.id.oir);


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
        AutoCompleteTextView rohs = findViewById(R.id.rohs);


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
        AutoCompleteTextView prodtype = findViewById(R.id.prodtype);


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
        AutoCompleteTextView matype = findViewById(R.id.mattype);


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
        AutoCompleteTextView ulmarking = findViewById(R.id.ulMarking);


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
        AutoCompleteTextView cocauto = findViewById(R.id.coc);


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
        AutoCompleteTextView testReport = findViewById(R.id.testreport);


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
        AutoCompleteTextView inspectype = findViewById(R.id.inspecttype);


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
            String query = "SELECT TOP 1 "+colname+" FROM TempHumid_Log ORDER BY Day_Time DESC";
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





        if(preparedby.length()==0)
        {
            preparedby.setError("Enter Prepared By");
        }

        if(partnum.length()==0)
        {
            partnum.setError("Enter Partnumber");
        }

        if(invoicequant.length()==0)
        {
            invoicequant.setError("Input Invoice Quantity");
        }

        if(invoicequant.length()==0)
        {
            invoicequant.setError("Input Invoice Quantity");
        }

        if(assemblyline.length()==0)
        {
            assemblyline.setError("Input Assembly Line");
        }

        else{

            DatabaseHelper myDB = new DatabaseHelper(InspectionDetailsActivity.this);

            myDB.addinspection(
                    preparedby.getText().toString().trim(),
                    dateeee.getText().toString().trim(),
                    temp.getText().toString().trim(),
                    assemblyline.getText().toString().trim(),
                    maker.getText().toString().trim(),
                    invoicenum.getText().toString().trim(),
                    goodc.getText().toString().trim(),
                    partnum.getText().toString().trim(),
                    partName.getText().toString().trim(),
                    humidity.getText().toString().trim(),
                    supplier.getText().toString().trim(),
                    inspector.getText().toString().trim(),
                    samplesize.getText().toString().trim(),
                    dateinspected.getText().toString().trim(),
                    datereceived.getText().toString().trim(),
                    invoicequant.getText().toString().trim(),
                    rohscomp.getText().toString().trim(),
                    prodtype.getText().toString().trim(),
                    mattype.getText().toString().trim(),
                    ulmarking.getText().toString().trim(),
                    inscoc.getText().toString().trim(),
                    oir.getText().toString().trim(),
                    testreport.getText().toString().trim(),
                    inspecttype.getText().toString().trim());
        }
            Toast.makeText(InspectionDetailsActivity.this, "please fill up data", Toast.LENGTH_SHORT).show();

    }
}