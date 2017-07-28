package com.example.Tools;

import com.example.service.InsertService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * 该广播用于触发插数据闹钟
 * @author hust
 *
 */
public class MyReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction().equals("INSERTDAY")){
			Log.d("receiver", "INSERTDAY 广播已接收");
			InsertService.setFlag(1);
		}
		/*if(intent.getAction().equals("INSERTMONTH")){
			Log.d("receiver", "INSERTMONTH 广播已接收");
			InsertService.setFlag(2);
		}*/
	}

}
