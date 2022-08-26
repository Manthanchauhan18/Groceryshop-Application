package com.example.groceryshopmanthanchauhan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdmineHome extends Activity
{

    Button btAdshow,btAdadd,btAddelete,btAdupdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admine_home);


        getWindow().setStatusBarColor(ContextCompat.getColor(AdmineHome.this,R.color.black));



        btAdshow=findViewById(R.id.btAdshow);
        btAdadd=findViewById(R.id.btAdadd);
        btAddelete=findViewById(R.id.btAddelete);
        btAdupdate=findViewById(R.id.btAdupdate);

        btAdshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent ii4=new Intent(getApplicationContext(),ShowUser.class);
                startActivity(ii4);
            }
        });


        btAdadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent ii5=new Intent(getApplicationContext(),AddGrocerry.class);
                startActivity(ii5);
            }
        });


        btAddelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent ii6=new Intent(getApplicationContext(),DeleteGrocery.class);
                startActivity(ii6);
            }
        });


        btAdupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent ii7=new Intent(getApplicationContext(),UpdateGrocery.class);
                startActivity(ii7);
            }
        });

    }
}