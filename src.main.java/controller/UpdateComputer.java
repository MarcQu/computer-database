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
import model.Company;
import model.Computer;
import service.CompanyService;
import service.ComputerService;

@Controller
@RequestMapping("/UpdateComputer")
public class UpdateComputer {
  public static final String VUE = "updateComputer";
  private Logger logger;
  private ComputerService computerService;
  private CompanyService companyService;

  /**
   * Initialise les instances.
   * @param computerService le service de computer
   * @param companyService le service de company
   */
  public UpdateComputer(ComputerService computerService, CompanyService companyService) {
    this.logger = LoggerFactory.getLogger(UpdateComputer.class);
    this.computerService = computerService;
    this.companyService = companyService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String updateComputer(@RequestParam("computerId") String id, @RequestParam("search") String search, @RequestParam("sort") String sort, @RequestParam("nombre") int nombre, @RequestParam("page") int page, Model model) {
    ComputerTO computerTO = new ComputerTO();
    computerTO.setId(id);
    try {
      Computer computer = this.computerService.showDetails(computerTO).get(0);
      List<CompanyTO> companies = this.companyService.listAll();
      model.addAttribute("computerId", id);
      model.addAttribute("computerName", computer.getName());
      model.addAttribute("introduced", computer.getIntroduced());
      model.addAttribute("discontinued", computer.getDiscontinued());
      model.addAttribute("companyComputer", computer.getCompany());
      model.addAttribute("companies", companies);
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
  public String doPost(@RequestParam("computerId") String id, @RequestParam("computerName") String name, @RequestParam("introduced") String introduced, @RequestParam("discontinued") String discontinued, @RequestParam("companyId") String companyId, @RequestParam("search") String search, @RequestParam("sort") String sort, @RequestParam("nombre") int nombre, @RequestParam("page") int page, Model model) {
    ComputerTO computerTO = new ComputerTO();
    computerTO.setId(id);
    computerTO.setName(name);
    computerTO.setIntroduced(introduced.toString());
    computerTO.setDiscontinued(discontinued.toString());
    CompanyTO companyTO = new CompanyTO();
    companyTO.setId(companyId);
    try {
      List<Company> company = this.companyService.showDetails(companyTO);
      if (company.size() > 0) {
        computerTO.setCompany(company.get(0));
      }
      List<CompanyTO> companies = this.companyService.listAll();
      model.addAttribute("companies", companies);
      this.computerService.update(computerTO);

      displayInformation(model, computerTO, search, sort, nombre, page);
      model.addAttribute("success", "Succès de la mise à jour");
    } catch (SQLException e) {
      this.logger.error(e.toString());
    } catch (EmptyNameException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(model, computerTO, search, sort, nombre, page);
        model.addAttribute("errorName", error);
        this.logger.error(e.toString());
      } catch (SQLException e1) {
        this.logger.error(e1.toString());
      }
    } catch (DateFormatException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(model, computerTO, search, sort, nombre, page);
        model.addAttribute("errorDate", error);
        this.logger.error(e.toString());
      } catch (SQLException e1) {
        this.logger.error(e1.toString());
      }
    } catch (DatePrecedenceException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(model, computerTO, search, sort, nombre, page);
        model.addAttribute("errorDate", error);
        this.logger.error(e.toString());
      } catch (SQLException e1) {
        this.logger.error(e1.toString());
      }
    } catch (SpecialCharacterException e) {
      try {
        String error = e.toString().split(": ")[1];
        displayInformation(model, computerTO, search, sort, nombre, page);
        model.addAttribute("error", error);
        this.logger.error(e.toString());
      } catch (SQLException e1) {
        this.logger.error(e1.toString());
      }
    }
    return VUE;
  }

  /**
   * Réaffiche les informations de l'ordinateur.
   * @param model le model de la page
   * @param computerTO l'ordinateur à afficher
   * @param nombre le nombre d'éléments affichés
   * @param page le numéro de la page
   * @param search la recherche
   * @param sort le trie
   * @throws SQLException SQLException
   */
  private void displayInformation(Model model, ComputerTO computerTO, String search, String sort, int nombre, int page) throws SQLException {
    Computer computer = this.computerService.showDetails(computerTO).get(0);
    model.addAttribute("computerId", computer.getId());
    model.addAttribute("computerName", computer.getName());
    model.addAttribute("introduced", computer.getIntroduced());
    model.addAttribute("discontinued", computer.getDiscontinued());
    model.addAttribute("companyComputer", computer.getCompany());
    model.addAttribute("nombre", nombre);
    model.addAttribute("page", page);
    model.addAttribute("search", search);
    model.addAttribute("sort", sort);
  }
}
