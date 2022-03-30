package com.jhajhria.CoursesApplication;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import com.jhajhria.CoursesApplication.controller.AllCourseData;
import com.jhajhria.CoursesApplication.repository.CoursesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Map;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("CoursesCatalog")
//@PactFolder("pacts")
//@PactUrl(urls = "https://kapiltechverito.pactflow.io/pacts/provider/CoursesCatalog/consumer/CatalogConsumer/latest", auth = @Authentication(token = "token_here"))
@PactBroker(url = "https://kapiltechverito.pactflow.io",authentication = @PactBrokerAuth(token = "token_here"))
public class PactProviderTest {

    @LocalServerPort
    public int port;

    @Autowired
    CoursesRepository coursesRepository;

    @BeforeEach
    public void setup(PactVerificationContext context) {
//        System.getProperties().setProperty("pact.verifier.publishResults", "true");
        context.setTarget(new HttpTestTarget("localhost", port));
    }

    @State(value = "courses exist", action = StateChangeAction.SETUP)
    public void courseExistSetup() {
        //setup state

    }

    @State(value = "Course Appium exist", action = StateChangeAction.SETUP)
    public void appiumCourseExist() {
        //appium
    }

    @State(value = "courses exist", action = StateChangeAction.TEARDOWN)
    public void courseExistTearDown() {
        //teardown state

    }


    @State(value = "Course Appium exist", action = StateChangeAction.TEARDOWN)
    public void appiumCourseExistTearDown() {
        //
    }


    @State(value = "Course Appium does not exist", action = StateChangeAction.SETUP)
    public void appiumCourseDoNotExist(Map<String, Object> params) {

        String name = (String) params.get("name");

        //to delete the appium record in database


        Optional<AllCourseData> del = coursesRepository.findById(name);//mock

        if (del.isPresent()) {
            coursesRepository.deleteById("Appium");
        }


    }

    @State(value = "Course Appium does not exist", action = StateChangeAction.TEARDOWN)
    public void appiumCourseDoNotExistTearDown(Map<String, Object> params) {
        ////add appium record in database
        String name = (String) params.get("name");
        Optional<AllCourseData> del = coursesRepository.findById(name);//mock

        if (!del.isPresent()) {

            AllCourseData allCourseData = new AllCourseData();
            allCourseData.setCourse_name("Appium");
            allCourseData.setCategory("mobile");
            allCourseData.setPrice(120);
            allCourseData.setId("12");
            coursesRepository.save(allCourseData);

        }

    }

        @TestTemplate
        // it means test can run multiple times based on how many times this end point test is called by other tests.  pact mock end point
        @ExtendWith(PactVerificationInvocationContextProvider.class)
        public void pactVerificationTest (PactVerificationContext context){
            System.setProperty("pact.verifier.publishResults", "true");
            context.verifyInteraction();
        }
    }



// define port number in the test class
//define port folder for the test class
//define setup and tear down state
// We are checking the interaction between the two services. Interaction when a valid request is made and data exist on server.
// So setup will be called before each test, this setup will create an object if it is not present.
// Tear down will then remove that object after each test
//
//Test should never fall for invalid request or absence of data
//