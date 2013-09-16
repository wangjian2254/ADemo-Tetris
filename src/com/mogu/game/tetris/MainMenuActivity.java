package com.mogu.game.tetris;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuActivity extends Activity {
	
	
	private Context con;
//	Button danren;
//	Button duoren;
//	Button daoju;
//	Button shezhi;
//	Button chengjiu;
//	Button yuezhan;
//	Button bangzhu;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        con=this;
        setContentView(R.layout.app_list);
//		danren=(Button)findViewById(R.id.danren);
//		duoren=(Button)findViewById(R.id.duoren);
//		daoju=(Button)findViewById(R.id.daoju);
//		shezhi=(Button)findViewById(R.id.shezhi);
//		chengjiu=(Button)findViewById(R.id.chenjiu);
//		yuezhan=(Button)findViewById(R.id.yuezhan);
//		bangzhu=(Button)findViewById(R.id.bangzhu);
		
		
		
    }
    
    public void onBtnClick(View v){
    	switch (v.getId()) {
			case R.id.danren:
				Intent mainIntent = new Intent(this,MainActivity.class);
		    	Bundle extras=new Bundle();
		    	mainIntent.putExtras(extras);
		    	startActivity(mainIntent); 
				break;
			case R.id.duoren:
				
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
    }
    public void onPause() {
        super.onPause();
    }
    
   
}