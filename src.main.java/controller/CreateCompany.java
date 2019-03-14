package controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dto.CompanyTO;
import exception.EmptyNameException;
import exception.SpecialCharacterException;
import service.CompanyService;

@Controller
@RequestMapping("/CreateCompany")
public class CreateCompany {
  public static final String VUE = "createCompany";
  private Logger logger;

  @Autowired
  private CompanyService companyService;

  /**
   * Initialise les instances.
   */
  public void init() {
    this.logger = LoggerFactory.getLogger(CreateCompany.class);
  }

  @RequestMapping(method = RequestMethod.GET)
  public String doGet(Model model) {
    return VUE;
  }

  @RequestMapping(method = RequestMethod.POST)
  public String doPost(@RequestParam("companyName") String name, Model model) {
    init();
    CompanyTO companyTO = new CompanyTO();
    companyTO.setName(name);
    try {
      companyService.createCompany(companyTO);
      model.addAttribute("success", "Succès de la création");
    } catch (IllegalArgumentException e) {
      this.logger.error(e.toString());
    } catch (SQLException e) {
      this.logger.error(e.toString());
    } catch (EmptyNameException e) {
      String error = e.toString().split(": ")[1];
      model.addAttribute("errorName", error);
      this.logger.error(e.toString());
    } catch (SpecialCharacterException e) {
      String error = e.toString().split(": ")[1];
      model.addAttribute("errorName", error);
      this.logger.error(e.toString());
    }
    return VUE;
  }
}
