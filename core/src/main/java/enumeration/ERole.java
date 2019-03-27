package enumeration;

public enum ERole {
  USER("user"),
  ADMIN("admin");
  
  private String role;
  
  ERole(String role){
    this.role = role;
  }

  public String toString() {
    return this.role;
  }
}
