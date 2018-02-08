package com.example.juanlu.youtubeplayerlist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juanlu.youtubeplayerlist.model.YoutubeVideo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by juanlu on 7/02/18.
 */

public class AdapterItem extends BaseAdapter {

    private Activity activity;
    private ArrayList<YoutubeVideo> videoList;

    public AdapterItem(Activity activity, ArrayList<YoutubeVideo> videoList) {
        this.activity = activity;
        this.videoList = videoList;
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public Object getItem(int position) {
        return videoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.youtubevideos_list_item, null);
        }

        YoutubeVideo youtubeVideo = videoList.get(position);

        TextView title = view.findViewById(R.id.textViewTitle);
        title.setText(youtubeVideo.getTitle());

        TextView owner = view.findViewById(R.id.textViewOwner);
        owner.setText(youtubeVideo.getOwner());

        ImageView imageUrl = view.findViewById(R.id.imageViewImage);
        Picasso.with(view.getContext()).load(youtubeVideo.getImageUrl()).into(imageUrl);

        return view;
    }
}
