package br.com.contabancaria;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import br.com.contabancaria.dtos.MovimentoContaReturnDTO;
import br.com.contabancaria.enums.TipoTransacao;
import br.com.contabancaria.models.Conta;
import br.com.contabancaria.models.MovimentoConta;
import br.com.contabancaria.repositories.ContaRepository;
import br.com.contabancaria.repositories.MovimentoContaRepository;
import br.com.contabancaria.response.Response;
import br.com.contabancaria.services.impl.MovimentoContaServiceImpl;
import br.com.contabancaria.utils.ConstantsUtil;


@DisplayName( "ContaServiceTest - Testa os métodos de ContaService" )
@SpringBootTest
class MovimentoContaServiceTest {
	
	@Mock
	MovimentoContaRepository movimentoContaRepository; 
	
	@Mock
	ContaRepository contaRepository;
	
	@InjectMocks
	private MovimentoContaServiceImpl movimentoContaService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks( this );
		 
	}
	
	@DisplayName( "Testa método realizarDeposito - Ok" )
	@Test
	void realizarDepositoOk() {
		
		when( contaRepository.findByNumeroConta( any( Integer.class ) ) ).thenReturn( getConta() );

		MovimentoConta movimento = getMovimentoConta();
		movimento.setValorTransacao( new BigDecimal( 50 ) );
		
		when( movimentoContaRepository.save( any( MovimentoConta.class) ) ).thenReturn( movimento );
		
		Response<MovimentoContaReturnDTO> deposito = movimentoContaService.realizarDeposito( 1, movimento.getValorTransacao() );
		
		assertNotNull( deposito.getData() );
		assertEquals( deposito.getData().getTipoTransacao(), TipoTransacao.D );
		assertEquals( deposito.getStatus() , HttpStatus.CREATED);
		assertEquals( deposito.getData().getNovoSaldo(), new BigDecimal( 150.25 ) );

	}
	
	@DisplayName( "Testa método realizarRetirada - Ok" )
	@Test
	void realizarRetiradaOk() {
		
		when( contaRepository.findByNumeroConta( any( Integer.class ) ) ).thenReturn( getConta() );

		MovimentoConta movimento = getMovimentoConta();
		movimento.setValorTransacao( new BigDecimal( 50 ) );
		
		when( movimentoContaRepository.save( any( MovimentoConta.class) ) ).thenReturn( movimento );
		
		Response<MovimentoContaReturnDTO> deposito = movimentoContaService.realizarRetirada( 1, movimento.getValorTransacao() );
		
		assertNotNull( deposito.getData() );
		assertEquals( deposito.getData().getTipoTransacao(), TipoTransacao.R );
		assertEquals( deposito.getStatus() , HttpStatus.CREATED);
		assertEquals( deposito.getData().getNovoSaldo(), new BigDecimal( 49.5 ) );

	}
	
	@DisplayName( "Testa método realizarRetirada - saldo negativo" )
	@Test
	void realizarRetiradaSaldoNegativo() {
		
		Conta conta = getConta();
		conta.setSaldo( new BigDecimal( 50.0 ) );
		
		when( contaRepository.findByNumeroConta( any( Integer.class ) ) ).thenReturn( conta );

		MovimentoConta movimento = getMovimentoConta();
		movimento.setValorTransacao( new BigDecimal( 50 ) );
		
		when( movimentoContaRepository.save( any( MovimentoConta.class) ) ).thenReturn( movimento );
		
		Response<MovimentoContaReturnDTO> deposito = movimentoContaService.realizarRetirada( 1, movimento.getValorTransacao() );
		
		assertNull( deposito.getData() );
		assertEquals( deposito.getStatus() , HttpStatus.PRECONDITION_FAILED);
		assertEquals( deposito.getMensagens().get(0), ConstantsUtil.MovimentoConta.MSG_RETIRADA_INVALIDA );

	}

	@DisplayName( "Testa método realizarRetirada - conta inválida" )
	@Test
	void realizarRetiradaContaInvalida() {
		
		when( contaRepository.findByNumeroConta( any( Integer.class ) ) ).thenReturn( null );

		
		Response<MovimentoContaReturnDTO> deposito = movimentoContaService.realizarRetirada( 1, new BigDecimal(50.0) );
		
		assertNull( deposito.getData() );
		assertEquals( deposito.getStatus() , HttpStatus.PRECONDITION_FAILED);
		assertEquals( deposito.getMensagens().get(0), ConstantsUtil.MovimentoConta.MSG_CONTA_INVALIDA );

	}
	
	@DisplayName( "Testa método realizarDeposito - conta inválida" )
	@Test
	void realizarDepositoContaInvalida() {
		
		when( contaRepository.findByNumeroConta( any( Integer.class ) ) ).thenReturn( null );
	
		Response<MovimentoContaReturnDTO> deposito = movimentoContaService.realizarDeposito( 1, new BigDecimal(50.0) );
		
		assertNull( deposito.getData() );
		assertEquals( deposito.getStatus() , HttpStatus.PRECONDITION_FAILED);
		assertEquals( deposito.getMensagens().get(0), ConstantsUtil.MovimentoConta.MSG_CONTA_INVALIDA );

	}

	
	private MovimentoConta getMovimentoConta() {
		MovimentoConta movimentoConta = new MovimentoConta();
		
		movimentoConta.setDataTransacao( new Date() );
		movimentoConta.setId( "1" );
		movimentoConta.setConta( getConta() );
		
		return movimentoConta;
	}
	
	private Conta getConta() {

		Conta conta = new Conta();
		conta.setId( "1" );
		conta.setNumeroConta( 1 );
		conta.setSaldo( new BigDecimal(100) );
		return conta;
	}

}
