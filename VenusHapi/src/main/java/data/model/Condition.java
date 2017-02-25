package data.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Condition implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("probability")
    @Expose
    private Double probability;

    protected Condition(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getProbability() {
        return probability;
    }

    public void setProbability(Double probability) {
        this.probability = probability;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(probability.toString());

    }
//    public static final Parcelable.Creator<Condition> CREATOR =
//            new Parcelable.Creator<Condition>() {
//                public Condition createFromParcel(Parcel in) {
//                    Condition category = new Category();
//                    category.category = in.readString();
//                    Bundle b = in.readBundle(Item.class.getClassLoader());
//                    Condition.items = b.getParcelableArrayList("items");
//
//                    return category;
//                }

    public static final Parcelable.Creator<Condition> CREATOR=new Creator<Condition>() {
        @Override
        public Condition createFromParcel(Parcel source) {
            Condition condition=new Condition(source);
            return condition;

        }

        @Override
        public Condition[] newArray(int size) {
            return new Condition[0];
        }
    };
}