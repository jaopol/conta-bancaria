package br.com.contabancaria.models;

import java.math.BigDecimal;
import java.time.Instant;

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
	
	private Instant dataTransacao;
	
	private BigDecimal valorTransacao;
	
	@DBRef
	private Conta conta;

}
