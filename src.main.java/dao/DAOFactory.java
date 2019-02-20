package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DAOFactory {
  private Connection conn;
  private static DAOFactory instance = null;

  /**
   * DAOFactory contient les méthodes spécifiques pour initier la connexion à la base de données.
   * @param url l'url de la base de données
   * @throws SQLException SQLException
   */
  private DAOFactory(String url) throws SQLException {
    this.initConnexion(url);
  }

  /**
   * Méthode qui retourne l'instance unique de la classe DAOFactory.
   * @param url l'url de la base de données
   * @return l'instance de la classe DAOFactory
   * @throws SQLException SQLException
   */
  public static DAOFactory getInstance(String url) throws SQLException {
    if (DAOFactory.instance == null) {
      DAOFactory.instance = new DAOFactory(url);
    }
    return DAOFactory.instance;
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
}
