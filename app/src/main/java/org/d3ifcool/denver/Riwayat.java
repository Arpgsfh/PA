package org.d3ifcool.denver;

public class Riwayat {
    String umur, nilai, tanggal;

    public Riwayat() {
    }

    public Riwayat(String umur, String nilai) {
        this.umur = umur;
        this.nilai = nilai;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }
}
