package org.d3ifcool.denver;

public class ProfilAnak {
    String id, nama, tglLahir;

    public ProfilAnak() {
    }

    public ProfilAnak(String id, String nama, String tglLahir) {
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

    public String getTglLahir() {
        return tglLahir;
    }
}
