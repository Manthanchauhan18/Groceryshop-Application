package com.example.groceryshopmanthanchauhan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroceryOrder extends Activity
{
    static RecyclerView myRecyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    Button btGroceryOrderConfirm;
    TextView tvGroceryOrderBill;

    DatabaseReference dbRef;
    ArrayList<Grocery> groceryList;

    ArrayList<String> gNameList = new ArrayList<String>();
    ArrayList<String> gQtyList = new ArrayList<String>();
    ArrayList<String> gPriceList = new ArrayList<String>();
    ArrayList<String> gTotalList = new ArrayList<String>();
    int grandTotal;
    int total[];

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_order);

        btGroceryOrderConfirm = findViewById(R.id.btGroceryOrderConfirm);
        tvGroceryOrderBill = findViewById(R.id.tvGroceryOrderBill);

        myRecyclerView = findViewById(R.id.recyclerGroceryOrderList);
        myRecyclerView.setHasFixedSize(true);       // It prevents repetation of same object.

        layoutManager = new GridLayoutManager(getApplicationContext(), 1);          // spanCount=1 is Only one column in each row
        myRecyclerView.setLayoutManager(layoutManager);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        dbRef = FirebaseDatabase.getInstance().getReference("grocery");
        groceryList = new ArrayList<Grocery>();

        dbRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                groceryList.clear();

                for (DataSnapshot shot : dataSnapshot.getChildren())
                {
                    Grocery g = shot.getValue(Grocery.class);
                    groceryList.add(g);
                }

                myRecyclerView.setItemViewCacheSize(groceryList.size());        //exactly no of rows would be created as per object fetch of Grocery
                total = new int[groceryList.size()];

                adapter = new GroceryOrderAdapter(GroceryOrder.this, groceryList, tvGroceryOrderBill, btGroceryOrderConfirm,gNameList, gQtyList, gPriceList, gTotalList, total);
                myRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                Toast.makeText(getApplicationContext(), "List Not Coming...", Toast.LENGTH_LONG).show();
            }
        });



        btGroceryOrderConfirm.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent bill = new Intent(getApplicationContext(), GroceryOrderBill.class);

                bill.putExtra("name", gNameList);
                bill.putExtra("qty", gQtyList);
                bill.putExtra("price", gPriceList);
                bill.putExtra("total", gTotalList);
                bill.putExtra("grandTotal", total);

                startActivity(bill);

            }
        });




    }


































}