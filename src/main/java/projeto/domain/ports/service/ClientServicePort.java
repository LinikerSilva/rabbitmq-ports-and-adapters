package projeto.domain.ports.service;

import projeto.domain.model.dtos.ClientDTO;

public interface ClientServicePort {

  ClientDTO create(ClientDTO clientDTO);
}
