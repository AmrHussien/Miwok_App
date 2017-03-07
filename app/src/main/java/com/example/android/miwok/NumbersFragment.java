package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;


    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        // Pause playback
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        // Resume playback
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {

                        releaseMediaPlayer();
                    }
                }
            };


    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }
    };

    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        /** TODO: Insert all the code from the NumberActivity’s onCreate() method after the setContentView method call */

        // Create and setup the {@Link AudioManager} to request audio focus.
        mAudioManager=(AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);


        final ArrayList<word> words = new ArrayList<word>();


        words.add(new word("One", "Lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new word("Two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new word("Three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new word("Four", "oyysua", R.drawable.number_four, R.raw.number_four));
        words.add(new word("Five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new word("Six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new word("Seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new word("Eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new word("Nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new word("Ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));


        wordAdapter adapter = new wordAdapter(getActivity(), words, R.color.category_numbers);
        ListView listview = (ListView) rootView.findViewById(R.id.list);
        assert listview != null;
        listview.setAdapter(adapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()

        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                word wordPos = words.get(position);

                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();


                //Request audio focus for playback.
                int result= mAudioManager.requestAudioFocus(afChangeListener,
                        // Use the music stream
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
                {

                    // Create and setup the {@Linek MediaPlayer} for the audio resource associated with the current word.
                    mMediaPlayer = MediaPlayer.create(getActivity(), wordPos.getmAudioResourceId());
                    mMediaPlayer.start();

                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }




            }
        });
        return rootView;
    }




    @Override
    public void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

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

            // Abandon audio focus when playback complete
            mAudioManager.abandonAudioFocus(afChangeListener);
        }
    }




}
