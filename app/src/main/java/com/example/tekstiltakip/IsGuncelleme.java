package com.example.tekstiltakip;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class IsGuncelleme extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_guncelleme);

        final EditText tarih = findViewById(R.id.tarih);
        final EditText kUzunluk = findViewById(R.id.kUzunluk);
        final EditText iplik = findViewById(R.id.iplik);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference("isGuncelleme");

        userReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                GuncellemeClass guncelle = dataSnapshot.getValue(GuncellemeClass.class);

                Toast.makeText(IsGuncelleme.this,guncelle.kUzunluk,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        findViewById(R.id.btnKaydet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gtarih = tarih.getText().toString();
                String Uzunluk = kUzunluk.getText().toString();
                String iplikAdet = iplik.getText().toString();


                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference userReference = database.getReference("isGuncelleme");

                GuncellemeClass is = new GuncellemeClass(gtarih , Uzunluk,iplikAdet);

                String userId = userReference.push().getKey();

                userReference.child(userId).setValue(is);




            }
        });


    }
}
