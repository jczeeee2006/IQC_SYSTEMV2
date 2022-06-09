package com.example.iqcapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "IQCDATABASE";
    private static final int DATABASE_VERSION = 28;

    private static final  String TABLE_NAME = "Lotnumberdb";
    private static final  String TABLE_NAME2 = "Dimensiondb";
    private static final  String TABLE_NAME3 = "Inspectiondb";
    private static final  String TABLE_NAME4 = "Visualdb";
    private static final  String TABLE_NAME5 = "Functiondb";

   // lot table
    private static final  String COLUMN_ID = "ID";
    private static final  String COLUMN_P_INVOICE = "INVOICE";
    private static final  String COLUMN_P_PART_NUMBER = "PART_NUMBER";
    public  static final  String COLUMN_P_GOODS_CODE = "GOODS_CODE";
    private static final  String COLUMN_P_PART_NAME = "PART_NAME";
    private static final  String COLUMN_P_BOX_NUMBER = "BOX_NUMBER";
    private static final  String COLUMN_P_BOXSEQ = "BOXSEQ";
    private static final  String COLUMN_P_ACTUALQUANT = "ACTUALQUANT";
    private static final  String COLUMN_P_TOTALQUANT = "TOTALQUANT";
    private static final  String COLUMN_P_REJECT = "REJECT";
    private static final  String COLUMN_P_SAMPLE = "SAMPLE";
    private static final  String COLUMN_P_LOTNUM = "LOTNUM";
    private static final  String COLUMN_P_LOTQUANT = "LOTQUANT";
    private static final  String COLUMN_P_REMARKS = "REMARKS";
    private static final  String COLUMN_P_DATETIME = "DATETIME";


    //dimension table
    private static final  String COLUMN_P_DCID = "DCID";
    private static final  String COLUMN_P_INSTRUMENT = "DCINSTRUMENT";
    private static final  String COLUMN_P_DCSAMPLENUM = "DCSAMPLENUM";
    private static final  String COLUMN_P_DCCHECKPOINT = "DCCHECKPOINT";
    private static final  String COLUMN_P_DCSAMPLEUNIT = "DCSAMPLEUNIT";

    private static final  String COLUMN_P_DCSAMPLE1 = "DCSAMPLE1";
    private static final  String COLUMN_P_DCSAMPLE2 = "DCSAMPLE2";
    private static final  String COLUMN_P_DCSAMPLE3 = "DCSAMPLE3";
    private static final  String COLUMN_P_DCSAMPLE4 = "DCSAMPLE4";
    private static final  String COLUMN_P_DCSAMPLE5 = "DCSAMPLE5";

    private static final  String COLUMN_P_DCSAMPLE6 = "DCSAMPLE6";
    private static final  String COLUMN_P_DCSAMPLE7 = "DCSAMPLE7";
    private static final  String COLUMN_P_DCSAMPLE8 = "DCSAMPLE8";
    private static final  String  COLUMN_P_DCSAMPLE9 = "DCSAMPLE9";
    private static final  String  COLUMN_P_DCSAMPLE10 = "DCSAMPLE10";

    private static final  String  COLUMN_P_LOWER = "LOWER";
    private static final  String  COLUMN_P_UPPER = "UPPER";
    private static final  String  COLUMN_P_MINIMUM = "MINIMUM";
    private static final  String  COLUMN_P_AVERAGE = "AVERAGE";
    private static final  String  COLUMN_P_MAXIMUM = "MAXIMUM";
    private static final  String  COLUMN_P_JUDGEMENT = "JUDGEMENT";
    private static final  String  COLUMN_P_DATEDIM = "DATE";


   //--------Function check----------------------//
    private static final  String COLUMN_P_FCID = "FCID";
    private static final  String COLUMN_P_FINSTRUMENT = "FCINSTRUMENT";
    private static final  String COLUMN_P_FCSAMPLENUM = "FCSAMPLENUM";
    private static final  String COLUMN_P_FCCHECKPOINT = "FCCHECKPOINT";
    private static final  String COLUMN_PF_FCSAMPLEUNIT = "FCSAMPLEUNIT";

    private static final  String COLUMN_P_FCSAMPLE1 = "FCSAMPLE1";
    private static final  String COLUMN_P_FCSAMPLE2 = "FCSAMPLE2";
    private static final  String COLUMN_P_FCSAMPLE3 = "FCSAMPLE3";
    private static final  String COLUMN_P_FCSAMPLE4 = "FCSAMPLE4";
    private static final  String COLUMN_P_FCSAMPLE5 = "FCSAMPLE5";

    private static final  String COLUMN_P_FCSAMPLE6 = "FCSAMPLE6";
    private static final  String COLUMN_P_FCSAMPLE7 = "FCSAMPLE7";
    private static final  String COLUMN_P_FCSAMPLE8 = "FCSAMPLE8";
    private static final  String  COLUMN_P_FCSAMPLE9 = "FCSAMPLE9";
    private static final  String  COLUMN_P_FCSAMPLE10 = "FCSAMPLE10";

    private static final  String  COLUMN_P_FLOWER = "FLOWER";
    private static final  String  COLUMN_P_FUPPER = "FUPPER";
    private static final  String  COLUMN_P_FMINIMUM = "FMINIMUM";
    private static final  String  COLUMN_P_FAVERAGE = "FAVERAGE";
    private static final  String  COLUMN_P_FMAXIMUM = "FMAXIMUM";
    private static final  String  COLUMN_P_FJUDGEMENT = "FJUDGEMENT";
    private static final  String  COLUMN_P_FDATEDIM = "FDATE";

    //inspection table
    private static final  String COLUMN_P_INSID = "INSID";
    private static final  String COLUMN_P_INSPREPARED = "INSPREPARED";
    private static final  String COLUMN_P_INSPREPAREDDATE = "INSPREPAREDDATE";
    private static final  String COLUMN_P_INSINVOICENUM = "INSINVOICENUM";
    private static final  String COLUMN_P_INSGOODS = "INSGOODS";
    private static final  String COLUMN_P_INSPARTNAME = "INSPARTNAME";
    private static final  String COLUMN_P_INSINVOICEQUANT = "INSINVOICEQUANT";
    private static final  String COLUMN_P_INSASSEMBLYLINE = "INSASSEMBLY";
    private static final  String COLUMN_P_INSPARTNUM = "INSPARTNUM";
    private static final  String COLUMN_P_INSTEMP = "INSTEMP";
    private static final  String COLUMN_P_INSROHS = "INSROHS";
    private static final  String COLUMN_P_INSDATEINSPECTED = "INSDATEINSPECT";
    private static final  String COLUMN_P_INSHUMID = "HUMID";
    private static final  String COLUMN_P_INSSUPP = "INSSUPP";
    private static final  String COLUMN_P_INSPECTOR = "INSPECTOR";
    private static final  String COLUMN_P_INSDATERECEIVED = "INSDATERECEIVED";
    private static final  String COLUMN_P_INSMAKER = "MAKER";
    private static final  String COLUMN_P_INSSAMPLE = "INSSAMPLE";
    private static final  String COLUMN_P_INSMATERIAL = "INSMATERIAL";
    private static final  String COLUMN_P_INSINSPECTTYPE = "INSINSPECTYPE";
    private static final  String COLUMN_P_INSULMARKING = "INSULMARKING";
    private static final  String COLUMN_P_INSOIR = "INSOIR";
    private static final  String COLUMN_P_INSCOC = "INSCOC";
    private static final  String COLUMN_P_INSPRODTYPE = "INSPRODTYPE";
    private static final  String COLUMN_P_INSTESTREPORT = "INSTESTREPORT";
    private static final  String COLUMN_P_DATE_TIMEE = "DATE_TIME";
    private static final  String COLUMN_P_BOXSEQID = "BOXSEQID";

    // appearance inspection
    private static final  String COLUMN_P_VISID = "ID";
    private static final  String COLUMN_P_VISINSTRUMENT = "INSTRUMENT_USED";
    private static final  String COLUMN_P_VISSAMPLESIZE = "SAMPLE_SIZE";
    private static final  String COLUMN_P_VICHECKPOINT = "CHECKPOINT";
    private static final  String COLUMN_P_VIREMARKS = "REMARKS";
    private static final  String COLUMN_P_VIJUDGEMENT = "JUDGEMENT";

    //TODO dagdagan ng column yung dim ng date time then fix yung inserting issue sa lahat then proceed sa visual checkpoint dapat matapso ko to by tomorrow


    public DatabaseHelper(@Nullable Context context) {

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String query  =
                "CREATE TABLE " + TABLE_NAME +
                        "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_P_INVOICE + " TEXT," +
                        COLUMN_P_PART_NUMBER + " TEXT," +
                        COLUMN_P_GOODS_CODE +" TEXT," +
                        COLUMN_P_PART_NAME + " TEXT," +
                        COLUMN_P_BOX_NUMBER + " TEXT," +
                        COLUMN_P_BOXSEQ + " TEXT," +
                        COLUMN_P_ACTUALQUANT + " TEXT," +
                        COLUMN_P_TOTALQUANT + " TEXT," +
                        COLUMN_P_REJECT + " TEXT," +
                        COLUMN_P_SAMPLE + " TEXT," +
                        COLUMN_P_LOTNUM + " TEXT," +
                        COLUMN_P_LOTQUANT + " TEXT," +
                        COLUMN_P_REMARKS  + " TEXT," +
                        COLUMN_P_DATETIME    + " TEXT );" ;


        String query1  =
                "CREATE TABLE " + TABLE_NAME2 +
                        "(" + COLUMN_P_DCID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_P_INSTRUMENT  + " TEXT," +
                        COLUMN_P_DCSAMPLENUM + " TEXT," +
                        COLUMN_P_DCCHECKPOINT + " TEXT," +
                        COLUMN_P_DCSAMPLEUNIT + " TEXT," +

                        COLUMN_P_DCSAMPLE1 + " TEXT," +
                        COLUMN_P_DCSAMPLE2 + " TEXT," +
                        COLUMN_P_DCSAMPLE3 + " TEXT," +
                        COLUMN_P_DCSAMPLE4 + " TEXT," +
                        COLUMN_P_DCSAMPLE5 + " TEXT," +
                        COLUMN_P_DCSAMPLE6 + " TEXT," +
                        COLUMN_P_DCSAMPLE7 + " TEXT," +
                        COLUMN_P_DCSAMPLE8 + " TEXT," +
                        COLUMN_P_DCSAMPLE9 + " TEXT," +
                        COLUMN_P_DCSAMPLE10 + " TEXT," +

                        COLUMN_P_LOWER + " TEXT," +
                        COLUMN_P_UPPER + " TEXT," +
                        COLUMN_P_MINIMUM  + " TEXT," +
                        COLUMN_P_AVERAGE  + " TEXT," +
                        COLUMN_P_MAXIMUM   + " TEXT," +
                        COLUMN_P_JUDGEMENT   + " TEXT," +
                        COLUMN_P_DATEDIM  + " TEXT );" ;


        String query2  =
                "CREATE TABLE " + TABLE_NAME3 +
                        "(" + COLUMN_P_INSID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_P_INSPREPARED + " TEXT," +
                        COLUMN_P_INSPREPAREDDATE + " TEXT," +
                        COLUMN_P_INSINVOICENUM + " TEXT," +
                        COLUMN_P_INSGOODS + " TEXT," +
                        COLUMN_P_INSPARTNAME + " TEXT," +
                        COLUMN_P_INSINVOICEQUANT + " TEXT," +
                        COLUMN_P_INSASSEMBLYLINE + " TEXT," +
                        COLUMN_P_INSPARTNUM + " TEXT," +
                        COLUMN_P_INSTEMP + " TEXT," +
                        COLUMN_P_INSROHS + " TEXT," +
                        COLUMN_P_INSDATEINSPECTED + " TEXT," +
                        COLUMN_P_INSHUMID + " TEXT," +
                        COLUMN_P_INSSUPP + " TEXT," +
                        COLUMN_P_INSPECTOR + " TEXT," +
                        COLUMN_P_INSDATERECEIVED + " TEXT," +
                        COLUMN_P_INSMAKER + " TEXT," +
                        COLUMN_P_INSSAMPLE + " TEXT," +
                        COLUMN_P_INSMATERIAL + " TEXT," +
                        COLUMN_P_INSINSPECTTYPE + " TEXT," +
                        COLUMN_P_INSULMARKING + " TEXT," +
                        COLUMN_P_INSOIR + " TEXT," +
                        COLUMN_P_INSCOC + " TEXT," +
                        COLUMN_P_INSPRODTYPE  + " TEXT," +
                        COLUMN_P_INSTESTREPORT   + " TEXT," +
                        COLUMN_P_DATE_TIMEE    + " TEXT," +
                        COLUMN_P_BOXSEQID + " TEXT);" ;

        String query3  =
                "CREATE TABLE " + TABLE_NAME4 +
                        "(" + COLUMN_P_VISID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_P_VISINSTRUMENT + " TEXT," +
                        COLUMN_P_VISSAMPLESIZE + " TEXT," +
                        COLUMN_P_VICHECKPOINT + " TEXT," +
                        COLUMN_P_VIREMARKS + " TEXT," +
                        COLUMN_P_VIJUDGEMENT + " TEXT);";



        String query4  =
                "CREATE TABLE " + TABLE_NAME5 +
                        "(" + COLUMN_P_FCID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_P_FINSTRUMENT  + " TEXT," +
                        COLUMN_P_FCSAMPLENUM + " TEXT," +
                        COLUMN_P_FCCHECKPOINT + " TEXT," +
                        COLUMN_PF_FCSAMPLEUNIT + " TEXT," +

                        COLUMN_P_FCSAMPLE1 + " TEXT," +
                        COLUMN_P_FCSAMPLE2 + " TEXT," +
                        COLUMN_P_FCSAMPLE3 + " TEXT," +
                        COLUMN_P_FCSAMPLE4 + " TEXT," +
                        COLUMN_P_FCSAMPLE5 + " TEXT," +
                        COLUMN_P_FCSAMPLE6 + " TEXT," +
                        COLUMN_P_FCSAMPLE7 + " TEXT," +
                        COLUMN_P_FCSAMPLE8 + " TEXT," +
                        COLUMN_P_FCSAMPLE9 + " TEXT," +
                        COLUMN_P_FCSAMPLE10 + " TEXT," +

                        COLUMN_P_FLOWER + " TEXT," +
                        COLUMN_P_FUPPER + " TEXT," +
                        COLUMN_P_FMINIMUM  + " TEXT," +
                        COLUMN_P_FAVERAGE  + " TEXT," +
                        COLUMN_P_FMAXIMUM   + " TEXT," +
                        COLUMN_P_FJUDGEMENT   + " TEXT," +
                        COLUMN_P_FDATEDIM  + " TEXT );" ;


        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(query2);
        db.execSQL(query3);
        db.execSQL(query4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME4);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME5);
        onCreate(db);


    }

    public void addData(String INVOICE,String PART_NUMBER, String GOODS_CODE,String PART_NAME,String BOX_NUMBER,String BOXSEQ,
                        String ACTUALQUANT, String TOTALQUANT, String REJECT,String  SAMPLE, String LOTNUM, String LOTQUANT,String REMARKS,String DATETIME ){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_P_INVOICE, INVOICE);
        cv.put(COLUMN_P_PART_NUMBER, PART_NUMBER);
        cv.put(COLUMN_P_GOODS_CODE, GOODS_CODE);
        cv.put(COLUMN_P_PART_NAME, PART_NAME);
        cv.put(COLUMN_P_BOX_NUMBER, BOX_NUMBER);
        cv.put(COLUMN_P_BOXSEQ, BOXSEQ);
        cv.put(COLUMN_P_ACTUALQUANT, ACTUALQUANT);
        cv.put(COLUMN_P_TOTALQUANT, TOTALQUANT);
        cv.put(COLUMN_P_REJECT, REJECT);
        cv.put(COLUMN_P_SAMPLE, SAMPLE);
        cv.put(COLUMN_P_LOTNUM, LOTNUM);
        cv.put(COLUMN_P_LOTQUANT, LOTQUANT);
        cv.put(COLUMN_P_REMARKS, REMARKS);
        cv.put(COLUMN_P_DATETIME, DATETIME);


        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Succesfully", Toast.LENGTH_SHORT).show();

        }
    }

    public void addDC(String instrumentUsed, String samplenum, String checkpoint, String samplUnit, String sample1, String sample2, String sample3, String sample4 , String sample5, String sample6,
                       String sample7, String sample8, String sample9,String sample10,String lower, String upper, String min,String average, String max, String judgement,String date){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_P_INSTRUMENT,instrumentUsed);
        cv.put(COLUMN_P_DCCHECKPOINT, checkpoint);
        cv.put(COLUMN_P_DCSAMPLENUM, samplenum);
        cv.put(COLUMN_P_DCCHECKPOINT, checkpoint);
        cv.put(COLUMN_P_DCSAMPLEUNIT, samplUnit);
        cv.put(COLUMN_P_DCSAMPLE1, sample1);
        cv.put(COLUMN_P_DCSAMPLE2, sample2);
        cv.put(COLUMN_P_DCSAMPLE3, sample3);
        cv.put(COLUMN_P_DCSAMPLE4, sample4);
        cv.put(COLUMN_P_DCSAMPLE5, sample5);
        cv.put(COLUMN_P_DCSAMPLE6, sample6);
        cv.put(COLUMN_P_DCSAMPLE7, sample7);
        cv.put(COLUMN_P_DCSAMPLE8, sample8);
        cv.put(COLUMN_P_DCSAMPLE9, sample9);
        cv.put(COLUMN_P_DCSAMPLE10, sample10);
        cv.put(COLUMN_P_LOWER, lower);
        cv.put(COLUMN_P_UPPER, upper);
        cv.put(COLUMN_P_MINIMUM, min);
        cv.put(COLUMN_P_AVERAGE, average);
        cv.put(COLUMN_P_MAXIMUM, max);
        cv.put(COLUMN_P_JUDGEMENT, judgement);
        cv.put(COLUMN_P_DATEDIM, date);

        long result = db.insert(TABLE_NAME2, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Succesfully", Toast.LENGTH_SHORT).show();
        }
    }


    public void addFC(String instrumentUsed, String samplenum, String checkpoint, String samplUnit, String sample1, String sample2, String sample3, String sample4 , String sample5, String sample6,
                      String sample7, String sample8, String sample9,String sample10,String lower, String upper, String min,String average, String max, String judgement,String date){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_P_FINSTRUMENT,instrumentUsed);
        cv.put(COLUMN_P_FCCHECKPOINT, checkpoint);
        cv.put(COLUMN_P_FCSAMPLENUM, samplenum);
        cv.put(COLUMN_P_FCCHECKPOINT, checkpoint);
        cv.put(COLUMN_PF_FCSAMPLEUNIT, samplUnit);
        cv.put(COLUMN_P_FCSAMPLE1, sample1);
        cv.put(COLUMN_P_FCSAMPLE2, sample2);
        cv.put(COLUMN_P_FCSAMPLE3, sample3);
        cv.put(COLUMN_P_FCSAMPLE4, sample4);
        cv.put(COLUMN_P_FCSAMPLE5, sample5);
        cv.put(COLUMN_P_FCSAMPLE6, sample6);
        cv.put(COLUMN_P_FCSAMPLE7, sample7);
        cv.put(COLUMN_P_FCSAMPLE8, sample8);
        cv.put(COLUMN_P_FCSAMPLE9, sample9);
        cv.put(COLUMN_P_FCSAMPLE10, sample10);
        cv.put(COLUMN_P_FLOWER, lower);
        cv.put(COLUMN_P_FUPPER, upper);
        cv.put(COLUMN_P_FMINIMUM, min);
        cv.put(COLUMN_P_FAVERAGE, average);
        cv.put(COLUMN_P_FMAXIMUM, max);
        cv.put(COLUMN_P_FJUDGEMENT, judgement);
        cv.put(COLUMN_P_FDATEDIM, date);

        long result = db.insert(TABLE_NAME5, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Succesfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void addinspection(String prepared, String prepareddate, String insinvoicenum, String insgoods, String inspartname, String insinvoicequant, String insassyline,
                              String inspatnum, String instemp, String insrohs, String insdateinspected, String inshumid, String insupp, String inspector, String insdatereceived,
                              String insmaker, String inssample, String insmaterial, String insinspecttype, String insulmarking, String insoir, String inscoc,String insprodtype, String instestreport,
                              String datetime ,String boxseqid
                            ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();


        cv.put(COLUMN_P_INSPREPARED, prepared);
        cv.put(COLUMN_P_INSPREPAREDDATE, prepareddate);
        cv.put(COLUMN_P_INSINVOICENUM, insinvoicenum);
        cv.put(COLUMN_P_INSGOODS, insgoods);
        cv.put(COLUMN_P_INSPARTNAME, inspartname);
        cv.put(COLUMN_P_INSINVOICEQUANT, insinvoicequant);
        cv.put(COLUMN_P_INSASSEMBLYLINE, insassyline);
        cv.put(COLUMN_P_INSPARTNUM, inspatnum);
        cv.put(COLUMN_P_INSTEMP, instemp);
        cv.put(COLUMN_P_INSROHS, insrohs);
        cv.put(COLUMN_P_INSDATEINSPECTED, insdateinspected);
        cv.put(COLUMN_P_INSHUMID, inshumid);
        cv.put(COLUMN_P_INSSUPP, insupp);
        cv.put(COLUMN_P_INSPECTOR, inspector);
        cv.put(COLUMN_P_INSDATERECEIVED, insdatereceived);
        cv.put(COLUMN_P_INSMAKER, insmaker);
        cv.put(COLUMN_P_INSSAMPLE, inssample);
        cv.put(COLUMN_P_INSMATERIAL, insmaterial);
        cv.put(COLUMN_P_INSINSPECTTYPE, insinspecttype);
        cv.put(COLUMN_P_INSULMARKING, insulmarking);
        cv.put(COLUMN_P_INSOIR, insoir);
        cv.put(COLUMN_P_INSCOC, inscoc);
        cv.put(COLUMN_P_INSPRODTYPE, insprodtype);
        cv.put(COLUMN_P_INSTESTREPORT, instestreport);
        cv.put(COLUMN_P_DATE_TIMEE, datetime);
        cv.put(COLUMN_P_BOXSEQID, boxseqid);



        long result = db.insert(TABLE_NAME3, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Succesfully", Toast.LENGTH_SHORT).show();
        }
    }

    public void addVisual( String instrument, String sampSize, String checkpoint,String remarks, String judgement){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_P_VISINSTRUMENT, instrument);
        cv.put(COLUMN_P_VISSAMPLESIZE, sampSize);
        cv.put(COLUMN_P_VICHECKPOINT, checkpoint);
        cv.put(COLUMN_P_VIREMARKS, remarks);
        cv.put(COLUMN_P_VIJUDGEMENT, judgement);

        long result = db.insert(TABLE_NAME4, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Succesfully", Toast.LENGTH_SHORT).show();
        }
    }




    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query,null);

        }
        return cursor;

    }


    public Cursor readdcAllData(){
        String query = "SELECT * FROM " + TABLE_NAME2;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query,null);

        }
        return cursor;

    }



    public Cursor readinspectData(){
        String query = "SELECT * FROM " + TABLE_NAME3;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query,null);

        }
        return cursor;

    }



    public Cursor readVisualData(){
        String query = "SELECT * FROM " + TABLE_NAME4;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query,null);

        }
        return cursor;

    }


    public Cursor readFcData(){
        String query = "SELECT * FROM " + TABLE_NAME5;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query,null);

        }
        return cursor;

    }



    public void updateinspectdata(String insid, String prepared, String prepareddate,String insinvoicenum, String insgoods,String inspartname, String insinvoicequant, String insassyline,
                                  String inspatnum,String instemp,String insrohs, String insdateinspected, String inshumid, String insupp, String inspector, String insdatereceived,
                                  String insmaker,String inssample, String insmaterial, String insinspecttype, String insulmarking, String insoir, String inscoc,String prodtypes, String instestreport,String dateteime){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_P_INSID, insid);
        cv.put(COLUMN_P_INSPREPARED, prepared);
        cv.put(COLUMN_P_INSPREPAREDDATE, prepareddate);
        cv.put(COLUMN_P_INSINVOICENUM, insinvoicenum);
        cv.put(COLUMN_P_INSGOODS, insgoods);
        cv.put(COLUMN_P_INSPARTNAME, inspartname);
        cv.put(COLUMN_P_INSINVOICEQUANT, insinvoicequant);
        cv.put(COLUMN_P_INSASSEMBLYLINE, insassyline);
        cv.put(COLUMN_P_INSPARTNUM, inspatnum);
        cv.put(COLUMN_P_INSTEMP, instemp);
        cv.put(COLUMN_P_INSROHS, insrohs);
        cv.put(COLUMN_P_INSDATEINSPECTED, insdateinspected);
        cv.put(COLUMN_P_INSHUMID, inshumid);
        cv.put(COLUMN_P_INSSUPP, insupp);
        cv.put(COLUMN_P_INSPECTOR, inspector);
        cv.put(COLUMN_P_INSDATERECEIVED, insdatereceived);
        cv.put(COLUMN_P_INSMAKER, insmaker);
        cv.put(COLUMN_P_INSSAMPLE, inssample);
        cv.put(COLUMN_P_INSMATERIAL, insmaterial);
        cv.put(COLUMN_P_INSINSPECTTYPE, insinspecttype);
        cv.put(COLUMN_P_INSULMARKING, insulmarking);
        cv.put(COLUMN_P_INSOIR, insoir);
        cv.put(COLUMN_P_INSCOC, inscoc);
        cv.put(COLUMN_P_INSPRODTYPE, prodtypes);
        cv.put(COLUMN_P_INSTESTREPORT, instestreport);
        cv.put(COLUMN_P_DATE_TIMEE, dateteime);


        long result = db.update(TABLE_NAME3, cv,"INSID=?", new String[]{insid});
        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context," Succesfully updated", Toast.LENGTH_SHORT).show();
        }
    }





    public void updateData(String row_id, String invoice, String part_number, String goods_code, String part_name, String box_number, String boxseq, String actualquant,
                           String totalquant, String reject, String sample, String lotnum, String lotquant, String remarks, String datetime) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_P_INVOICE, invoice);
        cv.put(COLUMN_P_PART_NUMBER, part_number);
        cv.put(COLUMN_P_GOODS_CODE, goods_code);
        cv.put(COLUMN_P_PART_NAME, part_name);
        cv.put(COLUMN_P_BOX_NUMBER, box_number);
        cv.put(COLUMN_P_BOXSEQ, boxseq);
        cv.put(COLUMN_P_ACTUALQUANT, actualquant);
        cv.put(COLUMN_P_TOTALQUANT, totalquant);
        cv.put(COLUMN_P_REJECT, reject);
        cv.put(COLUMN_P_SAMPLE, sample);
        cv.put(COLUMN_P_LOTNUM, lotnum);
        cv.put(COLUMN_P_LOTQUANT, lotquant);
        cv.put(COLUMN_P_REMARKS, remarks);
        cv.put(COLUMN_P_DATETIME, datetime);
        long result = db.update(TABLE_NAME, cv,"id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context," Succesfully updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateVisual(String id, String instrument, String sampSize, String checkpoint, String remarks, String judgement ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();



        cv.put(COLUMN_P_VISINSTRUMENT, instrument);
        cv.put(COLUMN_P_VISSAMPLESIZE, sampSize);
        cv.put(COLUMN_P_VICHECKPOINT, checkpoint);
        cv.put(COLUMN_P_VIREMARKS, remarks);
        cv.put(COLUMN_P_VIJUDGEMENT, judgement);


        long result = db.update(TABLE_NAME, cv,"id=?", new String[]{id});
        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context," Succesfully updated", Toast.LENGTH_SHORT).show();
        }
    }


    public void updateDc( String _id,String instrumentUsed,  String samplenum, String checkpoint, String samplUnit, String sample1, String sample2, String sample3, String sample4 , String sample5, String sample6,
                       String sample7, String sample8, String sample9,String sample10,String lower, String upper, String max, String min, String average, String judgement,String date){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_P_INSTRUMENT,instrumentUsed);
        cv.put(COLUMN_P_DCSAMPLENUM, samplenum);
        cv.put(COLUMN_P_DCCHECKPOINT, checkpoint);
        cv.put(COLUMN_P_DCSAMPLEUNIT, samplUnit);
        cv.put(COLUMN_P_DCSAMPLE1, sample1);
        cv.put(COLUMN_P_DCSAMPLE2, sample2);
        cv.put(COLUMN_P_DCSAMPLE3, sample3);
        cv.put(COLUMN_P_DCSAMPLE4, sample4);
        cv.put(COLUMN_P_DCSAMPLE5, sample5);
        cv.put(COLUMN_P_DCSAMPLE6, sample6);
        cv.put(COLUMN_P_DCSAMPLE7, sample7);
        cv.put(COLUMN_P_DCSAMPLE8, sample8);
        cv.put(COLUMN_P_DCSAMPLE9, sample9);
        cv.put(COLUMN_P_DCSAMPLE10, sample10);
        cv.put(COLUMN_P_LOWER, lower);
        cv.put(COLUMN_P_UPPER, upper);
        cv.put(COLUMN_P_MINIMUM, min);
        cv.put(COLUMN_P_AVERAGE, average);
        cv.put(COLUMN_P_MAXIMUM, max);
        cv.put(COLUMN_P_JUDGEMENT, judgement);
        cv.put(COLUMN_P_DATEDIM, date);

        long result = db.update(TABLE_NAME2, cv,"DCID=?", new String[]{_id});
        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context," Succesfully updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateFc( String _id,String instrumentUsed,  String samplenum, String checkpoint, String samplUnit, String sample1, String sample2, String sample3, String sample4 , String sample5, String sample6,
                          String sample7, String sample8, String sample9,String sample10,String lower, String upper, String max, String min, String average, String judgement,String date){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_P_FINSTRUMENT,instrumentUsed);
        cv.put(COLUMN_P_FCCHECKPOINT, checkpoint);
        cv.put(COLUMN_P_FCSAMPLENUM, samplenum);
        cv.put(COLUMN_P_FCCHECKPOINT, checkpoint);
        cv.put(COLUMN_PF_FCSAMPLEUNIT, samplUnit);
        cv.put(COLUMN_P_FCSAMPLE1, sample1);
        cv.put(COLUMN_P_FCSAMPLE2, sample2);
        cv.put(COLUMN_P_FCSAMPLE3, sample3);
        cv.put(COLUMN_P_FCSAMPLE4, sample4);
        cv.put(COLUMN_P_FCSAMPLE5, sample5);
        cv.put(COLUMN_P_FCSAMPLE6, sample6);
        cv.put(COLUMN_P_FCSAMPLE7, sample7);
        cv.put(COLUMN_P_FCSAMPLE8, sample8);
        cv.put(COLUMN_P_FCSAMPLE9, sample9);
        cv.put(COLUMN_P_FCSAMPLE10, sample10);
        cv.put(COLUMN_P_FLOWER, lower);
        cv.put(COLUMN_P_FUPPER, upper);
        cv.put(COLUMN_P_FMINIMUM, min);
        cv.put(COLUMN_P_FAVERAGE, average);
        cv.put(COLUMN_P_FMAXIMUM, max);
        cv.put(COLUMN_P_FJUDGEMENT, judgement);
        cv.put(COLUMN_P_FDATEDIM, date);

        long result = db.update(TABLE_NAME5, cv,"fCID=?", new String[]{_id});
        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context," Succesfully updated", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
      long result =   db.delete(TABLE_NAME,"id=?",new String[] {row_id});
      if(result == -1){

      }else{
          Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
      }
    }


    void deleteallRow(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

    void deleteallRow1(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME2);
    }


    public void deleteallRow2(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME3);
    }

    public void deleteallRow3(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME5);
    }
}
