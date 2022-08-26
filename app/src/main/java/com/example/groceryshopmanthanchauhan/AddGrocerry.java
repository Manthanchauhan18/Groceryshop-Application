package com.example.groceryshopmanthanchauhan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddGrocerry extends Activity
{

    EditText etAdaddname,etAdaddprice,etAdaddstock,etAdaddmeasurment;
    Button btAdaddimage,btAdadditem;
    ProgressBar pbAdadd;
    Uri imagePath;
    DatabaseReference dbRef;
    StorageReference storage,store;
    String gName,measure;
    int price,stock;
    String imageUrl;
    String gid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocerry);

        getWindow().setStatusBarColor(ContextCompat.getColor(AddGrocerry.this, R.color.black));

        etAdaddname = findViewById(R.id.etAdaddname);
        etAdaddprice = findViewById(R.id.etAdaddprice);
        etAdaddstock = findViewById(R.id.etAdaddstock);
        etAdaddmeasurment = findViewById(R.id.etAdaddmeasurment);

        btAdaddimage = findViewById(R.id.btAdaddimage);
        btAdadditem = findViewById(R.id.btAdadditem);

        pbAdadd = findViewById(R.id.pbAdadd);
        pbAdadd.setVisibility(ProgressBar.GONE);


        btAdaddimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);//for calling gallery
                startActivityForResult(gallery, 201);
            }
        });


        btAdadditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                pbAdadd.setVisibility(ProgressBar.VISIBLE);

                gName=etAdaddname.getText().toString();
                price=Integer.parseInt(etAdaddprice.getText().toString());
                stock=Integer.parseInt(etAdaddstock.getText().toString());
                measure=etAdaddmeasurment.getText().toString();

                dbRef=FirebaseDatabase.getInstance().getReference("grocery");
                storage=FirebaseStorage.getInstance().getReferenceFromUrl("gs://groceryshopmanthanchauhan.appspot.com/grocery");

                store=storage.child(gName);
                gid=dbRef.push().getKey();



                store.putFile(imagePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                            {
                                store.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri)
                                    {
                                        Toast.makeText(getApplicationContext(),"Image Uploaded....",Toast.LENGTH_LONG).show();

                                        Grocery g=new Grocery(gid,uri.toString(),gName,price,stock,measure);

                                        dbRef.child(gid).setValue(g);


                                        Toast.makeText(getApplicationContext(),"Grocerry added successfuly...",Toast.LENGTH_LONG).show();

                                        pbAdadd.setVisibility(pbAdadd.GONE);

                                        etAdaddmeasurment.setText("");
                                        etAdaddstock.setText("");
                                        etAdaddprice.setText("");
                                        etAdaddname.setText("");

                                    }
                                });
                            }
                        });


            }
        });

    }

    @Override
    public void onActivityResult(int reqCode,int result,Intent data)
    {
        if(result==RESULT_OK && reqCode==201)
        {
            imagePath = data.getData();

            Toast.makeText(getApplicationContext(),"Image Selected",Toast.LENGTH_LONG).show();

        }
    }


}