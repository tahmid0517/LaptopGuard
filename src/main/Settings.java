package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Settings 
{
	private static Settings instance;
	public static Settings getInstance()
	{
		if(instance == null)
			instance = new Settings();
		return instance;
	}
	
	public final static String BASE_PATH = "C:/Program Files (x86)/LaptopGuard";
	public final static String TORNADO_SIREN = BASE_PATH + "/AlarmSounds/tornadoSiren.wav";
	public final static String MASSIVE_WAR = BASE_PATH + "/AlarmSounds/massiveWar.wav";
	public final static String SUBMARINE_DIVING = BASE_PATH + "/AlarmSounds/submarineDiving.wav";
	
	private static String SETTINGS_PATH = BASE_PATH + "/Settings.txt";
	
	private String preferredAlarmPath;
	private int alarmID;
	private boolean sendEmail;
	private String emailToSendTo;
	public Settings()
	{
		if(!((new File(SETTINGS_PATH)).exists()))
		{
			loadDefaultSettings();
		}
		else
		{
			try
			{
				loadUserSettings();
			}
			catch(Exception e)
			{
				System.out.println("Something went wrong. Loading default settings.");
				loadDefaultSettings();
			}
		}
	}
	
	public void loadDefaultSettings()
	{
		preferredAlarmPath = TORNADO_SIREN;
		alarmID = 0;
		sendEmail = false;
	}
	
	public void loadUserSettings() throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(new File(SETTINGS_PATH)));
		int alarmID = Integer.parseInt(br.readLine());
		this.alarmID = alarmID;
		setAlarmSoundPathWithID();
		int emailPref = Integer.parseInt(br.readLine());
		if(emailPref == 1)
		{
			sendEmail = true;
			emailToSendTo = br.readLine().trim();
		}
		else
			sendEmail = false;
		br.close();
	}
	
	public void setAlarmSoundPathWithID()
	{
		if(alarmID == 0)
			preferredAlarmPath = TORNADO_SIREN;
		else if(alarmID == 1)
			preferredAlarmPath = MASSIVE_WAR;
		else
			preferredAlarmPath = SUBMARINE_DIVING;
	}
	
	public String getAlarmSoundPath()
	{
		return preferredAlarmPath;
	}
	
	public boolean shouldSendEmail()
	{
		return sendEmail;
	}
	
	public String getEmail()
	{
		return emailToSendTo;
	}
	
	public void changeSettings(int alarmID, boolean sendEmail, String email)
	{
		this.alarmID = alarmID;
		setAlarmSoundPathWithID();
		if(sendEmail)
		{
			this.sendEmail = true;
			emailToSendTo = email;
		}
		else
		{
			this.sendEmail = false;
			emailToSendTo = null;
		}
		saveChangedSettings();
	}
	
	public void saveChangedSettings()
	{
		try
		{
			BufferedWriter w = new BufferedWriter(new FileWriter(SETTINGS_PATH));
			w.write(alarmID+"\n");
			if(sendEmail)
			{
				w.write("1\n"+emailToSendTo);
			}
			else
				w.write("0\n");
			w.flush();
			w.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
