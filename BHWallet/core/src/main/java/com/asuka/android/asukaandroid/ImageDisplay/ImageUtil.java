package com.asuka.android.asukaandroid.ImageDisplay;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.asuka.android.asukaandroid.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

public class ImageUtil {
    public static DisplayImageOptions headOpton;
    public static DisplayImageOptions options;
    private static ImageLoadingListener animateFirstListener;
    private static ImageLoader imageLoader;

    static {
        animateFirstListener = new AnimateFirstDisplayListener();
        imageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_chat_def_pic)
                .showImageForEmptyUri(R.drawable.ic_chat_def_pic)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.ic_chat_def_pic)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(5))  // 设置成圆角图片
                .build();

        headOpton = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.head1)
                .showImageForEmptyUri(R.drawable.head1)  // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.head1)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true)                          // 设置下载的图片是否缓存在SD卡中
                .displayer(new CircleBitmapDisplayer())  // 设置成圆角图片
                .build();


    }

    public static void ShowIamge(ImageView view, String url) {
        ImageLoader.getInstance().displayImage(url, view, options, animateFirstListener);
    }

    public static void ShowHeader(ImageView view, String url) {

        ImageLoader.getInstance().displayImage(url, view, headOpton, animateFirstListener);
    }

    /**
     * 图片加载第一次显示监听器
     *
     * @author Administrator
     */
    private static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                // 是否第一次显示
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    // 图片淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }


}
