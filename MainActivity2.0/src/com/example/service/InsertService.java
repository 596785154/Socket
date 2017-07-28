package com.example.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
/**
 * �÷������ڶ�ʱɾ����ʱ���ݲ���¼�գ�����ʷ����
 * @author hust
 *
 */
public class InsertService extends IntentService {
	//ÿ�춨ʱ�������ݣ�������룬�²�������

	private static Boolean isRun = true;
	private static Boolean isDayRun = false;
	private static Boolean isMonthRun = true;
	private static int preDay;
	private static int nowDay;
    private static int flag = 0;
	
	public static int getFlag() {
		return flag;
	}

	public static void setFlag(int flag) {
		InsertService.flag = flag;
	}

	public static int getPreDay() {
		return preDay;
	}

	public static void setPreDay(int preDay) {
		InsertService.preDay = preDay;
	}

	public static int getNowDay() {
		return nowDay;
	}

	public static void setNowDay(int nowDay) {
		InsertService.nowDay = nowDay;
	}

	public static Boolean getIsRun() {
		return isRun;
	}

	public static void setIsRun(Boolean isRun) {
		InsertService.isRun = isRun;
	}

	public static Boolean getIsDayRun() {
		return isDayRun;
	}

	public static void setIsDayRun(Boolean isDayRun) {
		InsertService.isDayRun = isDayRun;
	}

	public static Boolean getIsMonthRun() {
		return isMonthRun;
	}

	public static void setIsMonthRun(Boolean isMonthRun) {
		InsertService.isMonthRun = isMonthRun;
	}

	public InsertService() {
		super("InsertDayService");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Time currentTime = new Time(Time.getCurrentTimezone());
		currentTime.setToNow();
		Log.d("time", currentTime.hour+":"+currentTime.minute+":"+currentTime.second);
		Intent intentDay = new Intent();
		intentDay.setAction("INSERTDAY");
		
		PendingIntent piDay = PendingIntent.getBroadcast(InsertService.this, 0, intentDay, PendingIntent.FLAG_UPDATE_CURRENT);

		AlarmManager daymanager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

		

		Log.d("time", getAfterDay(currentTime).hour+":"+getAfterDay(currentTime).minute+":"+getAfterDay(currentTime).second);

        Time time = new Time(Time.getCurrentTimezone());
		while (true) {
			//��ǰʱ�佫ִ��ɾ��������ʱ�������ݣ����ӱ��м���ƽ��ֵ�������ձ���Ϊ��ʷ����

			time.setToNow();
			
			if(flag==1){//��������ݲ���
				
				//������ʱ���в鴦�����������ݣ�����ƽ��ֵ����������ձ�
				
				
				//����ʱ����ɾ������������ʱ����
				
				
				//��������
				//Ҫ�������ݵ�ʱ����time��ǰһ��
				Log.d("day", "insert day");
				
				
				if(true){//���벢ɾ���ɹ�
					

					//�жϽ���ʱ���Ƿ����³���һ�죬�������ϸ��µ�����ʷ���ݼ������ƽ������������±�
					
					if(time.monthDay==/*1*/19){
						//�����ϸ��µ�����;
						
						Log.d("day", "insert month");
						
					}
					
					//�趨��һ����������ݵ����ӣ�����ͬһʱ��ִ����ͬ����

					daymanager.set(AlarmManager.RTC_WAKEUP, getAfterDay(time).toMillis(true), piDay);
				}
				flag=0;
			}else {
				continue;
			}
		}
	}

	//��ȡ����ʱ��  ��ǰ��ֵΪ������ֵ
	private Time getAfterDay(Time time) {
		long timeMill = time.toMillis(true);
		int month = time.month+1;
		//long afterTimeMill = timeMill + (24*60*60*1000);
		Time afterTime = new Time();
		/*afterTime.set(afterTimeMill);
		afterTime.hour = 00;
		afterTime.minute = 10;//�����賿��10����ͳ����һ������
		afterTime.second = 00;*/
		
			afterTime.year = time.year;
			afterTime.month = time.month;
			afterTime.monthDay = time.monthDay;
			afterTime.hour = time.hour;
			afterTime.minute = time.minute;
			afterTime.second = time.second+10;
		
		return afterTime;
	}
	

}
