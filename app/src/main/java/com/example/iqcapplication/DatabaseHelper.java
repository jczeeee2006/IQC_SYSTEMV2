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
    private static final int DATABASE_VERSION = 3;

    private static final  String TABLE_NAME = "Lotnumberdb";
    private static final  String TABLE_NAME2 = "Dimensiondb";
    private static final  String TABLE_NAME3 = "Inspectiondb";


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

    private static final  String COLUMN_P_DCID = "DCID";
    private static final  String COLUMN_P_DCSAMPLENUM = "DCSAMPLENUM";
    private static final  String COLUMN_P_DCCHECKPOINT = "DCCHECKPOINT";
    private static final  String COLUMN_P_DCSAMPLEVALUE = "DCSAMPLEVALUE";

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
                        COLUMN_P_REMARKS + " TEXT );" ;


        String query1  =
                "CREATE TABLE " + TABLE_NAME2 +
                        "(" + COLUMN_P_DCID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_P_DCSAMPLENUM + " TEXT," +
                        COLUMN_P_DCCHECKPOINT + " TEXT," +

                        COLUMN_P_DCSAMPLEVALUE + " TEXT );" ;


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
                        COLUMN_P_INSTESTREPORT + " TEXT);" ;


        db.execSQL(query);
        db.execSQL(query1);
        db.execSQL(query2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(db);


    }

    public void addData(String INVOICE,String PART_NUMBER, String GOODS_CODE,String PART_NAME,String BOX_NUMBER,String BOXSEQ,
                        String ACTUALQUANT, String TOTALQUANT, String REJECT,String  SAMPLE, String LOTNUM, String LOTQUANT,String REMARKS  ){


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

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Succesfully", Toast.LENGTH_SHORT).show();

        }
    }

    public void addDC( String samplenum, String checkpoint, String sampleValue){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_P_DCSAMPLENUM, samplenum);
        cv.put(COLUMN_P_DCCHECKPOINT, checkpoint);
        cv.put(COLUMN_P_DCSAMPLEVALUE, sampleValue);

        long result = db.insert(TABLE_NAME2, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Succesfully", Toast.LENGTH_SHORT).show();
        }
    }


    public void addinspection(String prepared, String prepareddate, String insinvoicenum, String insgoods, String inspartname, String insinvoicequant, String insassyline,
                              String inspatnum, String instemp, String insrohs, String insdateinspected, String inshumid, String insupp, String inspector, String insdatereceived,
                              String insmaker, String inssample, String insmaterial, String insinspecttype, String insulmarking, String insoir, String inscoc,String insprodtype, String instestreport

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
        cv.put( COLUMN_P_INSPRODTYPE, insprodtype);
        cv.put(COLUMN_P_INSTESTREPORT, instestreport);



        long result = db.insert(TABLE_NAME3, null, cv);
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


    public void updateinspectdata(String insid, String prepared, String prepareddate,String insinvoicenum, String insgoods,String inspartname, String insinvoicequant, String insassyline,
                                  String inspatnum,String instemp,String insrohs, String insdateinspected, String inshumid, String insupp, String inspector, String insdatereceived,
                                  String insmaker,String inssample, String insmaterial, String insinspecttype, String insulmarking, String insoir, String inscoc, String instestreport){
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
        cv.put(COLUMN_P_INSTESTREPORT, instestreport);

        long result = db.update(TABLE_NAME, cv,"id=?", new String[]{insid});
        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context," Succesfully updated", Toast.LENGTH_SHORT).show();
        }
    }


    public void updateData(String row_id, String INVOICE,String PART_NUMBER, String GOODS_CODE,String PART_NAME,String BOX_NUMBER,String BOXSEQ,
                           String ACTUALQUANT, String TOTALQUANT, String REJECT,String  SAMPLE, String LOTNUM, String LOTQUANT,String REMARKS ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_P_INVOICE, INVOICE);
        cv.put(COLUMN_P_INVOICE, PART_NUMBER);
        cv.put(COLUMN_P_INVOICE, GOODS_CODE);
        cv.put(COLUMN_P_INVOICE, PART_NAME);
        cv.put(COLUMN_P_INVOICE, BOX_NUMBER);
        cv.put(COLUMN_P_INVOICE, BOXSEQ);
        cv.put(COLUMN_P_INVOICE, ACTUALQUANT);
        cv.put(COLUMN_P_INVOICE, TOTALQUANT);
        cv.put(COLUMN_P_INVOICE, REJECT);
        cv.put(COLUMN_P_INVOICE, SAMPLE);
        cv.put(COLUMN_P_INVOICE, LOTNUM);
        cv.put(COLUMN_P_INVOICE, LOTQUANT);
        cv.put(COLUMN_P_INVOICE, REMARKS);

        long result = db.update(TABLE_NAME, cv,"id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context," Succesfully updated", Toast.LENGTH_SHORT).show();
        }
    }
}
