package br.com.contabancaria.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.contabancaria.models.MovimentoConta;

@Repository
public interface MovimentoContaRepository extends MongoRepository<MovimentoConta, String> {

}
