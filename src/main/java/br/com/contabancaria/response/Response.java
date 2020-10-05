/**
 * 
 */
package br.com.contabancaria.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class Response<T> {

	@ApiModelProperty(value = "Retorna o objeto")
	private T data;

	@ApiModelProperty(value = "Mensagens informativas e de erros")
	private List<String> mensagens;
	
	@ApiModelProperty(value = "Status da requisição")
	private HttpStatus status;

	public Response() {

	}

	public List<String> getMensagens() {
		if (this.mensagens == null) {
			this.mensagens = new ArrayList<String>();
		}

		return this.mensagens;
	}
	
	public Response(String msg, HttpStatus status, T data) {
		this.setData( data );
		this.setMensagens( Arrays.asList( msg ) );
		this.setStatus( status );
	}

}
