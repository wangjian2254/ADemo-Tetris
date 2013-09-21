package com.mogu.game.tetris.model;

import java.util.ArrayList;
import java.util.List;

public class Room {

	private int roomid=0;
	private int type=0;
	private String username=null;
	private String roompassword=null;
	private int maxpeople=0;
	private String roomname=null;
	private List<Player> roomplayer=new ArrayList<Player>();
	public int getRoomid() {
		return roomid;
	}
	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRoompassword() {
		return roompassword;
	}
	public void setRoompassword(String roompassword) {
		this.roompassword = roompassword;
	}
	public int getMaxpeople() {
		return maxpeople;
	}
	public void setMaxpeople(int maxpeople) {
		this.maxpeople = maxpeople;
	}
	public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	public List<Player> getRoomplayer() {
		return roomplayer;
	}
	public void setRoomplayer(List<Player> roomplayer) {
		this.roomplayer = roomplayer;
	}
}
