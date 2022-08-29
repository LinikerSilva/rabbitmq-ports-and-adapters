package projeto.application.adapters.controllers;

import static projeto.config.utils.QueueUtils.CLIENT_QUEUE;
import static projeto.config.utils.QueueUtils.EXCHANGE_NAME;
import static projeto.config.utils.QueueUtils.ORDER_QUEUE;

import java.net.URI;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import projeto.domain.model.dtos.ClientDTO;
import projeto.domain.model.dtos.OrderDTO;
import projeto.domain.ports.service.ClientServicePort;

@RestController
@RequestMapping("/clients")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ClientController {

  private ClientServicePort clientServicePort;

  private RabbitTemplate rabbitTemplate;
  @GetMapping(value = "/{id}")
  public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
    ClientDTO dto = clientServicePort.findById(id);
    return ResponseEntity.ok().body(dto);
  }

  @GetMapping
  public ResponseEntity<Page<ClientDTO>> findAll(
      @RequestParam(value = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
      @RequestParam(value = "direction", defaultValue = "ASC") String direction,
      @RequestParam(value = "orderBy", defaultValue = "id") String orderBy
  ) {
    PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
    Page<ClientDTO> list = clientServicePort.findAllPaged(pageRequest);
    return ResponseEntity.ok().body(list);
  }

  @PostMapping
  public ResponseEntity<ClientDTO> create(@RequestBody ClientDTO clientDTO) {
    clientDTO = clientServicePort.create(clientDTO);
    rabbitTemplate.convertAndSend(EXCHANGE_NAME, CLIENT_QUEUE, clientDTO.getId());
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(clientDTO.getId()).toUri();
    return ResponseEntity.created(uri).body(clientDTO);
  }

//  @PutMapping(value = "/{id}")
//  public ResponseEntity<ClientDTO> update(@PathVariable Long id, @RequestBody ClientDTO dto) {
//    dto = clientServicePort.update(id, dto);
//    return ResponseEntity.ok().body(dto);
//  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    clientServicePort.delete(id);
    return ResponseEntity.noContent().build();
  }
}
