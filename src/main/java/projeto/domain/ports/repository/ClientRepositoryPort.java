package projeto.domain.ports.repository;

import projeto.adapters.spring.entities.ClientEntity;

public interface ClientRepositoryPort {

  void save(ClientEntity user);
}
