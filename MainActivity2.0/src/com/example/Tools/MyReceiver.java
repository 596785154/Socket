package com.example.Tools;

import com.example.service.InsertService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * �ù㲥���ڴ�������������
 * @author hust
 *
 */
public class MyReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction().equals("INSERTDAY")){
			Log.d("receiver", "INSERTDAY �㲥�ѽ���");
			InsertService.setFlag(1);
		}
		/*if(intent.getAction().equals("INSERTMONTH")){
			Log.d("receiver", "INSERTMONTH �㲥�ѽ���");
			InsertService.setFlag(2);
		}*/
	}

}
