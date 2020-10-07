package br.com.contabancaria.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.contabancaria.dtos.MovimentoContaDTO;
import br.com.contabancaria.dtos.MovimentoContaReturnDTO;
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
	@PostMapping( "/realizarTransferencia" )
	public ResponseEntity<Response<MovimentoContaReturnDTO>> realizarTransferencia( @RequestBody MovimentoContaDTO movimentoConta ){
		
		Response<MovimentoContaReturnDTO> movimentoContaResponse = movimentoContaService.realizarTransferencia( movimentoConta.getNumeroContaOrigem() , 
													movimentoConta.getValorTransacao(),
													movimentoConta.getNumeroContaDestino() );
		
		return ResponseEntity.status( movimentoContaResponse.getStatus() ).body( movimentoContaResponse );
	}

}
