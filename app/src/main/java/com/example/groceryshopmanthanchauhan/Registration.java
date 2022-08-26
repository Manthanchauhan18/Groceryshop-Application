 package com.example.groceryshopmanthanchauhan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.FieldClassification;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends Activity
{

    TextView tv1;
    EditText etema1,etpass1,etna1,etadd1,etphno1;
    Button btreg1,btcan1;
    String uid;
    FirebaseAuth fAuth;
    DatabaseReference dbRef;
    int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getWindow().setStatusBarColor(ContextCompat.getColor(Registration.this,R.color.black));

        tv1=findViewById(R.id.tv1);
        etema1=findViewById(R.id.etema1);
        etpass1=findViewById(R.id.etpass1);
        etna1=findViewById(R.id.etna1);
        etadd1=findViewById(R.id.etadd1);
        etphno1=findViewById(R.id.etphno1);
        btreg1=findViewById(R.id.btreg1);
        btcan1=findViewById(R.id.btcan1);

        btreg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email=etema1.getText().toString();
                String pass=etpass1.getText().toString();
                String name=etna1.getText().toString();
                String address=etadd1.getText().toString();
                String phone=etphno1.getText().toString();



                String expname="^[A-Z a-z]{3,15}$";
                Pattern patname=Pattern.compile(expname);
                Matcher matname=patname.matcher(name);

                if(matname.matches()==false)
                {
                    Toast.makeText(getApplicationContext(),"Empty Name or Invalid Input",Toast.LENGTH_LONG).show();
                    etna1.requestFocus();
                    flag=1;
                }



                String expemail="^[a-zA-z0-9+_.-]+@[a-zA-Z0-9._]+$";
                Pattern patemail=Pattern.compile(expemail);
                Matcher matemail=patemail.matcher(email);

                if(matemail.matches()==false)
                {
                    Toast.makeText(getApplicationContext(),"Empty Mail or Invalid Input",Toast.LENGTH_LONG).show();
                    etema1.requestFocus();
                    flag=1;
                }



                String expPh="^[6-9]{1}[0-9]{9}$";
                Pattern patPh= Pattern.compile(expPh);
                Matcher matPh= patPh.matcher(phone);

                if(matPh.matches()==false)
                {
                    Toast.makeText(getApplicationContext(),"Invalid Phone or it is empty",Toast.LENGTH_LONG).show();
                    etphno1.requestFocus();

                    flag=1;
                }


                if(address.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"address is empty",Toast.LENGTH_LONG).show();
                    etadd1.requestFocus();

                    flag=1;
                }




                String exppass="^[A-Z]{1}[a-z0-9]{8,12}$";
                Pattern patpass=Pattern.compile(exppass);
                Matcher matpass=patpass.matcher(pass);

                if(pass.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"passward is empty",Toast.LENGTH_LONG).show();
                    etpass1.requestFocus();

                    flag=1;
                }




                if(flag==0) {

                    fAuth = FirebaseAuth.getInstance();
                    fAuth.createUserWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        dbRef = FirebaseDatabase.getInstance().getReference("User");

                                        uid = dbRef.push().getKey();
                                        User user = new User(uid, email, pass, name, address, phone);
                                        dbRef.child(uid).setValue(user);


                                        Toast.makeText(getApplicationContext(), "User Registerd Successfully", Toast.LENGTH_LONG).show();

                                        Intent ii = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(ii);

                                    }

                                }
                            });


                }
            }
        });

        btcan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                etema1.setText("");
                etpass1.setText("");
                etna1.setText("");
                etadd1.setText("");
                etphno1.setText("");

            }
        });

    }
}