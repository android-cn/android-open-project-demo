package cn.ztimc.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.cn.ztimc.demo.R;

public class MainActivity extends Activity {

    private String[] datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        PullToZoomListView list = (PullToZoomListView) findViewById(R.id.list);
        datas = new String[] { "aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii", "jjj kkk", "lll", "mmm" };
        list.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, datas));
        list.getHeaderView().setImageResource(R.drawable.demo);
        list.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);

    }

}
