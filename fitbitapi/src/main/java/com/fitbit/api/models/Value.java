package com.fitbit.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lennon_desa on 2/21/2017.
 */

public class Value
{
    @SerializedName("restingHeartRate")
    @Expose
    private String restingHeartRate;
    @SerializedName("heartRateZones")
    @Expose
    private HeartRateZones[] heartRateZones;
    @SerializedName("customHeartRateZones")
    @Expose
    private String[] customHeartRateZones;

    public String getRestingHeartRate ()
    {
        return restingHeartRate;
    }

    public void setRestingHeartRate (String restingHeartRate)
    {
        this.restingHeartRate = restingHeartRate;
    }

    public HeartRateZones[] getHeartRateZones ()
    {
        return heartRateZones;
    }

    public void setHeartRateZones (HeartRateZones[] heartRateZones)
    {
        this.heartRateZones = heartRateZones;
    }

    public String[] getCustomHeartRateZones ()
    {
        return customHeartRateZones;
    }

    public void setCustomHeartRateZones (String[] customHeartRateZones)
    {
        this.customHeartRateZones = customHeartRateZones;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [restingHeartRate = "+restingHeartRate+", heartRateZones = "+heartRateZones+", customHeartRateZones = "+customHeartRateZones+"]";
    }
}
