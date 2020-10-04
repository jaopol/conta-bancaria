package br.com.contabancaria.services;

import br.com.contabancaria.models.Conta;
import br.com.contabancaria.response.Response;

public interface ContaService {
	
	Response<Conta> abrirConta( Conta conta );
	
}
