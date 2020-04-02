package com.example.a27_12_19;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedReader;

public class kayitActivity  extends AppCompatActivity {


    private EditText isim;
    private EditText soyisim;
    private EditText firmaAdi;
    private EditText eposta;
    private EditText sifre;
    private Button KayitButon;
    private FirebaseAuth Auth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kayit);

        isim = (EditText) findViewById(R.id.isim);
        soyisim = (EditText) findViewById(R.id.soyISim);
        firmaAdi = (EditText) findViewById(R.id.firmaAdi);
        eposta = (EditText) findViewById(R.id.eposta);
        sifre = (EditText) findViewById(R.id.sifre);
        KayitButon = (Button) findViewById(R.id.kayitButon);

        Auth=FirebaseAuth.getInstance();


        KayitButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ad  = isim.getText().toString();
                String soyad = soyisim.getText().toString();
                String fAdi = firmaAdi.getText().toString();
                String Eposta = eposta.getText().toString();
                String Sifre = sifre.getText().toString();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(); // firebase class .getinstance diiynce static olduğu için sadece singletyon tek obje üsütndn donuyor
                DatabaseReference userReference = firebaseDatabase.getReference("users"); // tablo ya ulaştık (veriyi modellememk lazım)

                User user = new User(ad,soyad,fAdi,Eposta,Sifre);
                userReference.child(ad).setValue(user);

                if (!TextUtils.isEmpty(ad) && !TextUtils.isEmpty(soyad) && !TextUtils.isEmpty(fAdi)
                        && !TextUtils.isEmpty(Eposta) && !TextUtils.isEmpty(Sifre)){

                } else{
                    Toast.makeText(getApplicationContext(),"Butun alanları doldurmanız gerekiyor",Toast.LENGTH_SHORT).show();
                }
            }
        });
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(); // firebase class .getinstance diiynce static olduğu için sadece singletyon tek obje üsütndn donuyor
        DatabaseReference userReference = firebaseDatabase.getReference("users"); // tablo ya ulaştık (veriyi modellememk lazım)

        userReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                User user = dataSnapshot.getValue(User.class);Toast.makeText(kayitActivity.this,"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });









    }
}
