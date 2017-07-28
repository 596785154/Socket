package com.example.fragments;

import com.example.grehousemon.R;
import com.example.service.sendDataThread;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
/**
 * 自动有土fragment
 * 与自动无土实现情况相同  数据从数据库中无土栽培规则表获取（数据库功能应该没写）
 * @author hust
 *
 */
public class AutoSoilFrag extends Fragment{

    private Handler handler = sendDataThread.getHandler();
	Button buttonReset,buttonControl;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View viewSoil = inflater.inflate(R.layout.fragment_auto_soil, container, false);
		buttonControl = (Button)viewSoil.findViewById(R.id.button_auto_soil);
		buttonControl.setOnClickListener(controlListener);
		buttonReset = (Button)viewSoil.findViewById(R.id.button_reset_soil);
		buttonReset.setOnClickListener(resetListener);
		return viewSoil;
	}

	private OnClickListener controlListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Message msg = new Message();
			msg.what = 0x1110;
			handler.sendMessage(msg);
			Toast.makeText(getActivity(), "已进入自动控制模式", Toast.LENGTH_SHORT).show();
		}
	};
	
	private OnClickListener resetListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	};
}
