package mapper;

import java.sql.SQLException;
import java.util.ArrayList;

import dto.CompanyTO;
import model.Company;

public class CompanyMapper {
  private static CompanyMapper instance = null;

  /**
   * CompanyMapper contient les méthodes gérant les DTOs.
   * @throws SQLException SQLException
   */
  private CompanyMapper() {
  }

  /**
   * Méthode qui retourne l'instance unique de la classe CompanyMapper.
   * @return l'instance de la classe CompanyMapper
   * @throws SQLException SQLException
   */
  public static CompanyMapper getInstance() {
    if (CompanyMapper.instance == null) {
      CompanyMapper.instance = new CompanyMapper();
    }
    return CompanyMapper.instance;
  }

  /**
   * Récupère les DTOs.
   * @param companies les compagnies
   * @return companiesTO les DTOs
   */
  public ArrayList<CompanyTO> getCompanyTO(ArrayList<Company> companies) {
    return createCompanyTO(companies);
  }

  /**
   * Récupère les compagnies.
   * @param companiesTO les DTOs
   * @return companies les compagnies
   */
  public ArrayList<Company> getCompany(ArrayList<CompanyTO> companiesTO) {
    return createCompany(companiesTO);
  }

  /**
   * Crée les DTOs.
   * @param companies les compagnies
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

  /**
   * Crée les compagnies.
   * @param companiesTO les DTOs
   * @return companies les compagnies
   */
  private ArrayList<Company> createCompany(ArrayList<CompanyTO> companiesTO) {
    ArrayList<Company> companies = new ArrayList<Company>();
    for (CompanyTO companyTO : companiesTO) {
      Company company = new Company(companyTO.getId(), companyTO.getName());
      companies.add(company);
    }
      return companies;
  }
}
