    package com.example.iqcsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    public static ConnectionClass connectionClass;
    public static EditText edtuserid, edtpass;
    Button btnlogin;
    ProgressBar pbbar;
    public static String prepBy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionClass = new ConnectionClass();
        edtuserid = findViewById(R.id.et_username);
        edtpass = findViewById(R.id.et_password);
        btnlogin = findViewById(R.id.btn_Login);
        pbbar = findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtuserid.length() == 0) {
                    edtuserid.setError("Enter Username");
                }
                if (edtpass.length() == 0) {
                    edtpass.setError("Enter password");
                } else {
                    DoLogin doLogin = new DoLogin();
                    doLogin.execute("");
                }


            }
        });


    }

    //---------------------- GETTING USER FIRST AND LAST NAME------------------------//
    public static String firstandLastname(String username, String password)
    {
        String output = "";

        try {
            connectionClass = new ConnectionClass();
            Connection conn = connectionClass.CONN();
            String query = "SELECT firstname, lastname FROM User_Account WHERE employee_num = '" + username + "' AND password = '" + password + "'";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String fname = rs.getString("firstname");
                String lname = rs.getString("lastname");
                output = fname + " " + lname;
            }
        } catch (Exception e) {
            return output;
        }
        return output;
    }

    //------------------LOGIN NI FERDS--------------------//
    public class DoLogin extends AsyncTask<String, String, String>
    {
        String z = "";
        Boolean isSuccess = false;


        String userid = edtuserid.getText().toString();
        String password = edtpass.getText().toString();


        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();

            if (isSuccess) {
                prepBy = firstandLastname(userid, password);
                Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), LotNumber.class );
                startActivity(intent);
                finish();

            }

        }

        @Override
        protected String doInBackground(String... params) {
            if (userid.trim().equals("") || password.trim().equals(""))
                z = "Please enter User Id and Password";
            else {
                try {

                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        String query = "select * from User_Account where employee_num='" + userid + "' and password='" + password + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if (rs.next()) {

                            z = "Login successfully";

                            isSuccess = true;
                        } else {
                            z = "Invalid Credentials";
                            isSuccess = false;
                        }

                    }
                } catch (Exception ex) {
                    isSuccess = false;
                    z = "Exceptions";
                }
            }
            return z;
        }
    }
    @Override
    public void onBackPressed() {
        return;
    }
}