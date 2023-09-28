package com.example.saglik;

public class Doctor {
    String tc;
    String ad;
    String soyad;
    String email;

    public Doctor(String tc, String ad, String soyad, String email) {
        this.tc = tc;
        this.ad = ad;
        this.soyad = soyad;
        this.email = email;
    }

    public Doctor(){}

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
}
