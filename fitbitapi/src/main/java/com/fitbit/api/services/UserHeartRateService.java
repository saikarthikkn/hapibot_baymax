package com.fitbit.api.services;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.fitbit.api.APIUtils;
import com.fitbit.api.MissingScopesException;
import com.fitbit.api.ResourceLoadedHandler;
import com.fitbit.api.ResourceLoader;
import com.fitbit.api.TokenExpiredException;
import com.fitbit.api.models.ActivitiesHeart;
import com.fitbit.api.models.HeartRate;
import com.fitbit.authentication.AuthenticationManager;
import com.fitbit.authentication.Scope;

/**
 * Created by lennon_desa on 2/21/2017.
 */

public class UserHeartRateService {
    private final static String HEARTRATE_URL = "https://api.fitbit.com/1/user/-/activities/heart/date/today/1d.json";
    private static final ResourceLoader<HeartRate> USER_HEARTRATE_LOADER=new ResourceLoader<>(HEARTRATE_URL,HeartRate.class);

   /* public interface HeartRateHandler{
        void onHeartRateLoaded(ActivitiesHeart[] activitiesheart);

        void onErrorLoadingHeartRate(String errorMessage);
    }*/

    public static void getLoggedInUserHeartRateDetails(Activity activityContext, @NonNull final ResourceLoadedHandler<HeartRate> heartrateLoadedHandler) throws MissingScopesException, TokenExpiredException {
        APIUtils.validateToken(activityContext, AuthenticationManager.getCurrentAccessToken(), Scope.heartrate);
        USER_HEARTRATE_LOADER.loadResource(activityContext,heartrateLoadedHandler);
        /*USER_HEARTRATE_LOADER.loadResource(activityContext, new ResourceLoadedHandler<HeartRate>() {
            @Override
            public void onResourceLoaded(HeartRate resource) {
                heartrateHandler.onHeartRateLoaded(resource.getActivitiesheart());
            }

            @Override
            public void onResourceLoadError(String errorMessage) {
                heartrateHandler.onErrorLoadingHeartRate(errorMessage);
            }
        });*/
    }

}
