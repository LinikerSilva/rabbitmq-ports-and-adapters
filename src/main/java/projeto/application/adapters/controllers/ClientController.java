package projeto.application.adapters.controllers;

import java.net.URI;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import projeto.domain.model.dtos.ClientDTO;
import projeto.domain.ports.service.ClientServicePort;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientController {

  private ClientServicePort clientServicePort;

  @PostMapping
  public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO clientDTO) {
    clientDTO = clientServicePort.create(clientDTO);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(clientDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(clientDTO);
  }
}
