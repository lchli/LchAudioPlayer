package com.lch.audio_player.internal;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.lch.audio_player.AudioPlayer;
import com.lch.audio_player.Config;

import java.io.FileDescriptor;
import java.util.Locale;
import java.util.Map;

/**
 * Created by bbt-team on 2017/9/18.
 */

public class AudioPlayerProxy implements AudioPlayer {

    private static final String TAG = "AudioPlayerProxy";

    private final MediaPlayer mediaPlayer = new MediaPlayer();
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private StateListener mStateListener;
    private boolean isUpdatingProgress = false;
    private boolean isPrepared = false;
    private int bufferPercent;

    private final Runnable mTicker = new Runnable() {
        public void run() {
            if (mStateListener != null) {
                mStateListener.onProgress(getCurrentPosition(), getDuration());
            }

            long now = SystemClock.uptimeMillis();
            long next = now + (1000 - now % 1000);

            mHandler.postAtTime(mTicker, next);
        }
    };

    public AudioPlayerProxy() {
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                isPrepared = true;
                bufferPercent = 0;

                if (mStateListener != null) {
                    mStateListener.onPrepared();
                }

            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                bufferPercent = percent;

                if (mStateListener != null) {
                    mStateListener.onBufferingUpdate(percent);
                }
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mStateListener != null) {
                    mStateListener.onCompletion();
                }
                if (!isLooping()) {
                    stopUpateProgress();
                }
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (mStateListener != null) {
                    mStateListener.onError(what, extra);
                }
                return false;
            }
        });
        mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                Config.logger.logIfDebug(TAG, String.format(Locale.ENGLISH, "onInfo(what=%d,extra=%d)", what, extra));
                if (mStateListener != null) {
                    mStateListener.onInfo(what, extra);
                }
                return false;
            }
        });
        mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                if (mStateListener != null) {
                    mStateListener.onSeekComplete();
                }
            }
        });
    }

    @Override
    public MediaPlayer getRawPlayer() {
        return mediaPlayer;
    }

    @Override
    public void setDataSource(@NonNull Context context, @NonNull Uri uri) {
        setDataSource(context, uri, null);
    }

    @Override
    public void setDataSource(@NonNull Context context, @NonNull Uri uri, @Nullable Map<String, String> headers) {
        try {
            mediaPlayer.setDataSource(context, uri, headers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setDataSource(@NonNull FileDescriptor fd) {
        try {
            mediaPlayer.setDataSource(fd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prepareAsync() {
        try {
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        try {
            mediaPlayer.stop();
            reprepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isPrepared() {
        return isPrepared;
    }

    @Override
    public void start() {
        try {
            mediaPlayer.start();
            startUpateProgress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        try {
            mediaPlayer.pause();
            stopUpateProgress();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void reset() {
        try {
            mediaPlayer.reset();
            reprepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void reprepare() {
        isPrepared = false;
        stopUpateProgress();
    }

    @Override
    public void release() {
        try {
            mediaPlayer.release();
            reprepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        try {
            mediaPlayer.setVolume(leftVolume, rightVolume);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setLooping(boolean looping) {
        try {
            mediaPlayer.setLooping(looping);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void seekTo(int msec) {
        try {
            mediaPlayer.seekTo(msec);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAudioStreamType(int streamtype) {
        try {
            mediaPlayer.setAudioStreamType(streamtype);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setStateListener(StateListener listener) {
        mStateListener = listener;
    }

    @Override
    public boolean isPlaying() {
        try {
            return mediaPlayer.isPlaying();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public long getCurrentPosition() {
        try {
            return mediaPlayer.getCurrentPosition();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public long getDuration() {
        try {
            return mediaPlayer.getDuration();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean isLooping() {
        try {
            return mediaPlayer.isLooping();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int getBufferPercent() {
        return bufferPercent;
    }

    @Override
    public int getAudioSessionId() {
        try {
            return mediaPlayer.getAudioSessionId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void stopUpateProgress() {
        if (isUpdatingProgress) {
            mHandler.removeCallbacks(mTicker);
            isUpdatingProgress = false;
        }
    }

    private void startUpateProgress() {
        if (!isUpdatingProgress) {
            mTicker.run();
            isUpdatingProgress = true;
        }
    }
}
