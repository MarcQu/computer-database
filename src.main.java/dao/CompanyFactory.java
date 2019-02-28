package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Company;
import dto.CompanyTO;

public class CompanyFactory {
  private static CompanyFactory instance = null;
  private static final String COUNT_ALL = "SELECT COUNT(id) AS rowcount FROM company";
  private static final String COUNT = "SELECT COUNT(id) AS rowcount FROM company WHERE name like ?";
  private static final String LIST = "SELECT id, name FROM company LIMIT ? OFFSET ?";
  private static final String SEARCH = "SELECT id, name FROM company WHERE name LIKE ? LIMIT ? OFFSET ?";
  private static final String LIST_ALL = "SELECT id, name FROM company";

  /**
   * CompanyFactory contient les méthodes spécifiques à la table company.
   * @throws SQLException SQLException
   */
  private CompanyFactory() throws SQLException {
  }

  /**
   * Méthode qui retourne l'instance unique de la classe CompanyFactory.
   * @return l'instance de la classe CompanyFactory
   * @throws SQLException SQLException
   */
  public static CompanyFactory getInstance() throws SQLException {
    if (CompanyFactory.instance == null) {
      CompanyFactory.instance = new CompanyFactory();
    }
    return CompanyFactory.instance;
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
      if ("%null%".equals(search)) {
        stmt = factory.getConnection().prepareStatement(COUNT_ALL);
      } else {
        stmt = factory.getConnection().prepareStatement(COUNT);
        stmt.setString(1, search);
      }
      ResultSet rs = stmt.executeQuery();
      rs.next();
      nombre = rs.getInt("rowcount");
    } catch (Exception e) {
      e.printStackTrace();
    }
    return nombre;
  }

  /**
   * Liste les companies contenues dans la table company.
   * @param nombre le nombre de résultats à afficher
   * @param offset l'offset pour la requète sql
   * @param search le paramètre de la recherche
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> listCompanies(int nombre, int offset, String search)
      throws SQLException {
    ArrayList<Company> companies = new ArrayList<Company>();
    try (DAOFactory factory = new DAOFactory()) {
      PreparedStatement stmt;
      if ("%null%".equals(search)) {
        stmt = factory.getConnection().prepareStatement(LIST);
        stmt.setInt(1, nombre);
        stmt.setInt(2, offset);
      } else {
        stmt = factory.getConnection().prepareStatement(SEARCH);
        stmt.setString(1, search);
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
      e.printStackTrace();
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
      e.printStackTrace();
    }
    return companies;
  }

  /**
   * Récupère la DTO.
   * @param companies les compagnies
   * @return companiesTO les DTOs
   */
  public ArrayList<CompanyTO> getCompanyData(ArrayList<Company> companies) {
    return createCompanyTO(companies);
  }

  /**
   * Crée la DTO.
   * @param companies les com
   * @return companiesTO les DTOs
   */
  private ArrayList<CompanyTO> createCompanyTO(ArrayList<Company> companies) {
    ArrayList<CompanyTO> companiesTO = new ArrayList<CompanyTO>();
    for (Company company : companies) {
      CompanyTO companyTO = new CompanyTO(company);
      companiesTO.add(companyTO);
    }
      return companiesTO;
  }
}
