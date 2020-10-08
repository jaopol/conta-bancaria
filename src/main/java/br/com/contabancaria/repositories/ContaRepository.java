package br.com.contabancaria.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.contabancaria.models.Conta;

@Repository
public interface ContaRepository extends MongoRepository<Conta, String> {

	Boolean existsByCpf(String cpf);

	Conta findByNumeroConta(Integer numeroConta);

}
