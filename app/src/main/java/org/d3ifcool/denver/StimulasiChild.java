package org.d3ifcool.denver;

import android.os.Parcel;
import android.os.Parcelable;

public class StimulasiChild implements Parcelable {
    private String tahapan;
    private String stimulasi;

    public StimulasiChild() {
    }

    public StimulasiChild(String tahapan, String stimulasi) {
        this.tahapan = tahapan;
        this.stimulasi = stimulasi;
    }

    protected StimulasiChild(Parcel in) {
        tahapan = in.readString();
        stimulasi = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(tahapan);
        parcel.writeString(stimulasi);
    }
}
