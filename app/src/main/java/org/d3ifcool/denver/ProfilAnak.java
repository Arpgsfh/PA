package org.d3ifcool.denver;

public class ProfilAnak {
    String id, nama;
    Umur tglLahir;

    public ProfilAnak() {
    }

    public ProfilAnak(String id, String nama, Umur tglLahir) {
        this.id = id;
        this.nama = nama;
        this.tglLahir = tglLahir;
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public Umur getTglLahir() {
        return tglLahir;
    }
}
