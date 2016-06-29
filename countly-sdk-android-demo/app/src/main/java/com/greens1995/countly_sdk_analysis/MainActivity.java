package com.greens1995.countly_sdk_analysis;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

import ly.count.android.sdk.Countly;


public class MainActivity extends Activity {
    private Activity activity;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        activity = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Countly.onCreate(this);

        Button eventNormal = (Button) findViewById(R.id.event_normal);
        eventNormal.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Countly.sharedInstance().recordEvent("test", 1);
            }
        });

        Button eventSum = (Button) findViewById(R.id.event_sum);
        eventSum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Countly.sharedInstance().recordEvent("test2", 1, 2);
            }
        });

        Button eventSegment = (Button) findViewById(R.id.event_segment);
        eventSegment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Map<String,String> seg = new HashMap<>();
                seg.put(getPackageName() + "NetworkType", "3G");
                Countly.sharedInstance().recordEvent("login", seg, 1);
            }
        });

        Button eventSegmentSum = (Button) findViewById(R.id.event_segment_sum);
        eventSegmentSum.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final Map<String,String> seg = new HashMap<>();
                seg.put(getPackageName() + "NetworkType", "3G");
                Countly.sharedInstance().recordEvent("login", seg, 1,3);
            }
        });

        Button button1 = (Button) findViewById(R.id.runtime);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Countly.sharedInstance().addCrashLog("Button 1 pressed");
                Countly.sharedInstance().crashTest(4);
            }
        });

        Button button2 = (Button) findViewById(R.id.nullpointer);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Countly.sharedInstance().addCrashLog("Button 2 pressed");
                Countly.sharedInstance().crashTest(5);
            }
        });

        Button button3 = (Button) findViewById(R.id.division0);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Countly.sharedInstance().addCrashLog("Button 3 pressed");
                Countly.sharedInstance().crashTest(2);
            }
        });


        Button button5 = (Button) findViewById(R.id.stackoverflow);
        button5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Countly.sharedInstance().addCrashLog("Button 4 pressed");
                Countly.sharedInstance().crashTest(1);
            }
        });

        Button button6 = (Button) findViewById(R.id.handled);
        button6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Countly.sharedInstance().addCrashLog("Button 5 pressed");
                try {
                    Countly.sharedInstance().crashTest(5);
                }
                catch(Exception e){
                    Countly.sharedInstance().logException(e);
                }
            }
        });
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Countly.sharedInstance().onStart(this);
    }

    @Override
    public void onStop()
    {
        Countly.sharedInstance().onStop();
        super.onStop();
    }

}
