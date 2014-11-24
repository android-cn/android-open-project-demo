package com.grumoon.volleydemo.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.grumoon.volleydemo.R;
import com.grumoon.volleydemo.util.LruImageCache;
import com.grumoon.volleydemo.util.StringUtil;
import com.grumoon.volleydemo.util.VolleyUtil;

public class ImageLoaderAdapter extends ImageBaseAdapter{
	
	private ImageLoader imageLoader;
	

	public ImageLoaderAdapter(Context context, String[] imageUrlArray) {
		super(context, imageUrlArray);
		this.imageLoader=new ImageLoader(VolleyUtil.getQueue(context), new LruImageCache());
	}

	@Override
	int getItemLayout() {
		
		return R.layout.fr_image_request_list_item;
	}

	@Override
	void setImage(ImageView imageView, String imageUrl) {
		
		ImageContainer container;
		
		try {
			//如果当前ImageView上存在请求，先取消
			if(imageView.getTag()!=null)
			{
				container=(ImageContainer)imageView.getTag();
				container.cancelRequest();
			}
		} catch (Exception e) {

		}
		
		ImageListener listener=ImageLoader.getImageListener(imageView, R.drawable.ic_empty, R.drawable.ic_empty);
		
		container= imageLoader.get(StringUtil.preUrl(imageUrl),listener);
		
		//在ImageView上存储当前请求的Container，用于取消请求
		imageView.setTag(container);
		
	}

}
