package exception;

public class SpecialCharacterDiscontinuedException extends SpecialCharacterException {
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur classe SpecialCharacterDiscontinuedException.
   */
  public SpecialCharacterDiscontinuedException() {
    super("La date d'interruption ne doit pas contenir de caractères spéciaux (\"#;)");
  }
}
