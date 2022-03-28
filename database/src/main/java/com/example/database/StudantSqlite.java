package com.example.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import jw.database.Sqlite;
import jw.database.Tabel;


/********************************
 * 数据库例子
 * @author Administrator
 *
 */
public class StudantSqlite extends Sqlite {

	public StudantSqlite(Context iContext) {
		super(iContext,"mydata/sqllite","data.db");
        // TODO 自动生成的构造函数存根
        //新建一个学生表
		mSTabel=new StudantTabel(this,"studant");
	}
	/****************************
	 * 表列表
	 */
	//学生表
	public StudantTabel mSTabel;
	
	
	/***************************************
	 * 添加学生表数据表数据
	 * @param idata
	 */
	public void AddStudant(StudantTabel.Data idata)
	{
		List<Tabel.TData> iData = new ArrayList<Tabel.TData>();
		iData.add(idata);
		mSTabel.Insert(iData);
	}
}
