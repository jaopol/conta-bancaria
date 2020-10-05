package br.com.contabancaria.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.contabancaria.models.Conta;


public interface ContaRepository extends MongoRepository<Conta, String> {

	Boolean existsByCpf(String cpf);

	Conta findByNumeroConta(Integer numeroConta);

}
