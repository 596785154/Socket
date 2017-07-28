package com.example.grehousemon;

import com.example.fragments.AutoNonSoilFrag;
import com.example.fragments.AutoSoilFrag;
import com.example.fragments.ManNonSoilFrag;
import com.example.fragments.ManSoilFrag;
import com.example.service.sendDataThread;

import android.os.Bundle;
import android.os.Message;
import android.app.ActionBar.TabListener;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.app.TabActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
/**
 * 用于选择自动有土及无土模式选择
 * @author hust
 *
 */
public class AutoActivity extends TabActivity implements OnTabChangeListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto);
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("自动控制模式");
		
		TabHost tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);
		tabHost.addTab(tabHost.newTabSpec("specSoil").setIndicator(createTabView("有土栽培")).setContent(R.id.tab_soil));
		tabHost.addTab(tabHost.newTabSpec("specnonSoil").setIndicator(createTabView("无土栽培")).setContent(R.id.tab_nonsoil));
	}	

	private View createTabView(String text) {            
		 View view = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);            
		 TextView tv = (TextView) view.findViewById(R.id.tv_tab);
		 tv.setText(text);            
		 return view;    
    }

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		Log.d("tabId","id=="+tabId);
		Message msg = new Message();
		msg.what = 0x00;
		msg.obj = "auto";
		sendDataThread.getHandler().sendMessage(msg);
		if(tabId.equals("specSoil")){
			Toast.makeText(this, "自动有土栽培模式", Toast.LENGTH_SHORT).show();
			Fragment fragment = new AutoSoilFrag();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.container_auto, fragment).commit();
		}
		if(tabId.equals("specnonSoil")){
			Toast.makeText(this, "自动无土栽培模式", Toast.LENGTH_SHORT).show();
			Fragment fragment = new AutoNonSoilFrag();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.container_auto, fragment).commit();
		}
	
	}
	
		
}
