package com.example.tekstiltakip;

public class siparisler {
    String orani;

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    String firma;

    public String getOrani() {
        return orani;
    }

    public void setOrani(String orani) {
        this.orani = orani;
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

    public String getSon_tarih() {
        return Son_tarih;
    }

    public void setSon_tarih(String son_tarih) {
        Son_tarih = son_tarih;
    }

    String miktari;
    String fiyati;
    String Son_tarih;

    public String getDurumu() {
        return durumu;
    }

    public void setDurumu(String durumu) {
        this.durumu = durumu;
    }

    String durumu;
    public siparisler(String orani, String miktari, String fiyati, String son_tarih, String firma,String durumu) {
        this.orani = orani;
        this.durumu = durumu;
        this.firma = firma;
        this.miktari = miktari;
        this.fiyati = fiyati;
        Son_tarih = son_tarih;

    }
    public siparisler() {

    }


}
