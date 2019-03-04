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
  public ArrayList<CompanyTO> getCompanyData(ArrayList<Company> companies) {
    return createCompanyTO(companies);
  }

  /**
   * Crée les DTOs.
   * @param companies les com
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
}
