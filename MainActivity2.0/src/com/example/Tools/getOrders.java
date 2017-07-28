package com.example.Tools;

import java.util.ResourceBundle.Control;

import android.util.Log;

public class getOrders {

	/**
	 * 
	 * @param type  有土无土类型  0-->无土   1-->有土
	 * @param attr  要改变的属性 Light,Wind,Sun,Liquid
	 * @param order 开关命令  ON-->开  OFF-->关
	 * @return
	 */
	public static String getManOrders(int type,String attr,String order){

		if(order.equals("ON")){
			order = "ffff01";
		}else {
			order = "ffff02";
		}
		String orderInfo = "";
		if(attr.equals("Light")){
			orderInfo = "light.write,"+order;
		}
		if(attr.equals("Wind")){
			if(type==0){
				orderInfo = "wind0.write,"+order;
			}else {
				orderInfo = "wind1.write,"+order;
			}
		}
		if(attr.equals("Sun")){
			orderInfo = "sun.write,"+order;
		}
		if(attr.equals("Liquid")){
			orderInfo = "liquid.write,"+order;
		}
		Log.d("tag", "manual :"+orderInfo);
		return orderInfo;
	}

	/**
	 * 
	 * @param type 有土/无土类型  0-->有土   1-->有土
	 * @return String[]
	 *  有土:[0]通道名  control
	 *      [1]土温
	 *      [2]土湿
	 *      [3]室温
	 *      [4]室湿
	 *      [5]光强
	 *  无土:[0]通道名  control
	 *      [1]室温
	 *      [2]室湿
	 *      [3]光强
	 */
	private static String[] getAutoData(int type){
		String[] datas = null;
		if(type==0){//无土
			 datas = new String[4];
			 datas[0] = "control.write";
			 //查询数据
			/* datas[1] = 
			 datas[2] = 
			 datas[3] = */
		}else {//有土
			datas = new String[6];
			 datas[0] = "control.write";
			 /*datas[2] = 
			 datas[3] = 
			 datas[4] = 
			 datas[5] = */
					 
			//查询数据
		}
		
		return datas;
	}
	/**
	 * 
	 * @param type 自动控制模式下的有土无土  0-->无土   1-->有土
	 * @return String order 自动控制下的命令
	 */
	public static String getAutoOrder(int type){
		String order = "";
		String[] datas = getAutoData(type);
		for(int i=0;i<datas.length;i++){
			if(i==0){
				order += datas[i]+",";
			}
			order += datas[i]+";";
		}
		Log.d("tag", "auto :"+order);
		return order;
	}
	
	
}
