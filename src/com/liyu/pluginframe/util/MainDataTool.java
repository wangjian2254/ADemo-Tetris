package com.liyu.pluginframe.util;

import loon.utils.json.JSONObject;
import android.content.Context;
import android.content.SharedPreferences;

public class MainDataTool {

	public static void setResultString1(Context con,int jf,String message){
		SharedPreferences.Editor sharedata = con.getSharedPreferences("resultData", 0).edit();  
		JSONObject j=new JSONObject();
		j.put("version", 1);
		j.put("appcode", con.getPackageName());
		j.put("appname", con.getPackageManager().getApplicationLabel(con.getApplicationInfo()));
		j.put("newresult", jf);
		j.put("message", message);
		sharedata.putString("data",j.toString());  
		sharedata.commit();
	}
}
