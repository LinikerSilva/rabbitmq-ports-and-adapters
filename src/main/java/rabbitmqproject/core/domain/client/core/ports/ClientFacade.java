package rabbitmqproject.core.domain.client.core.ports;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rabbitmqproject.core.domain.client.core.model.Client;
import rabbitmqproject.core.domain.client.infrastructure.ClientDTO;
import rabbitmqproject.core.domain.client.infrastructure.ClientRepository;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientFacade {
  private ModelMapper modelMapper;
  private ClientRepository clientRepository;

  public void create(ClientDTO clientDTO) {
    Client newClient = modelMapper.map(clientDTO, Client.class);
    clientRepository.save(newClient);
    System.out.printf("------>" + newClient.getId());
  }
}