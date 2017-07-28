package com.example.grehousemon;

import com.example.fragments.ManNonSoilFrag;
import com.example.fragments.ManSoilFrag;
import com.example.service.sendDataThread;

import android.os.Bundle;
import android.os.Message;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
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
 * ��������ѡ���ֶ�������������ģʽ
 * @author hust
 *
 */
public class ManualActivity extends TabActivity implements OnTabChangeListener{

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manual);
		
		ActionBar actionBar = getActionBar();
		actionBar.setTitle("�ֶ�����ģʽ");
		
		TabHost tabHost = getTabHost();
		tabHost.setOnTabChangedListener(this);
		tabHost.addTab(tabHost.newTabSpec("specSoil").setIndicator(createTabView("��������")).setContent(R.id.tab_soil));
		
		tabHost.addTab(tabHost.newTabSpec("specnonSoil").setIndicator(createTabView("��������")).setContent(R.id.tab_nonsoil));
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
		msg.obj = "manual";
		sendDataThread.getHandler().sendMessage(msg);
		if(tabId.equals("specSoil")){
			Toast.makeText(this, "�ֶ���������ģʽ", Toast.LENGTH_SHORT).show();
			Fragment fragment = new ManSoilFrag();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.container_manual, fragment).commit();
		}
		if(tabId.equals("specnonSoil")){
			Toast.makeText(this, "�ֶ���������ģʽ", Toast.LENGTH_SHORT).show();
			Fragment fragment = new ManNonSoilFrag();
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.replace(R.id.container_manual, fragment).commit();
		}
	}

	

		
}


