package ui;

import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.Settings;

public class SettingsPanel 
{
	public static final double WIND_WIDTH = 200;
	public static final double WIND_HEIGHT = 210;
	public static final double SPACING = 10;
	public static final double EMAIL_INPUT_WIDTH = 150;
	
	private static SettingsPanel instance;
	public static SettingsPanel getInstance()
	{
		if(instance == null)
			instance = new SettingsPanel();
		return instance;
	}
	
	private Stage window;
	private VBox rootNode;
	private Scene scene;
	private ComboBox<String> alarmSelector;
	private CheckBox checkBox;
	private TextField emailInput;
	private Timer timer;
	private Button saveBtn;
	public SettingsPanel()
	{
		window = new Stage();
		window.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			@Override
			public void handle(WindowEvent arg0) 
			{
				timer.cancel();
			}
		});
		window.setTitle("Settings");
		rootNode = new VBox();
		rootNode.setAlignment(Pos.CENTER);
		rootNode.setSpacing(SPACING);
		scene = new Scene(rootNode);
		window.setWidth(WIND_WIDTH);
		window.setHeight(WIND_HEIGHT);
		window.setResizable(false);
		window.setScene(scene);
		loadAlarmSelector();
		loadEmailControls();
		loadSaveButton();
	}
	
	public void loadAlarmSelector()
	{
		Label label = new Label("Alarm Sound:");
		String a = "Tornado Siren";
		String b = "War Noises";
		String c = "Submarine";
		alarmSelector = new ComboBox<String>();
		alarmSelector.getItems().addAll(a,b,c);
		String currentAlarm = Settings.getInstance().getAlarmSoundPath();
		if(currentAlarm.equals(Settings.TORNADO_SIREN))
			alarmSelector.getSelectionModel().select(a);
		else if(currentAlarm.equals(Settings.MASSIVE_WAR))
			alarmSelector.getSelectionModel().select(b);
		else
			alarmSelector.getSelectionModel().select(c);
		rootNode.getChildren().addAll(label,alarmSelector);
	}
	
	public void loadEmailControls()
	{
		checkBox = new CheckBox();
		checkBox.setText("Send email notifs?");
		emailInput = new TextField();
		emailInput.setMaxWidth(EMAIL_INPUT_WIDTH);
		if(Settings.getInstance().shouldSendEmail())
		{
			checkBox.setSelected(true);
			emailInput.setText(Settings.getInstance().getEmail());
		}
		rootNode.getChildren().addAll(checkBox,emailInput);
		timer = new Timer();
		timer.schedule(new TimerTask()
			{
				@Override
				public void run() 
				{
					if(checkBox.isSelected())
						emailInput.setEditable(true);
					else
						emailInput.setEditable(false);
				}
			},0,10);
	}
	
	public void loadSaveButton()
	{
		saveBtn = new Button("SAVE SETTINGS");
		saveBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent arg0) 
			{
				int alarmSelectedID = alarmSelector.getSelectionModel().getSelectedIndex();
				boolean sendEmails = checkBox.isSelected();
				String email = emailInput.getText();
				Settings.getInstance().changeSettings(alarmSelectedID,sendEmails,email);
				close();
			}
		});
		rootNode.getChildren().add(saveBtn);
	}
	
	public void show()
	{
		window.show();
	}
	
	public void close()
	{
		window.close();
	}
}
