package com.mogu.game.tetris;

import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class GameDaTing extends Activity {

	private ViewPager viewPager; 
	private ArrayList<View> pageViews;  
	 private ImageView imageView;  
	 private ImageView[] imageViews; 
	 // 包裹滑动图片LinearLayout
	 private ViewGroup main;
	 // 包裹小圆点的LinearLayout
	 private ViewGroup group;
	 
	 private View romgrid;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		
		LayoutInflater inflater = getLayoutInflater();  
        pageViews = new ArrayList<View>();
		main = (ViewGroup)inflater.inflate(R.layout.dating, null);  
        
        group = (ViewGroup)main.findViewById(R.id.viewGroup);  
        viewPager = (ViewPager)main.findViewById(R.id.guidePages); 
        romgrid=inflater.inflate(R.layout.room_grid, null);
	}
}
