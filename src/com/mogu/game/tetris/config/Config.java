package com.mogu.game.tetris.config;

public class Config {

	public  int all_w=640;
	public  int all_h=960;
	public int movepoint=64;
	public int blockSize=38;
	public int blockSizeMin=20;
	public  float g_hold_x=2;
	public  float g_hold_y=84;
	public  float g_next_x=517;
	public  float g_btn_tool0_y=227;
	public  int g_btn_tool0_w=227;
	public  int g_btn_tool0_h=227;
	public  float g_btn_tool1_y=351;
	public  float g_btn_tool2_y=475;
	public  float g_board_x=107;//格子板x
	public  float g_board_y=74;
	public  float g_board_x_m=133;//方块起始x
	public  float g_board_y_m=100;
	
	public int main_menu_btn_x=99;
	public int main_menu_btn_y=136;
	
	public int main_menu_btn2_y=280;
	public int main_menu_btn3_y=424;
	public int main_menu_btn4_y=569;
	public int main_menu_btn5_y=714;
	
	public int topbar_btn_bangzhu_x=4;
	public int topbar_btn_bangzhu_y=5;
	public int topbar_btn_yuezhan_x=516;
	public int topbar_btn_yuezhan_y=5;
	
	public int h_b_x=54;
	public int h_b_y=90;
	public int h_c_x=85;
	public int h_c_y=117;
	public int h_c_y2=207;
	public int h_c_w=480;
	public int h_c_h0=90;
	public int h_c_h=630;
	
	public int game_pic_topbar_x=14;
	public int game_pic_topbar_y=11;
	
	public int g_p_t_1_x=14;
	public int g_p_t_1_w=100;
	public int g_p_t_2_w=90;
	public int g_p_t_2_x=124;
	public int g_p_t_3_w=294;
	public int g_p_t_3_x=220;
	
	public int hold_w=124;
	public int hold_x=2;
	public int hold_y=202;
	public int next_x=517;
	public int hold_f_s=28;
	
	
	public int game_siglemessage_x=19;
	public int game_siglemessage_y=881;
	
	
	public int zt_board_x=84;
	public int zt_board_y=113;
	
	public int zt_btn_w=309;
	public int zt_btn_h=100;
	public int zt_btn_x=168;
	public int zt_btn1_y=164;
	public int zt_btn2_y=266;
	public int zt_btn3_y=368;
	public int zt_btn4_y=470;
	public int zt_btn5_y=572;
	public int zt_btn6_y=674;
	
	public int t_p_dj_w=93;
	public int t_p_dj_h=75;
	
	
	
	
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
		
		this.topbar_btn_bangzhu1=basedir+"topbar_btn_bangzhu1.png";
		this.topbar_btn_bangzhu2=basedir+"topbar_btn_bangzhu2.png";
		this.topbar_btn_yuezhan1=basedir+"topbar_btn_yuezhan1.png";
		this.topbar_btn_yuezhan2=basedir+"topbar_btn_yuezhan2.png";
		this.topbar_btn_zhanting1=basedir+"topbar_btn_zhanting1.png";
		this.topbar_btn_zhanting2=basedir+"topbar_btn_zhanting2.png";
		
		this.topbar_btn_fanhui1=basedir+"topbar_btn_fanhui1.png";
		this.topbar_btn_fanhui2=basedir+"topbar_btn_fanhui2.png";
		this.help_block=basedir+"help_kuang.png";
		
		this.game_pic_topbar=basedir+"game_pic_topbar.png";
		this.game_siglemessage=basedir+"game_siglemessage.png";
		this.speechbubble2=basedir+"speechbubble2.png";
		
		this.zt_block=basedir+"fullscreen_shadow.png";
		this.zt_ban=basedir+"pause_ban.png";
		this.zt_btn1_1=basedir+"pause_btn_jixu1.png";
		this.zt_btn1_2=basedir+"pause_btn_jixu2.png";
		this.zt_btn2_1=basedir+"pause_btn_chongzhi1.png";
		this.zt_btn2_2=basedir+"pause_btn_chongzhi2.png";
		this.zt_btn3_1=basedir+"pause_btn_bangzhu1.png";
		this.zt_btn3_2=basedir+"pause_btn_bangzhu2.png";
		this.zt_btn4_1=basedir+"pause_btn_shezhi1.png";
		this.zt_btn4_2=basedir+"pause_btn_shezhi2.png";
		this.zt_btn5_1=basedir+"pause_btn_daoju1.png";
		this.zt_btn5_2=basedir+"pause_btn_daoju2.png";
		this.zt_btn6_1=basedir+"pause_btn_zhucai1.png";
		this.zt_btn6_2=basedir+"pause_btn_zhucai2.png";
		
		
		this.tools_pic_boom=basedir+"tools_pic_boom.png";
		this.tools_pic_wu=basedir+"tools_pic_wu.png";
		this.tools_pic_fanghuzhao=basedir+"tools_pic_fanghuzhao.png";
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
	
	public String topbar_btn_bangzhu1=null;
	public String topbar_btn_bangzhu2=null;
	public String topbar_btn_yuezhan1=null;
	public String topbar_btn_yuezhan2=null;
	public String topbar_btn_zhanting1=null;
	public String topbar_btn_zhanting2=null;
	public String topbar_btn_fanhui1=null;
	public String topbar_btn_fanhui2=null;
	
	public String help_block=null;
	
	public String game_pic_topbar=null;
	public String game_siglemessage=null;
	public String speechbubble2=null;
	
	public String zt_block="pic_fangkuai_block.png";
	public String zt_ban="pause_ban.png";
	public String zt_btn1_1="pause_btn_jixu1.png";
	public String zt_btn1_2="pause_btn_jixu2.png";
	public String zt_btn2_1="pause_btn_chongzhi1.png";
	public String zt_btn2_2="pause_btn_chongzhi2.png";
	public String zt_btn3_1="pause_btn_bangzhu1.png";
	public String zt_btn3_2="pause_btn_bangzhu2.png";
	public String zt_btn4_1="pause_btn_shezhi1.png";
	public String zt_btn4_2="pause_btn_shezhi2.png";
	public String zt_btn5_1="pause_btn_daoju1.png";
	public String zt_btn5_2="pause_btn_daoju2.png";
	public String zt_btn6_1="pause_btn_zhucai1.png";
	public String zt_btn6_2="pause_btn_zhucai2.png";
	
	
	public String tools_pic_boom=null;
	public String tools_pic_wu=null;
	public String tools_pic_fanghuzhao=null;
	
	
	
	
}
