package at.reactive;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"at.reactive.config", "at.reactive.config", "at.reactive.service"})
public class ReactiveServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveServiceApplication.class, args);
    }

}
