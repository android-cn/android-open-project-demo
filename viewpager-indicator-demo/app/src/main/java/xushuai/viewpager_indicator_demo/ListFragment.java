package xushuai.viewpager_indicator_demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public final class ListFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";

    public static ListFragment newInstance(String content) {
        ListFragment fragment = new ListFragment();
        fragment.mContent = content;
        return fragment;
    }

    private String mContent = "???";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mContent = savedInstanceState.getString(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       ListView lv = (ListView) inflater.inflate(R.layout.layout_list_view, null);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_expandable_list_item_1,
                getData());
        lv.setAdapter(adapter);
        return lv;
    }

    private ArrayList<String> getData()
    {
        ArrayList<String> list = new ArrayList<String>();
        list.add("120平米的房子");
        list.add("温柔贤惠的老婆");
        list.add("健康的身体");
        list.add("喜欢的事业");
        return list;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CONTENT, mContent);
    }
}
