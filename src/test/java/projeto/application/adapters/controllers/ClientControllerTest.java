package projeto.application.adapters.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import projeto.domain.model.dtos.ClientDTO;
import projeto.tests.Factory;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ClientControllerTest {

  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  private Long existingId;
  private Long nonExistingId;
  private Long countTotalClients;
  private ClientDTO clientDTO;

  @BeforeEach
  void setUp() throws Exception {
    existingId = 1L;
    nonExistingId = 1000L;
    countTotalClients = 2L;
    clientDTO = Factory.createClientDTO();
  }

  @Test
  void findAllShouldReturnSortedPageWhenSortByName() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/clients?page=0&size=12&sort=name,asc")
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.totalElements").value(countTotalClients));
    result.andExpect(jsonPath("$.content").exists());
    result.andExpect(jsonPath("$.content[0].name").value("Rodrigo de Souza Paula"));
    result.andExpect(jsonPath("$.content[1].name").value("João Freitas da Silva"));
  }

  @Test
  void deleteShouldReturnNoContentWhenIdExists() throws Exception {
    ResultActions result =
        mockMvc.perform(delete("/clients/{id}", existingId)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNoContent());
  }

  @Test
  void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
    ResultActions result =
        mockMvc.perform(delete("/clients/{id}", nonExistingId)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }

  @Test
  void insertShouldReturnClientDTOCreated() throws Exception {
    String jsonBody = objectMapper.writeValueAsString(clientDTO);

    ResultActions result =
        mockMvc.perform(post("/clients")
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isCreated());
    result.andExpect(jsonPath("$.id").exists());
    result.andExpect(jsonPath("$.name").exists());
    result.andExpect(jsonPath("$.orders").exists());
  }

  @Test
  void findAllShouldReturnPage() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/clients")
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
  }

  @Test
  void findByIdShouldReturnClientWhenIdExists() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/clients/{id}", existingId)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.id").exists());
    result.andExpect(jsonPath("$.name").exists());
    result.andExpect(jsonPath("$.orders").exists());
  }

  @Test
  void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/clients/{id}", nonExistingId)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }
}