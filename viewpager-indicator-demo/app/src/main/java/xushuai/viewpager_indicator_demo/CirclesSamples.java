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
public class CirclesSamples extends Activity implements AdapterView.OnItemClickListener{
    private ListView mSampleLv;
    private String[] adapterData = new String[] { "default", "initialPage","snap", "Styled(via layout)","Styled(via methods)","Styled(via theme)",
            "Withlistener" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSampleLv = (ListView) findViewById(R.id.lv_sample);
        mSampleLv.setAdapter(new ArrayAdapter<String>(CirclesSamples.this,
                android.R.layout.simple_list_item_1, adapterData));
        mSampleLv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                redictTo(SampleCirclesDefault.class);
                break;
            case 1:
                redictTo(SampleCirclesInitialPage.class);
                break;
            case 2:
                redictTo(SampleCirclesSnap.class);

                break;
            case 3:
                redictTo(SampleCirclesStyledLayout.class);
                break;
            case 4:

                redictTo(SampleCirclesStyledMethods.class);
                break;
            case 5:
                redictTo(SampleCirclesStyledTheme.class);
                break;
            case 6:
                redictTo(SampleCirclesWithListener.class);
                break;
        }
    }

    private void redictTo(Class clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
}
