package com.baoyz.butterknife.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 
 * @author baoyz
 * 
 */
public class SimpleActivity extends Activity {

	@InjectView(R.id.title)
	TextView mTitle; // 将R.id.title注入给该变量

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simple);

		// 开始注入
		ButterKnife.inject(this);
	}

	// 注入R.id.sayHello的点击事件
	@OnClick(R.id.sayhello)
	public void sayhello(Button bt) {
		mTitle.setText(bt.getText());
	}

	// 注入R.id.checkbox的选择状态改变事件
	@OnCheckedChanged(R.id.checkbox)
	public void checkbox(CheckBox cb, boolean checked) {
		cb.setText(checked ? "已选中" : "未选中");
	}

	// 注入R.id.edittext的文字改变事件
	@OnTextChanged(R.id.edittext)
	public void touchme(CharSequence s, int start, int before, int count) {
		mTitle.setText(s);
	}
}
