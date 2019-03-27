package exception;

public class SpecialCharacterNameException extends SpecialCharacterException {
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur classe SpecialCharacterNameException.
   */
  public SpecialCharacterNameException() {
    super("Le nom ne doit pas contenir de caractères spéciaux (\"#;)");
  }
}
