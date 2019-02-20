package enums;

public enum Action {
  LIST("List"), SHOW("Show"), CREATE("Create"), UPDATE("Update"), DELETE("Delete");

  private String name;

  /**
   * Constructeur enum Action.
   * @param name le nom
   */
  Action(String name) {
    this.name = name;
  }

  /**
   * Afficher le nom.
   * @return name le nom
   */
  public String toString() {
    return name;
  }
}
