package com.example.grehousemon;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * 该类用于选择自动模式与手动模式
 * @author hust
 *
 */
public class ControlActivity extends AbstractActivity {

	Button buttonManual,buttonAuto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control);
		buttonManual = (Button)findViewById(R.id.button_manual);
		buttonManual.setOnClickListener(listenerManual);
		buttonAuto = (Button)findViewById(R.id.button_auto);
		buttonAuto.setOnClickListener(listenerAuto);
	}
	
	private OnClickListener listenerManual = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ControlActivity.this, ManualActivity.class);
			//Intent intent = new Intent(ControlActivity.this, a.class);
			startActivity(intent);
		}
	};
	
	private OnClickListener listenerAuto = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(ControlActivity.this,AutoActivity.class);
			//Intent intent = new Intent(ControlActivity.this,a.class);
			startActivity(intent);
		}
	};

}
