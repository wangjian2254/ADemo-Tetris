package com.mogu.game.tetris.screen;




import loon.core.graphics.LColor;
import loon.core.graphics.LFont;
import loon.core.graphics.Screen;
import loon.core.graphics.component.LButton;
import loon.core.graphics.component.LMessage;
import loon.core.graphics.opengl.GLEx;
import loon.core.graphics.opengl.LTexture;
import loon.core.graphics.opengl.LTexture.Format;
import loon.core.input.LTouch;
import loon.core.timer.LTimer;
import loon.core.timer.LTimerContext;

import com.mogu.game.tetris.config.CT;




public class Tetris extends Screen {
	

	private int curLevel = 1;

	private boolean gameStart=false;

//	private final static LColor emptyColor = new LColor(120, 120, 190,90),
//			gridColor = new LColor(255, 255, 255,25);

	private LTimer delay;

	private TetrisField gameField;

	private LTexture[] stones = new LTexture[9];
	private LTexture[] stonesMin = new LTexture[9];

	private LTexture  styleImage,brand,game_pic_topbar,game_siglemessage;
	
	private LButton zanting;
	

//	public int xm=Config.getW(106+26);
//	public int ym=Config.getH(74+26);
	public float positionX;

	public float positionY;
	public boolean move=false;
	
	public int jiemian=0;
	
	public MainMenu mainMenu=null;
	
	public Tetris() {
		
		
	}
	


	public void initialize() {
		if(gameStart){
			return;
		}
		gameStart = true;
		// 默认游戏区域（网格）大小为横10,竖24
		gameField = new TetrisField(10, 20);
		gameField.createNextStone(((int) Math.round(Math.random() * 6) + 1));
		gameField.createCurrentStone(((int) Math.round(Math.random() * 6) + 1));
	}
	
	public void alter(LTimerContext timer) {
		// 自动计时
		if (delay.action(timer.getTimeSinceLastUpdate())) {
			// 已初始化并且非游戏失败
			if (gameStart && !gameField.isGameOver()) {
				if (!gameField.incrementPositionY(true)) {
					gameField.createCurrentStone(((int) Math.round(Math
							.random() * 6) + 1));
					if (gameField.hasLines()) {
						curLevel = gameField.getLevel();
					}
				}
				delay.setDelay(300 / curLevel);
			}else if(gameField!=null&&gameField.isGameOver()){
				if(jiemian==0){
					replaceScreen(new MainMenu(), MoveMethod.FROM_LEFT);
//					setScreen(new MainMenu());
					jiemian=1;
				}
			}
		}
	}

	

	

	/**
	 * 点击键盘
	 */
//	public void onKey(KeyEvent e) {
//		int key = e.getKeyCode();
//	if (gameStart) {
//		// 转换方向
//		if (key == KeyEvent.VK_UP) {
//			gameField.rotateStone();
//			// 向右
//		} else if (key == KeyEvent.VK_RIGHT) {
//			gameField.rightPositionX();
//			// 向左
//		} else if (key == KeyEvent.VK_LEFT) {
//			gameField.leftPositionX();
//			// 加速向下
//		} else if (key == KeyEvent.VK_DOWN) {
//			gameField.incrementPositionY(true);
//			// 重启游戏
//		} else if (key == KeyEvent.VK_ESCAPE) {
//			if (gameField != null && gameField.isGameOver()) {
//				initialize();
//			}
//		}
//	} else {
//		// 开始游戏
//		if (key == KeyEvent.VK_ENTER) {
//			initialize();
//		}
//	}
//
//	}

	
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
		g.drawTexture(game_siglemessage, CT.gC().game_siglemessage_x, CT.gC().game_siglemessage_y);
		g.drawTexture(brand, CT.gC().g_board_x,CT.gC().g_board_y);
		g.drawTexture(styleImage, CT.gC().g_btn_tool0_x, CT.gC().g_btn_tool0_y);
		g.drawTexture(styleImage, CT.gC().g_btn_tool1_x, CT.gC().g_btn_tool1_y);
		drawHNText(g,"HOLD",CT.gC().hold_x,CT.gC().hold_y,CT.gC().hold_w,CT.gC().hold_f_s,null);
		drawHNText(g,"NEXT",CT.gC().next_x,CT.gC().hold_y,CT.gC().hold_w,CT.gC().hold_f_s,null);
		drawText(g,"房主",CT.gC().g_p_t_1_x,
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
		
//		g.drawString("Lv" + Integer.toString(gameField.getLevel()), ConfigTool.getConfig().game_pic_topbar_x,
//				ConfigTool.getConfig().game_pic_topbar_y);
//		g.drawString("房主", ConfigTool.getConfig().game_pic_topbar_x,
//				ConfigTool.getConfig().game_pic_topbar_y);
//		g.drawString( Integer.toString(gameField.getPoints()), ConfigTool.getConfig().game_pic_topbar_x+game_pic_topbar.getWidth()/2,
//				ConfigTool.getConfig().game_pic_topbar_y);
		
	}
    
