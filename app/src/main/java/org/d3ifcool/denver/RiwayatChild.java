package org.d3ifcool.denver;

import android.os.Parcel;
import android.os.Parcelable;

public class RiwayatChild implements Parcelable {
    String id, nama, tanggal, ket;
    int nilai;
    int nKasar, nHalus, nBicara, nSosialisasi;
    int jKasar, jHalus, jBicara, jSosialisasi;

    public RiwayatChild() {
    }

    public RiwayatChild(String id, String nama, String tanggal, String ket, int nilai, int nKasar, int nHalus, int nBicara, int nSosialisasi, int jKasar, int jHalus, int jBicara, int jSosialisasi) {
        this.id = id;
        this.nama = nama;
        this.tanggal = tanggal;
        this.ket = ket;
        this.nilai = nilai;
        this.nKasar = nKasar;
        this.nHalus = nHalus;
        this.nBicara = nBicara;
        this.nSosialisasi = nSosialisasi;
        this.jKasar = jKasar;
        this.jHalus = jHalus;
        this.jBicara = jBicara;
        this.jSosialisasi = jSosialisasi;
    }

    protected RiwayatChild(Parcel in) {
        nama = in.readString();
        tanggal = in.readString();
        ket = in.readString();
        nilai = in.readInt();
        nKasar = in.readInt();
        nHalus = in.readInt();
        nBicara = in.readInt();
        nSosialisasi = in.readInt();
        jKasar = in.readInt();
        jHalus = in.readInt();
        jBicara = in.readInt();
        jSosialisasi = in.readInt();
    }

    public static final Creator<RiwayatChild> CREATOR = new Creator<RiwayatChild>() {
        @Override
        public RiwayatChild createFromParcel(Parcel in) {
            return new RiwayatChild(in);
        }

        @Override
        public RiwayatChild[] newArray(int size) {
            return new RiwayatChild[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public int getNilai() {
        return nilai;
    }

    public void setNilai(int nilai) {
        this.nilai = nilai;
    }

    public int getnKasar() {
        return nKasar;
    }

    public void setnKasar(int nKasar) {
        this.nKasar = nKasar;
    }

    public int getnHalus() {
        return nHalus;
    }

    public void setnHalus(int nHalus) {
        this.nHalus = nHalus;
    }

    public int getnBicara() {
        return nBicara;
    }

    public void setnBicara(int nBicara) {
        this.nBicara = nBicara;
    }

    public int getnSosialisasi() {
        return nSosialisasi;
    }

    public void setnSosialisasi(int nSosialisasi) {
        this.nSosialisasi = nSosialisasi;
    }

    public int getjKasar() {
        return jKasar;
    }

    public void setjKasar(int jKasar) {
        this.jKasar = jKasar;
    }

    public int getjHalus() {
        return jHalus;
    }

    public void setjHalus(int jHalus) {
        this.jHalus = jHalus;
    }

    public int getjBicara() {
        return jBicara;
    }

    public void setjBicara(int jBicara) {
        this.jBicara = jBicara;
    }

    public int getjSosialisasi() {
        return jSosialisasi;
    }

    public void setjSosialisasi(int jSosialisasi) {
        this.jSosialisasi = jSosialisasi;
    }

    @Override
    public int describeContents() {
        return 0;
    }



    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(nama);
        parcel.writeString(tanggal);
        parcel.writeInt(nilai);
        parcel.writeInt(nKasar);
        parcel.writeInt(nHalus);
        parcel.writeInt(nBicara);
        parcel.writeInt(nSosialisasi);
        parcel.writeInt(jKasar);
        parcel.writeInt(jHalus);
        parcel.writeInt(jBicara);
        parcel.writeInt(jSosialisasi);
    }
}
