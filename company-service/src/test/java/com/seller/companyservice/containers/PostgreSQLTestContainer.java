package com.seller.companyservice.containers;


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

  @Override
  public void stop() {
  }
}
