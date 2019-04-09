package org.d3ifcool.denver;

public class Test {
    String pertanyaan, imageUrl;

    public Test() {
    }

    public Test(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }

    public Test(String pertanyaan, String imageUrl) {
        this.pertanyaan = pertanyaan;
        this.imageUrl = imageUrl;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
