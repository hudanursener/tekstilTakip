package com.example.tekstiltakip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class stokEkle extends AppCompatActivity {
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok_ekle);
        final EditText tur =  findViewById(R.id.tur);
        final EditText miktar =  findViewById(R.id.miktar);
        final EditText fiyat =  findViewById(R.id.fiyat);
        final Button ekle =  findViewById(R.id.ekle);
        final FirebaseAuth mAuth= FirebaseAuth.getInstance();

        ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String turu=tur.getText().toString();
                String miktari=miktar.getText().toString();
                String fiyati=fiyat.getText().toString();
                final String kullaniciId =mAuth.getCurrentUser().getUid();
                mDatabase= FirebaseDatabase.getInstance().getReference().child("iplikUrun").child(kullaniciId).push();
                HashMap<String,String> stok = new HashMap<>();
                stok.put("turu",turu);
                stok.put("miktari",miktari);
                stok.put("fiyati",fiyati);
                mDatabase.setValue(stok);
                Toast.makeText(getApplicationContext(),"İplik Türü Eklendi.",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(stokEkle.this, iplikciAnasayfa.class);
                startActivity(intent);


            }
        });
    }
}
