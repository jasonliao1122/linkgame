package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jw.database.Tabel;
import jw.view.MyListView;

public class SQLiteActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s_q_lite);

        //初始化数据库数据
        InitSqlite();
        Init();

        //更新数据
        Updata();
    }

    /**************************************************************
     * 界面设计
     */
    private void Init()
    {
        //初始化标题内容
        MyTitle myTitle=new MyTitle(this,"SQLite数据库",true);

    }

    //初始化list控件
    private void InitListView()
    {
        if(mData==null)
            return;
        MyListView listView=new MyListView(this,findViewById(R.id.listview1),mData) {
            @Override
            public SimpleAdapter GetSimpleAdapter(List<Map<String, Object>> idata) {
                return new SimpleAdapter(SQLiteActivity.this, idata,
                        R.layout.layout_listview_item,
                        new String[]{"icon","name"},
                        new int[]{R.id.listimg1,R.id.listtext1}
                );
            }

            @Override
            public void OnItemTouch(AdapterView<?> parent, View view, int position, long id) {
                //点击列表控件执行事件
                //跳转到指定Activity
                //GoToActivity(mData[position].mClass,position);
            }

            @Override
            public Map<String, Object> AddItemData(Object iData) {
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("icon", ((MainActivity.data)iData).mImg);
                map.put("name", ((MainActivity.data)iData).mName);
                return map;
            }
        };
    }

    /***********************************************************
     * 按键事件 添加学生信息
     * @param view
     */
    public void AddStudant(View view) {

        //学生数据
        StudantTabel.Data tData = new  StudantTabel.Data();
        tData.NAME="张三";
        tData.NUMBEL=1;
        tData.CJ=80;

        //将数据存储到数据库表中
        mStudantSqlite.mSTabel.SetData(tData);

        //更新数据并在listview中显示
        Updata();
    }

    class data
    {
        public String mName;
        public int mNum,mCJ;

        public data(String iName,int iNum,int iCJ)
        {
            mName=iName;
            mNum=iNum;
            mCJ=iCJ;
        }
    }

    private data[] mData=null;

    /***********************************************************************************
     * 与数据库数据交互
     */
    //学生数据库
    private StudantSqlite mStudantSqlite;

    private void InitSqlite()
    {
        mStudantSqlite = new StudantSqlite(this);
    }

    //更新数据库数据
    private void Updata()
    {
        //获取学生表所有数据
        List<Tabel.TData> tAllData;
        tAllData = mStudantSqlite.mSTabel.Select();

        //数据的总长度
        int num=tAllData.size();

        if(num==0)
        {
            //表中无数据
            mData=null;
        }
        else
        {
            mData=new data[num];
            for(int i=0;i<mData.length;i++)
            {
                //按顺序获取数据表中数据
                StudantTabel.Data tData=(StudantTabel.Data)tAllData.get(i);

                //把数据表中数据赋值给listview 数据
                mData[i]=new data(tData.NAME,tData.NUMBEL,tData.CJ);
            }
        }

        //更新listview数据
        InitListView();
    }
}