package com.wmmeng.quickadapterdemo;

import android.util.Log;
import android.widget.BaseAdapter;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PicassoFragment extends BaseListViewFragment {
    String[] images = {"http://ent.iyaxin.com/attachement/jpg/site2/20110805/0019667873730fa5f54551.jpg",
                        "http://ent.iyaxin.com/attachement/jpg/site2/20110805/0019667873730fa5f68753.jpg",
                        "http://pic1.nipic.com/2008-12-02/200812210427444_2.jpg",
                        "http://www.gog.com.cn/pic/0/11/35/11/11351128_680557.jpg",
                        "http://www.gog.com.cn/pic/0/11/35/11/11351129_248834.jpg",
                        "http://www.gog.com.cn/pic/0/11/35/11/11351131_777144.jpg",
                        };
    ArrayList<String> data;
    BaseAdapter adapter;
	@Override
	protected BaseAdapter getAdapter() {
        adapter = new QuickAdapter<String>(getActivity(), R.layout.item_picasso_image, data) {
            @Override
            protected void convert(BaseAdapterHelper helper, String item) {
                Log.i("wmm", item);
                helper.setImageBuilder(R.id.iv_picasso_image, Picasso.with(getActivity()).load(item));
            }
        };
		return adapter;
	}

	@Override
	public void initData() {
		data = new ArrayList<>();
        for (int i = 0;i<images.length;i++){
            data.add(images[i]);
        }
	}

}
