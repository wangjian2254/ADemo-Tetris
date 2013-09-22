package com.mogu.game.tetris.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mogu.game.tetris.R;
import com.mogu.game.tetris.R.id;
import com.mogu.game.tetris.R.layout;
import com.mogu.game.tetris.R.style;
import com.mogu.game.tetris.model.Player;
import com.mogu.game.tetris.model.Room;

public class RoomAdapter extends BaseAdapter {
	Context mContext;
	List<Room> imgarrlist;
	private LayoutInflater mLayoutInflater = null;

	public RoomAdapter(Context c,List<Room> itemContent) {
		mContext = c;
		imgarrlist=itemContent;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imgarrlist.size();
	}
	
	public Room getImgArr(int position){
		return imgarrlist.get(position);
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		View localView = convertView;
		//判断当前view视图参数是否为null
	    if (localView == null){
	        //加载一级视图的布局文件
	    	localView = mLayoutInflater.inflate(R.layout.room, null);
	    }
	    Room r=imgarrlist.get(position);
	    TextView title=(TextView)localView.findViewById(R.id.title_name);
	    TextView roomnum=(TextView)localView.findViewById(R.id.room_num);
	    LinearLayout peoplelist=(LinearLayout)localView.findViewById(R.id.people_list);
	    peoplelist.removeAllViews();
	    title.setText(r.getRoomname());
	    roomnum.setText(r.getRoomplayer().size()+"/"+r.getMaxpeople());
	    TextView pt=null;
	    int i=0;
	    for(Player p:r.getRoomplayer()){
	    	pt=new TextView(mContext);
	    	pt.setTextAppearance(mContext, R.style.room);
	    	pt.setText(p.getLv()+" "+p.getNickname());
	    	peoplelist.addView(pt);
	    	i++;
	    	if(i==4){
	    		break;
	    	}
	    }
	    
		return localView;
	}
	

}
