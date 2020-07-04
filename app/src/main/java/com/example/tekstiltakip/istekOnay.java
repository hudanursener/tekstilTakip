package com.example.tekstiltakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class istekOnay extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    String miktar;
    String tur;
    String sonuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istek_onay);
        final ListView onaylv = findViewById(R.id.onaylv);
        final Button onayBtn = findViewById(R.id.onayBtn);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final String kullaniciId = mAuth.getCurrentUser().getUid();
        final ArrayList<String> iponay = new ArrayList<>();
        final ArrayList<String> miktar1l = new ArrayList<>();
        final ArrayList<String> miktar2l = new ArrayList<>();
        final ArrayList<String> idList = new ArrayList<>();
        final ArrayList<String> durumList = new ArrayList<>();
        final String ureticiId = getIntent().getExtras().getString("id");
        final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, iponay);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("iplikSiparis").child(kullaniciId).child(ureticiId);
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("iplikUrun").child(kullaniciId);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                iponay.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String durumId = ds.getKey();
                    iplikSiparis siparis = ds.getValue(iplikSiparis.class);

                    iponay.add(siparis.getTur() +  " " + siparis.getMiktar() + " " + siparis.getFiyati()+" "+siparis.getDurumu());
                   durumList.add(durumId);
                        miktar2l.add(siparis.getMiktar());
                    tur=siparis.getTur();
                    onaylv.setAdapter(listViewAdapter);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String uId = ds.getKey();
                    stoklar stok = ds.getValue(stoklar.class);

                    idList.add(uId);
                    miktar1l.add(stok.getMiktari());


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        onaylv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                System.out.println(miktar1l.get(i));
                System.out.println(miktar2l.get(i));
                sonuc= String.valueOf(hesapla(Integer.valueOf(miktar1l.get(i)),Integer.valueOf(miktar2l.get(i))));
                AlertDialog.Builder builder = new AlertDialog.Builder(istekOnay.this);

                builder.setTitle("onay ");
                builder.setMessage("onay veriyor musunuz ?");
                builder.setCancelable(true);
                builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        mDatabase2.child(idList.get(i)).child("miktari").setValue(sonuc);
                        mDatabase.child(durumList.get(i)).child("durumu").setValue("onaylandi");
                    }
                });
                builder.setNegativeButton("VAZGEÇ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.show();

            }
        });


    }

    private int hesapla(int miktar1, int miktar2) {
        int sonuc =0;
        sonuc=miktar1-miktar2;
        return sonuc;
    }


}
