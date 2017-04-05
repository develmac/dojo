package at.spardat.resource;

import at.spardat.model.domain.Playlist.Playlist;
import at.spardat.model.domain.Playlist.PlaylistTransformer;
import at.spardat.service.PlaylistService;
import io.reactivex.schedulers.Schedulers;
import org.glassfish.jersey.server.ChunkedOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;


@Component
@Path("/playlist")
public class PlaylistResource {

    @Autowired
    private PlaylistService playlistService;

    @GET
    @Path("/test1")
    @Produces(MediaType.APPLICATION_JSON)
    public void getPlaylistsTest1(@Suspended AsyncResponse asyncResponse) {
        playlistService
                .getPlaylistForTest1()
                .subscribe(asyncResponse::resume);
    }

    @GET
    @Path("/test2")
    @Produces(MediaType.APPLICATION_JSON)
    public void getPlaylistsTest2(@Suspended AsyncResponse asyncResponse) {
        playlistService
                .getPlayListForTest2()
                .map(PlaylistTransformer::transformToDto)
                .toList()
                .subscribe(asyncResponse::resume);
    }


    @GET
    @Path("/test3")
    @Produces(MediaType.APPLICATION_JSON)
    public void getPlaylist(@Suspended AsyncResponse asyncResponse) {
        playlistService.getPlayListForTest2()
                .subscribe(asyncResponse::resume);
    }


    @GET
    @Path("/test4")
    @Produces(MediaType.APPLICATION_JSON)
    public ChunkedOutput<Playlist> getPlaylistSlow() {
        final ChunkedOutput<Playlist> output = new ChunkedOutput<Playlist>(Playlist.class);
        playlistService.getSlowPlaylist()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(playlist -> {
                    output.write(playlist);
                    System.out.printf("Observe on " + Thread.currentThread().getName());
                }, System.err::println, output::close);

        return output;

    }


    private static Playlist wait(Playlist pl) {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return pl;
    }
}