package com.mogu.game.tetris.model;

import java.util.HashMap;
import java.util.Map;

import com.mogu.game.tetris.screen.TetrisField;

import loon.core.graphics.opengl.GLEx;
import loon.core.graphics.opengl.LTexture;
import loon.core.graphics.opengl.LTextures;
import loon.core.timer.LTimer;

public  class DaoJu {

	public float x0=0,y0=0;//贴图位置
	public float x1,y1,x2,y2;//贴图位置
	
	private LTimer delay;//持续时间
	private int used;//道具状态
	private int time;//道具显示回合
	private String name;//道具名称
	private int type;//道具类型
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public   void commit(TetrisField gameField){
		
	};//道具行为
	public   void commit(GLEx g){
		
	};//道具行为
	public LTimer getDelay() {
		return delay;
	}
	public void setDelay(LTimer delay) {
		this.delay = delay;
	}
	public int getUsed() {
		return used;
	}
	public void setUsed(int used) {
		this.used = used;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public float getX0() {
		return x0;
	}
	public void setX0(float x0) {
		this.x0 = x0;
	}
	public float getY0() {
		return y0;
	}
	public void setY0(float y0) {
		this.y0 = y0;
	}
	public void setPos(float x,float y,int w,int h){
		
	}
	public LTexture getImg() {
		return null;
	}
	public void setImg(LTexture i){
	}
	public boolean isClick(float x,float y){
		if(x>x1&&x<x2&&y>y1&&y<y2){
			return true;
		}else{
			return false;
		}
	}
}
