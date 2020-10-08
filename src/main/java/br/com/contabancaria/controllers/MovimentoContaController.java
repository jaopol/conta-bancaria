package br.com.contabancaria.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.contabancaria.dtos.MovimentoContaDTO;
import br.com.contabancaria.dtos.MovimentoContaReturnDTO;
import br.com.contabancaria.models.MovimentoConta;
import br.com.contabancaria.response.Response;
import br.com.contabancaria.services.MovimentoContaService;
import br.com.contabancaria.utils.ConstantsUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/movimento-conta")
public class MovimentoContaController {
	
	@Autowired
	private MovimentoContaService movimentoContaService;
	
	@ApiOperation( value = "Realizar uma transferência para outra conta" )
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = ConstantsUtil.MovimentoConta.MSG_TRANSFERENCIA_OK ),
		    @ApiResponse(code = 412, message = "Quando não atender aos requisitos mínimos" ),
		    @ApiResponse(code = 400, message = "Quando ocorrer algum problema na transação"),
		})
	@PostMapping( "/transferencia" )
	public ResponseEntity<Response<MovimentoContaReturnDTO>> realizarTransferencia( @RequestBody MovimentoContaDTO movimentoConta ){
		
		Response<MovimentoContaReturnDTO> movimentoContaResponse = movimentoContaService.realizarTransferencia( movimentoConta.getNumeroContaOrigem() , 
													movimentoConta.getValorTransacao(),
													movimentoConta.getNumeroContaDestino() );
		
		return ResponseEntity.status( movimentoContaResponse.getStatus() ).body( movimentoContaResponse );
	}
	
	@ApiOperation( value = "Realizar um deposito na conta" )
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = ConstantsUtil.MovimentoConta.MSG_DEPOSITO_OK ),
		    @ApiResponse(code = 412, message = "Quando não atender aos requisitos mínimos" ),
		    @ApiResponse(code = 400, message = "Quando ocorrer algum problema na transação"),
		})
	@PostMapping( "/deposito" )
	public ResponseEntity<Response<MovimentoContaReturnDTO>> realizarDeposito( @RequestBody MovimentoContaDTO movimentoConta ){
		
		Response<MovimentoContaReturnDTO> movimentoContaResponse = movimentoContaService.realizarDeposito( 
													movimentoConta.getNumeroContaOrigem(), 
													movimentoConta.getValorTransacao() );
		
		return ResponseEntity.status( movimentoContaResponse.getStatus() ).body( movimentoContaResponse );
	}
	
	@ApiOperation( value = "Realizar uma retirada na conta" )
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = ConstantsUtil.MovimentoConta.MSG_DEPOSITO_OK ),
		    @ApiResponse(code = 412, message = "Quando não atender aos requisitos mínimos" ),
		    @ApiResponse(code = 400, message = "Quando ocorrer algum problema na transação"),
		})
	@PostMapping( "/retirada" )
	public ResponseEntity<Response<MovimentoContaReturnDTO>> realizarRetirada( @RequestBody MovimentoContaDTO movimentoConta ){
		
		Response<MovimentoContaReturnDTO> movimentoContaResponse = movimentoContaService.realizarRetirada( 
													movimentoConta.getNumeroContaOrigem(), 
													movimentoConta.getValorTransacao() );
		
		return ResponseEntity.status( movimentoContaResponse.getStatus() ).body( movimentoContaResponse );
	}
	
	@ApiOperation( value = "Recupera os movimentos realizados" )	
	@GetMapping("/historico")
	public ResponseEntity<List<MovimentoContaReturnDTO>> recuperaMovimentos() {
		
		List<MovimentoConta> movimentos = movimentoContaService.findAll();
		List<MovimentoContaReturnDTO> listReturnDTO = movimentos.stream().map( movimento -> new MovimentoContaReturnDTO( movimento ) ).collect( Collectors.toList() );
		
		return ResponseEntity.ok( listReturnDTO ); 
	}

}
