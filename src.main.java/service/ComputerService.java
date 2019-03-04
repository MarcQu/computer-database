package service;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.ComputerFactory;
import dto.ComputerTO;
import model.Computer;

public class ComputerService {
  private static ComputerService instance  = null;

  /**
   * Méthode qui retourne l' unique de la classe ComputerService.
   * @return l' de la classe ComputerService
   * @throws SQLException
   */
  public static ComputerService getInstance() {
    if (ComputerService.instance == null) {
      ComputerService.instance = new ComputerService();
    }
    return ComputerService.instance;
  }

  /**
   * Retourne le nombre de lignes dans la table computer.
   * @param search le paramètre de la recherche
   * @return nombre le nombre de ligne
   * @throws SQLException SQLException
   */
  public int countComputers(String search) throws SQLException {
    return ComputerFactory.getInstance().countComputers(search);
  }

  /**
   * Liste cetrains ordinateurs contenus dans la table computer.
   * @param nombre le nombre de résultats à afficher
   * @param offset l'offset pour la requète sql
   * @param search le paramètre de la recherche
   * @param sort le sens de triage
   * @return le liste de certains ordinateurs
   * @throws SQLException SQLException
   */
  public ArrayList<Computer> listComputers(int nombre, int offset, String search, String sort)
      throws SQLException {
    return ComputerFactory.getInstance().listComputers(nombre, offset, search, sort);
  }

  /**
   * Liste tous les ordinateurs contenus dans la table computer.
   * @param champs les champs de la table à afficher
   * @return la liste de tous les ordinateurs
   * @throws SQLException SQLException
   */
  public ArrayList<Computer> listComputersAll(ArrayList<String> champs) throws SQLException {
    return ComputerFactory.getInstance().listComputersAll(champs);
  }

  /**
   * Affiche les informations d'un ordinateur contenu dans la table computer.
   * @param numero l'id de l'ordinateur � afficher
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public ArrayList<Computer> showComputerDetails(String numero) throws SQLException {
    return ComputerFactory.getInstance().showComputerDetails(numero);
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
    ComputerFactory.getInstance().createComputer(name, introduced, discontinued, companyId);
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
    ComputerFactory.getInstance().updateComputer(id, name, introduced, discontinued, companyId,
        champs);
  }

  /**
   * Supprime un ordinateur de la table computer.
   * @param id l'id de l'ordinateur à supprimer
   * @throws SQLException SQLException
   */
  public void deleteComputer(String id) throws SQLException {
    ComputerFactory.getInstance().deleteComputer(id);
  }

  /**
   * Récupère la DTO.
   * @param computers les ordinateurs
   * @return computersTO les DTOs
   * @throws SQLException SQLException
   */
  public ArrayList<ComputerTO> getComputerData(ArrayList<Computer> computers) throws SQLException {
    return ComputerFactory.getInstance().getComputerData(computers);
  }
}
