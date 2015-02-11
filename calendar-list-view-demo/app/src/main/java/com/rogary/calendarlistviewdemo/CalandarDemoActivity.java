package com.rogary.calendarlistviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andexert.calendarlistview.library.DatePickerController;
import com.andexert.calendarlistview.library.DayPickerView;
import com.andexert.calendarlistview.library.SimpleMonthAdapter;


public class CalandarDemoActivity extends Activity implements DatePickerController {

	private TextView selectedDaysText; //显示已选择的范围
	private DayPickerView dayPickerView; //日期选择View
	private TextView selectedText; //显示点击的日期

	@Override
	protected void onCreate ( Bundle savedInstanceState ) {
		super.onCreate ( savedInstanceState );
		setContentView ( R.layout.activity_calandar_demo );
		initView ();
	}


	private void initView () {
		selectedText = ( TextView ) findViewById ( R.id.activity_text );
		selectedDaysText = ( TextView ) findViewById ( R.id.activity_selectd_text );
		dayPickerView = ( DayPickerView ) findViewById ( R.id.activity_daypicker );
		dayPickerView.setController ( this );
	}

	@Override
	public int getMaxYear () {
		return 2017;
	}

	@Override
	public void onDayOfMonthSelected ( int year, int month, int day ) {
		selectedText.setText ( getResources ().getString ( R.string.selected ) + year + getResources ().getString ( R.string.year ) + month + getResources ().getString ( R.string.month ) + day + getResources ().getString ( R.string.day ) );
		selectedDaysText.setText("");
	}

	@Override
	public void onDateRangeSelected ( SimpleMonthAdapter.SelectedDays< SimpleMonthAdapter.CalendarDay > selectedDays ) {
		selectedDaysText.setText ( getResources ().getString ( R.string.selected ) + selectedDays.getFirst ().toString () + " --> " + selectedDays.getLast ().toString () );
	}
}
