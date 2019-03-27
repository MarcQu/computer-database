package model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import enumeration.ERole;

public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @ManyToOne
  @JoinColumn(name = "name")
  private String name;
  
  @Column(name = "role")
  private ERole role;

  public Role() {   
  }
  
  public Role(int id, String name, ERole role) {
    this.id = id;
    this.name = name;
    this.role = role;
  }

  public int getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }
  
  public ERole getRole() {
    return this.role;
  }
 
  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }
  
  public void setRole(ERole role) {
    this.role = role;
  }
}
