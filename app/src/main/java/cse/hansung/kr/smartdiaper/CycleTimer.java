package cse.hansung.kr.smartdiaper;

import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class CycleTimer {
    private static int period;

    public static void init(){
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                Log.d("Cycle","Cycle Alarm Success");
                ConnectServer.getInstance().requestGet("http://192.168.200.113:8080/smartDiaper/cycle","key");
            }
        };

        Timer timer = new Timer();
        timer.schedule(tt,0, 10000);
    }

    public static void setPeriod(int cycle){
        period = cycle * 1000;
    }
}
