package jw.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/*******************************************************
 * sqlit 数据库
 * @author Administrator
 *
 */
public class Sqlite {

	//android 数据库
	private SQLiteOpenHelper mDB;

	//上下文句柄
	private Context mContext;
	/************************
	 *数据库名
	 */
	public String mName;
	public String mPth;
	public Sqlite(Context iContext,String ipth,String iName)
	{
		mContext=iContext;
		mPth=ipth;
		mName=iName;
		
		File file = new File(mPth);
		
		if (!file.exists()) 
		{
            //文件夹不存在，新建文件夹
			JwFile.MKDIR(mPth);
       }
		
		mDB=new SQLiteOpenHelper(mContext,"/sdcard/"+mPth+"/"+mName,null,2)
		{

			@Override
			public void onCreate(SQLiteDatabase db) {
				// TODO 自动生成的方法存根
				
			}

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion,
					int newVersion) {
				// TODO 自动生成的方法存根
				
			}
			
		};
	}
	/*****************************************
	 * 连接到一个现有的数据库。如果数据库不存在，那么它就会被创建，最后将返回一个数据库对象。
	 *
	 */
	public SQLiteDatabase GetDB()
	{
		return mDB.getWritableDatabase();
	}
	
}
