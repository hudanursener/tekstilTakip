package com.example.tekstiltakip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SifremiUnuttum extends AppCompatActivity {

    Button onaykodu;
    EditText eposta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sifremi_unuttum);

        onaykodu = findViewById(R.id.onayKoduButon);
        eposta = findViewById(R.id.epostaSifreUnuttum);

        onaykodu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SifremiUnuttum.this,SifreSifirla.class);
                startActivity(intent);
            }
        });
    }
}
