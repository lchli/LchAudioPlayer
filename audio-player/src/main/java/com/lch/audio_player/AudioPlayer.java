package com.lch.audio_player;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.FileDescriptor;
import java.util.Map;

/**
 * Created by bbt-team on 2017/9/18.
 */

public interface AudioPlayer {

    abstract class StateListener {

        /**
         * 播放发生错误的回调。
         *
         * @param what  {@link MediaPlayer#MEDIA_ERROR_IO},etc
         * @param extra
         */
        public void onError(int what, int extra) {

        }

        /**
         * 播放普通信息回调。
         *
         * @param what  {@link MediaPlayer#MEDIA_INFO_UNKNOWN},etc
         * @param extra
         */
        public void onInfo(int what, int extra) {

        }


        /**
         * 播放完成的回调。
         */
        public void onCompletion() {
        }

        /**
         * 播放缓冲更新时回调。
         *
         * @param percent 已经完成的百分比。范围(0-100)
         */
        public void onBufferingUpdate(int percent) {

        }

        /**
         * 播放进度更新回调。
         *
         * @param currentPositionMillsecs 当前进度，单位毫秒
         * @param totalMillsecs           总时间，单位毫秒
         */
        public void onProgress(long currentPositionMillsecs, long totalMillsecs) {

        }

        /**
         * 拖动完成时回调。
         */
        public void onSeekComplete() {

        }

        /**
         * 播放准备好时的回调。
         */
        public void onPrepared() {

        }
    }

    /**
     * @return
     * @hide
     */
    MediaPlayer getRawPlayer();

    /**
     * 设置播放源。
     *
     * @param context
     * @param uri     支持本地URI或网络URI。
     */
    void setDataSource(@NonNull Context context, @NonNull Uri uri);

    /**
     * 设置播放源。
     *
     * @param context
     * @param uri     支持本地URI或网络URI。
     * @param headers 网络URI对应的http header。
     */
    void setDataSource(@NonNull Context context, @NonNull Uri uri, @Nullable Map<String, String> headers);

    /**
     * 设置播放源。
     *
     * @param fd 文件描述符。支持asset文件描述符和普通文件描述符。
     */
    void setDataSource(@NonNull FileDescriptor fd);

    /**
     * 异步准备。
     */
    void prepareAsync();

    /**
     * 开始播放。
     */
    void start();

    /**
     * 暂停播放。
     */
    void pause();

    /**
     * 停止播放。
     */
    void stop();

    /**
     * 重置。此方法调用后必须重新调用{@code setDataSource}
     */
    void reset();

    /**
     * 释放所有资源(内存，cpu占用等),通常在activity的{@code onDestroy}调用。
     * 此方法调用后此player已不可用，若需再用需要创建新的player。
     */
    void release();

    /**
     * 设置左右音量大小。
     * 音量范围 0.0 to 1.0
     *
     * @param leftVolume
     * @param rightVolume
     */
    void setVolume(float leftVolume, float rightVolume);

    /**
     * 设置是否循环播放。必须在{@code setDataSource}之后调用才生效。
     *
     * @param looping
     */
    void setLooping(boolean looping);

    /**
     * 拖动到指定时间位置进行播放。
     *
     * @param msec 单位毫秒。
     */
    void seekTo(int msec);

    /**
     * 设置音频流的类型。
     *
     * @param streamtype 常量定义查看{@link AudioManager#STREAM_MUSIC，etc}
     */
    void setAudioStreamType(int streamtype);

    /**
     * 设置播放状态监听器。
     *
     * @param listener
     */
    void setStateListener(StateListener listener);

    /**
     * 是否正在播放。
     *
     * @return
     */
    boolean isPlaying();

    /**
     * 获取当前播放进度。
     *
     * @return 当前进度，单位毫秒。
     */
    long getCurrentPosition();

    /**
     * 获取总的播放时间。
     *
     * @return 总播放时间，单位毫秒。
     */
    long getDuration();

    /**
     * 是否处于循环播放模式。
     *
     * @return
     */
    boolean isLooping();

    /**
     * 获取播放缓存百分比。范围(0-100)
     *
     * @return
     */
    int getBufferPercent();

    /**
     * 是否已准备好。在prepare完成后处于此状态。
     *
     * @return
     */
    boolean isPrepared();

    int getAudioSessionId();


}
