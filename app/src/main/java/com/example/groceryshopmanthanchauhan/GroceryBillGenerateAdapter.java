package com.example.groceryshopmanthanchauhan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GroceryBillGenerateAdapter extends ArrayAdapter<String>
{
    Context cont;
    int resource;
    ArrayList gNameList;
    ArrayList gQtyList;
    ArrayList gPriceList;
    ArrayList gItemTotal;
    TextView tvGroceryGrandTotal;


    GroceryBillGenerateAdapter(Context cont, int resource, ArrayList gNameList,ArrayList gQtyList,ArrayList gPriceList,ArrayList gItemTotal,TextView tvGroceryGrandTotal)
    {
        super(cont,resource,gNameList);   //pasing gNameList decides how many rows will be created by adapter

        this.cont=cont;
        this.resource=resource;
        this.gNameList=gNameList;
        this.gQtyList=gQtyList;
        this.gPriceList=gPriceList;;
        this.gItemTotal=gItemTotal;
        this.tvGroceryGrandTotal=tvGroceryGrandTotal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflat=LayoutInflater.from(cont);
        View view=inflat.inflate(resource,null,false);

        TextView tName=view.findViewById(R.id.tvGroceryProductName);
        TextView tQty=view.findViewById(R.id.tvGroceryProductQty);
        TextView tPrice=view.findViewById(R.id.tvGroceryProductRate);
        TextView tTotal=view.findViewById(R.id.tvGroceryProductAmount);

        tName.setText(""+gNameList.get(position));
        tQty.setText(""+gQtyList.get(position));
        tPrice.setText(""+gPriceList.get(position));
        tTotal.setText(""+gItemTotal.get(position));




        return view;

    };


}
