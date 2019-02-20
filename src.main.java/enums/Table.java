package enums;

public enum Table {
  COMPANY("company"), COMPUTER("computer");

  private String name;

  /**
   * Constructeur enum Table.
   * @param name le nom
   */
  Table(String name) {
    this.name = name;
  }

  /**
   * Affiche le nom.
   * @return name le nom
   */
  public String toString() {
    return name;
  }
}
