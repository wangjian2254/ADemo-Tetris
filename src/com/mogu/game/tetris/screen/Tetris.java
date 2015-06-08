package com.mogu.game.tetris.screen;




import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.widget.Toast;
import com.liyu.pluginframe.util.IGameSync;
import loon.LGame;
import loon.core.LSystem;
import loon.core.graphics.LColor;
import loon.core.graphics.LFont;
import loon.core.graphics.Screen;
import loon.core.graphics.component.LButton;
import loon.core.graphics.opengl.GLEx;
import loon.core.graphics.opengl.LTexture;
import loon.core.graphics.opengl.LTextures;
import loon.core.input.LTouch;
import loon.core.input.LTransition;
import loon.core.timer.LTimer;
import loon.core.timer.LTimerContext;

import com.liyu.pluginframe.util.MainDataTool;
import com.mogu.game.tetris.config.CT;
import com.mogu.game.tetris.model.DaoJu;
import com.mogu.game.tetris.model.ZhaDan;
import com.mogu.game.tetris.sprite.ZhaDanSprite;
import org.json.JSONObject;


public class Tetris extends Screen {
	

	private int curLevel = 1;

	private boolean gameStart=false;
	private boolean writeresult=false;

	private final static LColor emptyColor = new LColor(0f, 0f, 0f,0.3f);
//			gridColor = new LColor(255, 255, 255,25);

	private LTimer delay;

	private Map<String,String> user_points = new HashMap<String, String>();

	private TetrisField gameField;
	private LTexture[] stones = new LTexture[9];
	private LTexture[] stonesMin = new LTexture[9];

	private LTexture  zd,w,fanghuzhao,styleImage,pause_btn,brand,game_pic_topbar,game_siglemessage,pause_board,black_block;
	
	private LButton zanting,jixu,reset,bangzhu,shezhi,daoju,mainmenu;
	

	public float positionX;

	public float positionY;
	public boolean move=false;
	public boolean start=false;
	
	public boolean isDo=false;
	public int jiemian=0;

	private ArrayList<String> gamemsg=new ArrayList<String>();
	private int msg_time = 0;
	

	public DaoJu[] djs=new DaoJu[3];
	public DaoJu currentDaoJu=null;
	private ZhaDanSprite zhadan = null; /* 精灵：炸弹 */

	private StringBuffer sb=new StringBuffer();

    private boolean fasttools=false;
	   

//	private int num=0;
//	private int num1=0;
	Random random = new Random();
	@Override
	public LTransition onTransition() {
		return LTransition.newCrossRandom();
	}

	public Tetris() {
		djs[0]=new ZhaDan();
		djs[0].setPos(CT.gC().g_next_x,CT.gC().g_btn_tool0_y,CT.gC().hold_w,CT.gC().hold_w);
		djs[1]=new ZhaDan();
		djs[1].setPos(CT.gC().g_next_x,CT.gC().g_btn_tool1_y,CT.gC().hold_w,CT.gC().hold_w);
		djs[2]=new ZhaDan();
		djs[2].setPos(CT.gC().g_next_x,CT.gC().g_btn_tool2_y,CT.gC().hold_w,CT.gC().hold_w);

		MainDataTool.setIGameSync(new IGameSync() {
			@Override
			public void syncStartGame() {
				if(!gameStart){
					initialize();
					return;
				}
			}

			@Override
			public void syncGamePoints(String user, String point) {
				user_points.put(user, point);
			}

			@Override
			public void syncEndGamePoints(String user, String point) {
				user_points.put(user, point);
				gamemsg.add(user + " 以" + point + "分结束游戏。");
				msg_time+=3;
			}

			@Override
			public void syncEndGame() {

			}

			@Override
			public void syncEndGame(String from) {

			}

			@Override
			public void syncGameData(String from, JSONObject jsonObject) {

			}

			@Override
			public void syncGameData(String from, String[] to, JSONObject jsonObject) {

			}

			@Override
			public void syncGamePropertyInfo(String from, String property_flag) {

			}

			@Override
			public void syncGamePropertyInfo(String from, String[] to, String property_flag) {
				if("zhadan".equals(property_flag)){
//					Toast.makeText(LSystem.screenActivity, to[0]+" 使用炸弹~~", Toast.LENGTH_SHORT).show();
					gamemsg.add(to[0]+" 使用炸弹~~");
					msg_time+=3;

				}
			}

			@Override
			public void syncChat(String from, String msg) {

//				user_points.put(from, ""+msg.length());
			}

			@Override
			public void syncChat(String from, String to, String msg) {

			}

			@Override
			public void syncMemberChange(String user, boolean in) {
				   if(!in){
					   user_points.put(user, "remove");
				   }
			}

			@Override
			public void syncQuiteRoomByUser(String user) {
				gamemsg.add(user+":把我踢出了房间");
				msg_time+=3;
			}
		});
	}
	
