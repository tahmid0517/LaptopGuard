package ui;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.PowerSupplyMonitor;

public class MainWindow 
{
	public static void initWindow(Stage window)
	{
		MainWindow.window = window;
		window.setTitle("Plug in before enabling.");
		MainWindow.window.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			@Override
			public void handle(WindowEvent e) 
			{
				PowerSupplyMonitor.getInstance().disable();
				SettingsPanel.getInstance().close();
				System.exit(0);
			}
		});
	}
	private static Stage window;
	public static Stage getWindow()
	{
		return window;
	}
}
