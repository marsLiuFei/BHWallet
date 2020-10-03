package com.android.bhwallet.app.Photo.UI;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.view.annotation.ContentView;
import com.asuka.android.asukaandroid.view.annotation.ViewInject;
import com.asuka.android.asukaandroid.widget.photo.MyViewPager;
import com.asuka.android.asukaandroid.widget.photoview.PhotoView;
import com.android.bhwallet.R;
import com.android.bhwallet.app.Photo.Adapters.ImageBrowserAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImageBrowserActivity extends AsukaActivity implements OnPageChangeListener, OnClickListener {
    //	@ViewInject(R.id.imagebrowser_svp_pager)
//	private ScrollViewPager mSvpPager;
    private MyViewPager viewPager;
    private TextView mPtvPage;
    private ImageView mIvDownload;

    private ImageBrowserAdapter mAdapter;
    private String mType;
    private int mPosition;
    private int mTotal;
    private List<String> list;

    public static final String PATH_TYPE = "pathType";//1全路径
    public static final int TYPE_FULL = 1;//1全路径
    public static final int TYPE_PART = 0;//0部分路径
    public static final String IMAGE_TYPE = "image_type";
    public static final String TYPE_ALBUM = "image_album";
    public static final String TYPE_PHOTO = "image_photo";
    private int pathType;

    @Override
    protected void initView() {
        setContentView(R.layout.activity_imagebrowser);
        viewPager = findViewById(R.id.viewPager);
        mPtvPage = findViewById(R.id.imagebrowser_ptv_page);
        mIvDownload = findViewById(R.id.imagebrowser_iv_download);

        viewPager.setOnPageChangeListener(this);
        mIvDownload.setOnClickListener(this);
        Bundle bundle =getIntent().getExtras();
        if (bundle != null) {
            mType = bundle.getString(IMAGE_TYPE);
            pathType=bundle.getInt(PATH_TYPE,0);//默认是部分路径
        }

        if (TYPE_ALBUM.equals(mType)) {
//			mIvDownload.setVisibility(View.GONE);
            mPosition = bundle.getInt("position", 0);
            list = (ArrayList)bundle.getSerializable("images");
                mTotal = list.size();
                if (mPosition > mTotal) {
                mPosition = mTotal - 1;
            }
            if (mTotal > 0) {
                //mPosition += 1000 * mTotal;
                mPtvPage.setText((mPosition % mTotal) + 1 + "/" + mTotal);
                final ImageView []mImageView=new ImageView[list.size()];
                PhotoAdapter adapter=new PhotoAdapter(list,this);
                viewPager.setAdapter(adapter);
                viewPager.setCurrentItem(mPosition);
//				viewPager.setCurrentItem(mPosition,false);
//				mAdapter = new ImageBrowserAdapter(list, mType, this,pathType);
//				mSvpPager.setAdapter(mAdapter);
//				mSvpPager.setCurrentItem(mPosition, false);
            }
        } else if (TYPE_PHOTO.equals(mType)) {
//			mIvDownload.setVisibility(View.VISIBLE);
            final String path = bundle.getString("path");
            list = new ArrayList<String>();
            list.add(path);
            mPtvPage.setText("1/1");
            PhotoAdapter adapter=new PhotoAdapter(list,this);
            viewPager.setAdapter(adapter);
//			mAdapter = new ImageBrowserAdapter(list, mType, this,pathType);
//			mSvpPager.setAdapter(mAdapter);
        }
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void oberserMsg(String type, JSONObject object) {

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        mPosition = arg0;
        mPtvPage.setText((mPosition % mTotal) + 1 + "/" + mTotal);
    }

    @Override
    public void onClick(View arg0) {
        showSuccess("图片已保存到本地");
    }
    class PhotoAdapter extends PagerAdapter {
        private List<String> datas;
        private List<View> views;
        private Context context;
        private PhotoAdapter(List<String>datas,Context context){
            this.datas=datas;
            this.context=context;
            views=new ArrayList<View>();
            if (datas!=null){
                for (Object o:datas){
                    View view=View.inflate(context,R.layout.vp,null);
                    views.add(view);
                }
            }
        }
        @Override
        public int getCount() {
            return views==null?0:views.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view=views.get(position);
            PhotoView photoView= (PhotoView)view.findViewById(R.id.photoView);
            //ImageLoader.getInstance().displayImage(datas.get(position),photoView);
            photoView.setImageBitmap(BitmapFactory.decodeFile(datas.get(position)));
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }
    }

}
