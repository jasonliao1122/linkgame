package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
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
        MyListView listView=new MyListView(this,findViewById(R.id.listview2),mData) {
            @Override
            public SimpleAdapter GetSimpleAdapter(List<Map<String, Object>> idata) {
                return new SimpleAdapter(SQLiteActivity.this, idata,
                        R.layout.layout_listview_item2,
                        new String[]{"id","name","cj"},
                        new int[]{R.id.id,R.id.name,R.id.cj}
                )
                {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        //return super.getView(position, convertView, parent);
                        //重定义删除功能
                        View item=super.getView(position, convertView, parent);
                        item.findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Log.d("jason","#### del position="+position);
                                if(mData!=null)
                                DelDataForID(mData[position].mID);
                            }
                        });
                        return item;
                    }
                };
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
                map.put("id", ((data)iData).mID);
                //map.put("numbel", ((data)iData).mNum);
                map.put("name", ((data)iData).mName);
                map.put("cj", ((data)iData).mCJ);
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
        tData.NAME=RandomData.GetName();
        tData.NUMBEL=1;
        tData.CJ=80+RandomData.getNum(20);

        List<Tabel.TData> listdata = new ArrayList<>();
        listdata.add(tData);
        //将数据存储到数据库表中
        mStudantSqlite.mSTabel.Insert(listdata);

        //更新数据并在listview中显示
        Updata();
    }

    /******************************************
     * 全部删除
     * @param view
     */
    public void DelAll(View view) {

        //删除数据库表中数据
        mStudantSqlite.mSTabel.Delete(null);
        //更新数据并在listview中显示
        Updata();
    }

    class data
    {
        public String mName;
        public int mNum,mCJ,mID;

        public data(int iid,String iName,int iNum,int iCJ)
        {
            mName=iName;
            mNum=iNum;
            mCJ=iCJ;
            mID=iid;
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

        Log.d("jason","#### database Updata num="+num);

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
                mData[i]=new data(tData._id,tData.NAME,tData.NUMBEL,tData.CJ);

                Log.d("jason","#### database Updata i="+i+" name:"+mData[i].mName+
                        "  numbel:"+mData[i].mNum+"  cj:"+mData[i].mCJ);
            }
        }

        //更新listview数据
        InitListView();
    }

    /********************************************
     * 根据序号删除数据
     * @param id
     */
    private void DelDataForID(int id)
    {
        mStudantSqlite.mSTabel.Delete("_id="+id);
        Updata();
    }
}