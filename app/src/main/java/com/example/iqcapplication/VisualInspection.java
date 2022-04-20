package com.example.iqcapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iqcapplication.Update.LotFormActivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class VisualInspection extends AppCompatActivity {
    AutoCompleteTextView visualinsUsed;
    EditText visualsampleSize,visualCheckpoint, visualremarks, visualJudgement,visualDefectQuantity, visualdefectJudgement,visualdefectRemarks;

    Button appearanceOK, appearanceNG,visualAddDefect,visualnextForm;

    public ConnectionClass connectionClass;

    public static int appinspectioncheck_id_hldr =0;
    public ArrayAdapter instrumentUsedd;
    ArrayList<Integer> id_list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visual_inspection);
        visualinsUsed = findViewById(R.id.visualInstrument);
        visualsampleSize = findViewById(R.id.visualSampleSize);
        visualCheckpoint = findViewById(R.id.visualcheckPoint);
        visualremarks = findViewById(R.id.visualRemarks);
        visualJudgement  = findViewById(R.id.visualJudgement);
        visualDefectQuantity  = findViewById(R.id.visualQuantitiy);
        visualdefectRemarks   = findViewById(R.id.visualRemarksdefect);
        visualdefectJudgement = findViewById(R.id.visualjudgementdefect);

        appearanceOK = findViewById(R.id.visualOk);
        appearanceNG = findViewById(R.id.failedd);
        visualAddDefect  = findViewById(R.id.adddefect);
        visualnextForm  = findViewById(R.id.nextFormvisual);

        visualInspect();
        displayButton();

        visualnextForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisualInspection.this, LotFormActivity.class);
                update_judgement();
                insert_defect_AP();
                startActivity(intent);
            }
        });
    }


    public void displayButton(){
        appearanceOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insert_appearanceinspection("OK");
                visualJudgement.setText("PASSED");
                visualJudgement.setTextColor(Color.parseColor("#58f40b"));
                reset();

            }
        });

        appearanceNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insert_appearanceinspection("NG");
                visualJudgement.setText("FAILED");
                visualJudgement.setTextColor(Color.parseColor("#FF0000"));
                reset();
            }
        });
    }

    public void visualInspect(){



            instrumentUsedd = ArrayAdapter.createFromResource(this, R.array.instrument_array, android.R.layout.simple_dropdown_item_1line);
            instrumentUsedd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            visualinsUsed.setAdapter(instrumentUsedd);

            visualinsUsed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    visualinsUsed.showDropDown();
                }
            });

    }


    public void insert_appearanceinspection(String Result)
    {
        String Sample_size = visualsampleSize.getText().toString();
        String IGCheckpoints = visualCheckpoint.getText().toString();
        String Instrumentused = visualinsUsed.getText().toString();
        //String Result = App_Result.getText().toString();
        //String Judgement = App_Judgement.getText().toString();
        String Remarkss = visualremarks.getText().toString();

        if (IGCheckpoints.trim().equals("")||Instrumentused.trim().equals("")|| Sample_size.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Fill up all fields!", Toast.LENGTH_LONG).show();
        }
        else {
            try {
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN();

                String query = "INSERT INTO Appearance_Inspection ( invoice_no, ig_checkpoints, instrument_used, result, remarks, goodsCode,MaterialCodeBoxSeqID) values ('"+SapmpleActivityinlot.invoicenumholder+"','"+IGCheckpoints+"','"+Instrumentused+"','"+Result+"','"+Remarkss+"','"+SapmpleActivityinlot.goodscodeholder+"','"+SapmpleActivityinlot.boxseqholder+"')";
                String query1 = "UPDATE SampleSize SET appearance_sample_size = '"+visualsampleSize.getText().toString()+"' WHERE id = '"+DimensionalActivity.samplesize_id_hldr+"'";
                Statement stmt = con.createStatement();
                stmt.execute(query+query1);

                Toast.makeText(getApplicationContext(), "Successfully added!",Toast.LENGTH_LONG).show();
                appinspectioncheck_id_hldr = Latest_ID();
                id_list.add(Latest_ID());
                //AlertSuccess();
            }
            catch (Exception ex) {
                //AlertFailed();
                Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    public void reset()
    {
        //apsamplesize.setText("");
        visualCheckpoint.setText("");
        visualJudgement.setText("");
        visualremarks.setText("");

    }

    public void update_judgement()
    {
        String Judgement = visualJudgement.getText().toString();

        try {
            connectionClass = new ConnectionClass();
            Connection con = connectionClass.CONN();

            for(int x = 0; x<id_list.size(); x++) {
                String query1 = "UPDATE Appearance_Inspection SET judgement = '" + Judgement + "' WHERE id = '" + id_list.get(x) + "'";
                Statement stmt = con.createStatement();
                stmt.execute(query1);
            }
            Toast.makeText(getApplicationContext(), "Successfully updated!", Toast.LENGTH_LONG).show();
            appinspectioncheck_id_hldr = Latest_ID();
            id_list.add(Latest_ID());
            // AlertSuccess();



        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();

        }

    }

    public int Latest_ID()
    {
        int output = 0;

        connectionClass = new ConnectionClass();

        try {
            Connection con = connectionClass.CONN();//open ng connection sa connection class
            String query = "SELECT TOP 1 id FROM Appearance_Inspection ORDER BY id DESC";
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

    //-------------INSERT QUERY TO SQL DATABASE--------------------
    public void insert_defect_AP() {
        String Quantity = visualDefectQuantity.getText().toString();
        String Encountered = visualdefectJudgement.getText().toString();

        if (Quantity.trim().equals("") || Encountered.trim().equals(""))
            Toast.makeText(getApplicationContext(),"Fillup required fields!", Toast.LENGTH_LONG).show();
        else {


            try {
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN();

                String query = "UPDATE Appearance_Inspection SET defectqty = '"+Quantity+"', defect_enc = '"+Encountered+"' WHERE invoice_no = '"+SapmpleActivityinlot.invoicenumholder+"' AND id = '"+VisualInspection.appinspectioncheck_id_hldr+"'";
                Statement stmt = con.createStatement();
                stmt.execute(query);

                Toast.makeText(getApplicationContext(),"Successfully added!", Toast.LENGTH_SHORT).show();

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(),ex.toString(), Toast.LENGTH_LONG).show();

            }
        }
    }



}