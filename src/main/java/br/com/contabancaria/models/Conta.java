package br.com.contabancaria.models;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document( collection = "conta" )
@AllArgsConstructor
@NoArgsConstructor
public class Conta {
	
	@ApiModelProperty(value = "Chave gerada pela API")
	@Id
	private String id;
	
	@ApiModelProperty(value = "Número da conta gerado pela API")
	private Integer numeroConta;
	
	@ApiModelProperty(value = "Nome completo da pessoa")
	private String nomeCompleto;
	
	@ApiModelProperty(value = "CPF válido somente número")
	private String cpf;
	
	@ApiModelProperty(value = "Saldo da conta")
	private BigDecimal saldo;
	
	@ApiModelProperty(value = "Data de criação da conta")
	@CreatedDate
	private Instant dataCriacao;
	
	
	public Conta(String cpf, String nomeCompleto) {
		this.cpf = cpf;
		this.nomeCompleto = nomeCompleto;
	}
	

}
