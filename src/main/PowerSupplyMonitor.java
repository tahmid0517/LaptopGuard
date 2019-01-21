package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PowerSupplyMonitor 
{
	private static PowerSupplyMonitor instance;
	public static PowerSupplyMonitor getInstance()
	{
		if(instance == null)
			instance = new PowerSupplyMonitor();
		return instance;
	}
	
	public Timer timer;
	public boolean isEnabled = false;
	
	public void enable()
	{
		if(!isEnabled)
		{
			timer = new Timer();
			timer.schedule(new PowerSupplyCheckTask(), 0, 10);
			isEnabled = true;
		}
	}
	
	public void disable()
	{
		if(isEnabled && timer != null)
		{
			timer.cancel();
			System.out.println("Power supply monitor has been stopped.");
			isEnabled = false;
			stopAlarmSound();
		}
	}
	
	public boolean isConnectedToPowerSupply() 
	{
	    try 
	    {
	        Process proc = Runtime.getRuntime().exec
	        		("C:/Program Files (x86)/LaptopGuard/PowerSupplyTest.exe");
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
	
	private class PowerSupplyCheckTask extends TimerTask
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
				playAlarmSound(Settings.getInstance().getAlarmSoundPath());
				EmailSender.send(date.toString());
			}
			lastStatus = currentStatus;
		}
	}
	
	private static Clip clip;
	public void playAlarmSound(String path)
	{
		try
		{
			clip = AudioSystem.getClip();
	        AudioInputStream ais = AudioSystem.getAudioInputStream(new File(path));
	        clip.open(ais);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void stopAlarmSound()
	{
		if(clip == null)
			return;
		clip.stop();
	}
}
