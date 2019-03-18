package controller;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import dto.CompanyTO;
import exception.EmptyNameException;
import exception.SpecialCharacterException;
import model.Company;
import service.CompanyService;

@Controller
@RequestMapping("/UpdateCompany")
public class UpdateCompany {
  public static final String VUE = "updateCompany";
  private Logger logger;
  private CompanyService companyService;

  /**
   * Initialise les instances.
   * @param companyService le service
   */
  public UpdateCompany(CompanyService companyService) {
    this.logger = LoggerFactory.getLogger(UpdateCompany.class);
    this.companyService = companyService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String doGet(@RequestParam("companyId") String id, @RequestParam("search") String search, @RequestParam("sort") String sort, @RequestParam("nombre") int nombre, @RequestParam("page") int page, Model model) {
    CompanyTO companyTO = new CompanyTO();
    companyTO.setId(id);
    try {
      Company company = this.companyService.showDetails(companyTO).get(0);
      model.addAttribute("companyId", id);
      model.addAttribute("companyName", company.getName());
      model.addAttribute("nombre", nombre);
      model.addAttribute("page", page);
      model.addAttribute("search", search);
      model.addAttribute("sort", sort);
    } catch (SQLException e) {
      this.logger.error(e.toString());
    }
    return VUE;
  }

  @RequestMapping(method = RequestMethod.POST)
  public String doPost(@RequestParam("companyId") String id, @RequestParam("companyName") String name, @RequestParam("search") String search, @RequestParam("sort") String sort, @RequestParam("nombre") int nombre, @RequestParam("page") int page, Model model) {
    CompanyTO companyTO = new CompanyTO();
    companyTO.setId(id);
    companyTO.setName(name);
    try {
      this.companyService.update(companyTO);
      displayInformation(model, companyTO, search, sort, nombre, page);
      model.addAttribute("success", "Succès de la mise à jour");
    } catch (SQLException e) {
      this.logger.error(e.toString());
    } catch (EmptyNameException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(model, companyTO, search, sort, nombre, page);
        model.addAttribute("errorName", error);
        this.logger.error(e.toString());
      } catch (SQLException e1) {
        this.logger.error(e1.toString());
      }
    } catch (SpecialCharacterException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(model, companyTO, search, sort, nombre, page);
        model.addAttribute("error", error);
        this.logger.error(e.toString());
      } catch (SQLException e1) {
        this.logger.error(e1.toString());
      }
    }
    return VUE;
  }

  /**
   * Réaffiche les informations de la compagnie.
   * @param model le model de la page
   * @param companyTO la compagnie à afficher
   * @param nombre le nombre d'éléments affichés
   * @param page le numéro de la page
   * @param search la recherche
   * @param sort le trie
   * @throws SQLException SQLException
   */
  private void displayInformation(Model model, CompanyTO companyTO, String search, String sort, int nombre, int page) throws SQLException {
    Company company = this.companyService.showDetails(companyTO).get(0);
    model.addAttribute("companyId", company.getId());
    model.addAttribute("companyName", company.getName());
    model.addAttribute("nombre", nombre);
    model.addAttribute("page", page);
    model.addAttribute("search", search);
    model.addAttribute("sort", sort);
  }
}
