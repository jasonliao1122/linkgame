package com.example.database;

import java.util.List;

import jw.database.JResultSet;
import jw.database.Sqlite;
import jw.database.Tabel;


/***************************************
 * 学生数据库表
 * @author Administrator
 *
 */
public class StudantTabel extends Tabel {

	public StudantTabel(Sqlite iC, String iName) {
		super(iC, iName);

		//姓名 文本  学号 int  成绩 int
		super.Create("NAME           TEXT    NOT NULL,"
				+ "NUMBEL           INT    NOT NULL,"
				+ "CJ           INT    NOT NULL,"
				);
	}

	/******************************************
	 * 数据库表数据
	 */
	static public class Data extends TData
	{
		//姓名 学号 成绩
		public String NAME;
		public int NUMBEL,CJ;
		
		/*******************************************
		 *
		 * @param NUMBEL
		 * @param iNAME
		 * @param iCJ
		 */
		public Data(int NUMBEL, String iNAME,int iCJ)
		{
			NAME=iNAME;
			NUMBEL=NUMBEL;
			CJ=iCJ;
		}

		public Data() {
		}
	}
	
	@Override
	public void SetType() {
		//和 Create 顺序对应
		super.SType="(NAME,NUMBEL,CJ)";
	}

	@Override
	public String SetData(TData iDate) {
		// 设置数据
		String ret="VALUES (";
		ret+="'"+((Data)iDate).NAME+"'"+",";
		ret+="'"+((Data)iDate).NUMBEL+"'"+",";
		ret+="'"+((Data)iDate).CJ+"'";
		ret+=")";
		
		return ret;
	}

	@Override
	public void GetData(List<TData> iData, JResultSet rs) {
		// 获得数据
		Data iDate=new Data();
		iDate.CJ = rs.getInt("CJ");
		iDate.NUMBEL = rs.getInt("NUMBEL");
		iDate.NAME = rs.getString("NAME");
		iData.add(iDate);
	}

	@Override
	public String SetUpDate(TData iDate) {
		// 更新数据
		String ret="set";
		ret+=" NUMBEL = "+((Data)iDate).NUMBEL+",";
		ret+=" NAME = "+"'"+((Data)iDate).NAME+"'"+",";
		ret+=" CJ = "+((Data)iDate).CJ;
		return ret;
	}

}
