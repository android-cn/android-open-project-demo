
package com.simple.animations;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.PropertyValuesHolder;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;
import com.nineoldandroids.util.Property;

public class MainActivity extends Activity implements OnClickListener {

    private static final String TAG = "MainActivity";

    private Button mMenuButton;
    private Button mItemButton1;
    private Button mItemButton2;
    private Button mItemButton3;
    private Button mItemButton4;
    private Button mItemButton5;
    private ImageView mImageView;
    private int mImageAlpha = 255;
    private boolean isMenuOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mMenuButton = (Button) findViewById(R.id.menu);
        mMenuButton.setOnClickListener(this);

        mItemButton1 = (Button) findViewById(R.id.item1);
        mItemButton1.setOnClickListener(this);

        mItemButton2 = (Button) findViewById(R.id.item2);
        mItemButton2.setOnClickListener(this);

        mItemButton3 = (Button) findViewById(R.id.item3);
        mItemButton3.setOnClickListener(this);

        mItemButton4 = (Button) findViewById(R.id.item4);
        mItemButton4.setOnClickListener(this);

        mItemButton5 = (Button) findViewById(R.id.item5);
        mItemButton5.setOnClickListener(this);

        mImageView = (ImageView) findViewById(R.id.my_imageview);
        findViewById(R.id.start_anim).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startSingleAnimation();
            }
        });

        findViewById(R.id.start_value).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startValueAnimator();
            }
        });

        findViewById(R.id.start_custom).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startPropertyAnimation();
            }
        });
    }

    private void startSingleAnimation() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mImageView, "scaleX", 2.0f);
        objectAnimator.setDuration(1000);
        // 执行完之后,逆向执行
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        // 重复两次
        objectAnimator.setRepeatCount(3);
        objectAnimator.start();
    }

    private void startValueAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(255, 60);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(1);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                Log.e(TAG, "### value = " + animator.getAnimatedValue());
                mImageView.setAlpha((Integer) animator.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    private void startPropertyAnimation() {

        // 自定义Property
        Property<MainActivity, Integer> customProperty = new Property<MainActivity, Integer>(
                Integer.class, "mImageAlpha") {

            @Override
            public Integer get(MainActivity arg0) {
                return mImageAlpha;
            }

            @Override
            public void set(MainActivity object, Integer value) {
                mImageAlpha = value;
                mImageView.setAlpha(value);
            }
        };

        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(this,
                PropertyValuesHolder.ofInt(customProperty, 255, 100));
        objectAnimator.setDuration(1000);
        objectAnimator.start();

    }

    @Override
    public void onClick(View v) {

        if (!isMenuOpen) {
            doAnimateOpen(mItemButton1, 0, 5, 300);
            doAnimateOpen(mItemButton2, 1, 5, 300);
            doAnimateOpen(mItemButton3, 2, 5, 300);
            doAnimateOpen(mItemButton4, 3, 5, 300);
            doAnimateOpen(mItemButton5, 4, 5, 300);
            isMenuOpen = true;
        } else {
            doAnimateClose(mItemButton1, 0, 5, 300);
            doAnimateClose(mItemButton2, 1, 5, 300);
            doAnimateClose(mItemButton3, 2, 5, 300);
            doAnimateClose(mItemButton4, 3, 5, 300);
            doAnimateClose(mItemButton5, 4, 5, 300);
            isMenuOpen = false;
        }
    }

    /**
     * 打开菜单的动画
     * 
     * @param view 执行动画的view
     * @param index view在动画序列中的顺序
     * @param total 动画序列的个数
     * @param radius 动画半径
     */
    private void doAnimateOpen(View view, int index, int total, int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.PI * index / ((total - 1) * 2);
        int translationX = (int) (radius * Math.cos(degree));
        int translationY = (int) (radius * Math.sin(degree));
        Log.d(TAG, String.format("degree=%f, translationX=%d, translationY=%d",
                degree, translationX, translationY));
        AnimatorSet set = new AnimatorSet();
        // 包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, translationX),
                ObjectAnimator.ofFloat(view, "translationY", 0, translationY),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1));
        // 动画周期为500ms
        set.setDuration(1 * 500).start();
    }

    /**
     * 关闭菜单的动画
     * 
     * @param view 执行动画的view
     * @param index view在动画序列中的顺序
     * @param total 动画序列的个数
     * @param radius 动画半径
     */
    private void doAnimateClose(final View view, int index, int total,
            int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        double degree = Math.PI * index / ((total - 1) * 2);
        int translationX = (int) (radius * Math.cos(degree));
        int translationY = (int) (radius * Math.sin(degree));
        Log.d(TAG, String.format("degree=%f, translationX=%d, translationY=%d",
                degree, translationX, translationY));
        AnimatorSet set = new AnimatorSet();
        // 包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(view, "translationY", translationY, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));
        // 为动画加上事件监听，当动画结束的时候，我们把当前view隐藏
        set.addListener(new AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }
        });

        set.setDuration(1 * 500).start();
    }
}
