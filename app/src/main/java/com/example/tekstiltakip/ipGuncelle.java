package com.example.tekstiltakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ipGuncelle extends AppCompatActivity {
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip_guncelle);
        final EditText tur = findViewById(R.id.tur);
        final EditText miktar = findViewById(R.id.miktar);
        final EditText fiyat = findViewById(R.id.fiyat);
        Button guncelle = findViewById(R.id.guncelle);
        String uId = getIntent().getExtras().getString("id");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String kullaniciId = mAuth.getCurrentUser().getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("iplikUrun").child(kullaniciId).child(uId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                stoklar stok = new stoklar();
                stok = dataSnapshot.getValue(stok.getClass());

                    tur.setText(stok.getTuru());
                    miktar.setText(stok.getMiktari());
                    fiyat.setText(stok.getFiyati());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String turu= tur.getText().toString();
                String miktari= miktar.getText().toString();
                String fiyati= fiyat.getText().toString();
               mDatabase.child("turu").setValue(turu);
               mDatabase.child("miktari").setValue(miktari);
               mDatabase.child("fiyati").setValue(fiyati);
            }
        });
    }
}
