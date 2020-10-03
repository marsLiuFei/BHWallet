package com.android.bhwallet.app.UserCenter.Models;


import com.asuka.android.asukaandroid.base.Entity;

/**
 * Created by jdd on 2017/4/11.
 */
public class PicModel extends Entity {
    private boolean isAdd;
    private String url;



    public boolean isAdd() {
        return isAdd;
    }

    public void setIsAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
