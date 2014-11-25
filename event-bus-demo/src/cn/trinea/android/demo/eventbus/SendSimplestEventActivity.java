package cn.trinea.android.demo.eventbus;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.trinea.android.demo.eventbus.util.TextUtils;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.SubscriberExceptionEvent;

public class SendSimplestEventActivity extends BaseActivity {

    private EditText eventET;
    private Button   sendBtn;
    private TextView receivedEventTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_send_simplest_event);

        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        // EventBus.builder().throwSubscriberException(true).installDefaultEventBus().register(this);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    // Receive Event
    public void onEvent(String event) {
        receivedEventTV.setText(event);
    }

    // Process when EventBus call onEvent exception
    public void onEvent(SubscriberExceptionEvent event) {
        Log.e("event", "catch SubscriberExceptionEvent");
    }

    private void initView() {
        eventET = (EditText)findViewById(R.id.event_content);
        receivedEventTV = (TextView)findViewById(R.id.receive_event);
        sendBtn = (Button)findViewById(R.id.send_event);
        sendBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(TextUtils.getHintIfTextIsNull(eventET));
            }
        });
    }
}
