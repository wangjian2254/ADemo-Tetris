package com.mogu.game.tetris.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.os.Environment;


public class TetrisConvert {

//	public static final String hosturl="http://sell3.zxxsbook.com"; 

	public static final String hosturl="http://192.168.1.110:8000"; 
	
	public static final String DATABASE_NAME="tetris"; 
	public static final int DATABASE_VERSON=1; 
	public static final String APP_VERSON="0.1"; 
	
	public static String gamePath   = Environment.getExternalStorageDirectory()+"/Info/game/tetris/";

	public static final String testNum="&verson="+APP_VERSON;
	
	public static String uname=null;
	public static String upwd=null;
	
	
	public static DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	
	public static boolean hasImage=true;
	public static boolean newtel=false;
	public static String imgreg = "\\[\\*[A-Za-z]*/[-_0-9A-Za-z]*/[_A-Za-z0-9]*\\*\\]";
	

}
