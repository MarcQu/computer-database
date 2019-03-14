package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
  private static CompanyService instance = null;
  @Autowired
  private CompanyDAO companyFactory;
  @Autowired
  private ValidatorCompany validator;
  @Autowired
  private CompanyMapper mapper;
  /**
   * Constructeur vide de la classe CompanyService.
   * @throws SQLException SQLException
   */
  private CompanyService() throws SQLException {
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
  public List<CompanyTO> listCompanies(int nombre, int offset, String search, String sort)
      throws SQLException {
    return mapper.getCompanyTO(this.companyFactory.listCompanies(nombre, offset, search, sort));
  }

  /**
   * Liste toutes les companies contenues dans la table company.
   * @return retour la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<CompanyTO> listCompaniesAll() throws SQLException {
    return mapper.getCompanyTO(this.companyFactory.listCompaniesAll());
  }

  /**
   * Affiche les informations d'une companie contenu dans la table company.
   * @param companyTO la compagnie à afficher
   * @return companies la liste des resultats de la requète
   * @throws SQLException SQLException
   */
  public List<Company> showCompanyDetails(CompanyTO companyTO) throws SQLException {
    return this.companyFactory.showCompanyDetails(mapper.getCompany(companyTO));
  }

  /**
   * Ajoute une compagnie dans la table company.
   * @param companyTO la compagnie à afficher
   * @throws SQLException SQLException
   * @throws IllegalArgumentException IllegalArgumentException
   * @throws EmptyNameException EmptyNameException
   * @throws SpecialCharacterException SpecialCharacterException
   */
  public void createCompany(CompanyTO companyTO) throws SQLException, IllegalArgumentException, EmptyNameException, SpecialCharacterException {
    validator.validateEmptyName(companyTO.getName());
    validator.validateSpecialCharaterName(companyTO.getName());
    this.companyFactory.createCompany(mapper.getCompany(companyTO));
  }

  /**
   * Met à jour une compagnie dans la table company.
   * @param companyTO la compagnie à afficher
   * @param champs les champs qui sont prises en compte par la mise à jour
   * @throws SQLException SQLException
   * @throws EmptyNameException EmptyNameException
   * @throws SpecialCharacterException SpecialCharacterException
   */
  public void updateCompany(CompanyTO companyTO, ArrayList<String> champs) throws SQLException, EmptyNameException, SpecialCharacterException {
    validator.validateEmptyName(companyTO.getName());
    validator.validateSpecialCharaterId(companyTO.getId());
    validator.validateSpecialCharaterName(companyTO.getName());
    this.companyFactory.updateCompany(mapper.getCompany(companyTO), champs);
  }

  /**
   * Supprime une companie de la table company.
   * @param companyTO la compagnie à afficher
   * @throws SQLException SQLException
   */
  public void deleteCompany(CompanyTO companyTO) throws SQLException {
    this.companyFactory.deleteCompany(mapper.getCompany(companyTO));
  }
}
