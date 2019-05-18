package org.d3ifcool.denver;

public class Report {
    String rPertanyaan;
    int rJawaban;

    public Report() {
    }

    public Report(String rPertanyaan, int rJawaban) {
        this.rPertanyaan = rPertanyaan;
        this.rJawaban = rJawaban;
    }

    public String getrPertanyaan() {
        return rPertanyaan;
    }

    public int getrJawaban() {
        return rJawaban;
    }
}
