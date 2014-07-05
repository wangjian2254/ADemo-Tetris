package com.liyu.pluginframe.util;

import android.os.Parcel;
import android.os.Parcelable;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by wangjian2254 on 14-6-29.
 */
public class PomeloResult implements Parcelable {

    private JSONObject result;

    public PomeloResult() {

    }

    public PomeloResult(Parcel in){
        try {
            result = new JSONObject(in.readString());
        } catch (JSONException e) {
            result = new JSONObject();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(result.toString());

    }

    public void readFromParcel(Parcel in){
        try {
            result = new JSONObject(in.readString());
        } catch (JSONException e) {
            result = new JSONObject();
        }
    }

    public JSONObject getResult() {
        return result;
    }

    public void setResult(JSONObject result) {
        this.result = result;
    }

    /**
     * 实例化静态内部对象CREATOR实现接口Parcelable.Creator
     * public static final一个都不能少，内部对象CREATOR的名称也不能改变，必须全部大写
     */
    public static final Parcelable.Creator<PomeloResult> CREATOR = new Creator<PomeloResult>(){

        //将Parcel对象反序列化为HarlanInfo
        @Override
        public PomeloResult createFromParcel(Parcel source)
        {
            PomeloResult hlInfo = new PomeloResult(source);
            return hlInfo;
        }

        @Override
        public PomeloResult[] newArray(int size)
        {
            return new PomeloResult[size];
        }

    };
}
