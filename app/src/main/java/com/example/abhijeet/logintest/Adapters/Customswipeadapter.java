package com.example.abhijeet.logintest.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.abhijeet.logintest.ConstructorsandInterfaces.Communicator;
import com.example.abhijeet.logintest.ConstructorsandInterfaces.keys;
import com.example.abhijeet.logintest.MainStuff.MainFragment;
import com.example.abhijeet.logintest.R;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by abhij on 29-01-2016.
 */
public class Customswipeadapter extends android.support.v4.view.PagerAdapter {


    private int[] imageresources = {R.drawable.com_facebook_profile_picture_blank_square, R.drawable.messenger_bubble_large_blue};
    private Context context;
    private LayoutInflater layoutInflater;
    private NetworkImageView networkImageView;
    private ImageLoader imageLoader;
    private ArrayList<keys> mListPhotos = new ArrayList<>();

    public void setURLList(ArrayList<String> URLList) {
        this.URLList = URLList;
    }

    private ArrayList<String> URLList = new ArrayList<>();

    public Customswipeadapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return URLList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Log.d("Swipe  : ", "yes");

        View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);
//        ImageView imageView = (ImageView) item_view.findViewById(R.id.slider_image_viewer);
//        imageView.setImageResource(imageresources[position]);
        //  picture = (ImageView) item_view.findViewById(R.id.sliderimageview);


        networkImageView = (NetworkImageView) item_view.findViewById(R.id.network_imageview);
        imageLoader = VolleySingleton.getInstance().getImageLoader();
        networkImageView.setImageUrl(URLList.get(position), imageLoader);

        container.addView(item_view);

        notifyDataSetChanged();
        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }


    public void setPhotos(ArrayList<keys> keyList) {


        this.mListPhotos = keyList;
        //update the adapter to reflect the new set of movies
        notifyDataSetChanged();

        Log.d("URL", mListPhotos.toString());
//        this.mListPhotos = keyList;
//        for (int i = 0; i < mListPhotos.size(); i++) {
//            keys currentkey = mListPhotos.get(i);
//            String urlpicture = currentkey.getImageurl();
//            this.URLList.add(urlpicture);
//        }

    }

}