	@Override
	public void onLoad() {
		brand = new LTexture(CT.gC().game_board);
		black_block = LTextures.loadTexture(CT.gC().zt_block).getSubTexture(19, 0, 19, 19).scale(CT.gC().all_w, CT.gC().all_h);
		
		pause_btn=LTextures.loadTexture(CT.gC().topbar_btn_zhanting1);
		
		LTexture[] btn1={new LTexture(CT.gC().topbar_btn_zhanting1),new LTexture(CT.gC().topbar_btn_zhanting2)};
		zanting=new  LButton(btn1, null, btn1[0].getWidth(), btn1[0].getHeight(), CT.gC().topbar_btn_yuezhan_x, CT.gC().topbar_btn_yuezhan_y){
			@Override
			public void doClick(){
				delay.stop();
				setShowPause(true);
				
			}
		};
		add(zanting);
		
		LTexture[] ztn1={LTextures.loadTexture(CT.gC().zt_btn1_1),LTextures.loadTexture(CT.gC().zt_btn1_2)};
		jixu=new LButton(ztn1,null,CT.gC().zt_btn_w,CT.gC().zt_btn_h,CT.gC().zt_btn_x,CT.gC().zt_btn1_y){
			@Override
			public void doClick(){
				delay.start();
				setShowPause(false);
			}
		};
		
		LTexture[] ztn2={LTextures.loadTexture(CT.gC().zt_btn2_1),LTextures.loadTexture(CT.gC().zt_btn2_2)};
		reset=new LButton(ztn2,null,CT.gC().zt_btn_w,CT.gC().zt_btn_h,CT.gC().zt_btn_x,CT.gC().zt_btn2_y){
			@Override
			public void doClick(){
			}
		};
		LTexture[] ztn3={LTextures.loadTexture(CT.gC().zt_btn3_1),LTextures.loadTexture(CT.gC().zt_btn3_2)};
		bangzhu=new LButton(ztn3,null,CT.gC().zt_btn_w,CT.gC().zt_btn_h,CT.gC().zt_btn_x,CT.gC().zt_btn3_y){
			@Override
			public void doClick(){
				replaceScreen(new Help(Tetris.this), MoveMethod.FROM_RIGHT);
			}
		};
		LTexture[] ztn4={LTextures.loadTexture(CT.gC().zt_btn4_1),LTextures.loadTexture(CT.gC().zt_btn4_2)};
		shezhi=new LButton(ztn4,null,CT.gC().zt_btn_w,CT.gC().zt_btn_h,CT.gC().zt_btn_x,CT.gC().zt_btn4_y){
			@Override
			public void doClick(){
			}
		};
		LTexture[] ztn5={LTextures.loadTexture(CT.gC().zt_btn5_1),LTextures.loadTexture(CT.gC().zt_btn5_2)};
		daoju=new LButton(ztn5,null,CT.gC().zt_btn_w,CT.gC().zt_btn_h,CT.gC().zt_btn_x,CT.gC().zt_btn5_y){
			@Override
			public void doClick(){
			}
		};
		LTexture[] ztn6={LTextures.loadTexture(CT.gC().zt_btn6_1),LTextures.loadTexture(CT.gC().zt_btn6_2)};
		mainmenu=new LButton(ztn6,null,CT.gC().zt_btn_w,CT.gC().zt_btn_h,CT.gC().zt_btn_x,CT.gC().zt_btn6_y){
			@Override
			public void doClick(){
                LSystem.screenActivity.finish();
			}
		};
		setShowPause(false);
		add(jixu);
		add(reset);
		add(bangzhu);
		add(shezhi);
		add(daoju);
		add(mainmenu);
		//
		
		
		zd=LTextures.loadTexture(CT.gC().tools_pic_boom);
		w=LTextures.loadTexture(CT.gC().tools_pic_wu);
		fanghuzhao=LTextures.loadTexture(CT.gC().tools_pic_fanghuzhao);
		pause_board = new LTexture(CT.gC().zt_ban);
		game_pic_topbar = new LTexture(CT.gC().game_pic_topbar);
		game_siglemessage = new LTexture(CT.gC().game_siglemessage);
		// 提示背景
		styleImage = new LTexture(CT.gC().game_btn_tools1);
		LTexture b=new LTexture(CT.gC().pic_fangkuai_highlight);
		LTexture b2=new LTexture(CT.gC().pic_fangkuai_med);
		// 俄罗斯方块小图
//				LImage[] blocks = new LTexture(8, blockSize, blockSize, true);
		for (int i = 0; i < 8; i++) {
			stones[i + 1] = b.getSubTexture(i*CT.gC().blockSize, 0, CT.gC().blockSize, CT.gC().blockSize);
			stonesMin[i + 1] = b2.getSubTexture(i*CT.gC().blockSizeMin, 0, CT.gC().blockSizeMin, CT.gC().blockSizeMin);
		}
		delay = new LTimer(1000);
		setBackground(CT.gC().bg_001_light);
		
		
		
		/* 加载精灵资源 炸弹 */  
		   
		zhadan = new ZhaDanSprite(CT.gC().pic_fangkuai_baozha, CT.gC().zhadanW, CT.gC().zhadanW);  
		   
		    
		   
		  /* 设置精灵初始位置 */  
		   
		zhadan.setLocation(50, 250);






	}


