package org.d3ifcool.denver;

public class Stimulasi {
    String jenis, tahapan, stimulasi;

    public Stimulasi() {
    }

    public Stimulasi(String jenis, String tahapan, String stimulasi) {
        this.jenis = jenis;
        this.tahapan = tahapan;
        this.stimulasi = stimulasi;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getTahapan() {
        return tahapan;
    }

    public void setTahapan(String tahapan) {
        this.tahapan = tahapan;
    }

    public String getStimulasi() {
        return stimulasi;
    }

    public void setStimulasi(String stimulasi) {
        this.stimulasi = stimulasi;
    }
}
