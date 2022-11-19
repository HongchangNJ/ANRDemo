package com.example.demo.utils;

import android.util.Log;

import com.example.demo.MainActivity;

public class Lock2 implements Runnable{
    private static final String TAG = "Lock2";

    @Override
    public void run(){
        try{
            Log.e(TAG, "Lock2 running");
            while(true){
                synchronized(MainActivity.obj2){
                    Log.e(TAG, "Lock2 lock obj2");
                    Thread.sleep(3000);
                    synchronized(MainActivity.obj1){
                        Log.e(TAG, "Lock2 lock obj1");
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}