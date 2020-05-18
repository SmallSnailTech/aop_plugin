package com.snail.speeddemo;

import android.util.Log;

public class LauncherTimer {
    private static final String TAG = "LauncherTimer";
    private static long sStart;
    private static String mName;
    public static void startTracing(String tag){
        sStart = System.currentTimeMillis();
        mName = tag;
    }

    public static void endTracing(){
        Log.d(TAG,mName+" total time == "+ (System.currentTimeMillis() - sStart));
    }

    public static void endTracing(String tag2){
        Log.d(TAG,tag2+"  == "+ (System.currentTimeMillis() - sStart));
    }
}
