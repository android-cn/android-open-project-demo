package com.example.datadroiddemo.ui.citylist;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dataddroiddemo.R;
import com.example.datadroiddemo.base.RequestActivity;
import com.example.datadroiddemo.model.Cities;
import com.example.datadroiddemo.model.CityInfo;
import com.example.datadroiddemo.request.RequestFactory;
import com.foxykeep.datadroid.requestmanager.Request;

public class MainActivity extends RequestActivity {

	private CityListAdapter mListAdapter;
	private LayoutInflater mInflater;
	private ListView mListView;

	@Override
	public void initAllMembers(Bundle savedInstanceState) {
		super.initAllMembers(savedInstanceState);
		mListAdapter = new CityListAdapter(this);
		mInflater = getLayoutInflater();
		mListView = (ListView) findViewById(R.id.lv);
		mListView.setAdapter(mListAdapter);
	}

	@Override
	public int getContentViewId() {
		return R.layout.activity_main;
	}

	@Override
	public View getLoadingIndicatorView() {
		return findViewById(R.id.pb);
	}

	@Override
	public View getErrorIndicatorLayout() {
		return findViewById(R.id.ll_error_container);
	}

	@Override
	public TextView getErrorMsgTextView() {
		return (TextView) findViewById(R.id.tv_error_content);
	}

	@Override
	public Request getInitialRequest() {
		return RequestFactory.getCityListRequest();
	}

	@Override
	public void onRequestSucess(Request request, Bundle bundle) {
		Cities cityList = bundle
				.getParcelable(RequestFactory.BUNDLE_EXTRA_RESULT);

		if (cityList == null || cityList.cities == null
				|| cityList.cities.size() <= 0) {
			handleException(EXCEPTION_TYPE_EMPTY_RESULT);
			return;
		}
		mListAdapter.setNotifyOnChange(false);
		for (CityInfo city : cityList.cities) {
			mListAdapter.add(city);
		}
		mListAdapter.notifyDataSetChanged();
	}

	class ViewHolder {
		private TextView mTextViewName;
		private TextView mTextViewPostalCode;
		private TextView mTextViewState;
		private TextView mTextViewCountry;

		public ViewHolder(View view) {
			mTextViewName = (TextView) view.findViewById(R.id.tv_name);
			mTextViewPostalCode = (TextView) view
					.findViewById(R.id.tv_postal_code);
			mTextViewState = (TextView) view.findViewById(R.id.tv_state);
			mTextViewCountry = (TextView) view.findViewById(R.id.tv_country);
		}

		public void populateViews(CityInfo city) {
			mTextViewName.setText(city.name);
			mTextViewPostalCode.setText(city.postalCode);
			mTextViewState.setText(city.state);
			mTextViewCountry.setText(city.country);
		}
	}

	class CityListAdapter extends ArrayAdapter<CityInfo> {

		public CityListAdapter(Context context) {
			super(context, 0);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.city_list_item, null);
				viewHolder = new ViewHolder(convertView);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.populateViews(getItem(position));

			return convertView;
		}
	}
}
