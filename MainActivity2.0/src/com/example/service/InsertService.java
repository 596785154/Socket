package com.example.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
/**
 * 该服务用于定时删除临时数据并记录日，月历史数据
 * @author hust
 *
 */
public class InsertService extends IntentService {
	//每天定时插入数据，按天插入，月插入数据

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
			//当前时间将执行删除昨日临时表中数据，并从表中计算平均值，存入日表作为历史数据

			time.setToNow();
			
			if(flag==1){//天和月数据操作
				
				//从天临时表中查处昨天所有数据，计算平均值，将其插入日表
				
				
				//从临时表中删除昨天所有临时数据
				
				
				//插入数据
				//要插入数据的时间是time的前一天
				Log.d("day", "insert day");
				
				
				if(true){//插入并删除成功
					

					//判断今天时间是否是月初第一天，若是则将上个月的天历史数据计算出月平均情况，插入月表
					
					if(time.monthDay==/*1*/19){
						//插入上个月的数据;
						
						Log.d("day", "insert month");
						
					}
					
					//设定下一天插入日数据的闹钟，明天同一时刻执行相同操作

					daymanager.set(AlarmManager.RTC_WAKEUP, getAfterDay(time).toMillis(true), piDay);
				}
				flag=0;
			}else {
				continue;
			}
		}
	}

	//获取明天时间  当前数值为测试数值
	private Time getAfterDay(Time time) {
		long timeMill = time.toMillis(true);
		int month = time.month+1;
		//long afterTimeMill = timeMill + (24*60*60*1000);
		Time afterTime = new Time();
		/*afterTime.set(afterTimeMill);
		afterTime.hour = 00;
		afterTime.minute = 10;//次日凌晨过10分钟统计上一天数据
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
