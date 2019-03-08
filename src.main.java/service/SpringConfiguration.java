package service;

import java.sql.SQLException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = {"src.main.java.service"})
public class SpringConfiguration {
  /**
   * Retourne l'instance de CompanyService.
   * @return CompanyService CompanyService
   * @throws SQLException SQLException
   */
  @Bean
  public CompanyService getCompanyService() throws SQLException {
    return CompanyService.getInstance();
  }
}
