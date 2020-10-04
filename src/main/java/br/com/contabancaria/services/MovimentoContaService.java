package br.com.contabancaria.services;

import java.math.BigDecimal;

import br.com.contabancaria.models.Conta;
import br.com.contabancaria.models.MovimentoConta;
import br.com.contabancaria.response.Response;

public interface MovimentoContaService {
	
	Response<MovimentoConta> realizarTransferencia( Conta contaOrigem, BigDecimal valor, Conta contaDestino );
	
	Response<MovimentoConta> realizarDeposito( Long numeroConta, BigDecimal valor );
	
	Response<MovimentoConta> realizarRetirada( Long numeroConta, BigDecimal valor );

}
