package com.liyu.pluginframe.util;

/**
 * Created with IntelliJ IDEA.
 * User: WangJian
 * Date: 13-11-1
 * Time: 上午9:43
 * To change this template use File | Settings | File Templates.
 */
public class UserInfo {
    private String username=null;
    private String nickname="新用户";
    private String jid=null;
    private boolean newroom=false;
    private boolean challengr=false;
    private String c_name=null;
    private String c_jid=null;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public boolean isNewroom() {
        return newroom;
    }

    public void setNewroom(boolean newroom) {
        this.newroom = newroom;
    }

    public boolean isChallengr() {
        return challengr;
    }

    public void setChallengr(boolean challengr) {
        this.challengr = challengr;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_jid() {
        return c_jid;
    }

    public void setC_jid(String c_jid) {
        this.c_jid = c_jid;
    }
}
