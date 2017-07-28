package com.example.grehousemon;

import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
/**
 * �̳�activity��ʵ��menu�˳�Ӧ�ó��򼰺�̨���в���
 * ����Ŀ������activity���̳и���
 * @author hust
 *
 */
public class AbstractActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {  
		    case R.id.item_back_run: 
	    		Toast.makeText(this, "��̨����", Toast.LENGTH_SHORT).show();
	    		PackageManager pm = getPackageManager();  
	            ResolveInfo homeInfo = 
	                pm.resolveActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME), 0); 
	    		ActivityInfo ai = homeInfo.activityInfo;
	    		Intent startIntent = new Intent(Intent.ACTION_MAIN);
	    		startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
	    		startIntent.setComponent(new ComponentName(ai.packageName, ai.name));
	    		startActivity(startIntent);
		    	/*if(moveTaskToBack(false)){
		    		Toast.makeText(this, "��̨����", Toast.LENGTH_SHORT).show();
		    	}*/
		        break;  
		    case R.id.item_exit:
		    	Toast.makeText(this, "�˳�Ӧ�ó���", Toast.LENGTH_SHORT).show();
		    	System.exit(0);
		    	//android.os.Process.killProcess(android.os.Process.myPid());
		        break;  
		    default:  
		        break;  
		}  

		return super.onMenuItemSelected(featureId, item);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
