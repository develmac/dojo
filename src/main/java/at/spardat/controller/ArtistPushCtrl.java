package at.spardat.controller;

import at.spardat.model.domain.artist.ArtistTransformer;
import at.spardat.rto.ArtistRto;
import javaslang.control.Try;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import persistence.reposervice.ArtistNotificationService;
import persistence.reposervice.ArtistRepoService;

@Controller
public class ArtistPushCtrl {

    private final SimpMessagingTemplate template;

    private final ArtistNotificationService artistNotificationService;

    private final ArtistRepoService artistRepoService;

    @Autowired
    public ArtistPushCtrl(SimpMessagingTemplate template, ArtistNotificationService artistNotificationService, ArtistRepoService artistRepoService) {
        this.template = template;
        this.artistNotificationService = artistNotificationService;
        this.artistRepoService = artistRepoService;
        this.startListeningForartistCreated();
    }

    @MessageMapping("/artist")
    public void artistReq(ArtistRto artistRto) {
        Try.of(() -> artistRto)
                .map(ArtistTransformer::modelFrom)
                .map(ArtistTransformer::entityFrom)
                .mapTry(artistRepoService::save)
                .onFailure(throwable -> System.out.printf("ERROR-> %s", throwable));
    }

    private void startListeningForartistCreated() {
        artistNotificationService
                .startListeningForNewEntities()
                .map(ArtistTransformer::modelFrom)
                .map(ArtistTransformer::modelFrom)
                .doOnNext(artistRto -> System.out.printf("Sending new artist event using WS!"))
                .subscribe(this::sendArtistRto);


    }

    private void sendArtistRto(ArtistRto artistRto) {
        template.convertAndSend("/topic/artist", artistRto);
    }

}
