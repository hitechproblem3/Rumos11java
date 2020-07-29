package modal;

import java.math.BigDecimal;
import java.sql.Connection;
import util.*;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Datasource {

	private static Datasource instance = new Datasource();

	public Datasource() {

	}

	public static Datasource getInstance() {
		return instance;
	}

	public static final String DB_NAME = "banco";
	public static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/" + DB_NAME;
	public static final String CONNECTION_USER = "root";
	public static final String CONNECTION_PASS = "Pa$$w0rd";

	public static final String TABLE_CLIENTES = "clientes";
	public static final String CLIENTES_COL_ID = "Id";
	public static final String CLIENTES_COL_NOME = "Nome";
	public static final String CLIENTES_COL_TELEFONE = "Telefone";
	public static final String CLIENTES_COL_EMAIL = "Email";
	public static final String CLIENTES_COL_PROFISSAO = "Profissao";
	public static final String CLIENTES_COL_TIPO_CLI = "TipoCliente";

	public static final String TABLE_CARTOES = "cartoes";
	public static final String CARTOES_COL_ID = "Id";
	public static final String CARTOES_COL_NUMCARTAO = "NumCartao";
	public static final String CARTOES_COL_NUMCONTA = "numConta";
	public static final String CARTOES_COL_DESCRICAO = "Descricao";
	public static final String CARTOES_COL_VALCREDITO = "ValCredito";

	public static final String TABLE_CONTAS = "contas";
	public static final String CONTAS_COL_ID = "Id";
	public static final String CONTAS_COL_NUMCARTAO = "NumConta";
	public static final String CONTAS_COL_NUMCONTA = "Cliente";
	public static final String CONTAS_COL_DESCRICAO = "Saldo";
	public static final String CONTAS_COL_VALCREDITO = "TipoConta";

	public static final String TABLE_CONTAS_MOV = "contas_mov";
	public static final String CONTAS_MOV_COL_ID = "Id";
	public static final String CONTAS_MOV_COL_LINHA = "Linha";
	public static final String CONTAS_MOV_COL_NUMCONTA = "NumConta";
	public static final String CONTAS_MOV_COL_VALOR = "Valor";
	public static final String CONTAS_MOV_COL_DEB_CRE = "Deb_Cre";
	public static final String CONTAS_MOV_COL_NUM_CARTAO = "NumCartao";

	private Connection conn;
	util ut = new util();

	// ************************************************
	// ****** Abre a Base de dados *******
	// ************************************************
	public Connection abredb() {
		try {
			conn = (Connection) DriverManager.getConnection(CONNECTION_STRING, CONNECTION_USER, CONNECTION_PASS);
			return conn;
		} catch (SQLException e) {
			System.out.println("Não foi possivel abrir a base de dados Gcon " + e.getMessage());
			return null;
		}
	}
	// ************************************************

	// ************************************************
	// ****** Fecha a Base de dados *****
	// ************************************************
	public void fechadb() {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {

			System.out.println("Não foi possivel fechar a base de dados " + e.getMessage());
		}

	}

	// ************************************************
	// ****** Fecha a Base de dados *****
	// ************************************************
	public ResultSet pesquisa(String origem,String tabela, String campo, String chave) {
		abredb();
		String qnome= "";
		if (origem.equals("Clientes")) {
			qnome = "SELECT * from " + tabela + " WHERE " + campo + " = ? ";
		} else if (origem.equals("Contas")) {
			qnome = "SELECT * from " + tabela + " WHERE " + campo + " = ? ";
		}
		ResultSet rs;
		try {
			PreparedStatement stqnome = conn.prepareStatement(qnome);
			// stqnome.setString(1, campo);
			// stqnome.setString(1, tabela);
			// stqnome.setString(1, campo);
			if (origem.equals("Clientes")) {
				stqnome.setString(1, chave);
			} else if (origem.equals("Contas")) {
				int cha = Integer.parseInt(chave);
				stqnome.setInt(1, cha);
			}
			rs = stqnome.executeQuery();
			return rs;

		} catch (SQLException e) {
			return null;
		}
	}

	public boolean GravaCliente(String nome, String telef, String email, String prof, String tipoCli) {
		abredb();
		String graCli = "INSERT INTO clientes VALUE ( 0,?, ?, ?, ?, ?)";
		try {
			PreparedStatement stgraCli = conn.prepareStatement(graCli);
			stgraCli.setString(1, nome);
			stgraCli.setString(2, telef);
			stgraCli.setString(3, email);
			stgraCli.setString(4, prof);
			stgraCli.setString(5, tipoCli);
			stgraCli.executeUpdate();
			fechadb();
			return true;
		} catch (SQLException e) {
			ut.Aviso("Erro de gravação", "Atenção - Erro ao gravar os dados do cliente na base de dados " + e);
			fechadb();
			return false;
		}

	}

	public boolean UpdateCliente(String nome, String telef, String email, String prof, String tipoCli) {
		abredb();
		String graCli = "UPDATE clientes SET Nome = ?, Telefone =  ?, Email = ?, Profissao =  ?, TipoConta = ?)";
		try {
			PreparedStatement stgraCli = conn.prepareStatement(graCli);
			stgraCli.setString(1, nome);
			stgraCli.setString(2, telef);
			stgraCli.setString(3, email);
			stgraCli.setString(4, prof);
			stgraCli.setString(5, tipoCli);
			stgraCli.executeUpdate();
			fechadb();
			return true;
		} catch (SQLException e) {
			ut.Aviso("Erro de gravação", "Atenção - Erro ao gravar os dados do cliente na base de dados " + e);
			fechadb();
			return false;
		}
	}

	public boolean ApagaCliente(String idstr) {
		abredb();
		String graCli = "DELETE FROM clientes WHERE Id = ?";
		int id = Integer.parseInt(idstr);
		try {
			PreparedStatement stgraCli = conn.prepareStatement(graCli);
			stgraCli.setInt(1, id);
			System.out.println(stgraCli);
			stgraCli.executeUpdate();
			fechadb();
			return true;
		} catch (SQLException e) {
			ut.Aviso("Erro de gravação", "Atenção - Erro ao gravar os dados do cliente na base de dados " + e);
			fechadb();
			return false;
		}
	}
	
	public boolean GravaContas(String numConta, int numCliente, String tipoConta) {
		abredb();
		String graCli = "INSERT INTO contas VALUE ( 0,?, ?, ?, ?)";
		try {
			PreparedStatement stgraCli = conn.prepareStatement(graCli);
			stgraCli.setString(1, numConta);
			stgraCli.setInt(2, numCliente);
			BigDecimal val = new BigDecimal(0.00);
			stgraCli.setBigDecimal(3, val);
			stgraCli.setString(4, tipoConta);
			stgraCli.executeUpdate();
			fechadb();
			return true;
		} catch (SQLException e) {
			ut.Aviso("Erro de gravação", "Atenção - Erro ao criar os dados da conta na base de dados " + e);
			fechadb();
			return false;
		}
	}
	
	public boolean UpdateContas(String numConta, int numCliente, String tipoConta) {
		abredb();
		String graCli = "UPDATE contas SET NumConta = ?, Cliente =  ?, TipoConta =  ?)";
		try {
			PreparedStatement stgraCli = conn.prepareStatement(graCli);
			stgraCli.setString(1, numConta);
			stgraCli.setInt(2, numCliente);
			stgraCli.setString(3, tipoConta);
			stgraCli.executeUpdate();
			fechadb();
			return true;
		} catch (SQLException e) {
			ut.Aviso("Erro de gravação", "Atenção - Erro ao atualizar os dados da conta na base de dados " + e);
			fechadb();
			return false;
		}
	}
	
	public boolean ApagaContas(String idstr) {
		abredb();
		String graCli = "DELETE FROM clientes WHERE Id = ?";
		int id = Integer.parseInt(idstr);
		try {
			PreparedStatement stgraCli = conn.prepareStatement(graCli);
			stgraCli.setInt(1, id);
			System.out.println(stgraCli);
			stgraCli.executeUpdate();
			fechadb();
			return true;
		} catch (SQLException e) {
			ut.Aviso("Erro de gravação", "Atenção - Erro ao gravar os dados do cliente na base de dados " + e);
			fechadb();
			return false;
		}
	}	
	
	
}
