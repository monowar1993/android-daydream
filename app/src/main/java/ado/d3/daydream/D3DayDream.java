package ado.d3.daydream;

import android.service.dreams.DreamService;
import android.widget.VideoView;
import android.net.Uri;
import android.util.Log;
import android.media.MediaPlayer;

/**
 * Created by john on 5/23/16.
 */
public class D3DayDream extends DreamService {

    VideoView videoHolder;

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        Log.v("D3DayDream", "started");

    }

    @Override
    public void onDreamingStopped(){
        super.onDreamingStopped();
        Log.v("D3DayDream", "stoped");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.v("D3DayDream", "attached");

        setContentView(R.layout.main);

        setFullscreen(true);

        String uriPath = "android.resource://"+getPackageName()+"/"+R.raw.video;
        Uri uri = Uri.parse(uriPath);

        videoHolder = (VideoView)findViewById(R.id.VideoView);
        setContentView(videoHolder);
        videoHolder.setVideoURI(uri);
        videoHolder.requestFocus();
        videoHolder.start();

        videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                videoHolder.start(); //need to make transition seamless.
            }
        });
    }
}
