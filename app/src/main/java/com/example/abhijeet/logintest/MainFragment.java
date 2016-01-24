package com.example.abhijeet.logintest;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainFragment extends Fragment {
    public TextView mTextDetails;
    private AccessTokenTracker mTockenTracker;
    private ProfileTracker mProfileTracker;
    private CallbackManager mCallbackManager;

    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
           AccessToken accessToken=loginResult.getAccessToken();
            Profile profile=Profile.getCurrentProfile();
            mTextDetails.setText(ConstructWelcomeMessage(profile));
        }


        @Override
        public void onCancel() {
            Log.d("Ac:","OnCancel");
        }

        @Override
        public void onError(FacebookException error) {
            Log.d("Ac:","OnError"+error);
        }
    };

    private String ConstructWelcomeMessage(Profile profile) {
        StringBuffer stringBuffer=new StringBuffer();
        if(profile!=null){
            stringBuffer.append("welcome: "+profile.getName());
        }
        return stringBuffer.toString();
    }

    public MainFragment() {
    }




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCallbackManager = CallbackManager.Factory.create();
        setupTockenTraacker();
        setupprofiletracker();
        mTockenTracker.startTracking();
        mProfileTracker.startTracking();
    }

    private void setupTockenTraacker() {
        mTockenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newToken) {
            Log.d("AC",""+newToken);
            }
        };
    }

    private void setupprofiletracker() {
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                Log.d("AC",""+newProfile);
                mTextDetails.setText(ConstructWelcomeMessage(newProfile));
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupTextDetails(view);
        setupLoginButton(view);

    }

    private void setupLoginButton(View view) {
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setFragment(this);
        loginButton.setReadPermissions("user_friends");
        loginButton.registerCallback(mCallbackManager, mCallBack);
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        mTextDetails.setText(ConstructWelcomeMessage(profile));
    }

    public void onStop() {
        super.onStop();
        mTockenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }

    private void setupTextDetails(View view) {
        mTextDetails = (TextView) view.findViewById(R.id.text_details);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
