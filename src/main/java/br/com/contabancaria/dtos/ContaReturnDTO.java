package br.com.contabancaria.dtos;

import java.util.Date;

import br.com.contabancaria.models.Conta;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaReturnDTO {

	@ApiModelProperty(value = "Número da conta gerado pela API")
	private String numeroConta;
	
	@ApiModelProperty(value = "Nome completo da pessoa")
	private String nomeCompleto;
	
	@ApiModelProperty(value = "CPF válido somente número")
	private String cpf;
	
	@ApiModelProperty(value = "Saldo da conta")
	private String saldo;
	
	@ApiModelProperty(value = "Data de criação da conta")
	private Date dataCriacao;

	public ContaReturnDTO(Conta conta) {
		this.numeroConta = conta.getNumeroConta().toString();
		this.nomeCompleto = conta.getNomeCompleto();
		this.cpf = conta.getCpfSemMascara();
		this.saldo = saldo != null ? saldo.toString() : "0";
		this.dataCriacao = conta.getDataCriacao();
	}
	
}
