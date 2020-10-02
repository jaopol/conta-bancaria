package br.com.contabancaria.models;

import java.math.BigDecimal;
import java.time.Instant;

import br.com.contabancaria.enums.TipoTransacao;
import lombok.Data;

@Data
public class Conta {
	
	private String id;
	
	private Integer numeroConta;
	
	private String nomeCompleto;
	
	private String cpf;
	
	private BigDecimal saldo;
	
	private TipoTransacao tipoMovimento;
	
	private Instant dataTransacao;
	
	private BigDecimal valorTransacao;
	
	private Instant dataCriacao;
	

}
