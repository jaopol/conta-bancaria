package br.com.contabancaria.services.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.contabancaria.models.Conta;
import br.com.contabancaria.repositories.ContaRepository;
import br.com.contabancaria.response.Response;
import br.com.contabancaria.services.ContaService;

@Service
public class ContaServiceImpl implements ContaService {
	
	@Autowired
	ContaRepository contaRepository;

	@Override
	public Response<Conta> abrirConta(Conta conta) {
		
		Response<Conta> response = new Response<Conta>();
		Boolean existConta = contaRepository.existsByCpf( conta.getCpf() );
		
		if( existConta ) {
			response.setErros( Arrays.asList( "Conta já Cadastrada" ) );
		}
		
		conta.setBanco( "Donus" );
		conta.setNumeroConta( getNumeroConta() );
		conta.setSaldo( BigDecimal.ZERO );
		
		try {
			
			Optional<Conta> contaOk = Optional.ofNullable( contaRepository.save( conta ) );
			if( contaOk.isEmpty() ) {
				response.setData( contaOk.get() );
				response.setStatus( HttpStatus.CREATED );
				return response;
			}
			else {
				return getDefaultErroResponse(response);
			}
			
		}catch (Exception e) {
			return getDefaultErroResponse(response);
		}
		
	}

	private Integer getNumeroConta() {
	
		Random random = new Random();
		return 1 + random.nextInt(99999);
	}

	private Response<Conta> getDefaultErroResponse(Response<Conta> response) {
		response.setData( null );
		response.setErros( Arrays.asList( "Ocorreu um erro. Tente novamente mais tarde!" ) );
		response.setStatus( HttpStatus.BAD_REQUEST );
		return response;
	}

	@Override
	public Response<Conta> realizarTransferencia(Conta contaOrigem, BigDecimal valor, Conta contaDestino) {
		
		return null;
	}

	@Override
	public Response<Conta> realizarDeposito(Long numeroConta, BigDecimal valor) {
		
		return null;
	}

	@Override
	public Response<Conta> realizarRetirada(Long numeroConta, BigDecimal valor) {
		
		return null;
	}

}
