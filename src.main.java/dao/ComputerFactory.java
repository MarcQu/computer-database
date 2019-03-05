package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Company;
import model.Computer;

public class ComputerFactory {
  private static ComputerFactory instance = null;
  private static final String COUNT_ALL = "SELECT COUNT(id) AS rowcount FROM computer";
  private static final String COUNT = "SELECT COUNT(id) AS rowcount FROM computer WHERE name like ?";
  private static final String SHOW = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ? UNION ALL SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer RIGHT JOIN company ON computer.company_id = company.id WHERE computer.company_id IS NULL AND computer.id = ?";
  private static final String CREATE = "INSERT INTO computer(name, introduced, discontinued, company_id) values(?, ?, ?, ?)";
  private static final String LIST_ASC = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company ON computer.company_id = company.id UNION ALL SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer RIGHT JOIN company ON computer.company_id = company.id WHERE computer.company_id IS NULL AND computer.id IS NOT NULL ORDER BY name ASC LIMIT ? OFFSET ?";
  private static final String LIST_DESC = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company ON computer.company_id = company.id UNION ALL SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer RIGHT JOIN company ON computer.company_id = company.id WHERE computer.company_id IS NULL AND computer.id IS NOT NULL ORDER BY name DESC LIMIT ? OFFSET ?";
  private static final String SEARCH_ASC = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? UNION ALL SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer RIGHT JOIN company ON computer.company_id = company.id WHERE computer.company_id IS NULL AND computer.id IS NOT NULL AND computer.name LIKE ? ORDER BY name ASC LIMIT ? OFFSET ?";
  private static final String SEARCH_DESC = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? UNION ALL SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name as company_name FROM computer RIGHT JOIN company ON computer.company_id = company.id WHERE computer.company_id IS NULL AND computer.id IS NOT NULL AND computer.name LIKE ? ORDER BY name DESC LIMIT ? OFFSET ?";
  private static final String DELETE = "DELETE FROM computer WHERE id = ?";
  private Logger logger;

