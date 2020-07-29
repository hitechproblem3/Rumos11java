package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuController implements Initializable {

    Stage procura = new Stage();
    
    @FXML
    private MenuBar Mn_Menu;

    @FXML
    private MenuItem mn_tab_clientes;

    @FXML
    private MenuItem mn_tab_contas;

    @FXML
    private MenuItem mn_tab_cartoes;

    @FXML
    void act_tab_cartoes(ActionEvent event) {

    }

    @FXML
    void act_tab_clientes(ActionEvent event) throws IOException {
    	AbreJanela("../views/clientes.fxml");
    }

    @FXML
    void act_tab_contas(ActionEvent event) throws IOException {
    	AbreJanela("../views/Contas.fxml");
    }

    MenuController menuScreen;
    // Constrotor do controlador
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
        menuScreen = this;
	}    
    
    // *********************************************************************
    // ***     Bloco que controla a abertura de Janelas de pesquisa      ***
    // *********************************************************************
    public void AbreJanela(String fxml) throws IOException {
//		procura.setOnCloseRequest(e -> {
//			e.consume();
//			closeWindow();
//		});
        FXMLLoader loader = new FXMLLoader();
        AnchorPane fxmlDetails = (AnchorPane) loader.load(getClass().getResource(fxml).openStream());
        Scene scene = new Scene(fxmlDetails);
        procura.setScene(scene);
        procura.alwaysOnTopProperty();
    //    procura.initModality(Modality.APPLICATION_MODAL);
        procura.show();
    }

    public void closeWindow() {
            procura.close();
    }

}
