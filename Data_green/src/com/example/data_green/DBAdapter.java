package com.example.data_green;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	public final String KEY_CHANNEL="channel";
	public final String KEY_DATE="date";
	public final String KEY_DATA="data";
	private static final String TAG="DBAdapter";
	private static final String DATABASE_NAME="database_interim";
	private static final String DATABASE_TABLE="table_interim";
	private static final int DATABASE_VERSION=1;
	private static final String DATABASE_CREATE="create table table_interim( channel integer not null," +
			"date text not null,data double not null);";
	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	public DBAdapter(Context ctx){
		this.context=ctx;
		DBHelper=new DatabaseHelper(context);
	} 
	public static class DatabaseHelper extends SQLiteOpenHelper{
		public DatabaseHelper(Context context) {
			super(context,DATABASE_NAME,null,DATABASE_VERSION);
			// TODO 自动生成的构造函数存根
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO 自动生成的方法存根
			db.execSQL(DATABASE_CREATE);
			System.out.println("数据库输出：数据库"+DATABASE_CREATE+"创建成功！");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO 自动生成的方法存根
			Log.w(TAG,"upgrading database from versiion "+
			oldVersion+"to"+newVersion+", and will drop all old data");
			db.execSQL("drop TABLE if EXISTS titles");
			onCreate(db);
			System.out.println("数据库输出：数据库"+DATABASE_CREATE+"更新成功！");
		}				
	}
	public DBAdapter open() throws SQLException{
		db=DBHelper.getWritableDatabase();
		System.out.println("数据库输出：数据库打开成功！");
		return this;
	}
	public void close(){
	     DBHelper.close();
	     System.out.println("数据库输出：数据库关闭成功！");
	}
	//插入数据
	public long insert(int channel,String date,double data){
		ContentValues value=new ContentValues();
		value.put(KEY_CHANNEL,channel);
		value.put(KEY_DATE,date);
		value.put(KEY_DATA,data);
		System.out.println("数据库输出：数据库插入数据"+value+"成功！");
		return db.insert(DATABASE_TABLE,null, value);
	}
	//查询全部数据
	public Cursor get_all_data(){
		System.out.println("数据库输出：数据库查询所有数据！");
		 Cursor cursor=db.query( DATABASE_TABLE,new String[]{KEY_CHANNEL,KEY_DATE,KEY_DATA},
					null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
		int a=cursor.getInt(0);
		String b=cursor.getString(1);
		double c=cursor.getDouble(2);
		System.out.println("数据库输出  "+a+" "+b+" "+c);
		}while(cursor.moveToNext());
			}
		return cursor;
	}
	public boolean delete_data() {
		System.out.println("数据库输出：数据库删除成功！");
		return db.delete(DATABASE_TABLE,null, null) > 0;
	}


}
