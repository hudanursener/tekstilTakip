package com.example.tekstiltakip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class KayitOl extends AppCompatActivity {


    private EditText isim;
    private EditText soyisim;
    private EditText firmaAdi;
    private EditText eposta;
    private EditText sifre;
    private Button kayitButon;
    private ProgressDialog progressDialog1;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kayit_ol);


        kayitButon=(Button)findViewById(R.id.kayitButon);
        isim=(EditText) findViewById(R.id.isim);
        soyisim=(EditText) findViewById(R.id.soyISim);
        firmaAdi=(EditText) findViewById(R.id.firmaAdi);
        eposta=(EditText) findViewById(R.id.eposta);
        sifre=(EditText) findViewById(R.id.sifre);
        progressDialog1= new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();

        kayitButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String adi=isim.getText().toString();
                String soyadi=soyisim.getText().toString();
                String firmaAd=firmaAdi.getText().toString();
                String email=eposta.getText().toString();
                String sfre=sifre.getText().toString();

                if (!TextUtils.isEmpty(adi) || !TextUtils.isEmpty(soyadi)|| !TextUtils.isEmpty(firmaAd)|| !TextUtils.isEmpty(email)
                        ||!TextUtils.isEmpty(sfre)){
                        progressDialog1.setTitle("Kaydediliyor");
                        progressDialog1.setMessage("bekleyiniz");
                        progressDialog1.setCanceledOnTouchOutside(false);
                        progressDialog1.show();
                        register_user(adi,soyadi,firmaAd,email,sfre);
                }
            }
        });
    }
    private void register_user(final String adi,final String soyadi,final String firmaAd,final String email,final String sfre) {
        mAuth.createUserWithEmailAndPassword(email,sfre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressDialog1.dismiss();
                    Intent intent =new Intent(KayitOl.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    progressDialog1.dismiss();
                    Toast.makeText(getApplicationContext(),"hata"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
