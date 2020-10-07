package br.com.contabancaria.services;

import java.util.List;

import br.com.contabancaria.models.Conta;
import br.com.contabancaria.response.Response;

public interface ContaService {
	
	Response<Conta> abrirConta( Conta conta );

	List<Conta> findAll();
	
}
