package exception;

public class SpecialCharacterCompanyIdException extends SpecialCharacterException {
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur classe SpecialCharacterCompanyIdException.
   */
  public SpecialCharacterCompanyIdException() {
    super("L'id de la compagnie ne doit pas contenir de caractères spéciaux (\"#;)");
  }
}
