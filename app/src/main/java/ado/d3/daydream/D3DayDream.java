package ado.d3.daydream;

import android.service.dreams.DreamService;
import android.widget.VideoView;
import android.net.Uri;
import android.util.Log;
import android.media.MediaPlayer;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import android.view.WindowManager;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Activity;

/**
 * Created by john on 5/23/16.
 */

public class D3DayDream extends DreamService {

    private VideoView videoHolder;
    @SuppressWarnings("unchecked") private final List blockedKeys = new ArrayList(
            Arrays.asList(
                    KeyEvent.KEYCODE_VOLUME_DOWN,
                    KeyEvent.KEYCODE_VOLUME_UP,
                    KeyEvent.KEYCODE_BACK,
                    KeyEvent.KEYCODE_HOME,
                    KeyEvent.KEYCODE_POWER
            )
    );
    private Button hiddenExitButton;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(!hasFocus) {
            // Close every kind of system dialog
            Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeDialog);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        Log.v("D3DayDreamLog", "touchPressed");
        return true;//consume
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.v("D3DayDreamLog", "keyPressed");
        // any key
        return true;
        // controlled keys
//        if (blockedKeys.contains(event.getKeyCode())) {
//            return true;
//        } else {
//            return super.dispatchKeyEvent(event);
//        }
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();
        Log.v("D3DayDreamLog", "started");
    }

    @Override
    public void onDreamingStopped(){
        super.onDreamingStopped();
        Log.v("D3DayDreamLog", "stoped");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.v("D3DayDreamLog", "attached");

        setContentView(R.layout.main);

        setFullscreen(true);

        String uriPath = "android.resource://"+getPackageName()+"/"+R.raw.video;
        Uri uri = Uri.parse(uriPath);

        videoHolder = (VideoView)findViewById(R.id.VideoView);
        setContentView(videoHolder);
        videoHolder.setVideoURI(uri);
        videoHolder.requestFocus();
        videoHolder.start();
        //loop the video
        videoHolder.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                videoHolder.start();
            }
        });

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
