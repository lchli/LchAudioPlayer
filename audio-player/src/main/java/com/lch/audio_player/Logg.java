package com.lch.audio_player;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by lichenghang on 2017/5/14.
 */

public class Logg {

    private String prefix = "";
    private boolean isLogEnable = false;

    public Logg(String prefix) {
        this.prefix = prefix;
    }

    public Logg() {
    }


    public void setLogEnable(boolean logEnable) {
        isLogEnable = logEnable;
    }

    public void log(Object tag, Object msg) {
        if (msg != null && tag != null) {
            Log.e(tag.toString() + prefix, msg.toString());
        }
    }


    public void logIfDebug(Object tag, Object msg) {
        if (isLogEnable) {
            log(tag, msg);
        }
    }

    public void logJsonIfDebug(Object tag, String json) {

        int indent = 4;

        if (TextUtils.isEmpty(json)) {
            logIfDebug(tag, "JSON{json is empty}");
            return;
        }

        try {

            if (json.startsWith("{")) {

                JSONObject jsonObject = new JSONObject(json);

                String msg = jsonObject.toString(indent);

                logIfDebug(tag, msg);

            } else if (json.startsWith("[")) {

                JSONArray jsonArray = new JSONArray(json);

                String msg = jsonArray.toString(indent);

                logIfDebug(tag, msg);

            }

        } catch (Exception e) {
            logIfDebug(tag, e.toString() + "\n\njson = " + json);
        }

    }
}
