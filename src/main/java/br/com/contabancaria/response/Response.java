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

	private List<String> erros;
	
	private HttpStatus status;

	public Response() {

	}

	public List<String> getErros() {
		if (this.erros == null) {
			this.erros = new ArrayList<String>();
		}

		return this.erros;
	}

}
