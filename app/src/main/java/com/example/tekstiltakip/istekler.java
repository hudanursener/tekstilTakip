package com.example.tekstiltakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class istekler extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    private DatabaseReference mDatabase3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istekler);
        final ListView firmaLv= findViewById(R.id.firmaLv);
        final FirebaseAuth mAuth= FirebaseAuth.getInstance();
        final ArrayList<String> firmalist = new ArrayList<>();
        final ArrayList<String> firmaIdlist = new ArrayList<>();
        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, firmalist);
        final String kullaniciId =mAuth.getCurrentUser().getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Kullanicilar");
        mDatabase2= FirebaseDatabase.getInstance().getReference().child("iplikSiparis").child(kullaniciId);
        firmaLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(istekler.this,istekOnay.class);
                intent.putExtra("id",firmaIdlist.get(i));
                startActivity(intent);
            }
        });
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String ureticiId = ds.getKey();
                   mDatabase.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           firmalist.clear();
                           for (DataSnapshot ds : dataSnapshot.getChildren()) {
                               String kId = ds.getKey();
                               kullanicilar kullanici = ds.getValue(kullanicilar.class);
                               if (kId.equals(ureticiId)){
                                   firmalist.add(kullanici.firmaAd);
                                   firmaIdlist.add(kId);
                                   firmaLv.setAdapter(listViewAdapter);
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

    }
}
