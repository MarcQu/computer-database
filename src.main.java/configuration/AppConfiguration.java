package configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan({"service", "dao"})
public class AppConfiguration {
  /**
   * Initialise la connexion à la BDD hikari.
   * @return la dataSource
   */
  @Bean
  public HikariDataSource daoFactory() {
    HikariConfig config = new HikariConfig("D:\\Eclipse-workspace\\Computer-database\\src.main.resources\\datasource.properties");
    return new HikariDataSource(config);
  }
}
