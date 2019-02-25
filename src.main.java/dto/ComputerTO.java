package dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import model.Computer;
import model.Company;

public class ComputerTO implements Serializable {
  private static final long serialVersionUID = 1L;
  private Integer id;
  private String name;
  private Date introduced;
  private Date discontinued;
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
    this.id = computer.getId();
    this.name = computer.getName();
    this.introduced = computer.getIntroduced();
    this.discontinued = computer.getDiscontinued();
    this.company = computer.getCompany();
  }

  public Integer getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Date getIntroduced() {
    return this.introduced;
  }

  public Date getDiscontinued() {
    return this.discontinued;
  }

  public Company getCompany() {
    return this.company;
  }

  public void setId(Integer id) {
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
    this.introduced = date;
  }

  /**
   * Set la date d'interruption de Timestamp à Date.
   * @param discontinued la date d'interruption
   */
  public void setDiscontinued(Timestamp discontinued) {
    Date date = new Date(discontinued.getTime());
    this.discontinued = date;
  }

  public void setCompany(Company company) {
    this.company = company;
  }
}
