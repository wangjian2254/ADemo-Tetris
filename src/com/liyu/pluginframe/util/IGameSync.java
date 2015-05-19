package com.liyu.pluginframe.util;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: WangJian
 * Date: 13-11-1
 * Time: 上午9:43
 * To change this template use File | Settings | File Templates.
 */
public interface IGameSync {
    /**
     * 接收到开始游戏
     * by:王健 at:2015-5-19
     */
    public void syncStartGame();

    /**
     * 接收游戏进度、积分、当前得分
     * by:王健 at:2015-5-19
     * @param user 玩家
     * @param point  得分
     */
    public void syncGamePoints(String user, String point);

    /**
     * 将游戏的最终得分，以Map的形式，同步
     * by:王健 at:2015-5-19
     * @param userpointmap  得分Map
     */
    public void syncEndGamePoints(Map<String, String > userpointmap);

    /**
     * 服务器端发来游戏结束的信号
     * by:王健 at:2015-5-19
     */
    public void syncEndGame();

    /**
     * 自定义游戏数据的同步，同步给本客户端的
     * by:王健 at:2015-5-19
     * @param from   发起人
     * @param jsonObject 自定义信息
     */
    public void syncGameData(String from, JSONObject jsonObject);

    /**
     * 自定义游戏数据的同步，同步给一部分人
     * by:王健 at:2015-5-19
     * @param from 发起人
     * @param to  接收人数组
     * @param jsonObject 自定义数据
     */
    public void syncGameData(String from, String[] to, JSONObject jsonObject);

    /**
     * 同步游戏道具的使用，本客户端作为受体
     * by:王健 at:2015-5-19
     * @param from 发起人
     * @param property_flag 道具标示
     */
    public void syncGamePropertyInfo(String from, String property_flag);

    /**
     * 同步游戏道具的使用，针对一批用户的道具
     * by:王健 at:2015-5-19
     * @param from 发起人
     * @param to 接收人数组
     * @param property_flag 道具标示
     */
    public void syncGamePropertyInfo(String from, String[] to, String property_flag);


}