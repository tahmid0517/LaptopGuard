package main;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ui.MainMenu;
import ui.MainWindow;

public class Main extends Application
{
	@Override
	public void start(Stage window) throws Exception 
	{
		MainWindow.initWindow(window);
		splashScreenAndOpenMainWindow();
		
	}
	
	public void splashScreenAndOpenMainWindow()
	{
		final double LOGO_WIDTH = 200;
		final double LOGO_HEIGHT = 200;
		final double DURATION = 0.6;
		Stage splashWind = new Stage();
		splashWind.initStyle(StageStyle.UNDECORATED);
		ImageView imgView = new ImageView();
		Image logo = new Image("resources/logo.png",LOGO_WIDTH,LOGO_HEIGHT,true,true);
		imgView.setImage(logo);
		Group rootNode = new Group();
		Scene scene = new Scene(rootNode,logo.getWidth(),logo.getHeight(),Color.DARKGREY);
		splashWind.setScene(scene);
		rootNode.getChildren().add(imgView);
		splashWind.show();
		PauseTransition pause = new PauseTransition(Duration.seconds(DURATION));
		pause.setOnFinished(e -> 
		{
			splashWind.close();
			MainMenu.getInstance().setAsScene();
		});
		pause.play();
	}
	
	public static void main(String args[]) throws InterruptedException
	{
		launch(args);
	}
}
