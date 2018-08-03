package com.quantyam.app.paperlesshajj;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class Login extends AppCompatActivity {

    EditText email,pass;
    ProgressDialog dialog;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#A57849"));

//temp
       // Intent intent = new Intent(Login.this, mui.class);
      //  intent.putExtra("userid","1");
     //   startActivity(intent);
        //
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        login = findViewById(R.id.login);
        // change of reader state in dynamic
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(email.getText().toString().equals("") || pass.getText().toString().equals("")){
                    android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(Login.this, R.style.MyDialogTheme);
                    alertBuilder.setCancelable(true);

                    alertBuilder.setMessage("please type Email & Password");
                    alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {


                        }

                    });

                    android.support.v7.app.AlertDialog alert = alertBuilder.create();
                    alert.show();
                }else {

                    new chkcre().execute();
                }

            }
        });

    }


    class chkcre extends AsyncTask<Double, Double, String> {


        chkcre() {


        }



        @Override
        protected void onPreExecute( ) {

            dialog = ProgressDialog.show(Login.this, null, null, false, false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.progbar);
        }

        public void pr(String s) {

            System.out.println(s);
        }
        String data;
        @Override
        protected String doInBackground(Double... integers) {
            String link;

            BufferedReader bufferedReader;
            String result="";
            try {

                runOnUiThread(new Runnable() {
                    public void run() {


                    }
                });


             //   data = "?email=" + URLEncoder.encode(email.getText().toString(), "UTF-8");
              //  data += "&pass=" + URLEncoder.encode(pass.getText().toString(), "UTF-8");



                String el= "?email=" +Uri.encode(email.getText().toString());
                String ps="&pass=" + Uri.encode(pass.getText().toString());
                link = "http://quantyam.com/hajj/chkuser.php"+el+ps;


                pr(link);
                URL url = new URL(link);


                HttpURLConnection.setFollowRedirects(false);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5000);
                InputStreamReader is = new InputStreamReader(con.getInputStream());

                bufferedReader = new BufferedReader(is);
                result = bufferedReader.readLine();



            } catch (Exception eo) {
                eo.printStackTrace();
            }
            return result;
        }


        protected void onPostExecute(String result) {
dialog.dismiss();
            String jsonStr = result;
            Log.d("output","JSON IS ===================="+jsonStr+"======");

            if (!jsonStr.equals("invalid")) {
                try {


                    JSONObject jsonObj = new JSONObject(jsonStr);

                    String id = jsonObj.getString("ID");
                    String type = jsonObj.getString("Type");


                    Intent intent = new Intent(Login.this, mui.class);
                    intent.putExtra("userid",id);
                    startActivity(intent);

               /*     runOnUiThread(new Runnable() {
                        public void run() {
                            Log.d("output","NO CASH!");
                            ShowMes("No balance", Login.this);
                            dialog.dismiss();
                            //error     new helper().sdok(getString(R.string.plzupdate), Login.this);


                        }
                    });

*/

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                Log.d("output","BAD INFO");
            }
        }
    }

    public void ShowMes(String message, Activity a) {


        android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(a, R.style.MyDialogTheme);
        alertBuilder.setCancelable(true);

        alertBuilder.setMessage(message);
        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {


            }

        });

        android.support.v7.app.AlertDialog alert = alertBuilder.create();
        alert.show();

    }
}
