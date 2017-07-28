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
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
/**
 * 手动有土fragment
 * 点击开关像网关发送对应命令，该部分已实现
 * @author hust
 *
 */
public class ManSoilFrag extends Fragment {
	Switch switchLight, switchWind, switchSun, switchLiquid;
    Handler handler = sendDataThread.getHandler();
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View viewManual = inflater.inflate(R.layout.fragment_man_soil,
				container, false);
		switchLight = (Switch) viewManual.findViewById(R.id.switch_soil_light);
		switchWind = (Switch) viewManual.findViewById(R.id.switch_soil_wind);
		switchSun = (Switch) viewManual.findViewById(R.id.switch_soil_sun);
	    switchLiquid = (Switch) viewManual.findViewById(R.id.switch_soil_liquid);
	    switchLight.setOnCheckedChangeListener(onSwitchListener);
		switchWind.setOnCheckedChangeListener(onSwitchListener);
		switchSun.setOnCheckedChangeListener(onSwitchListener);
		switchLiquid.setOnCheckedChangeListener(onSwitchListener);

		return viewManual;
	}

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}


	private OnCheckedChangeListener onSwitchListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			int id = buttonView.getId();
			Message msg = new Message();
			
			msg.what = 0x1010;
			switch (id) {
			case R.id.switch_soil_light:
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

			case R.id.switch_soil_wind:

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

			case R.id.switch_soil_sun:

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

			case R.id.switch_soil_liquid:

				if(isChecked){
					msg.obj = "LiquidOn";
					Toast.makeText(getActivity(), "frag LiquidOn", Toast.LENGTH_SHORT).show();
					handler.sendMessage(msg);


				}else {
					msg.obj = "LiquidOff";
					Toast.makeText(getActivity(), "frag LiquidOff", Toast.LENGTH_SHORT).show();
					handler.sendMessage(msg);


				}
				break;
			default:
				break;
			}
		}
	};

}
