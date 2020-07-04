package com.example.tekstiltakip;

public class stoklar {


    String turu;
    String miktari;
    String fiyati;

    public stoklar(String turu, String miktari, String fiyati) {
        this.turu = turu;
        this.miktari = miktari;
        this.fiyati = fiyati;
    }
    public stoklar() {

    }
    public String getTuru() {
        return turu;
    }

    public void setTuru(String turu) {
        this.turu = turu;
    }

    public String getMiktari() {
        return miktari;
    }

    public void setMiktari(String miktari) {
        this.miktari = miktari;
    }

    public String getFiyati() {
        return fiyati;
    }

    public void setFiyati(String fiyati) {
        this.fiyati = fiyati;
    }


}
