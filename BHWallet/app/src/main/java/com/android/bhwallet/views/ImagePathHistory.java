package com.android.bhwallet.views;

import com.asuka.android.asukaandroid.orm.Model;
import com.asuka.android.asukaandroid.orm.annotation.Column;
import com.asuka.android.asukaandroid.orm.annotation.Table;
import com.asuka.android.asukaandroid.orm.query.Delete;
import com.asuka.android.asukaandroid.orm.query.Select;

import java.util.List;

/**
 * =================================================================================================
 * 备注：***
 * 作者：赵锋
 * 时间：2017/5/2 0002
 * <p>
 * 【嘟嘟oοО○●哇靠！！！快让开】
 * ╭══╮老婆！开车罗`坐好啊
 * ╭╯五档║老公！开慢点`我兴奋
 * ╰⊙═⊙╯。oо○ 压死了不赔！
 * =================================================================================================
 */
@Table(name = "tb_image")
public class ImagePathHistory extends Model {

    @Column(name = "key")
    private String key;

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public String getImagepath() {
        return imagepath;
    }

    @Column(name = "imagepath")
    private String imagepath;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }



    public List<ImagePathHistory> getList(String key){
        return   new Select().from(ImagePathHistory.class).where("key=?",key).execute();
    }

    public void  del(){
       new Delete().from(ImagePathHistory.class).execute();
    }

}
