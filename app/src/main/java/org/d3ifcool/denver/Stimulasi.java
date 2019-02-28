package org.d3ifcool.denver;

public class Stimulasi {
    String tahapan, stimulasi;

    public Stimulasi() {
    }

    public Stimulasi(String tahapan, String stimulasi) {
        this.tahapan = tahapan;
        this.stimulasi = stimulasi;
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
