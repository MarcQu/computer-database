package exception;

public class SpecialCharacterException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur classe SpecialCharacterException.
   * @param message le message affiché quand le champ contient des caractères spéciaux
   */
  public SpecialCharacterException(String message) {
    super(message);
  }
}