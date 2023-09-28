package com.example.saglik;

public class Favori {
    String tc;
    String ad;
    String soyad;
    String email;
    String point;

    public Favori(String tc, String ad, String soyad, String email, String point) {
        this.tc = tc;
        this.ad = ad;
        this.soyad = soyad;
        this.email = email;
        this.point = point;
    }

    public Favori(){}

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }
}
