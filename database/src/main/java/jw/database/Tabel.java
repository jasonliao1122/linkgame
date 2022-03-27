package jw.database;


import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**********************************************
 *数据库 表操作
 * @author Administrator
 *
 */
public abstract class Tabel {

	//数据库
	public Sqlite mSqlit;
	//表名
	public String mName;
	
	/**************
	 *  表
	 */
	
	/*********************************************
	 * 初始化表参数
	 * @param iC
	 * @param iName
	 */
	public Tabel(Sqlite iC , String iName)
	{
		mSqlit=iC;
		mName=iName;
		SetType();
	}
	
	static public class TData
	{
	}
	/***********************************
	 * 新建表
	 * @param iTabel
	 * ID INT PRIMARY KEY     NOT NULL," +
	                   " NAME           TEXT    NOT NULL, " + 
	                   " AGE            INT     NOT NULL, " + 
	                   " ADDRESS        CHAR(50), " + 
	                   " SALARY         REAL"+"
	 */
	public void Create(String iTabel)
	{	
		if(IsInDB()==true)
			return;
		
		SQLiteDatabase c = mSqlit.GetDB();
		if(c!=null)
		{
			try{
		   String sql = "CREATE TABLE "+mName+" " +
	                   "("+iTabel+")";
	       c.execSQL(sql);
	       c.close();
			}
	       catch(Exception e)
			{
			}
		
			
		}
	}

	/*****************************
	 *  插入数据到表中
	 * @param iData 数据列表
	 */
	public void Insert(List<TData> iData)
	{
		SQLiteDatabase c = mSqlit.GetDB();
		if(c!=null)
		{
		  try {
			
			for(int i=0;i<iData.size();i++)
			{
				String sql = "INSERT INTO "+mName+SType+" "+SetData(iData.get(i));
				//Log.d("sql:"+sql);
		    	c.execSQL(sql);
		    	
		    	c.close();
			}

		} catch (Exception e) {
			  // TODO 自动生成的 catch 块
			e.printStackTrace();
			//Log.d("### Tabel Insert err:"+e.toString());
		}
	      
		}
	}
	
	public String SType;
	
	/*****************************************
	 * 设置表头
	 */
	abstract public void SetType();
	
	/****************************************
	 *  插入表数据
	 * @param iDate
	 * @return
	 */
	abstract public String SetData(TData iDate);
	
	/*******************************************
	 *  获取表中所有数据
	 * @return
	 */
	public List<TData> Select()
	{
		List<TData> iData = new ArrayList<TData>(); 
		SQLiteDatabase c = mSqlit.GetDB();
		Cursor rs = null;
		if(c!=null)
		{
		  try {
			  rs = c.rawQuery( "SELECT * FROM "+mName+";",null);
		    while ( rs.moveToNext() ) {
		    	JResultSet tdata=new JResultSet();
		    	tdata.mData=rs;
		        GetData(iData,tdata);
		     }
	       c.close();
		} catch (Exception e) {
			  // TODO 自动生成的 catch 块
			e.printStackTrace();
			//Log.d("### Tabel Select err:"+e.toString());
		}finally {
        	//JWLog.d("finally cursor:"+cursor+"  db:"+db);
			
        	if(rs!=null)
        		rs.close();
            c.close();
        }
	      
		}
		
		return iData;
	}
	
	/********************************************************
	 *  获取数据
	 * @param iData
	 * @param rs
	 * @return
	 */
	abstract public void GetData(List<TData> iData,JResultSet rs);
	
	/*****************************************************
	 * 更新数据
	 * @param iDate 数据
	 * @param sqltype 条件 如 ID=1
	 */
	public void Update(TData iDate,String sqltype)
	{
		SQLiteDatabase c = mSqlit.GetDB();
		if(c!=null)
		{
		  try {
			
			String sql = "UPDATE "+mName+" "+SetUpDate(iDate)+
					" where "+sqltype+";";
		    c.execSQL(sql);
	        c.close();
		} catch (Exception e) {
			  // TODO 自动生成的 catch 块
			e.printStackTrace();
			//Log.d("### Tabel Update err:"+e.toString());
		}
	      
		}
	}
	
	/****************************************
	 * 更新数据格式
	 * @param iDate
	 * @return
	 */
	abstract public String SetUpDate(TData iDate);
	
	/******************************************
	 * 删除表中数据
	 * @param sqltype   条件 如 ID=1
	 */
	public void Delete(String sqltype)
	{
		SQLiteDatabase c = mSqlit.GetDB();
		if(c!=null)
		{
		  try {

			String sql = "DELETE from "+mName+
					" where "+sqltype+";";
			//Log.d("sql:"+sql);
		    c.execSQL(sql);
	        c.close();
		} catch (Exception e) {
			  // TODO 自动生成的 catch 块
			e.printStackTrace();
			//Log.d("### Tabel Delete err:"+e.toString());
		}
	      
		}
	}
	
	/*******************************************
	 *  获取表的总行数
	 * @param sqltype  条件 如 ID=1 null--无条件
	 * @return
	 */
	public int GetCount(String sqltype)
	{
		int ret=0;
		SQLiteDatabase c = mSqlit.GetDB();
		if(c!=null)
		{
		  try {
			String sql;
			if(sqltype==null)
			{
				sql="select count(*) totalCount from "+mName+" "+";";
			}
			else
			{
				sql="select count(*) totalCount from "+mName+" "+
			        "where "+sqltype+";";
			}
			Cursor rs=c.rawQuery(sql,null);
			while ( rs.moveToNext() ) {
				//ret=rs.getInt("totalCount");
				ret=rs.getInt(rs.getColumnIndex("totalCount"));
			}
		   rs.close();
	       c.close();
		} catch (Exception e) {
			e.printStackTrace();
			//Log.d("### Tabel GetCount err:"+e.toString());
		}
	      
		}
		
		return ret;
	}
	
	/******************************************************
	 * 表是否存在数据库中
	 * @return
	 */
	private boolean IsInDB()
	{
		int ret=0;
		SQLiteDatabase c = mSqlit.GetDB();
		if(c!=null)
		{
		  try {
			String sql;
			sql="select count(*) totalCount from sqlite_master where type ='table' and name='"+mName+"' ;";
			
			//Log.d("sql="+sql);
			Cursor rs=c.rawQuery(sql,null);
			while ( rs.moveToNext() ) {
				ret=rs.getInt(rs.getColumnIndex("totalCount"));
			}
		   rs.close();
	       c.close();
		} catch (Exception e) {
			e.printStackTrace();
			//Log.d("### Tabel IsInDB err:"+e.toString());
		}
	      
		}
		
		//Log.d("ret="+ret);
		if(ret==0)
			return false;
		
		return true;
	}
}
