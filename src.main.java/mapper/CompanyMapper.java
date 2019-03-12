package mapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

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
   * @param companyTO la DTO
   * @return company la compagnie
   */
  public Company getCompany(CompanyTO companyTO) {
    return createCompany(companyTO);
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
   * @param companyTO la DTO
   * @return company la compagnie
   */
  private Company createCompany(CompanyTO companyTO) {
    Company company = new Company();
    Optional<String> optionalId = Optional.ofNullable(companyTO.getId());
    Optional<String> optionalName = Optional.ofNullable(companyTO.getName());
    if (optionalId.isPresent() && !"".equals(companyTO.getId())) {
      company.setId(Integer.parseInt(companyTO.getId()));
    }
    if (optionalName.isPresent() && !"".equals(companyTO.getName())) {
      company.setName(companyTO.getName());
    }
    return company;
  }
}
