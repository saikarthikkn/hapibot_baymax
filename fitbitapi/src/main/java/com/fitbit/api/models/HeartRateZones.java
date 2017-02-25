package com.fitbit.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lennon_desa on 2/21/2017.
 */


public class HeartRateZones
{
    @SerializedName("min")
    @Expose
    private String min;
    @SerializedName("minutes")
    @Expose
    private String minutes;
    @SerializedName("max")
    @Expose
    private String max;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("caloriesOut")
    @Expose
    private String caloriesOut;

    public String getMin ()
    {
        return min;
    }

    public void setMin (String min)
    {
        this.min = min;
    }

    public String getMinutes ()
    {
        return minutes;
    }

    public void setMinutes (String minutes)
    {
        this.minutes = minutes;
    }

    public String getMax ()
    {
        return max;
    }

    public void setMax (String max)
    {
        this.max = max;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCaloriesOut ()
    {
        return caloriesOut;
    }

    public void setCaloriesOut (String caloriesOut)
    {
        this.caloriesOut = caloriesOut;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [min = "+min+", minutes = "+minutes+", max = "+max+", name = "+name+", caloriesOut = "+caloriesOut+"]";
    }
}
