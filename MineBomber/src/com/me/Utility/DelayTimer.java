package com.me.Utility;



public class DelayTimer {
	private long mPrevTime=-1;
	private long mDelay=0;
	public DelayTimer(int delayinmilisecond)
	{
		mDelay=delayinmilisecond;
	}
	public DelayTimer(int delayinmilisecond,boolean autoStart)
	{
		mDelay=delayinmilisecond;
		if(autoStart==true)
			Reset();
	}

	private long getTimeMillis() {
		return System.currentTimeMillis();
	}
	
	public boolean CheckTimeOut()
	{
		 
		if(mPrevTime==-1)
		{
			Reset();
			return false;
		}
		long curTime=getTimeMillis();
		
		if(curTime-mPrevTime>=mDelay)
		{
			mPrevTime=curTime;
			return true;
		}
		
		return false;
	}
	
	public void Reset()
	{
		mPrevTime=getTimeMillis();
	}
    public void Restart(){mPrevTime=-1;}
	
	
}
