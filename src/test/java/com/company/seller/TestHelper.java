package com.company.seller;

import com.company.seller.category.models.Category;
import com.company.seller.category.services.CategoryService;
import com.company.seller.company.models.Company;
import com.company.seller.company.services.CompanyService;
import com.company.seller.item.models.Item;
import com.company.seller.item.services.ItemService;
import com.company.seller.position.models.Position;
import com.company.seller.position.services.PositionService;
import com.company.seller.user.models.Role;
import com.company.seller.user.models.User;
import com.company.seller.user.services.UserService;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.testcontainers.shaded.org.apache.commons.lang3.RandomStringUtils;

@Service
@RequiredArgsConstructor
public class TestHelper {

  private final CompanyService companyService;
  private final UserService userService;
  private final ItemService itemService;
  private final PositionService positionService;
  private final CategoryService categoryService;

  public Category createRandomCategory() {
    var category = Category.builder()
        .name(generateRandomString())
        .description(generateRandomString())
        .build();
    return categoryService.create(category);
  }

  public Company createRandomCompany() {
    var company = Company.builder()
        .name(generateRandomString())
        .email(generateRandomString())
        .created(ZonedDateTime.now())
        .description(generateRandomString())
        .build();
    return companyService.create(company);
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
        .company(createRandomCompany())
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
        .company(createRandomCompany())
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
