package com.quantyam.app.paperlesshajj;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class mybalance extends AppCompatActivity {
    ProgressDialog dialog;
    TextView cash;
    String qrcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybalance);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#B58B5A"));

        cash = findViewById(R.id.balance);
        qrcode = getIntent().getStringExtra("code");
        new getnfo().execute();

    }

    class getnfo extends AsyncTask<Double, Double, String> {

        double moneyneeded = 0;

        getnfo() {


        }


        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(mybalance.this, null, null, false, false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.progbar);
        }

        public void pr(String s) {

            System.out.println(s);
        }

        @Override
        protected String doInBackground(Double... integers) {
            String link;

            BufferedReader bufferedReader;
            String result = "";
            try {

                runOnUiThread(new Runnable() {
                    public void run() {


                    }
                });


                link = "http://quantyam.com/hajj/getinfo.php?code=" + qrcode;

                pr(link);
                URL url = new URL(link);


                HttpURLConnection.setFollowRedirects(false);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5000);
                InputStreamReader is = new InputStreamReader(con.getInputStream());

                bufferedReader = new BufferedReader(is);
                result = bufferedReader.readLine();

                String jsonStr = result;
                Log.d("output", "JSON IS ====================" + jsonStr + "======");


            } catch (Exception eo) {
                eo.printStackTrace();
            }
            return result;
        }


        protected void onPostExecute(String result) {

            if (!result.equals("invalid")) {
                try {


                    JSONObject jsonObj = new JSONObject(result);


                    String mny = jsonObj.getString("Money");

                    final double d = Double.parseDouble(mny);
                    cash.setText(mny);


                    for (int i = 0; i < d; i++) {


                    }

                    runOnUiThread(new Runnable() {

                        public void run() {

                            for (int i = 0; i < d; i++) {
                                try {


                                    Thread.sleep(2);
                                    cash.setText(i + "");
                                    pr(i + "");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });



                   /* dp( CampainName ,        name ,        nationalityD ,        Residence ,        Money ,
                            MedicalRecord ,        dob ,        company,        Leader ,        SpaekingLanguage ,
                            Nationality ,        BloodType ,        PhoneNumber ,        RelativePhoneNo ,
                            Email ,        Transportation  );
*/


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            runOnUiThread(new Runnable() {
                public void run() {

                    //   ShowMes("Payment Successful!",Medical.this);
                    dialog.dismiss();
                }
            });
        }
    }
}
