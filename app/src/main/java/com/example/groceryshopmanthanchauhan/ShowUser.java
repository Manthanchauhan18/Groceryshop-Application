package com.example.groceryshopmanthanchauhan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

class ShowUser extends Activity
{

    ListView lvAdSh1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        getWindow().setStatusBarColor(ContextCompat.getColor(ShowUser.this,R.color.black));




        lvAdSh1=findViewById(R.id.lvAdSh1);
    }
}