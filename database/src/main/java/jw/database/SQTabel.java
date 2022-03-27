package jw.database;

import java.util.List;


/***************************************
 * 实例数据库表
 * @author Administrator
 *
 */
public class SQTabel extends Tabel{

	public SQTabel(Sqlite iC, String iName) {
		super(iC, iName);
		super.Create("NAME           TEXT    NOT NULL,"
				+ "LINK           TEXT    NOT NULL,"
				+ "ZZ           TEXT    NOT NULL,"
				+ "ZZTEXT           TEXT    NOT NULL,"
				+ "ZJ            INT     NOT NULL,"
				+ "Y            INT     NOT NULL,"
				+ "LY           TEXT    NOT NULL"
				);
	}

	/******************************************
	 * 数据库表数据
	 */
	static public class Data extends TData
	{
		//public int ID;
		public String NAME,LINK,ZZ,ZZTEXT,LY;
		public int ZJ,Y;
		
		/*******************************************
		 * 
		 * @param iID ID
		 * @param iZJ
		 * @param iY
		 * @param iNAME
		 * @param iLINK
		 * @param iZZ
		 * @param iZZTEXT
		 * @param iLY
		 */
		public Data(int iID,int iZJ,int iY,
				String iNAME,String iLINK,String iZZ,String iZZTEXT,String iLY)
		{
			//ID=iID;
			ZJ=iZJ;
			Y=iY;
			NAME=iNAME;
			LINK=iLINK;
			ZZ=iZZ;
			ZZTEXT=iZZTEXT;
			LY=iLY;
		}

		public Data() {
		}
	}
	
	@Override
	public void SetType() {
		// TODO �Զ����ɵķ������
		//super.SType="(ID,NAME,LINK,ZZ,ZZTEXT,ZJ,Y,LY)";
		super.SType="(NAME,LINK,ZZ,ZZTEXT,ZJ,Y,LY)";
	}

	@Override
	public String SetData(TData iDate) {
		// TODO �Զ����ɵķ������
		String ret="VALUES (";
		
		//ret+=((Data)iDate).ID+",";
		ret+="'"+((Data)iDate).NAME+"'"+",";
		ret+="'"+((Data)iDate).LINK+"'"+",";
		ret+="'"+((Data)iDate).ZZ+"'"+",";
		ret+="'"+((Data)iDate).ZZTEXT+"'"+",";
		ret+=((Data)iDate).ZJ+",";
		ret+=((Data)iDate).Y+",";
		ret+="'"+((Data)iDate).LY+"'";
		ret+=")";
		
		return ret;
	}

	@Override
	public void GetData(List<TData> iData, JResultSet rs) {
		// TODO �Զ����ɵķ������
		Data iDate=new Data();		
		//iDate.ID = rs.getInt("ID");
		iDate.ZJ = rs.getInt("ZJ");
		iDate.Y = rs.getInt("Y");
		iDate.NAME = rs.getString("NAME");
		iDate.LINK  = rs.getString("LINK");
		iDate.ZZ = rs.getString("ZZ");
		iDate.ZZTEXT = rs.getString("ZZTEXT");
		iDate.LY = rs.getString("LY");
		iData.add(iDate);

	}

	@Override
	public String SetUpDate(TData iDate) {
		// TODO �Զ����ɵķ������
		String ret="set";
		//ret+=" ID = "+((Data)iDate).ID+",";
		ret+=" ZJ = "+((Data)iDate).ZJ+",";
		ret+=" Y = "+((Data)iDate).Y+",";
		ret+=" NAME = "+"'"+((Data)iDate).NAME+"'"+",";
		ret+=" LINK = "+"'"+((Data)iDate).LINK+"'"+",";
		ret+=" ZZ = "+"'"+((Data)iDate).ZZ+"'"+",";
		ret+=" ZZTEXT = "+"'"+((Data)iDate).ZZTEXT+"'"+",";
		ret+=" LY = "+"'"+((Data)iDate).LY+"'";
		return ret;
	}

}
