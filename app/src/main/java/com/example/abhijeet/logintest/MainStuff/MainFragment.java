package com.example.abhijeet.logintest.MainStuff;

import android.content.Intent;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.abhijeet.logintest.Adapters.AdapterBeta;
import com.example.abhijeet.logintest.Adapters.Customswipeadapter;
import com.example.abhijeet.logintest.ConstructorsandInterfaces.Communicator;
import com.example.abhijeet.logintest.ConstructorsandInterfaces.keys;


import com.example.abhijeet.logintest.R;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainFragment extends Fragment implements AdapterBeta.Clicklistner {
    public static final String STATE_KEYS = "state_keys";
    public RecyclerView profilelist;
    public TextView mTextDetails;
    private AccessTokenTracker mTockenTracker;
    private ProfileTracker mProfileTracker;
    private CallbackManager mCallbackManager;
    public Communicator communicator;
    public String id;
    public ProfilePictureView profilePictureView;
    public static ArrayList<keys> keyList = new ArrayList<>();
    public ArrayList<String> urllist = new ArrayList<>();


    public LinearLayoutManager mlinearLayoutManager;
    public GridLayoutManager mGridLayoutManger;

    private AdapterBeta adapterBeta;
    private Customswipeadapter customswipeadapter;
    private FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {

            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            Log.d("Access Token", "" + accessToken);
            String name = profile.getName();
            id = profile.getId();
            //   profilePictureView.setProfileId(id);
            communicator.respond(id, name);

            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken,
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(
                                JSONObject object,
                                GraphResponse response) {
                            try {
                                JSONObject photos = object.getJSONObject("photos");
                                JSONArray data = photos.getJSONArray("data");


                                for (int i = 0; i < data.length(); i++) {
                                    keys imagekey = new keys();
                                    JSONObject picobj = data.getJSONObject(i);
                                    long picid = picobj.getLong("id");
                                    //      Log.d("id",i+"  "+picid);
                                    imagekey.setImageid(picid);

                                    JSONArray imagearray = picobj.getJSONArray("images");
                                    JSONObject imageobject = imagearray.getJSONObject(0);

                                    String picurl = imageobject.getString("source");
                                    // Log.d("picURL", picurl);
                                    imagekey.setImageurl(picurl);
                                    urllist.add(picurl);


                                    //     mtextview.append(i+"\n id:"+picid.toString()+"\n \n picURL:" + picurl.toString()+"\n");
                                    keyList.add(imagekey);
                                }
                                // Toast.makeText(getActivity(),keyList.toString(),Toast.LENGTH_LONG).show();


                                adapterBeta.setPhotos(keyList);
//                                customswipeadapter.setPhotos(keyList);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
            Bundle parameters = new Bundle();
            //   parameters.putString("fields", "photos.limit(30){id,picture}");

            parameters.putString("fields", "photos.limit(30){id,images}");
            request.setParameters(parameters);
            request.executeAsync();


        }


        @Override
        public void onCancel() {
            Log.d("Ac:", "OnCancel");
        }

        @Override
        public void onError(FacebookException error) {
            Log.d("Ac:", "OnError" + error);
        }
    };


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
        setupTockenTraacker();
        setupprofiletracker();
        mTockenTracker.startTracking();
        mProfileTracker.startTracking();

        communicator = (Communicator) getActivity();

    }

    private void setupTockenTraacker() {


        mTockenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newToken) {
                Log.d("AC", "" + newToken);
            }
        };
    }

    private void setupprofiletracker() {
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                Log.d("AC", "" + newProfile);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //   LoginManager.getInstance().logOut(); to logout form the app
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mlinearLayoutManager = new LinearLayoutManager(getActivity());
        mGridLayoutManger = new GridLayoutManager(getActivity(), 3);

        profilelist = (RecyclerView) view.findViewById(R.id.recyclewindow);
        profilelist.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBeta = new AdapterBeta(getActivity());
        customswipeadapter = new Customswipeadapter(getActivity());
        adapterBeta.setClicklistner(this);
        profilelist.setAdapter(adapterBeta);


        if (savedInstanceState != null) {
            keyList = savedInstanceState.getParcelableArrayList(STATE_KEYS);
            adapterBeta.setPhotos(keyList);
        } else {

        }
//ADD A ELSE STATEMENT TO PARSE JSON
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupLoginButton(view);
        setupGridButton(view);

    }

    private void setupGridButton(View view) {
        Button changeview = (Button) view.findViewById(R.id.changeview);
        changeview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                changeview();
            }
        });
    }

    public void changeview() {
        profilelist.setAdapter(null);
        if (profilelist.getLayoutManager() == mGridLayoutManger)
            profilelist.setLayoutManager(mlinearLayoutManager); // set appropriate layout manager

        else {
            // clear recycler view before tampering with its layout manager
            profilelist.setLayoutManager(mGridLayoutManger); // set appropriate layout manager
        }
        profilelist.setAdapter(adapterBeta); // reattach adapter
    }


    private void setupLoginButton(View view) {
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setFragment(this);
        loginButton.setReadPermissions("user_photos");
        loginButton.registerCallback(mCallbackManager, mCallBack);
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
    }

    public void onStop() {
        super.onStop();
        mTockenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_KEYS, keyList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void itemclicklistner(View view, int position) {
        Intent intent = new Intent(getActivity(), Photos_Activity.class);
        //   intent.putExtra("position", position);
        Log.d("posiion in main frag", "" + position);
        startActivity(intent);
    }
}
