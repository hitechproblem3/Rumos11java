package Controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.util;

public class ScreenController {
	util ut = new util();
	private static Stage stage;

	private static Scene mainScene;
	
	public void iniciaScreen(Stage primaryStage) throws IOException {

		stage = primaryStage;
		stage.setTitle("Banco");
		stage.setOnCloseRequest(e -> {
			e.consume();
			closeProgram();
		});

		Parent fxmlMain = FXMLLoader.load(getClass().getResource("../views/Main.fxml"));
		mainScene = new Scene(fxmlMain, 702, 460);

		stage.setScene(mainScene);
		stage.show();

	}
	
    public void closeProgram() {
        if (ut.Questao("Saida da aplicação", "Deseja sair da aplicação?")) {
            stage.close();
        }
    }
}
