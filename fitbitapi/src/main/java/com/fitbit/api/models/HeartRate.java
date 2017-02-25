package com.fitbit.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lennon_desa on 2/21/2017.
 */

public class HeartRate
{
    @SerializedName("activities-heart")
    @Expose
    private ActivitiesHeart[] activitiesheart;

    public ActivitiesHeart[] getActivitiesheart ()
    {
        return activitiesheart;
    }

    public void setActivitiesheart (ActivitiesHeart[] activitiesheart)
    {
        this.activitiesheart = activitiesheart;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [activities-heart = "+activitiesheart+"]";
    }
}
