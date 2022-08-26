package com.example.groceryshopmanthanchauhan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentActivity extends Activity implements PaymentResultListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        Checkout check=new Checkout();

        check.setKeyID("rzp_test_haZAuf8OuK4ZWA");

        JSONObject jason=new JSONObject();

        PaymentInfo payInfo=(PaymentInfo)getApplication();

        try
        {

            jason.put("Name",payInfo.name);
            jason.put("Description","Thanks For Purchase Grocery");
            jason.put("theme.color","#0093DD");
            jason.put("Currency","INR");
            jason.put("Amount",payInfo.amount*100);
            jason.put("prefill.contact",payInfo.mobile);
            jason.put("prefill.email",payInfo.mail);

            check.open(this,jason);

        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"Payment Error:"+e.getMessage(),Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onPaymentSuccess(String s)
    {

        Toast.makeText(getApplicationContext(),"Payment Successful"+s,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPaymentError(int i, String s)
    {

        Toast.makeText(getApplicationContext(),"Payment Fail"+s,Toast.LENGTH_LONG).show();
        finish();
    }
}