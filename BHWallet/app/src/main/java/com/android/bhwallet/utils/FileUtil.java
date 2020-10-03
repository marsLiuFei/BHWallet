package com.android.bhwallet.utils;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.android.bhwallet.app.Main.Interface.OnSexClickListener;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.AsukaTakePhotoActivity;
import com.asuka.android.asukaandroid.AsukaTakePhotoFragment;
import com.asuka.android.asukaandroid.comm.utils.StringUtils;
import com.asuka.android.asukaandroid.utils.PermissionTool;
import com.dhc.gallery.GalleryConfig;
import com.dhc.gallery.GalleryHelper;
import com.android.bhwallet.app.Photo.UI.ImageBrowserActivity;
import com.android.bhwallet.app.Photo.UI.PlayVideoActivity;
import com.android.bhwallet.views.Dialogs.IOSDialog.IosDialog;
import com.android.bhwallet.views.Dialogs.IOSDialog.OnSheetMyItemClickListner;
import com.android.bhwallet.views.Dialogs.IOSDialog.SheetItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by egojit on 16/6/20.
 */
public class FileUtil {

    public static int FILE_SELECT_CODE = 0x0011;

    public static void showFileChooser(AsukaTakePhotoActivity context) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            context.startActivityForResult(Intent.createChooser(intent, "选择一个文件"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            context.showWarning("请安装一个文件管理器！");
        }
    }


    /**
     * 选择图片对话框  具有裁剪功能
     *
     * @param activity
     * @param imageUrl
     */
    public static void showSelectDialogForCrop(final AsukaTakePhotoActivity activity, final String imageUrl, final String outputPath) {
        IosDialog iosDialog = new IosDialog(activity.getContext());
        ArrayList<SheetItem> listSheetItems = new ArrayList<SheetItem>();
        listSheetItems.add(new SheetItem("拍照", 1));
        listSheetItems.add(new SheetItem("选择照片", 2));
        if (!StringUtils.isEmpty(imageUrl))
            listSheetItems.add(new SheetItem("查看", 3));
        iosDialog.setSheetItems(listSheetItems, new OnSheetMyItemClickListner() {
            @Override
            public void onClickItem(int i) {
                switch (i) {
                    case 1:
                        if (Build.VERSION.SDK_INT > 22) {
                            List<String> permissionList = new ArrayList<>();
                            if (!PermissionTool.isCameraCanUse())
                                permissionList.add(android.Manifest.permission.CAMERA);
                            int check = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                            if (check != PackageManager.PERMISSION_GRANTED) {
                                permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            }
                            if (permissionList.size() == 0) {
                                GalleryHelper.with(activity).type(GalleryConfig.TAKE_PHOTO).isNeedCropWithPath(outputPath).requestCode(12).execute();
                            } else {
                                ActivityCompat.requestPermissions(activity,
                                        permissionList.toArray(new String[permissionList.size()]), Constants.media_ok_crop);
                            }
                        } else {
                            GalleryHelper.with(activity).type(GalleryConfig.TAKE_PHOTO).isNeedCropWithPath(outputPath).requestCode(12).execute();
                        }
                        break;
                    case 2:
                        if (Build.VERSION.SDK_INT > 22) {
                            List<String> permissionList = new ArrayList<>();
                            int check = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                            if (check != PackageManager.PERMISSION_GRANTED) {
                                permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            }
                            if (permissionList.size() == 0) {
                                GalleryHelper.with(activity).type(GalleryConfig.SELECT_PHOTO).requestCode(12).singlePhoto().isNeedCropWithPath(outputPath).execute();
                            } else {
                                ActivityCompat.requestPermissions(activity,
                                        permissionList.toArray(new String[permissionList.size()]), Constants.select_pic_and_crop);
                            }
                        } else {
                            GalleryHelper.with(activity).type(GalleryConfig.SELECT_PHOTO).requestCode(12).singlePhoto().isNeedCropWithPath(outputPath).execute();
                        }
                        break;
                    case 3:
                        Bundle bundle = new Bundle();
                        bundle.putString(ImageBrowserActivity.IMAGE_TYPE, ImageBrowserActivity.TYPE_PHOTO);
                        bundle.putString("path", imageUrl);
                        activity.startActivity(ImageBrowserActivity.class, "图片浏览", bundle);
                        break;
                }
            }
        });
        iosDialog.show();
    }


