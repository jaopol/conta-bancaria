package br.com.contabancaria;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.contabancaria.dtos.ContaDTO;
import br.com.contabancaria.models.Conta;
import br.com.contabancaria.repositories.ContaRepository;
import br.com.contabancaria.response.Response;
import br.com.contabancaria.services.impl.ContaServiceImpl;

@DisplayName( "ContaServiceTest - Testa os métodos de ContaService" )
@SpringBootTest
class ContaServiceTest {
	
	@Mock
	ContaRepository contaRepository; 
	
	@InjectMocks
	private ContaServiceImpl contaService;

	@InjectMocks
	private ContaDTO contaDTO;

	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks( this );
		when( contaRepository.save( any( Conta.class ) ) ).thenReturn( getContaSave() );
	}
	
	@DisplayName( "Testa método abrirConta - Ok" )
	@Test
	void abrirContaOk() {
		ContaDTO dto = getContaDTO();
		Response<Conta> contaOk = contaService.abrirConta( contaDTO.fromDtoToEntity( dto ) );
		assertNotNull( contaOk.getData() );
		assertEquals( contaOk.getData().getCpf(), dto.getCpf() );

	}
	
	@DisplayName( "Testa método abrirConta - CPF já cadastrado" )
	@Test
	void abrirContaCpfCadastrado() {
		when( contaRepository.existsByCpf( any( String.class ) ) ).thenReturn( true );
		
		ContaDTO dto = getContaDTO();
		Response<Conta> result = contaService.abrirConta( contaDTO.fromDtoToEntity( dto ) );
		assertNull( result.getData() );
		assertEquals( result.getErros().get(0), "Conta já Cadastrada" );

	}
	
	private ContaDTO getContaDTO() {

		return ContaDTO.builder()
				.cpf("11111111111")
				.nomeCompleto("Teste Silva")
				.build();				
	}
	
	private Conta getContaSave() {
		
		Conta conta = contaDTO.fromDtoToEntity( getContaDTO() );
		conta.setId("1");
		
		return conta;
	}

}
