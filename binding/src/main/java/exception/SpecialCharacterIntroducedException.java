package exception;

public class SpecialCharacterIntroducedException extends SpecialCharacterException {
  private static final long serialVersionUID = 1L;

  /**
   * Constructeur classe SpecialCharacterIntroducedException.
   */
  public SpecialCharacterIntroducedException() {
    super("La date d'introduction ne doit pas contenir de caractères spéciaux (\"#;)");
  }
}
