package com.liyu.pluginframe.util;

import android.app.Activity;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

public class MainDataTool {
	private final static String RESULTDATA="resultData";
	private final static String DATA="data";

    public static UserInfo getUserInfo() {
        return userInfo;
    }

    private static UserInfo userInfo=new UserInfo();

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

    public static void getUserInfoJSON(Activity mainactivity){
        JSONObject j=null;
        String result = mainactivity.getIntent().getExtras().getString("user");
        if(result!=null){
            try {
                j=new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
        if(j!=null){
            userInfo.setUsername(j.optString("username",null));
            userInfo.setNickname(j.optString("nickname","蘑菇玩家"));
            userInfo.setJid(j.optString("jid",null));
            userInfo.setNewroom(j.optBoolean("newroom",false));
            userInfo.setC_name(j.optString("c_name",null));
            userInfo.setC_jid(j.optString("c_jid",null));
            userInfo.setChallengr(j.optBoolean("challenger",false));
        }

    }
}
