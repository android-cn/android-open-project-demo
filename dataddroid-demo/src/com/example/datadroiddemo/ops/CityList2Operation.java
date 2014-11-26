package com.example.datadroiddemo.ops;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;

import com.example.datadroiddemo.model.Cities;
import com.example.datadroiddemo.model.CityInfo;
import com.example.datadroiddemo.request.RequestFactory;
import com.example.datadroiddemo.wsconfig.WSConfig;
import com.foxykeep.datadroid.exception.ConnectionException;
import com.foxykeep.datadroid.exception.DataException;
import com.foxykeep.datadroid.network.NetworkConnection;
import com.foxykeep.datadroid.network.NetworkConnection.ConnectionResult;
import com.foxykeep.datadroid.requestmanager.Request;
import com.foxykeep.datadroid.service.RequestService.Operation;
import com.google.gson.Gson;

public final class CityList2Operation implements Operation {

	@Override
	public Bundle execute(Context context, Request request)
			throws ConnectionException, DataException {
		NetworkConnection networkConnection = new NetworkConnection(context,
				WSConfig.WS_CITY_LIST_URL);
		ConnectionResult result = networkConnection.execute();

		// Cities cities = new Gson().fromJson(result.body, Cities.class);
		Cities cities = new Cities();// TODO, demo the data
		cities.cities = new ArrayList<CityInfo>();
		for (int i = 0; i < 50; i++) {
			CityInfo cityInfo = new CityInfo();
			cityInfo.country = "usa";
			cityInfo.name = "usa";
			cityInfo.postalCode = "1234567890";
			cityInfo.state = "usa";
			cities.cities.add(cityInfo);
		}
		Bundle bundle = new Bundle();
		bundle.putParcelable(RequestFactory.BUNDLE_EXTRA_RESULT, cities);
		return bundle;
	}
}
