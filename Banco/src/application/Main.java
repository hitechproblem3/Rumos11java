package application;

import java.io.IOException;
import Controller.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage home_page_stage) throws IOException {
		ScreenController sc = new ScreenController();
		sc.iniciaScreen(home_page_stage);

	}

	public static void main(String[] args) {
		launch(args);
	}
}
