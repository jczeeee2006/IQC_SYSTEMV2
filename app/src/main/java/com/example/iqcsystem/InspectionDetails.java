package com.example.iqcsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InspectionDetails extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //-----------------BUTTON INITIALIZATION START------------------
    Button InspectNext;
    ImageView logout;

    //-----------------BUTTON INITIALIZATION END------------------


    //-----------------CONNECTION INITIALIZATION START--------------
    static ConnectionClass connectionClass;
    //-----------------CONNECTION INITIALIZATION END--------------


    //-----------------INSPECTION DATA VARIABLE INITIALIZATION START--------------

    EditText checkedby, preparedby,approveby, temp, assemblyline, partnum, humidity, supplier,maker,  inspector, materialtype, partname, invoicequant;
    EditText  inspection_type, sample_size, Prepared;

    //------------SPINNER INITIALIZATION START------------------
    public EditText compliance;
    public Spinner comp_spinner;
    public ArrayAdapter comp_adapter;

    public EditText prod;
    public Spinner prod_spinner;
    public ArrayAdapter prod_adapter;

    public EditText materialType;
    public Spinner material_spinner;
    public ArrayAdapter material_adapter;


    public EditText ULmark;
    public Spinner ulmark_spinner;
    public ArrayAdapter ulmark_adapter;

    public EditText oir;
    public Spinner oir_spinner;
    public ArrayAdapter oir_adapter;

    public EditText coc;
    public Spinner coc_spinner;
    public ArrayAdapter coc_adapter;

    public EditText testreport;
    public Spinner tr_spinner;
    public ArrayAdapter tr_adapter;

    public EditText inspectreport;
    public Spinner inspect_spinner;
    public ArrayAdapter inspect_adapter;

    public AutoCompleteTextView invoicenum;
    public AutoCompleteTextView goodc;
    public Spinner invspinner;




    //------------SPINNER INITIALIZATION END------------------


    public EditText inspectedDate;
    ImageView cal4;
    public TextView recievedDate;
    ImageView cal5;
    ImageView calprepared;


    //-----------------INSPECTION DATA VARIABLE INITIALIZATION END--------------



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection_details);

        // Invoice Autocomplete fetching ng data from SQL TO APP

        connectionClass = new ConnectionClass();

        checkedby = findViewById(R.id.et_checkedby);
        preparedby = findViewById(R.id.et_preparedby);
        approveby = findViewById(R.id.et_approvedby);
        temp = findViewById(R.id.et_temp);
        temp.setEnabled(false);
        temp.setBackgroundColor(Color.parseColor("#d2d9d9"));
        assemblyline = findViewById(R.id.et_assline);
        maker = findViewById(R.id.et_maker);
        invoicenum = findViewById(R.id.et_invoicenum);
        goodc = findViewById(R.id.et_materialcode);
        partnum = findViewById(R.id.et_partnum);
        compliance = findViewById(R.id.et_compliance);
        humidity = findViewById(R.id.et_humidity);
        humidity.setEnabled(false);
        humidity.setBackgroundColor(Color.parseColor("#d2d9d9"));
        supplier = findViewById(R.id.et_supplier);

        inspector = findViewById(R.id.et_inspector);

        materialtype = findViewById(R.id.et_mattype);
        inspection_type = findViewById(R.id.et_inspectiontype);
        sample_size = findViewById(R.id.et_samplesize);
        ULmark = findViewById(R.id.et_ulmarking);
        coc = findViewById(R.id.et_coc);
        partname = findViewById(R.id.et_partname);
        invoicequant = findViewById(R.id.et_invoicequantity);

        preparedby.setText(MainActivity.prepBy);

        inspector.setText(MainActivity.prepBy);

        String date_now = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        EditText datee = findViewById(R.id.et_inspecteddate);
        datee.setText(date_now);

        String date_now1 = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        EditText dateee = findViewById(R.id.et_preparedddate);
        dateee.setText(date_now1);



        // TRAP FOR SET ERROR
        InspectNext = findViewById(R.id.btn_inspectnext);
        InspectNext.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

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
                    insert_inspectionDetails();

                }
            }
        });
        Button logout = findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InspectionDetails.this);
                builder.setMessage("Are you sure you want go to back?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(InspectionDetails.this, LotNumber.class);
                        startActivity(intent);

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                //creating alert dialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        //-----------PUBLIC VOID CALLING-------------------
      //  spinsupplier();
        suupplier();
        temp.setText(temp_hum("Temperature"));
        humidity.setText(temp_hum("Humidity"));
        DateReceived(LotNumber.invoicenumholder);
        invoicenum.setText(LotNumber.invoicenumholder);
        goodc.setText(LotNumber.materialholder);
        partname.setText(LotNumber.partnameholder);
        partnum.setText(LotNumber.partnumholder);
        // sample_size.setText(String.valueOf(LotNumber.samplesizeholder));




        //----------------Spinner Code Start----------------



        //RoHS Compliance Spinner
        compliance = findViewById(R.id.et_compliance);
        comp_spinner = findViewById(R.id.compliance_spinner);

        comp_adapter = ArrayAdapter.createFromResource(this, R.array.RoHS_compliance, android.R.layout.simple_spinner_item);
        comp_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        comp_spinner.setAdapter(comp_adapter);

        comp_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#eeeeee"));
                ((TextView) parent.getChildAt(0)).setTextSize(12);
                comp_selected();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        //-------------------------------------MATERIAL TYPE DROPDOWN------------------------
        //------------------------------------MATERIAL TYPE TO---------------------------
        materialType = findViewById(R.id.et_mattype);
        material_spinner = findViewById(R.id.material_spinner);

        material_adapter = ArrayAdapter.createFromResource(this, R.array.material, android.R.layout.simple_spinner_item);
        material_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        material_spinner.setAdapter(material_adapter);

        material_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#eeeeee"));
                ((TextView) parent.getChildAt(0)).setTextSize(5);
                material_selected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        //Production Type Spinner
        prod = findViewById(R.id.et_prodtype);
        prod_spinner = findViewById(R.id.prodtype_spinner);

        prod_adapter = ArrayAdapter.createFromResource(this, R.array.Production_type, android.R.layout.simple_spinner_item);
        prod_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prod_spinner.setAdapter(prod_adapter);


        prod_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#eeeeee"));
                prod_selected();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        //UL MAARKING ON LABEL Spinner
        ULmark = findViewById(R.id.et_ulmarking);
        ulmark_spinner = findViewById(R.id.ulmarking_spinner);

        ulmark_adapter = ArrayAdapter.createFromResource(this, R.array.UL_marking, android.R.layout.simple_spinner_item);
        ulmark_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ulmark_spinner.setAdapter(ulmark_adapter);

        ulmark_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#eeeeee"));
                ulmark_selected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        //UL MAARKING ON LABEL Spinner
        ULmark = findViewById(R.id.et_ulmarking);
        ulmark_spinner = findViewById(R.id.ulmarking_spinner);

        ulmark_adapter = ArrayAdapter.createFromResource(this, R.array.UL_marking, android.R.layout.simple_spinner_item);
        ulmark_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ulmark_spinner.setAdapter(ulmark_adapter);

        ulmark_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                ulmark_selected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //OIR Spinner
        oir = findViewById(R.id.et_oir);
        oir_spinner = findViewById(R.id.OIR_spinner);

        oir_adapter = ArrayAdapter.createFromResource(this, R.array.OIR, android.R.layout.simple_spinner_item);
        oir_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        oir_spinner.setAdapter(oir_adapter);

        oir_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                oir_selected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //CoC Spinner
        coc = findViewById(R.id.et_coc);
        coc_spinner = findViewById(R.id.coc_spinner);

        coc_adapter = ArrayAdapter.createFromResource(this, R.array.CoC, android.R.layout.simple_spinner_item);
        coc_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coc_spinner.setAdapter(coc_adapter);
        coc_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                coc_selected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //test REPORT
        testreport = findViewById(R.id.et_testreport);
        tr_spinner = findViewById(R.id.testreport_spinner);

        tr_adapter = ArrayAdapter.createFromResource(this, R.array.Test_report, android.R.layout.simple_spinner_item);
        tr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tr_spinner.setAdapter(tr_adapter);
        tr_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {


                testreport_selected();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //INSPECT REPORT
        inspectreport = findViewById(R.id.et_inspectiontype);
        inspect_spinner = findViewById(R.id.inspection_spinner);

        inspect_adapter = ArrayAdapter.createFromResource(this, R.array.Inspection_type, android.R.layout.simple_spinner_item);
        inspect_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        inspect_spinner.setAdapter(inspect_adapter);
        inspect_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                ((TextView)parent.getChildAt(0)).setTextColor(Color.parseColor("#eeeeee"));
                ((TextView) parent.getChildAt(0)).setTextSize(1);
                inspection_type();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // CHECKPOINT ALPHANUMERIC GENERATING
       /* preparedby.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0) {
                    inspector.setText("");
                    prpared_hldr = preparedby.getText().toString();
                    inspector.setText(inspector.getText().toString() + " " + prpared_hldr);
                }

                else
                {
                    inspector.setText("");
                }
            }
        });
*/


        //------------------- DATE PICKER CODE -------------------------------

        //CHECKED DATE DATEPICKER



        //prepared DATE DATEPICKER

        Prepared = findViewById(R.id.et_preparedddate);
        calprepared = findViewById(R.id.prepared_datepicker);


        calprepared.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Calendar Cal = Calendar.getInstance();
                final int year = Cal.get(Calendar.YEAR);
                final int month = Cal.get(Calendar.MONTH);
                final int day = Cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(InspectionDetails.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                        Prepared.setText((month + 1) + " /" + year + "/ " + day);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        //Approved DATE DATEPICKER



        //INSPECTED DATE DATEPICKER


        inspectedDate = findViewById(R.id.et_inspecteddate);
        cal4 = findViewById(R.id.inspected_datepicker);

        cal4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar Cal = Calendar.getInstance();
                final int year = Cal.get(Calendar.YEAR);
                final int month = Cal.get(Calendar.MONTH);
                final int day = Cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(InspectionDetails.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                        inspectedDate.setText((month + 1) + " /" + year + "/ " + day);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        //RECIEVED DATE DATEPICKER

        recievedDate = findViewById(R.id.et_recieveddate);
        cal5 = findViewById(R.id.recieved_datepicker);

        cal5.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Calendar Cal = Calendar.getInstance();
                final int year = Cal.get(Calendar.YEAR);
                final int month = Cal.get(Calendar.MONTH);
                final int day = Cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(InspectionDetails.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int day, int month, int year) {
                        recievedDate.setText((month + 1) + " /" + year + "/ " + day);
                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });

        //--------------- Date Picker Buttons END-----------------------


    }

    //-------SUPPLIER LIST
    public void suupplier(){
        final AutoCompleteTextView SUPPLIER = findViewById(R.id.et_supplier);
        connectionClass = new ConnectionClass();

        try{
            Connection con = connectionClass.CONN();//open ng connection sa connection class
            String query = "select DISTINCT (SuppName) from tblSupplierName";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            final ArrayList<String> invoice = new ArrayList<String>();
            while (rs.next()){
                String invoicenumber = rs.getString("SuppName");
                invoice.add(invoicenumber);
            }


            final ArrayAdapter<String> invoice_array = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, invoice);
            SUPPLIER.setThreshold(1);
            SUPPLIER.setAdapter(invoice_array);

            SUPPLIER.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();
                    SUPPLIER.setText(item);


                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });




        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    //-----------DATE RECEIVED
    public String DateReceived(String invoice)
    {
        String output = "";
        connectionClass = new ConnectionClass();

        try {
            Connection con2 = connectionClass.CONN2();//open ng connection sa connection class
            String query = "select TransactionDate from tblInventory_WHReceived WHERE InvoiceNumber= '" + invoice + "'";
            PreparedStatement stmt = con2.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String transactionDate = rs.getString("TransactionDate");
                output = transactionDate;
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        return output;
    }

    //----- TEMP AND HUMID---------------
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


  /*  //--------------SPINNER SUPPLIER
    public void spinsupplier()
    {
        connectionClass = new ConnectionClass();

        try{
            Connection con = connectionClass.CONN();//open ng connection sa connection class
            String query = "select DISTINCT (SuppName) from tblSupplierName";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            ArrayList<String> invoice = new ArrayList<String>();
            while (rs.next()){
                String invoicenumber = rs.getString("SuppName");
                invoice.add(invoicenumber);
            }


            Spinner suplier = findViewById(R.id.supplier_spinner);
            ArrayAdapter<String> invoice_array = new ArrayAdapter(this, android.R.layout.simple_list_item_1, invoice);
            suplier.setAdapter(invoice_array);
            suplier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });


        }catch (Exception ex){
            ex.printStackTrace();
        }

    }*/

    // COMP_SELECTED YES OR NO
    private void comp_selected()
    {
        String rohscomp = comp_spinner.getSelectedItem().toString();
        compliance.setEnabled(false);

        if (rohscomp.equals("")) {
            compliance.setText("");
        }

        if (rohscomp.equals("Yes")) {
            compliance.setText("Yes");

        }
        if (rohscomp.equals("No")) {
            compliance.setText("No");
        }

    }

    private void material_selected()
    {
        String material_Type = material_spinner.getSelectedItem().toString();
        materialType.setEnabled(false);

        if (material_Type.equals("")) {
            materialType.setText("");
        }

        if (material_Type.equals("PLASTIC")) {
            materialType.setText("PLASTIC");

        }
        if (material_Type.equals("METAL")) {
            materialType.setText("METAL");
        }
        if (material_Type.equals("ELECTRICAL")) {
            materialType.setText("ELECTRICAL");

        }
        if (material_Type.equals("COMPONENTS")) {
            materialType.setText("COMPONENTS");
        }
        if (material_Type.equals("OTHERS")) {
            materialType.setText("OTHERS");
        }

    }

    // PROD SELLECTED YED OR NO
    private void prod_selected()
    {
        String prodtype = prod_spinner.getSelectedItem().toString();
        prod.setEnabled(false);
        if (prodtype.equals("")) {
            prod.setText("");
        }

        if (prodtype.equals("SP")) {
            prod.setText("SP");

        }
        if (prodtype.equals("PP")) {
            prod.setText("PP");
        }
        if (prodtype.equals("MP")) {
            prod.setText("MP");
        }

    }

    //UL MARK YES OR NO
    private void ulmark_selected()

    {
        String ulmark = ulmark_spinner.getSelectedItem().toString();
        ULmark.setEnabled(false);
        if (ulmark.equals("")) {
            ULmark.setText("");
        }

        if (ulmark.equals("Yes")) {
            ULmark.setText("Yes");

        }
        if (ulmark.equals("No")) {
            ULmark.setText("No");
        }
        if (ulmark.equals("NA")) {
            ULmark.setText("NA");
        }

    }

    // OIR SELECTED

    private void oir_selected()

    {
        String OIR = oir_spinner.getSelectedItem().toString();
        oir.setEnabled(false);
        if (OIR.equals("")) {
            oir.setText("");
        }

        if (OIR.equals("Yes")) {
            oir.setText("Yes");

        }
        if (OIR.equals("No")) {
            oir.setText("No");
        }

    }

    //COC SELECTED
    private void coc_selected()

    {
        String COC = coc_spinner.getSelectedItem().toString();
        coc.setEnabled(false);
        if (COC.equals("")) {
            coc.setText("");
        }

        if (COC.equals("Yes")) {
            coc.setText("Yes");

        }
        if (COC.equals("No")) {
            coc.setText("No");
        }

    }

    //TEST REPORT SELECTED
    private void testreport_selected()

    {
        String TR = tr_spinner.getSelectedItem().toString();

        if (TR.equals("")) {
            testreport.setText("");
        }

        if (TR.equals("Yes")) {
            testreport.setText("Yes");

        }
        if (TR.equals("No")) {
            testreport.setText("No");
        }

    }

    //---INSPECTION TYPE
    private void inspection_type()

    {
        String inspectiontype = inspect_spinner.getSelectedItem().toString();

        if (inspectiontype.equals("")) {
            inspectreport.setText("");
        }

        if (inspectiontype.equals("NORMAL INSPECTION")) {
            inspectreport.setText("NORMAL INSPECTION");

        }
        if (inspectiontype.equals("TIGHTEN INSPECTION")) {
            inspectreport.setText("TIGHTEN INSPECTION");
        }
        if (inspectiontype.equals("100% INSPECTION")) {
            inspectreport.setText("100% INSPECTION");
        }

        if (inspectiontype.equals("LABEL AND QUANTITY")) {
            inspectreport.setText("LABEL AND QUANTITY");
        }

    }



    //--------------------INSERTING INSPECTION DETAILS------------------
    public void insert_inspectionDetails()

    {

        String prepare = preparedby.getText().toString();
        String prepareddate = Prepared.getText().toString();
        String temperature = temp.getText().toString();
        String assemline = assemblyline.getText().toString();
        String invioceno = invoicenum.getText().toString();
        String partno = partnum.getText().toString();
        String rohscomp = compliance.getText().toString();
        String humid =humidity.getText().toString();
        String inspectdate = inspectedDate.getText().toString();
        String receivdate = recievedDate.getText().toString();
        String supplierr = supplier.getText().toString();
        String makerr = maker.getText().toString();
        String inspectorr  = inspector.getText().toString();
        String materialType = materialtype.getText().toString();
        String prodType = prod.getText().toString();
        String inspectType = inspection_type.getText().toString();
        String Oir = oir.getText().toString();
        String testReport = testreport.getText().toString();
        String sampSize = sample_size.getText().toString();
        String ULMarking = ULmark.getText().toString();
        String Coc = coc.getText().toString();
        String partName = partname.getText().toString();
        String invoiceQuant = invoicequant.getText().toString();
        String Gd = goodc.getText().toString();

        if (Gd.trim().equals("")||partName.trim().equals("")||  partno.trim().equals("")|| invioceno.trim().equals("")|| sampSize.trim().equals("") || testReport.trim().equals("")||makerr.trim().equals("") || assemline.trim().equals("") || makerr.trim().equals("") ||  inspectType.trim().equals("") || Oir.trim().equals("") || materialType.trim().equals("") ||sampSize.trim().equals("")||prepare.trim().equals("")||prodType.trim().equals("") || prodType.trim().equals("") || ULMarking.trim().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Input all fields!", Toast.LENGTH_LONG).show();

        }
        else {
            try {
                connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN();

                String query = "INSERT INTO inspectiondata (prepared,  prepared_date, temperature, assembly_line, invoice_no, part_number, rohs_compliance, humidity, inspected_date, recieved_date, supplier, maker, inspector, material_type, production_type, inspection_type, oir, test_report, sample_size, ul_marking, coc, partname, invoicequant, goodsCode, MaterialCodeBoxSeqID) values ('" + prepare + "','" + prepareddate + "','" + temperature + "','" + assemline + "','" + invioceno + "','" + partno + "','" + rohscomp + "','" + humid + "','" + inspectdate + "','" + receivdate + "','" + supplierr + "','" + makerr + "','" + inspectorr + "','" + materialType + "','" + prodType + "','" + inspectType + "','" + Oir + "','" + testReport + "','" + sampSize + "','" + ULMarking + "','" + Coc + "','" + partName + "','" + invoiceQuant + "','" + Gd + "','" +LotNumber.boxseqholder + "')";
                Statement stmt = con.createStatement();
                stmt.execute(query);



                AlertDialog.Builder builder = new AlertDialog.Builder(InspectionDetails.this);
                builder.setMessage("Are you sure you want to proceed?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Successfully added!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(InspectionDetails.this, DimensionalCheck.class);
                        startActivity(intent);

                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                //creating alert dialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        return;
    }


}
