package com.quantyam.app.paperlesshajj;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class Medical extends AppCompatActivity {

    private static final String cameraPerm = Manifest.permission.CAMERA;

    // UI
    private TextView text;

    // QREader
    ProgressDialog dialog;
    int userid=1;
    double req_value=0.0;

    private SurfaceView mySurfaceView;
    private QREader qrEader;
    String qrcode="";
    boolean hasCameraPermission = false;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);
        hasCameraPermission = RuntimePermissionUtil.checkPermissonGranted(this, cameraPerm);

        //   text = findViewById(R.id.code_info);

        mySurfaceView = findViewById(R.id.camera_view);




        if (hasCameraPermission) {
            // Setup QREader
            setupQREader();
        } else {
            RuntimePermissionUtil.requestPermission(Medical.this, cameraPerm, 100);
        }
    }

    void restartActivity() {
        startActivity(new Intent(Medical.this, Medical.class));
        finish();
    }



    void setupQREader() {
        // Init QREader
        // ------------
        qrEader = new QREader.Builder(this, mySurfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {



                Log.d("output", "Value : " + data);
                qrcode=data;
                mySurfaceView.post(new Runnable() {
                    @Override
                    public void run() {
                        qrEader.stop();

                        Intent intent = new Intent(getBaseContext(), ShowMedical.class);
                        intent.putExtra("code", qrcode);
                        startActivity(intent);


                        //  text.setText(data);
                    }
                });
            }

        }).facing(QREader.BACK_CAM)
                .enableAutofocus(true)
                .height(mySurfaceView.getHeight())
                .width(mySurfaceView.getWidth())
                .build();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (hasCameraPermission) {

            // Cleanup in onPause()
            // --------------------
            qrEader.releaseAndCleanup();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (hasCameraPermission) {

            // Init and Start with SurfaceView
            // -------------------------------
            qrEader.initAndStart(mySurfaceView);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions,
                                           @NonNull final int[] grantResults) {
        if (requestCode == 100) {
            RuntimePermissionUtil.onRequestPermissionsResult(grantResults, new RPResultListener() {
                @Override
                public void onPermissionGranted() {
                    if ( RuntimePermissionUtil.checkPermissonGranted(Medical.this, cameraPerm)) {
                        restartActivity();
                    }
                }

                @Override
                public void onPermissionDenied() {
                    // do nothing
                }
            });
        }
    }


    class chkcre extends AsyncTask<Double, Double, String> {

        double moneyneeded=0;
        chkcre( ) {


        }




        @Override
        protected void onPreExecute( ) {

            dialog = ProgressDialog.show(Medical.this, null, null, false, false);
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


dp( CampainName ,        name ,        nationalityD ,        Residence ,        Money ,
        MedicalRecord ,        dob ,        company,        Leader ,        SpaekingLanguage ,
        Nationality ,        BloodType ,        PhoneNumber ,        RelativePhoneNo ,
        Email ,        Transportation  );



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                ShowMes("Code not Found", Medical.this);
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
