package com.mogu.game.tetris.config;

public class CT {
	private static Config c=null;
	public static Config gC(){
		return c;
	}
	public static void init(int w,int h){
		if(w==640&&h==960){
			c=new Config();
			return;
		}
		if(w==320&&h==480){
			c=new Config320();
			return;
		}
		c=new Config320();
		return;
	}
}
