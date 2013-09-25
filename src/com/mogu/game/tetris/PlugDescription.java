package com.mogu.game.tetris;




import com.liyu.pluginframe.beans.GenDescript;

import android.graphics.drawable.Drawable;
import android.util.Log;


public class PlugDescription extends GenDescript{
	
	/**
	 * 首先，这个无参数的构造方法是必须有的
	 * 其次，接口是主程序自己定义的，可自行定义并扩展
	 * 最后，assets/plugin.xml 中必须定义这个类
	 */
	public PlugDescription() {
//		 Log.i("org.igeek.android-plugin", "插件1实例化");  
		 iconResId= R.drawable.ic_launcher;
		 subTitle="俄罗斯方块";
		 description="俄罗斯方块";
	}
	


}
