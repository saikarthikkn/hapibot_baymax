```

Then, just invoke the register method on `Stormpath` class:

```java
Stormpath.register(registrationData, new StormpathCallback<Void>() {
    @Override
    public void onSuccess(Void aVoid) {
        // Handle successful registration
    }

    @Override
    public void onFailure(StormpathError error) {
        // Handle registration error
    }
});
```

## Logging in

To log in, collect the email (or username) and password from the user, and then pass them to the login method:

```java
Stormpath.login("user@example.com", "Pa55w0rd", new StormpathCallback<Void>() {
    @Override
    public void onSuccess(Void aVoid) {
        // Handle successful login
    }

    @Override
    public void onFailure(StormpathError error) {
        // Handle login error
    }
});
```

After login succeeds, the accessToken will be saved by the SDK. You can then get it by calling `Stormpath.accessToken()`.

## Logging in with Social Providers

Stormpath also supports logging in with a variety of social providers Facebook, Google, LinkedIn, GitHub, and more. There are two flows for enabling this:

1. Let Stormpath handle the social login.
2. Use the social provider's iOS SDK to get an access token, and pass it to Stormpath to log in.

We've made it extremely easy to set up social login without using the social provider SDKs, but if you need to use their SDKs for more features besides logging in, you should use flow #2 (and skip directly to [Using a social provider SDK](#using-a-social-provider-sdk)). 

### Configure Your Social Directory in Stormpath

To set up your social directory, read more about [social login in the Stormpath Client API Guide](https://docs.stormpath.com/client-api/product-guide/latest/social_login.html#before-you-start).


### Setting up your Android project

In your `AndroidManifest.xml`, you'll need to add Stormpath's login handler activity and configure it with an intent filter to recieve login callbacks from Stormpath. 

Add this to your manifest:

```xml
<activity android:name="com.hapi.sdk.CustomTabActivity"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:scheme="REPLACE-ME-HERE" />
    </intent-filter>
</activity>
```

For the `data android:scheme` tag, type in your Client API's DNS label, but reversed. For instance, if your Client API DNS Label is `edjiang.apps.stormpath.io`, type in `io.stormpath.apps.edjiang`. 

In the [Stormpath Admin Console](https://api.stormpath.com)'s Application settings, add that URL as an "authorized callback URL", appending `://stormpathCallback`. Following my earlier example, I would use `io.stormpath.apps.edjiang`. 

### Initiating Social Login

Now, you can initiate the login screen by calling: 

```java
Stormpath.loginWithProvider(Provider.FACEBOOK, this, new StormpathCallback<Void>() {
    @Override
    public void onSuccess(Void aVoid) {
        // Handle successful login
    }

    @Override
    public void onFailure(StormpathError error) {
        // Handle login error
    }
});
```

Valid `Provider` enum values are: `FACEBOOK, GOOGLE, LINKEDIN, GITHUB, TWITTER`, or you can enter a string. 

### Using the Google or Facebook SDK

If you're using the Facebook SDK or Google SDK for your app, follow their setup instructions instead. Once you successfully sign in with their SDK, utilize the following methods to send your access token to Stormpath, and log in your user:

```java
Stormpath.loginWithProvider(Provider.FACEBOOK, accessToken, new StormpathCallback<Void>() {
    @Override
    public void onSuccess(Void aVoid) {
        // Handle successful login
    }

    @Override
    public void onFailure(StormpathError error) {
        // Handle login error
    }
});
```

## User data

You can fetch the user data for the currently logged in user:

```java
Stormpath.getAccount(new StormpathCallback<Account>() {
    @Override
    public void onSuccess(Account account) {
        // Do things with the account
    }

    @Override
    public void onFailure(StormpathError error) {
        // Account request failed
    }
});
```

## Using the Access Token

You can utilize the access token to access any of your API endpoints that require authentication. It's stored as a property on the Stormpath object as `Stormpath.getAccessToken()`. You can use the access token by adding it to your `Authorization` header using the `Bearer scheme`. This looks like the following:

`Authorization: Bearer ACCESSTOKEN`

When the access token expires, you may need to refresh it. Expiration times are configurable in the Stormpath application settings. 

```java
Stormpath.refreshAccessToken(new StormpathCallback<Void>() {
    @Override
    public void onSuccess(Void aVoid) {
        // success, your new accessToken has been saved
    }

    @Override
    public void onFailure(StormpathError error) {
        // something went wrong - the user will have to log in again
    }
});
```

## Logout

You can also log the current user out:

```java
Stormpath.logout();
```

This will clear the saved accessToken and invalidate the token with the server.

## Reset password

To reset a password for a user, use their email address to call `Stormpath.resetPassword()`:

```java
Stormpath.resetPassword("user@example.com", new StormpathCallback<Void>() {
    @Override
    public void onSuccess(Void aVoid) {
        // success!
    }

    @Override
    public void onFailure(StormpathError error) {
        // something went wrong
    }
});
```

## Resend verification email

You can resend a verification email if the email verification flow is enabled:

```java
Stormpath.resendVerificationEmail("user@example.com", new StormpathCallback<Void>() {
    @Override
    public void onSuccess(Void aVoid) {
        // success!
    }

    @Override
    public void onFailure(StormpathError error) {
        // something went wrong
    }
});
```

## Verify email

If you need to verify the user via the API, you can do so using the `sptoken` from the verification email:

```java
Stormpath.verifyEmail(sptoken, new StormpathCallback<Void>() {
    @Override
    public void onSuccess(Void aVoid) {
        // success!
    }

    @Override
    public void onFailure(StormpathError error) {
        // something went wrong
    }
});
```

# License

This project is open source and uses the Apache 2.0 License. See [LICENSE file](LICENSE) for details.
