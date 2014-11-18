package cn.trinea.android.demo.eventbus;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.trinea.android.demo.eventbus.util.LogUtils;
import cn.trinea.android.demo.eventbus.util.TextUtils;
import de.greenrobot.event.EventBus;

public class DiffThreadModeActivity extends BaseActivity {

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
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    // Receive Event
    public void onEvent(String event) {
        LogUtils.d(this, "onEvent");
        appendText("onEvent receive msg: " + event);
    }

    // Receive Event
    public void onEventBackgroundThread(String event) {
        LogUtils.d(this, "onEventBackgroundThread");
        appendText("onEventBackgroundThread receive msg: " + event);
    }

    // Receive Event
    public void onEventAsync(String event) {
        LogUtils.d(this, "onEventAsync");
        appendText("onEventAsync receive msg: " + event);
    }

    // Receive Event
    public void onEventMainThread(String event) {
        LogUtils.d(this, "onEventMainThread");
        appendText("onEventMainThread receive msg: " + event);
    }

    private void appendText(String newText) {
        String threadInfo = ", current thread info: id is" + Thread.currentThread().getId() + ", content is "
                + Thread.currentThread().toString() + "}";
        receivedEventTV.setText(new StringBuilder(receivedEventTV.getText()).append("\r\n\r\n").append(newText)
                .append(threadInfo));
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
