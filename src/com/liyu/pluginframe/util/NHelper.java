package com.liyu.pluginframe.util;

import android.app.*;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class NHelper {
    public static  int SHORT=1000;
    public static int LONG=3000;
    public static int NEWMESSAGE=1200;
    public static final String NOTIFICATIONID ="notificationID";
	private static NHelper instance;
	public static Handler mHandler ;
    private static Map<String,Integer> nationidmap = new HashMap<String,Integer>();

	private boolean inited = false;

	private int statusBarHeight;
	private LinearLayout notiView;
	private TextView tv;
	private boolean showStatused = false;
	WindowManager mWindowManager;
    Context con;
	DisplayMetrics mDisplayMetrics;
	Object popWinLock = new Object();
	private int mNotifacationIDIndex = 1000;

	boolean getStatusing = false;

    private int x=0;
    private int y=0;
    private int w=120;
    private int h=24;
    private int color =0;


	/**
	 * 单例类，
	 */
	private NHelper() {

	}

	public static NHelper getNHelper() {
		if (instance == null) {
			instance = new NHelper();
		}
		return instance;
	}

    public static int getIDByKey(String key){
        if(nationidmap.containsKey(key)){
            return nationidmap.get(key);
        }else{
            nationidmap.put(key,++instance.mNotifacationIDIndex);
            return nationidmap.get(key);
        }
    }
    
    

	/**
	 * 初始化,请在主activity的onCreate中调用此方法
	 * 
	 * @param activity
	 */
	public void init(Activity activity) {

		if (!inited) {
            if (notiView == null) {


                notiView=new LinearLayout(activity);
                notiView.setOrientation(0);
                FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
                params.gravity=Gravity.BOTTOM|Gravity.RIGHT;
                tv=new TextView(activity);
                notiView.addView(tv, params);

                statusBarHeight = getStatusBarHeight(activity);
                mDisplayMetrics = new DisplayMetrics();

        


            }

			inited = true;
		}
	}

	/**
	 * 判断是否为平板模式
	 * 
	 * @param context
	 * @return
	 */
	public boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	/**
	 * 获取状态栏高度
	 * 
	 * @param context
	 * @return
	 */
	public int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			if (isTablet(context)) {
				field = c.getField("system_bar_height");
			} else {
				field = c.getField("status_bar_height");
			}
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * 获取是否为当前活动应用
	 * 
	 * @param context
	 * @return
	 */
	public boolean isOnTop(Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		if (context.getPackageName().equals(cn.getPackageName())) {
			return true;
		} else {
			return false;
		}
	}

	Runnable removePopWindow = new Runnable() {

		@Override
		public void run() {
			synchronized (popWinLock) {
				if (showStatused) {
					mWindowManager.removeView(notiView);
					showStatused = false;
				}
			}
		}
	};

    public void setStatus(Context context,int x,int y,int w,int h,int color){
        if(con!=context){
            synchronized (popWinLock) {
                if (showStatused) {
                    mWindowManager.removeView(notiView);
                    showStatused = false;
                }
            }
            mWindowManager = (WindowManager) context
                    .getSystemService("window");
            mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
            con=context;
            addViewToWindow();
        }

        if(this.x==x&&this.y==y&&this.w==w&&this.h==h&&this.color==color){
            return;
        }
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
        this.color=color;
//        RelativeLayout background = (RelativeLayout) notiView.findViewById(R.id.background);
//        background.setBackgroundColor(color);
//        TextView txtView = (TextView) notiView.findViewWithTag("title");
//        TextView txtView = (TextView) notiView.findViewById(R.id.title);
        tv.setTextColor(color);
        synchronized (popWinLock) {
//            if (showStatused) {
//                mWindowManager.removeView(notiView);
                WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
                wmParams.format = PixelFormat.RGBA_8888;
                wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
                wmParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

                wmParams.width = w;
                wmParams.height = h;

//		if (isTablet(context)) {
//			wmParams.gravity = Gravity.BOTTOM | Gravity.LEFT;
//			wmParams.y = -statusBarHeight;
//			wmParams.x = (mDisplayMetrics.widthPixels - wmParams.width) / 2;
//		} else {
                wmParams.gravity = Gravity.TOP | Gravity.LEFT;
                wmParams.x = x;
                wmParams.y = y;
                mWindowManager.updateViewLayout(notiView,wmParams);
//                mWindowManager.addView(notiView, wmParams);
//		}
//            }
        }
//        showStatus(context, message, time);
    }

	/**
	 * 显示状态信息，不能点击
	 * 
	 * @param context
	 * @param message
	 */
	public void showStatus(Context context, String message, int time) {
        if(con!=context){
            synchronized (popWinLock) {
                if (showStatused) {
                    mWindowManager.removeView(notiView);
                    showStatused = false;
                }
            }
            mWindowManager = (WindowManager) context
                    .getSystemService("window");
            mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);

            con=context;
        }





//		TextView txtView = (TextView) notiView.findViewById(R.id.title);
//        TextView txtView = (TextView) notiView.findViewWithTag("title");
		tv.setText(message);





		synchronized (popWinLock) {
			if (!showStatused) {
                addViewToWindow();

			}
		}

//		if (ring)
//			playRing(context);
//		if (vibrat)
//			vibrat(context);
//		mHandler.removeCallbacks(removePopWindow);
//		mHandler.postDelayed(removePopWindow, time);

	}

    private void addViewToWindow(){
        WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        wmParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                | WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        wmParams.width = w;
        wmParams.height = h;

        wmParams.gravity = Gravity.TOP | Gravity.LEFT;
        wmParams.x = x;
        wmParams.y = y;
        mWindowManager.addView(notiView, wmParams);
        showStatused = true;
    }

	private void playRing(Context context) {
		try {
			Uri notification = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			Ringtone r = RingtoneManager.getRingtone(context, notification);
			r.play();
		} catch (Throwable ex) {
		}
	}

	private void vibrat(Context context) {
		Vibrator vib = (Vibrator) context
				.getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(200);
	}

