package jw.view;
import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**************************************************
 * 重构ListView框架，方便后续重复调用
 */
public abstract class MyListView {

    public Context mContext;
    public AbsListView mListView;
    public MyListView(Context iContext, AbsListView iListView,Object[] iData)
    {
        mContext=iContext;
        mListView=iListView;
        List<Map<String,Object>> data=new ArrayList<Map<String,Object>>();
        if(iData!=null) {
            for (int i = 0; i < iData.length; i++)
                data.add(AddItemData(iData[i]));
        }
        mListView.setAdapter(GetSimpleAdapter(data));

        /*************************************************
         * 配置点击事件
         */
        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        OnItemTouch(parent,view,position,id);
                    }
                }
        );
    }

    /********************************************
     * 初始化适配器
     * @param idata
     * @return
     */
    abstract public SimpleAdapter GetSimpleAdapter(List<Map<String,Object>> idata);
    /***
     * SimpleAdapter listAdapter =new SimpleAdapter
     *                         (ListViewTestActivity.this, idata, R.layout.layout_listviewitem,
     *                                 new String[]{"icon","name","prices"},
     *                                 new int[]{R.id.shop_icon,R.id.shop_name,R.id.shop_prices}
     *                         );
     */

    /****************************
     * 点击item事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    abstract public void OnItemTouch(AdapterView<?> parent, View view, int position, long id);

    /***********************
     * 数据赋值到map
     * @param iData
     * @return
     */
    abstract public Map<String,Object> AddItemData(Object iData);
    /**
     * public Map<String, Object> AddItemData(Object iData) {
     *                 Map<String,Object> map=new HashMap<String, Object>();
     *                 map.put("icon", ((ListData)iData).mIcon);
     *                 map.put("name", ((ListData)iData).mName);
     *                 map.put("prices", ((ListData)iData).mPrices);
     *                 return map;
     *             }
     */
}
