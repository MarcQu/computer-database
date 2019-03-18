package service;

import java.sql.SQLException;
import java.util.List;

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
  private ComputerDAO computerDAO;
  private ValidatorComputer validator;
  private ComputerMapper mapper;
  /**
   * Constructeur de la classe CompanyService.
   * @param computerDAO la dao
   * @param validator le validator
   * @param mapper le mapper
   * @throws SQLException SQLException
   */
  public ComputerService(ComputerDAO computerDAO, ValidatorComputer validator, ComputerMapper mapper) throws SQLException {
    this.computerDAO = computerDAO;
    this.validator = validator;
    this.mapper = mapper;
  }

  /**
   * Retourne le nombre de lignes dans la table computer.
   * @param search le paramètre de la recherche
   * @return nombre le nombre de ligne
   * @throws SQLException SQLException
   */
  public int count(String search) throws SQLException {
    return this.computerDAO.count(search);
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
  public List<ComputerTO> list(int nombre, int offset, String search, String sort)
      throws SQLException {
    return this.mapper.getComputerTO(this.computerDAO.list(nombre, offset, search, sort));
  }

  /**
   * Affiche les informations d'un ordinateur contenu dans la table computer.
   * @param computerTO l'ordinateur à afficher
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<Computer> showDetails(ComputerTO computerTO) throws SQLException {
    return this.computerDAO.showDetails(this.mapper.getComputer(computerTO));
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
  public void create(ComputerTO computerTO)
      throws SQLException, IllegalArgumentException, EmptyNameException, SpecialCharacterException, DatePrecedenceException, DateFormatException {
    this.validator.validate(computerTO);
    this.computerDAO.create(this.mapper.getComputer(computerTO));
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
  public void update(ComputerTO computerTO) throws SQLException, EmptyNameException, SpecialCharacterException, DatePrecedenceException, DateFormatException {
    this.validator.validate(computerTO);
    this.validator.validateSpecialCharaterId(computerTO.getId());
    this.computerDAO.update(this.mapper.getComputer(computerTO));
  }

  /**
   * Supprime un ordinateur de la table computer.
   * @param computerTO l'ordinateur à supprimer
   * @throws SQLException SQLException
   */
  public void delete(ComputerTO computerTO) throws SQLException {
    this.computerDAO.delete(this.mapper.getComputer(computerTO));
  }
}
