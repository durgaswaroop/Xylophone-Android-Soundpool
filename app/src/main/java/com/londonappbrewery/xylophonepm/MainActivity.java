package com.londonappbrewery.xylophonepm;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;

import com.londonappbrewery.xylophonepm.utils.KeyTone;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {

    // Helpful Constants
    private final int NR_OF_SIMULTANEOUS_SOUNDS = 7;
    private final float LEFT_VOLUME = 1.0f;
    private final float RIGHT_VOLUME = 1.0f;
    private final int NO_LOOP = 0;
    private final int PRIORITY = 0;
    private final float NORMAL_PLAY_RATE = 1.0f;
    private final static String TAG = "xylo";
    List<KeyTone> keyTones = new ArrayList<>();
    boolean recordingFlag = false;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Create a new SoundPool
        final SoundPool mSoundPool = (new SoundPool.Builder()).build();
        Log.d("Xylo", "" + mSoundPool);

        // TODO: Load and get the IDs to identify the sounds
        int mCSoundId = mSoundPool.load(getApplicationContext(), R.raw.note1_c, 1);
        int mDSoundId = mSoundPool.load(getApplicationContext(), R.raw.note2_d, 1);
        int mESoundId = mSoundPool.load(getApplicationContext(), R.raw.note3_e, 1);
        int mFSoundId = mSoundPool.load(getApplicationContext(), R.raw.note4_f, 1);
        int mGSoundId = mSoundPool.load(getApplicationContext(), R.raw.note5_g, 1);
        int mASoundId = mSoundPool.load(getApplicationContext(), R.raw.note6_a, 1);
        int mBSoundId = mSoundPool.load(getApplicationContext(), R.raw.note7_b, 1);

        final SparseIntArray keyNoteMap = new SparseIntArray();
        keyNoteMap.put(R.id.c_key, mCSoundId);
        keyNoteMap.put(R.id.d_key, mDSoundId);
        keyNoteMap.put(R.id.e_key, mESoundId);
        keyNoteMap.put(R.id.f_key, mFSoundId);
        keyNoteMap.put(R.id.g_key, mGSoundId);
        keyNoteMap.put(R.id.a_key, mASoundId);
        keyNoteMap.put(R.id.b_key, mBSoundId);

        for (int i = 0; i < keyNoteMap.size(); i++) {
            int buttonId = keyNoteMap.keyAt(i);
            int soundId = keyNoteMap.get(buttonId);
            findViewById(buttonId).setOnClickListener(view -> {
                if (recordingFlag) {
                    Log.d("Xylophone", "Recording Flag on");
                    Log.d("Xylophone", "Adding Id " + view.getId());
//                    buttonsClicked.add(view.getId());

                    long currentTime = new Date().getTime();
                    keyTones.add(new KeyTone(buttonId, currentTime, -100000L));

                    int keyTonesSize = keyTones.size();
                    if (keyTonesSize >= 2) {
                        keyTones.get(keyTonesSize - 2).setEndTime(currentTime);
                    }
                }
                mSoundPool.play(soundId, LEFT_VOLUME, RIGHT_VOLUME, PRIORITY, NO_LOOP, NORMAL_PLAY_RATE);
            });
        }

        Button playButton = (Button) findViewById(R.id.button_play);
        Button recordStopButton = (Button) findViewById(R.id.button_record_stop);

        playButton.setOnClickListener(View -> {
            Log.d(TAG, "Playbutton clicked ");
            for (int i = 0; i < keyTones.size(); i++) {
                KeyTone currentTone = keyTones.get(i);
                Button button = (Button) findViewById(currentTone.getButtonId());
                button.performClick();

                long endTime = currentTone.getEndTime();
                long startTime = currentTone.getStartTime();
                if (endTime > 0) {
                    try {
                        Thread.sleep(endTime - startTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        recordStopButton.setOnClickListener((View) -> {
            Log.d(TAG, "record stop clicked ");
            if (recordingFlag) {
                recordStopButton.setText(R.string.record);
                recordStopButton.setBackgroundColor(getResources().getColor(R.color.green));
                recordingFlag = false;
//                Log.d(TAG, "Buttons clicked: " + buttonsClicked);
                Log.d(TAG, "Keytones: " + keyTones);
            } else {
                recordStopButton.setText(R.string.stop);
                recordStopButton.setBackgroundColor(getResources().getColor(R.color.red));
                recordingFlag = true;
//                buttonsClicked = new ArrayList<>();
                keyTones = new ArrayList<>();
            }
        });

    }

}
