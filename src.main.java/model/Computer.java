package model;

import java.sql.Timestamp;

public class Computer {
  private Integer id;
  private String name;
  private Timestamp introduced;
  private Timestamp discontinued;
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
  public Computer(Integer id, String name, Timestamp introduced, Timestamp discontinued,
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

  public Timestamp getIntroduced() {
    return this.introduced;
  }

  public Timestamp getDiscontinued() {
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

  public void setIntroduced(Timestamp introduced) {
    this.introduced = introduced;
  }

  public void setDiscontinued(Timestamp discontinued) {
    this.discontinued = discontinued;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  @Override
  public String toString() {
    String chaine = "Computer : ";
    if (this.id != null) {
      chaine += this.id + " ";
    }
    if (this.name != null) {
      chaine += this.name + " ";
    }
    if (this.introduced != null) {
      chaine += this.introduced + " ";
    }
    if (this.discontinued != null) {
      chaine += this.discontinued + " ";
    }
    if (this.company != null) {
      chaine += this.company.getId();
    }
    return chaine;
  }
}
