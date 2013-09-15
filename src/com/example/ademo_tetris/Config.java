package com.example.ademo_tetris;

public class Config {

	public static  float suoxiao_x=1;
	public static  float suoxiao_y=1;
	public static  float suoxiao_width=1;
	public static  float suoxiao_height=1;
	
	public static int getW(int w){
		return (int)(w*suoxiao_x);
	}
	public static int getH(int h){
		return (int)(h*suoxiao_y);
	}
}
