package com.quantyam.app.paperlesshajj;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import github.nisrulz.qreader.QREader;

public class ShowMedical extends AppCompatActivity {
    TextView medecalrecord,namef,nathonality,id,age,lang,recidance,phone,leader,companyf,camp,phone2,mail,trans;

    // UI
    private TextView text;

    // QREader
    ProgressDialog dialog;
    int userid=1;

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(getBaseContext(), mui.class);

        startActivity(intent);
    }

    String qrcode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medical);


        namef=findViewById(R.id.Nametxt);
        nathonality=findViewById(R.id.nath);
        id=findViewById(R.id.idtxt);
        age=findViewById(R.id.Dob);
        lang=findViewById(R.id.lang);
        recidance=findViewById(R.id.recdtxt);
        phone=findViewById(R.id.phone);
        leader=findViewById(R.id.lead);
        companyf=findViewById(R.id.company);
        camp=findViewById(R.id.camp);
        phone2=findViewById(R.id.phone2);
        mail=findViewById(R.id.mail);
        trans=findViewById(R.id.trans);
        medecalrecord=findViewById(R.id.medecalrecord);

        qrcode= getIntent().getStringExtra("code");
        new getnfo().execute();

    }

    class getnfo extends AsyncTask<Double, Double, String> {

        double moneyneeded=0;
        getnfo( ) {


        }




        @Override
        protected void onPreExecute( ) {

            dialog = ProgressDialog.show(ShowMedical.this, null, null, false, false);
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
            String result="";
            try {

                runOnUiThread(new Runnable() {
                    public void run() {


                    }
                });





                link = "http://quantyam.com/hajj/getinfo.php?code="+qrcode;

                pr(link);
                URL url = new URL(link);


                HttpURLConnection.setFollowRedirects(false);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5000);
                InputStreamReader is = new InputStreamReader(con.getInputStream());

                bufferedReader = new BufferedReader(is);
                result = bufferedReader.readLine();

                String jsonStr = result;
                Log.d("output","JSON IS ===================="+jsonStr+"======");



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
                    String Residence = jsonObj.getString("Residence");
                    String Money = jsonObj.getString("Money");
                    // String Photo = jsonObj.getString("ID");
                    String MedicalRecord = jsonObj.getString("MedicalRecord");
                    String dob = jsonObj.getString("DOB");
                    String company = jsonObj.getString("company");
                    String Leader = jsonObj.getString("Leader");
                    String SpaekingLanguage = jsonObj.getString("SpaekingLanguage");
                    String Nationality = jsonObj.getString("Nationality");
                    String 	BloodType = jsonObj.getString("BloodType");
                    String PhoneNumber = jsonObj.getString("PhoneNumber");
                    String RelativePhoneNo = jsonObj.getString("RelativePhoneNo");

                    String Email = jsonObj.getString("Email");
                    String Transportation = jsonObj.getString("Transportation");

                    namef.setText(name);
                    nathonality.setText(Nationality);
                    id.setText(nationalityD);
                    age.setText(dob);
                    lang.setText(SpaekingLanguage);
                    recidance.setText(Residence);
                    phone.setText(PhoneNumber);
                    leader.setText(Leader);
                    camp.setText(CampainName);
                    mail.setText(Email);
                    phone2.setText(RelativePhoneNo);
                    trans.setText(Transportation);
                    companyf.setText(company);
                    medecalrecord.setText(MedicalRecord);

                   /* dp( CampainName ,        name ,        nationalityD ,        Residence ,        Money ,
                            MedicalRecord ,        dob ,        company,        Leader ,        SpaekingLanguage ,
                            Nationality ,        BloodType ,        PhoneNumber ,        RelativePhoneNo ,
                            Email ,        Transportation  );
*/


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                ShowMes("Code not Found", ShowMedical.this);
            }
            runOnUiThread(new Runnable() {
                public void run() {

                    //   ShowMes("Payment Successful!",Medical.this);
                    dialog.dismiss();
                }
            });
        }
    }

    public void dp( String... args){
        Log.d("output","====================");
        Log.d("output","====================");Log.d("output","====================");Log.d("output","====================");
        Log.d("output","====================");




        for (String arg : args) {
            Log.d("output","Reading IS ==================== "+arg+" ======");


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
