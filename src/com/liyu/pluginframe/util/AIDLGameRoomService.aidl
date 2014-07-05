package com.liyu.pluginframe.util;
import com.liyu.pluginframe.util.ICallBack;

/**
 * Created by wangjian2254 on 14-6-29.
 */
interface AIDLGameRoomService {
    void init(String appcode);
    void addCB(in ICallBack icallback);
    void delCB(in ICallBack icallback);
    void  addRoomList(String appcode,String username,String userinfo);
    void  queryRoomList(String appcode,int start,int limit);
    void quiteRoomList(String appcode);
    void getMembersByRoom(String appcode,String roomid);
    void getRoomInfoByRoomId(String appcode,String roomid);

    void addRoom(String appcode,String roomid,String username);
    void quiteRoom(String appcode,String roomid,String username);
    void uploadPoint(String appcode,String roomid,String username,String point);
    void uploadEndPoint(String appcode,String roomid,String username,String point);
    void cleanPoint(String appcode,String roomid,String username);
}
