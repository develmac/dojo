package at.spardat.resource;

import at.spardat.service.PlaylistDataService;
import at.spardat.service.UserDataService;
import at.spardat.service.dto.UserConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;


@Component
@Path("/user")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserResource {

    private final PlaylistDataService playlistService;

    private final UserDataService userService;

    @GET
    @Path("/{id}/playlist")
    @Produces(MediaType.APPLICATION_JSON)
    public void getPlaylists(@PathParam("id") String id, @Suspended AsyncResponse asyncResponse) {
        userService.getUserById(id)
                .toObservable()
                .cache()
                .doOnEach(userDTONotification -> System.out.printf(userDTONotification.toString()))
                .map(UserConverter::convertFromDTO)
                .flatMap(playlistService::getPlayListForUser)
                .subscribe(asyncResponse::resume,
                        err -> System.out.printf("BOOOM"));
    }
}