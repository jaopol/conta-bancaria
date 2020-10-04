package br.com.contabancaria.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.contabancaria.models.MovimentoConta;

public interface MovimentoContaRepository extends MongoRepository<MovimentoConta, String> {

}
