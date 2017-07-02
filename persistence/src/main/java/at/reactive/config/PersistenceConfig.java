package at.reactive.config;

import at.reactive.dao.ChatMsgEntity;
import at.reactive.reposervice.ChatMsgDbChangeNotification;
import at.reactive.reposervice.ChatMsgRepoService;
import at.reactive.util.DbUtils;
import io.vavr.control.Option;
import lombok.SneakyThrows;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableJpaRepositories(basePackages = "at.reactive.repo")
@ComponentScan(basePackages = "at.reactive")
@EnableTransactionManagement
public class PersistenceConfig {

    @Bean
    @Primary
    public DataSource dataSource() throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser("playlistsapp");
        dataSource.setPassword("tadaa");
        dataSource.setURL("jdbc:oracle:thin:@localhost:1521:XE");
        dataSource.setImplicitCachingEnabled(true);
        dataSource.setFastConnectionFailoverEnabled(true);
        return dataSource;
    }

    @Bean
    public ChatMsgRepoService chatMsgRepoService() {
        return new ChatMsgRepoService();
    }

    @Bean
    public ChatMsgDbChangeNotification chatMsgDbChangeNotification(DataSource dataSource) {
        return Option.of(dataSource)
                .map(DbUtils::getOracleConnectionFromDataSource)
                .map(ChatMsgDbChangeNotification::new)
                .get();
    }

    @Bean
    @SneakyThrows
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan(ChatMsgEntity.class.getPackage().getName());
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

}
