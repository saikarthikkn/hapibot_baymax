package com.fitbit.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by lennon_desa on 2/21/2017.
 */

public class ActivitiesHeart
{
        @SerializedName("dateTime")
        @Expose
        private String dateTime;
        @SerializedName("value")
        @Expose
        private Value value;

        public String getDateTime ()
        {
                return dateTime;
        }

        public void setDateTime (String dateTime)
        {
                this.dateTime = dateTime;
        }

        public Value getValue ()
        {
                return value;
        }

        public void setValue (Value value)
        {
                this.value = value;
        }

        @Override
        public String toString()
        {
                return "ClassPojo [dateTime = "+dateTime+", value = "+value+"]";
        }
}
