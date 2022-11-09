package com.company.seller;

import com.company.seller.containers.PostgreSQLTestContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class BaseTest {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;

  @Container
  public static PostgreSQLTestContainer postgreSQLContainer = PostgreSQLTestContainer.getInstance();

  @DynamicPropertySource
  public static void overrideProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    registry.add("spring.datasource.driver-class-name", postgreSQLContainer::getDriverClassName);
  }

  protected ResultActions get(String path) throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders
        .get(path)
        .accept(MediaType.APPLICATION_JSON)
    );
  }

  protected ResultActions put(String path, Object toSend) throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders
        .put(path)
        .content(objectMapper.writeValueAsString(toSend))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    );
  }

  protected ResultActions post(String path, Object toSend) throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders
        .post(path)
        .content(objectMapper.writeValueAsString(toSend))
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
    );
  }

  protected ResultActions delete(String path) throws Exception {
    return mockMvc.perform(MockMvcRequestBuilders
        .delete(path)
        .accept(MediaType.APPLICATION_JSON)
    );
  }

  protected <T> T getBody(MockHttpServletResponse response, Class<T> classType) throws Exception {
    return objectMapper.readValue(response.getContentAsString(), classType);
  }

  protected <ID> String getPath(String path, ID id) {
    return String.format("%s/%s", path, id);
  }

}
