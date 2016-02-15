package com.stormpath.sdk;

import com.squareup.moshi.Moshi;
import com.stormpath.sdk.models.LoginResponse;
import com.stormpath.sdk.models.RegisterParams;
import com.stormpath.sdk.utils.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Dispatcher;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class ApiManager {

    public static final Pattern ACCESS_TOKEN_COOKIE_PATTERN = Pattern.compile("access_token=(.*?);.*");

    public static final Pattern REFRESH_TOKEN_COOKIE_PATTERN = Pattern.compile("refresh_token=(.*?);.*");

    private OkHttpClient okHttpClient;

    private Executor callbackExecutor;

    private StormpathConfiguration config;

    private PreferenceStore preferenceStore;

    private Moshi moshi = new Moshi.Builder().build();

    ApiManager(StormpathConfiguration config, Platform platform) {
        this.config = config;
        this.preferenceStore = platform.preferenceStore();
        this.callbackExecutor = platform.callbackExecutor();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Stormpath.logger().d(message);
            }
        });
        this.okHttpClient = new OkHttpClient.Builder()
                .dispatcher(new Dispatcher(platform.httpExecutorService()))
                .addNetworkInterceptor(httpLoggingInterceptor)
                .build();
    }

    void login(String username, String password, StormpathCallback<Void> callback) {
        RequestBody formBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .add("grant_type", "password")
                .build();

        Request request = new Request.Builder()
                .url(config.oauthUrl())
                .post(formBody)
                .build();

        okHttpClient.newCall(request).enqueue(new OkHttpCallback<Void>(callback) {
            @Override
            protected void onSuccess(Response response, StormpathCallback<Void> callback) {
                try {
                    LoginResponse loginResponse = moshi.adapter(LoginResponse.class).fromJson(response.body().source());
                    if (StringUtils.isBlank(loginResponse.getAccessToken())) {
                        failureCallback(new RuntimeException("access_token was not found in response. See debug logs for details."));
                        return;
                    }

                    if (StringUtils.isBlank(loginResponse.getRefreshToken())) {
                        Stormpath.logger().e("There was no refresh_token in the login response!");
                    }

                    preferenceStore.setAccessToken(loginResponse.getAccessToken());
                    preferenceStore.setRefreshToken(loginResponse.getRefreshToken());
                    successCallback(null);
                } catch (Throwable t) {
                    failureCallback(t);
                }
            }
        });
    }

    public void register(RegisterParams registerParams, StormpathCallback<Void> callback) {
        RequestBody body = RequestBody
                .create(MediaType.parse("application/json"), moshi.adapter(RegisterParams.class).toJson(registerParams));

        Request request = new Request.Builder()
                .url(config.registerUrl())
                .post(body)
                .build();

        okHttpClient.newCall(request).enqueue(new OkHttpCallback<Void>(callback) {
            @Override
            protected void onSuccess(Response response, StormpathCallback<Void> callback) {
                try {
                    List<String> cookies = response.headers("Set-Cookie");

                    String accessToken = null;
                    String refreshToken = null;

                    for (String cookieHeader : cookies) {
                        Matcher accessTokenMatcher = ACCESS_TOKEN_COOKIE_PATTERN.matcher(cookieHeader);
                        if (accessTokenMatcher.find()) {
                            accessToken = accessTokenMatcher.group(1);
                        }

                        Matcher refreshTokenMatcher = REFRESH_TOKEN_COOKIE_PATTERN.matcher(cookieHeader);
                        if (refreshTokenMatcher.find()) {
                            refreshToken = refreshTokenMatcher.group(1);
                        }
                    }

                    if (StringUtils.isNotBlank(accessToken)) {
                        preferenceStore.setAccessToken(accessToken);

                        if (StringUtils.isNotBlank(refreshToken)) {
                            preferenceStore.setRefreshToken(refreshToken);
                        }
                    } else {
                        Stormpath.logger().i("There was no access_token in the register cookies, if you want to skip the login after "
                                + "registration, enable the autologin in your Express app.");
                    }

                    successCallback(null);
                } catch (Throwable t) {
                    failureCallback(t);
                }
            }
        });
    }

    public void refreshAccessToken(final StormpathCallback<Void> callback) {
        String refreshToken = preferenceStore.getRefreshToken();

        if (StringUtils.isBlank(refreshToken)) {
            callbackExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    callback.onFailure(
                            new IllegalStateException("refresh_token was not found, did you forget to login? See debug logs for details."));
                }
            });
            return;
        }

        RequestBody formBody = new FormBody.Builder()
                .add("refresh_token", refreshToken)
                .add("grant_type", "refresh_token")
                .build();

        Request request = new Request.Builder()
                .url(config.oauthUrl())
                .post(formBody)
                .build();

        okHttpClient.newCall(request).enqueue(new OkHttpCallback<Void>(callback) {
            @Override
            protected void onSuccess(Response response, StormpathCallback<Void> callback) {
                try {
                    LoginResponse loginResponse = moshi.adapter(LoginResponse.class).fromJson(response.body().source());
                    if (StringUtils.isBlank(loginResponse.getAccessToken())) {
                        failureCallback(new RuntimeException("access_token was not found in response. See debug logs for details."));
                        return;
                    }

                    if (StringUtils.isBlank(loginResponse.getRefreshToken())) {
                        Stormpath.logger().e("There was no refresh_token in the login response!");
                    }

                    preferenceStore.setAccessToken(loginResponse.getAccessToken());
                    preferenceStore.setRefreshToken(loginResponse.getRefreshToken());
                    successCallback(null);
                } catch (Throwable t) {
                    failureCallback(t);
                }
            }
        });
    }

    public void getUserProfile(StormpathCallback<Map<String, String>> callback) {
        // TODO
    }

    public void resetPassword(String email, StormpathCallback<Void> callback) {
        // TODO
    }

    public void logout(StormpathCallback<Void> callback) {
        // TODO
    }

    private abstract class OkHttpCallback<T> implements Callback {

        private StormpathCallback<T> stormpathCallback;

        public OkHttpCallback(StormpathCallback<T> stormpathCallback) {
            this.stormpathCallback = stormpathCallback;
        }

        @Override
        public void onFailure(Call call, IOException e) {
            failureCallback(e);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if (response.isSuccessful()) {
                onSuccess(response, stormpathCallback);
            } else {
                failureCallback(new RuntimeException(
                        String.format("Call to %s failed with http status: %s %s. See debug logs for details.", response.request().url(),
                                response.code(), response.message())));
            }
        }

        protected abstract void onSuccess(Response response, StormpathCallback<T> callback);

        void successCallback(final T t) {
            callbackExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    stormpathCallback.onSuccess(t);
                }
            });
        }

        void failureCallback(final Throwable t) {
            callbackExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    stormpathCallback.onFailure(t);
                }
            });
        }
    }
}
