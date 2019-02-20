package service;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.CompanyFactory;
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
   * Liste certaines companies contenues dans la table company.
   * @param champs les champs de la table à afficher
   * @param nombre le nombre de résultats à afficher
   * @param offset l'offset pour la requète sql
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> listCompanies(int nombre, int offset, ArrayList<String> champs)
      throws SQLException {
    return CompanyFactory.getInstance().listCompanies(nombre, offset, champs);
  }

  /**
   * Liste toutes les companies contenues dans la table company.
   * @param champs les champs de la table à afficher
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> listCompaniesAll(ArrayList<String> champs) throws SQLException {
    return CompanyFactory.getInstance().listCompaniesAll(champs);
  }
}
