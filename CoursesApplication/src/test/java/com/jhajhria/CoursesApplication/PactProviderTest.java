package com.jhajhria.CoursesApplication;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("CoursesCatalog")
@PactFolder("pacts")
public class PactProviderTest {

    @LocalServerPort
    public int port;

    @BeforeEach
    public void setup(PactVerificationContext context)
    {

        context.setTarget(new HttpTestTarget("localhost",port));
    }

    @TestTemplate // it means test can run multiple times based on how many times this end point test is called by other tests.  pact mock end point
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void pactVerificationTest(PactVerificationContext context){
        context.verifyInteraction();
    }
}