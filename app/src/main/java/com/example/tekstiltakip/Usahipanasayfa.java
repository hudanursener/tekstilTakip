package com.example.tekstiltakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
        final ArrayList<String> siparisIdList = new ArrayList<>();
        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, siparisList);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(kullaniciId);
        mDatabase3= FirebaseDatabase.getInstance().getReference().child("Kullanicilar");
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
                siparisList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String kullaniciId = ds.getKey();
                    mDatabase3.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
                                final String kullaniciId2 = ds2.getKey();
                                kullanicilar kullanici = ds2.getValue(kullanicilar.class);
                                if (kullaniciId.equals(kullaniciId2)){
                                    siparisList.add(kullanici.firmaAd);
                                    siparisIdList.add(kullaniciId);
                                    siparisLv.setAdapter(listViewAdapter);
                                }
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
        siparisLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(Usahipanasayfa.this,ureticiFirmaSiparis.class);
                intent.putExtra("id",siparisIdList.get(i));
                startActivity(intent);
            }
        });
    }
}
