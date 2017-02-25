package com.hapi.sdk.HapiBot.views;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.fitbit.api.models.Device;
import com.hapi.sdk.HapiBot.R;
import com.hapi.sdk.HapiBot.databinding.LayoutInfoBinding;

/**
 * Created by jboggess on 10/3/16.
 */

public class DeviceView extends LinearLayout {


    private LayoutInfoBinding binding;

    public DeviceView(Context context) {
        super(context);
        init();
    }

    public DeviceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DeviceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init() {
        binding = DataBindingUtil.inflate(((Activity) getContext()).getLayoutInflater(), R.layout.layout_info, this, true);
        binding.setTitleText(R.string.devices);
        binding.setInfoText(getContext().getString(R.string.loading));
    }

    public void bindDevices(Device[] devices) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Device device : devices) {
            stringBuilder.append("<b>FITBIT ");
            stringBuilder.append(device.getDeviceVersion().toUpperCase());
            stringBuilder.append("&trade;</b><br>");

            stringBuilder.append("<b>&nbsp;&nbsp;Type: </b>");
            stringBuilder.append(device.getType().toLowerCase());
            stringBuilder.append("<br>");

            stringBuilder.append("<b>&nbsp;&nbsp;Last Sync: </b>");
            stringBuilder.append(device.getLastSyncTime());
            stringBuilder.append("<br>");

            stringBuilder.append("<b>&nbsp;&nbsp;Battery Level: </b>");
            stringBuilder.append(device.getBattery());
            stringBuilder.append("<br><br>");
        }
        stringBuilder.replace(stringBuilder.length(), stringBuilder.length(), " ");

        binding.setInfoText(Html.fromHtml(stringBuilder.toString()));
    }

}
