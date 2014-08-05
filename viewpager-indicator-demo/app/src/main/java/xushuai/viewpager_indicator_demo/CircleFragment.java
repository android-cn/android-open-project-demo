package xushuai.viewpager_indicator_demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public final class CircleFragment extends Fragment {
    private static final String KEY_CONTENT = "TestFragment:Content";
    private int mCurDrawableResId ;

    public static CircleFragment newInstance(int resId) {
        CircleFragment fragment = new CircleFragment();
        fragment.mCurDrawableResId = resId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
            mCurDrawableResId = savedInstanceState.getInt(KEY_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ImageView iv = new ImageView(getActivity());
        iv.setImageResource(mCurDrawableResId);
        return iv;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_CONTENT, mCurDrawableResId);
    }
}
