// Generated code from Butter Knife. Do not modify!
package com.baoyz.butterknife.demo;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class DemoActivity$$ViewInjector {
  public static void inject(Finder finder, final com.baoyz.butterknife.demo.DemoActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099648, "method 'simple'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.simple(p0);
        }
      });
  }

  public static void reset(com.baoyz.butterknife.demo.DemoActivity target) {
  }
}
