package com.example.tekstiltakip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SifreSifirla extends AppCompatActivity {

    EditText sifre;
    EditText sifretkr;
    Button sifirla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifre_sifirla);

        sifre = findViewById(R.id.yeniSifre);
        sifretkr = findViewById(R.id.yeniSifreOnay);
        sifirla = findViewById(R.id.sifreYenileButon);

        sifirla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SifreSifirla.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
