package exception;

public class SpecialCharacterIdException extends SpecialCharacterException {
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur classe SpecialCharacterIdException.
   */
  public SpecialCharacterIdException() {
    super("L'id ne doit pas contenir de caractères spéciaux (\"#;)");
  }
}
