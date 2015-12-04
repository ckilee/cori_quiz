package frolic.br.coriquiz.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ckilee on 03/12/15.
 */
public class RankingItem implements Parcelable{
    private String name;
    private String score;

    public RankingItem(String name, String score) {
        this.name = name;
        this.score = score;
    }

    public RankingItem(Parcel in) {
        this.name = in.readString();
        this.score = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return name+"  -  "+score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(score);
    }

    public static final Parcelable.Creator<RankingItem> CREATOR
            = new Parcelable.Creator<RankingItem>() {
        public RankingItem createFromParcel(Parcel in) {
            return new RankingItem(in);
        }

        public RankingItem[] newArray(int size) {
            return new RankingItem[size];
        }
    };


}
