package br.com.contabancaria.services;

import java.math.BigDecimal;
import java.util.Optional;

import br.com.contabancaria.models.Conta;

public interface ContaService {
	
	Optional<Conta> abrirConta( Conta conta );
	
	Conta realizarTransferencia( Conta contaOrigem, BigDecimal valor, Conta contaDestino );
	
	Conta realizarDeposito( Long numeroConta, BigDecimal valor );
	
	Conta realizarRetirada( Long numeroConta, BigDecimal valor );

}
