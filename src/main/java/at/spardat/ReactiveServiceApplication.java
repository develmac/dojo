package at.spardat;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class ReactiveServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveServiceApplication.class, args);
    }

    /*@Bean
    CommandLineRunner demo(UserDAORepo UserDAORepository) {
        return args -> {

            UserDAORepository.deleteAll();

            UserDAO greg = new UserDAO("Greg");
            UserDAO roy = new UserDAO("Roy");
            UserDAO craig = new UserDAO("Craig");

            List<UserDAO> team = Arrays.asList(greg, roy, craig);

            //log.info("Before linking up with Neo4j...");

            team.stream().forEach(UserDAO -> System.out.printf("\t" + UserDAO.toString()));

            UserDAORepository.save(greg);
            UserDAORepository.save(roy);
            UserDAORepository.save(craig);

            greg.worksWith(roy);
            greg.worksWith(craig);
            UserDAORepository.save(greg);

            roy = UserDAORepository.findByName(roy.getName());
            roy.worksWith(craig);
            // We already know that roy works with greg
            UserDAORepository.save(roy);

            // We already know craig works with roy and greg


            team.stream().forEach(person -> System.out.printf(
                    "\t" + UserDAORepository.findByName(person.getName()).toString()));

            greg = UserDAORepository.findByName(greg.getName());


            System.out.printf("greg:" + greg.toString());

        };
    }*/
}
