package org.d3ifcool.denver;

public class Riwayat {
    String id, nama, tanggal, umur;
    int nilai;

    public Riwayat() {
    }

    public Riwayat(String id, String nama, String tanggal, String umur, int nilai) {
        this.id = id;
        this.nama = nama;
        this.tanggal = tanggal;
        this.umur = umur;
        this.nilai = nilai;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
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
