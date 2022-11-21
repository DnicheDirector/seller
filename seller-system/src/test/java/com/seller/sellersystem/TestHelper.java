package com.seller.sellersystem;

import com.seller.sellersystem.category.models.Category;
import com.seller.sellersystem.category.services.CategoryService;
import com.seller.sellersystem.company.client.CompanyClient;
import com.seller.sellersystem.company.controllers.CompaniesMockTestHelper;
import com.seller.sellersystem.company.views.CompanyRequest;
import com.seller.sellersystem.company.views.CompanyResponse;
import com.seller.sellersystem.item.models.Item;
import com.seller.sellersystem.item.services.ItemService;
import com.seller.sellersystem.position.models.Position;
import com.seller.sellersystem.position.services.PositionService;
import com.seller.sellersystem.user.models.Role;
import com.seller.sellersystem.user.models.User;
import com.seller.sellersystem.user.services.UserService;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

@Service
@RequiredArgsConstructor
public class TestHelper {

  private final UserService userService;
  private final ItemService itemService;
  private final PositionService positionService;
  private final CategoryService categoryService;
  private final CompanyClient companyClient;
  private final CompaniesMockTestHelper companiesMockTestHelper;

  public Category createRandomCategory() {
    var category = Category.builder()
        .name(generateRandomString())
        .description(generateRandomString())
        .build();
    return categoryService.create(category);
  }

  public CompanyResponse createRandomCompany() {
    var company = CompanyRequest.builder()
        .name(generateRandomString())
        .email(generateRandomString())
        .description(generateRandomString())
        .build();
    this.companiesMockTestHelper.mockCompanyCreation(company);
    return companyClient.createCompany(company);
  }

  public Item createRandomItem() {
    var item = Item.builder()
        .name(generateRandomString())
        .description(generateRandomString())
        .category(createRandomCategory())
        .created(ZonedDateTime.now())
        .build();
    return itemService.create(item);
  }

  public User createRandomUser() {
    var now = ZonedDateTime.now();
    var user = User.builder()
        .name(generateRandomString())
        .username(generateRandomString())
        .email(generateRandomString())
        .companyId(createRandomCompany().getId())
        .role(Role.DIRECTOR)
        .created(now)
        .updated(now)
        .build();
    return userService.create(user);
  }

  public Position createRandomPosition() {
    var position = Position.builder()
        .createdBy(createRandomUser())
        .amount(BigDecimal.valueOf(generateRandomInt()))
        .created(ZonedDateTime.now())
        .companyId(createRandomCompany().getId())
        .item(createRandomItem())
        .build();
    return positionService.create(position);
  }

  private String generateRandomString() {
    return RandomStringUtils.randomAlphanumeric(20);
  }

  private int generateRandomInt() {
    return new Random().nextInt(100000);
  }

}
