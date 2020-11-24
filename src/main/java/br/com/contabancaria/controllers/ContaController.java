package br.com.contabancaria.controllers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.contabancaria.dtos.ContaDTO;
import br.com.contabancaria.dtos.ContaReturnDTO;
import br.com.contabancaria.models.Conta;
import br.com.contabancaria.response.Response;
import br.com.contabancaria.services.ContaService;
import br.com.contabancaria.utils.ConstantsUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController()
@RequestMapping("/conta")
public class ContaController {
	
	@Autowired
	private ContaService contaService;
	
	@ApiOperation( value = "Verifica disponibilidade da API" )	
	@GetMapping("/check")
	public String check() {
		return "API conta bancaria ok! " + new Date(); 
	}
	
	@ApiOperation( value = "Cadastrar uma nova conta" )
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = ConstantsUtil.Conta.MSG_CADASTRO_SUCESSO ),
		    @ApiResponse(code = 412, message = "Quando não atender aos requisitos mínimos" ),
		    @ApiResponse(code = 400, message = "Quando ocorrer algum problema na abertura da conta"),
		})
	@PostMapping( )
	public ResponseEntity<Response<Conta>> abrirConta( @RequestBody ContaDTO contaDTO ){
		
		Response<Conta> response = contaService.abrirConta( contaDTO.fromDtoToEntity() );
		
		return ResponseEntity.status( response.getStatus() ).body( response );
	}
	
	@ApiOperation( value = "Recupera as contas cadastradas" )	
	@GetMapping()
	public ResponseEntity<List<ContaReturnDTO>> recuperaContas() {
		
		List<Conta> contas = contaService.findAll();
		List<ContaReturnDTO> listReturnDTO = contas.stream().map( conta -> new ContaReturnDTO( conta ) ).collect( Collectors.toList() );
		
		return ResponseEntity.ok( listReturnDTO ); 
	}

}
