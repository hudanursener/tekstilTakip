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

public class isEkle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_ekle);

        final EditText kUzunluk = findViewById(R.id.kUzunluk);
        final EditText ttarih = findViewById(R.id.ttarih);
        final EditText PamukDeger = findViewById(R.id.PamukDeger);
        final EditText polyesterTur = findViewById(R.id.polyesterTur);
        final EditText ketenTur = findViewById(R.id.ketenTur);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userReference = database.getReference("isler");

        userReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Isler is = dataSnapshot.getValue(Isler.class);

                Toast.makeText(isEkle.this,is.ttarih,Toast.LENGTH_SHORT).show();
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
                String Uzunluk = kUzunluk.getText().toString();
                String tarih = ttarih.getText().toString();
                String Pamuk = PamukDeger.getText().toString();
                String polyester = polyesterTur.getText().toString();
                String keten = ketenTur.getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference userReference = database.getReference("isler");

                Isler is = new Isler(Uzunluk , tarih,Pamuk,polyester,keten);

                String userId = userReference.push().getKey();

                userReference.child(userId).setValue(is);




            }
        });

    }
}
