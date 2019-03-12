package dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import model.Computer;
import model.Company;

public class ComputerTO implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final DateTimeFormatter DATEFORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private String id;
  private String name;
  private String introduced;
  private String discontinued;
  private Company company;

  /**
   * Contructeur ComputerTO vide.
   */
  public ComputerTO() {
  }

  /**
   * Constructeur ComputerTO.
   * @param computer l'ordinateur
   */
  public ComputerTO(Computer computer) {
    this.id = Integer.toString(computer.getId());
    this.name = computer.getName();
    Optional<LocalDate> optionalIntroduced = Optional.ofNullable(computer.getIntroduced());
    Optional<LocalDate> optionalDiscontinued = Optional.ofNullable(computer.getDiscontinued());
    if (optionalIntroduced.isPresent()) {
      this.introduced = DATEFORMAT.format(computer.getIntroduced());
    }
    if (optionalDiscontinued.isPresent()) {
      this.discontinued = DATEFORMAT.format(computer.getDiscontinued());
    }
    this.company = computer.getCompany();
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getIntroduced() {
    return this.introduced;
  }

  public String getDiscontinued() {
    return this.discontinued;
  }

  public Company getCompany() {
    return this.company;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * Set la date d'introduction de Timestamp à Date.
   * @param introduced la date d'introduction
   */
  public void setIntroduced(String introduced) {
    if (!"".equals(introduced)) {
      introduced += " 00:00:00";
    }
    Optional<String> optionalIntroduced = Optional.ofNullable(introduced);
    if (optionalIntroduced.isPresent()) {
      this.introduced = introduced;
    }
  }

  /**
   * Set la date d'interruption de Timestamp à Date.
   * @param discontinued la date d'interruption
   */
  public void setDiscontinued(String discontinued) {
    if (!"".equals(discontinued)) {
      discontinued += " 00:00:00";
    }
    Optional<String> optionalDiscontinued = Optional.ofNullable(discontinued);
    if (optionalDiscontinued.isPresent()) {
      this.discontinued = discontinued;
    }
  }

  public void setCompany(Company company) {
    this.company = company;
  }
}
