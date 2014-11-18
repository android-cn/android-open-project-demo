package cn.trinea.android.demo.eventbus;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.trinea.android.demo.eventbus.util.TextUtils;
import de.greenrobot.event.EventBus;

public class SendSelfDefinedEventActivity extends BaseActivity {

    private EditText eventNameET;
    private EditText eventDescET;
    private Button   sendBtn;
    private TextView receivedEventTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_send_defined_event);

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
    public void onEvent(SelfDefinedEvent event) {
        receivedEventTV.setText("name is:" + event.getName() + ", desc is: " + event.getDesc());
    }

    private class SelfDefinedEvent {
        private CharSequence name;
        private CharSequence desc;

        public SelfDefinedEvent(CharSequence name, CharSequence desc) {
            this.name = name;
            this.desc = desc;
        }

        public CharSequence getName() {
            return name;
        }

        public CharSequence getDesc() {
            return desc;
        }
    }

    private void initView() {
        eventNameET = (EditText)findViewById(R.id.event_content_name);
        eventDescET = (EditText)findViewById(R.id.event_content_desc);
        receivedEventTV = (TextView)findViewById(R.id.receive_event);
        sendBtn = (Button)findViewById(R.id.send_event);
        sendBtn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(
                        new SelfDefinedEvent(TextUtils.getHintIfTextIsNull(eventNameET), TextUtils
                                .getHintIfTextIsNull(eventDescET)));
            }
        });
    }
}
