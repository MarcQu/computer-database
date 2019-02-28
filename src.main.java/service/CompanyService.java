package service;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.CompanyFactory;
import dto.CompanyTO;
import model.Company;

public class CompanyService {
  private static CompanyService instance = null;

  /**
   * Constructeur vide de la classe CompanyService.
   */
  private CompanyService() {
  }

  /**
   * Méthode qui retourne l'instance unique de la classe CompanyService.
   * @return l'instance de la classe CompanyService
   * @throws SQLException
   */
  public static CompanyService getInstance() {
    if (CompanyService.instance == null) {
      CompanyService.instance = new CompanyService();
    }
    return CompanyService.instance;
  }

  /**
   * Retourne le nombre de lignes dans la table company.
   * @param search le paramètre de la recherche
   * @return nombre le nombre de ligne
   * @throws SQLException SQLException
   */
  public int countCompanies(String search) throws SQLException {
    return CompanyFactory.getInstance().countCompanies(search);
  }

  /**
   * Liste certaines companies contenues dans la table company.
   * @param nombre le nombre de résultats à afficher
   * @param offset l'offset pour la requète sql
   * @param search le paramètre de la recherche
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> listCompanies(int nombre, int offset, String search)
      throws SQLException {
    return CompanyFactory.getInstance().listCompanies(nombre, offset, search);
  }

  /**
   * Liste toutes les companies contenues dans la table company.
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> listCompaniesAll() throws SQLException {
    return CompanyFactory.getInstance().listCompaniesAll();
  }

  /**
   * Ajoute une compagnie dans la table company.
   * @param name         le nom de la compagnie à ajouter
   * @throws SQLException             SQLException
   * @throws IllegalArgumentException IllegalArgumentException
   */
  public void createCompany(String name) throws SQLException, IllegalArgumentException {
    CompanyFactory.getInstance().createCompany(name);
  }

  /**
   * Récupère la DTO.
   * @param companies les compagnies
   * @return companiesTO les DTOs
   * @throws SQLException SQLException
   */
  public ArrayList<CompanyTO> getCompanyData(ArrayList<Company> companies) throws SQLException {
    return CompanyFactory.getInstance().getCompanyData(companies);
  }
}
