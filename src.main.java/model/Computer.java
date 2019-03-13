package model;

import java.time.LocalDate;
import java.util.Optional;

public class Computer {
  private Integer id;
  private String name;
  private LocalDate introduced;
  private LocalDate discontinued;
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
  public Computer(Integer id, String name, LocalDate introduced, LocalDate discontinued,
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

  public LocalDate getIntroduced() {
    return this.introduced;
  }

  public LocalDate getDiscontinued() {
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
  public void setIntroduced(LocalDate introduced) {
    this.introduced = introduced;
  }

  /**
   * Set la date d'interruption de Timestamp à Date.
   * @param discontinued la date d'interruption
   */
  public void setDiscontinued(LocalDate discontinued) {
    this.discontinued = discontinued;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  @Override
  public String toString() {
    String chaine = "Computer : ";
    Optional<Integer> optionalId = Optional.ofNullable(this.id);
    Optional<String> optionalName = Optional.ofNullable(this.name);
    Optional<LocalDate> optionalIntroduced = Optional.ofNullable(this.introduced);
    Optional<LocalDate> optionalDiscontinued = Optional.ofNullable(this.discontinued);
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

class ComputerBuilder {
  private final Integer id;
  private String name;
  private LocalDate introduced;
  private LocalDate discontinued;
  private Company company;

  /**
   * Contructeur ComputerBuilder.
   * @param id l'id de la compagnie
   * @param name le nom de la compagnie
   * @param introduced la date d'introduction de la compagnie
   * @param discontinued la date d'interruption de la compagnie
   * @param company la compagnie de l'ordinateur
   */
  ComputerBuilder(Integer id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
    this.id = id;
    this.name = name;
    this.introduced = introduced;
    this.discontinued = discontinued;
    this.company = company;
  }

  /**
   * Set le nom du ComputerBuilder.
   * @param name le nom de l'ordinateur
   * @return ComputerBuilder
   */
  public ComputerBuilder setName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Set la date d'introduction du CompagnyBuilder.
   * @param introduced la date d'introduction de l'ordinateur
   * @return CompagnyBuilder
   */
  public ComputerBuilder setIntroduced(LocalDate introduced) {
    this.introduced = introduced;
    return this;
  }

  /**
   * Set la date d'interruption du CompagnyBuilder.
   * @param discontinued la date d'interruption de l'ordinateur
   * @return CompagnyBuilder
   */
  public ComputerBuilder setDiscontinued(LocalDate discontinued) {
    this.discontinued = discontinued;
    return this;
  }

  /**
   * Set la compagnie  du CompagnyBuilder.
   * @param company la company de l'ordinateur
   * @return CompagnyBuilder
   */
  public ComputerBuilder setDiscontinued(Company company) {
    this.company = company;
    return this;
  }
}
