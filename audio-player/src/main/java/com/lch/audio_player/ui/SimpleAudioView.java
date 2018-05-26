package com.lch.audio_player.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.lch.audio_player.R;

/**
 * Created by lichenghang on 2018/5/26.
 */

public class SimpleAudioView extends FrameLayout {


    public ImageView ivPlayPause;
    public SeekBar seekBar;

    public SimpleAudioView(@NonNull Context context) {
        super(context);
        init();
    }

    public SimpleAudioView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SimpleAudioView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View.inflate(getContext(), R.layout.lch_audioplayer_simple_player_view, this);

        ivPlayPause = (ImageView)findViewById(R.id.ivPlayPause);
        seekBar = (SeekBar)findViewById(R.id.seekBar);
    }
}
