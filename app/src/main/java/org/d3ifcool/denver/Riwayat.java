package org.d3ifcool.denver;

public class Riwayat {
    String tanggal, umur;
    int nilai;

    public Riwayat() {
    }

    public Riwayat(String tanggal, String umur, int nilai) {
        this.tanggal = tanggal;
        this.umur = umur;
        this.nilai = nilai;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public int getNilai() {
        return nilai;
    }

    public void setNilai(int nilai) {
        this.nilai = nilai;
    }
}
