package com.lch.audio_player;


import com.lch.audio_player.internal.AudioPlayerProxy;

/**
 * Created by bbt-team on 2017/9/18.
 */

public final class LchAudioPlayer {

    private LchAudioPlayer() {
    }

    /**
     * 创建一个新的{@code AudioPlayer}
     *
     * @return {@code AudioPlayer}
     */
    public static AudioPlayer newAudioPlayer() {
        return new AudioPlayerProxy();
    }

    /**
     * 日志开关。
     *
     * @param enable
     */
    public static void setLogEnable(boolean enable) {
        Config.logger.setLogEnable(enable);
    }

}
