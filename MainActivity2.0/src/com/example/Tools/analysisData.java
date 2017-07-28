package com.example.Tools;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;

import com.example.grehousemon.MainActivity;
import com.example.rule.database.Atmosphere;
import com.example.rule.database.CO2;
import com.example.rule.database.DBAdapter;
import com.example.rule.database.Light;
import com.example.rule.database.Liquid;
import com.example.rule.database.WuTuHumidity;
import com.example.rule.database.WuTuTempurature;
import com.example.rule.database.YouTuHumidity;
import com.example.rule.database.YouTuRoomHumidity;
import com.example.rule.database.YouTuRoomTempurature;
import com.example.rule.database.YouTuTempurature;
import com.example.service.revDataThread;

public class analysisData {

	static int i =0;
	/**
	 * 将底层信息按照","进行分割得到字符串数组
	 * 
	 * @param info
	 *            接收到来自底端的数据信息
	 * @return String[] 各项信息拆分得到的数组
	 */
	public static String[] spiltData(String info, String regex) {
		Log.d("data", "执行分割");
		String[] datas = info.split(regex);
		if(regex.equals(",")){
			if(datas[3].equals("")||datas[3].equals("fd")){
				datas[3] = String.valueOf(0.0);
			}
		}
		
		return datas;
	}

	public static Boolean execInsert(String[] data, Context context) {
		i++;
		Log.d("exec", "正在插入");
		Boolean isSuccess = false;
		String type = data[1]; // 传递数据归属类别
		long time = getLongTime(data[2])+25200000+i; // 传递时间
		Log.d("tag","data[3]"+data[3]);
		float value  = 0;
		if(!type.endsWith(".write")){
			value = Float.parseFloat(data[3]); // 属性对应的值
		}else{
			return false;
		}
		if (type.equals("0010Temp.read")) {// 有土土壤温度
			Log.d("0010", "有土土壤温度");
			YouTuTempurature youTuTempurature = new YouTuTempurature();
			youTuTempurature.setTempurature(value, time);
			isSuccess = MainActivity.getDbAdapter().insert(youTuTempurature);
			Log.d("isSuccess", "0010Temp==" + isSuccess);
		} else if (type.equals("0010Humi.read")) {// 有土土壤湿度
			YouTuHumidity youTuHumidity = new YouTuHumidity();
			youTuHumidity.setHumidity(value, time);
			isSuccess = MainActivity.getDbAdapter().insert(youTuHumidity);
			Log.d("isSuccess", "0010Humi==" + isSuccess);
		} else if (type.equals("0011Temp.read")) {// 有土室内温度
			YouTuRoomTempurature youTuRoomTempurature = new YouTuRoomTempurature();
			youTuRoomTempurature.setTempurature(value, time);
			isSuccess = MainActivity.getDbAdapter().insert(youTuRoomTempurature);
			Log.d("isSuccess", "0011Temp==" + isSuccess);
		} else if (type.equals("0011Humi.read")) {// 有土室内湿度
			YouTuRoomHumidity youTuRoomHumidity = new YouTuRoomHumidity();
			youTuRoomHumidity.setHumidity(value, time);
			isSuccess = MainActivity.getDbAdapter().insert(youTuRoomHumidity);
			Log.d("isSuccess", "0011Humi==" + isSuccess);
		} else if (type.equals("0111Temp.read")) {// 无土室内温度
			WuTuTempurature wuTuTempurature = new WuTuTempurature();
			wuTuTempurature.setTempurature(value, time);
			isSuccess = MainActivity.getDbAdapter().insert(wuTuTempurature);
			Log.d("isSuccess", "0111Temp==" + isSuccess);
		} else if (type.equals("0111Humi.read")) {// 无土室内湿度
			WuTuHumidity wuTuHumidity = new WuTuHumidity();
			wuTuHumidity.setHumidity(value, time);
			isSuccess = MainActivity.getDbAdapter().insert(wuTuHumidity);
			Log.d("isSuccess", "0111Humi==" + isSuccess);
		} else if (type.equals("light.read")) {// 光照强度
			Light light = new Light();
			light.setCo2(value, time);
			isSuccess = MainActivity.getDbAdapter().insert(light);
			Log.d("isSuccess", "Light==" + isSuccess);
		} else if (type.equals("co2.read")) {
			CO2 co2 = new CO2();
			co2.setCo2(value, time);
			isSuccess = MainActivity.getDbAdapter().insert(co2);
			Log.d("isSuccess", "CO2==" + isSuccess);
		} else if (type.equals("Atmo.read")) {
			Atmosphere atmosphere = new Atmosphere();
			atmosphere.setAtmosphere(value, time);
			isSuccess = MainActivity.getDbAdapter().insert(atmosphere);
			Log.d("isSuccess", "Atmo==" + isSuccess);
		}else if (type.equals("liquid.read")) {
			 Liquid liquid = new Liquid();
			liquid.setLiquid(value, time);
			isSuccess = MainActivity.getDbAdapter().insert(liquid);
			Log.d("isSuccess", "Liquid==" + isSuccess);
		}else if (type.equals("wind0.read")) {


		}else if (type.equals("wind1.read")) {
			
			
		}else if (type.equals("sun.read")) {
			
			
		}
		return isSuccess;
	}

	private static long getLongTime(String time) {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long timeValue = 0;
		try {
			Date date = sd.parse(time);
			timeValue = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return timeValue;
	}
   
	//获取当前超越标准
	public static HashMap<String, Integer> getOutLimit(String[] info){
		HashMap<String, Integer> map = null;
		String type = info[0];
		Integer value = Integer.getInteger(info[2]);
		//查询是否超过范围
		if(true){
			map = new HashMap<String, Integer>();
			map.put(type, value);
		}
		return map;
	}
	/**
	 * 
	 * @param oriTime 开始时间
	 * @param currentTime  当前时间
	 * @param limit  限制时间  超过时间执行  
	 */
	public static Boolean isInsertAvg(long oriTime,long currentTime,long limit){
		Boolean isRun = false;
		long time = currentTime-oriTime;
		long temp = limit*2;
		if(time%temp>=limit){
			//达到时间限制
			isRun = true;
		}
		return isRun;
	}

}
