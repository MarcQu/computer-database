package controller;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dto.ComputerTO;
import model.Computer;
import service.ComputerService;

@RestController
@RequestMapping("/ComputerApi")
public class ComputerApi {
  private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  private Logger logger;
  private ComputerService computerService;

  /**
   * Initialise les instances.
   * @param computerService le service
   */
  public ComputerApi(ComputerService computerService) {
    this.logger = LoggerFactory.getLogger(ComputerApi.class);
    this.computerService = computerService;
  }

  @RequestMapping(value = "/{computerId}", method = RequestMethod.GET)
  public ComputerTO getComputer(@PathVariable Integer computerId) {
    Computer computer = new Computer();
    computer.setId(computerId);
    ComputerTO computerTO = new ComputerTO();
    try {
      computerTO = this.computerService.showDetails(computerId).get(0);
    } catch (SQLException e) {
      this.logger.error(e.toString());
    }
    return computerTO;
  }

  @RequestMapping(value = {"/{page}/{nombre}/{sort}/{search}", "/{page}/{nombre}/{sort}"}, method = RequestMethod.GET)
  public List<ComputerTO> getComputers(@PathVariable int page, @PathVariable int nombre, @PathVariable Optional<String> search, @PathVariable String sort) {
    List<ComputerTO> computers = new ArrayList<>();
    try {
      if(search.isPresent()) {
        computers = this.computerService.list(nombre, nombre * (page - 1), search.get(), sort);
      } else {
        computers = this.computerService.list(nombre, nombre * (page - 1), "", sort);
      }
    } catch (SQLException e) {
      this.logger.error(e.toString());
    }
    return computers;
  }

  @RequestMapping(value = {"/count/{search}", "/count"}, method = RequestMethod.GET)
  public int getCount(@PathVariable Optional<String> search) {
    int count = 0;
    try {
      if(search.isPresent()) {
        count = this.computerService.count(search.get());
      } else {
        count = this.computerService.count("");
      }
    } catch (SQLException e) {
      this.logger.error(e.toString());
    }
    return count;
  }
}

