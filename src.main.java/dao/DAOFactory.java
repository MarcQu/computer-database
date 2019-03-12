package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Service
public class DAOFactory implements AutoCloseable {
  private HikariConfig config = new HikariConfig("D:\\Eclipse-workspace\\Computer-database\\src.main.resources\\datasource.properties");
  private HikariDataSource datasource;
  private Connection conn;
  private Logger logger;

  /**
   * DAOFactory contient les méthodes spécifiques pour initier la connexion de la base de données.
   * @throws IOException IOException
   */
  @Autowired
  public DAOFactory() throws IOException {
    this.initConnexion();
    this.logger = LoggerFactory.getLogger(ComputerDAO.class);
  }

  /**
   * Initialise la connexion à la BDD.
   * @throws IOException IOException
   */
  private void initConnexion() throws IOException {
    try {
      this.config.setMaximumPoolSize(10);
      this.datasource = new HikariDataSource(this.config);
      this.conn = this.datasource.getConnection();
    } catch (SQLException e) {
      this.logger.error(e.toString());
    }
  }

  /**
   * Récupère la connexion de la base de données.
   * @return Connection la connection à la base de données
   */
  public Connection getConnection() {
    return this.conn;
  }

  @Override
  public void close() throws Exception {
    this.conn.close();
    this.datasource.close();
  }
}
