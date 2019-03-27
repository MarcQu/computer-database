package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "name")
  private String name;

  @Column(name = "password")
  private String password;
  
  @Column(name = "enabled")
  private boolean enabled;

  public User() {   
  }
  
  public User(String name, String password, boolean enabled) {
    this.name = name;
    this.password = password;
    this.enabled = enabled;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getPassword() {
    return this.password;
  }
  
  public boolean getEnabled() {
    return this.enabled;
  }
  
  public void setName(String name) {
    this.name = name;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
