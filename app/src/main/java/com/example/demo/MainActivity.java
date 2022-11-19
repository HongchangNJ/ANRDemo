package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.demo.utils.ANRFileObserver;
import com.example.demo.utils.ANRWatchDog;
import com.example.demo.utils.AnrTestService;
import com.example.demo.utils.Lock2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.concurrent.locks.LockSupport;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intentOne = new Intent(this, AnrTestService.class);
//        startService(intentOne);

//        ANRFileObserver fileObserver = new ANRFileObserver("/data/anr");
//        fileObserver.startWatching();

        ANRWatchDog.getInstance().addANRListener(new ANRWatchDog.ANRListener() {
            @Override
            public void onAnrHappened(String stackTraceInfo) {
                Log.e(TAG, "onAnrHappened: "+stackTraceInfo );
            }
        }).start();

    }

    public static String obj1 = "obj1";
    public static String obj2 = "obj2";

    public void test(View view) {

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        LockSupport.park();
//        try {
//            Thread.sleep(300000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        Log.e(TAG, "test: ----------------------------" );
//        try {
//            String absolutePath = this.getExternalCacheDir().getAbsolutePath();
//            FileOutputStream out = new FileOutputStream(absolutePath+"/1.txt");
//
//            while (true) {
//                out.write(new byte[1024]);
//                out.flush();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }


    public void test2(View view) {
//        Thread t = new Thread(new Lock1());
//        t.start();
        Thread t2 = new Thread(new Lock2());
//        t.start();
        t2.start();
    }

    public void test3(View view) {

        for (int i = 0; i < 3; i++) {
            synchronized (MainActivity.obj1) {
                Log.e(TAG, "**"+i+"**Lock2 lock obj2");
                LockSupport.parkNanos(3_000_000_000l);
                synchronized (MainActivity.obj2) {
                    Log.e(TAG, "Lock2 lock obj1");
                }
            }
        }
    }


    //死锁
    public void test4(View view) {
        for (int i = 0; i < 3; i++) {
            synchronized (MainActivity.obj1) {
                Log.e(TAG, "--"+i+"--Lock2 lock obj1");
                LockSupport.parkNanos(3_000_000_000l);
                synchronized (MainActivity.obj2) {
                    Log.e(TAG, "Lock2 lock obj2");
                }
            }
        }

    }
}