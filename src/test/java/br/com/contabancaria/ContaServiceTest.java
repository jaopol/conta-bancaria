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
import org.springframework.http.HttpStatus;

import br.com.contabancaria.dtos.ContaDTO;
import br.com.contabancaria.models.Conta;
import br.com.contabancaria.repositories.ContaRepository;
import br.com.contabancaria.response.Response;
import br.com.contabancaria.services.impl.ContaServiceImpl;
import br.com.contabancaria.utils.ConstantsUtil;

@DisplayName( "ContaServiceTest - Testa os métodos de ContaService" )
@SpringBootTest
class ContaServiceTest {
	
	@Mock
	ContaRepository contaRepository; 
	
	@InjectMocks
	private ContaServiceImpl contaService;
	
	@BeforeEach
	void init() {
		MockitoAnnotations.initMocks( this );
		when( contaRepository.save( any( Conta.class ) ) ).thenReturn( getContaSave() );
	}
	
	@DisplayName( "Testa método abrirConta - Ok" )
	@Test
	void abrirContaOk() {
		ContaDTO dto = getContaDTO();
		Response<Conta> contaOk = contaService.abrirConta( dto.fromDtoToEntity() );
		assertNotNull( contaOk.getData() );
		assertEquals( contaOk.getData().getCpf(), dto.getCpf() );
		assertEquals( contaOk.getStatus() , HttpStatus.CREATED);

	}
	
	@DisplayName( "Testa método abrirConta - CPF já cadastrado" )
	@Test
	void abrirContaCpfCadastrado() {
		when( contaRepository.existsByCpf( any( String.class ) ) ).thenReturn( true );
		
		ContaDTO dto = getContaDTO();
		Response<Conta> result = contaService.abrirConta( dto.fromDtoToEntity() );
		assertNull( result.getData() );
		assertEquals( result.getMensagens().get(0), ConstantsUtil.conta.MSG_CPF_REPETIDO  );

	}
	
	@DisplayName( "Testa método abrirConta - CPF não informado" )
	@Test
	void abrirContaCpfNaoInformado() {
		
		ContaDTO dto = getContaDTO();
		dto.setCpf( null );
		Response<Conta> result = contaService.abrirConta( dto.fromDtoToEntity() );
		assertNull( result.getData() );
		assertEquals( result.getMensagens().get(0), ConstantsUtil.conta.MSG_CPF_NOME_OBRIGATORIO );

	}
	
	@DisplayName( "Testa método abrirConta - Nome não informado" )
	@Test
	void abrirContaNomeNaoInformado() {
		
		ContaDTO dto = getContaDTO();
		dto.setNomeCompleto("");
		Response<Conta> result = contaService.abrirConta( dto.fromDtoToEntity() );
		assertNull( result.getData() );
		assertEquals( result.getMensagens().get(0), ConstantsUtil.conta.MSG_CPF_NOME_OBRIGATORIO );

	}
	
	@DisplayName( "Testa método abrirConta - CPF inválido" )
	@Test
	void abrirContaCpfInvalido() {
		
		ContaDTO dto = getContaDTO();
		dto.setCpf( "12345678910" );
		Response<Conta> result = contaService.abrirConta( dto.fromDtoToEntity() );
		assertNull( result.getData() );
		assertEquals( result.getMensagens().get(0), ConstantsUtil.conta.MSG_CPF_INVALIDO );

	}
	
	private ContaDTO getContaDTO() {

		return ContaDTO.builder()
				.cpf("73499896010")
				.nomeCompleto("Teste Silva")
				.build();				
	}
	
	private Conta getContaSave() {
		
		Conta conta = getContaDTO().fromDtoToEntity();
		conta.setId("1");
		
		return conta;
	}

}
