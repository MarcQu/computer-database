package dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

import model.Computer;
import model.Company;

public class ComputerTO implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-mm-dd");
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
    Optional<Date> optionalIntroduced = Optional.ofNullable(computer.getIntroduced());
    Optional<Date> optionalDiscontinued = Optional.ofNullable(computer.getDiscontinued());
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
  public void setIntroduced(Timestamp introduced) {
    Date date = new Date(introduced.getTime());
    Optional<Date> optionalIntroduced = Optional.ofNullable(date);
    if (optionalIntroduced.isPresent()) {
      this.introduced = DATEFORMAT.format(date);
    }
  }

  /**
   * Set la date d'interruption de Timestamp à Date.
   * @param discontinued la date d'interruption
   */
  public void setDiscontinued(Timestamp discontinued) {
    Date date = new Date(discontinued.getTime());
    Optional<Date> optionalDiscontinued = Optional.ofNullable(date);
    if (optionalDiscontinued.isPresent()) {
      this.discontinued = DATEFORMAT.format(date);
    }
  }

  public void setCompany(Company company) {
    this.company = company;
  }
}
