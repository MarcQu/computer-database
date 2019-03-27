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
import service.CompanyService;

@Controller
@RequestMapping("/DeleteCompany")
public class DeleteCompany {
  public static final String VUE = "companyMenu";
  private Logger logger;
  private CompanyService companyService;

  /**
   * Initialise les instances.
   * @param companyService le service
   */
  public DeleteCompany(CompanyService companyService) {
    this.logger = LoggerFactory.getLogger(DeleteCompany.class);
    this.companyService = companyService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String doGet(@RequestParam("search") String search, @RequestParam("sort") String sort, @RequestParam("nombre") int nombre, @RequestParam("page") int page, @RequestParam("selected") String selected, Model model) {
    try {
      if (selected != null) {
        String[] selectedCompanies = selected.split(",");
        for (String id : selectedCompanies) {
          CompanyTO companyTO = new CompanyTO();
          companyTO.setId(id);
          this.companyService.delete(companyTO);
        }
      }

      int nombreCompanies = this.companyService.count(search);
      List<CompanyTO> companies = this.companyService.list(nombre, nombre * (page - 1), search, sort);
      model.addAttribute("nombreCompanies", nombreCompanies);
      model.addAttribute("companies", companies);

      int pages = nombreCompanies / nombre + 1;
      model.addAttribute("nombre", nombre);
      model.addAttribute("page", page);
      model.addAttribute("pages", pages);
      model.addAttribute("search", search);
      model.addAttribute("sort", sort);
    } catch (SQLException e) {
      this.logger.error(e.toString());
    }
    return VUE;
  }
}
