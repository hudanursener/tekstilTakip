package com.example.tekstiltakip;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ureticiFirmaSiparis extends AppCompatActivity {
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uretici_firma_siparis);
        String uId = getIntent().getExtras().getString("id");
        final ListView siparisLv = (ListView) findViewById(R.id.siparisLV);
        final FirebaseAuth mAuth= FirebaseAuth.getInstance();
        final String kullaniciId =mAuth.getCurrentUser().getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("siparis").child(kullaniciId).child(uId);
        final ArrayList<String> siparisList = new ArrayList<>();
        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, siparisList);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    siparisler siparis = ds.getValue(siparisler.class);

                    siparisList.add( siparis.firma+"\nÜRÜN ORANI: "+siparis.orani+ "\nÜRETİLECEK MİKTAR: "+ siparis.miktari+ "\nMETREKARE FİYATI: "+ siparis.fiyati+ "\nTESLİM TARİHİ: "+siparis.Son_tarih+"\nSİPARİŞ DURUMU: "+siparis.durumu);

                    siparisLv.setAdapter(listViewAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
