package service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.ComputerDAO;
import dto.ComputerTO;
import exception.DateFormatException;
import exception.DatePrecedenceException;
import exception.EmptyNameException;
import exception.SpecialCharacterException;
import mapper.ComputerMapper;
import model.Computer;
import validator.ValidatorComputer;

@Service
public class ComputerService {
  @Autowired
  private ComputerDAO computerFactory;
  @Autowired
  private ValidatorComputer validator;
  @Autowired
  private ComputerMapper mapper;
  /**
   * Constructeur vide de la classe CompanyService.
   * @throws SQLException SQLException
   */
  private ComputerService() throws SQLException {
  }

  /**
   * Retourne le nombre de lignes dans la table computer.
   * @param search le paramètre de la recherche
   * @return nombre le nombre de ligne
   * @throws SQLException SQLException
   */
  public int countComputers(String search) throws SQLException {
    return this.computerFactory.countComputers(search);
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
  public List<ComputerTO> listComputers(int nombre, int offset, String search, String sort)
      throws SQLException {
    return mapper.getComputerTO(this.computerFactory.listComputers(nombre, offset, search, sort));
  }

  /**
   * Affiche les informations d'un ordinateur contenu dans la table computer.
   * @param computerTO l'ordinateur à afficher
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<Computer> showComputerDetails(ComputerTO computerTO) throws SQLException {
    return this.computerFactory.showComputerDetails(mapper.getComputer(computerTO));
  }

  /**
   * Ajoute un ordinateur dans la table computer.
   * @param computerTO l'ordinateur à ajouter
   * @throws SQLException SQLException
   * @throws IllegalArgumentException IllegalArgumentException
   * @throws EmptyNameException EmptyNameException
   * @throws SpecialCharacterException SpecialCharacterException
   * @throws DatePrecedenceException DatePrecedenceException
   * @throws DateFormatException DateFormatException
   */
  public void createComputer(ComputerTO computerTO)
      throws SQLException, IllegalArgumentException, EmptyNameException, SpecialCharacterException, DatePrecedenceException, DateFormatException {
    validator.validateEmptyName(computerTO.getName());
    validator.validateIntroducedFormat(computerTO.getIntroduced());
    validator.validateDiscontinuedFormat(computerTO.getDiscontinued());
    validator.validateSpecialCharaterName(computerTO.getName());
    validator.validateSpecialCharaterIntroduced(computerTO.getIntroduced());
    validator.validateSpecialCharaterDiscontinued(computerTO.getDiscontinued());
    validator.validateSpecialCharaterCompanyId(computerTO.getCompany());
    validator.validateDatePrecedence(computerTO.getIntroduced(), computerTO.getDiscontinued());
    this.computerFactory.createComputer(mapper.getComputer(computerTO));
  }

  /**
   * Met à jour un ordinateur dans la table computer.
   * @param computerTO l'ordinateur à mettre à jour
   * @throws SQLException SQLException
   * @throws EmptyNameException EmptyNameException
   * @throws SpecialCharacterException SpecialCharacterException
   * @throws DatePrecedenceException DatePrecedenceException
   * @throws DateFormatException DateFormatException
   */
  public void updateComputer(ComputerTO computerTO) throws SQLException, EmptyNameException, SpecialCharacterException, DatePrecedenceException, DateFormatException {
    validator.validateEmptyName(computerTO.getName());
    validator.validateIntroducedFormat(computerTO.getIntroduced());
    validator.validateDiscontinuedFormat(computerTO.getDiscontinued());
    validator.validateSpecialCharaterName(computerTO.getName());
    validator.validateSpecialCharaterIntroduced(computerTO.getIntroduced());
    validator.validateSpecialCharaterDiscontinued(computerTO.getDiscontinued());
    validator.validateSpecialCharaterCompanyId(computerTO.getCompany());
    validator.validateDatePrecedence(computerTO.getIntroduced(), computerTO.getDiscontinued());
    this.computerFactory.updateComputer(mapper.getComputer(computerTO));
  }

  /**
   * Supprime un ordinateur de la table computer.
   * @param computerTO l'ordinateur à supprimer
   * @throws SQLException SQLException
   */
  public void deleteComputer(ComputerTO computerTO) throws SQLException {
    this.computerFactory.deleteComputer(mapper.getComputer(computerTO));
  }
}
