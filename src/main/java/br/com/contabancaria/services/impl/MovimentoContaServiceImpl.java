package br.com.contabancaria.services.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.com.contabancaria.models.Conta;
import br.com.contabancaria.models.MovimentoConta;
import br.com.contabancaria.response.Response;
import br.com.contabancaria.services.MovimentoContaService;

@Service
public class MovimentoContaServiceImpl implements MovimentoContaService {

	@Override
	public Response<MovimentoConta> realizarTransferencia(Conta contaOrigem, BigDecimal valor, Conta contaDestino) {
		
		return null;
	}

	@Override
	public Response<MovimentoConta> realizarDeposito(Long numeroConta, BigDecimal valor) {
		
		return null;
	}

	@Override
	public Response<MovimentoConta> realizarRetirada(Long numeroConta, BigDecimal valor) {
		
		return null;
	}

}
