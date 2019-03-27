package model;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "company")
public class Company {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;
  @Column(name = "name")
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

class CompanyBuilder {
  private final Integer id;
  private String name;

  /**
   * Contructeur CompanyBuilder.
   * @param id l'id de la compagnie
   * @param name le nom de la compagnie
   */
  CompanyBuilder(Integer id, String name) {
    this.id = id;
    this.name = name;
  }

  /**
   * Set le nom du CompagnyBuilder.
   * @param name le nom de la compagnie
   * @return CompagnyBuilder
   */
  public CompanyBuilder setName(String name) {
    this.name = name;
    return this;
  }

  /**
   * Construit l'instance Company.
   * @return Company la compagnie
   */
  public Company build() {
    return new Company(this.id, this.name);
  }
}
