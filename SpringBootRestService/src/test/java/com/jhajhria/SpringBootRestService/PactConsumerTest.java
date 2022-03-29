package com.jhajhria.SpringBootRestService;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jhajhria.SpringBootRestService.controller.LibraryController;
import com.jhajhria.SpringBootRestService.controller.ProductsPrices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "CoursesCatalog")
public class PactConsumerTest {

    @Pact(consumer = "CatalogConsumer")// for testAllProductsSum
    public RequestResponsePact allCoursedDetailsPactConfig(PactDslWithProvider builder) throws JsonProcessingException {
        //
        return builder.given("courses exist").uponReceiving("getting all courses details").
                path("/allCourseDetails").method("GET").willRespondWith().status(200).
                body(PactDslJsonArray.arrayMinLike(2)
                        .stringType("course_name")//value is optional
                        .stringType("id")
                        .stringType("price","12.0")
                        .stringType("category")
                        .closeObject()).toPact();


    }



    @Autowired
    LibraryController libraryController;

    @Test
    @PactTestFor(pactMethod = "allCoursedDetailsPactConfig",port = "9999")
    public void testAllProductsSum(MockServer mockServer) throws JsonProcessingException {
        //get allProductPrices depends on CourseApplication service.
        //So we need to mock the service. We will use @Pact for this and our consumer for this pact will
        //be this service. Which we are calling "CatalogConsumer"
        libraryController.setBaseUrl(mockServer.getUrl());
        ProductsPrices productPrices = libraryController.getProductPrices();
    }
}