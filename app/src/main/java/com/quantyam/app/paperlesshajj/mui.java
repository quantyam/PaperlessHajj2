package com.quantyam.app.paperlesshajj;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class mui extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mui);



        Button buy =   findViewById(R.id.buy);
        buy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mui.this, pay.class);


                startActivity(intent);


            }
        });


        Button medical =   findViewById(R.id.medical);
        medical.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                Intent intent = new Intent(mui.this, Medical.class);


                startActivity(intent);


            }
        });


    }
}
