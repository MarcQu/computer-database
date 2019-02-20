package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

import model.Company;
import model.Computer;

public class ComputerFactory implements AutoCloseable {
  private Connection conn;
  private static ComputerFactory instance = null;
  private static final String URL = "jdbc:mysql://localhost:3306/computer-database-db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT%2B1";
  private static final String COUNT = "SELECT COUNT(id) AS rowcount FROM computer";

  /**
   * ComputerFactory contient les méthodes spécifiques à la table computer.
   * @throws SQLException SQLException
   */
  private ComputerFactory() throws SQLException {
    this.conn = DAOFactory.getInstance(URL).getConnection();
  }

  /**
   * Méthode qui retourne l'instance unique de la classe ComputerFactory.
   * @return l'instance de la classe ComputerFactory
   * @throws SQLException SQLException
   */
  public static ComputerFactory getInstance() throws SQLException {
    if (ComputerFactory.instance == null) {
      ComputerFactory.instance = new ComputerFactory();
    }
    return ComputerFactory.instance;
  }

  /**
   * Retourne le nombre de lignes dans la table computer.
   * @return nombre le nombre de ligne
   * @throws SQLException
   */
  public int countComputers() throws SQLException {
    Statement stmt = this.conn.createStatement();
    ResultSet rs = stmt.executeQuery(COUNT);
    rs.next();
    int nombre = rs.getInt("rowcount");
    return nombre;
  }

