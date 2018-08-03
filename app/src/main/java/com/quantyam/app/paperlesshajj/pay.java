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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

public class pay extends AppCompatActivity {

    private static final String cameraPerm = Manifest.permission.CAMERA;

    // UI
    private TextView text;

    // QREader
   ProgressDialog dialog;


    double req_value = 0.0;
    Button stateBtn, startQR;
    private SurfaceView mySurfaceView;
    private QREader qrEader;
    String qrcode = "";
    String id;
    boolean hasCameraPermission = false;
    EditText pass, requiredAMount;
TextView entrpin;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);
        hasCameraPermission = RuntimePermissionUtil.checkPermissonGranted(this, cameraPerm);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#94090D"));

        id = getIntent().getStringExtra("userid");
        //   text = findViewById(R.id.code_info);
        pass = findViewById(R.id.password);
        mySurfaceView = findViewById(R.id.camera_view);
        pass.setVisibility(View.GONE);

        requiredAMount = findViewById(R.id.rm);
        entrpin=findViewById(R.id.entrpin);
        entrpin.setVisibility(View.GONE);

       /* startQR = findViewById(R.id.sqr);
        // change of reader state in dynamic
        startQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new chkcre(req_value).execute();


            }
        });
*/

        stateBtn = findViewById(R.id.submit);

        stateBtn.setVisibility(View.GONE);
        // change of reader state in dynamic
        stateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pass.getText().toString().equals("")){
                    ShowMes("Please enter Pin Code", pay.this);
                }else {

                    new chkcre(req_value).execute();
                }

            }
        });


        if (hasCameraPermission) {
            // Setup QREader
            setupQREader();
        } else {
            RuntimePermissionUtil.requestPermission(com.quantyam.app.paperlesshajj.pay.this, cameraPerm, 100);
        }
    }

    void restartActivity() {
        startActivity(new Intent(com.quantyam.app.paperlesshajj.pay.this, com.quantyam.app.paperlesshajj.pay.class));
        finish();
    }


    void setupQREader() {

        qrEader = new QREader.Builder(this, mySurfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {


                Log.d("output", "Value : " + data);
                qrcode = data;
                pass.post(new Runnable() {
                    @Override
                    public void run() {
                        qrEader.stop();

                        new chkhajj(req_value).execute();



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
                    if (RuntimePermissionUtil.checkPermissonGranted(com.quantyam.app.paperlesshajj.pay.this, cameraPerm)) {
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


    class chkhajj extends AsyncTask<Double, Double, String> {

        double moneyneeded = 0;

        chkhajj(double x) {

            moneyneeded = x;
        }


        @Override
        protected void onPreExecute() {


        }

        public void pr(String s) {

            System.out.println(s);
        }

        @Override
        protected String doInBackground(Double... integers) {
            String result="invalid";

            try {

                runOnUiThread(new Runnable() {
                    public void run() {


                    }
                });


                String link;

                BufferedReader bufferedReader;


                link = "http://quantyam.com/hajj/getinfo.php?code=" + qrcode   ;

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
            String jsonStr = result;

            Log.d("output", "JSON IS ====================" + jsonStr + "======");

            if (!jsonStr.equals("invalid")) {
                req_value = Double.parseDouble((requiredAMount.getText().toString()));
                pass.setVisibility(View.VISIBLE);
                stateBtn.setVisibility(View.VISIBLE);
                entrpin.setVisibility(View.VISIBLE);


            } else {

                ShowMes("Hajj doesn't exist",pay.this);
                onRestart();
            }
        }
    }

    class chkcre extends AsyncTask<Double, Double, String> {

        double moneyneeded = 0;

        chkcre(double x) {

            moneyneeded = x;
        }


        @Override
        protected void onPreExecute() {

            dialog = ProgressDialog.show(com.quantyam.app.paperlesshajj.pay.this, null, null, false, false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.progbar);
        }

        public void pr(String s) {

            System.out.println(s);
        }

        @Override
        protected String doInBackground(Double... integers) {
            String result="invalid";

            try {

                runOnUiThread(new Runnable() {
                    public void run() {


                    }
                });


                String link;

                BufferedReader bufferedReader;


                link = "http://quantyam.com/hajj/chkpay.php?code=" + qrcode + "&pass=" + pass.getText();

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
            String jsonStr = result;

            Log.d("output", "JSON IS ====================" + jsonStr + "======");

            if (!jsonStr.equals("invalid")) {
                try {


                    JSONObject jsonObj = new JSONObject(jsonStr);


                    String cash = jsonObj.getString("Money");
                    String CampainName = jsonObj.getString("company");
                    String hajjid = jsonObj.getString("ID");

                    int currentver = Integer.parseInt(cash);

                    if (currentver > moneyneeded) {

                        Log.d("output", "good cash");
                        dialog.dismiss();
                        new dopay(moneyneeded, CampainName, hajjid).execute();
                    } else {

                        runOnUiThread(new Runnable() {
                            public void run() {
                                Log.d("output", "NO CASH!");
                                ShowMes("No balance", com.quantyam.app.paperlesshajj.pay.this);
                                dialog.dismiss();
                                //error     new helper().sdok(getString(R.string.plzupdate), Login.this);


                            }
                        });

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                dialog.dismiss();
                ShowMes("Payment Declined, Pin code is invalid",pay.this);
            }
        }
    }


    class dopay extends AsyncTask<Double, Double, String> {

        double moneyneeded = 0;
        String company = "", hajj_id = "";

        dopay(double x, String s, String d) {
            company = s;
            hajj_id = d;
            moneyneeded = x;
        }


        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(com.quantyam.app.paperlesshajj.pay.this, null, null, false, false);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.progbar);

        }

        public void pr(String s) {

            System.out.println(s);
        }

        @Override
        protected String doInBackground(Double... integers) {

            try {

                runOnUiThread(new Runnable() {
                    public void run() {


                    }
                });


                String link;

                BufferedReader bufferedReader;
                String result;


                link = "http://quantyam.com/hajj/pay.php?code=" + qrcode + "&amount=" + moneyneeded + "&company=" + company + "&userid=" + id + "&hajjid=" + hajj_id;

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

                if (!jsonStr.equals("Success")) {
                    runOnUiThread(new Runnable() {
                        public void run() {

                            ShowMes("Payment Successful!", com.quantyam.app.paperlesshajj.pay.this);

                        }
                    });


                } else {
                    runOnUiThread(new Runnable() {
                        public void run() {

                            ShowMes("Error Occurred", com.quantyam.app.paperlesshajj.pay.this);

                        }
                    });


                    Log.d("output", "Could not pay");
                }

            } catch (Exception eo) {
                eo.printStackTrace();
            }
            return null;
        }


        protected void onPostExecute(Integer result) {
dialog.dismiss();

        }
    }


    public void ShowMes(String message, Activity a) {


        android.support.v7.app.AlertDialog.Builder alertBuilder = new android.support.v7.app.AlertDialog.Builder(a, R.style.MyDialogTheme);
        alertBuilder.setCancelable(true);

        alertBuilder.setMessage(message);
        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                finish();

            }

        });

        android.support.v7.app.AlertDialog alert = alertBuilder.create();
        alert.show();

    }
}
