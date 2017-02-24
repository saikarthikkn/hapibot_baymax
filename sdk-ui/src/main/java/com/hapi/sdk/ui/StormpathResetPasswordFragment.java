package com.hapi.sdk.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.hapi.sdk.Stormpath;
import com.hapi.sdk.StormpathCallback;
import com.hapi.sdk.models.StormpathError;

public class StormpathResetPasswordFragment extends BaseFragment {



    private StormpathResetPasswordFragmentListener mListener;

    public StormpathResetPasswordFragment() {
        // Required empty public constructor
    }


    public static StormpathResetPasswordFragment newInstance(Bundle args) {
        StormpathResetPasswordFragment fragment = new StormpathResetPasswordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.stormpath_fragment_resetpassword, container, false);

        loginConfig = StormpathLoginConfig.fromBundle(getArguments());

        emailEditText = (EditText) view.findViewById(R.id.stormpath_input_username);
        emailEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @TargetApi(8)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                //do email validation
                if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {

                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches()) {

                        emailEditText.getBackground().clearColorFilter();

                    }
                    else{
                        //set underline color
                        emailEditText.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Bundle localBundle = getArguments();
        String username = null;
        if(localBundle!=null)
             username = localBundle.getString("username");

        if(username!=null)
            emailEditText.setText(username);

        progressBar = (ProgressBar) view.findViewById(R.id.stormpath_resetpw_progress_bar);
        sendButton = (Button) view.findViewById(R.id.stormpath_resetpw_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSend();
            }
        });

        return view;
    }

    protected void onSend() {
        if (TextUtils.isEmpty(emailEditText.getText().toString())) { //check for valid email
            Snackbar.make(sendButton, R.string.stormpath_all_fields_mandatory, Snackbar.LENGTH_SHORT).show();
            return;
        }

        Stormpath.resetPassword(emailEditText.getText().toString(), new StormpathCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                hideProgress();
                Snackbar.make(sendButton, getString(R.string.stormpath_resetpassword_result), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(StormpathError error) {
                hideProgress();
                Snackbar.make(sendButton, error.message(), Snackbar.LENGTH_LONG).show();
            }
        });

        showProgress();
    }

    public void showProgress() {
        sendButton.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgress() {
        sendButton.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StormpathResetPasswordFragmentListener) {
            mListener = (StormpathResetPasswordFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement StormpathResetPasswordFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface StormpathResetPasswordFragmentListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
