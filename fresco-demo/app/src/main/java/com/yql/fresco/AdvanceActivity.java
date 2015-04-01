package com.yql.fresco;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.facebook.imagepipeline.request.BaseRepeatedPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;


public class AdvanceActivity extends Activity {
    public static final String PROGRESSIVE_JPEG = "http://s.lowendshare.com/0/1357767852.513.bridge.jpg";
    private SimpleDraweeView simpleDraweeView;
    private GenericDraweeHierarchyBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFresco();
        setContentView(R.layout.activity_advance);
        initDraweeHierarchy();
        loadProgressvieJpeg();
    }

    private void initFresco() {
        //渐进式JPEG配置
//        ProgressiveJpegConfig progressiveJpegConfig = new SimpleProgressiveJpegConfig();
        ProgressiveJpegConfig progressiveJpegConfig = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int i) {
                return i + 2;
            }

            @Override
            public QualityInfo getQualityInfo(int i) {
                boolean isGoodEnough = (i >= 5);
                return ImmutableQualityInfo.of(i, isGoodEnough, false);
            }
        };
        /**
         *这里可以使用第三方网络请求库
         */
//        OkHttpClient okHttpClient = new OkHttpClient();
//        ImagePipelineConfig imagePipelineConfig = OkHttpImagePipelineConfigFactory.newBuilder(this, okHttpClient)
        //下面这个是Android默认的网络请求
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(progressiveJpegConfig)
                .build();
        Fresco.initialize(this, imagePipelineConfig);
    }

    private void initDraweeHierarchy() {
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.draweeview);
        simpleDraweeView.setAspectRatio(1f);
        builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder.setFadeDuration(100)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .setPlaceholderImage(getResources().getDrawable(R.color.placeholder))
                .setFailureImage(getResources().getDrawable(R.color.error))
                .setRetryImage(getResources().getDrawable(R.color.retrying))
                .build();
        simpleDraweeView.setHierarchy(hierarchy);
    }

    /**
     * 加载渐进式jpeg
     */
    private void loadProgressvieJpeg() {
        Uri uri = Uri.parse(PROGRESSIVE_JPEG);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                //这里设置渐进式jpeg开关，记得在fresco初始化时设置progressiveJpegConfig
                .setProgressiveRenderingEnabled(true)
//                .setResizeOptions(new ResizeOptions(width, height))在解码之前修改图片尺寸
//                .setAutoRotateEnabled(true)如果jpeg带有方向信息，则自动旋转图片
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setTapToRetryEnabled(true)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller);
    }

    /**
     * 多图加载，先显示低清图，再显示高清图
     */
    private void loadLow2HighImages() {
        Uri lowUri = Uri.parse("http://img4.imgtn.bdimg.com/it/u=2938912401,3712734065&fm=21&gp=0.jpg");
        Uri highUri = Uri.parse("http://www.33lc.com/article/UploadPic/2012-10/201210611525117694.jpg");
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(lowUri))
                .setImageRequest(ImageRequest.fromUri(highUri))
                .setTapToRetryEnabled(true)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller);
    }

    /**
     * 预加载缩略图，仅支持本地URI，并且是JPEG图片格式(要有EXIF缩略图)
     */
    private void loadThumbnailPreview() {
        Uri uri = Uri.parse("res://" + getResources().getResourcePackageName(R.drawable.loal_exif) + "/" + R.drawable.loal_exif);
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setLocalThumbnailPreviewsEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller);
    }

    /**
     * 优先显示队列(对本地图片进行复用)
     */
    private void loadFirstAvailableImg() {
        Uri uri0 = Uri.parse("res://" + getResources().getResourcePackageName(R.drawable.local_first_330_220) + "/" + R.drawable.local_first_330_220);
        Uri uri1 = Uri.parse("http://img.wallpapersking.com/d7/2012-8/2012081210374.jpg");
        ImageRequest imageRequest0 = ImageRequest.fromUri(uri0);
        ImageRequest imageRequest1 = ImageRequest.fromUri(uri1);
        ImageRequest[] imageRequests = {imageRequest0, imageRequest1};
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setFirstAvailableImageRequests(imageRequests)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller);
    }

    /**
     * 修改图片
     */
    private void loadModifyImg() {
        Uri uri = Uri.parse("res://" + getResources().getResourcePackageName(R.raw.local_img) + "/" + R.raw.local_img);
//        Postprocessor postprocessor = new Postprocessor() {
//            @Override
//            public void process(Bitmap bitmap) {
//                for (int x = 0; x < bitmap.getWidth(); x++) {
//                    for (int y = 0; y < bitmap.getHeight(); y+=50) {
//                        bitmap.setPixel(x, y, Color.RED);
//                    }
//                }
//            }
//
//            @Override
//            public String getName() {
//                return "modifyImg";
//            }
//        };
        MyPostProcessor postprocessor = new MyPostProcessor();
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setPostprocessor(postprocessor)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller);

        postprocessor.setColor(Color.YELLOW);
//        postprocessor.setColor(Color.RED);
    }

    /**
     * 多处理器
     */
    public class MyPostProcessor extends BaseRepeatedPostProcessor {
        int color = Color.TRANSPARENT;

        public void setColor(int color) {
            this.color = color;
            update();//记得要update
        }

        @Override
        public void process(Bitmap bitmap) {
            for (int x = 0; x < bitmap.getWidth(); x++) {
                for (int y = 0; y < bitmap.getHeight(); y += 50) {
                    bitmap.setPixel(x, y, color);
                }
            }
        }

        @Override
        public String getName() {
            return "MyPostProcessor";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_advance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_progressive_jpeg) {
            loadProgressvieJpeg();
            return true;
        } else if (id == R.id.action_low_to_high) {
            loadLow2HighImages();
            return true;
        } else if (id == R.id.action_thumb_preview) {
            loadThumbnailPreview();
            return true;
        } else if (id == R.id.action_first_availabel) {
            loadFirstAvailableImg();
            return true;
        } else if (id == R.id.action_modify_img) {
            loadModifyImg();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
