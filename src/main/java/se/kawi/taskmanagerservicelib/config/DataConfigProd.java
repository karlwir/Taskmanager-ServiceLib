package se.kawi.taskmanagerservicelib.config;

import java.net.URI;
import java.net.URISyntaxException;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories("se.kawi.taskmanagerservicelib.repository")
@EnableTransactionManagement
@EnableJpaAuditing
@Profile("prod")
public class DataConfigProd {

	@Bean
	DataSource dataSource() throws URISyntaxException {
		HikariConfig config = new HikariConfig();
		URI dbUri = new URI(System.getenv("DATABASE_URL"));
		config.setDriverClassName("org.postgresql.Driver");
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
		config.setJdbcUrl("jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath());
		config.setUsername(username);
		config.setPassword(password);

		return new HikariDataSource(config);
	}
	
	@Bean
	JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.POSTGRESQL);
		adapter.setGenerateDdl(true);

		return adapter;
	}
	
	@Bean
	JpaTransactionManager transactionManager(EntityManagerFactory factory) {
		return new JpaTransactionManager(factory);
	}

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		try {
			factory.setDataSource(dataSource());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		factory.setJpaVendorAdapter(jpaVendorAdapter());
		factory.setPackagesToScan("se.kawi.taskmanagerservicelib.model", "org.springframework.data.jpa.convert.threeten");

		return factory;
	}

}
