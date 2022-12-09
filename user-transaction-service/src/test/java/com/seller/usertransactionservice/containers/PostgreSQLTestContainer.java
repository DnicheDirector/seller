package com.seller.usertransactionservice.containers;


import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreSQLTestContainer extends PostgreSQLContainer<PostgreSQLTestContainer> {
  private static final String POSTGRESQL_IMAGE = "postgres:15";

  private static PostgreSQLTestContainer container;

  private PostgreSQLTestContainer() {
    super(POSTGRESQL_IMAGE);
  }

  public static PostgreSQLTestContainer getInstance() {
    if (container == null) {
      container = new PostgreSQLTestContainer();
    }
    return container;
  }

  public void addTestContainerProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", container::getJdbcUrl);
    registry.add("spring.datasource.username", container::getUsername);
    registry.add("spring.datasource.password", container::getPassword);
    registry.add("spring.datasource.driver-class-name", container::getDriverClassName);
  }

  @Override
  public void stop() { }

}
