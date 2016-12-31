package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FamilyFragment extends Fragment {

    //Create audio object
    private MediaPlayer mMediaPlayer;

    //Handles audio focus when playing a sound file
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {

        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ) {
                // Pause playback
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN state means we have regained focus and can resume playback
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                //The AUDIOFOCUS_LOSS case means we've lost audio focus and can stop playback and cleanup resources
                releaseMediaPlayer();
            }
        }
    };

    /*
    * This listener gets triggered when the {@link Media Player} has completed playing the audio file
    * */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            //Abandon audio focus when playback is complete
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    public FamilyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStop() {
        super.onStop();
        /*
        * When the activity is stopped, release the media player resources
        * because we won't be playing anymore sounds*/
        releaseMediaPlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Create a list of words
        ArrayList<Word> family = new ArrayList<Word>();
        family.add(new Word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        family.add(new Word("mother", "әṭa",R.drawable.family_mother, R.raw.family_mother));
        family.add(new Word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        family.add(new Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        family.add(new Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        family.add(new Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        family.add(new Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        family.add(new Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        family.add(new Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        family.add(new Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        WordAdapter adapter = new WordAdapter(getActivity(), family, R.color.category_family);

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called family_list, which is declared in the
        // activity_family.xml layout file.
        final ListView listView = (ListView) rootView.findViewById(R.id.list);

        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(adapter);

        // Create and setup the {@link AudioManager} to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a clickListener to play the audio file
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView < ? > adapter, View view, int position, long arg){
                /*
                * Store the audio resource ID to be passed to the mMediaPlayer variable*/
                Word audioResource = (Word)listView.getItemAtPosition(position);

                releaseMediaPlayer();

                // Create and setup the {@link AudioManager} to request audio focus
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.
                    mMediaPlayer = MediaPlayer.create(getActivity(), audioResource.getAudioResourceId());

                    // Start audio playback.
                    mMediaPlayer.start(); // no need to call prepare(); create() does that for you

                    //Release the Media Player once the audio playback is complete
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        return rootView;
    }


}
