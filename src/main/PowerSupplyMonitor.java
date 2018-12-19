package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class PowerSupplyMonitor 
{
	public static Timer timer;
	public static boolean isEnabled = false;
	
	public static void enable()
	{
		if(!isEnabled)
		{
			timer = new Timer();
			timer.schedule(new PowerSupplyCheckTask(), 0, 10);
			isEnabled = true;
		}
	}
	
	public static void disable()
	{
		if(isEnabled && timer != null)
		{
			timer.cancel();
			System.out.println("Power supply monitor has been stopped.");
			isEnabled = false;
		}
			
	}
	
	public static boolean isConnectedToPowerSupply() 
	{
	    try 
	    {
	        Process proc = Runtime.getRuntime().exec("C:/Program Files (x86)/LaptopGuard/PowerSupplyTest.exe");
	        InputStream powerSupplyTestOutput = proc.getInputStream();
	        BufferedReader r = new BufferedReader(new InputStreamReader(powerSupplyTestOutput));
	        int result = Integer.parseInt(r.readLine().trim());
	        proc.destroy();
	        r.close();
	        if(result == 0)
	        	return false;
	        else
	        	return true;
	    } 
	    catch (IOException ex) 
	    {
	        System.out.println("Something went wrong");
	        return false;
	    }
	}
	
	private static class PowerSupplyCheckTask extends TimerTask
	{
		private boolean lastStatus;
		public PowerSupplyCheckTask()
		{
			lastStatus = isConnectedToPowerSupply();
		}
		
		@Override
		public void run() 
		{
			boolean currentStatus = isConnectedToPowerSupply();
			if(lastStatus && !currentStatus)
			{
				Date date = new Date();
				System.out.println("DISCONNECTED at: " + date.toString() + "!");
			}
			lastStatus = currentStatus;
		}
	}
	
}
