package projeto.domain.ports.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import projeto.domain.model.dtos.ClientDTO;

public interface ClientServicePort {

  ClientDTO findById(Long id);

  Page<ClientDTO> findAllPaged(Pageable pageable);

  ClientDTO create(ClientDTO clientDTO);

//  ClientDTO update(Long id, ClientDTO clientDTO);

  void delete(Long id);
}
