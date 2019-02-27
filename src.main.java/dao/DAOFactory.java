package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory implements AutoCloseable {
  private Connection conn;

  /**
   * DAOFactory contient les méthodes spécifiques pour initier la connexion à la base de données.
   * @param url l'url de la base de données
   * @throws SQLException SQLException
   */
  public DAOFactory(String url) throws SQLException {
    this.initConnexion(url);
  }

  /**
   * Initialise la connexion à la BDD.
   * @param url l'url de la base de données
   * @throws SQLException
   */
  public void initConnexion(String url) {
    Properties prp = new Properties();
    prp.put("user", "root");
    prp.put("password", "network");
    try {
      this.conn = DriverManager.getConnection(url, prp);
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
