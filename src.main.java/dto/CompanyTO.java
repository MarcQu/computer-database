package dto;

import java.io.Serializable;
import model.Company;
public class CompanyTO implements Serializable {
  private static final long serialVersionUID = 1L;
  private int id;
  private String name;

  /**
   * Contructeur CompanyTO vide.
   */
  public CompanyTO() {
  }

  /**
   * Constructeur CompanyTO.
   * @param company la companie
   */
  public CompanyTO(Company company) {
    this.id = company.getId();
    this.name = company.getName();
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
}
