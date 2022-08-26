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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class UpdateGrocery extends Activity
{

    EditText etAdUpname, etAdUpprice, etAdUpstock, etAdUpmeasure;
    Button btAdUpUpgrocey, btAdUpUpimage;
    ProgressBar pbAdUp;
    Spinner spAdUp;

    List<String> listName = new ArrayList<String>();
    List<Grocery> listObj = new ArrayList<Grocery>();

    DatabaseReference dbRef;
    StorageReference storage;

    String gid, gUrl, gName, measure;
    int price, stock;

    Uri imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_grocery);

        getWindow().setStatusBarColor(ContextCompat.getColor(UpdateGrocery.this,R.color.black));

        etAdUpname = findViewById(R.id.etAdUpname);
        etAdUpprice = findViewById(R.id.etAdUpprice);
        etAdUpstock = findViewById(R.id.etAdUpstock);
        etAdUpmeasure = findViewById(R.id.etAdUpmeasure);

        btAdUpUpimage = findViewById(R.id.btAdUpUpimage);
        btAdUpUpgrocey = findViewById(R.id.btAdUpUpgrocery);

        pbAdUp = findViewById(R.id.pbAdUp);
        pbAdUp.setVisibility(ProgressBar.GONE);

        spAdUp = findViewById(R.id.spAdUp);

        dbRef = FirebaseDatabase.getInstance().getReference("grocery");
        storage = FirebaseStorage.getInstance().getReferenceFromUrl("gs://groceryshopmanthanchauhan.appspot.com/grocery");

        dbRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                listName.clear();
                listObj.clear();

                for (DataSnapshot shot : dataSnapshot.getChildren())
                {
                    Grocery grocery = shot.getValue(Grocery.class);

                    listName.add(grocery.getgName());
                    listObj.add(grocery);
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, listName);
                spAdUp.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        spAdUp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                gUrl = listObj.get(position).getImageUrl();
                gid = listObj.get(position).getGid();
                gName = listObj.get(position).getgName();
                price = listObj.get(position).getPrice();
                stock = listObj.get(position).getStock();
                measure = listObj.get(position).getMeasure();

                etAdUpname.setText(gName);
                etAdUpprice.setText("" + price);
                etAdUpstock.setText("" + stock);
                etAdUpmeasure.setText(""+measure);

                pbAdUp.setVisibility(ProgressBar.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        btAdUpUpimage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent image = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

                startActivityForResult(image, 201);

//                Intent image = new Intent(Intent.ACTION_GET_CONTENT);
//                image.setType("image/*");
//                startActivityForResult(Intent.createChooser(image, "Select Image"), 201);
            }
        });

        btAdUpUpgrocey.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pbAdUp.setVisibility(ProgressBar.VISIBLE);
                dbRef = FirebaseDatabase.getInstance().getReference("grocery");

                price = Integer.parseInt(etAdUpprice.getText().toString());
                stock = Integer.parseInt(etAdUpstock.getText().toString());
                measure = etAdUpmeasure.getText().toString();

                Grocery gr = new Grocery(gid, gUrl, gName, price, stock, measure);

                dbRef.child(gid).setValue(gr);
                Toast.makeText(getApplicationContext(), "Grocery Updated..." , Toast.LENGTH_LONG).show();
                pbAdUp.setVisibility(ProgressBar.GONE);
            }
        });
    }


    @Override
    public void onActivityResult(int reqCode, int res, Intent data)
    {
        if (res == RESULT_OK && reqCode == 201)
        {
            imagePath = data.getData();

            Toast.makeText(getApplicationContext(), " Image Selected Successfully... ", Toast.LENGTH_LONG).show();
        }
    }


}