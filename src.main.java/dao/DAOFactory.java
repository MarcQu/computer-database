package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory implements AutoCloseable {
  private Connection conn;

  /**
   * DAOFactory contient les méthodes spécifiques pour initier la connexion à la base de données.
   * @throws IOException IOException
   */
  public DAOFactory() throws IOException {
    this.initConnexion();
  }

  /**
   * Initialise la connexion à la BDD.
   * @throws IOException IOException
   */
  public void initConnexion() throws IOException {
    Properties prp = new Properties();
    String path = "D:\\Eclipse-workspace\\Computer-database\\src.main.java\\dao\\dao.properties";
    InputStream input = new FileInputStream(path);
    prp.load(input);
    try {
      this.conn = DriverManager.getConnection(prp.getProperty("url"), prp.getProperty("login"), prp.getProperty("password"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Récupère la connexion à la base de données.
   * @return Connection la connection à la base de données
   */
  public Connection getConnection() {
    return this.conn;
  }

  @Override
  public void close() throws Exception {
    this.conn.close();
  }
}