    /**
     * 选择图片对话框
     *
     * @param activity
     * @param imageUrl
     */
    public static void showSelectSingleDialog(final AsukaTakePhotoActivity activity, final String imageUrl) {
        IosDialog iosDialog = new IosDialog(activity.getContext());
        ArrayList<SheetItem> listSheetItems = new ArrayList<SheetItem>();
        listSheetItems.add(new SheetItem("拍照", 1));
        listSheetItems.add(new SheetItem("选择照片", 2));
        if (!StringUtils.isEmpty(imageUrl))
            listSheetItems.add(new SheetItem("查看", 3));
        iosDialog.setSheetItems(listSheetItems, new OnSheetMyItemClickListner() {
            @Override
            public void onClickItem(int i) {
                switch (i) {
                    case 1:
                        if (Build.VERSION.SDK_INT > 22) {
                            List<String> permissionList = new ArrayList<>();
                            if (!PermissionTool.isCameraCanUse())
                                permissionList.add(android.Manifest.permission.CAMERA);
                            int check = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                            if (check != PackageManager.PERMISSION_GRANTED) {
                                permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            }
                            if (permissionList.size() == 0) {
                                GalleryHelper.with(activity).type(GalleryConfig.TAKE_PHOTO).requestCode(12).execute();
                            } else {
                                ActivityCompat.requestPermissions(activity,
                                        permissionList.toArray(new String[permissionList.size()]), Constants.media_ok);
                            }
                        } else {
                            GalleryHelper.with(activity).type(GalleryConfig.TAKE_PHOTO).requestCode(12).execute();
                        }
                        break;
                    case 2:
                        GalleryHelper.with(activity).type(GalleryConfig.SELECT_PHOTO).requestCode(12).singlePhoto().execute();
                        break;
                    case 3:
                        Bundle bundle = new Bundle();
                        bundle.putString(ImageBrowserActivity.IMAGE_TYPE, ImageBrowserActivity.TYPE_PHOTO);
                        bundle.putString("path", imageUrl);
                        activity.startActivity(ImageBrowserActivity.class, "图片浏览", bundle);
                        break;
                }
            }
        });
        iosDialog.show();

    }

    /**
     * 选择图片对话框
     *
     * @param activity
     * @param imageUrl
     */
    public static void showSelectDialog(final AsukaTakePhotoActivity activity, final String imageUrl) {

        IosDialog iosDialog = new IosDialog(activity.getContext());
        ArrayList<SheetItem> listSheetItems = new ArrayList<SheetItem>();
        listSheetItems.add(new SheetItem("拍照", 1));
        listSheetItems.add(new SheetItem("选择照片", 2));
        if (!StringUtils.isEmpty(imageUrl))
            listSheetItems.add(new SheetItem("查看", 3));
        iosDialog.setSheetItems(listSheetItems, new OnSheetMyItemClickListner() {
            @Override
            public void onClickItem(int i) {
                switch (i) {
                    case 1:
                        if (Build.VERSION.SDK_INT > 22) {
                            List<String> permissionList = new ArrayList<>();
                            if (!PermissionTool.isCameraCanUse())
                                permissionList.add(android.Manifest.permission.CAMERA);
                            int check = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                            if (check != PackageManager.PERMISSION_GRANTED) {
                                permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            }
                            if (permissionList.size() == 0) {
                                GalleryHelper.with(activity).type(GalleryConfig.TAKE_PHOTO).requestCode(12).execute();
                            } else {
                                ActivityCompat.requestPermissions(activity,
                                        permissionList.toArray(new String[permissionList.size()]), Constants.media_ok);
                            }
                        } else {
                            GalleryHelper.with(activity).type(GalleryConfig.TAKE_PHOTO).requestCode(12).execute();
                        }
                        break;
                    case 2:
                        GalleryHelper.with(activity).type(GalleryConfig.SELECT_PHOTO).requestCode(12).limitPickPhoto(9).execute();
                        break;
                    case 3:
                        Bundle bundle = new Bundle();
                        bundle.putString(ImageBrowserActivity.IMAGE_TYPE, ImageBrowserActivity.TYPE_PHOTO);
                        bundle.putString("path", imageUrl);
                        activity.startActivity(ImageBrowserActivity.class, "图片浏览", bundle);
                        break;
                }
            }
        });
        iosDialog.show();


    }

