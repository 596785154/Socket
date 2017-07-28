package com.example.fragments;

import com.example.grehousemon.R;
import com.example.service.sendDataThread;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
/**
 * 手动无土fragment
 * 点击对应的switch 发送对应的命令，该部分已实现
 * @author hust
 *
 */
public class ManNonSoilFrag extends Fragment {

	Switch switchLight, switchWind, switchSun, switchLiquid;
    Handler handler = sendDataThread.getHandler();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View viewNonSoil = inflater.inflate(R.layout.fragment_man_non_soil, container,false);
		switchLight = (Switch) viewNonSoil.findViewById(R.id.switch_light);
		switchWind = (Switch) viewNonSoil.findViewById(R.id.switch_wind);
		switchSun = (Switch) viewNonSoil.findViewById(R.id.switch_sun);
	    switchLight.setOnCheckedChangeListener(onSwitchListener);
		switchWind.setOnCheckedChangeListener(onSwitchListener);
		switchSun.setOnCheckedChangeListener(onSwitchListener);
		return viewNonSoil;
	}
	private OnCheckedChangeListener onSwitchListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			int id = buttonView.getId();
			Message msg = new Message();
			
			msg.what = 0x1011;
			switch (id) {
			case R.id.switch_light:
				if(isChecked){
					msg.obj = "LightOn";
					Toast.makeText(getActivity(), "frag LightOn", Toast.LENGTH_SHORT).show();
					handler.sendMessage(msg);


				}else {
					msg.obj = "LightOff";
					Toast.makeText(getActivity(), "frag LightOff", Toast.LENGTH_SHORT).show();
					handler.sendMessage(msg);


				}
				break;

			case R.id.switch_wind:

				if(isChecked){
					msg.obj = "WindOn";
					Toast.makeText(getActivity(), "frag WindOn", Toast.LENGTH_SHORT).show();
					handler.sendMessage(msg);

				}else {
					msg.obj = "WindOff";
					Toast.makeText(getActivity(), "frag WindOff", Toast.LENGTH_SHORT).show();
					handler.sendMessage(msg);


				}
				break;

			case R.id.switch_sun:

				if(isChecked){
					msg.obj = "SunOn";
					Toast.makeText(getActivity(), "frag SunOn", Toast.LENGTH_SHORT).show();
					handler.sendMessage(msg);


				}else {
					msg.obj = "SunOff";
					Toast.makeText(getActivity(), "frag SunOff", Toast.LENGTH_SHORT).show();
					handler.sendMessage(msg);


				}
				break;

			default:
				break;
			}
		}
	};
}
