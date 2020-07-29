package Controller;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import modal.Datasource;
import util.util;

public class ContasController implements Initializable {

	ContasController contasScreen;
	Stage procura = new Stage();
	util ut = new util();
	String modoAltera;
	String guardaOrigemStage = "Contas";

	/// ***********************************************************
	// Variáveis de apoio
	int id = 0;
	String numConta;
	int numCliente = 0;
	BigDecimal saldo = new BigDecimal("0.0");

	@FXML
	private TextField txt_NumConta;

	@FXML
	private TextField txt_CodCliente;

	@FXML
	private TextField txt_TipoConta;

	@FXML
	private Button btn_ProcuraContas;

	@FXML
	private Button btn_Cancela;

	@FXML
	private Button btn_Sair;

	@FXML
	private Button btn_Apaga;

	@FXML
	private Button btn_Guarda;

	@FXML
	private Label lbl_Id;

	@FXML
	private Label lbl_Saldo;

	@FXML
	private Label lbl_descr;

	@FXML
	private Label lbl_Aviso;

	@FXML
	private Label lbl_nomeCliente;

	@FXML
	private Button btn_ProcuraCli1;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		contasScreen = this;
		lbl_descr.setText("Por favor preencha o campo com o número da conta.");
		txt_NumConta.requestFocus();
	}

	@FXML
	void act_NumConta(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
			event.consume();
			if (txt_NumConta.getText().equals("")) {
				ut.Aviso("Erro no campo de número de conta!",
						"Atenção o campo número de conta é de preenchimento obrigatório.");
				txt_NumConta.requestFocus();
			} else {
				try {
					ResultSet rs = Datasource.getInstance().pesquisa(guardaOrigemStage, "contas", "NumConta",
							txt_NumConta.getText());
					if (rs.next()) {
						// ut.Aviso("Erro Cliente existente!", "Atenção o cliente pedido já existe em
						// ficheiro.");
						lbl_Id.setText(rs.getString("Id"));
						numConta =rs.getString("NumConta");
						numCliente = Integer.parseInt(rs.getString("Cliente"));
						lbl_Saldo.setText(rs.getString("Saldo"));
						txt_NumConta.setText(rs.getString("NumConta"));
						txt_TipoConta.setText(rs.getString("TipoConta"));
						// Procura ficha de cliente
						ResultSet rs2 = Datasource.getInstance().pesquisa(guardaOrigemStage, "clientes", "Id",
								rs.getString("Cliente"));
						lbl_nomeCliente.setText(rs2.getString("Nome"));
						lbl_Aviso.setText("Alterar");
						modoAltera = "A";
						btn_Apaga.setDisable(false);
						Datasource.getInstance().fechadb();
					}
				} catch (SQLException e) {
					modoAltera = "N";
					lbl_Aviso.setText("Novo");
					lbl_descr.setText("Por favor preencha o número de conta.");
					txt_NumConta.requestFocus();
					return;
				}
				lbl_descr.setText("Por favor preencha o campo com o numero do cliente.");
				btn_Guarda.setDisable(false);
				txt_CodCliente.requestFocus();
			}
		}
	}

	@FXML
	void act_CodCliente(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
			event.consume();
			if (txt_CodCliente.getText().equals("")) {
				ut.Aviso("Erro no campo Nome!", "Atenção é necessário preencher o campo com o código do Cliente");
				txt_CodCliente.requestFocus();
			} else {
				try {
					ResultSet rs = Datasource.getInstance().pesquisa(guardaOrigemStage, "clientes", "Nome",
							txt_CodCliente.getText());
					if (rs.next()) {
						// ut.Aviso("Erro Cliente existente!", "Atenção o cliente pedido já existe em
						// ficheiro.");
						lbl_nomeCliente.setText(rs.getNString("Nome"));
						Datasource.getInstance().fechadb();
					}
				} catch (SQLException e) {
				}
				lbl_descr.setText("Por favor preencha o campo com o tipo de conta 'Normal' ou ' VIP'.");
				btn_Guarda.setDisable(false);
				txt_TipoConta.requestFocus();
			}
		}
	}

	@FXML
	void act_TipoConta(KeyEvent event) {
		if (event.getCode() == KeyCode.ENTER || event.getCode() == KeyCode.TAB) {
			event.consume();
			if (txt_TipoConta.getText().equals("")) {
				ut.Aviso("Erro no campo Tipo de Conta!", "Atenção é necessário preencher o campo com o tipo de conta.");
				txt_TipoConta.requestFocus();
			} else if (!txt_TipoConta.getText().toUpperCase().equals("NORMAL") || !txt_TipoConta.getText().toUpperCase().equals("VIP")) {
				ut.Aviso("Erro no campo Tipo de Conta!", "Por favor insira 'Normal' ou 'VIP' para o tipo de conta.");
				txt_TipoConta.requestFocus();
			}
			lbl_descr.setText("");
			btn_Guarda.requestFocus();
		}
	}	

	@FXML
	void act_ProcuraCli(ActionEvent event) {

	}

	@FXML
	void act_ProcuraContas(ActionEvent event) {

	}
	
	
	@FXML
	void act_Guarda(ActionEvent event) {
		if (modoAltera == "A") {
			if (Datasource.getInstance().UpdateContas(txt_NumConta.getText(), Integer.parseInt(txt_CodCliente.getText()), txt_TipoConta.getText())) {
				ut.Aviso("Informação", "Os dados foram guardados com sucesso");
				lbl_Aviso.setText("");
				limpaCampos();
			}
		} else if (Datasource.getInstance().GravaContas(txt_NumConta.getText(), Integer.parseInt(txt_CodCliente.getText()), txt_TipoConta.getText())) {
			ut.Aviso("Informação", "Os dados foram guardados com sucesso");
			lbl_Aviso.setText("");
			limpaCampos();
		}
	}

	@FXML
	void act_Sair(ActionEvent event) {

	}

	@FXML
	void act_Apaga(ActionEvent event) {
		if (modoAltera == "A") {
			
			lbl_Aviso.setText("Apagar");
			if (ut.Questao("Apaga Conta!",
					"Atenção - deseja eliminar esta conta asociada ao cliente " + lbl_nomeCliente.getText())) {
				if (Datasource.getInstance().ApagaCliente(lbl_Id.getText())) {
					ut.Aviso("Conta eliminado.", "A conta foi eliminado da base de dados");
					limpaCampos();
				}
			}
		}
	}

	@FXML
	void act_Cancela(ActionEvent event) {
		limpaCampos();
	}
	
	// *********************************************************************
	// *** Bloco que atualiza o ecran com os dados inseridos ***
	// *********************************************************************
	public void UpdateScreen(int id, String numConta, int codCliente, String nomeCliente, String saldo, String tipoConta) {
		lbl_Id.setText(Integer.toString(id));
		txt_NumConta.setText(numConta);
		txt_CodCliente.setText(Integer.toString(codCliente));
		lbl_nomeCliente.setText(nomeCliente);
		lbl_Saldo.setText(saldo);
		txt_TipoConta.setText(tipoConta);
		lbl_Aviso.setText("Alterar");
		btn_Apaga.setDisable(false);
		btn_Guarda.setDisable(false);
		modoAltera = "A";
		txt_NumConta.requestFocus();
	}
	
	public void limpaCampos() {
		lbl_Id.setText("");
		lbl_descr.setText("");
		lbl_Aviso.setText("");
		txt_NumConta.setText("");
		txt_CodCliente.setText("");
		lbl_Saldo.setText("");
		txt_TipoConta.setText("");
		btn_Guarda.setDisable(true);
		btn_Apaga.setDisable(true);
		txt_NumConta.requestFocus();
		modoAltera = "";
	}
}
