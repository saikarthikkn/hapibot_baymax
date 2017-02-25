package com.hapi.sdk.HapiBot.views;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.fitbit.api.models.User;
import com.google.gson.Gson;
import com.hapi.sdk.HapiBot.R;
import com.hapi.sdk.HapiBot.databinding.LayoutInfoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Created by jboggess on 10/3/16.
 */

public class ProfileInfoView extends LinearLayout {

    private LayoutInfoBinding binding;

    public ProfileInfoView(Context context) {
        super(context);
        init();
    }

    public ProfileInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProfileInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init() {
        binding = DataBindingUtil.inflate(((Activity) getContext()).getLayoutInflater(), R.layout.layout_info, this, true);
        binding.setTitleText(R.string.user_info);
        binding.setInfoText(getContext().getString(R.string.loading));
    }


    public void bindProfileInfo(User user) {
        StringBuilder stringBuilder = new StringBuilder();
        //Set user Info
        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(user));

            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String key = keys.next();
                Object value = jsonObject.get(key);
                if (!(value instanceof JSONObject)
                        && !(value instanceof JSONArray)) {
                    stringBuilder.append("<b>");
                    stringBuilder.append(key);
                    stringBuilder.append(":</b>&nbsp;");
                    stringBuilder.append(value.toString());
                    stringBuilder.append("<br>");
                }
            }

            binding.setInfoText(Html.fromHtml(stringBuilder.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
