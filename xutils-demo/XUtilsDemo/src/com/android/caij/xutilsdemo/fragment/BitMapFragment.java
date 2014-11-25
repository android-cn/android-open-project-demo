package com.android.caij.xutilsdemo.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.caij.xutilsdemo.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

public class BitMapFragment extends Fragment{
	
	/**
	 * 用的官方demo的图片地址
	 */
	private String[] imgSites = {
            "http://image.baidu.com/",
            "http://www.22mm.cc/",
            "http://www.moko.cc/",
            "http://eladies.sina.com.cn/photo/",
            "http://www.youzi4.com/"
    };
	
	@ViewInject(R.id.lv_image)
	private ListView listView;

	private MyAdapter adapter;
	
	private BitmapUtils bitmapUtils;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bitmap_fragment_view, container,
				false);
		ViewUtils.inject(this, view);
		adapter = new MyAdapter(getActivity());
		listView.setAdapter(adapter);
		
		bitmapUtils = new BitmapUtils(getActivity());
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.down);
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        
        loadImgList("http://www.22mm.cc/");
		return view;
	}
	
	
	private class MyAdapter extends BaseAdapter {
		
		private List<String> imgSrcList;
		private Context context;

		public MyAdapter(Context context) {
			super();
			this.imgSrcList = new ArrayList<String>();
			this.context = context;
		}

		@Override
		public int getCount() {
			return imgSrcList.size();
		}

		@Override
		public Object getItem(int position) {
			return imgSrcList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(context, R.layout.list_image_item, null);
				holder = new ViewHolder();
				holder.imageView = (ImageView) convertView.findViewById(R.id.img_item);
				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			bitmapUtils.display(holder.imageView, imgSrcList.get(position));
			
			return convertView;
		}

		public void addSrc(List<String> imgSrcList) {
			this.imgSrcList = imgSrcList;
		}
		
	}
	
	private class ViewHolder {
		public ImageView imageView;
	}
	
	/**
	 * @param url
	 */
	private void loadImgList(String url) {
        new HttpUtils().send(HttpRequest.HttpMethod.GET, url,
                new RequestCallBack<String>() {
                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                    	adapter.addSrc(getImgSrcList(responseInfo.result));
                    	adapter.notifyDataSetChanged();//通知listview更新数据
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                    }
                });
    }
	
	 /**
     * 得到网页中图片的地址
     */
    public static List<String> getImgSrcList(String htmlStr) {
        List<String> pics = new ArrayList<String>();

        String regEx_img = "<img.*?src=\"http://(.*?).jpg\""; // 图片链接地址
        Pattern p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
        Matcher m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            String src = m_image.group(1);
            if (src.length() < 100) {
                pics.add("http://" + src + ".jpg");
            }
        }
        return pics;
    }
}