	/**
	 * 游戏正式开始
	 */
	public void initialize() {
		if(gameStart){
			return;
		}
		gameStart = true;
		// 默认游戏区域（网格）大小为横10,竖24
		gameField = new TetrisField(10, 20);
		zhadan.setTF(gameField);
		gameField.createNextStone(((int) Math.round(Math.random() * 6) + 1));
		gameField.createCurrentStone(((int) Math.round(Math.random() * 6) + 1));
		
		writeresult=false;

		MainDataTool.startGame();
	}
	
	public void alter(LTimerContext timer) {
		if(!start){
			start=true;
		}
		// 自动计时
		if (delay!=null&&delay.action(timer.getTimeSinceLastUpdate())) {
//            MainDataTool.setPos(0,1,10,220,120,LSystem.screenActivity);
//            MainDataTool.setPos(1,1,10,354,80,LSystem.screenActivity);
//            MainDataTool.setPos(2,1,10,508,80,LSystem.screenActivity);
//            MainDataTool.setPos(3,1,10,602,100,LSystem.screenActivity);
//
//            MainDataTool.setPos(4,1,390,476,100,LSystem.screenActivity);
//            MainDataTool.setPos(5,1,390,602,80,LSystem.screenActivity);

//			if(num%20==0){
//				num1=Math.abs(random.nextInt())%10;
//			}
//			num++;
			// 已初始化并且非游戏失败
			if (gameStart && !gameField.isGameOver()) {
				if(msg_time>0){
					msg_time--;
					if(msg_time%3==0&&gamemsg.size()>0){
						gamemsg.remove(0);
					}
				}


				//炸弹道具
				if(currentDaoJu!=null){
					
					if(currentDaoJu.getUsed()==0){
						currentDaoJu.commit(gameField,zhadan);
//						setZhaDanLocation(100, 100, true);
					}
					if(currentDaoJu.getUsed()==2){
						currentDaoJu=null;
					}
				}
				if (!gameField.incrementPositionY(true)) {
                    fasttools = false;
					gameField.createCurrentStone(((int) Math.round(Math
							.random() * 6) + 1));
					if (gameField.hasLines()) {
						curLevel = gameField.getLevel();
					}
//                        MainDataTool.setPos(CT.gC().game_siglemessage_x+gameField.getPoints(),CT.gC().game_siglemessage_y,180,40,0xFFFFFFFF,LSystem.screenActivity);

                    MainDataTool.uploadGamePoint(String.valueOf(gameField.getPoints()));
//					for(int i=0;i<(gameField.getPoints()+10)*10;i++){
//						sb.append("s");
//					}
//					MainDataTool.sendChatToAllUser(sb.toString());
				}
                if(fasttools){
                    delay.setDelay(50);
                } else{
                    if(curLevel>7){
                        delay.setDelay(300);
                    } else{
                        delay.setDelay(1000 - curLevel*100);
                    }

                }
				
				
			}else if(gameField!=null&&gameField.isGameOver()){
				if(jiemian==0){
					LSystem.screenActivity.finish();
//					replaceScreen(new MainMenu(), MoveMethod.FROM_LEFT);
//					setScreen(new MainMenu());
					jiemian=1;
                    MainDataTool.uploadEndGamePoint(String.valueOf(gameField.getPoints()));

				}
			}
		}
	}

	

	

	
	public void drawText(GLEx g,String text,int x,int y,int w,int h,LColor c){
		LFont old = g.getFont();
		LFont font=LFont.getFont(h/2);
		g.setFont(font);
		if(c==null){
			g.setColor(LColor.white);
		}
		g.drawString(
				text,
				x + (w - font.stringWidth(text)) / 2,
				y + (h - font.getLineHeight()) / 2
						+ font.getLineHeight());
		g.setFont(old);
		g.resetColor();
	}
	public void drawHNText(GLEx g,String text,int x,int y,int w,int h,LColor c){
		LFont old = g.getFont();
		LFont font=LFont.getFont(h);
		g.setFont(font);
		if(c==null){
			c=LColor.white;
		}
		g.drawStyleString(
				text,
				x + (w - font.stringWidth(text)) / 2,
				y + font.getLineHeight(),c,c);
		g.setFont(old);
		g.resetColor();
	}
	
