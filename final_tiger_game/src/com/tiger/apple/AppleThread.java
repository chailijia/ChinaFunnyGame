package com.tiger.apple;


public class AppleThread extends Thread
{	
	int 	sleepSpan 	= 20;		//休眠时间
	boolean flag 		= false;	//线程执行标志位
	Apple	apple 		= null;
	public AppleThread(Apple	apple)
	{
		this.apple = apple ;
		flag = true;		//设置线程执行的标志位为true
	}	
	
	public void Exit()
	{
		flag = false;
	}
	
	public void run()
	{
		while(flag)
		{
			apple.Update();
			
			try{
				Thread.sleep(sleepSpan);		//休眠一段时间				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
