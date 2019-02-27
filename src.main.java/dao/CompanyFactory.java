package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Company;
import dto.CompanyTO;

public class CompanyFactory {
  private static CompanyFactory instance = null;
  private static final String COUNT = "SELECT COUNT(id) AS rowcount FROM company";
  private static final String LIST = "SELECT id, name FROM company LIMIT ? OFFSET ?";
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
   * @return nombre le nombre de ligne
   * @throws SQLException SQLException
   */
  public int countCompanies() throws SQLException {
    int nombre = 0;
    try (DAOFactory factory = new DAOFactory()) {
      Statement stmt = factory.getConnection().createStatement();
      ResultSet rs = stmt.executeQuery(COUNT);
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
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> listCompanies(int nombre, int offset)
      throws SQLException {
    ArrayList<Company> companies = new ArrayList<Company>();
    try (DAOFactory factory = new DAOFactory()) {
      PreparedStatement stmt = factory.getConnection().prepareStatement(LIST);
      stmt.setInt(1, nombre);
      stmt.setInt(2, offset);
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
