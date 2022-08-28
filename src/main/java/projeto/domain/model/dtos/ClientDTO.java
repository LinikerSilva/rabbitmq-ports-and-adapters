package projeto.domain.model.dtos;

import java.io.Serializable;

import projeto.adapters.spring.entities.ClientEntity;

public class ClientDTO implements Serializable {

  private Long id;

  private String name;

  public ClientDTO() {
  }

  public ClientDTO(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public ClientDTO(ClientEntity client) {
    this.id = client.getId();
    this.name = client.getName();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
