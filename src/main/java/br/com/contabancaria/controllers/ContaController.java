package br.com.contabancaria.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.contabancaria.dtos.ContaDTO;
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
		return "API conta ok!"; 
	}
	
	@ApiOperation( value = "Cadastrar uma nova conta" )
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = ConstantsUtil.conta.MSG_CADASTRO_SUCESSO ),
		    @ApiResponse(code = 412, message = ConstantsUtil.conta.MSG_CPF_INVALIDO ),
		    @ApiResponse(code = 412, message = ConstantsUtil.conta.MSG_CPF_NOME_OBRIGATORIO ),
		    @ApiResponse(code = 412, message = ConstantsUtil.conta.MSG_CPF_REPETIDO ),
		    @ApiResponse(code = 400, message = ConstantsUtil.conta.MSG_ERROR_DEFAULT),
		})
	@PostMapping( "/abrirConta" )
	public ResponseEntity<Response<Conta>> abrirConta( @RequestBody ContaDTO contaDTO ){
		
		Response<Conta> response = contaService.abrirConta( contaDTO.fromDtoToEntity() );
		
		return ResponseEntity.ok( response );
	}

}
