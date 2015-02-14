package com.ffish.dlapkdemo;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ffish.dlapkplugininterface.HostInterface;
import com.ffish.dlapkplugininterface.InterfaceManager;
import com.ryg.dynamicload.internal.DLIntent;
import com.ryg.dynamicload.internal.DLPluginManager;
import com.ryg.utils.DLUtils;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener, HostInterface{

    private ArrayList<ApkItem> mApkItems = new ArrayList<ApkItem>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        GridView gridView = (GridView)findViewById(R.id.gridView);
        if (mApkItems.size() == 0){
            gridView.setVisibility(View.GONE);
            TextView textView = (TextView)findViewById(R.id.noApkTip);
            textView.setText("SDCard的/RootApkPath路径下无Apk文件");
            textView.setVisibility(View.VISIBLE);
        } else {
            gridView.setAdapter(new ApkItemAdapter());
            gridView.setOnItemClickListener(this);
        }

        // 向插件提供宿主的服务
        InterfaceManager.getInstance().setHostInterface(this);
    }

    private void initData(){
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            DLUtils.showDialog(this, "提示", "sd卡未挂载");
            return;
        }

        String rootApkPath = Environment.getExternalStorageDirectory() + "/RootApkPath";
        File file = new File(rootApkPath);
        if (!file.exists() || !file.isDirectory()){
            boolean success = file.mkdir();
            if (!success){
                DLUtils.showDialog(this, "提示", "apk根目录创建失败");
                return;
            }
        }
        File[] apks = file.listFiles();
        for (File apk:apks){
            ApkItem item = new ApkItem();
            PackageInfo packageInfo = DLUtils.getPackageInfo(this, apk.getAbsolutePath());
            if (packageInfo.activities != null && packageInfo.activities.length > 0){
                item.launchActivityName = packageInfo.activities[0].name;
            }
            item.packageInfo = packageInfo;
            item.apkPath = apk.getAbsolutePath();
            mApkItems.add(item);
            DLPluginManager.getInstance(this).loadApk(item.apkPath);
        }

    }

    private class ApkItem{
        public String apkPath;
        public PackageInfo packageInfo;
        public String launchActivityName;
    }

    private class ApkItemAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return mApkItems.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                ImageView imageView = new ImageView(parent.getContext());
                imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                TextView textView = new TextView(parent.getContext());

                convertView = textView;
            }

            ApkItem item = (ApkItem)getItem(position);
//            ((ImageView)convertView).setImageDrawable(DLUtils.getAppIcon(parent.getContext(), item.apkPath));
            ((TextView)convertView).setCompoundDrawablesWithIntrinsicBounds(null, DLUtils.getAppIcon(parent.getContext(), item.apkPath), null, null);
            PackageManager pm = getApplicationContext().getPackageManager();
            ((TextView) convertView).setText(pm.getApplicationLabel(item.packageInfo.applicationInfo));
            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return mApkItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ApkItem item = mApkItems.get(position);
        DLPluginManager.getInstance(this).startPluginActivity(this, new DLIntent(item.packageInfo.packageName, item.launchActivityName));
    }

    @Override
    public void methodInHost() {
        Toast.makeText(this, "这里是宿主程序，您的访问请求已收到，正在处理...", Toast.LENGTH_SHORT).show();
    }
}
