package com.liyu.pluginframe.util;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainDataTool {
//	private final static String RESULTDATA="resultData";
	private final static String DATA="data";
    private static DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//默认应用中的时间格式化
    private static Context con;

    private static boolean debug=false;

    private static Handler mMainHandler =null;

    private static String appcode=null;
    private static String appname=null;


    public static UserInfo getUserInfo() {
        return userInfo;
    }

    private static UserInfo userInfo=new UserInfo();

    public static enum Model{NORMAL,DAILY,WEEKLY,MONTHLY,YEAR};

	public static void setResultString1(int jf,String message,Model model){
		SharedPreferences resp = con.getSharedPreferences(appcode, 0);
		String pjs=resp.getString(DATA,null);  
		int totaljf=jf;
		if(pjs!=null){
			try{
				JSONObject js=new JSONObject(pjs);
				if(js.optString("model","normal").equals("normal")&&js.optInt("newresult", 0)>0){
					totaljf+=js.getInt("newresult");
				}
			}catch(Exception e){
				
			}
		}
		
		SharedPreferences.Editor sharedata = con.getSharedPreferences(appcode, 0).edit();
		JSONObject j=new JSONObject();
		try {
			j.put("version", 1);
            switch (model){
                case NORMAL:
                    j.put("model","normal");
                    break;
                case DAILY:
                    j.put("model","daily");
                    break;
                case WEEKLY:
                    j.put("model","weekly");
                    break;
                case MONTHLY:
                    j.put("model","monthly");
                    break;
                case YEAR:
                    j.put("model","year");
                    break;
            }

			j.put("appcode", appcode);
            j.put("datetime",format1.format(new Date()));
			j.put("appname", appname);
			j.put("newresult", totaljf);

            j.put("jf",jf);

			j.put("message", message);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sharedata.putString(DATA,j.toString());
		sharedata.commit();
        if(!debug){
           return;
        }


//        Toast.makeText(con,""+jf,Toast.LENGTH_LONG).show();
        String log=null;
        for(int k=0;k<100;k++){
            log = resp.getString(DATA+k,null);
            if(log==null){
                sharedata.putString(DATA+k,j.toString());
                sharedata.commit();
                break;
            }
        }
        try{
            mMainHandler.obtainMessage(0,""+jf).sendToTarget();
        } catch (Exception e){

        }
	}

    public static void getUserInfoJSON(Activity mainactivity){
        con = mainactivity.getApplicationContext();
        appcode = con.getPackageName();
        appname =  con.getPackageManager().getApplicationLabel(con.getApplicationInfo()).toString();
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
            userInfo.setC_nickname(j.optString("c_nickname",null));
            userInfo.setC_username(j.optString("c_username",null));
            userInfo.setC_jid(j.optString("c_jid",null));
            userInfo.setChallengr(j.optBoolean("challenger",false));
            debug = j.optBoolean("debug",false);

            if(debug){
                mMainHandler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {


                        // 接收子线程的消息
                        Toast.makeText(con, msg.obj.toString(), Toast.LENGTH_SHORT).show();

//                        NHelper.getNHelper().showStatus(context, msg.obj.toString(), true, true, NHelper.SHORT);
                    }
                };
            }
        }

    }
}
