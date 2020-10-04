package br.com.contabancaria.dtos;

import br.com.contabancaria.models.Conta;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContaDTO {
	
	private String cpf;
	private String nomeCompleto;
	
	public Conta fromDtoToEntity( ContaDTO dto ) {
		return new Conta( dto.cpf, dto.nomeCompleto );
	}

}