	public void drawGameBackground(GLEx g){
		
		// 绘制游戏方块
		gameField.drawTexture(g, stonesMin,CT.gC().g_btn_tool1_x,CT.gC().g_btn_tool1_y,styleImage.getWidth(),styleImage.getHeight(),CT.gC().blockSizeMin);
		
		
	}

	@Override
	public void draw(GLEx g) {
		if (isOnLoadComplete()) {
			
			drawBackground(g);
		// TODO Auto-generated method stub
		if (gameStart) {
			
			int x, y;
			// 默认游戏区域（网格）大小为横10,竖24
			int[][] arrayStones = gameField.getStonePosition();
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
				g.setColor(LColor.white);
				g.drawString("GAME OVER", 120, 160);
				return;
			}
			
			drawGameBackground(g);
		} else {
			g.setColor(LColor.white);
			g.drawString("GAME START", 110, 160);
			g.drawString("请按下 [ENTER]", 105, 200);
		}
		}
	}

	@Override
	public void touchDown(LTouch e) {
		// TODO Auto-generated method stub
		positionX=e.getX();
		positionY=e.getY();
		move=false;
	}

	@Override
	public void touchUp(LTouch e) {
		// TODO Auto-generated method stub
		if(gameStart&&!gameField.isGameOver()){
			
			if(!move){
				gameField.rotateStone();
			}
			
		}else{
			
			initialize();
		}
		positionX=0;
		positionY=0;
	}

	@Override
	public void touchMove(LTouch e) {
		move=true;
		// TODO Auto-generated method stub
		if(Math.abs(e.getX()-positionX)>CT.gC().movepoint){
//			int num=(int)(Math.abs(e.getX()-positionX)-ConfigTool.getConfig().movepoint)/ConfigTool.getConfig().movepoint;
//			for(int i=0;i<num;i++){
				if(e.getX()-positionX>0){
					
					gameField.rightPositionX();
				}else{
					gameField.leftPositionX();
				}
//			}
				positionX=e.getX();
				positionY=e.getY();
		}
		
	}

	

	@Override
	public void onLoad() {
		brand = new LTexture(CT.gC().game_board);
		
		LTexture[] btn1={new LTexture(CT.gC().topbar_btn_zhanting1),new LTexture(CT.gC().topbar_btn_zhanting2)};
		zanting=new  LButton(btn1, null, btn1[0].getWidth(), btn1[0].getHeight(), CT.gC().topbar_btn_yuezhan_x, CT.gC().topbar_btn_yuezhan_y){
			@Override
			public void doClick(){
			}
		};
		add(zanting);
		
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
		delay = new LTimer(100);
		setBackground(CT.gC().bg_001_light);
	}

	@Override
	public void touchDrag(LTouch e) {
		// TODO Auto-generated method stub
		
	}

	

//	public static void main(String[] args) {
//		GameScene frame = new GameScene("����˹����", 320, 480);
//		Deploy deploy = frame.getDeploy();
//		deploy.setScreen(new Main());
//		deploy.setShowFPS(true);
//		deploy.setLogo(false);
//		deploy.setFPS(100);
//		deploy.mainLoop();
//		frame.showFrame();
//	}
}
