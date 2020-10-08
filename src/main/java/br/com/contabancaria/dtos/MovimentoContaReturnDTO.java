package br.com.contabancaria.dtos;

import java.math.BigDecimal;

import br.com.contabancaria.enums.TipoTransacao;
import br.com.contabancaria.models.MovimentoConta;
import lombok.Data;

@Data
public class MovimentoContaReturnDTO {
	
	private Integer numeroConta;
	
	private BigDecimal novoSaldoConta;
	
	private TipoTransacao tipoTransacao;
	
	private BigDecimal valorTransacao;

	
	public MovimentoContaReturnDTO() {
		
	}
	
	public MovimentoContaReturnDTO( Integer numeroConta, BigDecimal novoSaldo, TipoTransacao tipoTransacao, BigDecimal valorTransacao ) {
		this.numeroConta = numeroConta;
		this.novoSaldoConta = novoSaldo;
		this.tipoTransacao = tipoTransacao;
		this.valorTransacao = valorTransacao;
	}

	public MovimentoContaReturnDTO(MovimentoConta movimento) {
		this.numeroConta = movimento.getConta().getNumeroConta();
		this.novoSaldoConta = movimento.getConta().getSaldo();
		this.tipoTransacao = movimento.getTipoMovimento();
		this.valorTransacao = movimento.getValorTransacao();
	}
}
