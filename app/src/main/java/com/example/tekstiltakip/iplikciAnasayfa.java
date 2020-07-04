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

public class iplikciAnasayfa extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iplikci_anasayfa);
        final TextView firmaAdi = (TextView) findViewById(R.id.firmaAd);
        final Button istek = (Button) findViewById(R.id.istek);
        final Button stokEkle = (Button) findViewById(R.id.stokEkle);
        final ListView stokLV = (ListView) findViewById(R.id.stokLV);
        final FirebaseAuth mAuth= FirebaseAuth.getInstance();
        final ArrayList<String> uIdList = new ArrayList<>();
        final ArrayList<String> stokList = new ArrayList<>();
        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, stokList);
        final String kullaniciId =mAuth.getCurrentUser().getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(kullaniciId);
        mDatabase2= FirebaseDatabase.getInstance().getReference().child("iplikUrun").child(kullaniciId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kullanicilar kullanici= new kullanicilar();
                kullanici=dataSnapshot.getValue(kullanici.getClass());
                firmaAdi.setText(kullanici.firmaAd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stokList.clear();
                uIdList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String uId = ds.getKey();
                    stoklar stok = ds.getValue(stoklar.class);
                    uIdList.add(uId);
                    stokList.add(stok.getTuru()+" miktari: "+stok.getMiktari()+" fiyatı: "+stok.getFiyati());

                    stokLV.setAdapter(listViewAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        stokLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(iplikciAnasayfa.this,ipGuncelle.class);
                intent.putExtra("id",uIdList.get(i));
                startActivity(intent);
            }
        });

        stokEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(iplikciAnasayfa.this,stokEkle.class);
                startActivity(intent);
            }
        });
        istek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(iplikciAnasayfa.this,istekler.class);
                startActivity(intent);
            }
        });
    }
}
