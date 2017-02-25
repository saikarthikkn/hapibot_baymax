package data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by alok_shankar on 2/25/2017.
 */

public class HeartRate {
    @SerializedName("values")
    @Expose
    private String values;
    @SerializedName("accuracy")
    @Expose
    private String accuracy;

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
}
