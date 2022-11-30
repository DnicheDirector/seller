package com.seller.gateway.routes;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.seller.gateway.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.moreThanOrExactly;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

public class RoutesTest extends BaseTest {

    private static final String[] HEADER = { "Content-Type", "application/json" };

    private static final String USERS_ROUTE = "/api/users";
    private static final String POSITIONS_ROUTE = "/api/positions";
    private static final String CATEGORIES_ROUTE = "/api/categories";
    private static final String ITEMS_ROUTE = "/api/items";
    private static final String COMPANIES_ROUTE = "/api/companies";
    private static final String USER_TRANSACTIONS_ROUTE = "/api/user-transactions";


    @Test
    public void checkUsersRoute() {
        checkRoute(USERS_ROUTE);
    }

    @Test
    public void checkPositionsRoute() {
        checkRoute(POSITIONS_ROUTE);
    }

    @Test
    public void checkCategoriesRoute() {
        checkRoute(CATEGORIES_ROUTE);
    }

    @Test
    public void checkItemsRoute() {
        checkRoute(ITEMS_ROUTE);
    }

    @Test
    public void checkCompaniesRoute() {
        checkRoute(COMPANIES_ROUTE);
    }

    @Test
    public void checkUserTransactionsRoute() {
        checkRoute(USER_TRANSACTIONS_ROUTE);
    }

    private void checkRoute(String route) {
        stubFor(WireMock.get(urlEqualTo(route)).willReturn(
                        aResponse()
                                .withStatus(HttpStatus.OK.value())
                                .withHeader(HEADER[0], HEADER[1])
                )
        );
        get(route, HttpStatus.OK);
        verify(moreThanOrExactly(1), getRequestedFor(urlEqualTo(route)));
    }
}
