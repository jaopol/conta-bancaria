package br.com.contabancaria;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.contabancaria.dtos.ContaDTO;
import br.com.contabancaria.models.Conta;
import br.com.contabancaria.repositories.ContaRepository;
import br.com.contabancaria.response.Response;
import br.com.contabancaria.services.impl.ContaServiceImpl;

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
		Mockito.mock( ContaRepository.class );//.save( contaDTO.fromDtoToEntity( getContaDTO() ) );
		//when( contaRepository.save( any( Conta.class ) ) ).thenReturn( getContaSave() );
	}
	

	@Test
	void abrirConta() {
		ContaDTO dto = getContaDTO();
		Response<Conta> contaOk = contaService.abrirConta( contaDTO.fromDtoToEntity( dto ) );
		assertNotNull( contaOk.getData() );
		assertEquals( contaOk.getData().getCpf(), dto.getCpf() );

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
