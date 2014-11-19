package me.drakeet.androidviewhoverdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.androidviewhover.BlurLayout;

import butterknife.InjectView;

/**
 * Created by drakeet on 11/19/14.
 */
public class LayoutFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public static BlurLayout sBlurLayout;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static LayoutFragment newInstance(int sectionNumber) {
        LayoutFragment fragment = new LayoutFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public LayoutFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initBlurLayout(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER)
        );
    }

    private void initBlurLayout(View context) {
        BlurLayout.setGlobalDefaultDuration(450);
        sBlurLayout = (BlurLayout) context.findViewById(R.id.blur_layout);
        View hover = LayoutInflater.from(getActivity()).inflate(R.layout.hover_main, null);

        hover.findViewById(R.id.hover_menu_ershou).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        YoYo.with(Techniques.Tada)
                                .duration(550)
                                .playOn(v);
                        showMessage("0");
                    }
                }
        );
        hover.findViewById(R.id.hover_menu_lvyou).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        YoYo.with(Techniques.Swing)
                                .duration(550)
                                .playOn(v);
                        showMessage("1");
                    }
                }
        );
        hover.findViewById(R.id.hover_menu_wenda).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        YoYo.with(Techniques.Bounce)
                                .duration(550)
                                .playOn(v);
                        showMessage("2");
                    }
                }
        );
        hover.findViewById(R.id.hover_menu_tantian).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        YoYo.with(Techniques.Tada)
                                .duration(550)
                                .playOn(v);
                        showMessage("3");
                    }
                }
        );
        sBlurLayout.setHoverView(hover);
        sBlurLayout.setBlurDuration(550);

        sBlurLayout.addChildAppearAnimator(hover, R.id.hover_menu_ershou, Techniques.FlipInX);
        sBlurLayout.addChildAppearAnimator(hover, R.id.hover_menu_lvyou, Techniques.FlipInX);
        sBlurLayout.addChildAppearAnimator(hover, R.id.hover_menu_wenda, Techniques.FlipInX);
        sBlurLayout.addChildAppearAnimator(hover, R.id.hover_menu_tantian, Techniques.FlipInX);

        sBlurLayout.addChildDisappearAnimator(hover, R.id.hover_menu_ershou, Techniques.FlipOutX);
        sBlurLayout.addChildDisappearAnimator(hover, R.id.hover_menu_lvyou, Techniques.FlipOutX);
        sBlurLayout.addChildDisappearAnimator(hover, R.id.hover_menu_wenda, Techniques.FlipOutX);
        sBlurLayout.addChildDisappearAnimator(hover, R.id.hover_menu_tantian, Techniques.FlipOutX);

        sBlurLayout.addChildAppearAnimator(hover, R.id.description, Techniques.FadeInUp);
        sBlurLayout.addChildDisappearAnimator(hover, R.id.description, Techniques.FadeOutDown);
    }

    private void showMessage(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_overflow:
                sBlurLayout.toggleHover();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
