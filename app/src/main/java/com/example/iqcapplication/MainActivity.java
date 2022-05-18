package com.example.iqcapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {
        Button  btn_login;
    public static ConnectionClass connectionClass;
    Button btn1;
    public static EditText userNamee,passwordd;

    ProgressBar pbbar;
    public static String prepBy,usernameh,lastNameholder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login = findViewById(R.id.login);

        connectionClass = new ConnectionClass();
        userNamee = findViewById(R.id.userName);
        passwordd = findViewById(R.id.password);
        btn1 = findViewById(R.id.login);
        pbbar = findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoLogin doLogin = new DoLogin();
                doLogin.execute("");

            }
        });

    }


    public String firstandLastname(String username, String password)
    {
        String output = "";

        try {
            connectionClass = new ConnectionClass();
            Connection conn = connectionClass.CONN();
            String query = "SELECT *  FROM User_Account WHERE employee_num= '" + username + "' AND password= '" + password + "' ";

            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String fname = rs.getString("employee_num");
                usernameh = fname;
                String pos = rs.getString("password");
                String lastName = rs.getString("lastname");
                lastNameholder = lastName;
                output = fname + " " + pos + " " + lastName;
            }
        } catch (Exception e) {
            return output;
        }
        return output;
    }


    //------------------USER LOGIN COMPARING--------------------//
    public class DoLogin extends AsyncTask<String, String, String>
    {
        String z = "";
        Boolean isSuccess = false;

        String userid = userNamee.getText().toString();
        String password = passwordd.getText().toString();

        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(String... params) {
            if (userid.trim().equals("") || password.trim().equals(""))
                z = "Please enter User Id and Password";
            else {
                try {
                    connectionClass = new ConnectionClass();
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "No Internet Connection";
                    } else {
                        String query = "SELECT * from User_Account where employee_num='" + userid + "' and password='" + password + "'";
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
                    z = ex.toString();
                }
            }
            return z;
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();

            if (isSuccess) {
                prepBy = firstandLastname(userid, password);
                Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SapmpleActivityinlot.class );
                startActivity(intent);
                finish();

            }

        }



    }

}