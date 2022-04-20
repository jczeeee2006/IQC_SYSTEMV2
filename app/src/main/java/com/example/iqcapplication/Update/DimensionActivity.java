package com.example.iqcapplication.Update;

import static com.example.iqcapplication.MainActivity.connectionClass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class DimensionActivity extends AppCompatActivity {
    EditText dc_checkPoints,upperSpec,lowerSpec,sammpleUnit,dcsampleSize;

    TextView dc_Minimum,dc_Maximum,dc_Average,dc_Judgemen,dateToday,tvinvoice;

    EditText dc1,dc2,dc3,dc4,dc5,dc6,dc7,dc8,dc9,dc10;
    AutoCompleteTextView instrumentUsed;

    String id,instrumentUsedstring,  samplenum,  checkpoint,  samplUnit,  sample1,  sample2,  sample3,  sample4 ,  sample5,  sample6,
     sample7,  sample8,  sample9, sample10, lower,  upper,  max,  min,  average,  judgement,gdatedim;


    public static int  dimcheck_id_hldr = 0, samplesize_id_hldr=0;


    Button addData,uploadtosqlite,backbutton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dimension);


        sammpleUnit = findViewById(R.id.sampleUnitup);
        addData = findViewById(R.id.addDatafc);

        //-----SAMPLE VALUES---
        dc1 = findViewById(R.id.dc1up);
        dc2 = findViewById(R.id.dc2up);
        dc3 = findViewById(R.id.dc3up);
        dc4 = findViewById(R.id.dc4up);
        dc5 = findViewById(R.id.dc5up);
        dc6 = findViewById(R.id.dc6up);
        dc7 = findViewById(R.id.dc7up);
        dc8 = findViewById(R.id.dc8up);
        dc9 = findViewById(R.id.dc9up);
        dc10 = findViewById(R.id.dc10up);
        instrumentUsed = findViewById(R.id.instrumentUsedup);

        //-----MIN MAX AVERAGE
        dc_Minimum = findViewById(R.id.minimumup);
        dc_Maximum = findViewById(R.id.maximumup);
        dc_Average = findViewById(R.id.average_txtup);
        dc_Judgemen = findViewById(R.id.dcjudgeMentup);
        //--- upper and lower specs
        upperSpec = findViewById(R.id.upperspecsup);
        lowerSpec = findViewById(R.id.lowerspecup);
        dc_checkPoints = findViewById(R.id.checkPointup);
        dcsampleSize = findViewById(R.id.sampleSizedc_up);
        dateToday  = findViewById(R.id.datedimup);
        addData = findViewById(R.id.addDatafc);
        tvinvoice = findViewById(R.id.textView_invoiceno);
        uploadtosqlite = findViewById(R.id.uploadfc);
        backbutton = findViewById(R.id.backbuttondim);
        getIntentData();
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DimensionActivity.this, DimensionalActivity.class);

                startActivity(intent);
            }
        });




        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert_dimcheck();
                insert_sampleSize();

            }
        });

        uploadtosqlite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDatainSQlite();
            }
        });

    }



    public void insert_sampleSize(){

        try{
            connectionClass = new ConnectionClass();
            Connection con = connectionClass.CONN();
            String query1 = "INSERT INTO SampleSize (dimension_sample_size, goodsCode,invoice_number,MaterialCodeBoxSeqID) values ('"+dcsampleSize.getText().toString()+"', '"+SapmpleActivityinlot.materialholder+"', '"+SapmpleActivityinlot.invoicenumholder+"', '"+SapmpleActivityinlot.boxseqholder+"')";
            Statement stmt = con.createStatement();
            stmt.execute(query1);
            samplesize_id_hldr = Latest_ID("SampleSize");
            dimcheck_id_hldr = Latest_ID("DimensionalCheck");
        }catch (Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }


    public void updateDatainSQlite(){
        try{
            DatabaseHelper myDB = new DatabaseHelper(DimensionActivity.this);
            Intent intent = new Intent(DimensionActivity.this, InspectionDetailsActivity.class);
            instrumentUsedstring =    instrumentUsed.getText().toString().trim();
            samplenum  = dcsampleSize.getText().toString().trim();
            checkpoint = dc_checkPoints.getText().toString().trim();
            samplUnit  = sammpleUnit.getText().toString().trim();

            sample1 = dc1.getText().toString().trim();
            sample2 = dc2.getText().toString().trim();
            sample3 = dc3.getText().toString().trim();
            sample4 = dc4.getText().toString().trim();
            sample5 = dc5.getText().toString().trim();
            sample6 = dc6.getText().toString().trim();
            sample7 = dc7.getText().toString().trim();
            sample8 = dc8.getText().toString().trim();
            sample9 = dc9.getText().toString().trim();
            sample10 = dc10.getText().toString().trim();

            lower = lowerSpec.getText().toString().trim();
            upper = upperSpec.getText().toString().trim();

            min = dc_Minimum.getText().toString().trim();
            average = dc_Average.getText().toString().trim();
            max = dc_Maximum.getText().toString().trim();

            judgement = dc_Judgemen.getText().toString().trim();
            gdatedim = dateToday.getText().toString().trim();
                    myDB.updateDc( id, instrumentUsedstring, samplenum,  checkpoint,  samplUnit,  sample1,  sample2,  sample3,  sample4 ,  sample5,  sample6,
                             sample7,  sample8,  sample9, sample10, lower,  upper,  max,  min,  average,  judgement,gdatedim);

            Toast.makeText(DimensionActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
            startActivity(intent);

        }catch(Exception e){
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }



    public void insert_dimcheck()
    {
        String Checkpoints = dc_checkPoints.getText().toString();
        String Instrumentused = instrumentUsed.getText().toString();
        String Sampleunit = sammpleUnit.getText().toString();
        String DC1 = dc1.getText().toString();
        String DC2 = dc2.getText().toString();
        String DC3 = dc3.getText().toString();
        String DC4 = dc4.getText().toString();
        String DC5 = dc5.getText().toString();
        String DC6 = dc6.getText().toString();
        String DC7 = dc7.getText().toString();
        String DC8 = dc8.getText().toString();
        String DC9 = dc9.getText().toString();
        String DC10 = dc10.getText().toString();
        String Min = dc_Minimum.getText().toString();
        String Ave = dc_Average.getText().toString();
        String Max = dc_Maximum.getText().toString();
        String LowerSpec = lowerSpec.getText().toString();
        String UppperSpec = upperSpec.getText().toString();
        String Judgmnt = dc_Judgemen.getText().toString();




        if (Checkpoints.trim().equals("")||Instrumentused.trim().equals(""))//dagdagan mo
        {
            Toast.makeText(getApplicationContext(), "Must input atleast 1 checkpoint!", Toast.LENGTH_LONG).show();
        }

        else {
            try {
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN();

                String query = "INSERT INTO DimensionalCheck (invoice_no, goodsCode, checkpoints, instrument_used, sample_unit, sample1, sample2, sample3, sample4, sample5, sample6, sample7, sample8, sample9, sample10, minimum, average, maximum, lower_spec_limit, upper_spec_limit, judgement,MaterialCodeBoxSeqID) values ('"+ SapmpleActivityinlot.invoicenumholder+"', '"+InspectionDetailsActivity.goodscodeholder+"', '"+Checkpoints+"','"+Instrumentused+"','"+Sampleunit+"','"+DC1+"','"+DC2+"','"+DC3+"','"+DC4+"','"+DC5+"','"+DC6+"','"+DC7+"','"+DC8+"','"+DC9+"','"+DC10+"','"+Min+"','"+Ave+"','"+Max+"','"+LowerSpec+"','"+UppperSpec+"','"+Judgmnt+"','"+SapmpleActivityinlot.boxseqholder+"')"   ;

                Statement stmt = con.createStatement();
                stmt.execute(query);
                dimcheck_id_hldr = Latest_ID("DimensionalCheck");
                Toast.makeText(this, "Successfully uploaded", Toast.LENGTH_SHORT).show();



            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
            }
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
    public void getIntentData(){
        if(getIntent().hasExtra("id") &&  getIntent().hasExtra("getInstrumentUsed") && getIntent().hasExtra("getCheckpointdc") &&  getIntent().hasExtra("getSampleUnit") &&
                getIntent().hasExtra("getSampleSize") &&  getIntent().hasExtra("getSample1dc") &&
                getIntent().hasExtra("getSample2dc") &&  getIntent().hasExtra("getSample3dc") &&  getIntent().hasExtra("getSample4dc") &&   getIntent().hasExtra("getSample5dc") &&
                getIntent().hasExtra("getSample6dc") &&  getIntent().hasExtra("getSample7dc") &&  getIntent().hasExtra("getSample8dc") &&  getIntent().hasExtra("getSample9dc") &&
                getIntent().hasExtra("getSample10dc") &&   getIntent().hasExtra("getUpperSpecs") && getIntent().hasExtra("getLoweSpecs") &&
        getIntent().hasExtra("getMinimum") &&   getIntent().hasExtra("getAverage") &&  getIntent().hasExtra("getMaximum") &&  getIntent().hasExtra("getJudgement") &&  getIntent().hasExtra("getdatedim")
        ){


            id   = getIntent().getStringExtra("id");
            instrumentUsedstring = getIntent().getStringExtra("getInstrumentUsed");
            checkpoint   = getIntent().getStringExtra("getCheckpointdc");
            samplUnit   = getIntent().getStringExtra("getSampleUnit");
            samplenum   = getIntent().getStringExtra("getSampleSize");

            sample1   = getIntent().getStringExtra("getSample1dc");
            sample2   = getIntent().getStringExtra("getSample2dc");
            sample3   = getIntent().getStringExtra("getSample3dc");
            sample4   = getIntent().getStringExtra("getSample4dc");
            sample5   = getIntent().getStringExtra("getSample5dc");

            sample6   = getIntent().getStringExtra("getSample6dc");
            sample7   = getIntent().getStringExtra("getSample7dc");
            sample8   = getIntent().getStringExtra("getSample8dc");
            sample9   = getIntent().getStringExtra("getSample9dc");
            sample10   = getIntent().getStringExtra("getSample10dc");

            upper   = getIntent().getStringExtra("getUpperSpecs");
            lower   = getIntent().getStringExtra("getLoweSpecs");
            min   = getIntent().getStringExtra("getMinimum");
            average   = getIntent().getStringExtra("getAverage");
            max   = getIntent().getStringExtra("getMaximum");
            judgement   = getIntent().getStringExtra("getJudgement");
            gdatedim = getIntent().getStringExtra("getdatedim");


            sammpleUnit.setText(samplUnit);
            dcsampleSize.setText(samplenum);
            instrumentUsed.setText(instrumentUsedstring);
            dc_checkPoints.setText(checkpoint);

            dc1.setText(sample1);
            dc2.setText(sample2);
            dc3.setText(sample3);
            dc4.setText(sample4);
            dc5.setText(sample5);
            dc6.setText(sample6);
            dc7.setText(sample7);
            dc8.setText(sample8);
            dc9.setText(sample9);
            dc10.setText(sample10);


            lowerSpec.setText(lower);
            upperSpec.setText(upper);

            dc_Minimum.setText(min);
            dc_Maximum.setText(max);
            dc_Average.setText(average);

            dc_Judgemen.setText(judgement);

            dateToday.setText(gdatedim);
        }
    }
}