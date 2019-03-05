package controler;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Company;
import model.Computer;
import service.CompanyService;
import service.ComputerService;

public class Controler {
  private static Controler instance = null;
  private CompanyService companyService;
  private ComputerService computerService;

  /**
   * Constructeur vide privée classe Controler.
   * @throws SQLException SQLException
   */
  private Controler() throws SQLException {
    this.companyService = CompanyService.getInstance();
    this.computerService = ComputerService.getInstance();
  }

  /**
   * Méthode qui retourne l'instance unique de la classe Controler.
   * @return l'instance de la classe Controler
   * @throws SQLException SQLException
   */
  public static Controler getInstance() throws SQLException {
    if (Controler.instance == null) {
      Controler.instance = new Controler();
    }
    return Controler.instance;
  }

  /**
   * Retourne le nombre de lignes dans la table company.
   * @param search le paramètre de la recherche
   * @return nombre le nombre de ligne
   * @throws SQLException SQLException
   */
  public int countCompanies(String search) throws SQLException {
    return this.companyService.countCompanies(search);
  }

  /**
   * Liste certraines companies contenues dans la table company.
   * @param nombre le nombre de résultats à afficher
   * @param offset l'offset pour la requète sql
   * @param search le paramètre de la recherche
   * @param sort le sens de triage
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> listCompanies(int nombre, int offset, String search, String sort)
      throws SQLException {
    return this.companyService.listCompanies(nombre, offset, search, sort);
  }

  /**
   * Liste toutes les companies contenues dans la table company.
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> listCompaniesAll() throws SQLException {
    return this.companyService.listCompaniesAll();
  }

  /**
   * Affiche les informations d'une companie contenu dans la table company.
   * @param numero l'id de la compagnie à afficher
   * @return companies la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Company> showCompanyDetails(String numero) throws SQLException {
    return this.companyService.showCompanyDetails(numero);
  }

  /**
   * Ajoute une compagnie dans la table company.
   * @param name         le nom de la compagnie à ajouter
   * @throws SQLException             SQLException
   * @throws IllegalArgumentException IllegalArgumentException
   */
  public void createCompany(String name) throws SQLException, IllegalArgumentException {
    this.companyService.createCompany(name);
  }

  /**
   * Met à jour une compagnie dans la table company.
   * @param id           l'id de la compagnie à mettre à jour
   * @param name         le nom de la compagnie à mettre à jour
   * @param champs       les champs qui sont prises en compte par la mise à jour
   * @throws SQLException SQLException
   */
  public void updateCompany(String id, String name, ArrayList<String> champs) throws SQLException {
    this.companyService.updateCompany(id, name, champs);
  }

  /**
   * Supprime une companie de la table company.
   * @param id l'id de la compagnie à supprimer
   * @throws SQLException SQLException
   */
  public void deleteCompany(String id) throws SQLException {
    this.companyService.deleteCompany(id);
  }

  /**
   * Retourne le nombre de lignes dans la table computer.
   * @param search le paramètre de la recherche
   * @return nombre le nombre de ligne
   * @throws SQLException SQLException
   */
  public int countComputers(String search) throws SQLException {
    return this.computerService.countComputers(search);
  }

  /**
   * Liste certains ordinateurs contenus dans la table computer.
   * @param nombre le nombre de résultats à afficher
   * @param offset l'offset pour la requète sql
   * @param search le paramètre de la recherche
   * @param sort le sens de triage
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Computer> listComputers(int nombre, int offset, String search, String sort)
      throws SQLException {
    return this.computerService.listComputers(nombre, offset, search, sort);
  }

  /**
   * Liste tous les ordinateurs contenus dans la table computer.
   * @param champs les champs de la table à afficher
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Computer> listComputersAll(ArrayList<String> champs) throws SQLException {
    return this.computerService.listComputersAll(champs);
  }

  /**
   * Affiche les informations d'un ordinateur contenu dans la table computer.
   * @param numero l'id de l'ordinateur à afficher
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Computer> showComputerDetails(String numero) throws SQLException {
    return this.computerService.showComputerDetails(numero);
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
    this.computerService.createComputer(name, introduced, discontinued, companyId);
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
    this.computerService.updateComputer(id, name, introduced, discontinued, companyId,
        champs);
  }

  /**
   * Supprime un ordinateur de la table computer.
   * @param id           l'id de l'ordinateur à supprimer
   * @throws SQLException SQLException
   */
  public void deleteComputer(String id) throws SQLException {
    this.computerService.deleteComputer(id);
  }
}
