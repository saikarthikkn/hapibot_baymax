package com.hapi.sdk.HapiBot;

import com.hapi.sdk.Stormpath;
import com.hapi.sdk.StormpathConfiguration;
import com.hapi.sdk.StormpathLogger;

import android.app.Application;

public class StormpathVirtualCannonApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            // we only want to show the logs in debug builds, for easier debugging
            Stormpath.setLogLevel(StormpathLogger.VERBOSE);
        }

        // initialize stormpath
        StormpathConfiguration stormpathConfiguration = new StormpathConfiguration.Builder()
                .baseUrl("https://virtual-cannon.apps.stormpath.io/")
                .build();
        Stormpath.init(this, stormpathConfiguration);
    }
}
