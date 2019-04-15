package controller;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import mapper.CompanyMapper;
import model.Company;
import service.CompanyService;
import service.ComputerService;

@Controller
@RequestMapping("/CreateComputer")
public class CreateComputer {
  public static final String VUE = "createComputer";
  private Logger logger;
  private ComputerService computerService;
  private CompanyService companyService;
  private CompanyMapper companyMapper;

  /**
   * Initialise les instances.
   * @param computerService le service de computer
   * @param companyService le service de company
   */
  public CreateComputer(ComputerService computerService, CompanyService companyService, CompanyMapper companyMapper) {
    this.logger = LoggerFactory.getLogger(CreateComputer.class);
    this.computerService = computerService;
    this.companyService = companyService;
    this.companyMapper = companyMapper;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String doGet(Model model) {
    try {
      List<CompanyTO> companies = this.companyService.listAll();
      model.addAttribute("companies", companies);
    } catch (SQLException e) {
      this.logger.error(e.toString());
    }
    return VUE;
  }

  @RequestMapping(method = RequestMethod.POST)
  public String doPost(@RequestParam("computerName") String name, @RequestParam("introduced") String introduced, @RequestParam("discontinued") String discontinued, @RequestParam("companyId") Integer companyId, Model model) {
    ComputerTO computerTO = new ComputerTO();
    computerTO.setName(name);
    computerTO.setIntroduced(introduced);
    computerTO.setDiscontinued(discontinued);
    try {
      List<CompanyTO> companyTO = this.companyService.showDetails(companyId);
      if (companyTO.size() > 0) {
        computerTO.setCompany(this.companyMapper.getCompany(companyTO.get(0)));
      }
      List<CompanyTO> companies = this.companyService.listAll();
      model.addAttribute("companies", companies);
      this.computerService.create(computerTO);
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
