package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jw.database.JwFile;
import jw.view.MyListView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Init();

        //动态提示权限
        JwFile.Permissions(this,1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //权限提示框返回结果
        if(requestCode==1)
        {
            for(int i=0;i<permissions.length;i++)
            {
                if(permissions[i].equals("android.permission.WRITE_EXTERNAL_STORAGE"))
                {
                    //外部文件存储权限
                    if(grantResults[i]== PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this,"外部存储权限申请成功",Toast.LENGTH_SHORT);
                    else
                        Toast.makeText(this,"外部存储权限申请失败,程序部分功能无法使用",Toast.LENGTH_SHORT);
                }
            }

        }
    }

    private void Init()
    {
        //初始化标题内容
        MyTitle myTitle=new MyTitle(this,"第五章: 数据存储",true);

        //初始化列表控件
        InitListView();
    }

    private void InitListView()
    {
        MyListView listView=new MyListView(this,findViewById(R.id.listview1),mData) {
            @Override
            public SimpleAdapter GetSimpleAdapter(List<Map<String, Object>> idata) {
                return new SimpleAdapter(MainActivity.this, idata,
                        R.layout.layout_listview_item,
                        new String[]{"icon","name"},
                        new int[]{R.id.listimg1,R.id.listtext1}
                        );
            }

            @Override
            public void OnItemTouch(AdapterView<?> parent, View view, int position, long id) {
                //点击列表控件执行事件
                //跳转到指定Activity
                GoToActivity(mData[position].mClass,position);
            }

            @Override
            public Map<String, Object> AddItemData(Object iData) {
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("icon", ((data)iData).mImg);
                map.put("name", ((data)iData).mName);
                return map;
            }
        };
    }

    private void GoToActivity(Class<?> iClass,int idata)
    {
        Intent intent=new Intent(this,iClass);
        intent.putExtra("data",idata);
        startActivity(intent);
    }
    private data[] mData={
            new data("内部文件存储",R.drawable.n1,FileActivity.class),
            new data("外部文件存储",R.drawable.n2,FileActivity.class),
            new data("SharedPreferences存储",R.drawable.n3,SPActivity.class),
            new data("SQLite数据库存储",R.drawable.n4,MainActivity.class),
    };
    class data{
        public String mName;
        public int mImg;
        public Class<?> mClass;
        public data(String iname,int iimg,Class<?> iClass)
        {
            mName=iname;
            mImg=iimg;
            mClass=iClass;
        }
    }
}