    /**
     * 选择视频对话框
     *
     * @param activity
     * @param imageUrl
     */
    public static void showSelectVideoDialog(final AsukaTakePhotoActivity activity, final String imageUrl) {
        IosDialog iosDialog = new IosDialog(activity);
        ArrayList<SheetItem> listSheetItems = new ArrayList<SheetItem>();
        listSheetItems.add(new SheetItem("录视频", 1));
        listSheetItems.add(new SheetItem("选择视频", 2));
        if (!StringUtils.isEmpty(imageUrl))
            listSheetItems.add(new SheetItem("查看", 3));
        iosDialog.setSheetItems(listSheetItems, new OnSheetMyItemClickListner() {
            @Override
            public void onClickItem(int i) {
                switch (i) {
                    case 1:
                        List<String> permissionList = new ArrayList<>();
                        if (Build.VERSION.SDK_INT > 22) {
                            if (!PermissionTool.isCameraCanUse()) {
                                permissionList.add(android.Manifest.permission.CAMERA);
                            }
                        }
                        if (!VoicePermission.isHasPermission(activity)) {
                            permissionList.add(android.Manifest.permission.RECORD_AUDIO);
                        }
                        int check = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                        if (check != PackageManager.PERMISSION_GRANTED) {
                            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }

                        if (permissionList.size() > 0) {
                            ActivityCompat.requestPermissions(activity,
                                    permissionList.toArray(new String[permissionList.size()]), Constants.video_ok);

                        } else {
                            GalleryHelper.with(activity).type(GalleryConfig.RECORD_VEDIO).requestCode(12)
                                    .limitRecordTime(10)//限制时长
                                    .limitRecordSize(1)//限制大小
                                    .execute();
                        }

                        break;
                    case 2:
                        GalleryHelper.with(activity).type(GalleryConfig.SELECT_VEDIO).requestCode(12).isSingleVedio().execute();
                        break;
                    case 3:
                        Bundle bundle = new Bundle();
                        bundle.putString("url", imageUrl);
                        activity.startActivity(PlayVideoActivity.class, "", bundle);
                        break;
                }
            }
        });
        iosDialog.show();
    }

    //=======================fragment中调用=========================================


    /**
     * 选择图片对话框  具有裁剪功能
     *
     * @param fragment
     * @param imageUrl
     */
    public static void showSelectDialogForCrop(final AsukaTakePhotoFragment fragment, final String imageUrl, final String outputPath) {
        IosDialog iosDialog = new IosDialog(fragment.getContext());
        ArrayList<SheetItem> listSheetItems = new ArrayList<SheetItem>();
        listSheetItems.add(new SheetItem("拍照", 1));
        listSheetItems.add(new SheetItem("选择照片", 2));
        if (!StringUtils.isEmpty(imageUrl))
            listSheetItems.add(new SheetItem("查看", 3));
        iosDialog.setSheetItems(listSheetItems, new OnSheetMyItemClickListner() {
            @Override
            public void onClickItem(int i) {
                switch (i) {
                    case 1:
                        if (Build.VERSION.SDK_INT > 22) {
                            List<String> permissionList = new ArrayList<>();
                            if (!PermissionTool.isCameraCanUse())
                                permissionList.add(android.Manifest.permission.CAMERA);
                            int check = ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                            if (check != PackageManager.PERMISSION_GRANTED) {
                                permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            }
                            if (permissionList.size() == 0) {
                                GalleryHelper.with(fragment).type(GalleryConfig.TAKE_PHOTO).isNeedCropWithPath(outputPath).requestCode(12).execute();
                            } else {
                                ActivityCompat.requestPermissions(fragment.getActivity(),
                                        permissionList.toArray(new String[permissionList.size()]), Constants.media_ok_crop);
                            }
                        } else {
                            GalleryHelper.with(fragment).type(GalleryConfig.TAKE_PHOTO).isNeedCropWithPath(outputPath).requestCode(12).execute();
                        }
                        break;
                    case 2:
                        if (Build.VERSION.SDK_INT > 22) {
                            List<String> permissionList = new ArrayList<>();
                            int check = ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                            if (check != PackageManager.PERMISSION_GRANTED) {
                                permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            }
                            if (permissionList.size() == 0) {
                                GalleryHelper.with(fragment).type(GalleryConfig.SELECT_PHOTO).requestCode(12).singlePhoto().isNeedCropWithPath(outputPath).execute();
                            } else {
                                ActivityCompat.requestPermissions(fragment.getActivity(),
                                        permissionList.toArray(new String[permissionList.size()]), Constants.select_pic_and_crop);
                            }
                        } else {
                            GalleryHelper.with(fragment).type(GalleryConfig.SELECT_PHOTO).requestCode(12).singlePhoto().isNeedCropWithPath(outputPath).execute();
                        }
                        break;
                    case 3:
                        Bundle bundle = new Bundle();
                        bundle.putString(ImageBrowserActivity.IMAGE_TYPE, ImageBrowserActivity.TYPE_PHOTO);
                        bundle.putString("path", imageUrl);
                        fragment.startActivity(ImageBrowserActivity.class, "图片浏览", bundle);
                        break;
                }
            }
        });
        iosDialog.show();
    }


