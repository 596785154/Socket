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
import android.widget.TextView;
/**
 * 用于自动有土的fragment
 * 点击自动有土需向底端发送各培养环境的标准数据   位于数据库的自动无土规则表（应该没写）  发送数据部分未写
 * 点击重置按钮
 * @author hust
 *
 */
public class AutoNonSoilFrag extends Fragment{

    private Handler handler = sendDataThread.getHandler();
	private TextView textViewTemp,textViewHumi,textViewLight;
	private Button buttonControl,buttonReset;
	private String tempMin,tempMax,humiMin,humiMax,lightMin,lightMax;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View viewNonSoil = inflater.inflate(R.layout.fragment_auto_non_soil, container, false);
		textViewHumi = (TextView) viewNonSoil.findViewById(R.id.textView_humidity_range);
		textViewLight = (TextView) viewNonSoil.findViewById(R.id.textView_light_range);
		textViewTemp = (TextView) viewNonSoil.findViewById(R.id.textView_temperature_range);
		//下面的数值从数据库规则表查出 ：  温湿度计光照的合理范围
		/*tempMin = 
		tempMax = 
		humiMin = 
		humiMax = 
		lightMin = 
		lightMax = 
		String textTemp = tempMin+","+tempMax;
		String textHumi = humiMin+","+humiMax;
		String textLight = lightMin+","+lightMax;
		textViewTemp.setText(textTemp.toString());
		textViewHumi.setText(textHumi);
		textViewLight.setText(textLight);*/
		buttonControl = (Button)viewNonSoil.findViewById(R.id.button_auto);
		buttonControl.setOnClickListener(controlListener);
		buttonReset = (Button)viewNonSoil.findViewById(R.id.button_reset);
		buttonReset.setOnClickListener(resetListener);
		
		return viewNonSoil;
	}
    private OnClickListener resetListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//调用设置规则的Activity  该activity在王光琪代码中
			
		}
	};
	private OnClickListener controlListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Bundle bundle = new Bundle();
			
			/*bundle.putString("tempMin", tempMin);
			bundle.putString("tempMax", tempMax);
			bundle.putString("humiMin", humiMin);
			bundle.putString("humiMax", humiMax);
			bundle.putString("lightMin", lightMin);
			bundle.putString("lightMax", lightMax);*/
			Message msg = new Message();
			msg.what = 0x1111;
			//msg.setData(bundle);
			handler.sendMessage(msg);
		}
	};
	
}
