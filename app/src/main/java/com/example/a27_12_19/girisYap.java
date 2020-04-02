package com.example.a27_12_19;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class girisYap extends AppCompatActivity {

    EditText eposta;
    EditText sifre;
    Button btngiris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        eposta = findViewById(R.id.eposta);
        sifre = findViewById(R.id.sifre);
        btngiris = findViewById(R.id.btngiris);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference mesaj = database.getReference("message");


        btngiris.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                DatabaseReference reference = database.child("users");


                Query phoneQuery = reference.orderByChild(eposta.getText().toString());
                phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                                              @Override
                                                              public void onDataChange(DataSnapshot dataSnapshot) {
                                                                  for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                                                                  }
                                                              }

                                                              @Override
                                                              public void onCancelled(@NonNull DatabaseError databaseError) {

                                                              }
                                                          });
                    Intent Intent = new Intent(girisYap.this,MainActivity.class);
                    startActivity(Intent);
                    String ePosta = eposta.getText().toString();
                    String Sifre = sifre.getText().toString();

                mesaj.setValue(eposta);
                mesaj.setValue(sifre);


            }

        });
    }
}
