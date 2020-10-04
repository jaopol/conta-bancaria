package br.com.contabancaria.services.impl;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import br.com.contabancaria.models.Conta;
import br.com.contabancaria.repositories.ContaRepository;
import br.com.contabancaria.response.Response;
import br.com.contabancaria.services.ContaService;
import br.com.contabancaria.utils.ConstantsUtil;

@Service
public class ContaServiceImpl implements ContaService {
	
	@Autowired
	ContaRepository contaRepository;
	
	@Override
	public Response<Conta> abrirConta(Conta conta) {
		
		Response<Conta> response = new Response<Conta>();
		
		if( StringUtils.isEmpty( conta.getCpf() ) || StringUtils.isEmpty( conta.getNomeCompleto() ) ) {
			return getErroReturnResponse( response, ConstantsUtil.conta.MSG_CPF_NOME_OBRIGATORIO, HttpStatus.PRECONDITION_FAILED );
		}
		
		if( !validaCpf( conta.getCpf() ) ) {
			return getErroReturnResponse( response, ConstantsUtil.conta.MSG_CPF_INVALIDO, HttpStatus.PRECONDITION_FAILED );
		}
		
		Boolean existConta = contaRepository.existsByCpf( conta.getCpf() );
		
		if( existConta ) {
			return getErroReturnResponse(response, ConstantsUtil.conta.MSG_CPF_REPETIDO, HttpStatus.PRECONDITION_FAILED);
		}
		
		conta.setNumeroConta( getNumeroConta() );
		conta.setSaldo( BigDecimal.ZERO );
		
		try {
			
			Optional<Conta> contaOk = Optional.ofNullable( contaRepository.save( conta ) );
			if( contaOk.isPresent() ) {
				response.setData( contaOk.get() );
				response.setStatus( HttpStatus.CREATED );
				response.setMensagens( Arrays.asList( ConstantsUtil.conta.MSG_CADASTRO_SUCESSO ) );
				return response;
			}
			else {
				return getErroReturnResponse( response, ConstantsUtil.conta.MSG_ERROR_DEFAULT, HttpStatus.BAD_REQUEST );
			}
			
		}catch (Exception e) {
			return getErroReturnResponse(response, ConstantsUtil.conta.MSG_ERROR_DEFAULT , HttpStatus.BAD_REQUEST );
		}
		
	}

	private Integer getNumeroConta() {
	
		Random random = new Random();
		return 1 + random.nextInt(99999);
	}

	private Response<Conta> getErroReturnResponse(Response<Conta> response, String msg, HttpStatus status) {
		response.setData( null );
		response.setMensagens( Arrays.asList( msg ) );
		response.setStatus( status );
		return response;
	}
	
	/**
	 * 
	 *Utilizado para validar o CPF. Retorna false para CPF inválido 
	 *
	 */
	private Boolean validaCpf(String cpf) {
		
		CPFValidator cpfValidator = new CPFValidator();
		
		try {
			cpfValidator.assertValid( cpf );
			return true;
			
		} catch (InvalidStateException e) {
			return false;
		}
		
	}

}
