package br.com.contabancaria.dtos;

import java.math.BigDecimal;

import com.mongodb.lang.NonNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MovimentoContaDTO {
	
	@NonNull
	@ApiModelProperty( value = "Número da conta de origem" )
	private Integer numeroContaOrigem;
	
	@NonNull
	@ApiModelProperty( value = "Valor da transação" )
	private Integer numeroContaDestino;
	
	@ApiModelProperty( value = "Número da conta de destino - Obrigatório somente para transferência" )
	private BigDecimal valorTransacao;
	

}
