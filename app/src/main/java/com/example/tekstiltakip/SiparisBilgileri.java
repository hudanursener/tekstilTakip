package com.example.tekstiltakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SiparisBilgileri extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siparis_bilgileri);
        final String siparisIdIntent = getIntent().getExtras().getString("id");
        final TextView istenenMiktar =  findViewById(R.id.istenenMiktar);
        final EditText miktarGuncelle = findViewById(R.id.miktarGuncelle);
        Button onay = findViewById(R.id.onay);
        final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String kullaniciId = mAuth.getCurrentUser().getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("siparis");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String firmaId = ds.getKey();

                    mDatabase2=FirebaseDatabase.getInstance().getReference().child("siparis").child(firmaId).child(mAuth.getCurrentUser().getUid());
                    mDatabase2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
                                String siparisId = ds2.getKey();
                                if (siparisId.equals(siparisIdIntent)) {
                                    System.out.println(siparisId);
                                    siparisler siparis = ds2.getValue(siparisler.class);
                                    istenenMiktar.setText("istenilen miktar: " + siparis.getMiktari() + " " + siparis.getOrani());
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
        onay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String guncelle= miktarGuncelle.getText().toString();
                mDatabase2.child(siparisIdIntent).child("durumu").setValue(guncelle);
            }
        });
    }
}
