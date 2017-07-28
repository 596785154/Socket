package com.example.grehousemon;

import com.example.rule.database.DBAdapter;
import com.example.service.InsertService;
import com.example.service.RevService;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * 该activity用于初进入应用时设置网络并开启数据库相关功能
 * @author hust
 *
 */
public class MainActivity extends AbstractActivity {

	//建立socket连接的服务
	Intent intentRevService;
	//执行删除临时数据，将统计的数据插入日表及天表操作的服务
	Intent intentInsertService;
	static DBAdapter dbAdapter;
    static Handler revHandler;
	
	public static Handler getRevHandler() {
		return revHandler;
	}

	public static void setRevHandler(Handler revHandler) {
		MainActivity.revHandler = revHandler;
	}
	public static DBAdapter getDbAdapter() {
		return dbAdapter;
	}

	public static void setDbAdapter(DBAdapter dbAdapter) {
		MainActivity.dbAdapter = dbAdapter;
	}

	Boolean isRun = false;
	Button buttonControl;
	Handler handler;
	public static String IP;
	private final int WRAP_CONTENT = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int FILL_PARENT = ViewGroup.LayoutParams.FILL_PARENT;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//开启每日插表服务
		intentInsertService = new Intent(this, InsertService.class);
		startService(intentInsertService);
		//建立或打开数据库
		dbAdapter = new DBAdapter(this);
		setDbAdapter(dbAdapter);
		revHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what==0x123){
					Toast.makeText(getBaseContext(), "连接失败请重新输入IP", Toast.LENGTH_SHORT).show();
					MainActivity.IP = null;
					handler.sendEmptyMessage(0x3000);
				}
				if(msg.what==0x345){
					Toast.makeText(getBaseContext(), "自动控制模式已启动", Toast.LENGTH_SHORT).show();
				}
			}
			
		};
		setRevHandler(revHandler);
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stu
				if(msg.what==0x3000){
					final FrameLayout fl = new FrameLayout(MainActivity.this); 
					final EditText editText = new EditText(MainActivity.this);
	                editText.setGravity(Gravity.CENTER);
	                editText.setHint("请输入目的IP地址");
	                editText.setEnabled(true);
	                fl.addView(editText,new FrameLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT)); //给某屏幕添加组件
					new AlertDialog.Builder(MainActivity.this).setTitle("请设置通信IPAddress")
					.setView(fl).setPositiveButton("确定", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							IP = editText.getText().toString();
							Toast.makeText(MainActivity.this, "当前IP是"+IP, Toast.LENGTH_SHORT).show();
						}
					}).create().show();
				}
			}
			
		};
		new AlertDialog.Builder(this).setTitle("设置网络").setMessage("请选择正确的的网络进行数据传送")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
						dialog.cancel();
					}
				}).create().show();
		
			buttonControl = (Button)findViewById(R.id.button_control);
			buttonControl.setOnClickListener(listenerControl);
			Log.d("Activity", "main已启动");
	
	}

	private OnClickListener listenerControl = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			if(IP!=null){
				intentRevService = new Intent(MainActivity.this,RevService.class);
				startService(intentRevService);
				Intent intent = new Intent(MainActivity.this,ControlActivity.class);
				startActivity(intent);
				
			}else {
				Toast.makeText(MainActivity.this, "请设置正确的网络及目的IP地址", Toast.LENGTH_SHORT).show();
				Message msg = new Message();
				msg.what = 0x3000;
				handler.sendMessage(msg);
			}
			
		}
	};
	
}