  /**
   * Liste quelques ordinateurs contenus dans la table computer.
   * @param nombre nombre de résultat à afficher
   * @param champs les champs de la table à afficher
   * @param offset l'offset pour la requète sql
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Computer> listComputers(int nombre, int offset, ArrayList<String> champs)
      throws SQLException {
    ArrayList<Computer> computers = new ArrayList<Computer>();
    Statement stmt = this.conn.createStatement();
    Scanner scanner = new Scanner(System.in);
    String query = "SELECT ";
    for (int i = 0; i < champs.size() - 1; i++) {
      query += champs.get(i) + ", ";
    }
    query += champs.get(champs.size() - 1) + " FROM computer LIMIT " + nombre + " OFFSET " + offset;
    ResultSet rs = stmt.executeQuery(query);
    while (rs.next()) {
      Computer computer = new Computer();
      for (int i = 0; i < champs.size(); i++) {
        if (rs.getString(champs.get(i)) != null) {
          switch (champs.get(i)) {
          case "id":
            computer.setId(Integer.parseInt(rs.getString(champs.get(i))));
            break;
          case "name":
            computer.setName(rs.getString(champs.get(i)));
            break;
          case "introduced":
            if (!rs.getString(champs.get(i)).equals("0000-00-00 00:00:00")) {
              computer.setIntroduced(Timestamp.valueOf(rs.getString(champs.get(i))));
            }
            break;
          case "discontinued":
            if (!rs.getString(champs.get(i)).equals("0000-00-00 00:00:00")) {
              computer.setDiscontinued(Timestamp.valueOf(rs.getString(champs.get(i))));
            }
            break;
          case "company_id":
            computer.setCompany(new Company(Integer.parseInt(rs.getString(champs.get(i))), ""));
            break;
          default:
            break;
          }
        }
      }
      computers.add(computer);
    }
    return computers;
  }

  /**
   * Liste tous les ordinateurs contenus dans la table computer.
   * @param champs les champs de la table à afficher
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Computer> listComputersAll(ArrayList<String> champs) throws SQLException {
    ArrayList<Computer> computers = new ArrayList<Computer>();
    Statement stmt = this.conn.createStatement();
    Scanner scanner = new Scanner(System.in);
    String query = "SELECT ";
    for (int i = 0; i < champs.size() - 1; i++) {
      query += champs.get(i) + ", ";
    }
    query += champs.get(champs.size() - 1) + " FROM computer";
    ResultSet rs = stmt.executeQuery(query);
    while (rs.next()) {
      Computer computer = new Computer();
      for (int i = 0; i < champs.size(); i++) {
        if (rs.getString(champs.get(i)) != null) {
          switch (champs.get(i)) {
          case "id":
            computer.setId(Integer.parseInt(rs.getString(champs.get(i))));
            break;
          case "name":
            computer.setName(rs.getString(champs.get(i)));
            break;
          case "introduced":
            if (!rs.getString(champs.get(i)).equals("0000-00-00 00:00:00")) {
              computer.setIntroduced(Timestamp.valueOf(rs.getString(champs.get(i))));
            }
            break;
          case "discontinued":
            if (!rs.getString(champs.get(i)).equals("0000-00-00 00:00:00")) {
              computer.setDiscontinued(Timestamp.valueOf(rs.getString(champs.get(i))));
            }
            break;
          case "company_id":
            computer.setCompany(new Company(Integer.parseInt(rs.getString(champs.get(i))), ""));
            break;
          default:
            break;
          }
        }
      }
      computers.add(computer);
    }
    return computers;
  }

  /**
   * Affiche les informations d'un ordinateur contenu dans la table computer.
   * @param numero l'id de l'ordinateur à afficher
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Computer> showComputerDetails(int numero) throws SQLException {
    ArrayList<Computer> computers = new ArrayList<Computer>();
    Statement stmt = this.conn.createStatement();
    String query = "SELECT * FROM computer WHERE id = " + numero;
    ResultSet rs = stmt.executeQuery(query);
    String[] champs = {"id", "name", "introduced", "discontinued", "company_id"};
    while (rs.next()) {
      Computer computer = new Computer();
      for (int i = 0; i < champs.length; i++) {
        if (rs.getString(champs[i]) != null) {
          switch (champs[i]) {
          case "id":
            computer.setId(Integer.parseInt(rs.getString(champs[i])));
            break;
          case "name":
            computer.setName(rs.getString(champs[i]));
            break;
          case "introduced":
            if (!rs.getString(champs[i]).equals("0000-00-00 00:00:00")) {
              computer.setIntroduced(Timestamp.valueOf(rs.getString(champs[i])));
            }
            break;
          case "discontinued":
            if (!rs.getString(champs[i]).equals("0000-00-00 00:00:00")) {
              computer.setDiscontinued(Timestamp.valueOf(rs.getString(champs[i])));
            }
            break;
          case "company_id":
            computer.setCompany(new Company(Integer.parseInt(rs.getString(champs[i])), ""));
            break;
          default:
            break;
          }
        }
      }
      computers.add(computer);
    }
    return computers;
  }

  /**
   * Ajoute un ordinateur dans la table computer.
   * @param name         le nom de l'ordinateur à ajouter
   * @param introduced   la date d'introduction de l'ordinateur à ajouter
   * @param discontinued la date d'interruption de l'ordinateur à ajouter
   * @param companyId    l'id de la companie de l'ordinateur à ajouter
   * @throws SQLException             SQLException
   * @throws IllegalArgumentException IllegalArgumentException
   */
  public void createComputer(String name, String introduced, String discontinued, String companyId)
      throws SQLException, IllegalArgumentException {
    String query = "INSERT INTO computer(name, introduced, discontinued, company_id) values(?, ?, ?, ?)";
    PreparedStatement stmt = this.conn.prepareStatement(query);
    if ("".equals(name)) {
      stmt.setString(1, null);
    } else {
      stmt.setString(1, name);
    }
    if ("".equals(introduced)) {
      stmt.setTimestamp(2, null);
    } else {
      stmt.setTimestamp(2, Timestamp.valueOf(introduced));
    }
    if ("".equals(discontinued)) {
      stmt.setTimestamp(3, null);
    } else {
      stmt.setTimestamp(3, Timestamp.valueOf(discontinued));
    }
    if ("".equals(companyId)) {
      stmt.setString(4, null);
    } else {
      stmt.setInt(4, Integer.parseInt(companyId));
    }
    stmt.executeUpdate();
  }

