package projeto.adapters.spring.repositories;

import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import projeto.adapters.spring.entities.ClientEntity;
import projeto.domain.ports.repository.ClientRepositoryPort;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientRepository implements ClientRepositoryPort {

  private final ClientSpringRepository clientSpringRepository;

  @Override
  public Optional<ClientEntity> findById(Long id) {
    return clientSpringRepository.findById(id);
  }

  @Override
  public Page<ClientEntity> findAll(Pageable pageable) {
    return clientSpringRepository.findAll(pageable);
  }

  @Override
  public void save(ClientEntity client) {
    clientSpringRepository.save(client);
  }

  @Override
  public ClientEntity getReferenceById(Long id) {
    return clientSpringRepository.getReferenceById(id);
  }

  @Override
  public void deleteById(Long id) {
    clientSpringRepository.deleteById(id);
  }
}
