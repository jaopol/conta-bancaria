package br.com.contabancaria.dtos;

import java.math.BigDecimal;

import br.com.contabancaria.enums.TipoTransacao;
import lombok.Data;

@Data
public class MovimentoContaReturnDTO {
	
	private Integer numeroConta;
	
	private BigDecimal novoSaldo;
	
	private TipoTransacao tipoTransacao;
	
	private BigDecimal valorTransacao;

	
	public MovimentoContaReturnDTO() {
		
	}
	
	public MovimentoContaReturnDTO( Integer numeroConta, BigDecimal novoSaldo, TipoTransacao tipoTransacao, BigDecimal valorTransacao ) {
		this.numeroConta = numeroConta;
		this.novoSaldo = novoSaldo;
		this.tipoTransacao = tipoTransacao;
		this.valorTransacao = valorTransacao;
	}
}
