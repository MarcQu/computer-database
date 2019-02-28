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
  private static final String SHOW = "SELECT id, name FROM company WHERE id = ?";
  private static final String CREATE = "INSERT INTO company(name) values(?)";
  private static final String LIST = "SELECT id, name FROM company ORDER BY name ASC LIMIT ? OFFSET ?";
  private static final String SEARCH = "SELECT id, name FROM company WHERE name LIKE ? ORDER BY name ASC LIMIT ? OFFSET ?";
  private static final String LIST_ALL = "SELECT id, name FROM company ORDER BY name ASC";

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
      if (search == null) {
        stmt = factory.getConnection().prepareStatement(COUNT_ALL);
      } else {
        stmt = factory.getConnection().prepareStatement(COUNT);
        stmt.setString(1, new StringBuilder("%").append(search).append("%").toString());
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
      if (search == null) {
        stmt = factory.getConnection().prepareStatement(LIST);
        stmt.setInt(1, nombre);
        stmt.setInt(2, offset);
      } else {
        stmt = factory.getConnection().prepareStatement(SEARCH);
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
   * Affiche les informations d'une companie contenu dans la table company.
   * @param numero l'id de la compagnie à afficher
   * @return companies la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> showCompanyDetails(String numero) throws SQLException {
    ArrayList<Company> companies = new ArrayList<Company>();
    try (DAOFactory factory = new DAOFactory()) {
      PreparedStatement stmt = factory.getConnection().prepareStatement(SHOW);
      stmt.setString(1, numero);
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
      e.printStackTrace();
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
      e.printStackTrace();
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
      e.printStackTrace();
    }
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
