package service;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.CompanyDAOFactory;
import model.Company;

public class CompanyService {
  private static CompanyService instance = null;
  private CompanyDAOFactory companyFactory;

  /**
   * Constructeur vide de la classe CompanyService.
   * @throws SQLException SQLException
   */
  private CompanyService() throws SQLException {
    this.companyFactory = CompanyDAOFactory.getInstance();
  }

  /**
   * Méthode qui retourne l'instance unique de la classe CompanyService.
   * @return l'instance de la classe CompanyService
   * @throws SQLException SQLException
   */
  public static CompanyService getInstance() throws SQLException {
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
    return this.companyFactory.countCompanies(search);
  }

  /**
   * Liste certaines companies contenues dans la table company.
   * @param nombre le nombre de résultats à afficher
   * @param offset l'offset pour la requète sql
   * @param search le paramètre de la recherche
   * @param sort le sens de triage
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> listCompanies(int nombre, int offset, String search, String sort)
      throws SQLException {
    return this.companyFactory.listCompanies(nombre, offset, search, sort);
  }

  /**
   * Liste toutes les companies contenues dans la table company.
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> listCompaniesAll() throws SQLException {
    return this.companyFactory.listCompaniesAll();
  }

  /**
   * Affiche les informations d'une companie contenu dans la table company.
   * @param numero l'id de la compagnie à afficher
   * @return companies la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> showCompanyDetails(String numero) throws SQLException {
    return this.companyFactory.showCompanyDetails(numero);
  }

  /**
   * Ajoute une compagnie dans la table company.
   * @param name         le nom de la compagnie à ajouter
   * @throws SQLException             SQLException
   * @throws IllegalArgumentException IllegalArgumentException
   */
  public void createCompany(String name) throws SQLException, IllegalArgumentException {
    this.companyFactory.createCompany(name);
  }

  /**
   * Met à jour une compagnie dans la table company.
   * @param id           l'id de la compagnie à mettre à jour
   * @param name         le nom de la compagnie à mettre à jour
   * @param champs       les champs qui sont prises en compte par la mise à jour
   * @throws SQLException SQLException
   */
  public void updateCompany(String id, String name, ArrayList<String> champs) throws SQLException {
    this.companyFactory.updateCompany(id, name, champs);
  }

  /**
   * Supprime une companie de la table company.
   * @param id l'id de la compagnie à supprimer
   * @throws SQLException SQLException
   */
  public void deleteCompany(String id) throws SQLException {
    this.companyFactory.deleteCompany(id);
  }
}
