package br.com.contabancaria.services;

import java.math.BigDecimal;

import br.com.contabancaria.dtos.MovimentoContaReturnDTO;
import br.com.contabancaria.response.Response;

public interface MovimentoContaService {
	
	Response<MovimentoContaReturnDTO> realizarTransferencia( Integer numeroContaOrigem, BigDecimal valor, Integer numeroContaDestino );
	
	Response<MovimentoContaReturnDTO> realizarDeposito( Integer numeroConta, BigDecimal valor );
	
	Response<MovimentoContaReturnDTO> realizarRetirada( Integer numeroConta, BigDecimal valor );

}
