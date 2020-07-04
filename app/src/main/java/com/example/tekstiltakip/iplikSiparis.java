package com.example.tekstiltakip;

public class iplikSiparis {
    public iplikSiparis(String firmaAd, String miktar, String tur, String fiyati,String durumu) {
        this.firmaAd = firmaAd;
        this.miktar = miktar;
        this.tur = tur;
        this.fiyati = fiyati;
        this.durumu = durumu;
    }
    public iplikSiparis() {

    }

    public String getFirmaAd() {
        return firmaAd;
    }

    public void setFirmaAd(String firmaAd) {
        this.firmaAd = firmaAd;
    }

    public String getMiktar() {
        return miktar;
    }

    public void setMiktar(String miktar) {
        this.miktar = miktar;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public String getFiyati() {
        return fiyati;
    }

    public void setFiyati(String fiyati) {
        this.fiyati = fiyati;
    }


    public String getDurumu() {
        return durumu;
    }

    public void setDurumu(String durumu) {
        this.durumu = durumu;
    }

    String firmaAd;
    String miktar;
    String tur;
    String fiyati;
    String durumu;

}
