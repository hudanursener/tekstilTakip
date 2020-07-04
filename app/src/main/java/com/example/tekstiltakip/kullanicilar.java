package com.example.tekstiltakip;

public class kullanicilar {
    String ad;
    String soyadi;
    String firmaAd;
    String email;
    String sifre;
    String kTuru;

    public kullanicilar(String ad, String soyadi, String firmaAd, String email, String sifre, String kTuru) {
        this.ad = ad;
        this.soyadi = soyadi;
        this.firmaAd = firmaAd;
        this.email = email;
        this.sifre = sifre;
        this.kTuru = kTuru;
    }
    public kullanicilar() {

    }



    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyadi() {
        return soyadi;
    }

    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }

    public String getFirmaAd() {
        return firmaAd;
    }

    public void setFirmaAd(String firmaAd) {
        this.firmaAd = firmaAd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getkTuru() {
        return kTuru;
    }

    public void setkTuru(String kTuru) {
        this.kTuru = kTuru;
    }


}
