package com.example.groceryshopmanthanchauhan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;

public class GroceryOrderAdapter extends RecyclerView.Adapter<GroceryOrderAdapter.MyViewHolder>
{
    Context cont;
    ArrayList<Grocery> dataSet;
    TextView tvGroceryOrderBill;
    Button btGroceryOrderConfirm;

    int total[];
    int grandTotal;
    int checkIndex[];
    int totalNoOfGroceryBought = 0;
    int exists = 0;

    ArrayList<String> gNameList;
    ArrayList<String> gQtyList;
    ArrayList<String> gPriceList;
    ArrayList<String> gTotalList;

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView groceryImage;
        TextView groceryName,groceryPrice;
        ElegantNumberButton groceryButton;

        MyViewHolder(View itemView)
        {
            super(itemView);

            groceryImage = itemView.findViewById(R.id.cardGroceyOrderImage);
            groceryName = itemView.findViewById(R.id.tvCardGroceryOrderName);
            groceryPrice = itemView.findViewById(R.id.tvCardGroceryOrderPrice);
            groceryButton = itemView.findViewById(R.id.elegantNumGroceryOrder);

        }
    }

    GroceryOrderAdapter(Context cont , ArrayList<Grocery> list, TextView tvGroceryOrderBill, Button btGroceryOrderConfirm, ArrayList<String> gNameList, ArrayList<String> gQtyList, ArrayList<String> gPriceList, ArrayList<String> gTotalList, int total[])
    {
        this.cont = cont;
        this.dataSet = list;
        this.tvGroceryOrderBill = tvGroceryOrderBill;
        this.btGroceryOrderConfirm = btGroceryOrderConfirm;

        checkIndex = new int[dataSet.size()];
        this.total = total;
//        total = new int[dataSet.size()];

        this.gNameList = gNameList;
        this.gQtyList = gQtyList;
        this.gPriceList = gPriceList;
        this.gTotalList = gTotalList;

        this.grandTotal = grandTotal;

        for(int i=0;i<checkIndex.length;i++)
        {
            checkIndex[i] = -1;
        }
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // it is used to connect any xml file with non-activity class in android

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.groceryordercardview,parent,false);


        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position)
    {
        // this method will be called no of times matches with the no getItemCount() returns

        ImageView groceryImage = holder.groceryImage;
        final TextView groceryName = holder.groceryName;
        TextView groceryPrice = holder.groceryPrice;
        final ElegantNumberButton groceryButton = holder.groceryButton;

        groceryName.setText(dataSet.get(position).getgName());
        groceryPrice.setText("Rs. "+dataSet.get(position).getPrice());

        final int i = position;

        Glide.with(cont)
                .load(dataSet.get(position).getImageUrl())
                .override(800,400)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(groceryImage);

        groceryButton.setOnClickListener(new ElegantNumberButton.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                //   Toast.makeText(cont,"NAME : "+dataSet.get(i).getgName(),Toast.LENGTH_LONG).show();

                int qty = Integer.parseInt(groceryButton.getNumber());

                if(qty == 0)         // it will be executed when user shift stock to zero after buying grocery
                {
                    for(int k=0;k<checkIndex.length-1;k++)
                    {
                        if(checkIndex[k]==position)
                        {
                            for(int j=k;j<checkIndex.length-1;j++)
                            {
                                checkIndex[j] = checkIndex[j+1];
                                total[j] = total[j+1];
                            }

                            if(position == checkIndex.length -1)
                            {
                                checkIndex[position] = -1;
                                total[position] = 0;
                            }

                            totalNoOfGroceryBought--;
                            gNameList.remove(k);
                            gPriceList.remove(k);
                            gQtyList.remove(k);
                            gTotalList.remove(k);

//                            Toast.makeText(cont,""+totalNoOfGroceryBought+" , "+gNameList.size(),Toast.LENGTH_LONG).show();

                            break;
                        }
                    }
                }
                else
                {
                    //  if user increase or decrease the qty of grocery
                    int flag = 0;


                    for (exists = 0; exists < checkIndex.length; exists++)          // loop is used to find grocery in checkindex
                    {
                        if (checkIndex[exists] == position)         //  we found grocery in checkindex ==> client has bought this grocery
                        {
                            flag = 1;
                            break;
                        }
                    }

                    if (flag == 0)      // user has not bought grocery. he is buying now
                    {
                        checkIndex[totalNoOfGroceryBought] = position;
                        total[totalNoOfGroceryBought++] = dataSet.get(position).getPrice() * Integer.parseInt(groceryButton.getNumber());

                        gNameList.add(dataSet.get(position).getgName());
                        gPriceList.add("" + dataSet.get(position).getPrice());
                        gQtyList.add("" + groceryButton.getNumber());
                        gTotalList.add("" + dataSet.get(position).getPrice() * Integer.parseInt(groceryButton.getNumber()));
                    }
                    else             //  programing for grocery already bought with quantity
                    {
                        gQtyList.set(exists, "" + groceryButton.getNumber());
                        gTotalList.set(exists, "" + dataSet.get(position).getPrice() * Integer.parseInt(groceryButton.getNumber()));

                        total[exists] = dataSet.get(position).getPrice() * Integer.parseInt(groceryButton.getNumber());
                    }

                    String strr = "";
                    for (int r = 0; r < gNameList.size(); r++)
                    {
                        strr = strr + gNameList.get(r) + " , " + gQtyList.get(r) + " , " + gPriceList.get(r) + "\n";
                    }

//                    Toast.makeText(cont,str,Toast.LENGTH_LONG).show();
                }

                grandTotal = 0;

                for(int r = 0;r<gNameList.size();r++)
                {
                    grandTotal = grandTotal + total[r];
                }

                tvGroceryOrderBill.setText("Rs. "+grandTotal);



            }
        });






    }

    @Override
    public int getItemCount()   //  it decides how many object of grocery should be displayed on recyclerview
    {
        return dataSet.size();  // according to this, it generates rows for recyclerview
    }

}