package br.com.contabancaria.services;

import java.math.BigDecimal;

import br.com.contabancaria.models.Conta;
import br.com.contabancaria.response.Response;

public interface ContaService {
	
	Response<Conta> abrirConta( Conta conta );
	
	Response<Conta> realizarTransferencia( Conta contaOrigem, BigDecimal valor, Conta contaDestino );
	
	Response<Conta> realizarDeposito( Long numeroConta, BigDecimal valor );
	
	Response<Conta> realizarRetirada( Long numeroConta, BigDecimal valor );

}
