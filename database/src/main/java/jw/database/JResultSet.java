package jw.database;

import android.database.Cursor;

/************************************
 *数据库数据对象
 * @author Administrator
 *
 */
public class JResultSet {

	public Cursor mData;
	
	public int getInt(String iName)
	{
		return mData.getInt(mData.getColumnIndex(iName));
	}
	
	public String getString(String iName)
	{
		return mData.getString(mData.getColumnIndex(iName));
	}
}
