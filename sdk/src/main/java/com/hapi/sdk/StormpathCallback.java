package com.hapi.sdk;

import com.hapi.sdk.models.StormpathError;

public interface StormpathCallback<T> {

    void onSuccess(T t);

    void onFailure(StormpathError error);
}
