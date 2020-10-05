package br.com.contabancaria.models;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import br.com.contabancaria.enums.TipoTransacao;
import lombok.Data;

@Data
@Document( collection = "movimento_conta" )
public class MovimentoConta {
	
	@Id
	private String id;
	
	private TipoTransacao tipoMovimento;
	
	private Date dataTransacao;
	
	private BigDecimal valorTransacao;
	
	@DBRef
	private Conta conta;
	
	
	public MovimentoConta() {

	}
	
	public MovimentoConta( Conta conta, BigDecimal valorTransacao, TipoTransacao tipoTransacao, Date dataTransacao ) {
		this.conta = conta;
		this.dataTransacao = dataTransacao;
		this.tipoMovimento = tipoTransacao;
		this.valorTransacao = valorTransacao;
	}

}
