package com.example.iqcapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class FunctionalActivity extends AppCompatActivity {

    EditText upperSpecfc,lowerSpecfc,sammpleUnitfc,sampleSizefc,instrumentusedfc, checkPointfc;
    TextView fc_Minimum,fc_Maximum,fc_Average,fc_Judgemen,dateTodayfc;

    EditText fc1,fc2,fc3,fc4,fc5,fc6,fc7,fc8,fc9,fc10;

    AutoCompleteTextView instrumentUsed;

    public ArrayAdapter dcinstrument_adapter;

    Button addDatafc,uploadtosqlitefc,nextFomrmfc ;

    ImageButton helpbuttton;

    float num1 = 0;
    float num2 = 0;
    float num3 = 0;
    float num4 = 0;
    float num5 = 0;
    float num6 = 0;
    float num7 = 0;
    float num8 = 0;
    float num9 = 0;
    float num10 = 0;

    public static int ctr = 1, samplesize_id_hldr=0, dimcheck_id_hldr = 0, sampleSizeDC = 0;
    public  static String judgeHolder = "PASSED", colorHolder = "#58f40b";
    public static boolean fourinstrument = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functional);
        instrumentusedfc = findViewById(R.id.instrumentUsedfc);
        checkPointfc   = findViewById(R.id.checkPointfc);
        sammpleUnitfc  = findViewById(R.id.sampleUnit);
        sampleSizefc = findViewById(R.id.sampleSizefc_);
        checkPointfc  = findViewById(R.id.checkPointfc);
        lowerSpecfc   = findViewById(R.id.lowerspecfc);
        upperSpecfc = findViewById(R.id.upperspecsfc);

        fc_Minimum = findViewById(R.id.minimumfc);
        fc_Maximum = findViewById(R.id.maximumfc);
        fc_Average = findViewById(R.id.averagefc);
        fc_Judgemen = findViewById(R.id.fcjudgeMent);
        dateTodayfc = findViewById(R.id.dateTodayfc);

        fc1  = findViewById(R.id.fc1);
        fc2  = findViewById(R.id.fc2);
        fc3  = findViewById(R.id.fc3);
        fc4  = findViewById(R.id.fc4);
        fc5  = findViewById(R.id.fc5);
        fc6  = findViewById(R.id.fc6);
        fc7  = findViewById(R.id.fc7);
        fc8  = findViewById(R.id.fc8);
        fc9  = findViewById(R.id.fc9);
        fc10  = findViewById(R.id.fc10);

        nextFomrmfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        uploadtosqlitefc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addDatafc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



    }

    public void disableTexts() {
        fc2.setEnabled(false);
        fc3.setEnabled(false);
        fc4.setEnabled(false);
        fc5.setEnabled(false);
        fc6.setEnabled(false);
        fc7.setEnabled(false);
        fc8.setEnabled(false);
        fc9.setEnabled(false);
        fc10.setEnabled(false);

    }


    public void samplenumberenabled(){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(sampleSizefc.getText().toString().equals("2")){
                    fc2.setEnabled(true);
                }else if(sampleSizefc.getText().toString().equals("")){
                    fc2.setEnabled(false);
                    fc3.setEnabled(false);
                    fc4.setEnabled(false);
                    fc5.setEnabled(false);
                    fc6.setEnabled(false);
                    fc7.setEnabled(false);
                    fc8.setEnabled(false);
                    fc9.setEnabled(false);
                    fc10.setEnabled(false);
                }

                if(sampleSizefc.getText().toString().equals("3")){
                    dc2.setEnabled(true);
                    dc3.setEnabled(true);
                }else if(sampleSizefc.getText().toString().equals("")){
                    fc2.setEnabled(false);
                    fc3.setEnabled(false);
                    fc4.setEnabled(false);
                    fc5.setEnabled(false);
                    fc6.setEnabled(false);
                    fc7.setEnabled(false);
                    fc8.setEnabled(false);
                    fc9.setEnabled(false);
                    fc10.setEnabled(false);
                }
                if(sampleSizefc.getText().toString().equals("4")){
                    dc2.setEnabled(true);
                    dc3.setEnabled(true);
                    dc4.setEnabled(true);
                }else if(sampleSizefc.getText().toString().equals("")){
                    fc2.setEnabled(false);
                    fc3.setEnabled(false);
                    fc4.setEnabled(false);
                    fc5.setEnabled(false);
                    fc6.setEnabled(false);
                    fc7.setEnabled(false);
                    fc8.setEnabled(false);
                    fc9.setEnabled(false);
                    fc10.setEnabled(false);
                }

                if(sampleSizefc.getText().toString().equals("5")){
                    dc2.setEnabled(true);
                    dc3.setEnabled(true);
                    dc4.setEnabled(true);
                    dc5.setEnabled(true);
                }else if(sampleSizefc.getText().toString().equals("")){
                    fc2.setEnabled(false);
                    fc3.setEnabled(false);
                    fc4.setEnabled(false);
                    fc5.setEnabled(false);
                    fc6.setEnabled(false);
                    fc7.setEnabled(false);
                    fc8.setEnabled(false);
                    fc9.setEnabled(false);
                    fc10.setEnabled(false);
                }


                if(sampleSizefc.getText().toString().equals("6")){
                    fc2.setEnabled(true);
                    dc3.setEnabled(true);
                    dc4.setEnabled(true);
                    dc5.setEnabled(true);
                    dc6.setEnabled(true);
                }else if(sampleSizefc.getText().toString().equals("")){
                    fc2.setEnabled(false);
                    fc3.setEnabled(false);
                    fc4.setEnabled(false);
                    fc5.setEnabled(false);
                    fc6.setEnabled(false);
                    fc7.setEnabled(false);
                    fc8.setEnabled(false);
                    fc9.setEnabled(false);
                    fc10.setEnabled(false);
                }
                if(sampleSizefc.getText().toString().equals("7")) {
                    dc2.setEnabled(true);
                    dc3.setEnabled(true);
                    dc4.setEnabled(true);
                    dc5.setEnabled(true);
                    dc6.setEnabled(true);
                    dc7.setEnabled(true);

                }else if(sampleSizefc.getText().toString().equals("")){
                    fc2.setEnabled(false);
                    fc3.setEnabled(false);
                    fc4.setEnabled(false);
                    fc5.setEnabled(false);
                    fc6.setEnabled(false);
                    fc7.setEnabled(false);
                    fc8.setEnabled(false);
                    fc9.setEnabled(false);
                    fc10.setEnabled(false);
                }

                if(sampleSizefc.getText().toString().equals("8")) {
                    dc2.setEnabled(true);
                    dc3.setEnabled(true);
                    dc4.setEnabled(true);
                    dc5.setEnabled(true);
                    dc6.setEnabled(true);
                    dc7.setEnabled(true);
                    dc8.setEnabled(true);
                }else if(sampleSizefc.getText().toString().equals("")){
                    fc2.setEnabled(false);
                    fc3.setEnabled(false);
                    fc4.setEnabled(false);
                    fc5.setEnabled(false);
                    fc6.setEnabled(false);
                    fc7.setEnabled(false);
                    fc8.setEnabled(false);
                    fc9.setEnabled(false);
                    fc10.setEnabled(false);
                }
                if(sampleSizefc.getText().toString().equals("9")) {
                    dc2.setEnabled(true);
                    dc3.setEnabled(true);
                    dc4.setEnabled(true);
                    dc5.setEnabled(true);
                    dc6.setEnabled(true);
                    dc7.setEnabled(true);
                    dc8.setEnabled(true);
                    dc9.setEnabled(true);
                }else if(sampleSizefc.getText().toString().equals("")){
                    fc2.setEnabled(false);
                    fc3.setEnabled(false);
                    fc4.setEnabled(false);
                    fc5.setEnabled(false);
                    fc6.setEnabled(false);
                    fc7.setEnabled(false);
                    fc8.setEnabled(false);
                    fc9.setEnabled(false);
                    fc10.setEnabled(false);
                }
                if(sampleSizefc.getText().toString().equals("10")) {
                    dc2.setEnabled(true);
                    dc3.setEnabled(true);
                    dc4.setEnabled(true);
                    dc5.setEnabled(true);
                    dc6.setEnabled(true);
                    dc7.setEnabled(true);
                    dc8.setEnabled(true);
                    dc9.setEnabled(true);
                    dc10.setEnabled(true);
                }else if(sampleSizefc.getText().toString().equals("")){
                    fc2.setEnabled(false);
                    fc3.setEnabled(false);
                    fc4.setEnabled(false);
                    fc5.setEnabled(false);
                    fc6.setEnabled(false);
                    fc7.setEnabled(false);
                    fc8.setEnabled(false);
                    fc9.setEnabled(false);
                    fc10.setEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        sampleSizefc.addTextChangedListener(textWatcher);
        fc1.addTextChangedListener(textWatcher);
        fc2 .addTextChangedListener(textWatcher);
        fc3.addTextChangedListener(textWatcher);
        fc4.addTextChangedListener(textWatcher);
        fc5.addTextChangedListener(textWatcher);
        fc6.addTextChangedListener(textWatcher);
        fc7.addTextChangedListener(textWatcher);
        fc8.addTextChangedListener(textWatcher);
        fc9.addTextChangedListener(textWatcher);
        fc10.addTextChangedListener(textWatcher);
    }

}