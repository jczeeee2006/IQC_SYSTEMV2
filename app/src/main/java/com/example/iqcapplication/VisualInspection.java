package com.example.iqcapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class VisualInspection extends AppCompatActivity {
    AutoCompleteTextView visualinsUsed;
    EditText visualsampleSize,visualCheckpoint, visualremarks, visualJudgement,visualDefectQuantity, visualdefectJudgement,visualdefectRemarks;

    Button appearanceOK, appearanceNG,visualAddDefect,visualnextForm,addDdefet2;

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
        addDdefet2 = findViewById(R.id.adddefect2);
        appearanceOK = findViewById(R.id.visualOk);
        appearanceNG = findViewById(R.id.failedd);
        visualAddDefect  = findViewById(R.id.adddefect);
        visualnextForm  = findViewById(R.id.nextFormvisual);

        visualInspecXt();


        appearanceOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmDialog3();

            }
        });

        appearanceNG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog4();
            }
        });

        visualAddDefect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog2();
            }
        });

        visualnextForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VisualInspection.this, FunctionalActivity2.class);
                update_judgement();
                insert_defect_AP();

                startActivity(intent);
            }
        });

        addDdefet2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                confirmDialog6();

            }
        });



    }



    //--------------------------INSTRUMENTUSED----------------------//
    public void visualInspecXt(){



            instrumentUsedd = ArrayAdapter.createFromResource(this, R.array.instrument_array1, android.R.layout.simple_dropdown_item_1line);
            instrumentUsedd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            visualinsUsed.setAdapter(instrumentUsedd);

            visualinsUsed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    visualinsUsed.showDropDown();
                }
            });

    }

    //--------------------------INSERT/UPDATE----------------------//
    public void insert_appearanceinspection(String Result) {
        String Sample_size = visualsampleSize.getText().toString();
        String IGCheckpoints = visualCheckpoint.getText().toString();
        String Instrumentused = visualinsUsed.getText().toString();
        //String Result = App_Result.getText().toString();
        //String Judgement = App_Judgement.getText().toString();
        String Remarkss = visualremarks.getText().toString();

        if (IGCheckpoints.trim().equals("")||Instrumentused.trim().equals("")|| Sample_size.trim().equals("")) {
            Toast.makeText(getApplicationContext(), "Fill up all fields!", Toast.LENGTH_LONG).show();
            visualJudgement.setText("");
        }
        else {
            try {
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN2();

                String query = "INSERT INTO Appearance_Inspection ( invoice_no, ig_checkpoints, instrument_used, result, remarks, goodsCode,MaterialCodeBoxSeqID) values ('"+SapmpleActivityinlot.invoicenumholder+"','"+IGCheckpoints+"','"+Instrumentused+"','"+Result+"','"+Remarkss+"','"+SapmpleActivityinlot.goodscodeholder+"','"+SapmpleActivityinlot.boxseqholder+"')";
                String query1 = "UPDATE SampleSize SET appearance_sample_size = '"+visualsampleSize.getText().toString()+"' WHERE id = '"+ DimensionalActivity.samplesize_id_hldr+"'";
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


    public void reset() {
        //apsamplesize.setText("");
        visualCheckpoint.setText("");

        visualremarks.setText("");

    }

    //--------------------------UPDATE OVER ALL JUDGEMENT----------------------//
    public void update_judgement() {
        String Judgement = visualJudgement.getText().toString();

        try {
            connectionClass = new ConnectionClass();
            Connection con = connectionClass.CONN2();

            for(int x = 0; x<id_list.size(); x++) {
                String query1 = "UPDATE Appearance_Inspection SET judgement = '" + Judgement + "' WHERE id = '" + id_list.get(x) + "'";
                Statement stmt = con.createStatement();
                stmt.execute(query1);
            }

            appinspectioncheck_id_hldr = Latest_ID();
            id_list.add(Latest_ID());
            // AlertSuccess();



        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();

        }

    }


    //--------------------------GET LATEST ID----------------------//
    public int Latest_ID() {
        int output = 0;

        connectionClass = new ConnectionClass();

        try {
            Connection con = connectionClass.CONN2();//open ng connection sa connection class
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




    //-------------INSERT QUERY TO SQL DATABASE--------------------//
    public void insert_defect_AP() {
        String Quantity = visualDefectQuantity.getText().toString();
        String Encountered = visualdefectJudgement.getText().toString();

        if (Quantity.trim().equals("") || Encountered.trim().equals("")){

        }

        else {

            try {
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN2();

                String query = "UPDATE Appearance_Inspection SET defectqty = '"+Quantity+"', defect_enc = '"+Encountered+"' WHERE invoice_no = '"+SapmpleActivityinlot.invoicenumholder+"' AND id = '"+VisualInspection.appinspectioncheck_id_hldr+"'";
                Statement stmt = con.createStatement();
                stmt.execute(query);

                Toast.makeText(getApplicationContext(),"Successfully added!", Toast.LENGTH_SHORT).show();

            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(),ex.toString(), Toast.LENGTH_LONG).show();

            }
        }
    }



    //--------------ADD DEFECT-------------------------------//
    void confirmDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ADD" + "DATA " + "?");
        builder.setMessage("Are you sure you want to add defect data ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                insert_defect_AP();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


    //------------------ADD AS PASSED-------------------------//
    void confirmDialog3() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ADD" + " AS PASSED" + "?");
        builder.setMessage("Are you sure you want to add  data, this can't be undone ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                insert_appearanceinspection("OK");
                visualJudgement.setText("PASSED");
                visualJudgement.setTextColor(Color.parseColor("#23f011"));
                reset();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    //------------------ADD AS FAILED-------------------------//
    void confirmDialog4() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ADD" +  " AS FAILED" + "?");
        builder.setMessage("Are you sure you want to add  data, this can't be undone ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                insert_appearanceinspection("NG");
                visualJudgement.setText("FAILED");
                visualJudgement.setTextColor(Color.parseColor("#FF0000"));
                reset();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }



    //------------------ADD REJECT-------------------------//
    void confirmDialog6() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ADD" +  " REJECT" + "?");
        builder.setMessage("Are you sure you want to switch form?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Intent intent = new Intent(VisualInspection.this, SapmpleActivityinlot.class);

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


    @Override
    public void onBackPressed() {
        return;
    }

}




