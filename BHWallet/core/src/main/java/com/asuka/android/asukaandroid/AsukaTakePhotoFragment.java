package com.asuka.android.asukaandroid;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.support.annotation.NonNull;
import com.dhc.gallery.GalleryConfig;
import com.dhc.gallery.GalleryHelper;
import com.dhc.gallery.ui.GalleryActivity;
import java.util.Observer;


/**
 * Author:Asuka
 * Time:2016-9-9
 * Mask: 所有Fragment继承这个Fragment
 */
public abstract class AsukaTakePhotoFragment extends AsukaFragment implements Observer  {

    private String outputPath;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 99 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            /***拍照片onActivityResult {@link GalleryActivity.PHOTOS}*/
            GalleryHelper.with(this).type(GalleryConfig.TAKE_PHOTO).requestCode(12).execute();
        } else if (requestCode == 199 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            /***拍照片并裁剪 onActivityResult{@link GalleryActivity.CROP}*/
            GalleryHelper.with(this).type(GalleryConfig.TAKE_PHOTO).isNeedCropWithPath(outputPath).requestCode(12).execute();
        } else if (requestCode == 1999 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            /***选择单张图片并裁剪 onActivityResult{@link GalleryActivity.PHOTOS}*/
            GalleryHelper.with(this).type(GalleryConfig.SELECT_PHOTO).requestCode(12).singlePhoto().isNeedCropWithPath(outputPath).execute();
        }else if (requestCode == 19999 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            /***拍摄视频 onActivityResult{@link GalleryActivity.VIDEO}*/
            GalleryHelper.with(this).type(GalleryConfig.RECORD_VEDIO).requestCode(12)
                    .limitRecordTime(10)//限制时长
                    .limitRecordSize(1)//限制大小
                    .execute();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}