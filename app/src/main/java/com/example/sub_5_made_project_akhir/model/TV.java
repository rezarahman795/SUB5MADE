package com.example.sub_5_made_project_akhir.model;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Data
public class TV implements Parcelable {

    private String serialNameTV,tglSerial,descSerial,backdropPictTV,pictureTV,ID_TV,type;
    private double rateTV;

    public TV(JSONObject objTV){
        try {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd, mm, yyyy");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            String idMovieTV = objTV.getString("id");
            String titleTV = objTV.getString("original_name");
            String toDateTV = objTV.getString("first_air_date");
            double ratingTV = objTV.getDouble("vote_average");
            String detailDescTV = objTV.getString("overview");
            String posterTV = objTV.getString("poster_path");
            String backDropTV = objTV.getString("backdrop_path");

            this.ID_TV = idMovieTV;
            this.serialNameTV = titleTV;
            this.tglSerial = toDateTV;
            this.descSerial = detailDescTV;
            this.pictureTV = posterTV;
            this.backdropPictTV = backDropTV;
            this.rateTV = ratingTV;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TV() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ID_TV);
        dest.writeString(pictureTV);
        dest.writeString(backdropPictTV);
        dest.writeString(serialNameTV);
        dest.writeString(tglSerial);
        dest.writeString(descSerial);
        dest.writeDouble(rateTV);
    }
    protected TV(Parcel in) {
        ID_TV = in.readString();
        pictureTV = in.readString();
        backdropPictTV = in.readString();
        serialNameTV = in.readString();
        tglSerial = in.readString();
        descSerial = in.readString();
        rateTV = in.readDouble();
    }

    public static final Creator<TV> CREATOR = new Creator<TV>() {
        @Override
        public TV createFromParcel(Parcel in) {
            return new TV(in);
        }

        @Override
        public TV[] newArray(int size) {
            return new TV[size];
        }
    };
}
