package at.spardat.controller;

import at.spardat.rto.ArtistRto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ArtistPushCtrl {

    @MessageMapping("/topic/artist")

    public ArtistRto artistCreated(String message) throws Exception {
        return ArtistRto.of("Juhuuu", "PUSH");
    }

}
