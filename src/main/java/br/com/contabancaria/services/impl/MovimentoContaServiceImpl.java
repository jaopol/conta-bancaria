package br.com.contabancaria.services.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import br.com.contabancaria.dtos.MovimentoContaReturnDTO;
import br.com.contabancaria.enums.TipoTransacao;
import br.com.contabancaria.models.Conta;
import br.com.contabancaria.models.MovimentoConta;
import br.com.contabancaria.repositories.ContaRepository;
import br.com.contabancaria.repositories.MovimentoContaRepository;
import br.com.contabancaria.response.Response;
import br.com.contabancaria.services.MovimentoContaService;
import br.com.contabancaria.utils.ConstantsUtil;

@Service
public class MovimentoContaServiceImpl implements MovimentoContaService {
	
	@Autowired
	private MovimentoContaRepository movimentoContaRepository;
	
	@Autowired
	private ContaRepository contaRepository;

	@Override
	public Response<MovimentoContaReturnDTO> realizarTransferencia(Conta contaOrigem, BigDecimal valor, Conta contaDestino) {
		
		return null;
	}

	@Override
	public Response<MovimentoContaReturnDTO> realizarDeposito(Integer numeroConta, BigDecimal valor) {
		
		Conta conta = contaRepository.findByNumeroConta( numeroConta );
		
		if( conta == null ) {
			return new Response<MovimentoContaReturnDTO>( ConstantsUtil.MovimentoConta.MSG_CONTA_INVALIDA , HttpStatus.PRECONDITION_FAILED, null );
		}
		//No deposito acrescenta 0.5% no valor depositado
		BigDecimal valorAcrescido = valor.add( valor.multiply( new BigDecimal( 0.005 ) ) ); 
		BigDecimal novoSaldo = conta.getSaldo().add( new BigDecimal( valorAcrescido.doubleValue() ) ); 
		
		MovimentoConta movimentoConta = new MovimentoConta( conta, valor, TipoTransacao.D, new Date() );
		conta.setSaldo(novoSaldo);
		try {
			
			movimentoContaRepository.save( movimentoConta );
			contaRepository.save( conta );
			
			MovimentoContaReturnDTO returnDTO = new MovimentoContaReturnDTO( numeroConta, conta.getSaldo(), 
												movimentoConta.getTipoMovimento(), movimentoConta.getValorTransacao()  );
			
			return new Response<MovimentoContaReturnDTO>( ConstantsUtil.MovimentoConta.MSG_DEPOSITO_OK, HttpStatus.CREATED, returnDTO );
			
		}catch (Exception e) {
			return new Response<MovimentoContaReturnDTO>( ConstantsUtil.MovimentoConta.MSG_ERROR_DEFAULT , HttpStatus.BAD_REQUEST, null );
		}
		
	}

	@Override
	public Response<MovimentoContaReturnDTO> realizarRetirada(Integer numeroConta, BigDecimal valor) {
		
		return null;
	}

}
