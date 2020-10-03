package com.android.bhwallet.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.comm.utils.LogUtil;
import com.android.bhwallet.app.Photo.UI.ImageBrowserActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 备注：
 * 作者：王莹
 * 时间：2017/5/9.
 * ~_~想睡觉了!!
 * (～o～)~zZ我想睡啦～
 * π_π?打瞌睡
 */

public class ImageBrowerView extends android.support.v7.widget.AppCompatImageView {
    String path;
    int index;
    Context context;

    public ImageBrowerView(Context context) {
        super(context);
        this.context = context;
        setDatabase();
    }

    public ImageBrowerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setDatabase();
            }
        });
    }

    private void save() {
        ImagePathHistory m = new ImagePathHistory();
        List<ImagePathHistory> list = m.getList("Image_path");
        if (list == null || list.size() == 0) {
            index = 0;
        } else {
            index = list.size();
        }

        m.setKey("Image_path");
        m.setImagepath(path);
        m.save();
    }

    public ImageBrowerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setDatabase();
            }
        });
    }

    private void setDatabase() {
        ImagePathHistory m = new ImagePathHistory();
        List<String> pathlist = null;
        List<ImagePathHistory> list = m.getList("Image_path");
        for (int i = 0; i < list.size(); i++) {
            pathlist = new ArrayList<>();
            pathlist.add(list.get(i).getImagepath());
        }
        Bundle bundle = new Bundle();
        bundle.putString(ImageBrowserActivity.IMAGE_TYPE, ImageBrowserActivity.TYPE_ALBUM);

        bundle.putSerializable("images", (Serializable) pathlist);
        bundle.putInt("position", index);
        ((AsukaActivity) context).startActivity(ImageBrowserActivity.class, "图片浏览", bundle);
    }

    public void setPath(String path) {
        this.path = path;
        save();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        LogUtil.e("-------visibility-----" + visibility);
        if (visibility == GONE) {
            new ImagePathHistory().del();
        }
    }
}
