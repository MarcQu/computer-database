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

import dto.ComputerTO;
import service.ComputerService;

@Controller
@RequestMapping("/ComputerMenu")
public class ComputerMenu {
  private static final String VUE = "computerMenu";
  private Logger logger;
  private ComputerService computerService;

  /**
   * Initialise les instances.
   * @param computerService le service
   */
  public ComputerMenu(ComputerService computerService) {
    this.logger = LoggerFactory.getLogger(ComputerMenu.class);
    this.computerService = computerService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String doGet(@RequestParam("search") String search, @RequestParam("sort") String sort, @RequestParam("nombre") int nombre, @RequestParam("page") int page, Model model) {
    try {
      int nombreComputers = this.computerService.count(search);
      List<ComputerTO> computers = this.computerService.list(nombre, nombre * (page - 1), search, sort);
      model.addAttribute("nombreComputers", nombreComputers);
      model.addAttribute("computers", computers);
      int pages = nombreComputers / nombre + 1;
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
