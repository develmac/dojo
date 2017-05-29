package at.spardat.resource;


import at.spardat.model.domain.artist.ArtistTransformer;
import at.spardat.model.service.artist.ArtistDomainService;
import at.spardat.rto.ArtistRto;
import org.glassfish.jersey.server.ChunkedOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Component
@Path("/artist")
public class ArtistResource {

    private final ArtistDomainService artistDomainService;

    @Autowired
    public ArtistResource(ArtistDomainService artistDomainService) {
        this.artistDomainService = artistDomainService;
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ChunkedOutput<ArtistRto> getPlaylistSlow(@PathParam("id") String artistId) {
        final ChunkedOutput<ArtistRto> output = new ChunkedOutput<>(ArtistRto.class);
        artistDomainService
                .artistsById(artistId)
                .map(ArtistTransformer::from)
                .doFinally(output::close)
                .subscribe(output::write, err -> System.out.printf("BAM"));

        return output;
    }

}
