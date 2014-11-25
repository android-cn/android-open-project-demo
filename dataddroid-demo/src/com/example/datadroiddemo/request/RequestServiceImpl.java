package com.example.datadroiddemo.request;

import com.example.datadroiddemo.ops.CityList2Operation;
import com.foxykeep.datadroid.service.RequestService;

public final class RequestServiceImpl extends RequestService {

	@Override
	protected int getMaximumNumberOfThreads() {
		return 3;
	}

	@Override
	public Operation getOperationForType(int requestType) {
		switch (requestType) {
		case RequestFactory.REQUEST_TYPE_CITY_LIST:
			return new CityList2Operation();

		}
		return null;
	}
}
