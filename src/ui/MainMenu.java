package ui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import main.PowerSupplyMonitor;

public class MainMenu 
{
	public static final double WIND_WIDTH = 300;
	public static final double WIND_HEIGHT = 430;
	public static final double ENABLE_BTN_WIDTH = 100;
	public static final double LOGO_WIDTH = 300;
	public static final double LOGO_HEIGHT = 300;
	public static final double LOGO_BTN_SPACING = 10;
	public static final double BTN_SPACING = 10;
	public static final double BTN_WIDTH = 100;
	public static final double BTN_HEIGHT = 30;
	
	private static MainMenu instance;
	public static MainMenu getInstance()
	{
		if(instance == null)
		{
			instance = new MainMenu();
		}
		return instance;
	}
	
	
	private Group rootNode;
	public Scene scene;
	private Button enableBtn;
	public MainMenu()
	{
		rootNode = new Group();
		scene = new Scene(rootNode);
		ImageView imgView = new ImageView();
		Image logo = new Image("/resources/logo.png",LOGO_WIDTH, LOGO_HEIGHT,true,true);
		imgView.setImage(logo);
		rootNode.getChildren().add(imgView);
		double LOGO_POS_X = (WIND_WIDTH - logo.getWidth()) / 2;
		double LOGO_POS_Y = 0;
		imgView.setX(LOGO_POS_X);
		imgView.setY(LOGO_POS_Y);
		createButtons();
	}
	
	public void enable()
	{
		System.out.println("Enabled");
		PowerSupplyMonitor.getInstance().enable();
		try
		{
			Runtime.getRuntime().exec("C:/Program Files (x86)/LaptopGuard/LockComputer.exe");
		}
		catch(Exception ex) {}
		enableBtn.setOnAction(new EventHandler<ActionEvent> ()
		{
			@Override
			public void handle(ActionEvent e)
			{
				disable();
			}
		});
		enableBtn.setText("DISABLE");
	}
	
	public void disable()
	{
		System.out.println("Disabled");
		PowerSupplyMonitor.getInstance().disable();
		enableBtn.setText("ENABLE");
		enableBtn.setOnAction(new EventHandler<ActionEvent> ()
		{
			@Override
			public void handle(ActionEvent e)
			{
				enable();
			}
		});
	}
	
	public void createButtons()
	{
		enableBtn = new Button("ENABLE");
		enableBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent e)
			{
				enable();
			}
		});
		Button settingsBtn = new Button("SETTINGS");
		enableBtn.setMinSize(BTN_WIDTH, BTN_HEIGHT);
		enableBtn.setMaxSize(BTN_WIDTH, BTN_HEIGHT);
		settingsBtn.setMinSize(BTN_WIDTH, BTN_HEIGHT);
		settingsBtn.setMaxSize(BTN_WIDTH, BTN_HEIGHT);
		double centred_btn_x_pos = (WIND_WIDTH - BTN_WIDTH) / 2;
		enableBtn.setLayoutX(centred_btn_x_pos);
		settingsBtn.setLayoutX(centred_btn_x_pos);
		enableBtn.setLayoutY(LOGO_HEIGHT + LOGO_BTN_SPACING);
		settingsBtn.setLayoutY(enableBtn.getLayoutY() + BTN_HEIGHT + BTN_SPACING );
		settingsBtn.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override
			public void handle(ActionEvent e)
			{
				SettingsPanel.getInstance().show();
			}
		});
		rootNode.getChildren().addAll(enableBtn,settingsBtn);
	}
	
	public void setAsScene()
	{
		MainWindow.getWindow().setWidth(WIND_WIDTH);
		MainWindow.getWindow().setHeight(WIND_HEIGHT);
		MainWindow.getWindow().setResizable(false);
		MainWindow.getWindow().setScene(scene);
		MainWindow.getWindow().show();
	}
}
