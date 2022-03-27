package jw.database;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;


/********************************
 * 数据库例子
 * @author Administrator
 *
 */
public class TestSqlite extends Sqlite{

	public TestSqlite(Context iContext) {
		super(iContext,"mydata/book","book.db");
        // TODO 自动生成的构造函数存根
        //新建一个表
		mSQTabel=new SQTabel(this,"SQ");
	}
	/****************************
	 * 表列表
	 */
	//测试表
	public SQTabel mSQTabel;
	
	
	/***************************************
	 * 添加表数据
	 * @param idata
	 */
	public void AddSQ(SQTabel.Data idata)
	{
		List<Tabel.TData> iData = new ArrayList<Tabel.TData>();
		iData.add(idata);
		mSQTabel.Insert(iData);
	}
}
