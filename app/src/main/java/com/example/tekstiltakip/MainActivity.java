package com.example.tekstiltakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText email;
    private EditText sifre;
    private RadioButton hatirla;
    private Button girisyap;
    private Button kayit;
    private Button sifreUnuttum;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        kayit = findViewById(R.id.KayıtOl);

        kayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,KayitOl.class);
                startActivity(intent);
            }
        });

        sifreUnuttum = findViewById(R.id.sifremiUnuttum);

        sifreUnuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SifremiUnuttum.class);
                startActivity(intent);
            }
        });

        email = findViewById(R.id.eposta);
        sifre = findViewById(R.id.sifre);
        hatirla = findViewById(R.id.beniHatirla);
        mAuth = FirebaseAuth.getInstance();
        girisyap = findViewById(R.id.giris);


        girisyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eposta=email.getText().toString();
                String sfre=sifre.getText().toString();
                if (!TextUtils.isEmpty(eposta)||!TextUtils.isEmpty(sfre)){
                    girisYap(eposta,sfre);
                }
            }
        });
    }
    private void girisYap(String s_ad, String s_sif) {
        mAuth.signInWithEmailAndPassword(s_ad,s_sif).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent intent =new Intent(MainActivity.this,AnaSayfa.class);
                    startActivity(intent);
                }
            }
        });
    }


}
