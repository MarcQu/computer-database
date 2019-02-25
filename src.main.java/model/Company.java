package model;

import java.util.Optional;

public class Company {
  private Integer id;
  private String name;

  /**
   * Contructeur Company vide.
   */
  public Company() {
  }

  /**
   * Constructeur Company.
   * @param id   l'id de la companie
   * @param name le nom de la companie
   */
  public Company(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  public Integer getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    String chaine = "Companie : ";
    Optional<Integer> optionalId = Optional.ofNullable(this.id);
    Optional<String> optionalName = Optional.ofNullable(this.name);
    if (optionalId.isPresent()) {
      chaine += this.id + " ";
    }
    if (optionalName.isPresent()) {
      chaine += this.name;
    }
    return chaine;
  }
}
