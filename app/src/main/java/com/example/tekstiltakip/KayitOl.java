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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class KayitOl extends AppCompatActivity {


    private EditText isim;
    private EditText soyisim;
    private EditText firmaAdi;
    private EditText eposta;
    private EditText sifre;
    private Spinner kullaniciTuru;
    private Button kayitButon;
    private ProgressDialog progressDialog1;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


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
        kullaniciTuru=(Spinner) findViewById(R.id.kullaniciTuru);
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
                String kTuru=kullaniciTuru.getSelectedItem().toString();

                if (!TextUtils.isEmpty(adi) || !TextUtils.isEmpty(soyadi)|| !TextUtils.isEmpty(firmaAd)|| !TextUtils.isEmpty(email)
                        ||!TextUtils.isEmpty(sfre) ||!TextUtils.isEmpty(kTuru)){
                        progressDialog1.setTitle("Kaydediliyor");
                        progressDialog1.setMessage("bekleyiniz");
                        progressDialog1.setCanceledOnTouchOutside(false);
                        progressDialog1.show();
                        register_user(adi,soyadi,firmaAd,email,sfre,kTuru);
                }
            }
        });
    }
    private void register_user(final String adi,final String soyadi,final String firmaAd,final String email,final String sfre,final String kTuru) {
        mAuth.createUserWithEmailAndPassword(email,sfre).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    final String kullaniciId =mAuth.getCurrentUser().getUid();
                    mDatabase= FirebaseDatabase.getInstance().getReference().child("Kullanicilar").child(kullaniciId);
                    HashMap<String,String> kullaniciHM = new HashMap<>();
                    kullaniciHM.put("ad",adi);
                    kullaniciHM.put("soyadi",soyadi);
                    kullaniciHM.put("firmaAd",firmaAd);
                    kullaniciHM.put("email",email);
                    kullaniciHM.put("sifre",sfre);
                    kullaniciHM.put("kTuru",kTuru);
                    mDatabase.setValue(kullaniciHM).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                progressDialog1.dismiss();
                                Intent intent =new Intent(KayitOl.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"hata"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                            }
                        }
                    });
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