//	public int showNotification(Context context, int iconID, String title,
//			String message, Intent intent, int notificationID, boolean ring,
//			boolean vibrat, boolean onGoing) {
//        NotificationManager nm = (NotificationManager) context
//                .getSystemService(Context.NOTIFICATION_SERVICE);
//
//		if (notificationID == 0) {
//			notificationID = ++mNotifacationIDIndex;
//		}
//
//		if (intent != null) {
//
//			// 传递ID参数
//			intent.putExtra(NOTIFICATIONID, notificationID);
//		}
//
//		Notification notification = new Notification(iconID, title,
//				System.currentTimeMillis());
//		if (onGoing) {
//			notification.flags |= Notification.FLAG_ONGOING_EVENT;
//		} else {
//			notification.flags = Notification.FLAG_AUTO_CANCEL;
//		}
//		if (ring&& SettingsUtil.getLing(context)) {
//			notification.defaults |= Notification.DEFAULT_SOUND;
//		} else {
//			notification.defaults &= ~Notification.DEFAULT_SOUND;
//		}
//		if (vibrat&& SettingsUtil.getZhen(context)) {
//			notification.defaults |= Notification.DEFAULT_VIBRATE;
//		} else {
//			notification.defaults &= ~Notification.DEFAULT_VIBRATE;
//		}
//		PendingIntent contentIntent = PendingIntent.getActivity(context,
//                R.string.app_name, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//		notification.setLatestEventInfo(context, title, message, contentIntent);
//		nm.notify(notificationID, notification);
//
//		return notificationID;
//	}

    public void deleteNotification(Context context,int id){
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(id);
    }

    public void deleteNotification(Context context){
//        int nid=intent.getIntExtra(NHelper.NOTIFICATIONID, -1);
        for(Integer integer:nationidmap.values()){
            if(integer>-1){
                NHelper.getNHelper().deleteNotification(context,integer);
            }
        }

    }
}
