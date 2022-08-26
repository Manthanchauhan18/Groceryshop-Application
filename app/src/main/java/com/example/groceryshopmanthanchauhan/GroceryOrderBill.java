package com.example.groceryshopmanthanchauhan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GroceryOrderBill extends Activity
{

    TextView tvGroceryGrandTotal,tvGroceryBillDate,tvGroceryBillTime;
    String grandTotal;
    ListView lvGroceryPunchased;
    Button btGroceryPayment;
    ArrayList<String> gNameList=new ArrayList<String>();
    ArrayList<String> gPriceList=new ArrayList<String>();
    ArrayList<String> gQtyList=new ArrayList<String>();
    ArrayList<String> gItemTotal=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_order_bill);

        getWindow().setStatusBarColor(ContextCompat.getColor(GroceryOrderBill.this,R.color.black));

        Intent ii=getIntent();
        grandTotal=ii.getStringExtra("GroceryTotal");

        gNameList=ii.getStringArrayListExtra("gname");
        gQtyList=ii.getStringArrayListExtra("gqty");
        gPriceList=ii.getStringArrayListExtra("gqty");
        gItemTotal=ii.getStringArrayListExtra("gitemtotal");

        tvGroceryGrandTotal=findViewById(R.id.tvGroceryGrandTotal);
        lvGroceryPunchased=findViewById(R.id.lvGroceryPurchsed);
        btGroceryPayment=findViewById(R.id.btGroceryPayment);

        PaymentInfo payInfo=(PaymentInfo)getApplication();
        payInfo.amount= Float.parseFloat(grandTotal);

        GroceryBillGenerateAdapter generate = new GroceryBillGenerateAdapter(GroceryOrderBill.this, R.layout.grocerybilllist, gNameList, gQtyList, gPriceList, gItemTotal,tvGroceryGrandTotal);

        lvGroceryPunchased.setAdapter(generate);

        btGroceryPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent ii=new Intent(getApplicationContext(),PaymentActivity.class);
                startActivity(ii);
            }
        });


    }
}