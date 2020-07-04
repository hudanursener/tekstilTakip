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
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class isEkleIp extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    private DatabaseReference mDatabase3;
    private DatabaseReference mDatabase4;
    FirebaseAuth mAuth =FirebaseAuth.getInstance();
    String firmaId;
    String turId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_is_ekle_ip);
        final Spinner firmaSpn = findViewById(R.id.firmaSpn);
        final Spinner turSpn = findViewById(R.id.turSpn);
        final EditText miktarEt = findViewById(R.id.miktarEt);
        final TextView fiyat = findViewById(R.id.fiyat);
        final Button onay = findViewById(R.id.onay);
        final ArrayList<String> firmaIdList = new ArrayList<>();
        final ArrayList<String> turIdList = new ArrayList<>();
        final ArrayList<String> firmaList = new ArrayList<>();
        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, firmaList);
        final ArrayList<String> turList = new ArrayList<>();
        final ArrayAdapter<String> listViewAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, turList);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("iplikUrun");
        mDatabase2= FirebaseDatabase.getInstance().getReference().child("Kullanicilar");


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String kullaniciId = ds.getKey();
                mDatabase2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
                            String kId = ds2.getKey();
                            kullanicilar kullanici = ds2.getValue(kullanicilar.class);
                            if (kId.equals(kullaniciId)){
                            firmaList.add(kullanici.getFirmaAd());
                            firmaIdList.add(kId);
                            firmaSpn.setAdapter(listViewAdapter);
                                firmaSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {

                                        firmaId=firmaIdList.get(position);
                                        mDatabase3= FirebaseDatabase.getInstance().getReference().child("iplikUrun").child(firmaId);
                                        mDatabase3.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                turList.clear();
                                                turIdList.clear();
                                                for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
                                                    String kId2 = ds2.getKey();
                                                    turIdList.add(kId2);
                                                    final stoklar stok = ds2.getValue(stoklar.class);
                                                    turList.add(stok.getTuru());
                                                    turSpn.setAdapter(listViewAdapter2);


                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        turSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                turId=turIdList.get(i);
                mDatabase3= FirebaseDatabase.getInstance().getReference().child("iplikUrun").child(firmaId);
                mDatabase3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
                            String kId2 = ds2.getKey();

                            final stoklar stok = ds2.getValue(stoklar.class);
                            if (turId.equals(kId2)){

                                int sonuc=0;
                                String miktar =miktarEt.getText().toString();
                                sonuc=Integer.valueOf( miktar)*Integer.valueOf( stok.getFiyati());
                                fiyat.setText(sonuc+" ");
                            }




                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        onay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firmaAd = firmaSpn.getSelectedItem().toString();
                String miktar=miktarEt.getText().toString();
                String tur = turSpn.getSelectedItem().toString();
                String fiyati=fiyat.getText().toString();

              String kullaniciId =mAuth.getCurrentUser().getUid();
                mDatabase= FirebaseDatabase.getInstance().getReference().child("iplikSiparis").child(firmaId).child(kullaniciId).push();
                HashMap<String,String> ipsiparis = new HashMap<>();
                ipsiparis.put("firmaAd",firmaAd);
                ipsiparis.put("miktar",miktar);
                ipsiparis.put("tur",tur);
                ipsiparis.put("fiyati",fiyati);
                ipsiparis.put("durumu","bekliyor");
                mDatabase.setValue(ipsiparis);
            }
        });
    }
}
