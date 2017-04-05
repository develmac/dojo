package at.spardat.resource;

import at.spardat.service.PlaylistService;
import at.spardat.service.UserService;
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

    private final PlaylistService playlistService;

    private final UserService userService;

    @GET
    @Path("/{id}/playlist")
    @Produces(MediaType.APPLICATION_JSON)
    public void getPlaylists(@PathParam("id") String id, @Suspended AsyncResponse asyncResponse) {
        userService.getUserById(id)
                .toObservable()
                .cache()
                .flatMap(playlistService::getPlayListForUser)
                .subscribe(asyncResponse::resume,
                        err -> System.out.printf("BOOOM"));
    }
}