package service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Service;

import dao.CompanyDAO;
import dto.CompanyTO;
import exception.EmptyNameException;
import exception.SpecialCharacterException;
import mapper.CompanyMapper;
import model.Company;
import validator.ValidatorCompany;

@Service
public class CompanyService {
  private CompanyDAO companyDAO;
  private ValidatorCompany validator;
  private CompanyMapper mapper;
  /**
   * Constructeur de la classe CompanyService.
   * @param companyDAO la dao
   * @param validator le validator
   * @param mapper le mapper
   * @throws SQLException SQLException
   */
  public CompanyService(CompanyDAO companyDAO, ValidatorCompany validator, CompanyMapper mapper) throws SQLException {
    this.companyDAO = companyDAO;
    this.validator = validator;
    this.mapper = mapper;
  }

  /**
   * Retourne le nombre de lignes dans la table company.
   * @param search le paramètre de la recherche
   * @return nombre le nombre de ligne
   * @throws SQLException SQLException
   */
  public int count(String search) throws SQLException {
    return this.companyDAO.count(search);
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
  public List<CompanyTO> list(int nombre, int offset, String search, String sort)
      throws SQLException {
    return this.mapper.getCompanyTO(this.companyDAO.list(nombre, offset, search, sort));
  }

  /**
   * Liste toutes les companies contenues dans la table company.
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<CompanyTO> listAll() throws SQLException {
    return this.mapper.getCompanyTO(this.companyDAO.listAll());
  }

  /**
   * Affiche les informations d'une companie contenu dans la table company.
   * @param companyTO la compagnie à afficher
   * @return companies la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<CompanyTO> showDetails(Integer id) throws SQLException {
    return this.mapper.getCompanyTO(this.companyDAO.showDetails(id));
  }

  /**
   * Ajoute une compagnie dans la table company.
   * @param companyTO la compagnie à afficher
   * @throws SQLException SQLException
   * @throws IllegalArgumentException IllegalArgumentException
   * @throws EmptyNameException EmptyNameException
   * @throws SpecialCharacterException SpecialCharacterException
   */
  public void create(CompanyTO companyTO) throws SQLException, IllegalArgumentException, EmptyNameException, SpecialCharacterException {
    this.validator.validate(companyTO);
    this.companyDAO.create(this.mapper.getCompany(companyTO));
  }

  /**
   * Met à jour une compagnie dans la table company.
   * @param companyTO la compagnie à afficher
   * @throws SQLException SQLException
   * @throws EmptyNameException EmptyNameException
   * @throws SpecialCharacterException SpecialCharacterException
   */
  public void update(CompanyTO companyTO) throws SQLException, EmptyNameException, SpecialCharacterException {
    this.validator.validate(companyTO);
    this.validator.validateSpecialCharaterId(companyTO.getId());
    this.companyDAO.update(this.mapper.getCompany(companyTO));
  }

  /**
   * Supprime une companie de la table company.
   * @param companyTO la compagnie à afficher
   * @throws SQLException SQLException
   */
  public void delete(CompanyTO companyTO) throws SQLException {
    this.companyDAO.delete(this.mapper.getCompany(companyTO));
  }
}
