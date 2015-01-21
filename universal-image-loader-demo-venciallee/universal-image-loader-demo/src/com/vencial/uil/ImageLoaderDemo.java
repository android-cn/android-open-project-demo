package com.vencial.uil;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class ImageLoaderDemo {

    private static ImageLoaderConfiguration configuration = null;

    private static ImageTokenCallback imageTokenCallback = new ImageTokenCallback() {
        @Override
        public String getToken(String url) {
            return ImageUtils.getToken(url, AppModel.getInstance().getImageKey());
        }};

    static {
        Context context = Xiaoenai.getInstance().getApplicationContext();
        File imageCacheDir = StorageUtils.getIndividualCacheDirectory(context);
        int cacheSize = Math.round((1.0f / 6) * getMemoryClass(context) * 1024 * 1024);
        if (cacheSize < 2 * 1024 * 1024) {
            cacheSize = 2 * 1024 * 1024;//最小值是2M
        } else {
            cacheSize = 16 * 1024 * 1024;//最大值为16M
        }
        configuration = new ImageLoaderConfiguration.Builder(Xiaoenai.getInstance().getApplicationContext())
                .threadPoolSize(3)
                .threadPriority(Thread.MIN_PRIORITY)
                .discCache(new UnlimitedDiscCache(imageCacheDir))
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .memoryCache(new UsingFreqLimitedMemoryCache(cacheSize))
                .denyCacheImageMultipleSizesInMemory()
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();
        ImageLoader.getInstance().init(configuration, imageTokenCallback);
    }

    /**
     * 防盗逻辑，具体实现就如下方法即可。
     * 此token在BaseImageDownloader的createConnection方法
     * 放入Cookie里.
     *
     * @param url
     * @param sigKey
     * @return
     */
    public static String getToken(String url,String sigKey) {

    }


    public static void showImageWithUrl(ImageView imageView, String url, Boolean resetViewBeforeLoading,ImageLoadingListener listener) {
        DisplayImageOptions options;
        options = new DisplayImageOptions.Builder()
                .cacheOnDisc(true)
                .resetViewBeforeLoading(resetViewBeforeLoading)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        ImageLoader.getInstance().displayImage(url, imageView, options,listener);
    }

}