    /**
     * 选择图片对话框
     *
     * @param fragment
     * @param imageUrl
     */
    public static void showSelectSingleDialog(final AsukaTakePhotoFragment fragment, final String imageUrl, final int num) {
        IosDialog iosDialog = new IosDialog(fragment.getContext());
        ArrayList<SheetItem> listSheetItems = new ArrayList<SheetItem>();
        listSheetItems.add(new SheetItem("拍照", 1));
        listSheetItems.add(new SheetItem("选择照片", 2));
        if (!StringUtils.isEmpty(imageUrl))
            listSheetItems.add(new SheetItem("查看", 3));
        iosDialog.setSheetItems(listSheetItems, new OnSheetMyItemClickListner() {
            @Override
            public void onClickItem(int i) {
                switch (i) {
                    case 1:
                        if (Build.VERSION.SDK_INT > 22) {
                            List<String> permissionList = new ArrayList<>();
                            if (!PermissionTool.isCameraCanUse())
                                permissionList.add(android.Manifest.permission.CAMERA);
                            int check = ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                            if (check != PackageManager.PERMISSION_GRANTED) {
                                permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            }
                            if (permissionList.size() == 0) {
                                GalleryHelper.with(fragment).type(GalleryConfig.TAKE_PHOTO).requestCode(12).execute();
                            } else {
                                ActivityCompat.requestPermissions(fragment.getActivity(),
                                        permissionList.toArray(new String[permissionList.size()]), Constants.media_ok);
                            }
                        } else {
                            GalleryHelper.with(fragment).type(GalleryConfig.TAKE_PHOTO).requestCode(12).execute();
                        }
                        break;
                    case 2:
                        GalleryHelper.with(fragment).type(GalleryConfig.SELECT_PHOTO).requestCode(12).singlePhoto().execute();
                        break;
                    case 3:
                        Bundle bundle = new Bundle();
                        bundle.putString(ImageBrowserActivity.IMAGE_TYPE, ImageBrowserActivity.TYPE_PHOTO);
                        bundle.putString("path", imageUrl);
                        fragment.startActivity(ImageBrowserActivity.class, "图片浏览", bundle);
                        break;
                }
            }
        });
        iosDialog.show();

    }

