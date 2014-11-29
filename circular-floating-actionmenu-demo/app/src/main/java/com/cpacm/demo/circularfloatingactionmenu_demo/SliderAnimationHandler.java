package com.cpacm.demo.circularfloatingactionmenu_demo;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.Point;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;

import com.cpacm.library.FloatingActionMenu;
import com.cpacm.library.animation.MenuAnimationHandler;

/**
 * Created by cpacm on 2014/11/24.
 */
public class SliderAnimationHandler extends MenuAnimationHandler {
    //总体时间
    protected final static int DURATION = 600;
    //每个选项之间的延时
    protected static final int LAG_BETWEEN_ITEMS = 100;
    //距离的偏移量
    protected static int DIST = 200;

    private boolean animating;


    public SliderAnimationHandler() {
        setAnimating(false);
    }

    @Override
    public void setMenu(FloatingActionMenu menu) {
        super.setMenu(menu);
    }

    @Override
    public void animateMenuOpening(Point center) {
        super.animateMenuOpening(center);
        //动画中
        setAnimating(true);
        Animator lastAnimation = null;
        for (int i = 0; i < menu.getSubActionItems().size(); i++) {
            //子项设为不透明
            menu.getSubActionItems().get(i).view.setAlpha(0);
            //子项缩放为0
            menu.getSubActionItems().get(i).view.setScaleX(0);
            menu.getSubActionItems().get(i).view.setScaleY(0);
            //初始位置统一上移
            menu.getSubActionItems().get(i).view.setTranslationY(-DIST);
            //menu.getSubActionItems().get(i).view.setTranslationY(menu.getSubActionItems().get(i).y - center.y + menu.getSubActionItems().get(i).height / 2+DIST);
            /*FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) menu.getSubActionItems().get(i).view.getLayoutParams();
            params.setMargins(menu.getSubActionItems().get(i).x, menu.getSubActionItems().get(i).y + DIST_Y, 0, 0);
            menu.getSubActionItems().get(i).view.setLayoutParams(params);*/

            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X,menu.getSubActionItems().get(i).x - center.x + menu.getSubActionItems().get(i).width / 2);
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, menu.getSubActionItems().get(i).y - center.y + menu.getSubActionItems().get(i).height / 2);
            PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 730);
            PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1,2,1);
            PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1,2,1);
            PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 1);

            final ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(menu.getSubActionItems().get(i).view, pvhX, pvhY, pvhR, pvhsX, pvhsY, pvhA);
            animation.setDuration(DURATION);
            animation.setInterpolator(new OvershootInterpolator(0.9f));
            animation.addListener(new SubActionItemAnimationListener(menu.getSubActionItems().get(i), ActionType.OPENING));

            if(i == 0) {
                lastAnimation = animation;
            }
            animation.setStartDelay(Math.abs(menu.getSubActionItems().size()/2-i) * LAG_BETWEEN_ITEMS);
            animation.start();
        }
        if(lastAnimation != null) {
            lastAnimation.addListener(new LastAnimationListener());
        }
    }

    @Override
    public void animateMenuClosing(Point center) {
        super.animateMenuClosing(center);
        setAnimating(true);

        Animator lastAnimation = null;
        for (int i = 0; i <menu.getSubActionItems().size(); i++) {
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.TRANSLATION_X, -(menu.getSubActionItems().get(i).x - center.x + menu.getSubActionItems().get(i).width / 2));
            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, -(menu.getSubActionItems().get(i).y - center.y + menu.getSubActionItems().get(i).height / 2)-DIST);
            PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, -730);
            PropertyValuesHolder pvhsX = PropertyValuesHolder.ofFloat(View.SCALE_X,2, 1,0);
            PropertyValuesHolder pvhsY = PropertyValuesHolder.ofFloat(View.SCALE_Y,2, 1,0);
            PropertyValuesHolder pvhA = PropertyValuesHolder.ofFloat(View.ALPHA, 0);

            final ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(menu.getSubActionItems().get(i).view, pvhX, pvhY, pvhR, pvhsX, pvhsY, pvhA);
            animation.setDuration(DURATION);
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
            animation.addListener(new SubActionItemAnimationListener(menu.getSubActionItems().get(i), ActionType.CLOSING));

            if(i == 0) {
                lastAnimation = animation;
            }

            animation.setStartDelay((i) * LAG_BETWEEN_ITEMS);
            animation.start();
        }
        if(lastAnimation != null) {
            lastAnimation.addListener(new LastAnimationListener());
        }
    }

    @Override
    public boolean isAnimating() {
        return animating;
    }

    @Override
    protected void setAnimating(boolean animating) {
        this.animating = animating;
    }

    protected class SubActionItemAnimationListener implements Animator.AnimatorListener {

        private FloatingActionMenu.Item subActionItem;
        private ActionType actionType;

        public SubActionItemAnimationListener(FloatingActionMenu.Item subActionItem, ActionType actionType) {
            this.subActionItem = subActionItem;
            this.actionType = actionType;
        }

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            restoreSubActionViewAfterAnimation(subActionItem, actionType);
        }

        @Override
        public void onAnimationCancel(Animator animation) {
            restoreSubActionViewAfterAnimation(subActionItem, actionType);
        }

        @Override public void onAnimationRepeat(Animator animation) {}
    }
}
