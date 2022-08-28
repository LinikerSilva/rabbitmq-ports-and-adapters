package projeto.adapters.spring.repositories;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import projeto.adapters.spring.entities.ClientEntity;
import projeto.domain.ports.repository.ClientRepositoryPort;

@Component
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientRepository implements ClientRepositoryPort {

  private final ClientSpringRepository clientSpringRepository;

  @Override
  public void save(ClientEntity client) {
    clientSpringRepository.save(client);
  }
}