    /**
     * 选择图片对话框
     *
     * @param fragment
     * @param imageUrl
     */
    public static void showSelectDialog(final AsukaTakePhotoFragment fragment, final String imageUrl) {

        IosDialog iosDialog = new IosDialog(fragment.getContext());
        ArrayList<SheetItem> listSheetItems = new ArrayList<SheetItem>();
        listSheetItems.add(new SheetItem("拍照", 1));
        listSheetItems.add(new SheetItem("选择照片", 2));
        if (!StringUtils.isEmpty(imageUrl))
            listSheetItems.add(new SheetItem("查看", 3));
        iosDialog.setSheetItems(listSheetItems, new OnSheetMyItemClickListner() {
            @Override
            public void onClickItem(int i) {
                switch (i) {
                    case 1:
                        if (Build.VERSION.SDK_INT > 22) {
                            List<String> permissionList = new ArrayList<>();
                            if (!PermissionTool.isCameraCanUse())
                                permissionList.add(android.Manifest.permission.CAMERA);
                            int check = ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                            if (check != PackageManager.PERMISSION_GRANTED) {
                                permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            }
                            if (permissionList.size() == 0) {
                                GalleryHelper.with(fragment).type(GalleryConfig.TAKE_PHOTO).requestCode(12).execute();
                            } else {
                                ActivityCompat.requestPermissions(fragment.getActivity(),
                                        permissionList.toArray(new String[permissionList.size()]), Constants.media_ok);
                            }
                        } else {
                            GalleryHelper.with(fragment).type(GalleryConfig.TAKE_PHOTO).requestCode(12).execute();
                        }
                        break;
                    case 2:
                        GalleryHelper.with(fragment).type(GalleryConfig.SELECT_PHOTO).requestCode(12).limitPickPhoto(9).execute();
                        break;
                    case 3:
                        Bundle bundle = new Bundle();
                        bundle.putString(ImageBrowserActivity.IMAGE_TYPE, ImageBrowserActivity.TYPE_PHOTO);
                        bundle.putString("path", imageUrl);
                        fragment.startActivity(ImageBrowserActivity.class, "图片浏览", bundle);
                        break;
                }
            }
        });
        iosDialog.show();


    }

    /**
     * 选择视频对话框
     *
     * @param fragment
     * @param imageUrl
     */
    public static void showSelectVideoDialog(final AsukaTakePhotoFragment fragment, final String imageUrl) {
        IosDialog iosDialog = new IosDialog(fragment.getContext());
        ArrayList<SheetItem> listSheetItems = new ArrayList<SheetItem>();
        listSheetItems.add(new SheetItem("录视频", 1));
        listSheetItems.add(new SheetItem("选择视频", 2));
        if (!StringUtils.isEmpty(imageUrl))
            listSheetItems.add(new SheetItem("查看", 3));
        iosDialog.setSheetItems(listSheetItems, new OnSheetMyItemClickListner() {
            @Override
            public void onClickItem(int i) {
                switch (i) {
                    case 1:
                        List<String> permissionList = new ArrayList<>();
                        if (Build.VERSION.SDK_INT > 22) {
                            if (!PermissionTool.isCameraCanUse()) {
                                permissionList.add(android.Manifest.permission.CAMERA);
                            }
                        }
                        if (!VoicePermission.isHasPermission(fragment.getActivity())) {
                            permissionList.add(android.Manifest.permission.RECORD_AUDIO);
                        }
                        int check = ContextCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
                        if (check != PackageManager.PERMISSION_GRANTED) {
                            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        }

                        if (permissionList.size() > 0) {
                            ActivityCompat.requestPermissions(fragment.getActivity(),
                                    permissionList.toArray(new String[permissionList.size()]), Constants.video_ok);

                        } else {
                            GalleryHelper.with(fragment).type(GalleryConfig.RECORD_VEDIO).requestCode(12)
                                    .limitRecordTime(10)//限制时长
                                    .limitRecordSize(1)//限制大小
                                    .execute();
                        }

                        break;
                    case 2:
                        GalleryHelper.with(fragment).type(GalleryConfig.SELECT_VEDIO).requestCode(12).isSingleVedio().execute();
                        break;
                    case 3:
                        Bundle bundle = new Bundle();
                        bundle.putString("url", imageUrl);
                        fragment.startActivity(PlayVideoActivity.class, "", bundle);
                        break;
                }
            }
        });
        iosDialog.show();
    }


    public static void SexSelectDialog(AsukaActivity activity, final OnSexClickListener listener){
        IosDialog iosDialog = new IosDialog(activity);
        final ArrayList<SheetItem> listSheetItems = new ArrayList<SheetItem>();
        listSheetItems.add(new SheetItem("女", 0));
        listSheetItems.add(new SheetItem("男", 1));

        iosDialog.setSheetItems(listSheetItems, new OnSheetMyItemClickListner() {
            @Override
            public void onClickItem(int i) {
               listener.onClick(i,listSheetItems.get(i).getStrItemName());
            }
        });
        iosDialog.show();
    }
}
