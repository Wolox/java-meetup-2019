package ar.com.wolox.javameetup2019.repository;


import ar.com.wolox.javameetup2019.model.Client;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {

	List<Client> findByName(String name);

	Client findOneByLastName(String lastName);

	Client findOneByDocumentNumber(String documentNumber);
}
