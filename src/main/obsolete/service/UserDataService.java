package at.obsolete.service;

import at.obsolete.model.domain.user.User;
import at.obsolete.model.domain.user.UserId;
import at.obsolete.service.dto.UserDTO;
import io.reactivex.Observable;
import io.reactivex.Single;
import vavr.control.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;


@Service
public class UserDataService {

    private String url;
    private RestTemplate restTemplate;
    private Supplier<List<User>> USERS =
            () -> Arrays.asList(User.of(UserId.of(12), "MaC"),
                    User.of(UserId.of(123), "Anton"));


    @Autowired
    public UserDataService(@Value("${user.service.url}") String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;

        //set interceptors/requestFactory
        ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor();
        List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
        ris.add(ri);
        restTemplate.setInterceptors(ris);
        restTemplate.setRequestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));
    }

    public Observable<List<User>> getAllUsers() {


        return Observable.fromArray(USERS.get());
    }

    public Single<User> getUserByName(String userName) {
        return Observable.fromIterable(USERS.get())
                .filter(user -> user.getUsername().equals(userName))
                .firstOrError();
    }

    public Single<UserDTO> getUserById(String userName) {

        return Option.of(userName)
                .map(Integer::valueOf)
                .map(UserId::of)
                .map((userId) ->
                        Observable.fromIterable(getUsersData())
                                .filter(user -> userId.equals(user.getId()))
                                .firstOrError())
                .getOrElse(Single.never());
    }

    private List<UserDTO> getUsersData() {
        ParameterizedTypeReference<List<UserDTO>> responseType = new ParameterizedTypeReference<List<UserDTO>>() {
        };
        ResponseEntity<List<UserDTO>> exchange = restTemplate.exchange(url + "/all", HttpMethod.GET, null, responseType);
        System.out.printf("BODY: " + exchange.toString());
        return exchange.getBody();
    }


}

class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger log = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        ClientHttpResponse response = execution.execute(request, body);

        log(request, body, response);

        return response;
    }

    private void log(HttpRequest request, byte[] body, ClientHttpResponse response) throws IOException {
        System.out.printf("REST LOG" + response);
    }
}