  /**
   * ComputerFactory contient les méthodes spécifiques à la table computer.
   * @throws SQLException SQLException
   */
  private ComputerFactory() throws SQLException {
    this.logger = LoggerFactory.getLogger(ComputerFactory.class);
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
   * @param search le paramètre de la recherche
   * @return nombre le nombre de ligne
   * @throws SQLException SQLException
   */
  public int countComputers(String search) throws SQLException {
    int nombre = 0;
    try (DAOFactory factory = new DAOFactory()) {
      PreparedStatement stmt;
      if (search == null || "".equals(search)) {
        stmt = factory.getConnection().prepareStatement(COUNT_ALL);
      } else {
        stmt = factory.getConnection().prepareStatement(COUNT);
        stmt.setString(1, new StringBuilder("%").append(search).append("%").toString());
      }
      ResultSet rs = stmt.executeQuery();
      rs.next();
      nombre = rs.getInt("rowcount");
    } catch (Exception e) {
      this.logger.error(e.toString());
    }
    return nombre;
  }

  /**
   * Liste quelques ordinateurs contenus dans la table computer.
   * @param nombre nombre de résultat à afficher
   * @param offset l'offset pour la requète sql
   * @param search le paramètre de la recherche
   * @param sort le sens de triage
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Computer> listComputers(int nombre, int offset, String search, String sort)
      throws SQLException {
    ArrayList<Computer> computers = new ArrayList<Computer>();
    try (DAOFactory factory = new DAOFactory()) {
      PreparedStatement stmt;
      if (search == null || "".equals(search)) {
        if ("desc".equals(sort)) {
          stmt = factory.getConnection().prepareStatement(LIST_DESC);
        } else {
          stmt = factory.getConnection().prepareStatement(LIST_ASC);
        }
        stmt.setInt(1, nombre);
        stmt.setInt(2, offset);
      } else {
        if ("desc".equals(sort)) {
          stmt = factory.getConnection().prepareStatement(SEARCH_DESC);
        } else {
          stmt = factory.getConnection().prepareStatement(SEARCH_ASC);
        }
        stmt.setString(1, new StringBuilder("%").append(search).append("%").toString());
        stmt.setString(2, new StringBuilder("%").append(search).append("%").toString());
        stmt.setInt(3, nombre);
        stmt.setInt(4, offset);
      }
      ResultSet rs = stmt.executeQuery();
      String[] champs = {"id", "name", "introduced", "discontinued", "company_id", "company_name"};
      while (rs.next()) {
        Computer computer = new Computer();
        Company company = new Company();
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
                Timestamp stamp = Timestamp.valueOf(rs.getString(champs[i]));
                computer.setIntroduced(stamp);
              }
              break;
            case "discontinued":
              if (!rs.getString(champs[i]).equals("0000-00-00 00:00:00")) {
                Timestamp stamp = Timestamp.valueOf(rs.getString(champs[i]));
                computer.setDiscontinued(stamp);
              }
              break;
            case "company_id":
              company.setId(Integer.parseInt(rs.getString(champs[i])));
              break;
            case "company_name":
              company.setName(rs.getString(champs[i]));
              break;
            default:
              break;
            }
          }
        }
        computer.setCompany(company);
        computers.add(computer);
      }
    } catch (Exception e) {
      this.logger.error(e.toString());
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
    try (DAOFactory factory = new DAOFactory()) {
      Statement stmt = factory.getConnection().createStatement();
      StringBuilder query = new StringBuilder("SELECT ");
      for (int i = 0; i < champs.size() - 1; i++) {
        query.append(champs.get(i)).append(", ");
      }
      query.append(champs.get(champs.size() - 1)).append(" FROM computer");
      ResultSet rs = stmt.executeQuery(query.toString());
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
                Timestamp stamp = Timestamp.valueOf(rs.getString(champs.get(i)));
                computer.setIntroduced(stamp);
              }
              break;
            case "discontinued":
              if (!rs.getString(champs.get(i)).equals("0000-00-00 00:00:00")) {
                Timestamp stamp = Timestamp.valueOf(rs.getString(champs.get(i)));
                computer.setDiscontinued(stamp);
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
    } catch (Exception e) {
      this.logger.error(e.toString());
    }
    return computers;
  }

  /**
   * Affiche les informations d'un ordinateur contenu dans la table computer.
   * @param numero l'id de l'ordinateur à afficher
   * @return computers la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Computer> showComputerDetails(String numero) throws SQLException {
    ArrayList<Computer> computers = new ArrayList<Computer>();
    try (DAOFactory factory = new DAOFactory()) {
      PreparedStatement stmt = factory.getConnection().prepareStatement(SHOW);
      stmt.setString(1, numero);
      stmt.setString(2, numero);
      ResultSet rs = stmt.executeQuery();
      String[] champs = {"id", "name", "introduced", "discontinued", "company_id", "company_name"};
      while (rs.next()) {
        Company company = new Company();
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
                Timestamp stamp = Timestamp.valueOf(rs.getString(champs[i]));
                computer.setIntroduced(stamp);
              }
              break;
            case "discontinued":
              if (!rs.getString(champs[i]).equals("0000-00-00 00:00:00")) {
                Timestamp stamp = Timestamp.valueOf(rs.getString(champs[i]));
                computer.setDiscontinued(stamp);
              }
              break;
            case "company_id":
              company.setId(Integer.parseInt(rs.getString(champs[i])));
              break;
            case "company_name":
              company.setName(rs.getString(champs[i]));
              break;
            default:
              break;
            }
          }
        }
        computer.setCompany(company);
        computers.add(computer);
      }
    } catch (Exception e) {
      this.logger.error(e.toString());
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
    try (DAOFactory factory = new DAOFactory()) {
      PreparedStatement stmt = factory.getConnection().prepareStatement(CREATE);
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
    } catch (Exception e) {
      this.logger.error(e.toString());
    }
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
    StringBuilder query = new StringBuilder("UPDATE computer SET ");
    for (int i = 0; i < champs.size() - 1; i++) {
      query.append(champs.get(i)).append(" = ?, ");
    }
    query.append(champs.get(champs.size() - 1)).append(" = ? WHERE id = ?");
    try (DAOFactory factory = new DAOFactory()) {
      PreparedStatement stmt = factory.getConnection().prepareStatement(query.toString());
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
    } catch (Exception e) {
      this.logger.error(e.toString());
    }
  }

  /**
   * Supprime un ordinateur de la table computer.
   * @param id           l'id de l'ordinateur à supprimer
   * @throws SQLException SQLException
   */
  public void deleteComputer(String id) throws SQLException {
    try (DAOFactory factory = new DAOFactory()) {
      PreparedStatement stmt = factory.getConnection().prepareStatement(DELETE);
      stmt.setString(1, id);
      stmt.executeUpdate();
    } catch (Exception e) {
      this.logger.error(e.toString());
    }
  }
}
