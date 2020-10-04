/**
 * 
 */
package br.com.contabancaria.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;


@Data
public class Response<T> {

	private T data;

	private List<String> mensagens;
	
	private HttpStatus status;

	public Response() {

	}

	public List<String> getErros() {
		if (this.mensagens == null) {
			this.mensagens = new ArrayList<String>();
		}

		return this.mensagens;
	}

}
