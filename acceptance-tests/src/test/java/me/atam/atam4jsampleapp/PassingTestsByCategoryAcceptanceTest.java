package me.atam.atam4jsampleapp;

import me.atam.atam4j.dummytests.PassingTestsWithCategories;
import me.atam.atam4jdomain.IndividualTestResult;
import me.atam.atam4jdomain.TestsRunResult;
import me.atam.atam4jsampleapp.testsupport.AcceptanceTest;
import me.atam.atam4jsampleapp.testsupport.Atam4jApplicationStarter;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PassingTestsByCategoryAcceptanceTest extends AcceptanceTest {

    @Test
    public void givenPassingTestsWithMultipleCategories_whenTestsByCategoryEndpointCalledAfterTestRun_thenOKMessageReceivedForOneTestOnly(){

        dropwizardTestSupportAppConfig = Atam4jApplicationStarter.startApplicationWith(0, PassingTestsWithCategories.class);

        Response response = getResponseFromTestsWithCategoryOnceTestRunHasCompleted("A");
        TestsRunResult testRunResult = response.readEntity(TestsRunResult.class);
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(testRunResult.getTests().size(), is(1));
        assertThat(
                testRunResult.getTests(),
                hasItem(new IndividualTestResult(PassingTestsWithCategories.class.getName(), "testThatPassesWithCategoryA", "A", true))
        );
    }

    @Test
    public void givenPassingTests_whenTestsEndpointCalledAfterTestRun_thenOKMessageReceivedForTwoTests(){

        dropwizardTestSupportAppConfig = Atam4jApplicationStarter.startApplicationWith(0, PassingTestsWithCategories.class);

        Response response = getResponseFromTestsEndpointOnceTestsRunHasCompleted();
        TestsRunResult testRunResult = response.readEntity(TestsRunResult.class);
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        assertThat(testRunResult.getTests().size(), is(2));
        assertThat(testRunResult.getTests(),
                hasItem(new IndividualTestResult(PassingTestsWithCategories.class.getName(), "testThatPassesWithCategoryA", "A", true))
        );
        assertThat(testRunResult.getTests(),
                hasItem(new IndividualTestResult(PassingTestsWithCategories.class.getName(), "testThatPassesWithCategoryB", "B", true))
        );
    }
}
