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
 * ��activity���ڳ�����Ӧ��ʱ�������粢�������ݿ���ع���
 * @author hust
 *
 */
public class MainActivity extends AbstractActivity {

	//����socket���ӵķ���
	Intent intentRevService;
	//ִ��ɾ����ʱ���ݣ���ͳ�Ƶ����ݲ����ձ��������ķ���
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
		
		//����ÿ�ղ�����
		intentInsertService = new Intent(this, InsertService.class);
		startService(intentInsertService);
		//����������ݿ�
		dbAdapter = new DBAdapter(this);
		setDbAdapter(dbAdapter);
		revHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(msg.what==0x123){
					Toast.makeText(getBaseContext(), "����ʧ������������IP", Toast.LENGTH_SHORT).show();
					MainActivity.IP = null;
					handler.sendEmptyMessage(0x3000);
				}
				if(msg.what==0x345){
					Toast.makeText(getBaseContext(), "�Զ�����ģʽ������", Toast.LENGTH_SHORT).show();
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
	                editText.setHint("������Ŀ��IP��ַ");
	                editText.setEnabled(true);
	                fl.addView(editText,new FrameLayout.LayoutParams(FILL_PARENT, WRAP_CONTENT)); //��ĳ��Ļ������
					new AlertDialog.Builder(MainActivity.this).setTitle("������ͨ��IPAddress")
					.setView(fl).setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							IP = editText.getText().toString();
							Toast.makeText(MainActivity.this, "��ǰIP��"+IP, Toast.LENGTH_SHORT).show();
						}
					}).create().show();
				}
			}
			
		};
		new AlertDialog.Builder(this).setTitle("��������").setMessage("��ѡ����ȷ�ĵ�����������ݴ���")
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
						dialog.cancel();
					}
				}).create().show();
		
			buttonControl = (Button)findViewById(R.id.button_control);
			buttonControl.setOnClickListener(listenerControl);
			Log.d("Activity", "main������");
	
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
				Toast.makeText(MainActivity.this, "��������ȷ�����缰Ŀ��IP��ַ", Toast.LENGTH_SHORT).show();
				Message msg = new Message();
				msg.what = 0x3000;
				handler.sendMessage(msg);
			}
			
		}
	};
	
}
