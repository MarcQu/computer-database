package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exception.DatePrecedenceException;
import model.Company;
import model.Computer;

public class ComputerDAOFactory {
  private static ComputerDAOFactory instance = null;
  private static final DateTimeFormatter DATEFORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
  private ComputerDAOFactory() throws SQLException {
    this.logger = LoggerFactory.getLogger(ComputerDAOFactory.class);
  }

  /**
   * M�thode qui retourne l'instance unique de la classe ComputerFactory.
   * @return l'instance de la classe ComputerFactory
   * @throws SQLException SQLException
   */
  public static ComputerDAOFactory getInstance() throws SQLException {
    if (ComputerDAOFactory.instance == null) {
      ComputerDAOFactory.instance = new ComputerDAOFactory();
    }
    return ComputerDAOFactory.instance;
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
              computer.setId(rs.getString(champs[i]));
              break;
            case "name":
              computer.setName(rs.getString(champs[i]));
              break;
            case "introduced":
              if (!"0000-00-00 00:00:00".equals(rs.getString(champs[i]))) {
                Timestamp stamp = Timestamp.valueOf(rs.getString(champs[i]));
                computer.setIntroduced(stamp.toLocalDateTime().toLocalDate());
              }
              break;
            case "discontinued":
              if (!"0000-00-00 00:00:00".equals(rs.getString(champs[i]))) {
                Timestamp stamp = Timestamp.valueOf(rs.getString(champs[i]));
                computer.setDiscontinued(stamp.toLocalDateTime().toLocalDate());
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
              computer.setId(rs.getString(champs.get(i)));
              break;
            case "name":
              computer.setName(rs.getString(champs.get(i)));
              break;
            case "introduced":
              if (!"0000-00-00 00:00:00".equals(rs.getString(champs.get(i)))) {
                Timestamp stamp = Timestamp.valueOf(rs.getString(champs.get(i)));
                computer.setIntroduced(stamp.toLocalDateTime().toLocalDate());
              }
              break;
            case "discontinued":
              if (!"0000-00-00 00:00:00".equals(rs.getString(champs.get(i)))) {
                Timestamp stamp = Timestamp.valueOf(rs.getString(champs.get(i)));
                computer.setDiscontinued(stamp.toLocalDateTime().toLocalDate());
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
   * @param computer l'ordinateur à afficher
   * @return computers la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Computer> showComputerDetails(Computer computer) throws SQLException {
    ArrayList<Computer> computers = new ArrayList<Computer>();
    try (DAOFactory factory = new DAOFactory()) {
      PreparedStatement stmt = factory.getConnection().prepareStatement(SHOW);
      stmt.setInt(1, computer.getId());
      stmt.setInt(2, computer.getId());
      ResultSet rs = stmt.executeQuery();
      String[] champs = {"id", "name", "introduced", "discontinued", "company_id", "company_name"};
      while (rs.next()) {
        Company company = new Company();
        Computer computerTemp = new Computer();
        for (int i = 0; i < champs.length; i++) {
          if (rs.getString(champs[i]) != null) {
            switch (champs[i]) {
            case "id":
              computerTemp.setId(rs.getString(champs[i]));
              break;
            case "name":
              computerTemp.setName(rs.getString(champs[i]));
              break;
            case "introduced":
              if (!"0000-00-00 00:00:00".equals(rs.getString(champs[i]))) {
                Timestamp stamp = Timestamp.valueOf(rs.getString(champs[i]));
                computerTemp.setIntroduced(stamp.toLocalDateTime().toLocalDate());
              }
              break;
            case "discontinued":
              if (!"0000-00-00 00:00:00".equals(rs.getString(champs[i]))) {
                Timestamp stamp = Timestamp.valueOf(rs.getString(champs[i]));
                computerTemp.setDiscontinued(stamp.toLocalDateTime().toLocalDate());
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
        computerTemp.setCompany(company);
        computers.add(computerTemp);
      }
    } catch (Exception e) {
      this.logger.error(e.toString());
    }
    return computers;
  }

  /**
   * Ajoute un ordinateur dans la table computer.
   * @param computer l'ordinateur à ajouter
   * @throws SQLException             SQLException
   * @throws IllegalArgumentException IllegalArgumentException
   * @throws DatePrecedenceException DatePrecedenceException
   */
  public void createComputer(Computer computer)
      throws SQLException, IllegalArgumentException, DatePrecedenceException {
    try (DAOFactory factory = new DAOFactory()) {
      PreparedStatement stmt = factory.getConnection().prepareStatement(CREATE);
      stmt.setString(1, computer.getName());
      if (computer.getIntroduced() != null) {
        stmt.setTimestamp(2, Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
      } else {
        stmt.setTimestamp(2, null);
      }
      if (computer.getDiscontinued() != null) {
        stmt.setTimestamp(3, Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
      } else {
        stmt.setTimestamp(3, null);
      }
      if (computer.getCompany() != null) {
        stmt.setInt(4, computer.getCompany().getId());
      } else {
        stmt.setString(4, null);
      }
      stmt.executeUpdate();
    } catch (Exception e) {
      this.logger.error(e.toString());
    }
  }

  /**
   * Met à jour un ordinateur dans la table computer.
   * @param computer l'ordinateur à mettre à jour
   * @param champs       les champs qui sont prises en compte par la mise à jour
   * @throws SQLException SQLException
   * @throws DatePrecedenceException DatePrecedenceException
   */
  public void updateComputer(Computer computer, ArrayList<String> champs) throws SQLException, DatePrecedenceException {
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
          stmt.setString(i + 1, computer.getName());
          break;
        case "introduced":
          if (computer.getIntroduced() != null) {
            stmt.setTimestamp(i + 1, Timestamp.valueOf(computer.getIntroduced().atStartOfDay()));
          } else {
            stmt.setTimestamp(i + 1, null);
          }
          break;
        case "discontinued":
          if (computer.getDiscontinued() != null) {
            stmt.setTimestamp(i + 1, Timestamp.valueOf(computer.getDiscontinued().atStartOfDay()));
          } else {
            stmt.setTimestamp(i + 1, null);
          }
          break;
        case "company_id":
          if (computer.getCompany() != null) {
            stmt.setInt(i + 1, computer.getCompany().getId());
          } else {
            stmt.setString(i + 1, null);
          }
          break;
        default:
          break;
        }
      }
      stmt.setInt(champs.size() + 1, computer.getId());
      stmt.executeUpdate();
    } catch (Exception e) {
      this.logger.error(e.toString());
    }
  }

  /**
   * Supprime un ordinateur de la table computer.
   * @param computer l'ordinateur à supprimer
   * @throws SQLException SQLException
   */
  public void deleteComputer(Computer computer) throws SQLException {
    try (DAOFactory factory = new DAOFactory()) {
      PreparedStatement stmt = factory.getConnection().prepareStatement(DELETE);
      stmt.setInt(1, computer.getId());
      stmt.executeUpdate();
    } catch (Exception e) {
      this.logger.error(e.toString());
    }
  }
}
