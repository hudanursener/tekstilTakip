package com.example.tekstiltakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Usahipanasayfa extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    private DatabaseReference mDatabase3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usahipanasayfa);
        final Button siparis = (Button) findViewById(R.id.siparis);
        final TextView firmaAd = (TextView) findViewById(R.id.firmaAd);
        final ListView siparisLv = (ListView) findViewById(R.id.siparisLv);
        final FirebaseAuth mAuth= FirebaseAuth.getInstance();
        final String kullaniciId =mAuth.getCurrentUser().getUid();
        final ArrayList<String> siparisList = new ArrayList<>();
        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, siparisList);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(kullaniciId);
        mDatabase2= FirebaseDatabase.getInstance().getReference().child("siparis").child(kullaniciId);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kullanicilar kullanici= new kullanicilar();
                kullanici=dataSnapshot.getValue(kullanici.getClass());
                firmaAd.setText(kullanici.firmaAd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String kullaniciId = ds.getKey();
                   mDatabase3=mDatabase2.child(kullaniciId);
                   mDatabase3.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           siparisList.clear();
                           for (DataSnapshot ds : dataSnapshot.getChildren()) {

                               siparisler siparis = ds.getValue(siparisler.class);

                               siparisList.add( siparis.firma+" "+siparis.orani+ " "+ siparis.miktari+ " "+ siparis.fiyati+ " "+siparis.Son_tarih+" "+siparis.durumu);

                               siparisLv.setAdapter(listViewAdapter);

                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        siparis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Usahipanasayfa.this, siparisVer.class);
                startActivity(intent);
            }
        });
    }
}
