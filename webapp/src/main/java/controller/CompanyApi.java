package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dto.CompanyTO;
import model.Company;
import service.CompanyService;

@RestController
@RequestMapping("/CompanyApi")
public class CompanyApi {
  private Logger logger;
  private CompanyService companyService;

  /**
   * Initialise les instances.
   * @param companyService le service
   */
  public CompanyApi(CompanyService companyService) {
    this.logger = LoggerFactory.getLogger(CompanyApi.class);
    this.companyService = companyService;
  }

  @RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
  public CompanyTO getCompany(@PathVariable Integer companyId) {
    Company company = new Company();
    company.setId(companyId);
    CompanyTO companyTO = new CompanyTO();
    try {
      companyTO = this.companyService.showDetails(companyId).get(0);
    } catch (SQLException e) {
      this.logger.error(e.toString());
    }
    return companyTO;
  }

  @RequestMapping(value = {"/{page}/{nombre}/{sort}/{search}", "/{page}/{nombre}/{sort}"}, method = RequestMethod.GET)
  public List<CompanyTO> getCompanies(@PathVariable int page, @PathVariable int nombre, @PathVariable Optional<String> search, @PathVariable String sort) {
    List<CompanyTO> companies = new ArrayList<>();
    try {
      if(search.isPresent()) {
        companies = this.companyService.list(nombre, nombre * (page - 1), search.get(), sort);
      } else {
        companies = this.companyService.list(nombre, nombre * (page - 1), "", sort);
      }
    } catch (SQLException e) {
      this.logger.error(e.toString());
    }
    return companies;
  }

  @RequestMapping(value = "/all", method = RequestMethod.GET)
  public List<CompanyTO> getAll() {
    List<CompanyTO> companies = new ArrayList<>();
    try {
      companies = this.companyService.listAll();
    } catch (SQLException e) {
      this.logger.error(e.toString());
    }
    return companies;
  }

  @RequestMapping(value = {"/count/{search}", "/count"}, method = RequestMethod.GET)
  public int getCount(@PathVariable Optional<String> search) {
    int count = 0;
    try {
      if(search.isPresent()) {
        count = this.companyService.count(search.get());
      } else {
        count = this.companyService.count("");
      }
    } catch (SQLException e) {
      this.logger.error(e.toString());
    }
    return count;
  }
}