	public void drawBackground(GLEx g){
		g.drawTexture(game_pic_topbar, CT.gC().game_pic_topbar_x, CT.gC().game_pic_topbar_y);
		g.drawTexture(pause_btn, CT.gC().topbar_btn_yuezhan_x, CT.gC().topbar_btn_yuezhan_y);
		
		g.drawTexture(game_siglemessage, CT.gC().game_siglemessage_x, CT.gC().game_siglemessage_y);
//		drawText(g,msg[num1],CT.gC().game_siglemessage_x, CT.gC().game_siglemessage_y,game_siglemessage.getWidth(),game_siglemessage.getHeight(),null);
		g.drawTexture(brand, CT.gC().g_board_x,CT.gC().g_board_y);
		g.drawTexture(styleImage, CT.gC().g_hold_x, CT.gC().g_hold_y);
		g.drawTexture(styleImage, CT.gC().g_next_x, CT.gC().g_hold_y);
		
		g.drawTexture(styleImage, CT.gC().g_next_x, CT.gC().g_btn_tool0_y);
		g.drawTexture(styleImage, CT.gC().g_next_x, CT.gC().g_btn_tool1_y);
		g.drawTexture(styleImage, CT.gC().g_next_x, CT.gC().g_btn_tool2_y);
		
		for(int i=0;i<djs.length;i++){
			if(djs[i]!=null){
				g.drawTexture(getDJ(djs[i].getType()), djs[i].getX0(), djs[i].getY0());
			}
		}
		
		if(currentDaoJu!=null){
			currentDaoJu.commit(g);
		}
		drawHNText(g,"HOLD",CT.gC().hold_x,CT.gC().hold_y,CT.gC().hold_w,CT.gC().hold_f_s,null);
		drawHNText(g,"NEXT",CT.gC().next_x,CT.gC().hold_y,CT.gC().hold_w,CT.gC().hold_f_s,null);
		drawText(g,MainDataTool.getUserInfo().getNickname(),CT.gC().g_p_t_1_x,
				CT.gC().game_pic_topbar_y,CT.gC().g_p_t_1_w,game_pic_topbar.getHeight(),null);
		int lv=0;
		int point=0;
		if(gameField!=null){
			lv=gameField.getLevel();
			point=gameField.getPoints();
		}
		drawText(g,"Lv" + Integer.toString(lv),CT.gC().g_p_t_2_x,
				CT.gC().game_pic_topbar_y,CT.gC().g_p_t_2_w,game_pic_topbar.getHeight(),null);
		
		drawText(g,Integer.toString(point),CT.gC().g_p_t_3_x,
				CT.gC().game_pic_topbar_y,CT.gC().g_p_t_3_w,game_pic_topbar.getHeight(),null);

		int i = 2;
		String default_point="";
		for(String username: MainDataTool.getMembers()){
			drawHNText(g, username, CT.gC().hold_x,CT.gC().hold_y + i* CT.gC().hold_f_s, (int)(CT.gC().hold_w*0.6), (int)(CT.gC().hold_f_s*0.6),null);
			drawHNText(g, user_points.containsKey(username)?user_points.get(username):default_point, (int)(CT.gC().hold_x+CT.gC().hold_w*0.6),CT.gC().hold_y + i* CT.gC().hold_f_s, (int)(CT.gC().hold_w*0.6), (int)(CT.gC().hold_f_s*0.6),null);
//			g.drawString(username,CT.gC().hold_x,CT.gC().hold_y + i* CT.gC().hold_f_s , LColor.white);
//			g.drawString(user_points.containsKey(username)?user_points.get(username):default_point, CT.gC().hold_x+CT.gC().hold_f_s,CT.gC().hold_y + i* CT.gC().hold_f_s , LColor.white);

			i++;
		}
		if(gamemsg.size()>0&&msg_time>0){
			drawHNText(g, gamemsg.get(0), CT.gC().game_siglemessage_x, CT.gC().game_siglemessage_y,CT.gC().hold_w*3, CT.gC().hold_f_s,null);
		}

		
//		g.drawString("Lv" + Integer.toString(gameField.getLevel()), ConfigTool.getConfig().game_pic_topbar_x,
//				ConfigTool.getConfig().game_pic_topbar_y);
//		g.drawString("房主", ConfigTool.getConfig().game_pic_topbar_x,
//				ConfigTool.getConfig().game_pic_topbar_y);
//		g.drawString( Integer.toString(gameField.getPoints()), ConfigTool.getConfig().game_pic_topbar_x+game_pic_topbar.getWidth()/2,
//				ConfigTool.getConfig().game_pic_topbar_y);
		
	}
    
