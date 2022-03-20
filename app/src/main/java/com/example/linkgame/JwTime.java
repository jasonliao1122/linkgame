package com.example.linkgame;

import java.util.Date;

import android.os.Handler;

/************************************
 * 定时器
 * @author jason
 *
 */
public class JwTime {
	public int timenum;
	public int cureenttime=0;
	public int show_type = 1;

	/***************************************
	 * 设置定时器启动时间
	 * @param i_timenum
	 */
	public JwTime(int i_timenum)
	{
		timenum=i_timenum;
	}
	
	
	/**************************************
	 * 定时器到达指定时间执行的内容
	 * @return
	 */
	public int run()
	{
		return 0;
	}
	

	/*********************************************
	 * 获取系统时间
	 * @return
	 */
	static public long getsystimems()
	{
		Date date = new Date();  
		long tt=date.getTime();//curDate.toMillis(true);
		return tt;
	}
	
	
	/*********************************************
	 * 开启定时器
	 */
	public void start_time()
	{
		stop_time();
		handler.postDelayed(runnable, timenum); //每隔1s执行
		IsRuning=true;
	}

	//判断定时器是否运行
	public boolean IsRuning=false;
	/********************************************
	 * 关闭定时器
	 */
	public void stop_time()
	{

		handler.removeCallbacks(runnable);
		IsRuning=false;
	}
	
	/**********************************************
	 * 重设定时器
	 * @param i_time
	 */
	public void resettime(int i_time)
	{
		timenum=i_time;
	}
	
	private Handler handler = new Handler();  
	private Runnable runnable = new Runnable() {  
	  
	        @Override  
	        public void run() {  
	            // handler自带方法实现定时器  
	            try {
					JwTime.this.run();
	            } catch (Exception e) {  
	                // TODO Auto-generated catch block  
	            }  
	        }  
	    };  
}
