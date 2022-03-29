package com.jhajhria.SpringBootRestService;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonArray;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jhajhria.SpringBootRestService.controller.LibraryController;
import com.jhajhria.SpringBootRestService.controller.ProductsPrices;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "CoursesCatalog")
public class PactConsumerTest {

//    @Pact(consumer = "CatalogConsumer")// for testAllProductsSum
//    public V4Pact AllCoursedDetailsPactConfig(PactDslWithProvider builder) throws JsonProcessingException {
//        //
//        return builder.given("courses exist").uponReceiving("getting all courses details").
//                path("/allCourseDetails").method("GET").willRespondWith().status(200).
//                body(PactDslJsonArray.arrayMinLike(7)
//                        .stringType("course_name")//value is optional
//                        .stringType("id")
//                        .integerType("price",10)
//                        .stringType("category")
//                        ).toPact(V4Pact.class);
//
//    }

    @Pact(consumer = "CatalogConsumer")// for testAllProductsSum
    public V4Pact AllCoursedDetailsPriceOnlyPactConfig(PactDslWithProvider builder) throws JsonProcessingException {
        //
        return builder.given("courses exist").uponReceiving("getting all courses details").
                path("/allCourseDetails").method("GET").willRespondWith().status(200).
                body(PactDslJsonArray.arrayMinLike(2)
                        .integerType("price",10)
                ).toPact(V4Pact.class);
// course_name , id and category are optional for us right now as we are only utilizing price right now.
// so we can remove those from our test
    }



    @Autowired
    LibraryController libraryController;

    @Test
    @PactTestFor(pactMethod = "AllCoursedDetailsPriceOnlyPactConfig",port = "9999")
    public void testAllProductsSum(MockServer mockServer) throws JsonProcessingException {
        String expectedJson ="{\"booksPrice\":250,\"coursesPrice\":20}";//booksPrice is hardcoded


        //get allProductPrices depends on CourseApplication service.
        //So we need to mock the service. We will use @Pact for this and our consumer for this pact will
        //be this service. Which we are calling "CatalogConsumer"
        libraryController.setBaseUrl(mockServer.getUrl());
        ProductsPrices productPrices = libraryController.getProductPrices();

        ObjectMapper mapper = new ObjectMapper();
        String jsonObjStringActual = mapper.writeValueAsString(productPrices);

        Assertions.assertEquals(expectedJson,jsonObjStringActual);
    }
}