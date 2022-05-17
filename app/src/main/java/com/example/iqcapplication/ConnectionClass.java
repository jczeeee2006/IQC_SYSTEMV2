package com.example.iqcapplication;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    String ip = "192.168.2.15:40001" ;//14 dapat
    String ip2 = "192.168.2.14";
    String classs = "net.sourceforge.jtds.jdbc.Driver";
    String db = "IQCDatabase";
    String db2 = "MATech";
    String db3 = "TempHumid";
    String db4 = "MA_Receiving";
    String un = "iqc_db_user_dev";
    String password = "iqcdbuserdev";
    String un1 = "software";
    String password1 = "specialist";




    //-----------------IQC DATABASE-----------------------
    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {
            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";"
                    + "databaseName=" + db + ";user=" + un + ";password="
                    + password + ";///test?allowMultiQueries=true" + ";///useLOBs=false;";
            conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }

    //------------------------MATech Database---------------------
    @SuppressLint("NewApi")
    public Connection CONN2() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn2 = null;
        String ConnURL2 = null;
        try {

            Class.forName(classs);
            ConnURL2 = "jdbc:jtds:sqlserver://" + ip2 + ";"
                    + "databaseName=" + db + ";user=" + un1 + ";password="
                    + password1 + ";///test?allowMultiQueries=true";
            conn2 = DriverManager.getConnection(ConnURL2);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn2;
    }
    //-----------------------TEMPERATURE HUMIDITY -------------------------------

    @SuppressLint("NewApi")
    public Connection CONN3 () {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn3 = null;
        String ConnURL3 = null;
        try {

            Class.forName(classs);
            ConnURL3 = "jdbc:jtds:sqlserver://" + ip2 + ";"
                    + "databaseName=" + db3 + ";user=" + un1 + ";password="
                    + password1 + ";";
            conn3 = DriverManager.getConnection(ConnURL3);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn3;
    }

    @SuppressLint("NewApi")
    public Connection CONN4 () {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn4 = null;
        String ConnURL4 = null;
        try {

            Class.forName(classs);
            ConnURL4 = "jdbc:jtds:sqlserver://" + ip2 + ";"
                    + "databaseName=" + db4 + ";user=" + un1 + ";password="
                    + password1 + ";";
            conn4 = DriverManager.getConnection(ConnURL4);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn4;
    }

}
