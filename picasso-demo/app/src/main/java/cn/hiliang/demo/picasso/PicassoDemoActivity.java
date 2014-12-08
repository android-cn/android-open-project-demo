package cn.hiliang.demo.picasso;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.GridView;


public class PicassoDemoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picasso_demo);



        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, GridFragment.newInstance())
                    .commit();
        }
    }

    public static class GridFragment extends Fragment {

        public static GridFragment newInstance() {
            return new GridFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final PicassoDemoActivity activity = (PicassoDemoActivity) getActivity();
            final DemoAdapter adapter = new DemoAdapter(activity);

            GridView rootView = (GridView) inflater.inflate(R.layout.grid_detail_list, container, false);
            rootView.setAdapter(adapter);
            rootView.setOnScrollListener(new DemoScrollListener(activity));
            return rootView;
        }
    }
}
