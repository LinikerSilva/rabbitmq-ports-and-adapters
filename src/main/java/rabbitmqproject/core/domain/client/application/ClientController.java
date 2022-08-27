package rabbitmqproject.core.domain.client.application;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rabbitmqproject.core.domain.client.core.ports.ClientFacade;
import rabbitmqproject.core.domain.client.infrastructure.ClientDTO;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientController {

  private ClientFacade clientFacade;

  @PostMapping
  public ResponseEntity create(@RequestBody ClientDTO clientDTO) {
    clientFacade.create(clientDTO);
    return new ResponseEntity(HttpStatus.CREATED);
  }
}