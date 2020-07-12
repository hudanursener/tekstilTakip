package com.example.tekstiltakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AnaSayfa extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    private DatabaseReference mDatabase3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_sayfa);

        final FirebaseAuth mAuth= FirebaseAuth.getInstance();
        final TextView firmaIsim =  findViewById(R.id.firmaIsim);
        final Button iplikİste = findViewById(R.id.iplikİste);
        final ListView listv = findViewById(R.id.listv);
        final String kullaniciId =mAuth.getCurrentUser().getUid();
        final ArrayList<String> arraylist = new ArrayList<>();
        final ArrayList<String> siparisId = new ArrayList<>();
        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, arraylist);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(kullaniciId);
        mDatabase2= FirebaseDatabase.getInstance().getReference().child("siparis");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kullanicilar kullanici= new kullanicilar();
                kullanici=dataSnapshot.getValue(kullanici.getClass());
                firmaIsim.setText(kullanici.firmaAd);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String firmaId = ds.getKey();
                  mDatabase3=FirebaseDatabase.getInstance().getReference().child("siparis").child(firmaId).child(mAuth.getCurrentUser().getUid());
                  mDatabase3.addValueEventListener(new ValueEventListener() {
                      @Override
                      public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                          arraylist.clear();
                          for (DataSnapshot ds : dataSnapshot.getChildren()) {
                              String firmaSiparisId = ds.getKey();
                              siparisler firmalar = ds.getValue(siparisler.class);
                              siparisId.add(firmaSiparisId);
                              arraylist.add(firmalar.getFirma()+"\nKumaş Oranı: " +firmalar.getOrani()+"\nÜretilecek Miktar: " +firmalar.getMiktari()+"\nMetrekare Fiyatı: "+firmalar.getFiyati()+"\nTeslim Tarihi: "+firmalar.getSon_tarih()+"\nSon Güncelleme: "+firmalar.durumu);

                              listv.setAdapter(listViewAdapter);

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
        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent= new Intent(AnaSayfa.this,SiparisBilgileri.class);
                intent.putExtra("id",siparisId.get(i));
                startActivity(intent);
            }
        });

        iplikİste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(AnaSayfa.this,isEkleIp.class);
                startActivity(intent);
            }
        });


    }
}
