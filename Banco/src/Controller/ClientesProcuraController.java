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

public class ClientesProcuraController implements Initializable {

	public int id;
	public String nome;
	public String telef;
	public String email;
	public String prof;
	public String tipocli;
	ClientesController Clientes_controller;
	
	@FXML
	private TableView<Cliente> tv_clientes;

	@FXML
	private TableColumn<Cliente, String> colunaId;

	@FXML
	private TableColumn<Cliente, String> colunaNome;

	@FXML
	private TableColumn<Cliente, String> colunaTelefone;

	@FXML
	private TableColumn<Cliente, String> colunaEmail;

	@FXML
	private TableColumn<Cliente, String> colunaProfissao;

	@FXML
	private TableColumn<Cliente, String> colunaTipoCli;

    private final ObservableList<Cliente> oblist = FXCollections.observableArrayList();

    Connection conn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		 try {
	            conn = Datasource.getInstance().abredb();

	            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM clientes");

	            while (rs.next()) {
	                oblist.add(new Cliente(rs.getInt("Id"),
	                						rs.getString("Nome"), 
	                						rs.getString("Telefone"), 
	                						rs.getString("Email"), 
	                						rs.getString("Profissao"), 
	                						rs.getString("TipoCliente")));
	            }

	        } catch (SQLException e) {
	        	System.out.println(e);
	           return;  // e.printStackTrace();
	        }

	        colunaId.setCellValueFactory(new PropertyValueFactory<>("Id"));
	        colunaNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
	        colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
	        colunaEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
	        colunaProfissao.setCellValueFactory(new PropertyValueFactory<>("Profissao"));
	        colunaTipoCli.setCellValueFactory(new PropertyValueFactory<>("TipoCliente"));

	        tv_clientes.setItems(oblist);

	}
	
    public void recebeParametros(ClientesController clientes) {
        Clientes_controller = clientes;
    }
    
    @FXML
    void selecionaRegisto(MouseEvent event) {

        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
            id = tv_clientes.getSelectionModel().getSelectedItem().getId();
            nome = tv_clientes.getSelectionModel().getSelectedItem().getNome();
            telef = tv_clientes.getSelectionModel().getSelectedItem().getTelefone();
            email = tv_clientes.getSelectionModel().getSelectedItem().getEmail();
            prof = tv_clientes.getSelectionModel().getSelectedItem().getProfissao();
            tipocli = tv_clientes.getSelectionModel().getSelectedItem().getTipoCliente();
            Clientes_controller.UpdateScreen(id, nome, telef, email, prof, tipocli);
            Stage scene = (Stage) tv_clientes.getScene().getWindow();
            scene.close();
            Datasource.getInstance().fechadb();
        }

    }
}
