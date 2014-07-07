package com.liyu.pluginframe.util;

import android.app.Activity;
import android.content.*;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainDataTool {
//	private final static String RESULTDATA="resultData";
	private final static String DATA="data";
    private static DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//默认应用中的时间格式化
    private static Context con;

    private static boolean debug=false;

    private static Handler mMainHandler =null;
    private static Handler gamehandler =null;

    private static String appcode=null;
    private static String appname=null;
    private static String static_point=null;
    private static long timeline=0;


    private static int version=0;
    private static String roomid =null;
    private static String author=null;
    private static String gameroomurl=null;
    private static Map<String,Integer> userlist=new HashMap<String, Integer>();
    private static Map<String,String> nicklist=new HashMap<String, String>();
    private static Map<Integer,String> shunxulist=new HashMap<Integer, String>();
    private static Map<Integer,Map<String,Integer>> weizhilist=new HashMap<Integer, Map<String, Integer>>();

    private static Map<String,String> userPointMap = new HashMap<String, String>();
    private static List<BasicNameValuePair> getPointList = new ArrayList< BasicNameValuePair >();

    private static boolean runing=false;
    private static boolean runing2=false;

    private static int x,y,w,h,c ;

    private static AIDLGameRoomService gameRoomService;


    private static   Thread th = new Thread()
    {
        public void run()
        {
            while (runing){
                try{
                    HttpPost postMet = new HttpPost(gameroomurl+"/GetAllPoint");

                    postMet.setEntity(new UrlEncodedFormEntity(getPointList, HTTP.UTF_8));

                    Log.e("request", gameroomurl+"/GetAllPoint");
                    DefaultHttpClient client = new DefaultHttpClient();
                    HttpContext httpContext=new BasicHttpContext();
                    client.getParams().setParameter(
                            CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
                    client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 30000);
                    client.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, false);


                    HttpResponse httpResponse = client.execute(postMet,httpContext);



                    int statusCode=httpResponse.getStatusLine().getStatusCode();

                    if (statusCode == 200) {
                        // 取出回应字串
                        setPoint(EntityUtils.toString(httpResponse.getEntity()));
                    }
                }
                catch (Exception e)
                {

                }

                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    };
    private static String point;
    private static   Thread th2 = null;

    private static void setPoint(String result){
        JSONObject jsonobj= null;
        try {
            jsonobj = new JSONObject(result);
            int status=jsonobj.getInt("status");
            if(status==200){
                JSONArray jsonArray= jsonobj.getJSONArray("result");
                JSONObject userp = null;
                StringBuilder sb = new StringBuilder();
                for(int i=0;i<jsonArray.length();i++){
                    userp = jsonArray.getJSONObject(i);
                    userPointMap.put(userp.optString("username"),userp.optString("point",""));
                    sb.append(userp.optString("username"));
                    sb.append(":");
                    sb.append(userp.optString("point"));
                    sb.append(";");
                    if(debug){
                        try{
                            mMainHandler.obtainMessage(0,""+userp.optString("username")+":"+userp.optString("point","初始化")).sendToTarget();
                        } catch (Exception e){

                        }
                    }

                }

                gamehandler.obtainMessage(0).sendToTarget();

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public static void startGetPoint(){
        uploadPoint("",con);
    }
    public static void stopGetPoint(){
        runing=false;
        runing2=false;
    }

    public static void setPos(int num,int xx,int yy,int ww,int color,Context context){
        int hh=ww+24;
        Map<String,Integer> numpos=weizhilist.get(num);
        if (numpos!=null){
            x=numpos.get("x");
            y=numpos.get("y");
            w=numpos.get("w");
            h=numpos.get("h");
            c = numpos.get("color");
            if(x==xx&&y==yy&&w==ww&&h==hh&&c==color&&con==context){
                return;
            }
        }else{
            numpos = new HashMap<String, Integer>();
            weizhilist.put(num,numpos);
        }

        con= context;
        Map<String,Integer> pos =new HashMap<String,Integer>();
        pos.put("x",xx);
        pos.put("y",yy);
        pos.put("w",ww);
        pos.put("h",hh);
        pos.put("color",color);
        pos.put("num",num);

        gamehandler.obtainMessage(2,pos).sendToTarget();
        numpos.put("x",xx);
        numpos.put("y",yy);
        numpos.put("w",ww);
        numpos.put("h",hh);
        numpos.put("color",color);

    }



    public static void uploadEndPoint(String p,Context context){
        if(p==null||p.equals(static_point)){
            return;
        }

        if(roomid==null||userInfo.getUsername()==null||appcode==null){
            return;
        }

        static_point=p;
        con=context;
        try {
            gameRoomService.uploadPoint(appcode,roomid,userInfo.getUsername(),p);
            gameRoomService.uploadEndPoint(appcode, roomid, userInfo.getUsername(), p);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        userPointMap.put(userInfo.getUsername(),p);
        gamehandler.obtainMessage(0).sendToTarget();
    }
      
    public static void uploadPoint(String p,Context context){
        static_point=null;
        if((System.currentTimeMillis()-timeline)/1000<1){
            return;
        }
        timeline = System.currentTimeMillis();

        if(roomid==null||userInfo.getUsername()==null||appcode==null){
            return;
        }
        con=context;
        try {
            gameRoomService.uploadPoint(appcode,roomid,userInfo.getUsername(),p);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        userPointMap.put(userInfo.getUsername(),p);
        gamehandler.obtainMessage(0).sendToTarget();
    }

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
                if(!js.optString("model","normal").equals("normal")){
                    if(js.getInt("newresult")>=totaljf){
                        totaljf=js.getInt("newresult");
                    }
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
			e.printStackTrace();
		}
		
		sharedata.putString(DATA,j.toString());
		sharedata.commit();
        if(!debug){
           return;
        }


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
        NHelper.getNHelper().init(mainactivity);

        NHelper.mHandler = new Handler();
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
            debug = false;
            if(j.has("version")&&j.optInt("version",1)==2){
                version = j.optInt("version");
                roomid = j.optString("spaceid","");
                author = j.optString("author","");
                gameroomurl = j.optString("gameroom","");
                int face_board,n_1,n_2,n_3,n_4,n_5,n_6;
                face_board = j.optInt("face_board");
                n_1=j.optInt("n_1");
                n_2=j.optInt("n_2");
                n_3=j.optInt("n_3");
                n_4=j.optInt("n_4");
                n_5=j.optInt("n_5");
                n_6=j.optInt("n_6");
                NHelper.getNHelper().setResid(face_board,n_1,n_2,n_3,n_4,n_5,n_6);
                try {
                    JSONArray jsonArray = new JSONArray(j.getString("userlist"));
                    JSONArray nickArray = new JSONArray(j.getString("nicklist"));
                    JSONObject ju=null;
                    JSONObject jn=null;
                    for(int i=0;i<jsonArray.length();i++){
                        ju = jsonArray.getJSONObject(i);
                        jn = nickArray.getJSONObject(i);
                        userlist.put(ju.getString("username"),ju.getInt("head"));
                        nicklist.put(jn.getString("username"),jn.getString("nickname"));
                        shunxulist.put(i,ju.getString("username"));
                    }

                    NHelper.getNHelper().setHead(mainactivity,userlist,nicklist);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getPointList.add(new BasicNameValuePair("username", userInfo.getUsername()));
                getPointList.add(new BasicNameValuePair("appcode",appcode));
                getPointList.add(new BasicNameValuePair("roomid", roomid));

            }
            getServiceConnect(mainactivity.getApplicationContext());

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

            gamehandler = new Handler(){
                @Override
                public void handleMessage(Message msg) {

                    if(msg.what==2){
                        Map<String,Integer> m=(Map<String,Integer>)msg.obj;
                        NHelper.getNHelper().setStatus(con,m.get("num"),m.get("x"),m.get("y"),m.get("w"),m.get("h"),m.get("color"));
                    }else if(msg.what==0){
                        NHelper.getNHelper().showStatus(con, userPointMap);
                    }else if(msg.what==1){
                        NHelper.getNHelper().hiddenStatus();
                    }

                }
            };

        }

    }
    private static void getServiceConnect(Context con)
    {
        Intent it = new Intent();
        it.setAction("com.liyu.pluginframe.util.AIDLGameRoomService");
        con.startService(it);
        con.bindService(it, mserviceConnection, Context.BIND_AUTO_CREATE);
    }

    private static ServiceConnection mserviceConnection = new ServiceConnection()
    {

        @Override
        public void onServiceDisconnected(ComponentName name)
        {
            Log.d("pomelo", "onServiceDisconnected");


            gameRoomService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service)
        {
            Log.d("pomelo", "onServiceConnected");
            //获取服务端传过来的IBinder对象,通过该对象调用服务端的方法
            gameRoomService = AIDLGameRoomService.Stub.asInterface(service);
            if (gameRoomService != null)
            {
                try {
                    gameRoomService.addCB(mCallBack);
                    gameRoomService.init(appcode);

                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private static ICallBack.Stub mCallBack = new ICallBack.Stub()
    {
        //客户端回调方法的具体实现
        @Override
        public void handleByServer(String data) throws RemoteException {
            JSONObject result = null;
            try {
                result = new JSONObject(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(result.optInt("code")!=200&&mMainHandler!=null){
                mMainHandler.obtainMessage(500,result.optString("message","房间服务器异常，请稍后再试。")).sendToTarget();
                return;
            }

            if(result.optString("route","").equals("connect")){
                if(result.optBoolean("success",false)){
                }
            }
            if(result.optString("route","").equals("onChat")){
                try{
                    String from = result.getString("from");
                    String msg = result.getString("msg");
                    userPointMap.put(from,msg);
                    gamehandler.obtainMessage(0).sendToTarget();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(result.optString("route","").equals("onEndPoint")){

            }

            if(result.optString("route","").equals("uploadPoint")){

            }

            if(result.optString("route","").equals("intoRoom")){
                // 取消显示 头像。
                gamehandler.obtainMessage(1).sendToTarget();
            }



        }
    };
}
