package br.com.contabancaria.services.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
	public Response<MovimentoContaReturnDTO> realizarTransferencia(Integer numeroContaOrigem, BigDecimal valor, Integer numeroContaDestino) {
		
		Conta contaOrigem = contaRepository.findByNumeroConta( numeroContaOrigem );
		
		if( contaOrigem == null ) {
			return new Response<MovimentoContaReturnDTO>( ConstantsUtil.MovimentoConta.MSG_CONTA_ORIGEM_INVALIDA , HttpStatus.PRECONDITION_FAILED, null );
		}
		
		Conta contaDestino = contaRepository.findByNumeroConta( numeroContaDestino );
		
		if( contaDestino == null ) {
			return new Response<MovimentoContaReturnDTO>( ConstantsUtil.MovimentoConta.MSG_CONTA_DESTINO_INVALIDA , HttpStatus.PRECONDITION_FAILED, null );
		}
		
		MovimentoConta movimentoContaOrigem = new MovimentoConta( contaOrigem, valor, TipoTransacao.T, new Date() );
		BigDecimal novoSaldoOrigem = contaOrigem.getSaldo().subtract( movimentoContaOrigem.getValorTransacao() ); 
		contaOrigem.setSaldo( novoSaldoOrigem );
		BigDecimal novoSaldoDestino = contaDestino.getSaldo().add( movimentoContaOrigem.getValorTransacao() );
		contaDestino.setSaldo( novoSaldoDestino	 );
		
		try {
			
			movimentoContaRepository.save( movimentoContaOrigem );
			contaRepository.save( contaOrigem );
			contaRepository.save( contaDestino );
			
			MovimentoContaReturnDTO returnDTO = new MovimentoContaReturnDTO( numeroContaOrigem, novoSaldoOrigem, 
												movimentoContaOrigem.getTipoMovimento(), movimentoContaOrigem.getValorTransacao()  );
			
			return new Response<MovimentoContaReturnDTO>( ConstantsUtil.MovimentoConta.MSG_TRANSFERENCIA_OK, HttpStatus.CREATED, returnDTO );
			
		}catch (Exception e) {
			return new Response<MovimentoContaReturnDTO>( ConstantsUtil.MovimentoConta.MSG_ERROR_DEFAULT , HttpStatus.BAD_REQUEST, null );
		}
		
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
		
		Conta conta = contaRepository.findByNumeroConta( numeroConta );
		
		if( conta == null ) {
			return new Response<MovimentoContaReturnDTO>( ConstantsUtil.MovimentoConta.MSG_CONTA_INVALIDA , HttpStatus.PRECONDITION_FAILED, null );
		}
		//Na retirada cobra 1% no valor retirado
		BigDecimal valorRetirada = valor.add( valor.multiply( new BigDecimal( 0.01 ) ) );
		BigDecimal novoSaldo = conta.getSaldo().subtract( new BigDecimal( valorRetirada.doubleValue() ) );
		
		if( novoSaldo.compareTo( BigDecimal.ZERO ) < 0 ) {
			return new Response<MovimentoContaReturnDTO>( ConstantsUtil.MovimentoConta.MSG_RETIRADA_INVALIDA, HttpStatus.PRECONDITION_FAILED, null );
		}
		
		MovimentoConta movimentoConta = new MovimentoConta( conta, valor, TipoTransacao.R, new Date() );
		conta.setSaldo(novoSaldo);
		try {
			
			movimentoContaRepository.save( movimentoConta );
			contaRepository.save( conta );
			
			MovimentoContaReturnDTO returnDTO = new MovimentoContaReturnDTO( numeroConta, conta.getSaldo(), 
												movimentoConta.getTipoMovimento(), movimentoConta.getValorTransacao()  );
			
			return new Response<MovimentoContaReturnDTO>( ConstantsUtil.MovimentoConta.MSG_RETIRADA_OK, HttpStatus.CREATED, returnDTO );
			
		}catch (Exception e) {
			return new Response<MovimentoContaReturnDTO>( ConstantsUtil.MovimentoConta.MSG_ERROR_DEFAULT , HttpStatus.BAD_REQUEST, null );
		}
		
	}

	@Override
	public List<MovimentoConta> findAll() {
		return movimentoContaRepository.findAll();
	}

}