  /**
   * Met à jour un ordinateur dans la table computer.
   * @param id           l'id de l'ordinateur à mettre à jour
   * @param name         le nom de l'ordinateur à mettre à jour
   * @param introduced   la date d'introduction de l'ordinateur à mettre à jour
   * @param discontinued la date d'interruption de l'ordinateur à mettre à jour
   * @param companyId    l'id de la companie de l'ordinateur à mettre à jour
   * @param champs       les champs qui sont prises en compte par la mise à jour
   * @throws SQLException SQLException
   */
  public void updateComputer(String id, String name, String introduced, String discontinued,
      String companyId, ArrayList<String> champs) throws SQLException {
    String query = "UPDATE computer SET ";
    for (int i = 0; i < champs.size() - 1; i++) {
      query += champs.get(i) + " = ?, ";
    }
    query += champs.get(champs.size() - 1) + " = ? WHERE id = ?";
    PreparedStatement stmt = this.conn.prepareStatement(query);
    for (int i = 0; i < champs.size(); i++) {
      switch (champs.get(i)) {
      case "name":
        if ("".equals(name)) {
          stmt.setString(i + 1, null);
        } else {
          stmt.setString(i + 1, name);
        }
        break;
      case "introduced":
        if ("".equals(introduced)) {
          stmt.setTimestamp(i + 1, null);
        } else {
          stmt.setTimestamp(i + 1, Timestamp.valueOf(introduced));
        }
        break;
      case "discontinued":
        if ("".equals(discontinued)) {
          stmt.setTimestamp(i + 1, null);
        } else {
          stmt.setTimestamp(i + 1, Timestamp.valueOf(discontinued));
        }
        break;
      case "company_id":
        if ("".equals(companyId)) {
          stmt.setString(i + 1, null);
        } else {
          stmt.setInt(i + 1, Integer.parseInt(companyId));
        }
        break;
      default:
        break;
      }
    }
    stmt.setInt(champs.size() + 1, Integer.parseInt(id));
    stmt.executeUpdate();
  }

  /**
   * Supprime un ordinateur de la table computer.
   * @param id           l'id de l'ordinateur à supprimer
   * @param name         le nom de l'ordinateur à supprimer
   * @param introduced   la date d'introduction de l'ordinateur à supprimer
   * @param discontinued la date d'interruption de l'ordinateur à supprimer
   * @param companyId    l'id de la companie de l'ordinateur à supprimer
   * @param champs       les champs qui sont prises en compte par la suppression
   * @throws SQLException SQLException
   */
  public void deleteComputer(String id, String name, String introduced, String discontinued,
      String companyId, ArrayList<String> champs) throws SQLException {
    String query = "DELETE FROM computer WHERE ";
    for (int i = 0; i < champs.size() - 1; i++) {
      query += champs.get(i) + " = ? AND ";
    }
    query += champs.get(champs.size() - 1) + " = ?";
    PreparedStatement stmt = this.conn.prepareStatement(query);
    for (int i = 0; i < champs.size(); i++) {
      switch (champs.get(i)) {
      case "id":
        if ("".equals(id)) {
          stmt.setString(i + 1, null);
        } else {
          stmt.setInt(i + 1, Integer.parseInt(id));
        }
        break;
      case "name":
        if ("".equals(name)) {
          stmt.setString(i + 1, null);
        } else {
          stmt.setString(i + 1, name);
        }
        break;
      case "introduced":
        if ("".equals(introduced)) {
          stmt.setTimestamp(i + 1, null);
        } else {
          stmt.setTimestamp(i + 1, Timestamp.valueOf(introduced));
        }
        break;
      case "discontinued":
        if ("".equals(discontinued)) {
          stmt.setTimestamp(i + 1, null);
        } else {
          stmt.setTimestamp(i + 1, Timestamp.valueOf(discontinued));
        }
        break;
      case "company_id":
        if ("".equals(companyId)) {
          stmt.setString(i + 1, null);
        } else {
          stmt.setInt(i + 1, Integer.parseInt(companyId));
        }
        break;
      default:
        break;
      }
    }
    stmt.executeUpdate();
  }

  @Override
  public void close() throws Exception {
    this.conn.close();
  }
}
