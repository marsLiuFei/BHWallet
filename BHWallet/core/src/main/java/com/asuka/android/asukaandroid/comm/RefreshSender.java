package com.asuka.android.asukaandroid.comm;

import java.util.Observable;

/**
 * Created by Administrator on 2017/2/14.
 */

public class RefreshSender extends Observable {

    private static RefreshSender instances;


    public static RefreshSender getInstances(){
        if(instances==null)
            instances=new RefreshSender();

        return  instances;
    }


    /**
     * 发送消息
     * @param type
     */
    public void sendMessage(String type){
        setChanged();
        this.notifyObservers(type);
    }


}