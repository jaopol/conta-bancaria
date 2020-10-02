package br.com.contabancaria.services.impl;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.contabancaria.models.Conta;
import br.com.contabancaria.services.ContaService;

@Service
public class ContaServiceImpl implements ContaService {

	@Override
	public Optional<Conta> abrirConta(Conta conta) {
		
		return null;
	}

	@Override
	public Conta realizarTransferencia(Conta contaOrigem, BigDecimal valor, Conta contaDestino) {
		
		return null;
	}

	@Override
	public Conta realizarDeposito(Long numeroConta, BigDecimal valor) {
		
		return null;
	}

	@Override
	public Conta realizarRetirada(Long numeroConta, BigDecimal valor) {
		
		return null;
	}

}
