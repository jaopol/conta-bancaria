package br.com.contabancaria.models;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.contabancaria.enums.TipoTransacao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document( collection = "conta" )
@AllArgsConstructor
@NoArgsConstructor
public class Conta {
	
	@Id
	private String id;
	
	private String numeroConta;
	
	private String banco;
	
	private String nomeCompleto;
	
	private String cpf;
	
	private BigDecimal saldo;
	
	private TipoTransacao tipoMovimento;
	
	private Instant dataTransacao;
	
	private BigDecimal valorTransacao;
	
	@CreatedDate
	private Instant dataCriacao;
	

}
