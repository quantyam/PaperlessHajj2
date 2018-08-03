package com.quantyam.app.paperlesshajj;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class ShowVol extends AppCompatActivity {
    TextView  namef, nathonality,   lang, recidance,   companyf, camp;  ;

    // UI
    private TextView text;
    GridView gv;
    // QREader
    ProgressDialog dialog;
    int userid = 1;
    Button navigate;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getBaseContext(), mui.class);

        startActivity(intent);
    }

    String qrcode = "";
    ArrayList<String> list = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_vol);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#B58B5A"));

navigate=findViewById(R.id.nav);




        namef = findViewById(R.id.Nametxt);
        nathonality = findViewById(R.id.nath);

        lang = findViewById(R.id.lang);
        recidance = findViewById(R.id.recdtxt);

        companyf = findViewById(R.id.company);
        camp = findViewById(R.id.camp);


        qrcode = getIntent().getStringExtra("code");
        new getnfo().execute();

    }

    class getnfo extends AsyncTask<Double, Double, String> {

        double moneyneeded = 0;

        getnfo() {


        }


        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(ShowVol.this, null, null, false, false);
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


                    String cash = jsonObj.getString("Money");
                    String CampainName = jsonObj.getString("company");
                    String name = jsonObj.getString("Name");
                    String nationalityD = jsonObj.getString("NationalityD");
                  final  String Residence = jsonObj.getString("Residence");
                    String Money = jsonObj.getString("Money");
                    // String Photo = jsonObj.getString("ID");
                    String MedicalRecord = jsonObj.getString("MedicalRecord");
                    String dob = jsonObj.getString("DOB");
                    String company = jsonObj.getString("company");
                    String Leader = jsonObj.getString("Leader");
                    String SpaekingLanguage = jsonObj.getString("SpaekingLanguage");
                    String Nationality = jsonObj.getString("Nationality");
                    String BloodType = jsonObj.getString("BloodType");
                    String PhoneNumber = jsonObj.getString("PhoneNumber");
                    String RelativePhoneNo = jsonObj.getString("RelativePhoneNo");



                    String Email = jsonObj.getString("Email");
                    String Transportation = jsonObj.getString("Transportation");


                    namef.setText(name);
                    nathonality.setText(Nationality);

                    lang.setText(SpaekingLanguage);
                    recidance.setText(Residence);

                    camp.setText(CampainName);

                    companyf.setText(company);

                    navigate.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=21.6223223,39.1524336 ("+Residence+")" );
                             pr(uri);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                            intent.setPackage("com.google.android.apps.maps");
                            startActivity(intent);


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

            } else {
                ShowMes("Code not Found", ShowVol.this);
            }
            runOnUiThread(new Runnable() {
                public void run() {

                    //   ShowMes("Payment Successful!",Medical.this);
                    dialog.dismiss();
                }
            });
        }
    }

    public void dp(String... args) {
        Log.d("output", "====================");
        Log.d("output", "====================");
        Log.d("output", "====================");
        Log.d("output", "====================");
        Log.d("output", "====================");


        for (String arg : args) {
            Log.d("output", "Reading IS ==================== " + arg + " ======");


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
