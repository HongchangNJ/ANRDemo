package com.example.demo.utils;

import android.util.Log;

import com.example.demo.MainActivity;

public class Lock1 implements Runnable{
    private static final String TAG = "Lock1";

    @Override
    public void run(){
        try{
            Log.e(TAG, "Lock1 running");
            while(true){
                synchronized(MainActivity.obj1){
                    Log.e(TAG, "Lock1 lock obj1");
                    Thread.sleep(3000);//获取obj1后先等一会儿，让Lock2有足够的时间锁住obj2
                    synchronized(MainActivity.obj2){
                        Log.e(TAG, "Lock1 lock obj2");
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}