	public void drawGameBackground(GLEx g){
		
		// 绘制游戏方块
		gameField.drawTexture(g, stonesMin,CT.gC().g_next_x,CT.gC().g_hold_y,styleImage.getWidth(),styleImage.getHeight(),CT.gC().blockSizeMin);
		
		if(gameStart&&!delay.isActive()){

			g.drawTexture(black_block, 0, 0);
			g.drawTexture(pause_board, CT.gC().zt_board_x, CT.gC().zt_board_y);
		}
		if(currentDaoJu!=null){
			currentDaoJu.commit(g);
		}else{
		}
		zhadan.createUI(g);
		zhadan.update();
		
		
	}

	@Override
	public void draw(GLEx g) {
		if (isOnLoadComplete()) {
			
			drawBackground(g);
		// TODO Auto-generated method stub
		if (gameStart&&gameField!=null) {
			
			int x, y;
			// 默认游戏区域（网格）大小为横10,竖24
			int[][] arrayStones = gameField.getAllStonePosition();
//			g.setColor(emptyColor);
//			g.fillRect(0,0,gameField.getRow() * blockSize, gameField.getCol() * blockSize);
			for (x = 0; x < gameField.getRow(); x++) {
				for (y = 0; y < gameField.getCol(); y++) {
					
//					g.setColor(gridColor);
//					g.drawRect(x * blockSize, y * blockSize, blockSize, blockSize);
					if (arrayStones[x][y] != 0) {
						
						g.drawTexture(stones[arrayStones[x][y]], x * CT.gC().blockSize+CT.gC().g_board_x_m, y * CT.gC().blockSize+CT.gC().g_board_y_m);
					}
				}
			}


			if (gameField.isGameOver()) {
				if(!writeresult){
					MainDataTool.setResultString1(gameField.getPoints(), "俄罗斯方块获取新的积分！", MainDataTool.Model.WEEKLY);
					writeresult=true;
				}
				g.setColor(LColor.white);
				g.drawString("游戏结束，恭喜 "+MainDataTool.getUserInfo().getNickname()+" 获得积分："+gameField.getPoints(), 120, 160);
				return;
			}
			
			drawGameBackground(g);
		} else {
			g.setColor(LColor.white);
//			g.drawString("", 110, 160);
			g.drawString("轻触屏幕，游戏开始", 105, 200);
		}
		}
	}

