package com.mogu.game.tetris;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.liyu.pluginframe.beans.UserInfo;
import com.liyu.pluginframe.util.UrlSync;
import com.liyu.pluginframe.util.UrlTask;
import com.mogu.game.tetris.adapter.RoomAdapter;
import com.mogu.game.tetris.model.Player;
import com.mogu.game.tetris.model.Room;
import com.mogu.game.tetris.util.RoomSync;
import com.mogu.game.tetris.util.TetrisConvert;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;

public class GameDaTing extends Activity {

	private ViewPager viewPager; 
	private ArrayList<View> pageViews;  
	private ArrayList<Room> roomList=new ArrayList<Room>();  
	private ArrayList<Room> currentroom=new ArrayList<Room>();  
	 private ImageView imageView;  
	 private ImageView[] imageViews; 
	 private int index=0;
	 // 包裹滑动图片LinearLayout
	 private ViewGroup main;
	 // 包裹小圆点的LinearLayout
	 private ViewGroup group;
	 
	 private View romgrid;
	 public Handler roomDataHandler;
	 private UserInfo user;
	 private GridView grid;
	 LayoutInflater inflater;
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		testData();
		inflater = getLayoutInflater();  
        pageViews = new ArrayList<View>();
		main = (ViewGroup)inflater.inflate(R.layout.dating, null);  
        
        group = (ViewGroup)main.findViewById(R.id.viewGroup);  
        
        viewPager = (ViewPager)main.findViewById(R.id.guidePages); 
        
//        grid =(GridView)romgrid.findViewById(R.id.gridRoomView);
        setContentView(main);
        roomDataHandler=new Handler(){
			@Override
			public void handleMessage(Message msg) {

				// // 接收子线程的消息
				if(msg.arg1==10){
					initRoomData();
			        
				}
				
			}
		};
		initRoomData();
	}
	public void testData(){
		Room r=null;
		Player u=null;
		for(int i=0;i<40;i++){
			r=new Room();
			r.setType(1);
			r.setMaxpeople(8);
			r.setRoomname("room"+i);
			r.setRoomplayer(new ArrayList<Player>());
			r.setUsername("demo00"+i);
			r.setRoomid(i);
			for(int j=0;j<7;j++){
				u=new Player();
				u.setLv(""+j);
				u.setNickname("demo"+j+"0"+i);
				u.setUsername("user"+j+"0"+i);
				r.getRoomplayer().add(u);
			}
			roomList.add(r);
		}
	}
	
	public void getRoomData(){
		UrlSync us=new RoomSync();
		us.setMainContext(this);
		us.setModth(UrlSync.POST);
		us.setToast(true);
			
			us.setToastContentSu("验证信息成功。");
			us.setToastContentFa("验证信息失败。");
		us.setUser(user);
		us.setUri(TetrisConvert.hosturl+"/game/getGameRoom/");
		List<NameValuePair> param=new ArrayList<NameValuePair>();
    	param.add(new BasicNameValuePair("appcode",getPackageName()));
    	param.add(new BasicNameValuePair("username",user.getUsername()));
    	us.setPrarm(param);
		us.setHandler(roomDataHandler);
		UrlTask ut=new UrlTask(this);
		ut.setUrlSync(us);
		ut.start();
	}
	
	
	
	public void initRoomData(){
		imageViews=new ImageView[roomList.size()/9];
        for (int i = 0; i < roomList.size()/9; i++) {  
        	romgrid=inflater.inflate(R.layout.room_grid, null);
        	currentroom=new ArrayList<Room>();
    		for(int j=i*9;j<roomList.size();j++){
    			if(j==(i+1)*9){
    				break;
    			}
    			currentroom.add(roomList.get(j));
    		}
    		grid=(GridView)romgrid.findViewById(R.id.gridRoomView);
    		grid.setAdapter(new RoomAdapter(this, currentroom));
    		
        	pageViews.add(romgrid);
            imageView = new ImageView(this); 
            imageViews[i] = imageView;  
            
            if (i == 0) {  
                //默认选中第一张图片
                imageViews[i].setBackgroundResource(R.drawable.lobby_pic_point1);  
            } else {  
                imageViews[i].setBackgroundResource(R.drawable.lobby_pic_point2);  
            }  
            
            group.addView(imageViews[i]);  
        }  
        
        
        viewPager.setAdapter(new GuidePageAdapter());  
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());  
        changeRoomData();
	}
	
	public void changeRoomData(){
		
//		((RoomAdapter)grid.getAdapter()).notifyDataSetChanged();
		
	}
	
	// 指引页面数据适配器
    class GuidePageAdapter extends PagerAdapter {  
  	  
        @Override  
        public int getCount() {  
            return pageViews.size();  
        }  
  
        @Override  
        public boolean isViewFromObject(View arg0, Object arg1) {  
            return arg0 == arg1;  
        }  
  
        @Override  
        public int getItemPosition(Object object) {  
            // TODO Auto-generated method stub  
            return super.getItemPosition(object);  
        }  
  
        @Override  
        public void destroyItem(View arg0, int arg1, Object arg2) {  
            // TODO Auto-generated method stub  
            ((ViewPager) arg0).removeView(pageViews.get(arg1));  
        }  
  
        @Override  
        public Object instantiateItem(View arg0, int arg1) {  
            // TODO Auto-generated method stub  
//        	index=arg1;
            ((ViewPager) arg0).addView(pageViews.get(arg1));  
//            changeRoomData();
            return pageViews.get(arg1);  
        }  
  
        @Override  
        public void restoreState(Parcelable arg0, ClassLoader arg1) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public Parcelable saveState() {  
            // TODO Auto-generated method stub  
            return null;  
        }  
  
        @Override  
        public void startUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void finishUpdate(View arg0) {  
            // TODO Auto-generated method stub  
  
        }  
    } 
    
    // 指引页面更改事件监听器
    class GuidePageChangeListener implements OnPageChangeListener {  
    	  
        @Override  
        public void onPageScrollStateChanged(int arg0) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageScrolled(int arg0, float arg1, int arg2) {  
            // TODO Auto-generated method stub  
  
        }  
  
        @Override  
        public void onPageSelected(int arg0) {  
            for (int i = 0; i < imageViews.length; i++) {  
                imageViews[arg0].setBackgroundResource(R.drawable.lobby_pic_point1);
                
                if (arg0 != i) {  
                    imageViews[i].setBackgroundResource(R.drawable.lobby_pic_point2);  
                }  
            }
        }  
    } 
}
