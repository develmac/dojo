package at.obsolete.integration;


import io.restassured.RestAssured;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class UsersIT {


    private static final String ANY_BODY = "[{\n" +
            "\t\"username\": \"mac\",\n" +
            "\t\"id\": 123\n" +
            "}]";

    @LocalServerPort
    private Integer randomServerPort;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    @Ignore
    public void should_find_playlist_for_user_by_userId() throws Exception {
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();

        server.expect(once(),
                requestTo(String.format("http://localhost:8081/users/all", randomServerPort)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(ANY_BODY, MediaType.APPLICATION_JSON));

        RestAssured.when()
                .get(String.format("http://localhost:%s/api/user/123/playlist", randomServerPort))
                .then()
                .body(equalTo("test"));

        server.verify();


    }
}
