package com.example.a27_12_19;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final EditText Isim = findViewById(R.id.isim);
        final EditText Soyisim = findViewById(R.id.soyISim);
        final EditText FirmaAdi = findViewById(R.id.firmaAdi);
        final EditText ePosta = findViewById(R.id.eposta);
        final EditText Sifre = findViewById(R.id.sifre);


        findViewById(R.id.kayitButon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isimStr = Isim.getText().toString();
                String soyisimStr = Soyisim.getText().toString();
                String firmaAdiStr = FirmaAdi.getText().toString();
                String epostaStr = ePosta.getText().toString();
                String sifreStr = Sifre.getText().toString();

               FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance(); // firebase class .getinstance diiynce static olduğu için sadece singletyon tek obje üsütndn donuyor
               DatabaseReference userReference = firebaseDatabase.getReference("users"); // tablo ya ulaştık (veriyi modellememk lazım)

                User user = new User(isimStr,soyisimStr,firmaAdiStr,epostaStr,sifreStr);
                userReference.child(isimStr).setValue(user);
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

                User user = dataSnapshot.getValue(User.class);Toast.makeText(MainActivity.this,"Kayıt Başarılı",Toast.LENGTH_SHORT).show();
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
