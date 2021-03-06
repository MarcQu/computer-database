package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import dto.CompanyTO;
import model.Company;

@Component
public class CompanyMapper implements RowMapper<Company> {
  /**
   * Récupère les DTOs.
   * @param companies les compagnies
   * @return companiesTO les DTOs
   */
  public List<CompanyTO> getCompanyTO(List<Company> companies) {
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
  private List<CompanyTO> createCompanyTO(List<Company> companies) {
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

  @Override
  public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
    Company company = new Company();
    company.setId(rs.getInt("id"));
    company.setName(rs.getString("name"));
    return company;
  }
}
