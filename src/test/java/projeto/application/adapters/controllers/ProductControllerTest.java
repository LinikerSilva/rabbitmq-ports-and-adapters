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
import projeto.domain.model.dtos.OrderDTO;
import projeto.domain.model.dtos.ProductDTO;
import projeto.tests.Factory;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AllArgsConstructor(onConstructor = @__(@Autowired))
class ProductControllerTest {

  private MockMvc mockMvc;

  private ObjectMapper objectMapper;

  private Long existingId;
  private Long nonExistingId;
  private Long countTotalOrders;
  private ProductDTO productDTO;

  @BeforeEach
  void setUp() throws Exception {
    existingId = 1L;
    nonExistingId = 1000L;
    countTotalOrders = 2L;
    productDTO = Factory.createProductDTO();
  }

  @Test
  void findAllShouldReturnSortedPageWhenSortByName() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/products?page=0&size=12&sort=name,asc")
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.totalElements").value(countTotalOrders));
    result.andExpect(jsonPath("$.content").exists());
    result.andExpect(jsonPath("$.content[0].id").value(existingId));
    result.andExpect(jsonPath("$.content[0].name").value("Estojo"));
    result.andExpect(jsonPath("$.content[0].value").value(25.0));
  }

  @Test
  void deleteShouldReturnNoContentWhenIdExists() throws Exception {
    ResultActions result =
        mockMvc.perform(delete("/products/{id}", existingId)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNoContent());
  }

  @Test
  void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
    ResultActions result =
        mockMvc.perform(delete("/products/{id}", nonExistingId)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }

  @Test
  void insertShouldReturnProductDTOCreated() throws Exception {
    String jsonBody = objectMapper.writeValueAsString(productDTO);

    ResultActions result =
        mockMvc.perform(post("/products")
            .content(jsonBody)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isCreated());
    result.andExpect(jsonPath("$.content[0].id").exists());
    result.andExpect(jsonPath("$.content[0].name").exists());
    result.andExpect(jsonPath("$.content[0].value").exists());
  }

  @Test
  void findAllShouldReturnPage() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/products")
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
  }

  @Test
  void findByIdShouldReturnProductWhenIdExists() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/products/{id}", existingId)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isOk());
    result.andExpect(jsonPath("$.content[0].id").exists());
    result.andExpect(jsonPath("$.content[0].name").exists());
    result.andExpect(jsonPath("$.content[0].value").exists());
  }

  @Test
  void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
    ResultActions result =
        mockMvc.perform(get("/products/{id}", nonExistingId)
            .accept(MediaType.APPLICATION_JSON));

    result.andExpect(status().isNotFound());
  }
}