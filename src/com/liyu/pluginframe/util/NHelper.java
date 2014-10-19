package com.liyu.pluginframe.util;

import android.app.*;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

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

	private boolean showStatused = false;
	WindowManager mWindowManager;
    Context con;
	DisplayMetrics mDisplayMetrics;
	Object popWinLock = new Object();
	private int mNotifacationIDIndex = 1000;

	boolean getStatusing = false;

    private static Map<Integer,Map<String,Integer>> weizhilist=new HashMap<Integer, Map<String, Integer>>();

    private static Map<Integer,RelativeLayout> notiViewmap=new HashMap<Integer, RelativeLayout>();

    private static Map<String,ImageView> paiming = new HashMap<String, ImageView>();
    private static Map<String,TextView> jindulist = new HashMap<String, TextView>();
    private static Map<String,TextView> pointlist = new HashMap<String, TextView>();
    private static Map<String,Integer> wlist = new HashMap<String, Integer>();
    private static  Map<String,Integer> userlist;
    private static  Map<String,String> nicklist;



    private int x=0;
    private int y=0;
    private int w=120;
    private int h=24;
    private int c =0;

    private int face_board=0;
    private int[] nn =new int[3];

    private String headpic="com.mogu3.mainapp.im.util.HeaderPic";
	/**
	 * 单例类，
	 */
	private NHelper() {

	}

    public void setResid(int f,int n1,int n2,int n3){
        face_board=f;
        nn[0]=n1;
        nn[1]=n2;
        nn[2]=n3;
//        nn[3]=n4;
//        nn[4]=n5;
//        nn[5]=n6;

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
//            if (notiView == null) {


//                notiView=new LinearLayout(activity);
//                notiView.setOrientation(0);
//                FrameLayout.LayoutParams params=new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
//                params.gravity=Gravity.BOTTOM|Gravity.RIGHT;





                statusBarHeight = getStatusBarHeight(activity);
                mDisplayMetrics = new DisplayMetrics();

        
                mWindowManager = (WindowManager) activity
                        .getSystemService("window");
                mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
                con=activity;

//            }

			inited = true;
		}
	}
    private static Bitmap drawableToBitmap(Drawable drawable) // drawable 转换成bitmap
    {
        int width = drawable.getIntrinsicWidth();   // 取drawable的长宽
        int height = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888:Bitmap.Config.RGB_565;         //取drawable的颜色格式
        Bitmap bitmap = Bitmap.createBitmap(width, height, config);     // 建立对应bitmap
        Canvas canvas = new Canvas(bitmap);         // 建立对应bitmap的画布
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);      // 把drawable内容画到画布中
        return bitmap;
    }

    private static Bitmap zoomDrawable(Drawable drawable, int w, int h)
    {
        int width = drawable.getIntrinsicWidth();
        int height= drawable.getIntrinsicHeight();
        Bitmap oldbmp = drawableToBitmap(drawable); // drawable转换成bitmap
        Matrix matrix = new Matrix();   // 创建操作图片用的Matrix对象
        float scaleWidth = ((float)w / width);   // 计算缩放比例
        float scaleHeight = ((float)h / height);
        matrix.postScale(scaleWidth, scaleHeight);         // 设置缩放比例
        Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height, matrix, true);       // 建立新的bitmap，其内容是对原bitmap的缩放后的图
        return newbmp;       // 把bitmap转换成drawable并返回
    }

    public void setHead(Context content,Map<String,Integer> usermap,Map<String,String> nickmap){
        con=content;
        userlist = usermap;
        nicklist = nickmap;
        initHead();
    }
    private void initHead(){

        try {

            Context targetContext=con.createPackageContext("com.mogu3.mainapp", Context.CONTEXT_INCLUDE_CODE|Context.CONTEXT_IGNORE_SECURITY);
            Class<?> c;
            c = targetContext.getClassLoader().loadClass(headpic);
            Method m = c.getMethod("getHeader", new Class[] {int.class});
            Object[] p=new Object[1];
            TextView t=null;
            TextView n=null;
            ImageView imageView=null;


            RelativeLayout parent=null;
            LinearLayout     panellinear =null;


            int num=0;
            TextView t1;
            for (String u:userlist.keySet()){
                Map<String,Integer> numpos=weizhilist.get(num);

                int face_board_w=70;
                if (numpos!=null){
                    face_board_w=numpos.get("w");
                }
                parent=notiViewmap.get(num);
                if(parent==null){
                    parent=new RelativeLayout(con);
                }else{
                    parent.removeAllViews();
                }
                panellinear=new LinearLayout(con);

                panellinear.setBackgroundDrawable(targetContext.getResources().getDrawable(face_board));
                LinearLayout.LayoutParams lb = new LinearLayout.LayoutParams(face_board_w, ViewGroup.LayoutParams.WRAP_CONTENT);
                panellinear.setOrientation(LinearLayout.VERTICAL);
                panellinear.setLayoutParams(lb);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                parent.setLayoutParams(params);

                RelativeLayout.LayoutParams lineparam = new RelativeLayout.LayoutParams(face_board_w, ViewGroup.LayoutParams.WRAP_CONTENT);

                lineparam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                lineparam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                lineparam.setMargins((int)(face_board_w/6.0+0.5),(int)(face_board_w/6.0+0.5),0,0);
//                lineparam.addRule(RelativeLayout.MarginLayoutParams);

                parent.addView(panellinear,lineparam);

//                panellinear1=new RelativeLayout(con);
//                panellinear1.setPadding(0,0,0,0);
//                RelativeLayout.LayoutParams n0=new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//                n0.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                t = new TextView(con);
                t.setSingleLine(true);
//                t.setHeight(30);
                t.setText(nicklist.get(u));
//                t.setPadding(0, 2, 0, 0);
                t.setWidth(face_board_w);
                t.setTextSize(12);
                t.setGravity(Gravity.CENTER);
                t.setId(num+200);
                panellinear.addView(t,lb);


//                panellinear2=new RelativeLayout(con);
//                imageView = new  ImageView(con);
//                imageView.setImageDrawable();
//                panellinear.addView(imageView);

//                panellinear.addView(panellinear2);
//                panellinear.addView(panellinear1);

//                panellinear2.setPadding(5,5,5,5);

//                RelativeLayout.LayoutParams param=new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//                RelativeLayout.LayoutParams nickparam=new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//                RelativeLayout.LayoutParams paimingparam=new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//
//                nickparam.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//                nickparam.addRule(RelativeLayout.CENTER_HORIZONTAL);

//                t = new TextView(con);
//
//                t.setHeight(21);
//                t.setPadding(0, 0, 0, 0);
//                t.setWidth(face_board_w-10);
//                t.setTextSize(12);
//                t.setId(num+1);
//                panellinear2.addView(t,nickparam);

                if(numpos==null||numpos.get("type")==1){
                    imageView = new  ImageView(con);
                    p[0]=userlist.get(u);
//                BitmapFactory.decodeResource()
                    imageView.setImageBitmap(zoomDrawable(targetContext.getResources().getDrawable((Integer) m.invoke(c, p)), face_board_w, (int)((62.0/70.0)*face_board_w+0.5)));
                    imageView.setId(num+100);
//                param.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//                param.addRule(RelativeLayout.CENTER_HORIZONTAL);

                    panellinear.addView(imageView);
                }





                //进度条
//                RelativeLayout.LayoutParams j1=new RelativeLayout.LayoutParams(
//                        ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT);
//                j1.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//                j1.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                LinearLayout j = new LinearLayout(con);
                LinearLayout.LayoutParams jin = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,8);
                if(numpos==null||numpos.get("type")==1){
                    j.setBackgroundColor(0xff505760);
                }else{
                    j.setBackgroundColor(0xff0c1218);
                }
                j.setOrientation(LinearLayout.VERTICAL);
                j.setLayoutParams(jin);

                t1 = new TextView(con);
                t1.setWidth(0);
                t1.setHeight(8);
                t1.setBackgroundColor(0xfffffc00);
                j.addView(t1);
                panellinear.addView(j);
                jindulist.put(u,t1);





                n = new TextView(con);
//                n.setHeight(25);
                n.setWidth(face_board_w);
                n.setTextSize(12);
                n.setGravity(Gravity.CENTER);
                n.setId(num + 300);
                panellinear.addView(n,lb);
                pointlist.put(u,n);

                notiViewmap.put(num, parent);

                ImageView pai = new ImageView(con);
                pai.setVisibility(View.GONE);
                RelativeLayout.LayoutParams jinduparam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                jinduparam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
                jinduparam.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                parent.addView(pai,jinduparam);
                paiming.put(u,pai);
                wlist.put(u,face_board_w);
                num++;

            }
            addViewToWindow();

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
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

//	Runnable removePopWindow = new Runnable() {
//
//		@Override
//		public void run() {
//			synchronized (popWinLock) {
//				if (showStatused) {
//					mWindowManager.removeView(notiView);
//					showStatused = false;
//				}
//			}
//		}
//	};

    public void setStatus(Context context,int num,int type,int xx,int yy,int ww,int hh){
        RelativeLayout notiView = notiViewmap.get(num);
        if(notiView==null){
            return;
        }

        if(con!=context){
            hiddenStatus();
            mWindowManager = (WindowManager) context
                    .getSystemService("window");
            mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
            con=context;
            addViewToWindow();
        }

        Map<String,Integer> numpos=weizhilist.get(num);
        if (numpos==null){
            numpos = new HashMap<String, Integer>();
            weizhilist.put(num,numpos);
        }

        numpos.put("type",type);
        numpos.put("x",xx);
        numpos.put("y",yy);
        numpos.put("w",ww);
        numpos.put("h",hh);
//        numpos.put("color",color);


        synchronized (popWinLock) {
                WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
                wmParams.format = PixelFormat.RGBA_8888;
                wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
                wmParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                        | WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

                wmParams.width = ww+15;
                wmParams.height = hh*2;

                wmParams.gravity = Gravity.TOP | Gravity.LEFT;
                wmParams.x = xx;
                wmParams.y = yy;
                mWindowManager.updateViewLayout(notiView,wmParams);

        }
        initHead();
//        showStatus(context, message, time);
    }

    public void hiddenStatus(){
        synchronized (popWinLock) {
            if (showStatused) {
                for (RelativeLayout notiView:notiViewmap.values()){
                    mWindowManager.removeView(notiView);
                }

                showStatused = false;
            }
        }
    }

	/**
	 * 显示状态信息，不能点击
	 * 
	 * @param context
	 * @param up
	 */
	public void showStatus(Context context,Map<String,String> up) {
        if(con!=context){
            hiddenStatus();
            mWindowManager = (WindowManager) context
                    .getSystemService("window");
            mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);

            con=context;
            addViewToWindow();
        }

        ///////////////////////////////////////////////////
        float max=1;
        for(String u:up.keySet()){
                try{
                    if(Integer.valueOf(up.get(u)).intValue()>max){
                        max = Integer.valueOf(up.get(u)).intValue();
                    }
                }catch (Exception e){

                }

        }
        int point=0;
        TextView t=null;

        for(String u:up.keySet()){
            int face_board_w=80;
            if (wlist.containsKey(u)){
                face_board_w=wlist.get(u);
            }
             try{
                 point = Integer.valueOf(up.get(u));
             }catch (Exception e){
                 point = 0;
             }
              
            t=jindulist.get(u);
            t.setWidth((int)(point/max*face_board_w));

        }
        ///////////////////////////////////////////////////////////////////






        Map<String, Integer> map = new HashMap<String, Integer>();
        for(String u:up.keySet()){
            try{
                map.put(u, Integer.valueOf(up.get(u)));
                pointlist.get(u).setText(up.get(u));
            }catch (Exception e){
                map.put(u,0);
            }
        }

        List<Map.Entry<String, Integer>> infoIds =
                new ArrayList<Map.Entry<String, Integer>>(map.entrySet());


        //排序
        Collections.sort(infoIds, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });


        try {
            Context targetContext=context.createPackageContext("com.mogu3.mainapp", Context.CONTEXT_INCLUDE_CODE|Context.CONTEXT_IGNORE_SECURITY);
            for (int i = 0; i < infoIds.size(); i++) {
                if(i==nn.length){
                    paiming.get(infoIds.get(i).getKey()).setVisibility(View.GONE);
                      continue;
                }
                int pw=25;
                if(wlist.get(infoIds.get(i).getKey())!=null){
                    pw = (int)(wlist.get(infoIds.get(i).getKey())/2.8+0.5);
                }

                paiming.get(infoIds.get(i).getKey()).setImageBitmap(zoomDrawable(targetContext.getResources().getDrawable(nn[i]),pw,pw));
                paiming.get(infoIds.get(i).getKey()).setVisibility(View.VISIBLE);
            }


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

		synchronized (popWinLock) {
			if (!showStatused) {
                addViewToWindow();

			}
		}

	}

    private void addViewToWindow(){
        if(mWindowManager==null){
            return;
        }
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
        for(RelativeLayout notiView:notiViewmap.values()){
            try{
            mWindowManager.addView(notiView, wmParams);
            }catch (Exception e){

            }
        }


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


    public void deleteNotification(Context context,int id){
        NotificationManager nm = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(id);
    }

    public void deleteNotification(Context context){
        for(Integer integer:nationidmap.values()){
            if(integer>-1){
                NHelper.getNHelper().deleteNotification(context,integer);
            }
        }

    }
}
