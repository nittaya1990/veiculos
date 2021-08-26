package entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name ="veiculos")
public class EVeiculo implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "modelo")
	private String modelo;
	
	@Column(name = "fabricante")
	private String fabricante;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "anoFabricacao")
	private String anoFabricacao;
	
	@Column(name = "anoModelo")
	private String anoModelo;
	
	@Column(name = "valor")
	private BigDecimal valor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String sexo) {
		this.descricao = sexo;
	}


	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String nome) {
		this.fabricante = nome;
	}

	public String getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(String email) {
		this.anoFabricacao = email;
	}

	public String getAnoModelo() {
		return anoModelo;
	}

	public void setAnoModelo(String observacao) {
		this.anoModelo = observacao;
	}

	
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
}
