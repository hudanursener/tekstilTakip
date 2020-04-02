package com.example.a27_12_19;

import androidx.appcompat.app.AppCompatActivity;

public class User extends AppCompatActivity {

    public String isim;
    public String soyisim;
    public String firmaAdi;
    public String eposta;
    public String sifre;


    public User(){

    }
    public User(String Isim,String Soyisim,String FirmaAdi,String Eposta ,String Sifre){
        this.isim= Isim;
        this.soyisim = Soyisim;
        this.firmaAdi= FirmaAdi;
        this.eposta = Eposta;
        this.sifre= Sifre;
    }
}
