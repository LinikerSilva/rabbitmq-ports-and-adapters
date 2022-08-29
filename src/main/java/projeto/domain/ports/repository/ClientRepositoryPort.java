package projeto.domain.ports.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projeto.adapters.spring.entities.ClientEntity;

public interface ClientRepositoryPort {

  Optional<ClientEntity> findById(Long id);
  Page<ClientEntity> findAll(Pageable pageable);
  void save(ClientEntity client);
  ClientEntity getReferenceById(Long id);
  void deleteById(Long id);
}
