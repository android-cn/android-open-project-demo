package cn.trinea.android.demo.eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * BroadcastReceiver Demo, include general broadcast, local broadcast, ordered broadcast,sticky broadcast
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2012-9-20
 */
public class MainActivity extends BaseActivity {

    private Button sendSimplestEventBtn;
    private Button sendEventSelfDefinedBtn;
    private Button diffThreadModeBtn;
    private Button sendOrderedEventBtn;
    private Button sendStickyEventBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);

        initView();
    }

    private void initView() {
        sendSimplestEventBtn = (Button)findViewById(R.id.send_simplest_event);
        sendSimplestEventBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SendSimplestEventActivity.class));
            }
        });

        sendEventSelfDefinedBtn = (Button)findViewById(R.id.send_event_self_defined);
        sendEventSelfDefinedBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SendSelfDefinedEventActivity.class));
            }
        });

        diffThreadModeBtn = (Button)findViewById(R.id.diff_thread_mode);
        diffThreadModeBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, DiffThreadModeActivity.class));
            }
        });

        sendOrderedEventBtn = (Button)findViewById(R.id.send_ordered_event);
        sendOrderedEventBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ViewPagerDemo.class));
            }
        });

        sendStickyEventBtn = (Button)findViewById(R.id.send_sticky_event);
        sendStickyEventBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SendStickyEventActivity.class));
            }
        });
    }
}
