package ar.com.wolox.javameetup2019.repository;


import ar.com.wolox.javameetup2019.model.Client;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

	Optional<List<Client>> findByName(String name);

	Optional<Client> findOneByDocumentNumber(String documentNumber);

}
