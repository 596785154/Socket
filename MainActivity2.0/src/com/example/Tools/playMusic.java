package com.example.Tools;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.media.MediaPlayer;
import android.text.format.Time;

/**
 * 播放音乐功能代码  （王光琪）
 * @author hust
 *
 */
public class playMusic {
	MediaPlayer mediaplayer=new MediaPlayer();
	public void play(String path){
		 start(path); //aaaaaa为播放的音乐的路径
		 Timer timer  = new Timer();
	        timer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	                stop();
	            }
	        }, 1000*60);   
	}
public void start(String path){
	try {
        mediaplayer.setDataSource(path);
        mediaplayer.prepare();
        mediaplayer.start();    
     } catch (IllegalArgumentException e) {
        e.printStackTrace();
     } catch (IllegalStateException e) {
        e.printStackTrace();
     } catch (IOException e) {
        e.printStackTrace();
     }
}
public void stop(){	
		mediaplayer.stop();
		mediaplayer.release();
}
}
