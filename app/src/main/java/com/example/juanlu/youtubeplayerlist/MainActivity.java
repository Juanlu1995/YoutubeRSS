package com.example.juanlu.youtubeplayerlist;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.juanlu.youtubeplayerlist.model.YoutubeVideo;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_API_KEY = "AIzaSyB8b9oZgwp_hQiUSQ82Vew5i8dikzHxFY8";

    private Button mDownloadXML;
    private ListView mListView;
    private YoutubeVideo video;
    private String videoID;
    public String mXmlContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = findViewById(R.id.youtubeListView);
        mDownloadXML = findViewById(R.id.downloadXML);


        mDownloadXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ParseVideos parseVideo = new ParseVideos(mXmlContent);

                parseVideo.process();

                AdapterItem adapterItem = new AdapterItem(
                        MainActivity.this,
                        parseVideo.getYoutubeVideos()
                );

                mListView.setAdapter(adapterItem);

                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        video = parseVideo.getVideo(position);
                        videoID = video.getUrl();
                        Intent videoIntent = YouTubeStandalonePlayer.createVideoIntent(
                                MainActivity.this,
                                YOUTUBE_API_KEY,
                                videoID
                        );
                        startActivity(videoIntent);
                    }
                });
            }
        });


        DownloadData downloadData = new DownloadData();
        downloadData.execute("https://www.youtube.com/feeds/videos.xml?playlist_id=PLRZlMhcYkA2EQRcAq4nf7pFP3LcD5uX7h");
    }



    private class DownloadData extends AsyncTask<String, Void, String> {

        private static final String TAG = "DownloadData";

        @Override
        protected String doInBackground(String... strings) {
            mXmlContent = downloadXmlFile(strings[0]);

            if( mXmlContent == null ){
                Log.d(TAG, "Problema descargando el XML");
            }
            return mXmlContent;
        }

        public String downloadXmlFile(String urlVideo) {

            StringBuilder tempBuffer = new StringBuilder();

            try {
                URL url = new URL(urlVideo);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int response = connection.getResponseCode();
                Log.d(TAG, "Response Code: " + response);

                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                int charsRead;
                char[] inputBuffer = new char[500];

                while( true ) {
                    charsRead = isr.read(inputBuffer);

                    if( charsRead <= 0 ) {
                        break;
                    }

                    tempBuffer.append(String.copyValueOf(inputBuffer, 0, charsRead));
                }

                return tempBuffer.toString();

            }catch (MalformedURLException e){
                Log.d(TAG,"No se pudo cargar el RSS ->" +e.getMessage() );
            }catch (IOException e){
                Log.d(TAG,"No se pudo cargar el RSS ->" +e.getMessage() );
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
