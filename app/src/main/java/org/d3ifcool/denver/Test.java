package org.d3ifcool.denver;

public class Test {
    int kategori;
    String pertanyaan, imageUrl;

    public Test() {
    }

    public Test(int kategori, String pertanyaan) {
        this.kategori = kategori;
        this.pertanyaan = pertanyaan;
    }

    public Test(int kategori, String pertanyaan, String imageUrl) {
        this.kategori = kategori;
        this.pertanyaan = pertanyaan;
        this.imageUrl = imageUrl;
    }

    public int getKategori() {
        return kategori;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
