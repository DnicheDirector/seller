package com.company.seller;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ContainerTest extends BaseTest {

  @Test
  void testContainerConnection() {
    assertTrue(postgreSQLContainer.isRunning());
  }
}
