package com.liyu.pluginframe.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

public class MainDataTool {
	private final static String RESULTDATA="resultData";
	private final static String DATA="data";

	public static void setResultString1(Context con,int jf,String message){
		SharedPreferences resp = con.getSharedPreferences(RESULTDATA, 0); 
		String pjs=resp.getString(DATA,null);  
		int totaljf=jf;
		if(pjs!=null){
			try{
				JSONObject js=new JSONObject(pjs);
				if(js.optInt("newresult", 0)>0){
					totaljf+=js.getInt("newresult");
				}
			}catch(Exception e){
				
			}
		}
		
		SharedPreferences.Editor sharedata = con.getSharedPreferences(RESULTDATA, 0).edit();  
		JSONObject j=new JSONObject();
		try {
			j.put("version", 1);
			j.put("appcode", con.getPackageName());
			j.put("appname", con.getPackageManager().getApplicationLabel(con.getApplicationInfo()));
			j.put("newresult", totaljf);
			j.put("message", message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sharedata.putString(DATA,j.toString());  
		sharedata.commit();
	}
}
