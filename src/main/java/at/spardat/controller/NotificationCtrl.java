package at.spardat.controller;

import at.spardat.rto.ArtistRto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationCtrl {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ArtistRto greeting(String message) throws Exception {
        System.out.printf("PUSHIT");
        Thread.sleep(1000); // simulated delay
        return ArtistRto.of("Juhuuu", "PUSH");
    }

}
