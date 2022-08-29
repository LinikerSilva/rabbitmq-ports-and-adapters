package projeto.application.adapters.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.util.Date;

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
import projeto.domain.model.dtos.OrderDTO;
import projeto.tests.Factory;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
class OrderControllerTest {

  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  private Long existingId;
  private Long nonExistingId;
  private Long countTotalOrders;
  private OrderDTO orderDTO;

  @BeforeEach
  void setUp() throws Exception {
    existingId = 1L;
    nonExistingId = 1000L;
    countTotalOrders = 2L;
    orderDTO = Factory.createOrderDTO();
  }

  @Test
  void findAllShouldReturnSortedPageWhenSortByName() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/orders?page=0&size=12&sort=name,asc")
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.totalElements").value(countTotalOrders));
    result.andExpect(jsonPath("$.content").exists());
    result.andExpect(jsonPath("$.content[0].id").value(existingId));
    result.andExpect(jsonPath("$.content[0].client_id").value(1));
    result.andExpect(jsonPath("$.content[0].created_at").value(Date.from(Instant.now())));
    result.andExpect(jsonPath("$.content[0].total").value(325.0));
    result.andExpect(jsonPath("$.content[0].status").value("DONE"));
    result.andExpect(jsonPath("$.content[0].products").exists());
  }

  @Test
  void deleteShouldReturnNoContentWhenIdExists() throws Exception {
    ResultActions result =
        mockMvc.perform(delete("/orders/{id}", existingId)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNoContent());
  }

  @Test
  void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
    ResultActions result =
        mockMvc.perform(delete("/orders/{id}", nonExistingId)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }

  @Test
  void insertShouldReturnOrderDTOCreated() throws Exception {
    String jsonBody = objectMapper.writeValueAsString(orderDTO);

    ResultActions result =
        mockMvc.perform(post("/orders")
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isCreated());
    result.andExpect(jsonPath("$.content[0].id").exists());
    result.andExpect(jsonPath("$.content[0].client_id").exists());
    result.andExpect(jsonPath("$.content[0].created_at").exists());
    result.andExpect(jsonPath("$.content[0].total").exists());
    result.andExpect(jsonPath("$.content[0].status").exists());
    result.andExpect(jsonPath("$.content[0].products").exists());
  }

  @Test
  void findAllShouldReturnPage() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/orders")
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
  }

  @Test
    void findByIdShouldReturnOrderWhenIdExists() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/orders/{id}", existingId)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.content[0].id").exists());
    result.andExpect(jsonPath("$.content[0].client_id").exists());
    result.andExpect(jsonPath("$.content[0].created_at").exists());
    result.andExpect(jsonPath("$.content[0].total").exists());
    result.andExpect(jsonPath("$.content[0].status").exists());
    result.andExpect(jsonPath("$.content[0].products").exists());
  }

  @Test
  void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/orders/{id}", nonExistingId)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }
}