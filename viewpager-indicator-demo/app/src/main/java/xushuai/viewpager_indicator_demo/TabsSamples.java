package xushuai.viewpager_indicator_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by xushuai on 2014/11/27.
 */
public class TabsSamples extends Activity implements AdapterView.OnItemClickListener{
    private ListView mSampleLv;
    private String[] adapterData = new String[] { "default", "styled","with icons"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSampleLv = (ListView) findViewById(R.id.lv_sample);
        mSampleLv.setAdapter(new ArrayAdapter<String>(TabsSamples.this,
                android.R.layout.simple_list_item_1, adapterData));
        mSampleLv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                redictTo(SampleTabsDefault.class);
                break;
            case 1:
                redictTo(SampleTabsStyled.class);
                break;
            case 2:
                redictTo(SampleTabsWithIcons.class);
                break;

        }
    }

    private void redictTo(Class clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
}
