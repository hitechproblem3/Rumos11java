package Controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import obj.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import modal.Datasource;

public class ContasProcuraController implements Initializable {

	public int id;
	public String numConta;
	public int codCliente;
	public String nomeCliente;
	public String saldo;
	public String tipoConta;

	ContasController Contas_controller;
	
    @FXML
    private TableView<Contas> tv_contas;

    @FXML
    private TableColumn<Contas, String> colunaId;

    @FXML
    private TableColumn<Contas, String> colunaNumConta;
    
    @FXML
    private TableColumn<Contas, String> colunaCliente;

    @FXML
    private TableColumn<Contas, String> colunaSaldo;

    @FXML
    private TableColumn<Contas, String> colunaTipoConta;

    private final ObservableList<Contas> oblist = FXCollections.observableArrayList();

    Connection conn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		 try {
	            conn = Datasource.getInstance().abredb();

	            ResultSet rs = conn.createStatement().executeQuery("SELECT contas.Id, contas.NumConta, contas.Cliente, clientes.Nome,"
	            		+ " contas.saldo, contas.TipoConta FROM contas INNER JOIN clientes WHERE contas.Cliente = clientes.id");

	            while (rs.next()) {
	                oblist.add(new Contas(rs.getInt("Id"),
	                						rs.getString("NumConta"),
	                						rs.getInt("Cliente"),
	                						rs.getString("clientes.Nome"), 
	                						rs.getBigDecimal("saldo"), 
	                						rs.getString("TipoConta")));
	            }

	        } catch (SQLException e) {
	        	System.out.println(e);
	           return;  // e.printStackTrace();
	        }

	        colunaId.setCellValueFactory(new PropertyValueFactory<>("Id"));
	        colunaNumConta.setCellValueFactory(new PropertyValueFactory<>("NumConta"));
	        colunaCliente.setCellValueFactory(new PropertyValueFactory<>("Nome"));
	        colunaSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
	        colunaTipoConta.setCellValueFactory(new PropertyValueFactory<>("TipoConta"));

	        tv_contas.setItems(oblist);

	}
	
    public void recebeParametros(ContasController contas) {
        Contas_controller = contas;
    }
    
    @FXML
    void selecionaRegisto(MouseEvent event) {

        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            id = tv_contas.getSelectionModel().getSelectedItem().getId();
            numConta = tv_contas.getSelectionModel().getSelectedItem().getNumConta();
            codCliente = tv_contas.getSelectionModel().getSelectedItem().getCliente();
            nomeCliente = tv_contas.getSelectionModel().getSelectedItem().getNomeCliente();
            saldo = tv_contas.getSelectionModel().getSelectedItem().getSaldo().toString();
            tipoConta = tv_contas.getSelectionModel().getSelectedItem().getTipoConta();
            Contas_controller.UpdateScreen(id, numConta, codCliente, nomeCliente, saldo, tipoConta);
            Stage scene = (Stage) tv_contas.getScene().getWindow();
            scene.close();
            Datasource.getInstance().fechadb();
        }

    }
}
