package com.hapi.sdk.HapiBot.views;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.fitbit.api.models.ActivitiesHeart;
import com.fitbit.api.models.HeartRate;
import com.fitbit.api.models.Value;
import com.hapi.sdk.HapiBot.R;
import com.hapi.sdk.HapiBot.databinding.LayoutInfoBinding;

/**
 * Created by lennon_desa on 2/22/2017.
 */

public class UserHeartRateView extends LinearLayout {

    private LayoutInfoBinding binding;

    public UserHeartRateView(Context context) {
        super(context);
        init();
    }

    public UserHeartRateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UserHeartRateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init() {
        binding = DataBindingUtil.inflate(((Activity) getContext()).getLayoutInflater(), R.layout.layout_info, this, true);
        binding.setTitleText(R.string.heartrate_info);
        binding.setInfoText("Loading...");
    }

    public void bindHeartRateData(HeartRate heartrate) {
        StringBuilder stringBuilder = new StringBuilder();

        ActivitiesHeart[] ah = heartrate.getActivitiesheart();
        Value value;
        for (ActivitiesHeart actheart:ah)
        {
            value=actheart.getValue();
            stringBuilder.append("<b>&nbsp;&nbsp;Battery Level: </b>");
            stringBuilder.append(value.getRestingHeartRate());
            stringBuilder.append("<br><br>");
        }
        binding.setInfoText(Html.fromHtml(stringBuilder.toString()));
    }

}
