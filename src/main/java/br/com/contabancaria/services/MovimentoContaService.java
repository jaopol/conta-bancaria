package br.com.contabancaria.services;

import java.math.BigDecimal;

import br.com.contabancaria.dtos.MovimentoContaReturnDTO;
import br.com.contabancaria.models.Conta;
import br.com.contabancaria.response.Response;

public interface MovimentoContaService {
	
	Response<MovimentoContaReturnDTO> realizarTransferencia( Conta contaOrigem, BigDecimal valor, Conta contaDestino );
	
	Response<MovimentoContaReturnDTO> realizarDeposito( Integer numeroConta, BigDecimal valor );
	
	Response<MovimentoContaReturnDTO> realizarRetirada( Integer numeroConta, BigDecimal valor );

}
