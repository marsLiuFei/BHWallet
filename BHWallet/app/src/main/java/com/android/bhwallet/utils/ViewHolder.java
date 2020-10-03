package com.android.bhwallet.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


@SuppressLint("ResourceAsColor")
public class ViewHolder {

	private SparseArray<View> mViews;
	private View mConvertView;

	private ViewHolder(Context context, View convertView, ViewGroup parent, int layoutId) {
		mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
//		LogUtil.e("---mConvertViewid----" + mConvertView.getId() + "------");
		mConvertView.setTag(this);
	}

	public static ViewHolder get(Context context, View convertView,
			ViewGroup parent, int layoutId) {
		if (convertView == null) {

			return new ViewHolder(context, convertView, parent, layoutId);
		}
		return (ViewHolder) convertView.getTag();
	}

	@SuppressWarnings("unchecked")
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);

		}
		return (T) view;
	}
	//根据控件ID设置文字
	public void setText(int viewId, String text) {
		TextView textView = getView(viewId);
		textView.setText(text);
	}
	//根据控件ID设置颜色
	public void setColor(int viewId,int color) {
		TextView textView = getView(viewId);
		textView.setTextColor(color);
	}
	public void setViewVisibility(int viewId,int view){
		ImageView textView = getView(viewId);
		textView.setVisibility(view);
	}
	public View getConvertView() {
		return mConvertView;
	}

}
