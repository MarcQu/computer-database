package controller;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dto.CompanyTO;
import dto.ComputerTO;
import exception.DateFormatException;
import exception.DatePrecedenceException;
import exception.EmptyNameException;
import exception.SpecialCharacterException;
import model.Company;
import service.CompanyService;
import service.ComputerService;

@Controller
@RequestMapping("/CreateComputer")
public class CreateComputer {
  public static final String VUE = "createComputer";
  private Logger logger;

  @Autowired
  private ComputerService computerService;

  @Autowired
  private CompanyService companyService;

  /**
   * Initialise les instances.
   */
  public void init() {
    this.logger = LoggerFactory.getLogger(CreateComputer.class);
  }

  @RequestMapping(method = RequestMethod.GET)
  public String doGet(Model model) {
    init();
    try {
      List<CompanyTO> companies = companyService.listCompaniesAll();
      model.addAttribute("companies", companies);
    } catch (SQLException e) {
      this.logger.error(e.toString());
    }
    return VUE;
  }

  @RequestMapping(method = RequestMethod.POST)
  public String doPost(@RequestParam("computerName") String name, @RequestParam("introduced") String introduced, @RequestParam("discontinued") String discontinued, @RequestParam("companyId") String companyId, Model model) {
    init();
    ComputerTO computerTO = new ComputerTO();
    computerTO.setName(name);
    computerTO.setIntroduced(introduced);
    computerTO.setDiscontinued(discontinued);
    CompanyTO companyTO = new CompanyTO();
    companyTO.setId(companyId);

    try {
      List<Company> company = companyService.showCompanyDetails(companyTO);
      if (company.size() > 0) {
        computerTO.setCompany(company.get(0));
      }
      List<CompanyTO> companies = companyService.listCompaniesAll();
      model.addAttribute("companies", companies);
      computerService.createComputer(computerTO);
      model.addAttribute("success", "Succès de la création");
    } catch (SQLException e) {
      this.logger.error(e.toString());
    } catch (IllegalArgumentException e) {
      this.logger.error(e.toString());
    } catch (EmptyNameException e) {
      String error = e.toString().split(": ")[1];
      model.addAttribute("errorName", error);
      this.logger.error(e.toString());
    } catch (SpecialCharacterException e) {
      String error = e.toString().split(": ")[1];
      model.addAttribute("error", error);
      this.logger.error(e.toString());
    } catch (DatePrecedenceException e) {
      String error = e.toString().split(": ")[1];
      model.addAttribute("errorDate", error);
      this.logger.error(e.toString());
    } catch (DateFormatException e) {
      String error = e.toString().split(": ")[1];
      model.addAttribute("errorDate", error);
      this.logger.error(e.toString());
    }
    return VUE;
  }
}
