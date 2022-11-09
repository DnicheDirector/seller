package com.company.seller.position.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.company.seller.BaseTest;
import com.company.seller.position.views.PositionInputViewModel;
import com.company.seller.position.views.PositionOutputViewModel;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql(scripts = "/position/create-positions-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/clean-test-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class PositionsControllerTest extends BaseTest {

  private static final String BASE_PATH = "/positions";

  private static final Long COMPANY_ID = 100L;
  private static final UUID USER_ID = UUID.fromString("fd422f25-7e5e-4b90-9d5f-cc835aa0900f");

  private static final Long[] POSITION_IDS = { 10L, 11L };

  private static final UUID[] ITEM_IDS = {
      UUID.fromString("b812e9a3-9c8f-46cd-ae79-cabecded7852"),
      UUID.fromString("fd422f25-7e5e-4b90-9d5f-cc835aa0900f")
  };

  @Test
  public void createPositionIfValidData() throws Exception {
    var dto = PositionInputViewModel.builder()
        .createdById(USER_ID)
        .amount(BigDecimal.valueOf(500))
        .created(LocalDateTime.now())
        .companyId(COMPANY_ID)
        .itemId(ITEM_IDS[0])
        .created(LocalDateTime.now())
        .build();

    var response = post(BASE_PATH, dto)
        .andExpect(status().isCreated())
        .andReturn().getResponse();
    var body = getBody(response, PositionOutputViewModel.class);

    assertNotNull(body.getId());
    assertEquals(dto.getItemId(), body.getItemId());
    assertEquals(dto.getCreatedById(), body.getCreatedById());
    assertEquals(dto.getCompanyId(), body.getCompanyId());
    assertEquals(dto.getCreated(), body.getCreated());
    assertEquals(dto.getAmount(), body.getAmount());
  }

  @Test
  public void createPositionReturnsBadRequestIfInvalidData() throws Exception {
    var dto = PositionInputViewModel.builder()
        .createdById(USER_ID)
        .build();

    post(BASE_PATH, dto)
        .andExpect(status().isBadRequest());
  }

  @Test
  public void getPositionIfExists() throws Exception {
    var id = POSITION_IDS[0];
    var response = get(getPath(BASE_PATH, id))
        .andExpect(status().isOk())
        .andReturn().getResponse();

    var body = getBody(response, PositionOutputViewModel.class);

    assertEquals(id, body.getId());
  }

  @Test
  public void getPositionReturnsNotFoundIfDoesntExist() throws Exception {
    get(getPath(BASE_PATH, 1000L))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getAllPositions() throws Exception {
    getAll(2);
  }

  @Test
  public void successUpdatePosition() throws Exception {
    var dto = PositionInputViewModel.builder()
        .createdById(USER_ID)
        .amount(BigDecimal.valueOf(1000))
        .created(LocalDateTime.now())
        .companyId(COMPANY_ID)
        .itemId(ITEM_IDS[1])
        .created(LocalDateTime.now())
        .build();

    var id = POSITION_IDS[0];

    var response = put(getPath(BASE_PATH, id), dto)
        .andExpect(status().isOk())
        .andReturn().getResponse();
    var body = getBody(response, PositionOutputViewModel.class);

    assertEquals(id, body.getId());
    assertEquals(dto.getItemId(), body.getItemId());
    assertEquals(dto.getCreatedById(), body.getCreatedById());
    assertEquals(dto.getCompanyId(), body.getCompanyId());
    assertEquals(dto.getCreated(), body.getCreated());
    assertEquals(dto.getAmount(), body.getAmount());
  }

  @Test
  public void successDelete() throws Exception {
    getAll(2);
    delete(getPath(BASE_PATH, POSITION_IDS[0]))
        .andExpect(status().isNoContent());
    getAll(1);
  }

  private void getAll(int expectedSize) throws Exception {
    get(BASE_PATH)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(expectedSize)));
  }
}