	@Override
	public void touchDown(LTouch e) {
		if(!start){
			return;
		}
        if(fasttools){
            return;
        }
		positionX=e.getX();
		positionY=e.getY();
		move=false;
		if(positionX>CT.gC().g_board_x&&positionX<CT.gC().g_next_x&&positionY>CT.gC().g_board_y){
			isDo=true;
		}else{
			isDo=false;
		}
	}

	@Override
	public void touchUp(LTouch e) {
		if(!start){
			return;
		}
        if(fasttools){
            return;
        }
		if(gameStart&&!gameField.isGameOver()){
			
			if(!move&&isDo){
				gameField.rotateStone();
			}
			if(!move){
				
				int i=0;
				for(DaoJu d:djs){
					if(d!=null&&d.isClick(e.getX(), e.getY())){
						currentDaoJu=d;
						break;
					}
					i++;
				}
				if(currentDaoJu!=null){
					djs[i]=null;
					MainDataTool.pushPropertyDataToUser("zhadan", MainDataTool.getUserInfo().getUsername());
				}
				
			}
			
		}else{
			
			initialize();
		}
		positionX=0;
		positionY=0;
		isDo=false;
	}

	@Override
	public void touchMove(LTouch e) {
		if(!start){
			return;
		}
        if(fasttools){
           return;
        }
		if(isDo){
			move=true;
			if(Math.abs(e.getX()-positionX)>CT.gC().movepoint){
					if(e.getX()-positionX>0){
						
						gameField.rightPositionX();
					}else{
						gameField.leftPositionX();
					}
			}else if(Math.abs(e.getY()-positionY)>CT.gC().movepoint){
				if(e.getY()-positionY>0){
					gameField.incrementPositionY(true);
                    fasttools=true;
				}
			}else{
				return;
			}
			positionX=e.getX();
			positionY=e.getY();
		}
	}

	public void setShowPause(boolean v){
		zanting.setVisible(!v);
		jixu.setVisible(v);
		reset.setVisible(v);
		bangzhu.setVisible(v);
		shezhi.setVisible(v);
		daoju.setVisible(v);
		mainmenu.setVisible(v);
	}
	

	

	@Override
	public void touchDrag(LTouch e) {
		if(!start){
			return;
		}
		
	}
	
	

	public LTexture getDJ(int i){
		LTexture l=null;
		switch (i) {
		case 1:
			l=zd;
			break;
		case 2:
			l=w;
			break;
		case 3:
			l=fanghuzhao;
			break;

		default:
			break;
		}
		return l;
	}

}
