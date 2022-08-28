package projeto.domain.adapters;

import org.modelmapper.ModelMapper;
import projeto.adapters.spring.entities.ClientEntity;
import projeto.domain.model.dtos.ClientDTO;
import projeto.domain.ports.repository.ClientRepositoryPort;
import projeto.domain.ports.service.ClientServicePort;

public class ClientServiceAdapter implements ClientServicePort {

  private final ClientRepositoryPort clientRepositoryPort;
  private final ModelMapper modelMapper = new ModelMapper();

  public ClientServiceAdapter(ClientRepositoryPort clientRepositoryPort) {
    this.clientRepositoryPort = clientRepositoryPort;
  }

  @Override
  public ClientDTO create(ClientDTO clientDTO) {
    ClientEntity newClient = modelMapper.map(clientDTO, ClientEntity.class);
    clientRepositoryPort.save(newClient);
    System.out.printf("------>" + newClient.getId());
    return new ClientDTO(newClient);
  }
}
