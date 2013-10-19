package com.liyu.pluginframe.util;

import android.content.Context;
import android.content.SharedPreferences;

public class MainDataTool {

	public static void setResultString(Context con,String data){
		SharedPreferences.Editor sharedata = con.getSharedPreferences("resultData", 0).edit();  
		sharedata.putString("data",data);  
		sharedata.commit();
	}
}
