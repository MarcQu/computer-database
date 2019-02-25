package model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Optional;

public class Computer {
  private Integer id;
  private String name;
  private Date introduced;
  private Date discontinued;
  private Company company;

  /**
   * Constructeur Computer vide.
   */
  public Computer() {
  }

  /**
   * Constructeur Computer.
   * @param id           l'id de l'ordinateur
   * @param name         le nom de l'ordinateur
   * @param introduced   la date d'introduction de l'ordinateur
   * @param discontinued la date d'interruption de l'ordinateur
   * @param company      la companie de l'ordinateur
   */
  public Computer(Integer id, String name, Date introduced, Date discontinued,
      Company company) {
    this.id = id;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.company = company;
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

  @Override
  public String toString() {
    String chaine = "Computer : ";
    Optional<Integer> optionalId = Optional.ofNullable(this.id);
    Optional<String> optionalName = Optional.ofNullable(this.name);
    Optional<Date> optionalIntroduced = Optional.ofNullable(this.introduced);
    Optional<Date> optionalDiscontinued = Optional.ofNullable(this.discontinued);
    Optional<Company> optionalCompany = Optional.ofNullable(this.company);
    if (optionalId.isPresent()) {
      chaine += this.id + " ";
    }
    if (optionalName.isPresent()) {
      chaine += this.name + " ";
    }
    if (optionalIntroduced.isPresent()) {
      chaine += this.introduced + " ";
    }
    if (optionalDiscontinued.isPresent()) {
      chaine += this.discontinued + " ";
    }
    if (optionalCompany.isPresent()) {
      chaine += this.company;
    }
    return chaine;
  }
}
