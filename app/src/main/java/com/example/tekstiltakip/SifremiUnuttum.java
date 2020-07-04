package com.example.tekstiltakip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class SifremiUnuttum extends AppCompatActivity {

    Button sifirla;
    EditText eposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_unuttum);

        sifirla = findViewById(R.id.sifirla);
        eposta = findViewById(R.id.epostaSifreUnuttum);
        final String eMail= eposta.getText().toString();
       final FirebaseAuth mAuth=FirebaseAuth.getInstance();;
        sifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.sendPasswordResetEmail(eMail);

            }
        });
    }
}
