package xushuai.viewpager_indicator_demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener{
    private ListView mSampleLv;
    private String[] adapterData = new String[] { "Circles", "lines","Underlines", "Titles",
            "Icons","Tabs" };//"SampleTabsDefault",

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        mSampleLv = (ListView) findViewById(R.id.lv_sample);
        mSampleLv.setAdapter(new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, adapterData));
        mSampleLv.setOnItemClickListener(this);
	}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                redictTo(CirclesSamples.class);
                break;
            case 1:
                redictTo(SampleLinesDefault.class);
                break;
            case 2:
                redictTo(UnderlinesSamples.class);

                break;
            case 3:
                redictTo(TitlesSamples.class);
                break;
            case 4:
                redictTo(SampleIconsDefault.class);
                break;
            case 5:
                redictTo(TabsSamples.class);
                break;
        }
    }

    private void redictTo(Class clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }
}
