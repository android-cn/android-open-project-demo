// Generated code from Butter Knife. Do not modify!
package com.baoyz.butterknife.demo;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class SimpleActivity$$ViewInjector {
  public static void inject(Finder finder, final com.baoyz.butterknife.demo.SimpleActivity target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131099651, "field 'mTitle'");
    target.mTitle = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131099653, "method 'checkbox'");
    ((android.widget.CompoundButton) view).setOnCheckedChangeListener(
      new android.widget.CompoundButton.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(
          android.widget.CompoundButton p0,
          boolean p1
        ) {
          target.checkbox((android.widget.CheckBox) p0, p1);
        }
      });
    view = finder.findRequiredView(source, 2131099652, "method 'sayhello'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.sayhello((android.widget.Button) p0);
        }
      });
    view = finder.findRequiredView(source, 2131099654, "method 'touchme'");
    ((android.widget.TextView) view).addTextChangedListener(
      new android.text.TextWatcher() {
        @Override public void onTextChanged(
          java.lang.CharSequence p0,
          int p1,
          int p2,
          int p3
        ) {
          target.touchme(p0, p1, p2, p3);
        }
        @Override public void beforeTextChanged(
          java.lang.CharSequence p0,
          int p1,
          int p2,
          int p3
        ) {
          
        }
        @Override public void afterTextChanged(
          android.text.Editable p0
        ) {
          
        }
      });
  }

  public static void reset(com.baoyz.butterknife.demo.SimpleActivity target) {
    target.mTitle = null;
  }
}
