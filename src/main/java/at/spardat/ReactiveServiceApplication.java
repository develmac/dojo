package at.spardat;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories(excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE)
})

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
