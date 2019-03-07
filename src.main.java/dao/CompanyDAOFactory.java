package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Company;

public class CompanyDAOFactory {
  private static CompanyDAOFactory instance = null;
  private static final String COUNT_ALL = "SELECT COUNT(id) AS rowcount FROM company";
  private static final String COUNT = "SELECT COUNT(id) AS rowcount FROM company WHERE name like ?";
  private static final String SHOW = "SELECT id, name FROM company WHERE id = ?";
  private static final String CREATE = "INSERT INTO company(name) values(?)";
  private static final String LIST_ASC = "SELECT id, name FROM company ORDER BY name ASC LIMIT ? OFFSET ?";
  private static final String LIST_DESC = "SELECT id, name FROM company ORDER BY name DESC LIMIT ? OFFSET ?";
  private static final String SEARCH_ASC = "SELECT id, name FROM company WHERE name LIKE ? ORDER BY name ASC LIMIT ? OFFSET ?";
  private static final String SEARCH_DESC = "SELECT id, name FROM company WHERE name LIKE ? ORDER BY name DESC LIMIT ? OFFSET ?";
  private static final String LIST_ALL = "SELECT id, name FROM company";
  private static final String DELETE = "DELETE FROM company WHERE id = ?";
  private static final String DELETE_COMPUTERS = "DELETE FROM computer WHERE company_id = ?";
  private Logger logger;

  /**
   * CompanyFactory contient les m�thodes sp�cifiques de la table company.
   * @throws SQLException SQLException
   */
  private CompanyDAOFactory() throws SQLException {
    this.logger = LoggerFactory.getLogger(CompanyDAOFactory.class);
  }

  /**
   * M�thode qui retourne l'instance unique de la classe CompanyFactory.
   * @return l'instance de la classe CompanyFactory
   * @throws SQLException SQLException
   */
  public static CompanyDAOFactory getInstance() throws SQLException {
    if (CompanyDAOFactory.instance == null) {
      CompanyDAOFactory.instance = new CompanyDAOFactory();
    }
    return CompanyDAOFactory.instance;
  }

  /**
   * Retourne le nombre de lignes dans la table company.
   * @param search le paramètre de la recherche
   * @return nombre le nombre de ligne
   * @throws SQLException SQLException
   */
  public int countCompanies(String search) throws SQLException {
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
   * Liste les companies contenues dans la table company.
   * @param nombre le nombre de résultats à afficher
   * @param offset l'offset pour la requète sql
   * @param search le paramètre de la recherche
   * @param sort le sens de triage
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> listCompanies(int nombre, int offset, String search, String sort)
      throws SQLException {
    ArrayList<Company> companies = new ArrayList<Company>();
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
        stmt.setInt(2, nombre);
        stmt.setInt(3, offset);
      }
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        Company company = new Company();
        company.setId(Integer.parseInt(rs.getString("id")));
        company.setName(rs.getString("name"));
        companies.add(company);
      }
    } catch (Exception e) {
      this.logger.error(e.toString());
    }
    return companies;
  }

  /**
   * Liste toutes les companies contenues dans la table company.
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> listCompaniesAll() throws SQLException {
    ArrayList<Company> companies = new ArrayList<Company>();
    try (DAOFactory factory = new DAOFactory()) {
      PreparedStatement stmt = factory.getConnection().prepareStatement(LIST_ALL);
      ResultSet rs = stmt.executeQuery();
      while (rs.next()) {
        Company company = new Company();
        company.setId(Integer.parseInt(rs.getString("id")));
        company.setName(rs.getString("name"));
        companies.add(company);
      }
    } catch (Exception e) {
      this.logger.error(e.toString());
    }
    return companies;
  }

  /**
   * Affiche les informations d'une companie contenu dans la table company.
   * @param id l'id de la compagnie à afficher
   * @return companies la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> showCompanyDetails(String id) throws SQLException {
    ArrayList<Company> companies = new ArrayList<Company>();
    try (DAOFactory factory = new DAOFactory()) {
      PreparedStatement stmt = factory.getConnection().prepareStatement(SHOW);
      stmt.setString(1, id);
      ResultSet rs = stmt.executeQuery();
      String[] champs = {"id", "name"};
      while (rs.next()) {
        Company company = new Company();
        for (int i = 0; i < champs.length; i++) {
          if (rs.getString(champs[i]) != null) {
            switch (champs[i]) {
            case "id":
              company.setId(Integer.parseInt(rs.getString(champs[i])));
              break;
            case "name":
              company.setName(rs.getString(champs[i]));
              break;
            default:
              break;
            }
          }
        }
        companies.add(company);
      }
    } catch (Exception e) {
      this.logger.error(e.toString());
    }
    return companies;
  }

  /**
   * Ajoute une compagnie dans la table company.
   * @param name         le nom de la compagnie à ajouter
   * @throws SQLException             SQLException
   * @throws IllegalArgumentException IllegalArgumentException
   */
  public void createCompany(String name) throws SQLException, IllegalArgumentException {
    try (DAOFactory factory = new DAOFactory()) {
      PreparedStatement stmt = factory.getConnection().prepareStatement(CREATE);
      if ("".equals(name)) {
        stmt.setString(1, null);
      } else {
        stmt.setString(1, name);
      }
      stmt.executeUpdate();
    } catch (Exception e) {
      this.logger.error(e.toString());
    }
  }

  /**
   * Met à jour une compagnie dans la table company.
   * @param id           l'id de la compagnie à mettre à jour
   * @param name         le nom de la compagnie à mettre à jour
   * @param champs       les champs qui sont prises en compte par la mise à jour
   * @throws SQLException SQLException
   */
  public void updateCompany(String id, String name, ArrayList<String> champs) throws SQLException {
    StringBuilder query = new StringBuilder("UPDATE company SET ");
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
   * Supprime une companie de la table company.
   * @param id l'id de la compagnie à supprimer
   * @throws SQLException SQLException
   */
  public void deleteCompany(String id) throws SQLException {
    Connection conn = null;
    try (DAOFactory factory = new DAOFactory()) {
      conn = factory.getConnection();
      conn.setAutoCommit(false);
      PreparedStatement stmtComputers = conn.prepareStatement(DELETE_COMPUTERS);
      stmtComputers.setString(1, id);
      stmtComputers.executeUpdate();
      PreparedStatement stmt = conn.prepareStatement(DELETE);
      stmt.setString(1, id);
      stmt.executeUpdate();
      conn.commit();
      conn.setAutoCommit(true);
    } catch (Exception e) {
      conn.rollback();
      this.logger.error(e.toString());
    }
  }
}
