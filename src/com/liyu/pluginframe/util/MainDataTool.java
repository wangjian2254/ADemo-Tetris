package com.liyu.pluginframe.util;

import android.app.Activity;
import android.content.*;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainDataTool {
//	private final static String RESULTDATA="resultData";
	private final static String DATA="data";

	private final static String START_GAME="start_game";
	private final static String END_GAME="end_game";
	private final static String UPLOAD_GAME_POINT="upload_game_point";
	private final static String UPLOAD_END_GAME_POINT="upload_end_game_point";
	private final static String PUSH_GAME_DATA="push_game_data";
	private final static String PUSH_PROPERTY_DATA="push_property_data";
	private final static String SEND_CHAT="send_chat";
	private final static String IN_ROOM="in_room";
	private final static String OUT_ROOM="out_room";
    private static Map<String, Long> api_timeout= new HashMap<String, Long>();
    private static DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//默认应用中的时间格式化
    private static Context con;
    private static IGameSync iGameSync;

    private static boolean debug=false;

    private static Handler mMainHandler =null;
    private static Handler gamehandler =null;

    private static String appcode=null;
    private static String appname=null;
    private static String static_point=null;
    private static long timeline=0;


    private static int version=0;
    private static String roomid =null;
    private static String author =null;
//    private static String gameroomurl =null;
    private static Map<String,Integer> userlist=new HashMap<String, Integer>();
    private static Map<String,String> nicklist=new HashMap<String, String>();
    private static ArrayList<String> shunxulist = new ArrayList<String>();
//    private static Map<Integer,String> shunxulist=new HashMap<Integer, String>();
    private static Map<Integer,Map<String,Integer>> weizhilist=new HashMap<Integer, Map<String, Integer>>();

    private static Map<String,String> userPointMap = new HashMap<String, String>();
    private static List<BasicNameValuePair> getPointList = new ArrayList< BasicNameValuePair >();


    private static int type,x,y,w,h ;
    public static enum HEAD_TYPE{HEAD,POINT};

    private static AIDLGameRoomService gameRoomService;



//
//    public static void uploadEndPoint(String p,Context context){
//        if(p==null||p.equals(static_point)){
//            return;
//        }
//
//        if(roomid==null||userInfo.getUsername()==null||appcode==null){
//            return;
//        }
//
//        static_point=p;
//        con=context;
//        try {
//            gameRoomService.uploadPoint(appcode,roomid,userInfo.getUsername(),p);
//            gameRoomService.uploadEndPoint(appcode, roomid, userInfo.getUsername(), p);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        userPointMap.put(userInfo.getUsername(),p);
//        gamehandler.obtainMessage(0).sendToTarget();
//    }
//
//    public static void uploadPoint(String p,Context context){
//
//        static_point=null;
//        if(p==null||(System.currentTimeMillis()-timeline)/1000<1){
//            return;
//        }
//        timeline = System.currentTimeMillis();
//
//        if(roomid==null||userInfo.getUsername()==null||appcode==null){
//            return;
//        }
//        con=context;
//        try {
//            gameRoomService.uploadPoint(appcode,roomid,userInfo.getUsername(),p);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        userPointMap.put(userInfo.getUsername(),p);
//        gamehandler.obtainMessage(0).sendToTarget();
//    }

    public static void setIGameSync(IGameSync iGameSync1){
        iGameSync = iGameSync1;

    }

    /**
     * 获取用户信息
     * @return
     */
    public static UserInfo getUserInfo() {
        return userInfo;
    }

    private static UserInfo userInfo=new UserInfo();

    public static enum Model{NORMAL,DAILY,WEEKLY,MONTHLY,YEAR};

	public static void setResultString1(int jf, String message, Model model){
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

    /**
     * 获取用户信息、包括房间信息
     * @param mainactivity  Activity 句柄
     */
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
            debug = false;
            if(j.has("version")&&j.optInt("version",1)==2){
                version = j.optInt("version");
                roomid = j.optString("spaceid","");
                author = j.optString("author","");
//                gameroomurl = j.optString("gameroom","");
                int face_board,n_1,n_2,n_3;
                face_board = j.optInt("face_board");
                n_1=j.optInt("n_1");
                n_2=j.optInt("n_2");
                n_3=j.optInt("n_3");
                try {
                    JSONArray jsonArray = new JSONArray(j.getString("userlist"));
                    JSONArray nickArray = new JSONArray(j.getString("nicklist"));
                    JSONObject ju=null;
                    JSONObject jn=null;
                    shunxulist.clear();
                    userlist.clear();
                    nicklist.clear();
                    for(int i=0;i<jsonArray.length();i++){
                        ju = jsonArray.getJSONObject(i);
                        jn = nickArray.getJSONObject(i);
                        userlist.put(ju.getString("username"),ju.getInt("head"));
                        nicklist.put(jn.getString("username"),jn.getString("nickname"));
                        shunxulist.add(ju.getString("username"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getPointList.add(new BasicNameValuePair("username", userInfo.getUsername()));
                getPointList.add(new BasicNameValuePair("appcode",appcode));
                getPointList.add(new BasicNameValuePair("roomid", roomid));
                getServiceConnect(mainactivity.getApplicationContext());
            }


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
                    }else if(msg.what==0){
                        JSONObject result = (JSONObject)msg.obj;
                        JSONObject result_json = (JSONObject)result.optJSONObject("json");
                        String from = result_json.optString("from", null);
                        String route = result.optString("route","");
                        if(iGameSync==null){
                            Log.e("game_sync", "iGameSync is null");
                            return;
                        }
                        if(route.equals(START_GAME)){
                            iGameSync.syncStartGame();
                        }
                        if(route.equals(UPLOAD_GAME_POINT)){
                            iGameSync.syncGamePoints(from, result_json.optString("point", ""));
                        }
                        if(route.equals(UPLOAD_END_GAME_POINT)){
                            iGameSync.syncEndGamePoints(from, result_json.optString("point", ""));
                        }
                        if(route.equals(END_GAME)){
                            if(from == null){
                                iGameSync.syncEndGame();
                            }else{
                                iGameSync.syncEndGame(from);
                            }

                        }
                        if(route.equals(PUSH_GAME_DATA)){
                            if(result_json.has("users")){
                                JSONArray jsonArray = result_json.optJSONArray("users");
                                if(jsonArray.length()>0){
                                    String[] users = new String[jsonArray.length()];
                                    for(int i=0;i<jsonArray.length();i++){
                                        users[i] = jsonArray.optString(i);
                                    }
                                    iGameSync.syncGameData(from, users,result);
                                }

                            }else{
                                iGameSync.syncGameData(from, result);
                            }

                        }
                        if(route.equals(PUSH_PROPERTY_DATA)){
                            if(result_json.has("users")){
                                JSONArray jsonArray = result_json.optJSONArray("users");
                                if(jsonArray.length()>0){
                                    String[] users = new String[jsonArray.length()];
                                    for(int i=0;i<jsonArray.length();i++){
                                        users[i] = jsonArray.optString(i);
                                    }
                                    iGameSync.syncGamePropertyInfo(from, users, result_json.optString("property_flag", null));
                                }

                            }else{
                                iGameSync.syncGamePropertyInfo(from, result_json.optString("property_flag", null));
                            }
                        }
                        if(route.equals(SEND_CHAT)){
                            if(result_json.has("user")){
                                iGameSync.syncChat(from, result_json.optString("user"), result_json.optString("msg"));
                            }else{
                                iGameSync.syncChat(from, result_json.optString("msg"));
                            }
                        }
                        if(route.equals(OUT_ROOM)){
                            shunxulist.remove(result_json.optString("user"));
                            iGameSync.syncMemberChange(result_json.optString("user"), false);
                        }
                    }else if(msg.what==1){
                    }else if(msg.what==800){
                        JSONObject m=new JSONObject();
                        try {
                            m.put("code",200);
                            m.put("route","disconnect");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            if(mCallBack!=null){
                                mCallBack.handleByServer(m.toString());
                            }

                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
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
                    gameRoomService.addCB("game",mCallBack);
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
            if(result.optString("route","").equals("disconnect")){
                Log.e("kill game","ggggggggggggg");
                android.os.Process.killProcess(android.os.Process.myPid());
                gamehandler.obtainMessage(800).sendToTarget();
            }
            String route = result.optString("route","");
            if(route.equals(START_GAME)||route.equals(UPLOAD_GAME_POINT)||route.equals(UPLOAD_END_GAME_POINT)||route.equals(PUSH_GAME_DATA)||route.equals(PUSH_PROPERTY_DATA)||route.equals(SEND_CHAT)){
                gamehandler.obtainMessage(0, result).sendToTarget();
            }
            if(route.equals("onLeave")){
                try{
                    String username = result.getString("user");
                    String roomid = result.getString("roomid");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("user", username);
                    jsonObject.put("route", OUT_ROOM);
                    gamehandler.obtainMessage(0, jsonObject).sendToTarget();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
//            if(result.optString("route","").equals("onChat")){
//                try{
//                    String from = result.getString("from");
//                    String msg = result.getString("msg");
//                    userPointMap.put(from,msg);
//                    gamehandler.obtainMessage(0).sendToTarget();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//            if(result.optString("route","").equals("onEndPoint")){
//
//            }
//
//            if(result.optString("route","").equals("uploadPoint")){
//
//            }

            if(result.optString("route","").equals("intoRoom")){
                // 取消显示 头像。
                gamehandler.obtainMessage(1).sendToTarget();
            }



        }
    };

    private static int syncGameRoom(String route, String json){
        long currentTimeMillis = System.currentTimeMillis();
        if(api_timeout.containsKey(route)) {
            if (currentTimeMillis - api_timeout.get(route) < 100) {
                return 1;
            }
        }
        api_timeout.put(route, currentTimeMillis);
        try {
            gameRoomService.syncGameRoom(appcode,userInfo.getUsername(), roomid, route, json);
            return 0;
        } catch (RemoteException e) {
            e.printStackTrace();
            Log.e("game_sync", route + "-----error");
            return 2;
        }
    }

    /**
     * 开始游戏
     * @return 0成功，1间隔太短，2失败
     */
    public static int startGame(){
        return syncGameRoom(START_GAME, "{}");
    }

    /**
     * 上传积分，进度……游戏数据
     * @param point
     *  @return 0成功，1间隔太短，2失败
     */
    public static int uploadGamePoint(String point){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("point", point);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return syncGameRoom(UPLOAD_GAME_POINT, jsonObject.toString());
    }

    /**
     * 上传最终结果数据
     * @param point
     *  @return 0成功，1间隔太短，2失败
     */
    public static int uploadEndGamePoint(String point){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("point", point);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return syncGameRoom(UPLOAD_END_GAME_POINT, jsonObject.toString());
    }

    /**
     * 向用户发送自定义游戏数据
     * @param jsonObject
     * @param user
     *  @return 0成功，1间隔太短，2失败
     */
    public static int pushGameDataToUser(JSONObject jsonObject, String user){
        try {
            JSONArray users = new JSONArray();
            users.put(user);
            jsonObject.put("users", users);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return syncGameRoom(PUSH_GAME_DATA, jsonObject.toString());
    }

    /**
     * 向多个用户发送自定义游戏数据
     * @param jsonObject
     * @param users
     *  @return 0成功，1间隔太短，2失败
     */
    public static int pushGameDataToUsers(JSONObject jsonObject, String[] users){
        try {
            JSONArray jsonArray = new JSONArray();
            for(String user:users){
                jsonArray.put(user);
            }
            jsonObject.put("users", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return syncGameRoom(PUSH_GAME_DATA, jsonObject.toString());
    }

    /**
     * 向所有用户发送自定义游戏数据
     * @param jsonObject
     *  @return 0成功，1间隔太短，2失败
     */
    public static int pushGameDataToAllUser(JSONObject jsonObject){

        return syncGameRoom(PUSH_GAME_DATA, jsonObject.toString());
    }

    /**
     * 向用户发送道具数据
     * @param property_flag
     * @param user
     *  @return 0成功，1间隔太短，2失败
     */
    public static int pushPropertyDataToUser(String property_flag, String user){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("property_flag", property_flag);
            JSONArray jsonArray = new JSONArray();
            jsonArray.put(user);
            jsonObject.put("users", jsonArray);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return syncGameRoom(PUSH_PROPERTY_DATA, jsonObject.toString());
    }

    /**
     * 向多个用户发送道具数据
     * @param property_flag
     * @param users
     *  @return 0成功，1间隔太短，2失败
     */
    public static int pushPropertyDataToUsers(String property_flag, String[] users){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("property_flag", property_flag);
            JSONArray jsonArray = new JSONArray();
            for(String user:users){
                jsonArray.put(user);
            }
            jsonObject.put("users", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return syncGameRoom(PUSH_PROPERTY_DATA, jsonObject.toString());
    }

    /**
     * 向所有用户发送道具数据
     * @param property_flag
     *  @return 0成功，1间隔太短，2失败
     */
    public static int pushPropertyDataToAllUser(String property_flag){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("property_flag", property_flag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return syncGameRoom(PUSH_PROPERTY_DATA, jsonObject.toString());
    }

    /**
     * 发送私聊内容
     * @param msg
     * @param user
     *  @return 0成功，1间隔太短，2失败
     */
    public static int sendChatToUser(String msg, String user){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msg", msg);
            jsonObject.put("user", user);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return syncGameRoom(SEND_CHAT, jsonObject.toString());
    }

    /**
     *发送谈话内容
     * @param msg 谈话内容
     *  @return 0成功，1间隔太短，2失败
     */
    public static int sendChatToAllUser(String msg){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msg", msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return syncGameRoom(SEND_CHAT, jsonObject.toString());
    }

    /**
     * 获取当前用户列表，username列表
     * @return 用户列表
     */
    public static ArrayList<String> getMembers(){
        return shunxulist;
    }


    /**
     * 退出当前房间，如果在游戏，则退出游戏
     */
    public static void quiteRoom(){
        try {
            gameRoomService.quiteRoom(appcode,roomid, getUserInfo().getUsername());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
