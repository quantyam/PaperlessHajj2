package com.quantyam.app.paperlesshajj;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class mui extends AppCompatActivity {
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mui);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#94090D"));


        id = getIntent().getStringExtra("userid");
        CardView buy = findViewById(R.id.buy);

        buy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mui.this, pay.class);
                intent.putExtra("userid", id);
                startActivity(intent);


            }
        });

        CardView vol = findViewById(R.id.voln);

        vol.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mui.this, vol.class);

                startActivity(intent);


            }
        });

        CardView medical = findViewById(R.id.medical);
        medical.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mui.this, Medical.class);


                startActivity(intent);


            }
        });


        CardView hajj = findViewById(R.id.myhajj);
        hajj.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mui.this, MyHajj.class);


                startActivity(intent);


            }
        });


        CardView myblnc = findViewById(R.id.mybalance);
        myblnc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mui.this, scanforbalance.class);

                intent.putExtra("userid", id);
                startActivity(intent);


            }
        });

    }
}
