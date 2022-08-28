package projeto.domain.adapters;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import projeto.adapters.spring.entities.ClientEntity;
import projeto.domain.adapters.exceptions.DatabaseException;
import projeto.domain.adapters.exceptions.ResourceNotFoundException;
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
  public ClientDTO findById(Long id) {
    Optional<ClientEntity> obj = clientRepositoryPort.findById(id);
    ClientEntity entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    return new ClientDTO(entity, entity.getOrders());
  }

  @Override
  public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
    Page<ClientEntity> list = clientRepositoryPort.findAll(pageRequest);
    return list.map(x -> new ClientDTO(x, x.getOrders()));
  }

  @Override
  public ClientDTO create(ClientDTO clientDTO) {
    ClientEntity newClient = modelMapper.map(clientDTO, ClientEntity.class);
    newClient.calculateOrdersValues();
    clientRepositoryPort.save(newClient);
    System.out.printf("------>" + newClient.getId());
    return new ClientDTO(newClient, newClient.getOrders());
  }

//  @Override
//  public ClientDTO update(Long id, ClientDTO clientDTO) {
//    try {
//      ClientEntity client = clientRepositoryPort.getReferenceById(id);
//      client = modelMapper.map(clientDTO, ClientEntity.class);
//      clientRepositoryPort.save(client);
//      return new ClientDTO(client, client.getOrders());
//    }
//    catch (EntityNotFoundException e) {
//      throw new ResourceNotFoundException("Id not found " + id);
//    }
//  }

  @Override
  public void delete(Long id) {
    try {
      clientRepositoryPort.deleteById(id);
    }
    catch (EmptyResultDataAccessException e) {
      throw new ResourceNotFoundException("Id not found " + id);
    }
    catch (DataIntegrityViolationException e) {
      throw new DatabaseException("Integrity violation");
    }
  }
}
