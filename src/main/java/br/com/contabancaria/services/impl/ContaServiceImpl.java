package br.com.contabancaria.services.impl;

import java.math.BigDecimal;
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
		
		
		if( StringUtils.isEmpty( conta.getCpf() ) || StringUtils.isEmpty( conta.getNomeCompleto() ) ) {
			return new Response<Conta>( ConstantsUtil.Conta.MSG_CPF_NOME_OBRIGATORIO, HttpStatus.PRECONDITION_FAILED, null );
		}
		
		if( !validaCpf( conta.getCpf() ) ) {
			return new Response<Conta>( ConstantsUtil.Conta.MSG_CPF_INVALIDO, HttpStatus.PRECONDITION_FAILED, null );
		}
		
		conta.setCpf( conta.getCpf().replace( "." , "").replace("-", "") );		
		Boolean existConta = contaRepository.existsByCpf( conta.getCpf() );
		
		if( existConta ) {
			return new Response<Conta>( ConstantsUtil.Conta.MSG_CPF_REPETIDO, HttpStatus.PRECONDITION_FAILED, null);
		}
		
		conta.setNumeroConta( getNumeroConta() );
		conta.setSaldo( BigDecimal.ZERO );
		
		try {
			
			Optional<Conta> contaOk = Optional.ofNullable( contaRepository.save( conta ) );
			if( contaOk.isPresent() ) {
				return new Response<Conta>( ConstantsUtil.Conta.MSG_CADASTRO_SUCESSO, HttpStatus.CREATED, contaOk.get() );
			}
			else {
				return new Response<Conta>( ConstantsUtil.Conta.MSG_ERROR_DEFAULT, HttpStatus.BAD_REQUEST, null );
			}
			
		}catch (Exception e) {
			return new Response<Conta>(ConstantsUtil.Conta.MSG_ERROR_DEFAULT , HttpStatus.BAD_REQUEST, null );
		}
		
	}

	private Integer getNumeroConta() {
	
		Random random = new Random();
		return 1 + random.nextInt(99999);
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
