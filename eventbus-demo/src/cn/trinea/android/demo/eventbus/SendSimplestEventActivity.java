package cn.trinea.android.demo.eventbus;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.trinea.android.demo.eventbus.util.TextUtils;
import de.greenrobot.event.EventBus;

public class SendSimplestEventActivity extends BaseActivity {

    private EditText eventET;
    private Button   sendBtn;
    private TextView receivedEventTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_send_simplest_event);

        initView();

        // Register
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        // Unregister
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    // Receive Event
    public void onEvent(String event) {
        receivedEventTV.setText(event);
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
