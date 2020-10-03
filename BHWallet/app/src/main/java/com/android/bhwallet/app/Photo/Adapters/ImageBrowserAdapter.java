package com.android.bhwallet.app.Photo.Adapters;

import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.asuka.android.asukaandroid.AsukaActivity;
import com.asuka.android.asukaandroid.ImageDisplay.ImageUtil;
import com.asuka.android.asukaandroid.widget.photoview.PhotoView;
import com.android.bhwallet.app.Photo.UI.ImageBrowserActivity;

import java.util.ArrayList;
import java.util.List;


public class ImageBrowserAdapter extends PagerAdapter {

	private AsukaActivity activity;
	//private BaseApplication mApplication;
	private List<String> mPhotos = new ArrayList();
	private String mType;
	private int PATH_TYPE=0;
	public ImageBrowserAdapter(List<String> photos, String type, AsukaActivity ac, int ppathType) {
		PATH_TYPE=ppathType;
		if (photos != null) {
			mPhotos = photos;
		}
		mType = type;
		this.activity=ac;
	}

	@Override
	public int getCount() {
		if (mPhotos.size() > 1) {
			return Integer.MAX_VALUE;
		}
		return mPhotos.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public View instantiateItem(ViewGroup container, int position) {
		PhotoView photoView = new PhotoView(container.getContext());
		photoView.setScaleType(ImageView.ScaleType.CENTER);
		Bitmap bitmap = null;
		if (ImageBrowserActivity.TYPE_ALBUM.equals(mType)) {
//			bitmap = mApplication.getPhotoOriginal(mPhotos.get(position
//					% mPhotos.size()));
			String path= mPhotos.get(position
					% mPhotos.size());
			if(PATH_TYPE== ImageBrowserActivity.TYPE_PART)
			ImageUtil.ShowIamge(photoView, path);
		} else if (ImageBrowserActivity.TYPE_PHOTO.equals(mType)) {
			String path= mPhotos.get(position);
			if(PATH_TYPE==ImageBrowserActivity.TYPE_PART)

			ImageUtil.ShowIamge(photoView, path);
			//bitmap = BitmapFactory.decodeFile(mPhotos.get(position));
		}
		//photoView.setImageBitmap(bitmap);
		container.addView(photoView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		return photoView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
}
