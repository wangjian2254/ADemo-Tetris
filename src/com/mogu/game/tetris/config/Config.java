package com.mogu.game.tetris.config;

public class Config {

	public  float all_w=640;
	public  float all_h=960;
	public int movepoint=64;
	public int blockSize=38;
	public int blockSizeMin=20;
	public  float g_btn_tool0_x=517;
	public  float g_btn_tool0_y=84;
	public  float g_board_x=107;
	public  float g_board_y=74;
	public  float g_board_x_m=133;
	public  float g_board_y_m=100;
	
	public int main_menu_btn_x=99;
	public int main_menu_btn_y=136;
	public int main_menu_btn2_y=280;
	public int main_menu_btn3_y=424;
	public int main_menu_btn4_y=569;
	public int main_menu_btn5_y=714;
	
	
	public String basedir="assets/640_960/";
	
	public Config(){
		getPic();
	}
	
	public void getPic(){
		this.game_board=basedir+"game_board.png";
		this.bg_001_light=basedir+"bg_001_light.png";
		this.game_btn_tools1=basedir+"game_btn_tools1.png";
		this.pic_fangkuai_highlight=basedir+"pic_fangkuai_highlight.png";
		this.pic_fangkuai_med=basedir+"pic_fangkuai_med.png";
		
		this.menu_btn_danren1=basedir+"menu_btn_danren1.png";
		this.menu_btn_danren2=basedir+"menu_btn_danren2.png";
		this.menu_btn_duoren1=basedir+"menu_btn_duoren1.png";
		this.menu_btn_duoren2=basedir+"menu_btn_duoren2.png";
		this.menu_btn_daoju1=basedir+"menu_btn_daoju1.png";
		this.menu_btn_daoju2=basedir+"menu_btn_daoju2.png";
		this.menu_btn_shezhi1=basedir+"menu_btn_shezhi1.png";
		this.menu_btn_shezhi2=basedir+"menu_btn_shezhi2.png";
		this.menu_btn_chengjiu1=basedir+"menu_btn_chenjiu1.png";
		this.menu_btn_chengjiu2=basedir+"menu_btn_chenjiu2.png";
	}
	public String game_board=null;
	public String bg_001_light=null;
	public String game_btn_tools1=null;
	public String pic_fangkuai_highlight=null;
	public String pic_fangkuai_med=null;
	
	public String menu_btn_danren1=null;
	public String menu_btn_danren2=null;
	public String menu_btn_duoren1=null;
	public String menu_btn_duoren2=null;
	public String menu_btn_daoju1=null;
	public String menu_btn_daoju2=null;
	public String menu_btn_shezhi1=null;
	public String menu_btn_shezhi2=null;
	public String menu_btn_chengjiu1=null;
	public String menu_btn_chengjiu2=null;
	
	
	
}