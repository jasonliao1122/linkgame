package jw.database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;
import java.util.Set;

/*************************************************
 * 封装 系统sharedpreferences
 */
public class JwShared {
    private Context mContext;
    private SharedPreferences mSp;
    private SharedPreferences.Editor mEditor;
    /**********************************************
     * 实例化
     * @param iContext 上下文句柄
     * @param name 文件名
     */
    public JwShared(Context iContext,String name)
    {
        mContext = iContext;
        mSp=mContext.getSharedPreferences(name,Context.MODE_PRIVATE);
        mEditor = mSp.edit();
    }

    /*****************************************
     *写入各个类型数据
     */

    public void put(String key,String data)
    {
        mEditor.putString(key,data);
    }
    public void put(String key,int data)
    {
        mEditor.putInt(key,data);
    }
    public void put(String key,boolean data)
    {
        mEditor.putBoolean(key,data);
    }
    public void put(String key,float data)
    {
        mEditor.putFloat(key,data);
    }
    public void put(String key,long data)
    {
        mEditor.putLong(key,data);
    }
    public void put(String key, Set<String> data)
    {
        mEditor.putStringSet(key,data);
    }

    /*******************************
     * 提交数据
     */
    public void commit()
    {
        mEditor.commit();
    }

    /********************************************
     *获得各种类型数据
     */
    public String get(String key,String dvale)
    {
        return mSp.getString(key,dvale);
    }

    public int get(String key,int dvale)
    {
        return mSp.getInt(key,dvale);
    }
    public boolean get(String key,boolean dvale)
    {
        return mSp.getBoolean(key,dvale);
    }
    public float get(String key,float dvale)
    {
        return mSp.getFloat(key,dvale);
    }
    public long get(String key,long dvale)
    {
        return mSp.getLong(key,dvale);
    }
    public Set<String> get(String key,Set<String> dvale)
    {
        return mSp.getStringSet(key,dvale);
    }

    public Map<String, ?> get()
    {
        return mSp.getAll();
    }

    /************************************
     * 删除数据
     * @param key
     */
    public void remove(String key)
    {
        mEditor.remove(key);
    }

    /*************************************
     * 清空数据
     */
    public void clear()
    {
        mEditor.clear();
    }
}
