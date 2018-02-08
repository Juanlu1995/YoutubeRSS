package com.example.juanlu.youtubeplayerlist;

import android.util.Log;

import com.example.juanlu.youtubeplayerlist.model.YoutubeVideo;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by juanlu on 7/02/18.
 */

public class ParseVideos {
    private static final String TAG = "Parse video";

    private String xmlData;
    private ArrayList<YoutubeVideo> youtubeVideos;

    public ParseVideos(String xmlData) {
        this.xmlData = xmlData;
        youtubeVideos = new ArrayList<>();
    }

    public String getXmlData() {
        return xmlData;
    }

    public void setXmlData(String xmlData) {
        this.xmlData = xmlData;
    }

    public ArrayList<YoutubeVideo> getYoutubeVideos() {
        return youtubeVideos;
    }

    public void setYoutubeVideos(ArrayList<YoutubeVideo> youtubeVideos) {
        this.youtubeVideos = youtubeVideos;
    }


    public boolean process() {
        boolean status = true;
        YoutubeVideo currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(this.xmlData));
            int eventType = xpp.getEventType();

            String tagName;

            while (eventType != XmlPullParser.END_DOCUMENT) {

                tagName = xpp.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase("entry")) {
                            inEntry = true;
                            currentRecord = new YoutubeVideo();
                        }
                        if (tagName.equalsIgnoreCase("title"))
                            break;
                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (inEntry) {
                            if (tagName.equalsIgnoreCase("entry")) {
                                youtubeVideos.add(currentRecord);
                                inEntry = false;
                            } else if (tagName.equalsIgnoreCase("title")) {
                                currentRecord.setTitle(textValue);
                            } else if (tagName.equalsIgnoreCase("videoId")) {
                                currentRecord.setUrl(textValue);
                            } else if (tagName.equalsIgnoreCase("name")) {
                                currentRecord.setOwner(textValue);
                            } else if (tagName.equalsIgnoreCase("thumbnail")) {
                                String url = xpp.getAttributeValue(null, "url");
                                currentRecord.setImageUrl(url);
                            }
                        }
                        break;
                    default:
                        // Nada
                }
            }
        } catch (XmlPullParserException e) {
            Log.d(TAG, "error parseando xml" + e.getMessage());
            e.printStackTrace();
            status = false;
        }

        return status;
    }

    public YoutubeVideo getVideo(int position){
        return youtubeVideos.get(position);
    }
}