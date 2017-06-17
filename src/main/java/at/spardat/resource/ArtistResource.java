package at.spardat.resource;


import at.spardat.model.domain.artist.ArtistTransformer;
import at.spardat.model.service.artist.ArtistDomainService;
import at.spardat.rto.ArtistRto;
import org.glassfish.jersey.server.ChunkedOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    public ChunkedOutput<ArtistRto> getArtistById(@PathParam("id") String artistId) {
        final ChunkedOutput<ArtistRto> output = new ChunkedOutput<>(ArtistRto.class);
        artistDomainService
                .artistsById(artistId)
                .map(ArtistTransformer::modelFrom)
                .doFinally(output::close)
                .subscribe(output::write, err -> System.out.printf("BAM"));

        return output;
    }

    @GET
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    public ChunkedOutput<List<ArtistRto>> getArtistByName(@QueryParam(value = "name") String artistName) throws IOException {
        final ChunkedOutput<List<ArtistRto>> output = new ChunkedOutput<>(ArtistRto.class);
        List<ArtistRto> resultList = new ArrayList<>();
        artistDomainService
                .artistsByNameLike(artistName)
                .map(ArtistTransformer::modelFrom)
                .subscribe(resultList::add);

        output.write(resultList); //TODO: do it real? rx style
        output.close();
        return output;
    }



}
