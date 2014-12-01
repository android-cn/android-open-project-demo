package com.android.caij.xutilsdemo.fragment;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.caij.xutilsdemo.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class DownFragment extends Fragment {

	@ViewInject(R.id.start)
	private Button start;
	@ViewInject(R.id.pb_seek)
	private ProgressBar progressBar;
	private HttpUtils http;
	private static final String URL = "http://apps.lidroid.com/apiv2/dl/0000000/com.lidroid.fileexplorer";
	private HttpHandler<File> handler;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.down_fragment_view, container,
				false);
		ViewUtils.inject(this, view);
		http = new HttpUtils();
		return view;
	}

	@OnClick(R.id.start)
	public void start(View view) {
		if (handler == null || handler.isCancelled()) {
			handler = http.download(URL, 
					Environment.getExternalStorageDirectory().getAbsolutePath() + "/test.apk", 
					true,  // 如果目标文件存在，接着未完成的部分继续下载。服务器不支持RANGE时将从新下载。
					false, // 如果从请求返回信息中获取到文件名，下载完成后自动重命名。
					new RequestCallBack<File>() {
	
						@Override
						public void onLoading(long total, long current,
								boolean isUploading) {
							super.onLoading(total, current, isUploading);
							progressBar.setMax((int) total);
							progressBar.setProgress((int) current);
						}
	
						@Override
						public void onSuccess(ResponseInfo<File> responseInfo) {
							Toast.makeText(getActivity(), "下载成功", Toast.LENGTH_SHORT).show();
							progressBar.setProgress(0);
							start.setText("开始");
						}
	
						@Override
						public void onFailure(HttpException error, String msg) {
							Toast.makeText(getActivity(), "下载失败" + msg, Toast.LENGTH_SHORT).show();
							start.setText("开始");
						}
					});
			start.setText("暂停");
		}
		else {
			handler.cancel();
			start.setText("开始");
		}
	}

}
