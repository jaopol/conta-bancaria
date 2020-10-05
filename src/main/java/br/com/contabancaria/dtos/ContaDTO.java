package br.com.contabancaria.dtos;

import br.com.contabancaria.models.Conta;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContaDTO {
	
	@ApiModelProperty(value = "CPF válido somente número")
	private String cpf;
	
	@ApiModelProperty(value = "Nome completo")
	private String nomeCompleto;
	
	public Conta fromDtoToEntity() {
		return new Conta( this.cpf, this.nomeCompleto );
	}

}
