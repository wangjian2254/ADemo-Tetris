package com.mogu.game.tetris;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.liyu.pluginframe.util.MainDataTool;
import com.liyu.pluginframe.util.UserInfo;
import com.umeng.analytics.MobclickAgent;
import org.json.JSONException;
import org.json.JSONObject;

public class MainMenuActivity extends Activity {
	
	
	private Context con;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con=this;
        MobclickAgent.onError(this);
        setContentView(R.layout.app_list);
        MainDataTool.getUserInfoJSON(this);

		
    }
    
    public void onBtnClick(View v){
    	switch (v.getId()) {
			case R.id.danren:
				Intent mainIntent = new Intent(this,MainActivity.class);
		    	Bundle extras=new Bundle();
		    	mainIntent.putExtras(extras);
		    	startActivity(mainIntent);
//                finish();
				break;
			case R.id.duoren:
				Intent mainIntent2 = new Intent(this,GameDaTing.class);
		    	Bundle extras2=new Bundle();
		    	mainIntent2.putExtras(extras2);
		    	startActivity(mainIntent2); 
				break;
			case R.id.daoju:
				
				break;
			case R.id.shezhi:
				
				break;
			case R.id.chenjiu:
				
				break;
			case R.id.bangzhu:
				
				break;
			case R.id.yuezhan:
				
				break;

		
		}
    }
    
    
    public void onResume(){
    	super.onResume();
    	MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
    
   
}