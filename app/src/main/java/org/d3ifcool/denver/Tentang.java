package org.d3ifcool.denver;

public class Tentang {
    String pendahuluan, alatInstrumen, caraMenggunakan, interpretasi, intervensi;

    public Tentang() {
    }

    public Tentang(String pendahuluan, String alatInstrumen, String caraMenggunakan, String interpretasi, String intervensi) {
        this.pendahuluan = pendahuluan;
        this.alatInstrumen = alatInstrumen;
        this.caraMenggunakan = caraMenggunakan;
        this.interpretasi = interpretasi;
        this.intervensi = intervensi;
    }

    public String getPendahuluan() {
        return pendahuluan;
    }

    public String getAlatInstrumen() {
        return alatInstrumen;
    }

    public String getCaraMenggunakan() {
        return caraMenggunakan;
    }

    public String getInterpretasi() {
        return interpretasi;
    }

    public String getIntervensi() {
        return intervensi;
    }
}
