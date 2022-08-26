package com.example.groceryshopmanthanchauhan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MainActivity extends Activity
{

    TextView tv,tvnew,tvforg;
    EditText etema,etpass;
    Button btlog,btcan;
    String mail,pass;
    FirebaseAuth fAuth;
    DatabaseReference DbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.black));

        tv=findViewById(R.id.tv);
        tvnew=findViewById(R.id.tvnew);
        tvforg=findViewById(R.id.tvforg);
        etema=findViewById(R.id.etema);
        etpass=findViewById(R.id.etpass);
        btlog=findViewById(R.id.btlog);
        btcan=findViewById(R.id.btcan);


        tvnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent ii=new Intent(getApplicationContext(),Registration.class);

                startActivity(ii);
            }
        });

        btlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mail=etema.getText().toString();
                pass=etpass.getText().toString();




                if(mail.equals("admin")&& pass.equals("food"))
                {
                    Toast.makeText(getApplicationContext(),"Welcome Admin",Toast.LENGTH_LONG).show();

                    DbRef= FirebaseDatabase.getInstance().getReference("user");


                    Intent ii8=new Intent(getApplicationContext(),AdmineHome.class);
                    startActivity(ii8);


                }
                else {

                    fAuth = FirebaseAuth.getInstance();

                    fAuth.signInWithEmailAndPassword(mail, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Authorised User", Toast.LENGTH_LONG).show();

                                        DbRef= FirebaseDatabase.getInstance().getReference("User");
                                        Query findQ=DbRef.orderByChild("email").equalTo(mail);

                                        findQ.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot)
                                            {
                                                User u1=null;
                                                for(DataSnapshot snap: snapshot.getChildren())
                                                {
                                                    u1=snap.getValue(User.class);
                                                }
                                                PaymentInfo payInfo=(PaymentInfo)getApplication();

                                                payInfo.name=u1.getName();
                                                payInfo.addr=u1.getAddr();
                                                payInfo.mobile=u1.getMobile();
                                                payInfo.mail=u1.getEmail();
                                                payInfo.amount=0;


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });

                                        Intent ii=new Intent(getApplicationContext(),GroceryOrder.class);
                                        startActivity(ii);

                                    }

                                    else {
                                        Toast.makeText(getApplicationContext(), "UnAuthhorised user", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

        btcan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                etema.setText("");
                etpass.setText("");
            }
        });


    }
}