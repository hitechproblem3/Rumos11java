package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modal.Datasource;

public class ClientesController implements Initializable {

	ClientesController clientesScreen;
	Stage procura = new Stage();
	util ut = new util();
	String modoAltera;
	String guardaOrigemStage = "Cliente";

	@FXML
	private Label lbl_Id;

	@FXML
	private Label lbl_Aviso;

	@FXML
	private TextField txt_Nome;

	@FXML
	private TextField txt_Telef;

	@FXML
	private TextField txt_Email;

	@FXML
	private TextField txt_Profissao;

	@FXML
	private TextField txt_tipoCli;

	@FXML
	private Label lbl_descr;

	@FXML
	private Button btn_ProcuraCli;

	@FXML
	private Button btn_Cancela;

	@FXML
	private Button btn_Sair;

	@FXML
	private Button btn_Apaga;

	@FXML
	private Button btn_Guarda;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		clientesScreen = this;
		lbl_descr.setText("Por favor preencha o campo com o nome do cliente.");
		txt_Nome.requestFocus();

		// Datasource.getInstance().abredb();
	}

	@FXML
	void act_Apaga(ActionEvent event) throws SQLException {
		if (modoAltera == "A") {
			ResultSet rs = Datasource.getInstance().pesquisa("Contas", "contas", "Cliente", lbl_Id.getText());
			if (rs.next()) {
				ut.Aviso("Cliente com dados.",
						"Atenção, não é possivel efetuar esta operação porque o Cliente tem contas abertas.");
			} else {
				lbl_Aviso.setText("Apagar");
				if (ut.Questao("Apaga Cliente!", "Atenção - deseja eliminar este cliente?")) {
					if (Datasource.getInstance().ApagaCliente(lbl_Id.getText())) {
						ut.Aviso("Cliente eliminado.", "O Cliente foi eliminado da base de dados");
						limpaCampos();
					}
				}
			}
			Datasource.getInstance().fechadb();
		}
	}

	@FXML
	void act_Cancela(ActionEvent event) {
		limpaCampos();
	}

	@FXML
	void act_Nome(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
			event.consume();
			if (txt_Nome.getText().equals("")) {
				ut.Aviso("Erro no campo Nome!", "Atenção é necessário preencher o campo com o nome do Cliente");
				txt_Nome.requestFocus();
			} else {
				try {
					ResultSet rs = Datasource.getInstance().pesquisa(guardaOrigemStage, "clientes", "Nome",
							txt_Nome.getText());
					if (rs.next()) {
						// ut.Aviso("Erro Cliente existente!", "Atenção o cliente pedido já existe em
						// ficheiro.");
						lbl_Id.setText(rs.getString("Id"));
						txt_Nome.setText(rs.getString("Nome"));
						txt_Telef.setText(rs.getString("Telefone"));
						txt_Email.setText(rs.getString("Email"));
						txt_Profissao.setText(rs.getString("Profissao"));
						txt_tipoCli.setText(rs.getString("TipoCliente"));
						lbl_Aviso.setText("Alterar");
						modoAltera = "A";
						btn_Apaga.setDisable(false);
						Datasource.getInstance().fechadb();
					}
				} catch (SQLException e) {
					modoAltera = "N";
					lbl_Aviso.setText("Novo");
				}
				lbl_descr.setText("Por favor preencha o campo com o numero de telefone do cliente.");
				btn_Guarda.setDisable(false);
				txt_Telef.requestFocus();
			}
		}
	}

	@FXML
	void act_Telef(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
			if (!txt_Telef.getText().equals("")) {
				try {
					ResultSet rs = Datasource.getInstance().pesquisa(guardaOrigemStage, "clientes", "Telefone",
							txt_Telef.getText());
					if (rs.next()) {
						if (rs.getInt("Id") != Integer.valueOf(lbl_Id.getText())) {
							ut.Aviso("Erro Telefone existente!",
									"Atenção este numero de telefone já se encontra associado ao cliente "
											+ rs.getString("Nome"));
							txt_Telef.requestFocus();
							return;
						}
					}
				} catch (SQLException e) {

				}
				lbl_descr.setText("Por favor preencha o campo com o E-mail do cliente.");
				txt_Email.requestFocus();
			}
		}
	}

	@FXML
	void act_Email(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
			lbl_descr.setText("Por favor preencha o campo com a profissão do cliente.");
			txt_Profissao.requestFocus();
		}
	}

	@FXML
	void act_Profissao(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
			lbl_descr.setText("Por favor preencha o campo com o tipo de cliente 'Normal' ou 'VIP'.");
			txt_tipoCli.requestFocus();
		}
	}

	@FXML
	void act_tipoCli(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
			if (txt_tipoCli.getText().equals("")) {
				ut.Aviso("Erro no campo de Tipo de Cliente!", "Atenção é obrigatório preencher este campo.");
				txt_tipoCli.requestFocus();
			} else if (txt_tipoCli.getText().toUpperCase().equals("NORMAL")
					|| txt_tipoCli.getText().toUpperCase().equals("VIP")) {
				lbl_descr.setText("");
				btn_Guarda.requestFocus();
			} else
				txt_tipoCli.requestFocus();
		}
	}

	@FXML
	void act_Guarda(ActionEvent event) {
		if (modoAltera == "A") {
			if (Datasource.getInstance().UpdateCliente(txt_Nome.getText(), txt_Telef.getText(), txt_Email.getText(),
					txt_Profissao.getText(), txt_tipoCli.getText())) {
				ut.Aviso("Informação", "Os dados foram guardados com sucesso");
				lbl_Aviso.setText("");
				limpaCampos();
			}
		} else if (Datasource.getInstance().GravaCliente(txt_Nome.getText(), txt_Telef.getText(), txt_Email.getText(),
				txt_Profissao.getText(), txt_tipoCli.getText())) {
			ut.Aviso("Informação", "Os dados foram guardados com sucesso");
			lbl_Aviso.setText("");
			limpaCampos();
		}
	}

	@FXML
	void act_ProcuraCli(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		BorderPane fxmlDetails = (BorderPane) loader
				.load(getClass().getResource("../views/clientesProcura.fxml").openStream());
		ClientesProcuraController ControllerInstancia = (ClientesProcuraController) loader.getController();
		ControllerInstancia.recebeParametros(clientesScreen);
		Scene scene = new Scene(fxmlDetails);
		procura.setScene(scene);
		procura.alwaysOnTopProperty();
		// procura.initModality(Modality.APPLICATION_MODAL);
		procura.show();
	}

	@FXML
	void act_Sair(ActionEvent event) {
		Datasource.getInstance().fechadb();
		Stage scene = (Stage) txt_Nome.getScene().getWindow();
		scene.close();
	}

	// *********************************************************************
	// *** Bloco que atualiza o ecran com os dados inseridos ***
	// *********************************************************************
	public void UpdateScreen(int id, String nome, String telef, String email, String prof, String tipoCli) {
		lbl_Id.setText(Integer.toString(id));
		txt_Nome.setText(nome);
		txt_Telef.setText(telef);
		txt_Email.setText(email);
		txt_Profissao.setText(prof);
		txt_tipoCli.setText(tipoCli);
		lbl_Aviso.setText("Alterar");
		btn_Apaga.setDisable(false);
		btn_Guarda.setDisable(false);
		modoAltera = "A";
		txt_Nome.requestFocus();
	}

	public void limpaCampos() {
		lbl_Id.setText("");
		lbl_descr.setText("");
		lbl_Aviso.setText("");
		txt_Nome.setText("");
		txt_Telef.setText("");
		txt_Email.setText("");
		txt_Profissao.setText("");
		txt_tipoCli.setText("");
		btn_Guarda.setDisable(true);
		btn_Apaga.setDisable(true);
		txt_Nome.requestFocus();
		modoAltera = "";
	}
}
