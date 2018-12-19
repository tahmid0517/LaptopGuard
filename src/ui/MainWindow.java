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
		window.setTitle("LAPTOP GUARD");
		MainWindow.window.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			@Override
			public void handle(WindowEvent e) 
			{
				PowerSupplyMonitor.disable();
			}
		});
	}
	private static Stage window;
	public static Stage getWindow()
	{
		return window;
	}
}
