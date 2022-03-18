package com.example.iqcapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Sample2darray extends AppCompatActivity {
    private LinearLayout lnrDynamicEditTextHolder;
    private EditText edtNoCreate,samplenum;
    private static Button btnCreate,btnsub;
    private Editable edittextholder;
    PreparedStatement stmt;
    ConnectionClass connectionClass;
    static int [][] initialArray = {

            { 1, 111, 22, 33, 44 },
            { 2, 21, 22, 23 ,24 },
            { 3, 31, 32, 33, 34 },
            { 4, 41, 41, 43, 44 },
            { 5, 51, 52, 53, 54 },

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample2darray);


        connectionClass = new ConnectionClass();
        lnrDynamicEditTextHolder = (LinearLayout) findViewById(R.id.layoutlist);
        edtNoCreate = (EditText) findViewById(R.id.chkpoint);
        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnsub =  (Button) findViewById(R.id.btnssub);
        getData();


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtNoCreate.getText().toString().length() > 0) {
                    try {
                        lnrDynamicEditTextHolder.removeAllViews();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }

                    int length = Integer.parseInt(edtNoCreate.getText().toString());

                    int ctr = 0;
                    for (int i = 0; i < length; i++) {
                        EditText editText = new EditText(Sample2darray.this);
                        editText.setId(i + 1);
                        editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        editText.setHint("EditText " + (i + 1));
                        lnrDynamicEditTextHolder.addView(editText);
                    }

                        btnsub.setOnClickListener(new View.OnClickListener() {

                            @Override

                            public void onClick(View v) {
                                String [] array = new String[lnrDynamicEditTextHolder.getChildCount()];
                                for(int j = 0 ; j < lnrDynamicEditTextHolder.getChildCount() ;j++) {

                                    EditText editText = (EditText)lnrDynamicEditTextHolder.getChildAt(j);

                                    array[j] = editText.getText().toString();
                                    Toast.makeText(Sample2darray.this,array[j], Toast.LENGTH_SHORT).show();

                                    try {
                                        connectionClass = new ConnectionClass();
                                        Connection con = connectionClass.CONN();
                                        con.setAutoCommit(false);
                                         stmt = con.prepareStatement( "INSERT INTO DimensionalCheck (sample1,sample2,sample3,sample4,sample5) values (?, ?, ?, ?, ?)");
                                        for(int i = 0; i < 5; i ++){
                                            stmt.setString(1, String.valueOf(initialArray[i][0]));
                                            stmt.setString(1, String.valueOf(initialArray[i][1]));
                                            stmt.setString(1, String.valueOf(initialArray[i][2]));
                                            stmt.setString(1, String.valueOf(initialArray[i][3]));
                                            stmt.setString(1, String.valueOf(initialArray[i][4]));
                                            for(int k = 0; j < 5; k ++) {
                                                stmt.setString(2, String.valueOf(initialArray[k][0]));
                                                stmt.setString(2, String.valueOf(initialArray[k][1]));
                                                stmt.setString(2, String.valueOf(initialArray[k][2]));
                                                stmt.setString(2, String.valueOf(initialArray[k][3]));
                                                stmt.setString(2, String.valueOf(initialArray[k][4]));
                                            }
                                            for(int l = 0; l < 5; l++) {
                                                stmt.setString(3, String.valueOf(initialArray[l][0]));
                                                stmt.setString(3, String.valueOf(initialArray[l][1]));
                                                stmt.setString(3, String.valueOf(initialArray[l][2]));
                                                stmt.setString(3, String.valueOf(initialArray[l][3]));
                                                stmt.setString(3, String.valueOf(initialArray[l][4]));

                                            }
                                            for(int m = 0; m < 5; m++) {
                                                stmt.setString(4, String.valueOf(initialArray[m][0]));
                                                stmt.setString(4, String.valueOf(initialArray[m][1]));
                                                stmt.setString(4, String.valueOf(initialArray[m][2]));
                                                stmt.setString(4, String.valueOf(initialArray[m][3]));
                                                stmt.setString(4, String.valueOf(initialArray[m][4]));


                                            }
                                            for(int xx = 0; xx < 5; xx++) {
                                                stmt.setString(5, String.valueOf(initialArray[xx][0]));
                                                stmt.setString(5, String.valueOf(initialArray[xx][1]));
                                                stmt.setString(5, String.valueOf(initialArray[xx][2]));
                                                stmt.setString(5, String.valueOf(initialArray[xx][3]));
                                                stmt.setString(5, String.valueOf(initialArray[xx][4]));

                                            }


                                            stmt.addBatch();
                                            stmt.executeBatch();
                                            con.commit();
                                        }



                                    } catch (Exception e) {
                                        Toast.makeText(Sample2darray.this, e.toString(), Toast.LENGTH_SHORT).show();
                                    }

                                }

                            }

                        });


                }
            }
        });


    }

    public void getData(){
        btnsub =  (Button) findViewById(R.id.btnssub);

    }



    /*public double[] columnSum(double [][] array){
        int index = 0;
        double temp[] = new double[array[index].length];

        for (int i = 0; i < array[i].length; i++){
            double sum = 0;

            for (int j = 0; j < array.length; j++){
                sum += array[j][i];

            }
            temp[index] = sum;
            System.out.println("Index is: " + index + " Sum is: "+sum);
            index++;

        }

        return temp;
    }*/



}