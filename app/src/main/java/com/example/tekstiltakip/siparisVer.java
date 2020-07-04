package com.example.tekstiltakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class siparisVer extends AppCompatActivity {
    private DatabaseReference mDatabase;
    String firmaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siparis_ver);

        final Spinner firmalar = findViewById(R.id.firmalar);
        final EditText oran = findViewById(R.id.oran);
        final EditText miktar = findViewById(R.id.miktar);
        final EditText fiyat = findViewById(R.id.fiyat);
        final EditText tarih = findViewById(R.id.tarih);
        Button onay = findViewById(R.id.onay);
        final ArrayList<String> firmaList = new ArrayList<>();
        final ArrayList<String> firmaIdList = new ArrayList<>();
        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, firmaList);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Kullanicilar");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String kullaniciId = ds.getKey();
                    kullanicilar kullanici = ds.getValue(kullanicilar.class);
                    if (kullanici.getkTuru().equals("üretici")) {
                        firmaList.add(kullanici.getFirmaAd());
                        firmalar.setAdapter(listViewAdapter);
                        firmaIdList.add(kullaniciId);
                        firmalar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                firmaId = firmaIdList.get(position);


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        onay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String orani = oran.getText().toString();
                String miktari = miktar.getText().toString();
                String fiyati = fiyat.getText().toString();
                String tarihi = tarih.getText().toString();
                String firma = firmalar.getSelectedItem().toString();
                final FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mDatabase = FirebaseDatabase.getInstance().getReference().child("siparis").child(mAuth.getCurrentUser().getUid()).child(firmaId).push();
                HashMap<String, String> siparis = new HashMap<>();
                siparis.put("firma", firma);
                siparis.put("orani", orani);
                siparis.put("miktari", miktari);
                siparis.put("fiyati", fiyati);
                siparis.put("Son_tarih", tarihi);
                siparis.put("durumu", "bekliyor");
                mDatabase.setValue(siparis);
            }
        });
    }
}
