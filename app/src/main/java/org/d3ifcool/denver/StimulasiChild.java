package org.d3ifcool.denver;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

public class StimulasiChild implements Parcelable {
    private String tahapan;
    private String stimulasi;
    private String imageUrl;

    public StimulasiChild() {
    }

    public StimulasiChild(String tahapan, String stimulasi) {
        this.tahapan = tahapan;
        this.stimulasi = stimulasi;
    }

    public StimulasiChild(String tahapan, String stimulasi, String imageUrl) {
        this.tahapan = tahapan;
        this.stimulasi = stimulasi;
        this.imageUrl = imageUrl;
    }

    protected StimulasiChild(Parcel in) {
        tahapan = in.readString();
        stimulasi = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<StimulasiChild> CREATOR = new Creator<StimulasiChild>() {
        @Override
        public StimulasiChild createFromParcel(Parcel in) {
            return new StimulasiChild(in);
        }

        @Override
        public StimulasiChild[] newArray(int size) {
            return new StimulasiChild[size];
        }
    };

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tahapan);
        parcel.writeString(stimulasi);
        parcel.writeString(imageUrl);
    }
}
