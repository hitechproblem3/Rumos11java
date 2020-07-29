package util;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

public class util {

	 public void Aviso(String titulo, String mens) {
	        Alert dialogoAviso = new Alert(Alert.AlertType.WARNING);
	        dialogoAviso.setTitle(titulo);
	        dialogoAviso.setHeaderText(null);
	        dialogoAviso.setContentText(mens);
	        dialogoAviso.showAndWait();
	    }

	    public boolean Questao(String titulo, String mens) {
	        Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle(titulo);
	        alert.setHeaderText(null);
	        alert.setContentText(mens);

	        Optional<ButtonType> result = alert.showAndWait();
	        if (result.get() == ButtonType.OK) {
	            return true;
	        } else {
	            return false;
	        }

	    }

}
