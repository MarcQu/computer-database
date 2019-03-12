package dto;

import java.io.Serializable;
import model.Company;
public class CompanyTO implements Serializable {
  private static final long serialVersionUID = 1L;
  private String id;
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
    this.id = Integer.toString(company.getId());
    this.name = company.getName();
  }

  public String getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }
}
