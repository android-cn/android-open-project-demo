package com.promeg.learn.otto.demo.activity;

import com.promeg.learn.otto.demo.R;
import com.promeg.learn.otto.demo.base.BaseActivity;
import com.squareup.otto.Subscribe;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import model.event.GetInfoDoneEvent;


public class TestProduceActivity extends BaseActivity {
    @InjectView(R.id.tv_info)
    TextView mInfoTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testproduce_layout);
        ButterKnife.inject(this);

    }

    @Subscribe
    public void onGetInfoDone(GetInfoDoneEvent event){
        if(event.isSucess){
            mInfoTv.append(event.ticker.toString());
            mInfoTv.append("\n");
        }else if(event.netError != null){
            mInfoTv.setText(event.netError.toString());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
