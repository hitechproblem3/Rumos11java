package obj;

import java.math.BigDecimal;

public class Contas {
	
	private int id;
	private String numConta;
	private int cliente;
	private String nomeCliente;
	private BigDecimal saldo;
	private String tipoConta;
	
	
	public Contas(int id, String numConta, int cliente, String nomeCliente, BigDecimal saldo, String tipoConta) {
		super();
		this.id = id;
		this.numConta = numConta;
		this.cliente = cliente;
		this.nomeCliente = nomeCliente;
		this.saldo = saldo;
		this.tipoConta = tipoConta;
	}
	
	public Contas(int id, String numConta, int cliente, BigDecimal saldo, String tipoConta) {
		super();
		this.id = id;
		this.numConta = numConta;
		this.cliente = cliente;
		this.saldo = saldo;
		this.tipoConta = tipoConta;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumConta() {
		return numConta;
	}
	public void setNumConta(String numConta) {
		this.numConta = numConta;
	}
	public int getCliente() {
		return cliente;
	}
	public void setCliente(int cliente) {
		this.cliente = cliente;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	public String getTipoConta() {
		return tipoConta;
	}
	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	
}
